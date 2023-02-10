package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.wisedrive.customerapp.adapters.Adapter_Addons_list;
import com.wisedrive.customerapp.pojos.Pojo_Class_Addons_List;

import java.util.ArrayList;

public class Addons extends AppCompatActivity {
     RecyclerView rv_addons_plan;
    Adapter_Addons_list adapter_addons_list;
    ArrayList<Pojo_Class_Addons_List> pojo_class_addons_listArrayList;
    Context context;
    View view;
    AppCompatButton pay_button;
    RelativeLayout rl_back_button;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addons);

        rv_addons_plan= findViewById(R.id.rv_addons_plan);

        pojo_class_addons_listArrayList = new ArrayList<>();
        pojo_class_addons_listArrayList.add(new Pojo_Class_Addons_List("Recommended","Select","Comprehensive Warranty","Shafts,Gears,Brake/Clutch Bands,Oil Pump, Bearings, Bushes, Valves, DrivesPlate ,Transfer Gear...","INR 10,000","10,000","30%","1"));
        pojo_class_addons_listArrayList.add(new Pojo_Class_Addons_List("New","Select","Comprehensive Warranty","Shafts,Gears,Brake/Clutch Bands,Oil Pump, Bearings, Bushes, Valves, DrivesPlate ,Transfer Gear...","INR 10,000","10,000","30%","2"));
        pojo_class_addons_listArrayList.add(new Pojo_Class_Addons_List("Recommended","Select","Comprehensive Warranty","Shafts,Gears,Brake/Clutch Bands,Oil Pump, Bearings, Bushes, Valves, DrivesPlate ,Transfer Gear...","INR 10,000","10,000","30%","3"));
        pojo_class_addons_listArrayList.add(new Pojo_Class_Addons_List("New","Select","Comprehensive Warranty","Shafts,Gears,Brake/Clutch Bands,Oil Pump,Bearings, Bushes, Valves, DrivesPlate ,Transfer Gear...","INR 10,000","10,000","30%","4"));
        pojo_class_addons_listArrayList.add(new Pojo_Class_Addons_List("Recommended","Select","Comprehensive Warranty","Shafts,Gears,Brake/Clutch Bands,Oil Pump,Bearings, Bushes, Valves, DrivesPlate ,Transfer Gear...","INR 10,000","10,000","30%","5"));
        pojo_class_addons_listArrayList.add(new Pojo_Class_Addons_List("Recommended","Select","Comprehensive Warranty","Shafts,Gears,Brake/Clutch Bands,Oil Pump,Bearings, Bushes, Valves, DrivesPlate ,Transfer Gear...","INR 10,000","10,000","30%","6"));
        pojo_class_addons_listArrayList.add(new Pojo_Class_Addons_List("Recommended","Select","Comprehensive Warranty","Shafts,Gears,Brake/Clutch Bands,Oil Pump, Bearings, Bushes, Valves,DrivesPlate ,Transfer Gear...","INR 10,000","10,000","30%","6"));
        adapter_addons_list = new Adapter_Addons_list(this, pojo_class_addons_listArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Addons.this, LinearLayoutManager.VERTICAL, false);
        rv_addons_plan.setLayoutManager(linearLayoutManager);
        rv_addons_plan.setAdapter(adapter_addons_list);

        pay_button=findViewById(R.id.pay_button);
        pay_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Addons.this,Confirmation_Activity.class);
                startActivity(intent);
            }
        });

        rl_back_button=findViewById(R.id.rl_back_button);
        rl_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Addons.this, Warranty_Description.class);
                startActivity(intent);
            }
        });



    }
}