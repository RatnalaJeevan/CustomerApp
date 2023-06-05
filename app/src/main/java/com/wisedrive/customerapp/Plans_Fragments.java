package com.wisedrive.customerapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appsflyer.AppsFlyerLib;
import com.bumptech.glide.Glide;
import com.wisedrive.customerapp.adapters.Adapter_Combo_Plans;
import com.wisedrive.customerapp.adapters.Adapter_Comprehensive_service;
import com.wisedrive.customerapp.adapters.Adapter_Service_and_Maintanance_Plan;
import com.wisedrive.customerapp.adapters.Adapter_Exteneded_Warranty_Plan;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.pojos.Pojo_Combo_Plans;
import com.wisedrive.customerapp.pojos.Pojo_Service_and_Maintanance_Plans;
import com.wisedrive.customerapp.pojos.Pojo_Extended_Warranty_Plan;
import com.wisedrive.customerapp.pojos.Pojo_Comprehensive_Plans;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Plans_Fragments extends Fragment
{
    ImageView iv_a;
    TextView label_cw,label_get_cw,check_my_car;
    ProgressBar idPBLoading;
    private ApiInterface apiInterface;
    private RecyclerView rv_warranty_pac_list,rv_service_pac_list,rv_combo_pac_list ;
    Adapter_Exteneded_Warranty_Plan adapter_plans_1;
    ArrayList<Pojo_Extended_Warranty_Plan> pojo_extended_warranty_planArrayList;
    private RecyclerView recycler_v_addon;
    Adapter_Service_and_Maintanance_Plan adapter_service_and_maintanance_plan;
    ArrayList<Pojo_Service_and_Maintanance_Plans> pojo_service_and_maintanance_plansArrayList;
    Adapter_Combo_Plans adapter_combo_plans;
    ArrayList<Pojo_Combo_Plans> pojo_combo_plansArrayList;
    Activity activity;
    RelativeLayout rl_check_my_car,rl1;
    String pack_id;
    String main_pack_id;
    String pack_name;
    String pack_type_name_id,id="";
    public Plans_Fragments(){
    }

    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragments_plans, container, false);
        activity=getActivity();
        init_params(view);
        getActivity().getWindow().setStatusBarColor(getActivity().getColor(R.color.new_app_bg));
        get_pack_list();

        rl_check_my_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                if(id.equals("8"))
                {
                    Intent intent=new Intent(activity,EnterCarDetails.class);
                    startActivity(intent);
                }else if(id.equals("1")){
                    PopupWDBenefits bottomSheetDialogFragment = new PopupWDBenefits();
                    bottomSheetDialogFragment.show(((FragmentActivity)activity).getSupportFragmentManager(), bottomSheetDialogFragment.getTag());

                }else if(id.equals("5")){
                    ActYourPack bottomSheetDialogFragment = new ActYourPack();
                    bottomSheetDialogFragment.show(((FragmentActivity)activity).getSupportFragmentManager(), bottomSheetDialogFragment.getTag());

                }
                else if(id.equals("7")){

                    SPHelper.pojo_service_includes=new ArrayList<>();
                    PopupFullPayment bottomSheetDialogFragment = new PopupFullPayment();
                    bottomSheetDialogFragment.show(((FragmentActivity)activity).getSupportFragmentManager(), bottomSheetDialogFragment.getTag());

                }else if(id.equals("4")){
                    PopupChooseTypeInsp bottomSheetDialogFragment = new PopupChooseTypeInsp();
                    bottomSheetDialogFragment.show(((FragmentActivity)activity).getSupportFragmentManager(), bottomSheetDialogFragment.getTag());

                }
                   else if(id.equals("3")||
                        id.equals("2")||
                        id.equals("6")
                )
                   {
                SPHelper.package_id=pack_id;
                SPHelper.package_name=pack_name;
                SPHelper.main_pack_id=main_pack_id;
                SPHelper.plan_id=pack_type_name_id;
                Intent intent=new Intent(activity,Warranty_Description.class);
                startActivity(intent);


                }
                else if(id.equals("10")){
                    SPHelper.comingfrom="";
                    SPHelper.fragment_is="act";
                    Intent intent=new Intent(activity,CustomerHomepage.class);
                    startActivity(intent);
                }

                //push notifiations
//                Map<String, Object> eventValue = new HashMap<String, Object>();
//                eventValue.put("login", "member");
//                AppsFlyerLib.getInstance().sendPushNotificationData(activity);
            }
        });

        get_popup();
        return view;
    }

    public void init_params(View view){
        iv_a=view.findViewById(R.id.iv_a);
        rl1=view.findViewById(R.id.rl1);
        rl_check_my_car=view.findViewById(R.id.rl_check_my_car);
        idPBLoading=view.findViewById(R.id.idPBLoading);
        rv_warranty_pac_list =  view.findViewById(R.id.rv_warranty_pac_list);
        rv_service_pac_list =view.findViewById(R.id.rv_service_pac_list);
        rv_combo_pac_list  = view.findViewById(R.id.rv_combo_pac_list);
        label_cw= view.findViewById(R.id.label_cw);
        label_get_cw= view.findViewById(R.id.label_get_cw);
        check_my_car= view.findViewById(R.id.check_my_car);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public  void get_pack_list()
    {
            if (!Connectivity.isNetworkConnected(activity)) {
                Toast.makeText(activity,
                        "Plaese Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            } else {
                 idPBLoading.setVisibility(View.VISIBLE);
                Call<AppResponse> call = apiInterface.getNewPackList(SPHelper.getSPData(activity,SPHelper.lead_id,""),
                SPHelper.getSPData(activity,SPHelper.customer_id,""));
                call.enqueue(new Callback<AppResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                        AppResponse appResponse = response.body();
                        assert appResponse != null;
                        String response_code = appResponse.getResponseType();
                        if (response.body() != null) {
                            if (response_code.equals("200"))
                            {
                                idPBLoading.setVisibility(View.GONE);
                                pojo_combo_plansArrayList= new ArrayList<>();
                                pojo_combo_plansArrayList=appResponse.getResponseModel().getPackList();
                                adapter_combo_plans = new Adapter_Combo_Plans(getContext(), pojo_combo_plansArrayList);
                                LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                                rv_combo_pac_list.setLayoutManager(linearLayoutManager3);
                                rv_combo_pac_list.setAdapter(adapter_combo_plans);

                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter_combo_plans.notifyDataSetChanged();

                                    }
                                });
                            } else if (response_code.equals("300")) {
                                 idPBLoading.setVisibility(View.GONE);
                            }
                        } else {
                            idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(activity, "internal server error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                        Toast.makeText(activity,
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                         idPBLoading.setVisibility(View.GONE);
                    }
                });
            }
    }

    public  void get_popup()
    {
        if (!Connectivity.isNetworkConnected(activity)) {
            Toast.makeText(activity,
                    "Plaese Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        } else {
            idPBLoading.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.get_display_popup(
                    SPHelper.getSPData(activity,SPHelper.customer_id,""),
                    SPHelper.getSPData(activity,SPHelper.lead_id,""));
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                    AppResponse appResponse = response.body();
                    assert appResponse != null;
                    String response_code = appResponse.getResponseType();
                    if (response.body() != null) {
                        if (response_code.equals("200"))
                        {
                            idPBLoading.setVisibility(View.GONE);

                            String btn_name=appResponse.getResponseModel().getDisplayPopup().getButtonName();
                            String disp_msg=appResponse.getResponseModel().getDisplayPopup().getDisplaymsg();
                            String btn_clr=appResponse.getResponseModel().getDisplayPopup().getButtoncolor();
                            String txt_clrr=appResponse.getResponseModel().getDisplayPopup().getTextcolor();
                            String title=appResponse.getResponseModel().getDisplayPopup().getTitle();
                            String ar_img=appResponse.getResponseModel().getDisplayPopup().getArrowimg();
                            String veh_no=appResponse.getResponseModel().getDisplayPopup().getVehicle_no();
                            String caat_id=appResponse.getResponseModel().getDisplayPopup().getCategory_id();
                            String dp_id=appResponse.getResponseModel().getDisplayPopup().getDpp_id();
                             pack_id=appResponse.getResponseModel().getDisplayPopup().getPackage_id();
                             main_pack_id=appResponse.getResponseModel().getDisplayPopup().getMain_package_id();
                             pack_name=appResponse.getResponseModel().getDisplayPopup().getPackage_name();
                             pack_type_name_id=appResponse.getResponseModel().getDisplayPopup().getPackage_type_name_id();
                             id=appResponse.getResponseModel().getDisplayPopup().getId();
                            double rem_amount=appResponse.getResponseModel().getDisplayPopup().getRemaining_amount();
                            SPHelper.show_add_car=appResponse.getResponseModel().getDisplayPopup().getShowAddcarDynamic();
                            SPHelper.show_self=appResponse.getResponseModel().getDisplayPopup().getSelfinspection();
                            SPHelper.is_odo_update=appResponse.getResponseModel().getDisplayPopup().getOdometerUpdate();
                            SPHelper.remain_amount=rem_amount;
                            if(caat_id==null||caat_id.equals("null")){
                                SPHelper.cat_id="";
                            }else {
                                SPHelper.cat_id=caat_id;
                            }
                            if(dp_id==null||dp_id.equals("null")){
                                SPHelper.dpp_id="";
                            }else {
                                SPHelper.dpp_id=dp_id;
                            }
                            if(veh_no==null||veh_no.equals("null")){
                                SPHelper.veh_no="";
                            }else {
                                SPHelper.veh_no=veh_no;
                            }
                            if(disp_msg.equals(""))
                            {
                                rl1.setVisibility(View.GONE);

                            }else if(btn_name.equals("")){
                                rl1.setVisibility(View.VISIBLE);
                                rl_check_my_car.setVisibility(View.GONE);
                            }else {
                                rl1.setVisibility(View.VISIBLE);
                                rl_check_my_car.setVisibility(View.VISIBLE);
                            }
                            check_my_car.setText(btn_name);
                            label_get_cw.setText(disp_msg);
                            label_cw.setText(title);
                            if(btn_clr==null||btn_clr.equals("null")||btn_clr.equals("")){

                            }else {
                                rl1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(btn_clr)));
                                check_my_car.setTextColor(Color.parseColor(btn_clr));
                            }
//                            if(txt_clrr==null||txt_clrr.equals("null")||txt_clrr.equals("")){
//
//                            }else {
//                                check_my_car.setTextColor(Color.parseColor(txt_clrr));
//                            }

                            Glide.with(activity).load(ar_img).placeholder(R.drawable.r_arrow).into(iv_a);


                        } else if (response_code.equals("300")) {
                            idPBLoading.setVisibility(View.GONE);
                        }
                    } else {
                        idPBLoading.setVisibility(View.GONE);
                        Toast.makeText(activity, "internal server error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                    Toast.makeText(activity,
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    idPBLoading.setVisibility(View.GONE);
                }
            });
        }
    }

}







