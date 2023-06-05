package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.wisedrive.customerapp.commonclasses.SPHelper;

public class EnterCarDetails extends AppCompatActivity {

    RelativeLayout rl3,rl_get_carinfo,rl_back,rl_mycars,rl_enter_car_no,rl2;
    public Fragment fragment=null;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_car_details);
       getWindow().setStatusBarColor(getColor(R.color.new_app_bg));
        rl_back=findViewById(R.id.rl_back);
        rl3=findViewById(R.id.rl3);
        rl_get_carinfo=findViewById(R.id.rl_get_carinfo);
        rl_mycars=findViewById(R.id.rl_mycars);
        rl2=findViewById(R.id.rl2);
        rl_enter_car_no=findViewById(R.id.rl_enter_car_no);

        if(SPHelper.show_add_car.equalsIgnoreCase("n")){
            rl2.setVisibility(View.GONE);
            rl_get_carinfo.setVisibility(View.GONE);
            rl_enter_car_no.setVisibility(View.GONE);
        }else {
            rl2.setVisibility(View.VISIBLE);
            rl_get_carinfo.setVisibility(View.VISIBLE);
            rl_enter_car_no.setVisibility(View.VISIBLE);
        }
        rl_mycars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EnterCarDetails.this,HelpCentre.class);
                startActivity(intent);
            }
        });
        rl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                AddYourCar popup = new AddYourCar();
                popup.show(getSupportFragmentManager(),"");
            }
        });

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EnterCarDetails.this,CustomerHomepage.class);
                startActivity(intent);
            }
        });
        rl_get_carinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EnterCarDetails.this,MoreCardata.class);
                startActivity(intent);
                finish();
            }
        });
    }


    @Override
    public void onBackPressed() {
        Intent intent=new Intent(EnterCarDetails.this,CustomerHomepage.class);
        startActivity(intent);
    }
}