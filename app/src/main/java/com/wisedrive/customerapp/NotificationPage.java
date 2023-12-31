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
import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationPage extends AppCompatActivity {
    RelativeLayout tv_AU_Update1;
    TextView tv_AU_skip,tv_no_thanks;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    public  Intent intent;
    String customer_support_no,customer_support_email;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_page);
        getWindow().setStatusBarColor(getColor(R.color.white));
        SPHelper.sharedPreferenceInitialization(NotificationPage.this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        progressDialog = new ProgressDialog(NotificationPage.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        tv_AU_skip=findViewById(R.id.tv_AU_skip);
        tv_AU_Update1=findViewById(R.id.tv_AU_Update1);
        tv_no_thanks=findViewById(R.id.tv_no_thanks);
        if(SPHelper.can_skip.equalsIgnoreCase("y")){
            tv_AU_skip.setVisibility(View.VISIBLE);
            tv_no_thanks.setVisibility(View.GONE);
        }else {
            tv_AU_skip.setVisibility(View.GONE);
            tv_no_thanks.setVisibility(View.VISIBLE);
        }
        tv_AU_Update1.setOnClickListener(view -> {
            Intent httpIntent = new Intent(Intent.ACTION_VIEW);
            httpIntent.setData(Uri.parse(SPHelper.app_url));
            startActivity(httpIntent);
        });
        tv_AU_skip.setOnClickListener(view -> get_support_details());
        tv_no_thanks.setOnClickListener(view -> {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
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


                            String lead_id=SPHelper.getSPData(NotificationPage.this, SPHelper.lead_id, "");
                            String c_id=SPHelper.getSPData(NotificationPage.this, SPHelper.customer_id, "");
                            String pack_activated=SPHelper.getSPData(NotificationPage.this, SPHelper.package_activated, "");

                            if(!lead_id.equals("")||!c_id.equals(""))
                            {
                                SPHelper.fragment_is="plans";
                                Intent intent=new Intent(NotificationPage.this, CustomerHomepage.class);
                                startActivity(intent);
                                finish();
                            }else{
                                SPHelper.fragment_is="plans";
                                Intent intent=new Intent(NotificationPage.this, Login_customer_app.class);
                                startActivity(intent);
                                finish();
                            }
//

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