package com.wisedrive.customerapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginNewPage extends AppCompatActivity {

    Boolean mBackSpace;
    Integer mPreviousLength;
    TextView tv_login,tv_sendotp,tv_signup,tv_otp,tv_resend,timer;
    EditText selected_mobileno,otp1,otp2,otp3,otp4;
    LinearLayout otp_ll;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
     String otps;
     Integer textlength1,textlength2,textlength3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginnew_page);
        SPHelper.sharedPreferenceInitialization(LoginNewPage.this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        progressDialog = new ProgressDialog(LoginNewPage.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        timer=findViewById(R.id.timer);
        otp_ll=(LinearLayout)findViewById(R.id.otp_ll);
        tv_otp=findViewById(R.id.tv_otp);
        otp1=findViewById(R.id.otp1);
        otp2=findViewById(R.id.otp2);
        otp3=findViewById(R.id.otp3);
        otp4=findViewById(R.id.otp4);
        tv_resend=findViewById(R.id.tv_resend);
        selected_mobileno=findViewById(R.id.selected_mobileno);
        tv_signup=findViewById(R.id.tv_signup) ;
        tv_login=findViewById(R.id.tv_login);
        tv_sendotp=findViewById(R.id.tv_sendotp);
        selected_mobileno.requestFocus();
        phnnumvalidate();
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selected_mobileno.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),
                            "Please Enter Your PhoneNo",
                            Toast.LENGTH_SHORT).show();
                }else if(selected_mobileno.getText().toString().length()<10){
                    Toast.makeText(getApplicationContext(),
                            "Please Enter Valid Phone Number",
                            Toast.LENGTH_SHORT).show();
                }else if(otp1.getText().toString().length()<1 || otp2.getText().toString().length()<1||otp3.getText().toString().length()<1||otp4.getText().toString().length()<1){

                    Toast.makeText(getApplicationContext(),
                            "Please Enter Valid Otp",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    verify_otp();
                }
            }
        });
        tv_sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //tv_sendotp.setVisibility(View.GONE);
                if(selected_mobileno.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),
                            "Please Enter your Phone Number",
                            Toast.LENGTH_SHORT).show();
                }else if(selected_mobileno.getText().toString().length()<10){
                    Toast.makeText(getApplicationContext(),
                            "Please Enter Valid Phone Number",
                            Toast.LENGTH_SHORT).show();
                }else{

                    send_otp();
                }

            }
        });
        tv_resend.setVisibility(View.INVISIBLE);

        tv_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv_resend.setVisibility(View.INVISIBLE);
                timer.setVisibility(View.VISIBLE);
                if(selected_mobileno.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),
                            "Please Enter your Phone Number",
                            Toast.LENGTH_SHORT).show();
                }else if(selected_mobileno.getText().toString().length()<10){
                    Toast.makeText(getApplicationContext(),
                            "Please Enter Valid Phone Number",
                            Toast.LENGTH_SHORT).show();
                }else{

                    send_otp();
                }
                settimer();
            }
        });

        selected_mobileno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(selected_mobileno.getText().toString().length()<10){
                    tv_sendotp.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(selected_mobileno.getText().toString().length()<10){
                    tv_sendotp.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

                if(selected_mobileno.getText().toString().length()==10)
                {
                    hideKeybaord();
                    tv_sendotp.setVisibility(View.VISIBLE);
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
                    tv_login.setVisibility(View.VISIBLE);
                    tv_login.setBackground(getApplicationContext().getDrawable(R.drawable.cardview_dealership));
                    tv_login.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
                    tv_login.setTextColor(Color.parseColor("#FFFFFFFF"));
                    verify_otp();
                }else{
                    tv_login.setBackground(getApplicationContext().getDrawable(R.drawable.cardview_dealership));
                    tv_login.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.greywhite));
                    tv_login.setTextColor(Color.parseColor("#0619c3"));
                }

            }
        });

    }

    public void phnnumvalidate() {

        selected_mobileno.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                if(selected_mobileno.getText().toString().length()<10)
                {
//                    Toast.makeText(getApplicationContext(),
//                            "Please Enter Valid Phone Number",
//                            Toast.LENGTH_SHORT).show();

                    otp_ll.setVisibility(View.INVISIBLE);
                    tv_otp.setVisibility(View.INVISIBLE);
                    tv_resend.setVisibility(View.INVISIBLE);
                    tv_login.setVisibility(View.INVISIBLE);
                    tv_sendotp.setVisibility(View.VISIBLE);
                    timer.setVisibility(View.INVISIBLE);
                    tv_resend.setVisibility(View.INVISIBLE);

                }

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {

            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {

            }
        });
    }
    public void send_otp()
    {

        if(!Connectivity.isNetworkConnected(LoginNewPage.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }else
        {
            progressDialog.show();
            Call<AppResponse> call =  apiInterface.getOTP(selected_mobileno.getText().toString().trim());
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                    AppResponse appResponse = response.body();
                    String responsetype = appResponse.getResponseType();

                    if (responsetype.equals("200")) {
                        progressDialog.dismiss();
                        Toast.makeText(LoginNewPage.this, "OTP sent successfully", Toast.LENGTH_SHORT).show();
                        String textToPass = (String) selected_mobileno.getText().toString();
                        otp_ll.setVisibility(View.VISIBLE);
                        tv_otp.setVisibility(View.VISIBLE);
                        tv_login.setVisibility(View.VISIBLE);
                        tv_sendotp.setVisibility(View.INVISIBLE);
                        timer.setVisibility(View.VISIBLE);
                        settimer();
                        //settimer();
                       // tv_resend.setVisibility(View.VISIBLE);

//                        Intent intent = new Intent(LoginNewPage.this, OTPActivity.class);
//                        intent.putExtra(Intent.EXTRA_TEXT, textToPass);
//                        startActivity(intent);
                    } else if (responsetype.equals("300")) {
                        progressDialog.dismiss();
                        otp_ll.setVisibility(View.INVISIBLE);
                        tv_otp.setVisibility(View.INVISIBLE);
                        tv_login.setVisibility(View.INVISIBLE);
                        tv_sendotp.setVisibility(View.VISIBLE);
                        tv_resend.setVisibility(View.INVISIBLE);
                        timer.setVisibility(View.INVISIBLE);
                        Toast.makeText(LoginNewPage.this, "Employee not available", Toast.LENGTH_SHORT).show();
                        selected_mobileno.setText("");
                    } else {
                        progressDialog.dismiss();
                        otp_ll.setVisibility(View.INVISIBLE);
                        tv_otp.setVisibility(View.INVISIBLE);
                        tv_login.setVisibility(View.INVISIBLE);
                        tv_sendotp.setVisibility(View.VISIBLE);
                        tv_resend.setVisibility(View.INVISIBLE);
                        timer.setVisibility(View.INVISIBLE);
                        Toast.makeText(LoginNewPage.this, "invalid phoneNo", Toast.LENGTH_SHORT).show();
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

    public  void verify_otp(){

        otps=otp1.getText().toString()+otp2.getText().toString()+otp3.getText().toString()+otp4.getText().toString();
        if(!Connectivity.isNetworkConnected(LoginNewPage.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            progressDialog.show();
            Call<AppResponse> call=apiInterface.verifyOtp(selected_mobileno.getText().toString().trim(),otps);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response)
                {
                    AppResponse appResponse=response.body();

                    if(appResponse.getResponseType().equals("200"))
                    {
                        progressDialog.dismiss();
                        SPHelper.saveSPdata(LoginNewPage.this, SPHelper.customer_id, appResponse.getResponseModel().getLogindetails().getCustomer_id_1());
                        SPHelper.saveSPdata(LoginNewPage.this, SPHelper.customer_phoneno, selected_mobileno.getText().toString().trim());
                        SPHelper.saveSPdata(LoginNewPage.this, SPHelper.otp_activated, "y");
                        SPHelper.saveSPdata(LoginNewPage.this, SPHelper.awssecret, appResponse.getResponseModel().getCredentials().getAws_secret());
                        SPHelper.saveSPdata(LoginNewPage.this, SPHelper.awskey, appResponse.getResponseModel().getCredentials().getAws_key());
                        SPHelper.saveSPdata(LoginNewPage.this, SPHelper.comet_authkey, appResponse.getResponseModel().getCredentials().getComet_auth_key());
                        SPHelper.saveSPdata(LoginNewPage.this, SPHelper.comet_region, appResponse.getResponseModel().getCredentials().getComet_region());
                        SPHelper.saveSPdata(LoginNewPage.this, SPHelper.comet_appid, appResponse.getResponseModel().getCredentials().getComet_app_id());

                        Intent intent = new Intent(LoginNewPage.this, PackageActivation.class);
                        startActivity(intent);
                        finish();
                    }else  if(appResponse.getResponseType().equals("300")){
                        progressDialog.dismiss();
                        otp1.setText("");
                        otp2.setText("");
                        otp3.setText("");
                        otp4.setText("");
                        otp1.requestFocus();
                        Toast.makeText(LoginNewPage.this,"entered otp is wrong", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(LoginNewPage.this,
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

    public  void settimer()
    {
        new CountDownTimer(30000, 1000)
        {
            public void onTick(long millisUntilFinished)
            {
                timer.setText(""+ millisUntilFinished / 1000);
            }
            public void onFinish() {
                tv_resend.setVisibility(View.VISIBLE);
                timer.setText("");
            }
        }.start();
    }

    private void hideKeybaord() {
        InputMethodManager inputManager = (InputMethodManager) LoginNewPage.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(LoginNewPage.this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

    }
}