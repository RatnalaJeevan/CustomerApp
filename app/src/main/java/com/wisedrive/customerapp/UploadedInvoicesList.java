package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wisedrive.customerapp.adapters.AdapterInvoicesList;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.pojos.PojoInvoicesList;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadedInvoicesList extends AppCompatActivity {
    RecyclerView rv_invoices_list;
    ArrayList<PojoInvoicesList> invoicesLists;
    public AdapterInvoicesList adapterInvoicesList;
    RelativeLayout rl_back;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    TextView noresults;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploaded_invoices_list);
        SPHelper.sharedPreferenceInitialization(UploadedInvoicesList.this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        progressDialog = new ProgressDialog(UploadedInvoicesList.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progress=findViewById(R.id.progress);
        rl_back=findViewById(R.id.rl_back);
        noresults=findViewById(R.id.noresults);
        rv_invoices_list= findViewById(R.id.rv_invoices_list);

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        get_invoiceList();
    }

    public  void get_invoiceList(){
        if(!Connectivity.isNetworkConnected(UploadedInvoicesList.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }else
        {

            Call<AppResponse> call =  apiInterface.get_uploadedinvoiceList(SPHelper.veh_id,SPHelper.getSPData(UploadedInvoicesList.this, SPHelper.customer_id, ""));
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response)
                {
                    AppResponse appResponse = response.body();
                    progress.setVisibility(View.VISIBLE);
                    if (response.body()!=null) {
                        assert appResponse != null;
                        if (appResponse.getResponseType().equals("200")) {
                            progress.setVisibility(View.GONE);
                            invoicesLists=new ArrayList<>();
                            invoicesLists=appResponse.getResponseModel().getUploadedInvoice();
                            if(invoicesLists.isEmpty()){
                                noresults.setVisibility(View.VISIBLE);
                                rv_invoices_list.setVisibility(View.GONE);
                            }else {
                                noresults.setVisibility(View.GONE);
                                rv_invoices_list.setVisibility(View.VISIBLE);
                                adapterInvoicesList= new AdapterInvoicesList(invoicesLists, UploadedInvoicesList.this);
                                GridLayoutManager layoutManager = new GridLayoutManager(UploadedInvoicesList.this, 2);
                                rv_invoices_list.setLayoutManager(layoutManager);
                                rv_invoices_list.setAdapter(adapterInvoicesList);
                                UploadedInvoicesList.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapterInvoicesList.notifyDataSetChanged();
                                    }
                                });
                            }
                        } else if (appResponse.getResponseType().equals("300")) {
                            progress.setVisibility(View.GONE);
                            Toast.makeText(UploadedInvoicesList.this, "internal server error" + "response code:" + appResponse.getResponseType(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        progress.setVisibility(View.GONE);
                        Toast.makeText(UploadedInvoicesList.this, "internal server error" , Toast.LENGTH_SHORT).show();
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