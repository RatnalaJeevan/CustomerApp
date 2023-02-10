package com.wisedrive.customerapp;

import static java.lang.String.valueOf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.wisedrive.customerapp.adapters.Adapter_Additional_services;
import com.wisedrive.customerapp.adapters.Adapter_Service_List;
import com.wisedrive.customerapp.adapters.Adapter_class_mycar;
import com.wisedrive.customerapp.pojos.Pojo_Additional_Services;
import com.wisedrive.customerapp.pojos.Pojo_Class_Mycar;
import com.wisedrive.customerapp.pojos.Pojo_Service_list;

import java.util.ArrayList;

public class Warranty_Description extends AppCompatActivity {
    AppCompatButton pay_button;
    public static RelativeLayout relative_layout_mycar;
    private RecyclerView recyclerView;
    Adapter_Service_List adapter_service_list;
    ArrayList<Pojo_Service_list> pojo_service_listArrayList;
    Context context;
    View view;
    ScrollView scroll_view;
    RelativeLayout rl_back_button,rl_six_month_validity,rl_one_year_validity;
    TextView tv_six_month,tv_1y;


    RecyclerView recycler_view_mycars;
    ArrayList<Pojo_Class_Mycar> pojo_class_mycarArrayList;
    Adapter_class_mycar adapter_class_mycar;

    RecyclerView rv_additional_plan;
    Adapter_Additional_services adapter_additional_services;
    ArrayList<Pojo_Additional_Services>pojoAdditionalServicesArrayList;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.warranty_description);
        scroll_view=findViewById(R.id.scroll_view);
        scroll_view.fullScroll(View.FOCUS_DOWN);
        scroll_view.setSmoothScrollingEnabled(true);

        relative_layout_mycar = findViewById(R.id.relative_layout_mycar);
        rl_six_month_validity=findViewById(R.id.rl_six_month_validity);
        rl_one_year_validity=findViewById(R.id.rl_one_year_validity);
        tv_six_month=findViewById(R.id.tv_six_month);
        tv_1y=findViewById(R.id.tv_1y);

            recyclerView = findViewById(R.id.recycler_service_list);
            recyclerView.setHasFixedSize(true);
            rl_back_button = findViewById(R.id.rl_back_button);
            rl_back_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Warranty_Description.this, Customer_Home_page_bottom_navigation.class);
                    startActivity(intent);

                }
            });


            pojo_service_listArrayList = new ArrayList<>();
            pojo_service_listArrayList.add(new Pojo_Service_list("General Service Standard", "OEM Authorized Service Centre","Oil pump,Crank shaft &amp;Pulley ,big end &amp; main bearing ,Connecting rods,gudgeon pin,piston and rings,inlet & ehaust valves (excluding burnt and pitted valves),springs and guides,cylinder block and cylinder head,cam shaft,rocker arms and shaft,timing gears,tensioner bearing,water pump.(failure due to external damage or corrosion is not covered),inlet and exhaust manifold.",R.drawable.service_image));
            pojo_service_listArrayList.add(new Pojo_Service_list("AC Service", "OEM Authorized Service Centre","",R.drawable.service_image));
            pojo_service_listArrayList.add(new Pojo_Service_list("Health Checkup", "OEM Authorized Service Centre","",R.drawable.service_image));
            pojo_service_listArrayList.add(new Pojo_Service_list("Battery Jump Start", "OEM Authorized Service Centre","",R.drawable.service_image));
            pojo_service_listArrayList.add(new Pojo_Service_list("Punture Repair", "OEM Authorized Service Centre","",R.drawable.service_image));
            pojo_service_listArrayList.add(new Pojo_Service_list("Road Side Assistance", "OEM Authorized Service Centre","",R.drawable.service_image));
            adapter_service_list = new Adapter_Service_List(this, pojo_service_listArrayList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Warranty_Description.this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapter_service_list);

            recycler_view_mycars = findViewById(R.id.recycler_view_mycars);
            recycler_view_mycars.setHasFixedSize(true);


            pojo_class_mycarArrayList = new ArrayList<>();
            pojo_class_mycarArrayList.add(new Pojo_Class_Mycar("DATSUN-", "Cross", "4000kms", "Manual", "Petrol", R.drawable.blue_car_image, R.drawable.kms_logo, R.drawable.transmission_type,R.drawable.fuel_type));
            pojo_class_mycarArrayList.add(new Pojo_Class_Mycar("DATSUN-", "Cross", "4000kms", "Manual", "Diesel", R.drawable.blue_car_image, R.drawable.kms_logo, R.drawable.transmission_type,R.drawable.fuel_type));
            pojo_class_mycarArrayList.add(new Pojo_Class_Mycar("DATSUN-", "Cross", "4000kms", "Manual", "Petrol", R.drawable.blue_car_image, R.drawable.kms_logo, R.drawable.transmission_type,R.drawable.fuel_type));
            pojo_class_mycarArrayList.add(new Pojo_Class_Mycar("DATSUN-", "Cross", "4000kms", "Manual", "Petrol", R.drawable.blue_car_image, R.drawable.kms_logo, R.drawable.transmission_type,R.drawable.fuel_type));
            pojo_class_mycarArrayList.add(new Pojo_Class_Mycar("DATSUN-", "Cross", "4000kms", "Manual", "Diesel" +
                    "", R.drawable.blue_car_image, R.drawable.kms_logo, R.drawable.transmission_type,R.drawable.fuel_type));
            adapter_class_mycar = new Adapter_class_mycar(this, pojo_class_mycarArrayList);
            LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(Warranty_Description.this, LinearLayoutManager.HORIZONTAL, false);
            recycler_view_mycars.setLayoutManager(linearLayoutManager2);
            recycler_view_mycars.setAdapter(adapter_class_mycar);


        rv_additional_plan = findViewById(R.id.rv_additional_plan);
        rv_additional_plan.setHasFixedSize(true);


        pojoAdditionalServicesArrayList = new ArrayList<>();
        pojoAdditionalServicesArrayList.add(new Pojo_Additional_Services(R.drawable.car_logo_bg,"Comprehensive Warranty"));
        pojoAdditionalServicesArrayList.add(new Pojo_Additional_Services(R.drawable.car_logo_bg,"Showroom Warranty"));
        pojoAdditionalServicesArrayList.add(new Pojo_Additional_Services(R.drawable.car_logo_bg,"Comprehensive Warranty"));
        adapter_additional_services = new Adapter_Additional_services(this, pojoAdditionalServicesArrayList);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(Warranty_Description.this, LinearLayoutManager.HORIZONTAL, false);
        rv_additional_plan.setLayoutManager(linearLayoutManager3);
        rv_additional_plan.setAdapter(adapter_additional_services);

        rl_six_month_validity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_six_month_validity.setBackgroundResource(R.drawable.circular_layout_1y);
                rl_one_year_validity.setBackgroundResource(R.drawable.circular_relative_layout_validity_1);
                tv_six_month.setTextColor(Color.parseColor("#ffffff"));
                tv_1y.setTextColor(Color.parseColor("#6A5FF4"));
            }
        });

        rl_one_year_validity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_six_month_validity.setBackgroundResource(R.drawable.circular_relative_layout_validity_1);
                rl_one_year_validity.setBackgroundResource(R.drawable.circular_layout_1y);
                tv_six_month.setTextColor(Color.parseColor("#6A5FF4"));
                tv_1y.setTextColor(Color.parseColor("#ffffff"));
            }
        });

        pay_button=findViewById(R.id.pay_button);
        pay_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Warranty_Description.this,Addons.class);
                startActivity(intent);
            }
        });

    }
    }







