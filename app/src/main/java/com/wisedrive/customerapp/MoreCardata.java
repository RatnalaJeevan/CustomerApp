package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

public class MoreCardata extends AppCompatActivity {
    RadioButton manual,auto,yes,no;
    RadioGroup radioGroup,radioGroup1;
    RelativeLayout rl_back;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_cardata);
        rl_back=findViewById(R.id.rl_back);
        radioGroup = findViewById(R.id.radioGroup);
        radioGroup1=findViewById(R.id.radioGroup1);
        auto = findViewById(R.id.auto);
        manual=findViewById(R.id.manual);
        yes = findViewById(R.id.yes);
        no=findViewById(R.id.no);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                RadioButton radioButton = findViewById(checkedId);
                String selectedOption = radioButton.getText().toString();
                if(selectedOption.equalsIgnoreCase("manual")){
                    manual.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
                    auto.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.grey)));
                }else {
                    auto.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
                    manual.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.grey)));
                }
            }
        });

        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                RadioButton radioButton = findViewById(checkedId);
                String selectedOption = radioButton.getText().toString();
                if(selectedOption.equalsIgnoreCase("yes")){
                    yes.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
                    no.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.grey)));
                }else {
                    no.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
                    yes.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.grey)));
                }
            }
        });

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MoreCardata.this,EnterCarDetails.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(MoreCardata.this,EnterCarDetails.class);
        startActivity(intent);
    }
}