package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
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

public class ActivatedMaintenancePlan extends AppCompatActivity {
    private ApiInterface apiInterface;
    public RelativeLayout rl_viewservices,rl_view_policy;
    TextView veh_reg_no,validfrom;
    ProgressBar progress;
    //bahaaha
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activated_maintenance_plan);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        validfrom=findViewById(R.id.validfrom);
        progress=findViewById(R.id.progress);
        veh_reg_no=findViewById(R.id.veh_reg_no);
        rl_viewservices=findViewById(R.id.rl_viewservices);
        rl_view_policy=findViewById(R.id.rl_view_policy);
        rl_viewservices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ActivatedMaintenancePlan.this,VehiclePackageDetails.class);
                startActivity(intent);
            }
        });
        rl_view_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.comingfrom="vp";
                Intent intent=new Intent(ActivatedMaintenancePlan.this,WebPage.class);
                startActivity(intent);
            }
        });
        veh_reg_no.setText(SPHelper.customer_veh_no);
        validfrom.setText("Valid from "+SPHelper.veh_valid_from);
        get_web_links();
    }

    public  void get_web_links(){
        if(!Connectivity.isNetworkConnected(ActivatedMaintenancePlan.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }else
        {
            progress.setVisibility(View.VISIBLE);

            Call<AppResponse> call =  apiInterface.get_weblinks();
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response)
                {
                    AppResponse appResponse = response.body();

                    if (response.body()!=null) {
                        assert appResponse != null;
                        if (appResponse.getResponseType().equals("200"))
                        {
                            progress.setVisibility(View.GONE);

                            if(!appResponse.getResponseModel().getGetweblinks().getView_policy().equals("")){
                                SPHelper.viewpolicy=appResponse.getResponseModel().getGetweblinks().getView_policy();
                            }else{
                                SPHelper.viewpolicy="";
                            }

                        } else if (appResponse.getResponseType().equals("300")) {
                            progress.setVisibility(View.GONE);
                            Toast.makeText(ActivatedMaintenancePlan.this, "internal server error" + "response code:" + appResponse.getResponseType(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        progress.setVisibility(View.GONE);
                        Toast.makeText(ActivatedMaintenancePlan.this, "internal server error" , Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AppResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    progress.setVisibility(View.GONE);
                }
            });
        }
    }
}