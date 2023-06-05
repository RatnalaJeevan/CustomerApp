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

import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.PojoCreateLead;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Create_Account extends AppCompatActivity
{
    RelativeLayout relativeLayout,create_account,rl_check,rl_bottom,edit_account,rl_back;
    TextView tv_login,tv_tnc,text_view1,text_view2,text_view4;
    EditText lead_name,lead_email_id,lead_no;
    ImageView checked;
    String emailpattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    String mobile_no_pattern="^[6-9][0-9]{9}$";
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    String c_no,c_name,c_mail,c_id,l_id;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        rl_back=findViewById(R.id.rl_back);
        text_view4=findViewById(R.id.text_view4);
        edit_account=findViewById(R.id.edit_account);
        rl_bottom=findViewById(R.id.rl_bottom);
        text_view2=findViewById(R.id.text_view2);
        text_view1=findViewById(R.id.text_view1);
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
        create_account.setOnClickListener(new View.OnClickListener()
        {
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
        if(SPHelper.is_edit.equals("y")){
            c_no= SPHelper.getSPData(Create_Account.this,SPHelper.customer_phoneno,"");
            c_name=SPHelper.getSPData(Create_Account.this,SPHelper.cust_name,"");
            c_mail=SPHelper.getSPData(Create_Account.this,SPHelper.cust_mail,"");
            c_id=SPHelper.getSPData(Create_Account.this,SPHelper.customer_id,"");
            l_id=SPHelper.getSPData(Create_Account.this,SPHelper.lead_id,"");
            rl_back.setVisibility(View.VISIBLE);
            text_view1.setText("Edit");
            text_view2.setText("your account");
            rl_bottom.setVisibility(View.GONE);
            edit_account.setVisibility(View.VISIBLE);
            rl_check.setVisibility(View.GONE);
            text_view4.setVisibility(View.GONE);
            tv_tnc.setVisibility(View.GONE);
            lead_name.setText(c_name);
            lead_no.setText(c_no);
            lead_email_id.setText(c_mail);
        }

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        edit_account.setOnClickListener(new View.OnClickListener() {
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
                }else if(lead_name.getText().toString().equals(c_name)&&
                        lead_no.getText().toString().equals(c_no)&&
                        lead_email_id.getText().toString().equals(c_mail)){
                    Toast.makeText(Create_Account.this, "Please edit to request" , Toast.LENGTH_SHORT).show();

                }else {
                   editt_account();
                }

            }
        });

    }



    private void hideKeybaord()
    {
        View view = Create_Account.this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)Create_Account.this.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void editt_account()
    {
        if(c_id==null||c_id.equals("null")){
            c_id="";
        }
        if(l_id==null||l_id.equals("null")){
            l_id="";
        }
        if(!Connectivity.isNetworkConnected(Create_Account.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }else
        {
            progressDialog.show();
            PojoCreateLead pojoCreateLead=new PojoCreateLead(lead_name.getText().toString(),lead_no.getText().toString(),
                    c_id,l_id,lead_email_id.getText().toString());
            Call<AppResponse> call =  apiInterface.req_for_edit(pojoCreateLead);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response)
                {

                    AppResponse appResponse = response.body();
                    String responsetype = appResponse.getResponseType();
                    progressDialog.dismiss();
                    if (responsetype.equals("200"))
                    {
                        SPHelper.fragment_is="profile";
                        Toast.makeText(Create_Account.this, appResponse.getResponseModel().getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(Create_Account.this,CustomerHomepage.class);
                        startActivity(intent);
                    } else if (responsetype.equals("300")) {
                        Toast.makeText(Create_Account.this, appResponse.getResponseModel().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AppResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }
    }
}