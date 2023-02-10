package com.wisedrive.customerapp;

import static com.wisedrive.customerapp.R.id.relative_layout_send_otp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Sign_In_Activity extends AppCompatActivity {
    RelativeLayout rl_send_otp_button,rl_otp_boxes,relative_layout_login,rl_Login_button,relative_layout_send_otp;
    TextView tv_sign_in,tv_wisedrive, tv_in_to,tv_sign_up,text_verification,text_code,tv_passcode,
            text_phone_number_field;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        tv_sign_in=findViewById(R.id.tv_sign_in);
        tv_wisedrive=findViewById(R.id.tv_wisedrive);
        tv_in_to=findViewById(R.id.tv_in_to);
        text_verification=findViewById(R.id.text_verification);
        text_code=findViewById(R.id.text_code);
        tv_passcode=findViewById(R.id.tv_passcode);
        text_phone_number_field=findViewById(R.id.text_phone_number_field);
        rl_otp_boxes=findViewById(R.id.rl_otp_boxes);
        rl_send_otp_button=findViewById(R.id.send_otp_button);
        relative_layout_login=findViewById(R.id.relative_layout_login);
        relative_layout_send_otp=findViewById(R.id.relative_layout_send_otp);
        rl_Login_button=findViewById(R.id.rl_Login_button);
        rl_send_otp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_sign_in.setVisibility(View.INVISIBLE);
                tv_wisedrive.setVisibility(View.INVISIBLE);
                tv_in_to.setVisibility(View.INVISIBLE);
                text_verification.setVisibility(View.VISIBLE);
                text_code.setVisibility(View.VISIBLE);
                tv_passcode.setVisibility(View.VISIBLE);
                text_phone_number_field.setVisibility(View.VISIBLE);
                relative_layout_login.setVisibility(View.VISIBLE);
                rl_otp_boxes.setVisibility(View.VISIBLE);
                relative_layout_send_otp.setVisibility(View.INVISIBLE);



            }
        });
        rl_Login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Sign_In_Activity.this,Customer_Home_page_bottom_navigation.class);
                  startActivity(intent);

            }
        });
        tv_sign_up=findViewById(R.id.tv_sign_up);
        tv_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Sign_In_Activity.this,Create_Account.class);
                startActivity(intent);
            }
        });

    }
}