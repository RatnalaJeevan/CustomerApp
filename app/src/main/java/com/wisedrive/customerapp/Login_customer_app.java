package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class Login_customer_app extends AppCompatActivity {
    RelativeLayout relativeLayout1,relativeLayout2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_customer_app);
        relativeLayout1=findViewById(R.id.rl_create_account);
        relativeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login_customer_app.this,Create_Account.class);
                startActivity(intent);
            }
        });
        relativeLayout2=findViewById(R.id.rl_sign_in);
        relativeLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login_customer_app.this,Sign_In_Activity.class);
                startActivity(intent);
            }
        });
    }
}