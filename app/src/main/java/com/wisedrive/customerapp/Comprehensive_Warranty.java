package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class Comprehensive_Warranty extends AppCompatActivity {
    RelativeLayout rl_request_claim,rl_back;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comprehensive_warranty);
        rl_request_claim=findViewById(R.id.rl_request_claim);
        rl_request_claim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Comprehensive_Warranty.this, Initiate_Claim_Activity_New_Customer_App.class);
                startActivity(intent);
            }
        });

        rl_back=findViewById(R.id.rl_back);
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Comprehensive_Warranty.this,Customer_Home_page_bottom_navigation.class);
                startActivity(intent);

            }
        });
    }
}