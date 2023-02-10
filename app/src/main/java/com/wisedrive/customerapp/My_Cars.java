package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.wisedrive.customerapp.adapters.Adapter_Class_My_Car_page;
import com.wisedrive.customerapp.adapters.Adapter_class_mycar;
import com.wisedrive.customerapp.pojos.Pojo_Class_Mycar;

import java.util.ArrayList;

public class My_Cars extends AppCompatActivity {
    private RecyclerView recycler_mycars;
    Adapter_Class_My_Car_page adapter_class_my_car_page;
    ArrayList<Pojo_Class_Mycar> pojo_class_mycarArrayList;
    Context context;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cars);

        recycler_mycars=findViewById(R.id.recycler_mycars);

        pojo_class_mycarArrayList = new ArrayList<>();
        pojo_class_mycarArrayList.add(new Pojo_Class_Mycar("DATSUN-","Cross","4000kms","Manual","Diesel",R.drawable.blue_car_image,R.drawable.kms,R.drawable.transmission,R.drawable.fuel_type));
        pojo_class_mycarArrayList.add(new Pojo_Class_Mycar("DATSUN-","Cross","4000kms","Manual","Petrol",R.drawable.blue_car_image,R.drawable.kms,R.drawable.transmission,R.drawable.fuel_type));
        pojo_class_mycarArrayList.add(new Pojo_Class_Mycar("DATSUN-","Cross","4000kms","Manual","Diesel",R.drawable.blue_car_image,R.drawable.kms,R.drawable.transmission,R.drawable.fuel_type));
        pojo_class_mycarArrayList.add(new Pojo_Class_Mycar("DATSUN-","Cross","4000kms","Manual","Petrol",R.drawable.blue_car_image,R.drawable.kms,R.drawable.transmission,R.drawable.fuel_type));
        pojo_class_mycarArrayList.add(new Pojo_Class_Mycar("DATSUN-","Cross","4000kms","Manual","Petrol",R.drawable.blue_car_image,R.drawable.kms,R.drawable.transmission,R.drawable.fuel_type));
        adapter_class_my_car_page= new Adapter_Class_My_Car_page(My_Cars.this, pojo_class_mycarArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycler_mycars.setLayoutManager(linearLayoutManager);
        recycler_mycars.setAdapter(adapter_class_my_car_page);

    }
}