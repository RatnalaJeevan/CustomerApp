package com.wisedrive.customerapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Plans_Fragments extends Fragment
{
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
    public Plans_Fragments(){

    }

    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragments_plans, container, false);
        activity=getActivity();
        init_params(view);

        get_pack_list();

        return view;
    }

    public void init_params(View view){
        idPBLoading=view.findViewById(R.id.idPBLoading);
        rv_warranty_pac_list =  view.findViewById(R.id.rv_warranty_pac_list);
        rv_service_pac_list =view.findViewById(R.id.rv_service_pac_list);
        recycler_v_addon=view.findViewById(R.id.recycler_v_addon);
        rv_combo_pac_list  = view.findViewById(R.id.rv_combo_pac_list);
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
                Call<AppResponse> call = apiInterface.getPackList(SPHelper.getSPData(activity,SPHelper.lead_id,""),
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
                                pojo_extended_warranty_planArrayList = new ArrayList<>();
                                pojo_extended_warranty_planArrayList=appResponse.getResponseModel().getWarrantyPackList();
                                adapter_plans_1 = new Adapter_Exteneded_Warranty_Plan(getContext(),  pojo_extended_warranty_planArrayList);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                                rv_warranty_pac_list.setLayoutManager(linearLayoutManager);
                                rv_warranty_pac_list.setAdapter(adapter_plans_1);

                                pojo_service_and_maintanance_plansArrayList= new ArrayList<>();
                                pojo_service_and_maintanance_plansArrayList=appResponse.getResponseModel().getServicePackList();
                                adapter_service_and_maintanance_plan = new Adapter_Service_and_Maintanance_Plan(getContext(), pojo_service_and_maintanance_plansArrayList);
                                LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                                rv_service_pac_list.setLayoutManager(linearLayoutManager1);
                                rv_service_pac_list.setAdapter(adapter_service_and_maintanance_plan);

                                pojo_combo_plansArrayList= new ArrayList<>();
                                pojo_combo_plansArrayList=appResponse.getResponseModel().getComboPackList();
                                adapter_combo_plans = new Adapter_Combo_Plans(getContext(), pojo_combo_plansArrayList);
                                LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                                rv_combo_pac_list.setLayoutManager(linearLayoutManager3);
                                rv_combo_pac_list.setAdapter(adapter_combo_plans);

                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter_combo_plans.notifyDataSetChanged();
                                        adapter_service_and_maintanance_plan.notifyDataSetChanged();
                                        adapter_plans_1.notifyDataSetChanged();
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

}







