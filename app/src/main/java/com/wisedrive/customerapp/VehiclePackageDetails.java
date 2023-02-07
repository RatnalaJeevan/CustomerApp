package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.wisedrive.customerapp.adapters.AdapterPackageDetails;
import com.wisedrive.customerapp.adapters.AdapterRSAPackageDetails;
import com.wisedrive.customerapp.adapters.AdapterVehDetails;
import com.wisedrive.customerapp.adapters.SlideAdapter;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.AppResponse;
import com.wisedrive.customerapp.pojos.PojoPackageList;
import com.wisedrive.customerapp.pojos.PojoPeriodicMaintenanceServices;
import com.wisedrive.customerapp.pojos.PojoRsa;
import com.wisedrive.customerapp.pojos.PojoVehDetails;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VehiclePackageDetails extends AppCompatActivity  implements ViewPager.OnPageChangeListener {

    TextView[] dots;
    TabLayout indicator1;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    RelativeLayout rl_warranty,rl_profile;
    TextView warranty_expiry_date,veh_exp_kms;
    LinearLayout dots_container;
    ProgressBar progress;
    ViewPager view_pager_2;
    View tv8,tv9;
    public String isselect="pm";
    CardView label11,label12;
    ArrayList<PojoPackageList> pmlist;
    ArrayList<PojoPackageList> rsalist;
    ArrayList<PojoPackageList> pmpservicelist;
    ArrayList<PojoVehDetails> vehdetailslist;
    public  SlideAdapter adapter1;
   public AdapterPackageDetails adapter;
    AdapterRSAPackageDetails rsaadapter;
    public ImageView customer_profile_page;
    HorizontalScrollView hsv_veh_details;
    public RecyclerView rv_pmpservices,rv_veh_details,rsa_pmpservices;
    TextView rsa,periodic_maintenance,request_additional;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_package_details);
        SPHelper.sharedPreferenceInitialization(VehiclePackageDetails.this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        progressDialog = new ProgressDialog(VehiclePackageDetails.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        vehdetailslist = new ArrayList<PojoVehDetails>();
        pmpservicelist=new ArrayList<PojoPackageList>();
        pmlist = new ArrayList<PojoPackageList>();
        rsalist=new ArrayList<PojoPackageList>();
        rl_profile=findViewById(R.id.rl_profile);
        progress=findViewById(R.id.progress);
        request_additional= findViewById(R.id.request_additional);
        rsa_pmpservices=findViewById(R.id.rsa_pmpservices);
        customer_profile_page=findViewById(R.id.customer_profile_page);
        indicator1=findViewById(R.id.indicator1);
        view_pager_2= findViewById(R.id.view_pager_2);
        rl_warranty= findViewById(R.id.rl_warranty);
        rv_pmpservices =  findViewById(R.id.rv_pmpservices);
        warranty_expiry_date =  findViewById(R.id.warranty_expiry_date);
        veh_exp_kms =  findViewById(R.id.veh_exp_kms);
        tv8=findViewById(R.id.tv8);

        /*periodic_maintenance =  findViewById(R.id.periodic_maintenance);
        rsa =  findViewById(R.id.rsa);*/


        rl_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(VehiclePackageDetails.this,CustomerProfile.class);
                startActivity(intent);
            }
        });
        rl_warranty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(VehiclePackageDetails.this,EngineTransmissionWarranty.class);
                startActivity(intent);
            }
        });
        request_additional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.from="req";
                Intent intent=new Intent(VehiclePackageDetails.this,ScheduleService.class);
                startActivity(intent);
            }
        });
        get_vehicle_details();
    }

    public  void get_vehicle_details(){
        if(!Connectivity.isNetworkConnected(VehiclePackageDetails.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }else
        {
            
            Call<AppResponse> call =  apiInterface.get_veh_details(SPHelper.getSPData(VehiclePackageDetails.this, SPHelper.customer_id, ""));
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
                            vehdetailslist = new ArrayList<>();
                            vehdetailslist=appResponse.getResponseModel().getVehicleList();
                            adapter1 = new SlideAdapter(vehdetailslist, VehiclePackageDetails.this);
                            view_pager_2.setCurrentItem(0);
                            view_pager_2.setAdapter(adapter1);
                            view_pager_2.setOnPageChangeListener(VehiclePackageDetails.this);
                            indicator1.setupWithViewPager(view_pager_2, true);
                            post_veh_data();

                        } else if (appResponse.getResponseType().equals("300")) {
                            progress.setVisibility(View.GONE);
                            Toast.makeText(VehiclePackageDetails.this, "internal server error" + "response code:" + appResponse.getResponseType(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        progress.setVisibility(View.GONE);
                        Toast.makeText(VehiclePackageDetails.this, "internal server error" , Toast.LENGTH_SHORT).show();
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

    private void post_veh_data()
    {
        if (!Connectivity.isNetworkConnected(VehiclePackageDetails.this)) {
            Toast.makeText(VehiclePackageDetails.this, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
            return;
        } else {
            progress.setVisibility(View.VISIBLE);
            PojoPeriodicMaintenanceServices postmodel = new PojoPeriodicMaintenanceServices(SPHelper.veh_id,SPHelper.getSPData(VehiclePackageDetails.this, SPHelper.customer_id, ""));
            Call<AppResponse> call = apiInterface.post_veh_details(postmodel);
            call.enqueue(new Callback<AppResponse>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                    System.out.print(response.body());
                    if (response.body() != null) {
                        AppResponse data = response.body();
                        if (data.getResponseType().equalsIgnoreCase("200")) {
                            progress.setVisibility(View.GONE);
                            if(data.getResponseModel().getWarrantydetails().getExpires_on_kms()==null||data.getResponseModel().getWarrantydetails().getExpires_on_kms().equals("null")){
                                veh_exp_kms.setText("");
                            }else if(data.getResponseModel().getWarrantydetails().getExpires_on_kms().isEmpty()){
                                veh_exp_kms.setText("");
                            }else{
                                double exp_kms= Double.parseDouble(data.getResponseModel().getWarrantydetails().getExpires_on_kms());
                                int x=(int)exp_kms;
                                String s = NumberFormat.getIntegerInstance().format(x);
                                veh_exp_kms.setText(s+" "+"km");
                            }
                            warranty_expiry_date.setText(data.getResponseModel().getWarrantydetails().getExpires_on_date());
                            SPHelper.customer_veh_no=data.getResponseModel().getWarrantydetails().getVehicle_no();
                            pmpservicelist=new ArrayList<PojoPackageList>();
                            rsalist=new ArrayList<PojoPackageList>();
                            pmlist=new ArrayList<PojoPackageList>();
                            pmpservicelist=data.getResponseModel().getPackagelist();

                            adapter = new AdapterPackageDetails(pmpservicelist, getApplicationContext());
                            LinearLayoutManager layoutManager = new LinearLayoutManager(VehiclePackageDetails.this, LinearLayoutManager.VERTICAL, false);
                            rv_pmpservices.setLayoutManager(layoutManager);
                            rv_pmpservices.setAdapter(adapter);
//                            for (int i=0;i<pmpservicelist.size();i++){
//
//                                if (pmpservicelist.get(i).getPackage_type().equalsIgnoreCase("RSA"))
//                                {
//                                    PojoPackageList model = new PojoPackageList();
//                                    model.setPackage_name(pmpservicelist.get(i).getPackage_name());
//                                    model.setIcon_url(pmpservicelist.get(i).getIcon_url());
//                                    model.setPackage_id(pmpservicelist.get(i).getPackage_id());
//                                    model.setStatus_name(pmpservicelist.get(i).getStatus_name());
//                                    model.setStatus_id(pmpservicelist.get(i).getStatus_id());
//                                    model.setService_id(pmpservicelist.get(i).getService_id());
//                                    rsalist.add(model);
//                                }
//                              else {
//                                    PojoPackageList model1 = new PojoPackageList();
//                                    model1.setPackage_name(pmpservicelist.get(i).getPackage_name());
//                                    model1.setIcon_url(pmpservicelist.get(i).getIcon_url());
//                                    model1.setPackage_id(pmpservicelist.get(i).getPackage_id());
//                                    model1.setStatus_name(pmpservicelist.get(i).getStatus_name());
//                                    model1.setStatus_id(pmpservicelist.get(i).getStatus_id());
//                                    model1.setService_id(pmpservicelist.get(i).getService_id());
//                                    pmlist.add(model1);
//                                }
//                            }

                            /*adapter = new AdapterPackageDetails(pmlist, getApplicationContext());
                            LinearLayoutManager layoutManager = new LinearLayoutManager(VehiclePackageDetails.this, LinearLayoutManager.VERTICAL, false);
                            rv_pmpservices.setLayoutManager(layoutManager);
                            rv_pmpservices.setAdapter(adapter);*/

//                            rsaadapter = new AdapterRSAPackageDetails(rsalist, getApplicationContext());
//                            LinearLayoutManager layoutManager1 = new LinearLayoutManager(VehiclePackageDetails.this, LinearLayoutManager.VERTICAL, false);
//                            rsa_pmpservices.setLayoutManager(layoutManager1);
//                            rsa_pmpservices.setAdapter(rsaadapter);
/*
                            if(isselect.equalsIgnoreCase("rsa")){
                                rsa_pmpservices.setVisibility(View.VISIBLE);
                                rv_pmpservices.setVisibility(View.GONE);

                            }else {
                                rsa_pmpservices.setVisibility(View.GONE);
                                rv_pmpservices.setVisibility(View.VISIBLE);
                            }*/

                            VehiclePackageDetails.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                    //rsaadapter.notifyDataSetChanged();
                                }
                            });

                        } else {
                            progress.setVisibility(View.GONE);
                            Toast.makeText(VehiclePackageDetails.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    progress.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<AppResponse> call, Throwable th) {
                    Toast.makeText(VehiclePackageDetails.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    progress.setVisibility(View.GONE);
                }
            });
        }
    }

    public  void  onselectrsa(View view) {
        periodic_maintenance.setTextColor(Color.parseColor("#D3D3D3"));
        rsa.setTextColor(Color.parseColor("#0619c3"));
        tv8.setBackgroundColor(Color.parseColor("#C7C7C7"));
        tv9.setBackgroundColor(Color.parseColor("#0619c3"));
        isselect="rsa";
        post_veh_data();

    }

    public  void  onselectpmpservice(View view){
        periodic_maintenance.setTextColor(Color.parseColor("#0619c3"));
        rsa.setTextColor(Color.parseColor("#D3D3D3"));
        tv9.setBackgroundColor(Color.parseColor("#C7C7C7"));
        tv8.setBackgroundColor(Color.parseColor("#0619c3"));
        isselect="pm";
        post_veh_data();
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
    {
        SPHelper.veh_id = vehdetailslist.get(position).getVehicle_id();
        SPHelper.customer_name = vehdetailslist.get(position).getCustomer_name();
        SPHelper.customer_phoneno = vehdetailslist.get(position).getPhone_no();
        SPHelper.veh_model = vehdetailslist.get(position).getVehicle_model();
        SPHelper.veh_make = vehdetailslist.get(position).getVehicle_make();
        SPHelper.veh_valid_to = vehdetailslist.get(position).getValid_to();
        SPHelper.customer_veh_no=vehdetailslist.get(position).getVehicle_no();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    @Override
    public void onPageSelected(int position) {
            SPHelper.veh_id = vehdetailslist.get(position).getVehicle_id();
            SPHelper.customer_name = vehdetailslist.get(position).getCustomer_name();
            SPHelper.customer_phoneno = vehdetailslist.get(position).getPhone_no();
            SPHelper.veh_model = vehdetailslist.get(position).getVehicle_model();
            SPHelper.veh_make = vehdetailslist.get(position).getVehicle_make();
            SPHelper.veh_valid_to = vehdetailslist.get(position).getValid_to();
            SPHelper.customer_veh_no=vehdetailslist.get(position).getVehicle_no();
            post_veh_data();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}