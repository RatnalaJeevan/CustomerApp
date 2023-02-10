package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.wisedrive.customerapp.adapters.Adapter_Q_And_A;
import com.wisedrive.customerapp.adapters.Adapter_Select_Your_Vehicle_No;
import com.wisedrive.customerapp.pojos.Pojo_Q_And_A;
import com.wisedrive.customerapp.pojos.Pojo_Select_Your_Vehicle_no;

import java.util.ArrayList;

public class Activity_Q_And_A extends AppCompatActivity {
    RecyclerView rv_q_and_a;
    Adapter_Q_And_A adapter_q_and_a;
    ArrayList<Pojo_Q_And_A> pojo_q_and_aArrayList;
    Context context;
    View view;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qand);

        rv_q_and_a=findViewById(R.id.rv_q_and_a);

        pojo_q_and_aArrayList = new ArrayList<>();
        pojo_q_and_aArrayList.add(new Pojo_Q_And_A("When is the last time ,General Service is done?Is it done with wisedrive or elsewhere?","You may select more than one option"));
        pojo_q_and_aArrayList.add(new Pojo_Q_And_A("When is the last time ,General Service is done?Is it done with wisedrive or elsewhere?","You may select more than one option"));
        pojo_q_and_aArrayList.add(new Pojo_Q_And_A("When is the last time ,General Service is done?Is it done with wisedrive or elsewhere?","You may select more than one option"));
        pojo_q_and_aArrayList.add(new Pojo_Q_And_A("When is the last time ,General Service is done?Is it done with wisedrive or elsewhere?","You may select more than one option"));
        pojo_q_and_aArrayList.add(new Pojo_Q_And_A("When is the last time ,General Service is done?Is it done with wisedrive or elsewhere?","You may select more than one option"));
        pojo_q_and_aArrayList.add(new Pojo_Q_And_A("When is the last time ,General Service is done?Is it done with wisedrive or elsewhere?","You may select more than one option"));
        adapter_q_and_a = new Adapter_Q_And_A(this, pojo_q_and_aArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Activity_Q_And_A.this, LinearLayoutManager.VERTICAL, false);
        rv_q_and_a.setLayoutManager(linearLayoutManager);
        rv_q_and_a.setAdapter(adapter_q_and_a);


    }
}