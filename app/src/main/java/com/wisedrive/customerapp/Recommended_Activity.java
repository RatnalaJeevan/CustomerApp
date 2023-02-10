package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class Recommended_Activity extends AppCompatActivity {
    RelativeLayout rl_upgrade_save_button;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended);

        rl_upgrade_save_button=findViewById(R.id.rl_upgrade_save_button);
        rl_upgrade_save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Recommended_Activity.this,Activation_Confirmation_Activity.class);
                startActivity(intent);
            }
        });
    }
}