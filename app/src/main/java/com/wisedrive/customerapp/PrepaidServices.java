package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.wisedrive.customerapp.adapters.AdapterPrePaidServices;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.pojos.PojoPrepaidServices;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrepaidServices extends AppCompatActivity {

    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    ArrayList<PojoPrepaidServices> prepaidservices;
    AdapterPrePaidServices adapter;
    public RecyclerView rv_pre_services;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepaid_services);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        progressDialog = new ProgressDialog(PrepaidServices.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        prepaidservices=new ArrayList<PojoPrepaidServices>();
        back=findViewById(R.id.back);
        rv_pre_services=findViewById(R.id.rv_pre_services);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PrepaidServices.this, ServiceCompletedPage.class);
                startActivity(intent);
            }
        });
        get_prepaid_services_list();
    }

    public  void get_prepaid_services_list(){
        if(!Connectivity.isNetworkConnected(PrepaidServices.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }else
        {
            progressDialog.show();
            Call<AppResponse> call =  apiInterface.get_prepaid_servicelist("20");
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
                            prepaidservices=new ArrayList<>();
                            prepaidservices=appResponse.getResponseModel().getPrepaidServiceList();
                            adapter = new AdapterPrePaidServices(prepaidservices, PrepaidServices.this);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(PrepaidServices.this, LinearLayoutManager.VERTICAL, false);
                            rv_pre_services.setLayoutManager(layoutManager);
                            rv_pre_services.setAdapter(adapter);

                        } else if (appResponse.getResponseType().equals("300")) {
                            progressDialog.dismiss();
                            Toast.makeText(PrepaidServices.this, "internal server error" + "response code:" + appResponse.getResponseType(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(PrepaidServices.this, "internal server error" , Toast.LENGTH_SHORT).show();
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