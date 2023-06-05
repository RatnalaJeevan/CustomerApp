package com.wisedrive.customerapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.stmt.query.In;
import com.wisedrive.customerapp.adapters.Adapter_Additional_services;
import com.wisedrive.customerapp.adapters.Adapter_Addons_list;
import com.wisedrive.customerapp.adapters.Adapter_My_Car_Page_Package_list;
import com.wisedrive.customerapp.adapters.Adapter_class_mycar;
import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.Pojo_Class_Mycar;
import com.wisedrive.customerapp.pojos.Pojo_My_Car_page_package_list;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyCar_Fragment extends Fragment implements ViewPager.OnPageChangeListener{

    ProgressBar idPBLoading;
    private ApiInterface apiInterface;
    Adapter_class_mycar adapter_class_mycar;
    ArrayList<Pojo_Class_Mycar> pojo_class_mycarArrayList;
    public RelativeLayout rl_my_cars,rl_add_car,rl_no_packages,buy_packages,
            rl_act_warr,rl_purchase_pac,rl1,rl_purchase;
    private  RecyclerView rv_service_list;
    Adapter_My_Car_Page_Package_list adapter_my_car_warranty_list;
    ArrayList<Pojo_My_Car_page_package_list>pojo_my_car_warranty_listArrayList;
    Activity activity;
    ViewPager view_pager_2;
    String veh_id="";
    public TextView text_name,act_warr;
    public  static MyCar_Fragment  instance;
    public MyCar_Fragment(){

    }
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_car_, container, false);
        SPHelper.current_page="mycars";
        instance=this;
        rl_purchase=view.findViewById(R.id.rl_purchase);
        rl_purchase_pac=view.findViewById(R.id.rl_purchase_pac);
        rl1=view.findViewById(R.id.rl1);
        text_name=view.findViewById(R.id.text_name);
        act_warr=view.findViewById(R.id.act_warr);
        rl_act_warr=view.findViewById(R.id.rl_act_warr);
        idPBLoading=view.findViewById(R.id.idPBLoading);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        buy_packages=view.findViewById(R.id.buy_packages);
        view_pager_2 = view.findViewById(R.id.view_pager_2);
        rv_service_list =view.findViewById(R.id. rv_service_list);
        rl_my_cars=view.findViewById(R.id.rl_my_cars);
        rl_add_car=view.findViewById(R.id.rl_add_car);
        rl_no_packages=view.findViewById(R.id.rl_no_packages);
        activity=getActivity();
        getActivity().getWindow().setStatusBarColor(getActivity().getColor(R.color.new_app_bg));

        rl_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.fragment_is="plans";
                Intent intent=new Intent(activity,CustomerHomepage.class);
                startActivity(intent);
            }
        });
        rl_add_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.carmodelid="";
                SPHelper.carbrandid="";
                Intent intent=new Intent(activity,EnterCarDetails.class);
                startActivity(intent);
            }
        });

        rl_act_warr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                    ActYourPack bottomSheetDialogFragment = new ActYourPack();
                    bottomSheetDialogFragment.show(((FragmentActivity)activity).getSupportFragmentManager(),
                            bottomSheetDialogFragment.getTag());
            }
        });
        get_cars_list();
        return view;
    }

    public  void get_cars_list()
    {
        if (!Connectivity.isNetworkConnected(activity)) {
            Toast.makeText(activity,
                    "Plaese Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        } else {
            idPBLoading.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.getVehList(SPHelper.getSPData(activity,SPHelper.lead_id,""),
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
                            pojo_class_mycarArrayList = new ArrayList<>();
                            pojo_class_mycarArrayList=appResponse.getResponseModel().getVehicleList();
                            if(pojo_class_mycarArrayList.isEmpty()){
                                //show add car
                                rl_my_cars.setVisibility(View.GONE);
                                rl_add_car.setVisibility(View.VISIBLE);
                                CustomerHomepage.getInstance().rl_add_car_button.setVisibility(View.GONE);
                            }else{
                                CustomerHomepage.getInstance().rl_add_car_button.setVisibility(View.VISIBLE);
                                rl_my_cars.setVisibility(View.VISIBLE);
                                rl_add_car.setVisibility(View.GONE);
                                SPHelper.d_id=pojo_class_mycarArrayList.get(0).getDealer_id();
                                veh_id=pojo_class_mycarArrayList.get(0).getVehicle_id();
                                SPHelper.veh_no=pojo_class_mycarArrayList.get(0).getVehicle_no();
                                SPHelper.veh_make=pojo_class_mycarArrayList.get(0).getVehicle_make();
                                SPHelper.veh_model=pojo_class_mycarArrayList.get(0).getVehicle_model();
                                SPHelper.fuel_type=pojo_class_mycarArrayList.get(0).getFuel_type();
                                SPHelper.mnf_year=pojo_class_mycarArrayList.get(0).getManufacturing_year();
                                SPHelper.kms_driven=pojo_class_mycarArrayList.get(0).getOdometer();
                                SPHelper.color=pojo_class_mycarArrayList.get(0).getColor();
                                SPHelper.veh_id=pojo_class_mycarArrayList.get(0).getVehicle_id();
                                SPHelper.lead_veh_id=pojo_class_mycarArrayList.get(0).getLead_veicle_id();
                                SPHelper.w_ins_id="";
                                adapter_class_mycar = new Adapter_class_mycar(activity, pojo_class_mycarArrayList);
                                view_pager_2.setCurrentItem(0);
                                view_pager_2.setAdapter(adapter_class_mycar);
                                view_pager_2.setOnPageChangeListener(MyCar_Fragment.this);

                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter_class_mycar.notifyDataSetChanged();
                                    }
                                });
                                getVehProductDetails();
                            }
                        }
                        else if (response_code.equals("300")) {
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


    public  void getVehProductDetails()
    {
        if(veh_id==null||veh_id.equals("null")){
            SPHelper.veh_id="";
        }else {
            SPHelper.veh_id=veh_id;
        }
        if (!Connectivity.isNetworkConnected(activity)) {
            Toast.makeText(activity,
                    "Plaese Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        } else {
            idPBLoading.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.getVehProductDetails(SPHelper.veh_id);
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
                            pojo_my_car_warranty_listArrayList = new ArrayList<>();
                            pojo_my_car_warranty_listArrayList=appResponse.getResponseModel().getVehicleProductDetails();

                            if(pojo_my_car_warranty_listArrayList.isEmpty()){

                                rl1.setVisibility(View.GONE);
                                rl_purchase_pac.setVisibility(View.VISIBLE);
                                rv_service_list.setVisibility(View.GONE);
                            }else{

                                rl_purchase_pac.setVisibility(View.GONE);
                                rv_service_list.setVisibility(View.VISIBLE);
                                adapter_my_car_warranty_list= new Adapter_My_Car_Page_Package_list(activity, pojo_my_car_warranty_listArrayList);
                                LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
                                rv_service_list.setLayoutManager(linearLayoutManager2);
                                rv_service_list.setAdapter(adapter_my_car_warranty_list);

                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter_class_mycar.notifyDataSetChanged();
                                    }
                                });
                            }

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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        veh_id=pojo_class_mycarArrayList.get(position).getVehicle_id();
        SPHelper.veh_no=pojo_class_mycarArrayList.get(position).getVehicle_no();
        SPHelper.d_id=pojo_class_mycarArrayList.get(position).getDealer_id();
        SPHelper.veh_make=pojo_class_mycarArrayList.get(position).getVehicle_make();
        SPHelper.veh_model=pojo_class_mycarArrayList.get(position).getVehicle_model();
        SPHelper.fuel_type=pojo_class_mycarArrayList.get(position).getFuel_type();
        SPHelper.mnf_year=pojo_class_mycarArrayList.get(position).getManufacturing_year();
        SPHelper.kms_driven=pojo_class_mycarArrayList.get(position).getOdometer();
        SPHelper.color=pojo_class_mycarArrayList.get(position).getColor();
        SPHelper.veh_id=pojo_class_mycarArrayList.get(position).getVehicle_id();
        SPHelper.lead_veh_id=pojo_class_mycarArrayList.get(position).getLead_veicle_id();
        SPHelper.w_ins_id="";
        getVehProductDetails();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public static MyCar_Fragment get_instance(){
        return instance;
    }
}