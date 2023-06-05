package com.wisedrive.customerapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.wisedrive.customerapp.adapters.AdapterCPlansList;
import com.wisedrive.customerapp.adapters.AdapterWarrantyList;
import com.wisedrive.customerapp.adapters.Adapter_Additional_services;
import com.wisedrive.customerapp.adapters.Adapter_class_mycar;
import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.PojoPlansList;
import com.wisedrive.customerapp.pojos.PojoSpecificPacList;
import com.wisedrive.customerapp.pojos.PojoWarrantyList;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopupSelectPack extends BottomSheetDialogFragment
{
    TextView label;
    RecyclerView rv_plans_list,rv_warranty_list;
    public AdapterCPlansList adapterCPlansList;
    ArrayList<PojoPlansList> pojoPlansLists;
    public ArrayList<PojoSpecificPacList> pojoSpecificPacLists=new ArrayList<>();
    AdapterWarrantyList adapterWarrantyList;
    ArrayList<PojoWarrantyList> pojoWarrantyLists;
    Activity activity;
    RelativeLayout confirm;
    private ApiInterface apiInterface;
    public int plan_size=0;
    TextView pack_title;
    public int count=0;
    public  static  PopupSelectPack instance;
    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_popup_select_pack, container, false);
        activity=getActivity();
        instance=this;
        confirm=v.findViewById(R.id.confirm);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        rv_warranty_list=v.findViewById(R.id.rv_warranty_list);
        rv_plans_list=v.findViewById(R.id.rv_plans_list);
        pack_title=v.findViewById(R.id.pack_title);
        label=v.findViewById(R.id.label);

        if(SPHelper.it_is.equals("upgrade"))
        {
            label.setText("Service Includes");
            rv_plans_list.setVisibility(View.GONE);
            confirm.setVisibility(View.GONE);
            pack_title.setVisibility(View.GONE);
            LinearLayoutManager l2=new LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false);
            adapterWarrantyList=new AdapterWarrantyList(pojoSpecificPacLists,activity);
            rv_warranty_list.setLayoutManager(l2);
            rv_warranty_list.setAdapter(adapterWarrantyList);
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapterWarrantyList.notifyDataSetChanged();

                }
            });

        }else {
            pack_title.setVisibility(View.VISIBLE);
            label.setText("Customize your plan");
            rv_plans_list.setVisibility(View.VISIBLE);
            confirm.setVisibility(View.VISIBLE);
            get_pack_plan_list();
        }
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.package_id=pojoWarrantyLists.get(adapterCPlansList.selectedPosition).getPackage_id();
                SPHelper.package_name=pojoWarrantyLists.get(adapterCPlansList.selectedPosition).getDisplay_name();
                Warranty_Description.getInstance().get_product_list();
                dismiss();
            }
        });


        return  v;
    }

    public  void get_plan(){
        count=1;
        pojoPlansLists =new ArrayList<>();
        pojoPlansLists.add(new PojoPlansList("",""));
        // pojoPlansLists=appResponse.getResponseModel().getPlanList();
        LinearLayoutManager l1=new LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false);
        adapterCPlansList=new AdapterCPlansList(pojoPlansLists,activity);
        rv_plans_list.setLayoutManager(l1);
        rv_plans_list.setAdapter(adapterCPlansList);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapterCPlansList.notifyDataSetChanged();
            }
        });
    }
    public  void get_pack_plan_list()
    {
        if (!Connectivity.isNetworkConnected(activity)) {
            Toast.makeText(activity,
                    "Plaese Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        } else {
           // idPBLoading.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.get_pack_based_plan(SPHelper.getSPData(activity,SPHelper.lead_id,""),
                    SPHelper.getSPData(activity,SPHelper.customer_id,""),SPHelper.plan_id);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                    AppResponse appResponse = response.body();
                    assert appResponse != null;
                    String response_code = appResponse.getResponseType();
                    if (response.body() != null)
                    {
                        if (response_code.equals("200"))
                        {
                          //  idPBLoading.setVisibility(View.GONE);
                            pojoWarrantyLists=new ArrayList<>();
                            pojoWarrantyLists=appResponse.getResponseModel().getPackBasedOnPlan();

                            SPHelper.plan_size=pojoWarrantyLists.size();
                            if(count==0){
                                get_plan();
                            }

                           get_specific_pro_list();


                        } else if (response_code.equals("300")) {
                           // idPBLoading.setVisibility(View.GONE);
                        }
                    } else {
                        //idPBLoading.setVisibility(View.GONE);
                        Toast.makeText(activity, "internal server error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                    Toast.makeText(activity,
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    //idPBLoading.setVisibility(View.GONE);
                }
            });
        }

    }


    public void get_specific_pro_list(){
        pack_title.setText(pojoWarrantyLists.get(adapterCPlansList.selectedPosition).getDisplay_name());
        pack_title.setText(pojoWarrantyLists.get(adapterCPlansList.selectedPosition).getDisplay_name());
        pojoSpecificPacLists=pojoWarrantyLists.get(adapterCPlansList.selectedPosition).getProductList();
        LinearLayoutManager l2=new LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false);
        adapterWarrantyList=new AdapterWarrantyList(pojoSpecificPacLists,activity);
        rv_warranty_list.setLayoutManager(l2);
        rv_warranty_list.setAdapter(adapterWarrantyList);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapterWarrantyList.notifyDataSetChanged();

            }
        });
    }

    public  static PopupSelectPack getInstance() {
        return instance;
    }

}