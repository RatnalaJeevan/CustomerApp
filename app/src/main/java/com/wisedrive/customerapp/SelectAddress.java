package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wisedrive.customerapp.adapters.AdapterCustomerAdresses;
import com.wisedrive.customerapp.adapters.AdapterVehDetails;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.AppResponse;
import com.wisedrive.customerapp.pojos.PojoAddresses;
import com.wisedrive.customerapp.pojos.PojoVehDetails;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectAddress extends AppCompatActivity {

    TextView noresults;
    ImageView back;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    TextView add_address_enter;
    ArrayList<PojoAddresses> addresslist;
    AdapterCustomerAdresses adapter1;
    RecyclerView rv_added_addressses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address);
        SPHelper.sharedPreferenceInitialization(SelectAddress.this);
        addresslist=new ArrayList<PojoAddresses>();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        progressDialog = new ProgressDialog(SelectAddress.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        back=findViewById(R.id.back);
        add_address_enter=findViewById(R.id.add_address_enter);
        noresults=findViewById(R.id.noresults);
        rv_added_addressses=findViewById(R.id.rv_added_addressses);
        add_address_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SelectAddress.this,AddAddress.class);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SelectAddress.this,ScheduleService.class);
                startActivity(intent);
                finish();
            }
        });
        get_vehicle_details();
    }
    public  void get_vehicle_details(){
        if(!Connectivity.isNetworkConnected(SelectAddress.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }else
        {
            progressDialog.show();
            Call<AppResponse> call =  apiInterface.get_address_list(SPHelper.getSPData(SelectAddress.this, SPHelper.customer_id, ""));
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response)
                {
                    AppResponse appResponse = response.body();

                    if (response.body()!=null) {
                        assert appResponse != null;
                        if (appResponse.getResponseType().equals("200")) {
                            progressDialog.dismiss();
                           if(appResponse.getResponseModel().getAddressList().isEmpty()||appResponse.getResponseModel().getAddressList()==null){
                                noresults.setVisibility(View.VISIBLE);
                            }
                            addresslist = new ArrayList();
                            addresslist=appResponse.getResponseModel().getAddressList();
                            adapter1 = new AdapterCustomerAdresses(addresslist, SelectAddress.this);
                            LinearLayoutManager layoutManager1 = new LinearLayoutManager(SelectAddress.this, LinearLayoutManager.VERTICAL, false);
                            rv_added_addressses.setLayoutManager(layoutManager1);
                            rv_added_addressses.setAdapter(adapter1);

                            SelectAddress.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter1.notifyDataSetChanged();
                                }
                            });

                        } else if (appResponse.getResponseType().equals("300")) {
                            progressDialog.dismiss();
                            Toast.makeText(SelectAddress.this, "internal server error" + "response code:" + appResponse.getResponseType(), Toast.LENGTH_SHORT).show();

                        }
                    }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(SelectAddress.this, "internal server error" , Toast.LENGTH_SHORT).show();
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
    public  void check_address(){
        if(!Connectivity.isNetworkConnected(SelectAddress.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }else
        {
            progressDialog.show();
            Call<AppResponse> call =  apiInterface.check_address(adapter1.selected_pincode,SPHelper.veh_id);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response)
                {
                    AppResponse appResponse = response.body();

                    if (response.body()!=null)
                    {
                        assert appResponse != null;
                        if (appResponse.getResponseType().equals("200"))
                        {
                            progressDialog.dismiss();
                            System.out.println("Serving"+appResponse.getResponseModel().getGetservingdetails().getIs_serving());
                            String serving=appResponse.getResponseModel().getGetservingdetails().getIs_serving();
                             if(serving.equals("N"))
                            {
                                Toast.makeText(SelectAddress.this, "Sorry,We are not serving at this location" , Toast.LENGTH_SHORT).show();
                            }else {
                               Intent intent=new Intent(SelectAddress.this,ScheduleService.class);
                               SPHelper.is_serving="yes";
                               startActivity(intent);
                               finish();
                            }

                        } else if (appResponse.getResponseType().equals("300")) {
                            progressDialog.dismiss();
                            Toast.makeText(SelectAddress.this, "internal server error" + "response code:" + appResponse.getResponseType(), Toast.LENGTH_SHORT).show();

                        }
                    }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(SelectAddress.this, "internal server error" , Toast.LENGTH_SHORT).show();
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