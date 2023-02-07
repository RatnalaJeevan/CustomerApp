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
import com.wisedrive.customerapp.pojos.AppResponse;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
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
                if(!app_version.equals(getString(R.string.app_version_name))){
                    Intent intent=new Intent(SplashScreen.this, NotificationPage.class);
                    startActivity(intent);
                }else{
                    get_support_details();
                }
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
                            if(SPHelper.getSPData(SplashScreen.this, SPHelper.package_activated, "").equals("y"))
                            {
                                intent.setClass(SplashScreen.this,
                                        VehiclePackageDetails.class);
                                SplashScreen.this.startActivity(intent);
                                SplashScreen.this.finish();

                            }else if(SPHelper.getSPData(SplashScreen.this, SPHelper.otp_activated, "").equals("y")){
                                intent.setClass(SplashScreen.this,
                                        PackageActivation.class);
                                SplashScreen.this.startActivity(intent);
                                SplashScreen.this.finish();
                            }else{
                                intent.setClass(SplashScreen.this,
                                        LoginNewPage.class);
                                SplashScreen.this.startActivity(intent);
                                SplashScreen.this.finish();
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
}