package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.AppResponse;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationPage extends AppCompatActivity {
    RelativeLayout tv_AU_Update1;
    TextView tv_AU_skip;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    public  Intent intent;
    String customer_support_no,customer_support_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_page);
        SPHelper.sharedPreferenceInitialization(NotificationPage.this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        progressDialog = new ProgressDialog(NotificationPage.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        tv_AU_skip=findViewById(R.id.tv_AU_skip);
        tv_AU_Update1=findViewById(R.id.tv_AU_Update1);

        if(SPHelper.can_skip.equalsIgnoreCase("y")){
            tv_AU_skip.setVisibility(View.VISIBLE);
        }else {
            tv_AU_skip.setVisibility(View.GONE);
        }
        tv_AU_Update1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent httpIntent = new Intent(Intent.ACTION_VIEW);
                httpIntent.setData(Uri.parse(SPHelper.app_url));
                startActivity(httpIntent);
            }
        });
        tv_AU_skip.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                get_support_details();
            }
        });
    }

    public  void get_support_details(){
        if(!Connectivity.isNetworkConnected(NotificationPage.this))
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
                            SPHelper.saveSPdata(NotificationPage.this, SPHelper.customer_support_phoneno, customer_support_no);
                            SPHelper.saveSPdata(NotificationPage.this, SPHelper.customer_support_email, customer_support_email);
                            SPHelper.comingfrom="";
                            if(SPHelper.getSPData(NotificationPage.this, SPHelper.package_activated, "").equals("y"))
                            {
//                                Intent intent=new Intent(NotificationPage.this, VehiclePackageDetails.class);
//                                startActivity(intent);
                                finish();

                            }else if(SPHelper.getSPData(NotificationPage.this, SPHelper.otp_activated, "").equals("y")){
//                                Intent intent=new Intent(NotificationPage.this, PackageActivation.class);
//                                startActivity(intent);
                                finish();

                            }else{
//                                Intent intent=new Intent(NotificationPage.this, LoginNewPage.class);
//                                startActivity(intent);
//                                finish();

                            }

                        } else if (appResponse.getResponseType().equals("300")) {
                            progressDialog.dismiss();
                            Toast.makeText(NotificationPage.this, "internal server error" + "response code:" + appResponse.getResponseType(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(NotificationPage.this, "internal server error" , Toast.LENGTH_SHORT).show();
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
    public void onBackPressed() {
        super.onBackPressed();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}