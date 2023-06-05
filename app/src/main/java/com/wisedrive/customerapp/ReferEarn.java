package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ReferEarn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer_earn);
        ReferEarn.this.getWindow().setStatusBarColor(ReferEarn.this.getColor(R.color.bl_gre));

    }
}