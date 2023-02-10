package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.wisedrive.customerapp.adapters.Adapter_Activate_Confirmation_Page;
import com.wisedrive.customerapp.adapters.Adapter_Addons_list;
import com.wisedrive.customerapp.pojos.Pojo_Activate_Confirmation_Page;
import com.wisedrive.customerapp.pojos.Pojo_Class_Addons_List;

import java.util.ArrayList;

public class Activation_Confirmation_Activity extends AppCompatActivity {
    RecyclerView rv_warranty;
    Adapter_Activate_Confirmation_Page adapter_activate_confirmation_page;
    ArrayList<Pojo_Activate_Confirmation_Page> pojo_activate_confirmation_pageArrayList;
    Context context;
    View view;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activation_confirmation);

        rv_warranty=findViewById(R.id.rv_warranty);

        pojo_activate_confirmation_pageArrayList = new ArrayList<>();
        pojo_activate_confirmation_pageArrayList.add(new Pojo_Activate_Confirmation_Page(R.drawable.confirmation_11,"Comprehensive Warranty with Buyback Guarantee","04 Feb,2023"));
        pojo_activate_confirmation_pageArrayList.add(new Pojo_Activate_Confirmation_Page(R.drawable.confirmation_1,"Service & Maintanance at OEM Service Center","04 Feb,2023"));
        pojo_activate_confirmation_pageArrayList.add(new Pojo_Activate_Confirmation_Page(R.drawable.confirmation_11,"Battery Warranty,AC Compressor Warranty,turbo Warranty","04 Feb,2023"));
        adapter_activate_confirmation_page = new  Adapter_Activate_Confirmation_Page(this,pojo_activate_confirmation_pageArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Activation_Confirmation_Activity.this, LinearLayoutManager.VERTICAL, false);
        rv_warranty.setLayoutManager(linearLayoutManager);
        rv_warranty.setAdapter(adapter_activate_confirmation_page);



    }
}