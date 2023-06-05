package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreen extends AppCompatActivity {
    String app_version;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    private static final int SPLASH_DISPLAY_TIME = 2000;
    String customer_support_no,customer_support_email;
    public  Intent intent;
    String lead_id, c_id,pack_activated;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setStatusBarColor(getColor(R.color.new_blue));
       // (SplashScreen)getWindow().setStatusBarColor((SplashScreen)getApplicationContext()).getColor(R.color.white));
        SPHelper.sharedPreferenceInitialization(SplashScreen.this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        progressDialog = new ProgressDialog(SplashScreen.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        intent=new Intent();
        update_app();
    }

    public void update_app(){
        if(!Connectivity.isNetworkConnected(SplashScreen.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Please Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        }else
        {

            Call<AppResponse> call =  apiInterface.getapp_update_deatails("2","1");
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response)
                {
                    AppResponse appResponse = response.body();
                    assert appResponse != null;
                    String response_code = appResponse.getResponseType();
                    if (response.body()!=null)
                    {
                        if (response_code.equals("200"))
                        {
                            SPHelper.app_url=appResponse.getResponseModel().getAppUpdateDetails().getApp_url();
                            SPHelper.can_skip=appResponse.getResponseModel().getAppUpdateDetails().getCan_skip();
                            app_version=appResponse.getResponseModel().getAppUpdateDetails().getAppversion();
                            SPHelper.tnc=appResponse.getResponseModel().getAppUpdateDetails().getTerms();
                            appResponse.getResponseModel().getAppUpdateDetails().getIs_current();
                            show_update();
                        }
                        else if (response_code.equals("300")) {
                            Toast.makeText(SplashScreen.this, appResponse.getResponseModel().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else{

                        Toast.makeText(SplashScreen.this, "internal server error" , Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                    Toast.makeText(getApplicationContext(),
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    public void show_update(){
        new Handler().postDelayed(new Runnable() {
            public void run()
            {
//                if(!app_version.equals(getString(R.string.app_version_name)))
//                {
//                    Intent intent=new Intent(SplashScreen.this, NotificationPage.class);
//                    startActivity(intent);
//                }else{
                    get_support_details();
                //}
            }
        }, SPLASH_DISPLAY_TIME);
    }
    public  void get_support_details(){
        if(!Connectivity.isNetworkConnected(SplashScreen.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }else
        {
            progressDialog.show();
            Call<AppResponse> call =  apiInterface.get_support_details();
            call.enqueue(new Callback<AppResponse>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response)
                {
                    AppResponse appResponse = response.body();

                    if (response.body()!=null) {
                        assert appResponse != null;
                        if (appResponse.getResponseType().equals("200")) {
                            progressDialog.dismiss();
                            customer_support_no=appResponse.getResponseModel().getSupportdetails().getCustomer_support_phone_no();
                            customer_support_email=appResponse.getResponseModel().getSupportdetails().getEmail_id();
                            SPHelper.saveSPdata(SplashScreen.this, SPHelper.customer_support_phoneno, customer_support_no);
                            SPHelper.saveSPdata(SplashScreen.this, SPHelper.customer_support_email, customer_support_email);
                            SPHelper.comingfrom="";
                             lead_id=SPHelper.getSPData(SplashScreen.this, SPHelper.lead_id, "");
                             c_id=SPHelper.getSPData(SplashScreen.this, SPHelper.customer_id, "");
                             pack_activated=SPHelper.getSPData(SplashScreen.this, SPHelper.package_activated, "");

                            if(!lead_id.equals("")||!c_id.equals(""))
                            {
                                //call api
                                get_customer_act_status();

                            }else{
                                SPHelper.fragment_is="plans";
                                Intent intent=new Intent(SplashScreen.this, Login_customer_app.class);
                                startActivity(intent);
                                finish();
                            }


                        } else if (appResponse.getResponseType().equals("300")) {
                            progressDialog.dismiss();
                            Toast.makeText(SplashScreen.this, "internal server error" + "response code:" + appResponse.getResponseType(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(SplashScreen.this, "internal server error" , Toast.LENGTH_SHORT).show();
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


    public void get_customer_act_status(){
        if(!Connectivity.isNetworkConnected(SplashScreen.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Please Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        }else
        {

            Call<AppResponse> call =  apiInterface.get_cust_status(c_id,lead_id);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response)
                {
                    AppResponse appResponse = response.body();
                    assert appResponse != null;
                    String response_code = appResponse.getResponseType();
                    if (response.body()!=null)
                    {
                        if (response_code.equals("200"))
                        {
                            String is_active=appResponse.getResponseModel().getGetstatus().getIs_active();
                            SPHelper.saveSPdata(SplashScreen.this, SPHelper.lead_id, appResponse.getResponseModel().getGetstatus().getLead_id());
                            SPHelper.saveSPdata(SplashScreen.this, SPHelper.customer_id, appResponse.getResponseModel().getGetstatus().getCustomer_id());
                            SPHelper.saveSPdata(SplashScreen.this, SPHelper.customer_name, appResponse.getResponseModel().getGetstatus().getCustomer_name());
                            SPHelper.saveSPdata(SplashScreen.this, SPHelper.customer_phoneno, appResponse.getResponseModel().getGetstatus().getPhone_no());
                            SPHelper.saveSPdata(SplashScreen.this, SPHelper.cust_mail, appResponse.getResponseModel().getGetstatus().getEmail_id());

                            if(is_active.equalsIgnoreCase("y"))
                            {
//                                if(!c_id.equals("")&&pack_activated.equalsIgnoreCase("n")){
//                                    SPHelper.fragment_is="act";
//                                }else{
//                                    SPHelper.fragment_is="plans";
//                                }
                                if(!app_version.equals(getString(R.string.app_version_name)))
                                {
                                    Intent intent=new Intent(SplashScreen.this, NotificationPage.class);
                                    startActivity(intent);
                                }else {
                                    SPHelper.fragment_is="plans";
                                    Intent intent=new Intent(SplashScreen.this, CustomerHomepage.class);
                                    startActivity(intent);
                                    finish();
                                }

                            }else {
                                SPHelper.fragment_is="plans";
                                Intent intent=new Intent(SplashScreen.this, Login_customer_app.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                        else if (response_code.equals("300")) {
                            Toast.makeText(SplashScreen.this, appResponse.getResponseModel().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else{

                        Toast.makeText(SplashScreen.this, "internal server error" , Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                    Toast.makeText(getApplicationContext(),
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

}