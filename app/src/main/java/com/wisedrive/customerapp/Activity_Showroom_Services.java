package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.wisedrive.customerapp.adapters.Adapter_Addons_list;
import com.wisedrive.customerapp.adapters.Adapter_Select_Date;
import com.wisedrive.customerapp.adapters.Adapter_Showroom_Services;
import com.wisedrive.customerapp.adapters.Adapter_Tracking_page;
import com.wisedrive.customerapp.pojos.Pojo_Class_Addons_List;
import com.wisedrive.customerapp.pojos.Pojo_Select_Date;
import com.wisedrive.customerapp.pojos.Pojo_Showroom_services;
import com.wisedrive.customerapp.pojos.Pojo_Tracking_page;

import java.util.ArrayList;

public class Activity_Showroom_Services extends AppCompatActivity {
   public RelativeLayout rl_transperant, rl_transperant_date_page,rl_select_date,rl_transperant_select_status_page,rl_track_service_status;
    RecyclerView rv_showroom_services;
    Adapter_Showroom_Services adapter_showroom_services;
    ArrayList<Pojo_Showroom_services>pojo_showroom_servicesArrayList;
    Context context;
    View view;

    RecyclerView rv_select_date;
    Adapter_Select_Date adapter_select_date;
    ArrayList<Pojo_Select_Date>pojo_select_dateArrayList;

   RecyclerView rv_select_service_status;
   Adapter_Tracking_page adapter_tracking_page;
   ArrayList<Pojo_Tracking_page>pojo_tracking_pageArrayList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showroom_services);

        rv_showroom_services=findViewById(R.id.rv_showroom_services);
        pojo_showroom_servicesArrayList = new ArrayList<>();
        pojo_showroom_servicesArrayList.add(new Pojo_Showroom_services("General Service","OEM Authorized Service Center","","","BOOK NOW",R.drawable.service_image,"1"));
        pojo_showroom_servicesArrayList.add(new Pojo_Showroom_services("General Service","OEM Authorized Service Center","Status","inprogress","Track SERVICE",R.drawable.service_image,"2"));
        pojo_showroom_servicesArrayList.add(new Pojo_Showroom_services("General Service","OEM Authorized Service Center","Status","completede","Track SERVICE",R.drawable.service_image,"2"));
        pojo_showroom_servicesArrayList.add(new Pojo_Showroom_services("General Service","OEM Authorized Service Center","Status","Delivered","Track SERVICE",R.drawable.service_image,"2"));
        pojo_showroom_servicesArrayList.add(new Pojo_Showroom_services("General Service","OEM Authorized Service Center","Status","inprogress","Track SERVICE",R.drawable.service_image,"2"));
        pojo_showroom_servicesArrayList.add(new Pojo_Showroom_services("General Service","OEM Authorized Service Center","Status","inprogress","Track SERVICE",R.drawable.service_image,"2"));
        pojo_showroom_servicesArrayList.add(new Pojo_Showroom_services("General Service","OEM Authorized Service Center","Status","inprogress","Track SERVICE",R.drawable.service_image,"2"));
        adapter_showroom_services = new Adapter_Showroom_Services(this, pojo_showroom_servicesArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Activity_Showroom_Services.this, LinearLayoutManager.VERTICAL, false);
        rv_showroom_services.setLayoutManager(linearLayoutManager);
        rv_showroom_services.setAdapter(adapter_showroom_services);

        rv_select_date=findViewById(R.id.rv_select_date);
        pojo_select_dateArrayList = new ArrayList<>();
        pojo_select_dateArrayList.add(new Pojo_Select_Date("04 Feb,Mon"));
        pojo_select_dateArrayList.add(new Pojo_Select_Date("04 Feb,Mon"));
        pojo_select_dateArrayList.add(new Pojo_Select_Date("04 Feb,Mon"));
        pojo_select_dateArrayList.add(new Pojo_Select_Date("04 Feb,Mon"));
        pojo_select_dateArrayList.add(new Pojo_Select_Date("04 Feb,Mon"));
        pojo_select_dateArrayList.add(new Pojo_Select_Date("04 Feb,Mon"));
        pojo_select_dateArrayList.add(new Pojo_Select_Date("04 Feb,Mon"));
        pojo_select_dateArrayList.add(new Pojo_Select_Date("04 Feb,Mon"));
        pojo_select_dateArrayList.add(new Pojo_Select_Date("04 Feb,Mon"));
        pojo_select_dateArrayList.add(new Pojo_Select_Date("04 Feb,Mon"));
        pojo_select_dateArrayList.add(new Pojo_Select_Date("04 Feb,Mon"));
        pojo_select_dateArrayList.add(new Pojo_Select_Date("04 Feb,Mon"));
        pojo_select_dateArrayList.add(new Pojo_Select_Date("04 Feb,Mon"));
        pojo_select_dateArrayList.add(new Pojo_Select_Date("04 Feb,Mon"));
        adapter_select_date = new Adapter_Select_Date(this,pojo_select_dateArrayList);
        GridLayoutManager linearLayoutManager1 = new GridLayoutManager(this, 3);
      //  linearLayoutManager1.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
        rv_select_date.setLayoutManager(linearLayoutManager1);
         rv_select_date.setAdapter(adapter_select_date);

        rl_transperant=findViewById(R.id.rl_transperant);
        rl_select_date=findViewById(R.id.rl_select_date);
        rl_transperant_date_page=findViewById(R.id.rl_transperant_date_page);
        rl_transperant_date_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_transperant_date_page.setEnabled(true);
                rl_select_date.setVisibility(View.INVISIBLE);
                rl_transperant.setVisibility(View.INVISIBLE);
            }
        });

        rv_select_service_status=findViewById(R.id.rv_select_service_status);
        pojo_tracking_pageArrayList= new ArrayList<>();
        pojo_tracking_pageArrayList.add(new Pojo_Tracking_page(R.drawable.request,"Request","Booking request recieved","12 Feb,Wed","1",R.color.green,""));
        pojo_tracking_pageArrayList.add(new Pojo_Tracking_page(R.drawable.schedule,"Scheduled","Service is sheduled with showroom","12 Feb,Wed","2",R.color.green,""));
        pojo_tracking_pageArrayList.add(new Pojo_Tracking_page(R.drawable.check_with_circle_orange,"Inprogress","Service inprogress","12 Feb,Wed","3",R.color.orange,""));
        pojo_tracking_pageArrayList.add(new Pojo_Tracking_page(R.drawable.completed,"Completed","Service has been completed","","4",R.color.middle_gray,""));
        pojo_tracking_pageArrayList.add(new Pojo_Tracking_page(R.drawable.otp,"OTP","provide OTP to deliver the car","","4",R.color.middle_gray,"12345"));
        pojo_tracking_pageArrayList.add(new Pojo_Tracking_page(R.drawable.delivered,"Delivered","Your car has been delivered","","4",R.color.middle_gray,""));
        adapter_tracking_page = new Adapter_Tracking_page(this,pojo_tracking_pageArrayList);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(Activity_Showroom_Services.this, LinearLayoutManager.VERTICAL, false);
        rv_select_service_status.setLayoutManager(linearLayoutManager2);
        rv_select_service_status.setAdapter(adapter_tracking_page);

        rl_transperant_select_status_page=findViewById(R.id.rl_transperant_select_status_page);
        rl_track_service_status=findViewById(R.id.rl_track_service_status);
        rl_transperant_select_status_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_transperant_select_status_page.setEnabled(true);
                rl_track_service_status.setVisibility(View.INVISIBLE);
                rl_transperant.setVisibility(View.INVISIBLE);
            }
        });


    }
}