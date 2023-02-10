package com.wisedrive.customerapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wisedrive.customerapp.adapters.Adapter_Combo_Plans;
import com.wisedrive.customerapp.adapters.Adapter_Comprehensive_service;
import com.wisedrive.customerapp.adapters.Adapter_Service_and_Maintanance_Plan;
import com.wisedrive.customerapp.adapters.Adapter_Exteneded_Warranty_Plan;
import com.wisedrive.customerapp.pojos.Pojo_Combo_Plans;
import com.wisedrive.customerapp.pojos.Pojo_Service_and_Maintanance_Plans;
import com.wisedrive.customerapp.pojos.Pojo_Extended_Warranty_Plan;
import com.wisedrive.customerapp.pojos.Pojo_Comprehensive_Plans;

import java.util.ArrayList;


public class Plans_Fragments extends Fragment {

   private RecyclerView recycler_view_warranty_plan;
    Adapter_Exteneded_Warranty_Plan adapter_plans_1;
    ArrayList<Pojo_Extended_Warranty_Plan> pojo_extended_warranty_planArrayList;
    Context context;
    View view;

   private RecyclerView recycler_service_maintanance;
    Adapter_Service_and_Maintanance_Plan adapter_service_and_maintanance_plan;
    ArrayList<Pojo_Service_and_Maintanance_Plans> pojo_service_and_maintanance_plansArrayList;

    private RecyclerView recycle_combo_plan;
    Adapter_Combo_Plans adapter_combo_plans;
    ArrayList<Pojo_Combo_Plans> pojo_combo_plansArrayList;

    private RecyclerView rexyclerView3;
    Adapter_Comprehensive_service adapter_comprehensive_service;
    ArrayList<Pojo_Comprehensive_Plans>pojo_comprehensive_plansArrayList;
    public Plans_Fragments(){

    }


    public static Fragment newInstance() {
        return null;
    }

    @SuppressLint("MissingInflatedId")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragments_plans, container, false);






        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        RecyclerView recycler_view_warranty_plan = (RecyclerView) view.findViewById(R.id.recycler_view_warranty_plan);
        recycler_view_warranty_plan.setHasFixedSize(true);

        RecyclerView recycler_service_maintanance = (RecyclerView) view.findViewById(R.id.recycler_service_maintanance);
        recycler_service_maintanance.setHasFixedSize(true);

         @SuppressLint({"MissingInflatedId", "LocalSuppress"})
         RecyclerView recyclerView3=(RecyclerView) view.findViewById(R.id.recycler_v_addon);
         recyclerView3.setHasFixedSize(true);

        pojo_extended_warranty_planArrayList = new ArrayList<>();
        pojo_extended_warranty_planArrayList.add(new Pojo_Extended_Warranty_Plan("Extended Warranty & Buyback Guarantee"," 25,000"," 20000"));
        pojo_extended_warranty_planArrayList.add(new Pojo_Extended_Warranty_Plan("Extended Warranty & Buyback Guarantee","25,000"," 20000"));
        pojo_extended_warranty_planArrayList.add(new Pojo_Extended_Warranty_Plan("Extended Warranty & Buyback Guarantee"," 15,000"," 20000"));
        pojo_extended_warranty_planArrayList.add(new Pojo_Extended_Warranty_Plan("Extended Warranty & Buyback Guarantee","15,000","30000"));
        pojo_extended_warranty_planArrayList.add(new Pojo_Extended_Warranty_Plan("Extended Warranty & Buyback Guarantee","15,000","50000"));
        adapter_plans_1 = new Adapter_Exteneded_Warranty_Plan(getContext(),  pojo_extended_warranty_planArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recycler_view_warranty_plan.setLayoutManager(linearLayoutManager);
        recycler_view_warranty_plan.setAdapter(adapter_plans_1);

        pojo_service_and_maintanance_plansArrayList= new ArrayList<>();
        pojo_service_and_maintanance_plansArrayList.add(new Pojo_Service_and_Maintanance_Plans("General Service","General Service Standard",
                "Ac Service","Ac Service Standard","Health Check up","Health Checkup Description"," Service & Maintanance Plans","30,000","1200",R.drawable.confirmation_1,R.drawable.image_2,R.drawable.confirmation_3));
        pojo_service_and_maintanance_plansArrayList.add(new Pojo_Service_and_Maintanance_Plans("General Service","General Service Standard",
                "Ac Service","Ac Service Standard","Health Check up","Health Checkup Description","Service & Maintanance Plans","15,000","1200",R.drawable.confirmation_1,R.drawable.service_image_logo,R.drawable.service_image_logo));
        pojo_service_and_maintanance_plansArrayList.add(new Pojo_Service_and_Maintanance_Plans("General Service","General Service Standard",
                "Ac Service","Ac Service Standard","Health Check up","Health Checkup Description","Service & Maintanance Plans","3,000","1300",R.drawable.confirmation_1,R.drawable.service_image_logo,R.drawable.service_image_logo));
        adapter_service_and_maintanance_plan = new Adapter_Service_and_Maintanance_Plan(getContext(), pojo_service_and_maintanance_plansArrayList);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recycler_service_maintanance.setLayoutManager(linearLayoutManager1);
        recycler_service_maintanance.setAdapter(adapter_service_and_maintanance_plan);

        pojo_comprehensive_plansArrayList = new ArrayList<>();
        pojo_comprehensive_plansArrayList.add(new Pojo_Comprehensive_Plans("Extended Warranty","Service & Maintanance","10000","68%"));
        pojo_comprehensive_plansArrayList.add(new Pojo_Comprehensive_Plans("Extended Warranty","Service & Maintanance","10000","68%"));
        pojo_comprehensive_plansArrayList.add(new Pojo_Comprehensive_Plans("Extended Warranty","Service & Maintanance","10000","68%"));
        pojo_comprehensive_plansArrayList.add(new Pojo_Comprehensive_Plans("Extended Warranty","Service & Maintanance","10000","68%"));
        pojo_comprehensive_plansArrayList.add(new Pojo_Comprehensive_Plans("Extended Warranty","Service & Maintanance","10000","68%"));
        pojo_comprehensive_plansArrayList.add(new Pojo_Comprehensive_Plans("Extended Warranty","Service & Maintanance","10000","68%"));
        adapter_comprehensive_service = new Adapter_Comprehensive_service(getContext(), pojo_comprehensive_plansArrayList);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView3.setLayoutManager(linearLayoutManager2);
        recyclerView3.setAdapter(adapter_comprehensive_service);

        RecyclerView recycle_combo_plan  = (RecyclerView) view.findViewById(R.id.recycle_combo_plan);
        recycle_combo_plan.setHasFixedSize(true);

        pojo_combo_plansArrayList= new ArrayList<>();
        pojo_combo_plansArrayList.add(new Pojo_Combo_Plans("All-in-One Super Saver Plan","INR 21,000","Comprehensive Warranty","Buy back Guarantee","Service & Maintanance","At OEM","Roadside Assistance","Upto 50 kms around city","12,000","1000",R.drawable.service_image,R.drawable.service_image,R.drawable.service_image));
        pojo_combo_plansArrayList.add(new Pojo_Combo_Plans("All-in-One Super Saver Plan","INR 21,000","Comprehensive Warranty","Buy back Guarantee","Service & Maintanance","At OEM","Roadside Assistance","Upto 50 kms around city","12,000","5000",R.drawable.service_image,R.drawable.service_image,R.drawable.service_image));
        pojo_combo_plansArrayList.add(new Pojo_Combo_Plans("All-in-One Super Saver Plan","INR 21,000","Comprehensive Warranty","Buy back Guarantee","Service & Maintanance","At OEM","Roadside Assistance","Upto 50 kms around city","12,000","15000",R.drawable.service_image,R.drawable.service_image,R.drawable.service_image));
        pojo_combo_plansArrayList.add(new Pojo_Combo_Plans("All-in-One Super Saver Plan","INR 21,000","Comprehensive Warranty","Buy back Guarantee","Service & Maintanance","At OEM","Roadside Assistance","Upto 50 kms around city","12,000","20000",R.drawable.service_image,R.drawable.service_image,R.drawable.service_image));
        pojo_combo_plansArrayList.add(new Pojo_Combo_Plans("All-in-One Super Saver Plan","INR 21,000","Comprehensive Warranty","Buy back Guarantee","Service & Maintanance","At OEM","Roadside Assistance","Upto 50 kms around city","12,000","30000",R.drawable.service_image,R.drawable.service_image,R.drawable.service_image));
        adapter_combo_plans = new Adapter_Combo_Plans(getContext(), pojo_combo_plansArrayList);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recycle_combo_plan.setLayoutManager(linearLayoutManager3);
        recycle_combo_plan.setAdapter(adapter_combo_plans);








        return view;





    }



}







