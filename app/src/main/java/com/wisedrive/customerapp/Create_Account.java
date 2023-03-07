package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

public class Create_Account extends AppCompatActivity {
    RelativeLayout relativeLayout,create_account,rl_check;
    TextView tv_login,tv_tnc;
    EditText lead_name,lead_email_id,lead_no;
    ImageView checked;
    String emailpattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    String mobile_no_pattern="^[6-9][0-9]{9}$";
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        tv_tnc=findViewById(R.id.tv_tnc);
        lead_name=findViewById(R.id.lead_name);
        lead_email_id=findViewById(R.id.lead_email_id);
        lead_no=findViewById(R.id.lead_no);
        tv_login=findViewById(R.id.tv_login);
        rl_check=findViewById(R.id.rl_check);
        checked=findViewById(R.id.checked);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        progressDialog = new ProgressDialog(Create_Account.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        relativeLayout=findViewById(R.id.button);
        create_account=findViewById(R.id.create_account);
        lead_no.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {

                if(lead_no.getText().toString().length()==10)
                {
                    hideKeybaord();
                }
            }
        });
        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lead_name.getText().toString().equals("")){
                    Toast.makeText(Create_Account.this, "Enter a name" , Toast.LENGTH_SHORT).show();
                }else if(lead_email_id.getText().toString().equals("")){
                    Toast.makeText(Create_Account.this, "Enter an email" , Toast.LENGTH_SHORT).show();
                }else if(!lead_email_id.getText().toString().matches(emailpattern)){
                    Toast.makeText(Create_Account.this, "Enter valid email" , Toast.LENGTH_SHORT).show();
                }else if(lead_no.getText().toString().equals("")){
                    Toast.makeText(Create_Account.this, "Enter a phone number" , Toast.LENGTH_SHORT).show();
                }else if(lead_no.getText().toString().length()<10){
                    Toast.makeText(Create_Account.this, "Enter a valid phone number" , Toast.LENGTH_SHORT).show();
                }
                else if(!lead_no.getText().toString().matches(mobile_no_pattern)){
                    Toast.makeText(Create_Account.this, "Enter a valid phone number" , Toast.LENGTH_SHORT).show();
                }
                else if(checked.getVisibility()==View.GONE){
                    Toast.makeText(Create_Account.this, "Please agree with the terms and conditions" , Toast.LENGTH_SHORT).show();
                }
                else {
                   // create_account();
                    Intent intent = new Intent(Create_Account.this, Sign_In_Activity.class);
                    intent.putExtra("comingfrom","create");
                    intent.putExtra("name",lead_name.getText().toString());
                    intent.putExtra("email",lead_email_id.getText().toString());
                    intent.putExtra("no",lead_no.getText().toString());
                    startActivity(intent);
                }
            }
        });

        rl_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checked.getVisibility()==View.VISIBLE){
                    checked.setVisibility(View.GONE);
                }else{
                    checked.setVisibility(View.VISIBLE);
                }
            }
        });
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Create_Account.this,Sign_In_Activity.class);
                intent.putExtra("comingfrom","login");
                startActivity(intent);

            }
        });

        tv_tnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.comingfrom="tnc";
                Intent intent=new Intent(Create_Account.this,WebPage.class);
                startActivity(intent);
            }
        });
    }



    private void hideKeybaord() {
        InputMethodManager inputManager = (InputMethodManager) Create_Account.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(Create_Account.this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
    }
}