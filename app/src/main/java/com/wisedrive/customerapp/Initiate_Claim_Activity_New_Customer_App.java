package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wisedrive.customerapp.adapters.Adapter_Claim_Type_New_Cus_App;
import com.wisedrive.customerapp.adapters.Adapter_Initiate_Claims_Photos;
import com.wisedrive.customerapp.adapters.Adapter_Select_Your_Vehicle_No;
import com.wisedrive.customerapp.pojos.Pojo_Claim_Type_New_Cus_App;
import com.wisedrive.customerapp.pojos.Pojo_Select_Your_Vehicle_no;
import com.wisedrive.customerapp.pojos.Pojo_initiate_Claims_Photos;

import java.util.ArrayList;
import java.util.Calendar;

public class Initiate_Claim_Activity_New_Customer_App extends AppCompatActivity {
    RelativeLayout rl_symptoms_of_issue;
    RecyclerView rv_select_vehicle_no;
    Adapter_Select_Your_Vehicle_No adapter_select_your_vehicle_no;
    ArrayList<Pojo_Select_Your_Vehicle_no> pojo_select_your_vehicle_noArrayList;
    Context context;
    View view;
    TextView tv_calender;
    DatePickerDialog picker;

    RecyclerView rv_select_claim_type;
    Adapter_Claim_Type_New_Cus_App adapter_claim_type;
    ArrayList<Pojo_Claim_Type_New_Cus_App> pojo_claim_typeArrayList;


    RecyclerView rv_photos;
    Adapter_Initiate_Claims_Photos adapter_initiate_claims_photos;
    ArrayList<Pojo_initiate_Claims_Photos> pojo_initiate_claims_photosArrayList;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initiate_claim_new_customer_app);


        rv_select_vehicle_no=findViewById(R.id.rv_select_vehicle_no);
        pojo_select_your_vehicle_noArrayList = new ArrayList<>();
        pojo_select_your_vehicle_noArrayList.add(new Pojo_Select_Your_Vehicle_no("KA01234567"));
        pojo_select_your_vehicle_noArrayList.add(new Pojo_Select_Your_Vehicle_no("KA01234567"));
        pojo_select_your_vehicle_noArrayList.add(new Pojo_Select_Your_Vehicle_no("KA01234567"));
        pojo_select_your_vehicle_noArrayList.add(new Pojo_Select_Your_Vehicle_no("KA01234567"));
        pojo_select_your_vehicle_noArrayList.add(new Pojo_Select_Your_Vehicle_no("KA01234567"));
        adapter_select_your_vehicle_no = new Adapter_Select_Your_Vehicle_No(this, pojo_select_your_vehicle_noArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Initiate_Claim_Activity_New_Customer_App.this, LinearLayoutManager.HORIZONTAL, false);
        rv_select_vehicle_no.setLayoutManager(linearLayoutManager);
        rv_select_vehicle_no.setAdapter(adapter_select_your_vehicle_no);

        rv_select_claim_type=findViewById(R.id.rv_select_claim_type);
        pojo_claim_typeArrayList = new ArrayList<>();
        pojo_claim_typeArrayList.add(new Pojo_Claim_Type_New_Cus_App("Engine"));
        pojo_claim_typeArrayList.add(new Pojo_Claim_Type_New_Cus_App("Transmission"));
        pojo_claim_typeArrayList.add(new Pojo_Claim_Type_New_Cus_App("Engine & Transmission"));
        pojo_claim_typeArrayList.add(new Pojo_Claim_Type_New_Cus_App("Battery"));
        adapter_claim_type = new Adapter_Claim_Type_New_Cus_App(this,pojo_claim_typeArrayList);
        GridLayoutManager linearLayoutManager1 = new GridLayoutManager(this, 2);
        rv_select_claim_type.setLayoutManager(linearLayoutManager1);
        rv_select_claim_type.setAdapter(adapter_claim_type);

        rv_photos=findViewById(R.id.rv_photos);
        pojo_initiate_claims_photosArrayList = new ArrayList<>();
        pojo_initiate_claims_photosArrayList.add(new Pojo_initiate_Claims_Photos("Front side Car Number", R.drawable.upload_photos_image));
        pojo_initiate_claims_photosArrayList.add(new Pojo_initiate_Claims_Photos("Front side Car Number", R.drawable.upload_photos_image));
        pojo_initiate_claims_photosArrayList.add(new Pojo_initiate_Claims_Photos("Front side Car Number", R.drawable.upload_photos_image));
        pojo_initiate_claims_photosArrayList.add(new Pojo_initiate_Claims_Photos("Front side Car Number", R.drawable.upload_photos_image));
        pojo_initiate_claims_photosArrayList.add(new Pojo_initiate_Claims_Photos("Front side Car Number", R.drawable.upload_photos_image));
        pojo_initiate_claims_photosArrayList.add(new Pojo_initiate_Claims_Photos("Front side Car Number", R.drawable.upload_photos_image));
        adapter_initiate_claims_photos = new  Adapter_Initiate_Claims_Photos(this, pojo_initiate_claims_photosArrayList);
        GridLayoutManager linearLayoutManager2 = new GridLayoutManager(this, 3);
        rv_photos.setLayoutManager(linearLayoutManager2);
        rv_photos.setAdapter(adapter_initiate_claims_photos);


        rl_symptoms_of_issue=findViewById(R.id.rl_symptoms_of_issue);
        rl_symptoms_of_issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Initiate_Claim_Activity_New_Customer_App.this,Activity_Q_And_A.class);
                startActivity(intent);
            }
        });

        tv_calender=findViewById(R.id.tv_calender);
        tv_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                picker = new DatePickerDialog(Initiate_Claim_Activity_New_Customer_App.this,
                        new DatePickerDialog.OnDateSetListener() {


                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                tv_calender.setText(String.valueOf(dayOfMonth) + "/" + (monthOfYear + 1) + "/" + year);
                                Toast.makeText(Initiate_Claim_Activity_New_Customer_App.this, "Date Added Successfully", Toast.LENGTH_LONG).show();


                            }
                        }, mYear, mMonth, mDay);
                picker.show();

            }
        });
            }







    }
