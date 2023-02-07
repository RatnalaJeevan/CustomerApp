package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.wisedrive.customerapp.adapters.AdapterAddServiceList;
import com.wisedrive.customerapp.adapters.AdapterInvoicesList;
import com.wisedrive.customerapp.adapters.SlideAdapter;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.AppResponse;
import com.wisedrive.customerapp.pojos.PojoAddServiceList;
import com.wisedrive.customerapp.pojos.PojoInvoicesList;
import com.wisedrive.customerapp.pojos.PojoVehDetails;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdditionalServicePage extends AppCompatActivity implements ViewPager.OnPageChangeListener  {

    int current_position=0;
    ImageView iv_plus,iv_minus;
    RecyclerView rv_add_service_list;
    ArrayList<PojoAddServiceList> addServiceLists;
    public AdapterAddServiceList adapterAddServiceList;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    public  SlideAdapter adapter1;
    ViewPager view_pager_2;
    TabLayout indicator1;
    RelativeLayout rl_back;
    ArrayList<PojoVehDetails> vehdetailslist;
    ProgressBar progress,progress1;
    TextView noresults;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_service_page);
        SPHelper.sharedPreferenceInitialization(AdditionalServicePage.this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        progressDialog = new ProgressDialog(AdditionalServicePage.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        noresults=findViewById(R.id.noresults);
        vehdetailslist = new ArrayList<PojoVehDetails>();
        view_pager_2= findViewById(R.id.view_pager_2);
        indicator1=findViewById(R.id.indicator1);
        progress=findViewById(R.id.progress);
        progress1=findViewById(R.id.progress1);
        iv_plus=findViewById(R.id.iv_plus);
        iv_minus=findViewById(R.id.iv_minus);
        rl_back=findViewById(R.id.rl_back);
        rv_add_service_list= findViewById(R.id.rv_add_service_list);

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdditionalServicePage.this,CustomerProfile.class);
                startActivity(intent);
            }
        });

        get_vehicle_details();
    }

    public void onAddServiceList(View view) {
        SPHelper.from="req";
        SPHelper.get_customer_address="";
        SPHelper.is_serving="";
        SPHelper.veh_id = vehdetailslist.get(current_position).getVehicle_id();
        SPHelper.customer_name = vehdetailslist.get(current_position).getCustomer_name();
        SPHelper.customer_phoneno = vehdetailslist.get(current_position).getPhone_no();
        SPHelper.veh_model = vehdetailslist.get(current_position).getVehicle_model();
        SPHelper.veh_make = vehdetailslist.get(current_position).getVehicle_make();
        SPHelper.veh_valid_to = vehdetailslist.get(current_position).getValid_to();
        SPHelper.customer_veh_no = vehdetailslist.get(current_position).getValid_to();
        SPHelper.customer_veh_no=vehdetailslist.get(current_position).getVehicle_no();
        Intent intent=new Intent(AdditionalServicePage.this,ScheduleService.class);
        startActivity(intent);
    }

    public void on_upload_invoice(View view) {
        SPHelper.veh_id = vehdetailslist.get(current_position).getVehicle_id();
        Intent intent=new Intent(AdditionalServicePage.this, AddInvoice.class);
        startActivity(intent);
    }

    public void on_uploaded_invoices(View view) {
        SPHelper.veh_id = vehdetailslist.get(current_position).getVehicle_id();
        Intent intent=new Intent(AdditionalServicePage.this,UploadedInvoicesList.class);
        startActivity(intent);
    }

    public void add_servicelist(View view) {
        if(iv_plus.getVisibility()==View.VISIBLE){
            iv_plus.setVisibility(View.GONE);
            iv_minus.setVisibility(View.VISIBLE);
            rv_add_service_list.setVisibility(View.VISIBLE);
            SPHelper.veh_id=vehdetailslist.get(current_position).getVehicle_id();
            get_add_serviceList();
        }else {
            iv_plus.setVisibility(View.VISIBLE);
            iv_minus.setVisibility(View.GONE);
            rv_add_service_list.setVisibility(View.GONE);
            //get_add_serviceList();
        }
    }

    public  void get_vehicle_details(){
        if(!Connectivity.isNetworkConnected(AdditionalServicePage.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }else
        {
            progress.setVisibility(View.VISIBLE);

            Call<AppResponse> call =  apiInterface.get_veh_details(SPHelper.getSPData(AdditionalServicePage.this, SPHelper.customer_id, ""));
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response)
                {
                    AppResponse appResponse = response.body();

                    if (response.body()!=null)
                    {
                        assert appResponse != null;
                        if (appResponse.getResponseType().equals("200")) {
                            progress.setVisibility(View.GONE);
                            vehdetailslist = new ArrayList<>();
                            vehdetailslist=appResponse.getResponseModel().getVehicleList();
                            adapter1 = new SlideAdapter(vehdetailslist, AdditionalServicePage.this);
                            view_pager_2.setCurrentItem(0);
                            view_pager_2.setAdapter(adapter1);
                            view_pager_2.setOnPageChangeListener(AdditionalServicePage.this);
                            indicator1.setupWithViewPager(view_pager_2, true);
                            //get_add_serviceList();
                        } else if (appResponse.getResponseType().equals("300")) {
                            progress.setVisibility(View.GONE);
                            Toast.makeText(AdditionalServicePage.this, "internal server error" + "response code:" + appResponse.getResponseType(), Toast.LENGTH_SHORT).show();
                        }else{
                            progress.setVisibility(View.GONE);
                        }
                    }
                    else {
                        progress.setVisibility(View.GONE);
                        Toast.makeText(AdditionalServicePage.this, "internal server error" , Toast.LENGTH_SHORT).show();
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

    public  void get_add_serviceList(){
        if(!Connectivity.isNetworkConnected(AdditionalServicePage.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }else
        {

            Call<AppResponse> call =  apiInterface.get_AdditionalServiceList(SPHelper.veh_id,SPHelper.getSPData(AdditionalServicePage.this, SPHelper.customer_id, ""),
                    "1");
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response)
                {
                    AppResponse appResponse = response.body();
                    progress1.setVisibility(View.VISIBLE);
                    if (response.body()!=null) {
                        assert appResponse != null;
                        if (appResponse.getResponseType().equals("200"))
                        {
                            progress1.setVisibility(View.GONE);
                            addServiceLists=new ArrayList<>();
                            addServiceLists=appResponse.getResponseModel().getAdditionalServicesList();

                            if(addServiceLists.isEmpty()){
                                noresults.setVisibility(View.VISIBLE);
                                rv_add_service_list.setVisibility(View.GONE);
                            }else{
                                noresults.setVisibility(View.GONE);
                                rv_add_service_list.setVisibility(View.VISIBLE);
                                adapterAddServiceList = new AdapterAddServiceList(addServiceLists, AdditionalServicePage.this);
                                LinearLayoutManager layoutManager = new LinearLayoutManager(AdditionalServicePage.this, LinearLayoutManager.VERTICAL, false);
                                rv_add_service_list.setLayoutManager(layoutManager);
                                rv_add_service_list.setAdapter(adapterAddServiceList);
                                AdditionalServicePage.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapterAddServiceList.notifyDataSetChanged();
                                    }
                                });
                            }

                        } else if (appResponse.getResponseType().equals("300")) {
                            progress1.setVisibility(View.GONE);
                            Toast.makeText(AdditionalServicePage.this, "internal server error" + "response code:" + appResponse.getResponseType(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        progress1.setVisibility(View.GONE);
                        Toast.makeText(AdditionalServicePage.this, "internal server error" , Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AppResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    progress1.setVisibility(View.GONE);
                }
            });
        }
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        current_position=position;
    }

    @Override
    public void onPageSelected(int position) {
        current_position=position;
        iv_plus.setVisibility(View.VISIBLE);
        iv_minus.setVisibility(View.GONE);
        rv_add_service_list.setVisibility(View.GONE);
        noresults.setVisibility(View.GONE);
        SPHelper.veh_id=vehdetailslist.get(position).getVehicle_id();
       // get_add_serviceList();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(AdditionalServicePage.this,CustomerProfile.class);
        startActivity(intent);
    }
}