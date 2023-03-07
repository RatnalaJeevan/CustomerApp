package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PackageActivation extends AppCompatActivity {

    TextView yes,no,title,title1;
    private  Dialog dialog;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    EditText entered_act_code;
    TextView req_activation_code,changeno,cancel;
    TextView activate_button;
    RelativeLayout rl_activation_error,rl_transparent,rl_changeno_cancel;
    private boolean doubleBackToExitPressedOnce=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_activation);
        SPHelper.sharedPreferenceInitialization(PackageActivation.this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        progressDialog = new ProgressDialog(PackageActivation.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        rl_changeno_cancel= findViewById(R.id.rl_changeno_cancel);
        rl_activation_error= findViewById(R.id.rl_activation_error);
        rl_transparent= findViewById(R.id.rl_transparent);
        activate_button=findViewById(R.id.activate_button);
        title1=findViewById(R.id.title1);
        title=findViewById(R.id.title);
        changeno=findViewById(R.id.changeno);
        cancel=findViewById(R.id.cancel);
        entered_act_code= findViewById(R.id.entered_act_code);
        req_activation_code= findViewById(R.id.req_activation_code);

        dialog = new Dialog(PackageActivation.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.popup_logout_dialog);
        dialog.setCancelable(true);

        yes=dialog.findViewById(R.id.yes) ;
        yes.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                SPHelper.saveSPdata(PackageActivation.this, SPHelper.package_activated, "");
                SPHelper.saveSPdata(PackageActivation.this, SPHelper.otp_activated, "");
                SPHelper.saveSPdata(PackageActivation.this, SPHelper.customer_phoneno, "");
                SPHelper.saveSPdata(PackageActivation.this, SPHelper.customer_support_phoneno, "");
                SPHelper.saveSPdata(PackageActivation.this, SPHelper.customer_support_email, "");

                Intent i = new Intent(PackageActivation.this,LoginNewPage.class);
                startActivity(i);
                finish();
                dialog.dismiss();
            }
        });
        no=dialog.findViewById(R.id.no) ;
        no.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dialog.cancel();
                dialog.dismiss();
            }
        });

        req_activation_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                request_act_code();
            }
        });
        rl_transparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_activation_error.setVisibility(View.GONE);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_activation_error.setVisibility(View.GONE);
            }
        });

        activate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (entered_act_code.getText().toString().equals(""))
                {
                    Toast.makeText(PackageActivation.this, "Please enter an activation code", Toast.LENGTH_SHORT).show();

                }
                else if (entered_act_code.getText().toString().trim().length() == 10)
                {
                    verify_act_code();
                }else {
                    Toast.makeText(PackageActivation.this, "Invalid activation code", Toast.LENGTH_SHORT).show();
                }
            }
        });
        changeno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PackageActivation.this,LoginNewPage.class);
                startActivity(intent);
            }
        });

        entered_act_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(entered_act_code.getText().toString().length()==10){
                    hideKeybaord();
                }
            }
        });

    }

    public void verify_act_code()
    {
        if(!Connectivity.isNetworkConnected(PackageActivation.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }else
        {
            progressDialog.show();
            Call<AppResponse> call =  apiInterface.verify_activation_code( SPHelper.getSPData(PackageActivation.this, SPHelper.customer_phoneno, ""),entered_act_code.getText().toString().trim());
            call.enqueue(new Callback<AppResponse>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                    AppResponse appResponse = response.body();
                    if(appResponse.getResponseType()!=null)
                    {
                        if (appResponse.getResponseType().equals("200")) {
                            progressDialog.dismiss();
                            SPHelper.customer_veh_id = appResponse.getResponseModel().getCodedetails().getVehicle_id();
                            SPHelper.customer_exp_kms = appResponse.getResponseModel().getCodedetails().getExpires_on_kms();
                            SPHelper.veh_valid_from = appResponse.getResponseModel().getCodedetails().getValid_from();
                            SPHelper.customer_veh_no = appResponse.getResponseModel().getCodedetails().getVehicle_no();
                            SPHelper.veh_valid_to = appResponse.getResponseModel().getCodedetails().getValid_to();
                            SPHelper.saveSPdata(PackageActivation.this, SPHelper.package_activated, "y");
                            Intent intent = new Intent(PackageActivation.this, ActivatedMaintenancePlan.class);
                            startActivity(intent);
                        } else if (appResponse.getResponseType().equals("300")) {
                            progressDialog.dismiss();
                            rl_activation_error.setVisibility(View.VISIBLE);
                            changeno.setVisibility(View.VISIBLE);
                            cancel.setVisibility(View.VISIBLE);
                            title.setText("Activation Code Error");
                            title1.setText("Entered activation code is not valid  ");

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),
                                    "Error" + response.code(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),
                                "Something went wrong" ,
                                Toast.LENGTH_SHORT).show();
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
    public void request_act_code()
    {
        if(!Connectivity.isNetworkConnected(PackageActivation.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }else
        {
            progressDialog.show();
            Call<AppResponse> call =  apiInterface.request_activation_code( SPHelper.getSPData(PackageActivation.this, SPHelper.customer_phoneno, ""));
            call.enqueue(new Callback<AppResponse>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                    AppResponse appResponse = response.body();
                    if(appResponse.getResponseType()!=null) {
                        if (appResponse.getResponseType().equals("200")) {
                            progressDialog.dismiss();
                            System.out.println("activation code request for 200 response");
                            rl_activation_error.setVisibility(View.VISIBLE);
                            rl_changeno_cancel.setVisibility(View.GONE);
                            title.setText("Success");
                            title.setTextColor(Color.parseColor("#008000"));
                            title1.setText("We have sent an activation code to your registered mobile number  ");

                        } else if (appResponse.getResponseType().equals("300")) {
                            progressDialog.dismiss();
                            rl_activation_error.setVisibility(View.VISIBLE);
                            cancel.setVisibility(View.VISIBLE);
                            changeno.setVisibility(View.VISIBLE);
                            title.setText("Activation code status");
                            title1.setText(appResponse.getResponseModel().getMessage());
                            cancel.setText("Cancel");
                        } else if (appResponse.getResponseType().equals("500")) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),
                                    "Error" + response.code(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),
                                "Something went wrong" ,
                                Toast.LENGTH_SHORT).show();
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
    @Override
    public void onBackPressed()
    {
        if(SPHelper.comingfrom.equals("customerpage")){
            Intent intent=new Intent(PackageActivation.this,CustomerProfile.class);
            startActivity(intent);
            finish();
        }else
            {
             dialog.show();
           }

    }

    private void hideKeybaord() {
        InputMethodManager inputManager = (InputMethodManager) PackageActivation.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(PackageActivation.this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

    }
}