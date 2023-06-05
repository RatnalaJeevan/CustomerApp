package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.pojos.PojoCreateLead;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Sign_In_Activity extends AppCompatActivity {
    String mobile_no_pattern="^[6-9][0-9]{9}$";
    Boolean mBackSpace;
    Integer mPreviousLength,textlength1,textlength2,textlength3;
    String otps,is_for_login="",name,email,no,comingfrom="";
    RelativeLayout rl_send_otp_button,rl_otp_boxes,relative_layout_login,rl_Login_button,relative_layout_send_otp;
    TextView tv_sign_in,tv_sign_up,cust_no,text_resend;
    EditText entered_cust_no,otp1,otp2,otp3,otp4;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        SPHelper.sharedPreferenceInitialization(Sign_In_Activity.this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        progressDialog = new ProgressDialog(Sign_In_Activity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        text_resend=findViewById(R.id.text_resend);
        tv_sign_in=findViewById(R.id.tv_sign_in);
        cust_no=findViewById(R.id.cust_no);
        rl_otp_boxes=findViewById(R.id.rl_otp_boxes);
        rl_send_otp_button=findViewById(R.id.send_otp_button);
        relative_layout_login=findViewById(R.id.relative_layout_login);
        relative_layout_send_otp=findViewById(R.id.relative_layout_send_otp);
        rl_Login_button=findViewById(R.id.rl_Login_button);
        entered_cust_no=findViewById(R.id.entered_cust_no);
        tv_sign_up=findViewById(R.id.tv_sign_up);
        otp1=findViewById(R.id.otp1);
        otp2=findViewById(R.id.otp2);
        otp3=findViewById(R.id.otp3);
        otp4=findViewById(R.id.otp4);
        rl_send_otp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(entered_cust_no.getText().toString().equals("")){
                    Toast.makeText(Sign_In_Activity.this, "Enter a phone number" , Toast.LENGTH_SHORT).show();
                }else if(entered_cust_no.getText().toString().length()<10){
                    Toast.makeText(Sign_In_Activity.this, "Enter a valid phone number" , Toast.LENGTH_SHORT).show();
                }
                else if(!entered_cust_no.getText().toString().matches(mobile_no_pattern)){
                    Toast.makeText(Sign_In_Activity.this, "Enter a valid phone number" , Toast.LENGTH_SHORT).show();
                }else{
                    send_otp();

                }
            }
        });

        text_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send_otp();
            }
        });
        entered_cust_no.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {

                if(entered_cust_no.getText().toString().length()==10)
                {
                    hideKeybaord();
                }
            }
        });


        otp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {
                mPreviousLength = s.length();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                textlength1 = otp1.getText().length();
                if (textlength1 >= 1) {
                    otp2.setEnabled(true);
                    otp2.requestFocus();
                }
                mBackSpace = mPreviousLength > s.length();

                if (mBackSpace) {
                    otp1.setText("");
                    otp1.requestFocus();
                }

            }
        });
        otp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {
                mPreviousLength = s.length();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int count) {

            }


            @Override
            public void afterTextChanged(Editable s) {
                textlength2 = otp2.getText().length();
                if (textlength2 >= 1) {
                    otp3.requestFocus();
                }
                mBackSpace = mPreviousLength > s.length();

                if (mBackSpace) {
                    otp2.setText("");
                    otp1.requestFocus();
                    // do your stuff ...

                }
            }
        });
        otp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {
                mPreviousLength = s.length();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                textlength3 = otp3.getText().length();

                if (textlength3 >= 1) {
                    otp4.requestFocus();
                }
                mBackSpace = mPreviousLength > s.length();

                if (mBackSpace) {
                    otp3.setText("");
                    otp2.requestFocus();
                    // do your stuff ...

                }
            }
        });
        otp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {
                mPreviousLength = s.length();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mBackSpace = mPreviousLength > s.length();

                if (mBackSpace) {
                    otp4.setText("");
                    otp3.requestFocus();
                    // do your stuff ...

                }
                Integer textlength4 = otp4.getText().length();

                if (textlength4 >= 1)
                {
                    hideKeybaord();
                    //verify_otp();
                }

            }
        });
        rl_Login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otps=otp1.getText().toString()+otp2.getText().toString()+otp3.getText().toString()+otp4.getText().toString();
                if(otps.length()==0){
                    Toast.makeText(Sign_In_Activity.this, "Enter an otp" , Toast.LENGTH_SHORT).show();
                }
               else if(otps.length()<4){
                    Toast.makeText(Sign_In_Activity.this, "Enter a valid otp" , Toast.LENGTH_SHORT).show();
                }else{
                    validate_otp();
                }
            }
        });

        tv_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Sign_In_Activity.this,Create_Account.class);
                startActivity(intent);
            }
        });

         Intent intent=getIntent();
         comingfrom = intent.getStringExtra("comingfrom");
         name = intent.getStringExtra("name");
         email = intent.getStringExtra("email");
         no = intent.getStringExtra("no");
         System.out.println("name"+comingfrom+name+email+no);
        if(comingfrom.equals("create")){
            is_for_login="N";
            cust_no.setText(no);
            entered_cust_no.setText(no);
            send_otp();
        }else{
            is_for_login="Y";

        }

    }

    public void send_otp()
    {
        if(!Connectivity.isNetworkConnected(Sign_In_Activity.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }else
        {
            progressDialog.show();
            Call<AppResponse> call =  apiInterface.insertOtp(entered_cust_no.getText().toString(),is_for_login);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response)
                {
                    AppResponse appResponse = response.body();
                    String responsetype = appResponse.getResponseType();
                    progressDialog.dismiss();
                    if (responsetype.equals("200"))
                    {
                        Toast.makeText(Sign_In_Activity.this, appResponse.getResponseModel().getMessage(), Toast.LENGTH_SHORT).show();
                        entered_cust_no.setVisibility(View.GONE);
                        tv_sign_in.setText("Enter\nOne Time\nPasscode");
                        cust_no.setText(entered_cust_no.getText().toString());
                        cust_no.setVisibility(View.VISIBLE);
                        rl_otp_boxes.setVisibility(View.VISIBLE);
                        relative_layout_send_otp.setVisibility(View.GONE);
                        relative_layout_login.setVisibility(View.VISIBLE);
                        settimer();
                    } else if (responsetype.equals("300")) {
                        Toast.makeText(Sign_In_Activity.this, appResponse.getResponseModel().getMessage(), Toast.LENGTH_SHORT).show();
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

    public  void validate_otp(){

        if(!Connectivity.isNetworkConnected(Sign_In_Activity.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            progressDialog.show();
            Call<AppResponse> call=apiInterface.validateOtp(entered_cust_no.getText().toString().trim(),otps);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response)
                {
                    AppResponse appResponse=response.body();

                    if(appResponse.getResponseType().equals("200"))
                    {
                        progressDialog.dismiss();

                        SPHelper.saveSPdata(Sign_In_Activity.this, SPHelper.lead_id, appResponse.getResponseModel().getLogindetails().getLead_id());
                        SPHelper.saveSPdata(Sign_In_Activity.this, SPHelper.customer_id, appResponse.getResponseModel().getLogindetails().getCustomer_id());
                        SPHelper.saveSPdata(Sign_In_Activity.this, SPHelper.customer_phoneno, entered_cust_no.getText().toString().trim());
                        SPHelper.saveSPdata(Sign_In_Activity.this, SPHelper.cust_mail, appResponse.getResponseModel().getLogindetails().getEmail_id());
                        SPHelper.saveSPdata(Sign_In_Activity.this, SPHelper.cust_name, appResponse.getResponseModel().getLogindetails().getCustomer_name());
                        SPHelper.saveSPdata(Sign_In_Activity.this, SPHelper.package_activated, appResponse.getResponseModel().getLogindetails().getIs_package_activated());
                        SPHelper.saveSPdata(Sign_In_Activity.this, SPHelper.otp_activated, "y");
                        SPHelper.saveSPdata(Sign_In_Activity.this, SPHelper.awssecret, appResponse.getResponseModel().getCredentials().getAws_secret());
                        SPHelper.saveSPdata(Sign_In_Activity.this, SPHelper.awskey, appResponse.getResponseModel().getCredentials().getAws_key());
                        SPHelper.saveSPdata(Sign_In_Activity.this, SPHelper.comet_authkey, appResponse.getResponseModel().getCredentials().getComet_auth_key());
                        SPHelper.saveSPdata(Sign_In_Activity.this, SPHelper.comet_region, appResponse.getResponseModel().getCredentials().getComet_region());
                        SPHelper.saveSPdata(Sign_In_Activity.this, SPHelper.comet_appid, appResponse.getResponseModel().getCredentials().getComet_app_id());

                        if(comingfrom.equals("create")){
                            create_account();
                        }else{
//                            if(SPHelper.getSPData(Sign_In_Activity.this, SPHelper.package_activated, "").equalsIgnoreCase("y"))
//                            {
//                                SPHelper.fragment_is="plans";
//                            }else{
//                                SPHelper.fragment_is="act";
//                            }
                            SPHelper.fragment_is="plans";
                            Intent intent = new Intent(Sign_In_Activity.this, CustomerHomepage.class);
                            startActivity(intent);
                            finish();
                        }

                    }else  if(appResponse.getResponseType().equals("300")){
                        progressDialog.dismiss();
                        otp1.setText("");
                        otp2.setText("");
                        otp3.setText("");
                        otp4.setText("");
                        otp1.requestFocus();
                        Toast.makeText(Sign_In_Activity.this,"entered otp is wrong", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(Sign_In_Activity.this,
                                "entered otp is wrong,please enter valid otp",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AppResponse> call, Throwable t)
                {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void create_account()
    {
        if(!Connectivity.isNetworkConnected(Sign_In_Activity.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }else
        {
            progressDialog.show();
            PojoCreateLead pojoCreateLead=new PojoCreateLead(name,email,no);
            Call<AppResponse> call =  apiInterface.createLead(pojoCreateLead);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response)
                {

                    AppResponse appResponse = response.body();
                    String responsetype = appResponse.getResponseType();
                    progressDialog.dismiss();
                    if (responsetype.equals("200"))
                    {
                        SPHelper.saveSPdata(Sign_In_Activity.this, SPHelper.lead_id, appResponse.getResponseModel().getLogindetails().getLead_id());
                        SPHelper.saveSPdata(Sign_In_Activity.this, SPHelper.customer_id, appResponse.getResponseModel().getLogindetails().getCustomer_id());
                        SPHelper.saveSPdata(Sign_In_Activity.this, SPHelper.customer_phoneno, appResponse.getResponseModel().getLogindetails().getCustomer_no());
                        SPHelper.saveSPdata(Sign_In_Activity.this, SPHelper.cust_mail, appResponse.getResponseModel().getLogindetails().getEmail_id());
                        SPHelper.saveSPdata(Sign_In_Activity.this, SPHelper.cust_name, appResponse.getResponseModel().getLogindetails().getCustomer_name());
                        SPHelper.fragment_is="plans";
                        Intent intent=new Intent(Sign_In_Activity.this, CustomerHomepage.class);
                        startActivity(intent);
                    } else if (responsetype.equals("300")) {
                        Toast.makeText(Sign_In_Activity.this, appResponse.getResponseModel().getMessage(), Toast.LENGTH_SHORT).show();
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
    private void hideKeybaord() {

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } }

    public  void settimer()
    {
        new CountDownTimer(30000, 1000)
        {
            public void onTick(long millisUntilFinished)
            {
                text_resend.setEnabled(false);
                text_resend.setText("Resend code in\t"+ millisUntilFinished / 1000+"\tsec");
            }
            public void onFinish() {
                text_resend.setEnabled(true);
                text_resend.setText("Resend");
            }
        }.start();
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(Sign_In_Activity.this,Login_customer_app.class);
        startActivity(intent);
    }
}