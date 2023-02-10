package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Create_Account extends AppCompatActivity {
    RelativeLayout relativeLayout;
    ImageView check1,check2,check3;
    CheckBox checkbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        relativeLayout=findViewById(R.id.button);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Create_Account.this,Verification_code.class);
                startActivity(intent);
            }
        });
        check1=findViewById(R.id.check1);
        check2=findViewById(R.id.check2);
        check3=findViewById(R.id.check3);
        checkbox=findViewById(R.id.checkbox);
        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int isvisible = checkbox.getVisibility();
                switch (isvisible) {
                    case View.VISIBLE:
                        check1.setVisibility(View.VISIBLE);
                        check2.setVisibility(View.GONE);
                        check3.setVisibility(View.GONE);

                        break;
                    default:
                        check1.setVisibility(View.GONE);
                        break;
                }
                    check2.setVisibility(View.VISIBLE);
                    check3.setVisibility(View.VISIBLE);



            }
        });
    }
}