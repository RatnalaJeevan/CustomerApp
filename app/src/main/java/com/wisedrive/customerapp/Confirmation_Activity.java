package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.wisedrive.customerapp.adapters.Adapter_Addons_list;
import com.wisedrive.customerapp.adapters.Adapter_Service_Includes;
import com.wisedrive.customerapp.adapters.Adapter_Service_List;
import com.wisedrive.customerapp.pojos.Pojo_Class_Addons_List;
import com.wisedrive.customerapp.pojos.Pojo_Service_Includes;

import java.util.ArrayList;

public class Confirmation_Activity extends AppCompatActivity {
    RecyclerView rv_service_incluedes_list;
    Adapter_Service_Includes adapter_service_includes;
    ArrayList<Pojo_Service_Includes>pojo_service_includesArrayList;
    Context context;
    View view;
    RelativeLayout request_inspection;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        rv_service_incluedes_list=findViewById(R.id.rv_service_incluedes_list);

        pojo_service_includesArrayList= new ArrayList<>();
        pojo_service_includesArrayList.add(new Pojo_Service_Includes("Comprehensive Warranty","with Buyback Guarantee",R.drawable.repair));
        pojo_service_includesArrayList.add(new Pojo_Service_Includes("Comprehensive Warranty","with Buyback Guarantee",R.drawable.repair));
        pojo_service_includesArrayList.add(new Pojo_Service_Includes("Comprehensive Warranty","with Buyback Guarantee",R.drawable.repair));
        adapter_service_includes = new Adapter_Service_Includes(this, pojo_service_includesArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Confirmation_Activity.this, LinearLayoutManager.VERTICAL, false);
        rv_service_incluedes_list.setLayoutManager(linearLayoutManager);
        rv_service_incluedes_list.setAdapter(adapter_service_includes);

        request_inspection=findViewById(R.id.request_inspection);
        request_inspection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Confirmation_Activity.this,Request_Inspection.class);
                startActivity(intent);
            }
        });

    }
}