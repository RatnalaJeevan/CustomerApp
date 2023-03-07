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
import android.widget.TextView;
import android.widget.Toast;

import com.wisedrive.customerapp.adapters.AdapterAddonServices;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.pojos.PojoAddOnServices;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddOnServices extends AppCompatActivity {
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    ArrayList<PojoAddOnServices> addOnServices;
    AdapterAddonServices adapter;
    public RecyclerView rv_addon_services;
    ImageView back;
    TextView amount_paid;
    public  double sum=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addon_services);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        progressDialog = new ProgressDialog(AddOnServices.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        amount_paid=findViewById(R.id.amount_paid);
        back=findViewById(R.id.back);
        addOnServices=new ArrayList<PojoAddOnServices>();
        rv_addon_services=findViewById(R.id.rv_addon_services);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AddOnServices.this, ServiceCompletedPage.class);
                startActivity(intent);
            }
        });
        get_addon_services();
    }
    public  void get_addon_services(){
        if(!Connectivity.isNetworkConnected(AddOnServices.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }else
        {
            progressDialog.show();
            Call<AppResponse> call =  apiInterface.get_addon_servicelist("20");
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
                            addOnServices=new ArrayList<>();
                            addOnServices=appResponse.getResponseModel().getPostpaidServiceList();
                            adapter = new AdapterAddonServices(addOnServices, AddOnServices.this);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(AddOnServices.this, LinearLayoutManager.VERTICAL, false);
                            rv_addon_services.setLayoutManager(layoutManager);
                            rv_addon_services.setAdapter(adapter);
                            for(int i=0;i<addOnServices.size();i++){
                               String finalamount=appResponse.getResponseModel().getPostpaidServiceList().get(i).getFinal_amount();
                                sum=sum+Double.parseDouble(finalamount);
                            }
                           String amountpaid= String .valueOf(sum);
                            amount_paid.setText(amountpaid);

                        } else if (appResponse.getResponseType().equals("300")) {
                            progressDialog.dismiss();
                            Toast.makeText(AddOnServices.this, "internal server error" + "response code:" + appResponse.getResponseType(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(AddOnServices.this, "internal server error" , Toast.LENGTH_SHORT).show();
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