package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wisedrive.customerapp.adapters.AdapterServiceStatus;
import com.wisedrive.customerapp.adapters.AdapterTrackList;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.AppResponse;
import com.wisedrive.customerapp.pojos.PojoAdditionalService;
import com.wisedrive.customerapp.pojos.PojoServiceflow;
import com.wisedrive.customerapp.pojos.PojoServices;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceCompletedPage extends AppCompatActivity {
    ImageView add_services_included,minus_services_included,minus_track,add_track;
    TextView customer_name,customer_phoneno,customer_vehname,customer_vehno,warranty_exp_date,service_centrename;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    RelativeLayout rl_prepaid_service,rl_addon_services,rl_contact,rl_feedback,rl_label,rl_back;
    RelativeLayout rl_lostitems,rl_contact_support,rl_wisedrive_contact,rl_transparent,rl_wisedrive_email;
    TextView service_completed_date;
    ImageView back;
    RecyclerView rv_all_services,rv_track_list;
    CardView cv_service_track;
    AdapterTrackList adapterTrackList;
    ArrayList<PojoServices> servicelist;
    ArrayList<PojoServiceflow> serviceflowlist;
    AdapterServiceStatus adapter1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_completed);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        progressDialog = new ProgressDialog(ServiceCompletedPage.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        back=findViewById(R.id.back);
        rl_back=findViewById(R.id.rl_back);
        cv_service_track=(CardView)findViewById(R.id.cv_service_track);
        rl_label=findViewById(R.id.rl_label);
        rv_track_list=findViewById(R.id.rv_track_list);
        rv_all_services=findViewById(R.id.rv_all_services);
        add_services_included=findViewById(R.id.add_services_included);
        minus_services_included=findViewById(R.id.minus_services_included);
        add_track=findViewById(R.id.add_track);
        minus_track=findViewById(R.id.minus_track);
        service_completed_date=findViewById(R.id.service_completed_date);
        rl_addon_services=findViewById(R.id.rl_addon_services);
        rl_prepaid_service=findViewById(R.id.rl_prepaid_service);
        rl_lostitems= findViewById(R.id.rl_lostitems);
        rl_feedback= findViewById(R.id.rl_feedback);
        rl_contact= findViewById(R.id.rl_contact);
        rl_contact_support= findViewById(R.id.rl_contact_support);
        rl_wisedrive_contact= findViewById(R.id.rl_wisedrive_contact);
        rl_wisedrive_email= findViewById(R.id.rl_wisedrive_email);
        rl_transparent= findViewById(R.id.rl_transparent);
        customer_name= findViewById(R.id.customer_name);
        customer_phoneno= findViewById(R.id.customer_phoneno);
        customer_vehname= findViewById(R.id.customer_vehname);
        customer_vehno= findViewById(R.id.customer_vehno);
        warranty_exp_date= findViewById(R.id.warranty_exp_date);
        customer_name.setText(SPHelper.customer_name);
        customer_phoneno.setText(SPHelper.customer_phoneno);
        customer_vehname.setText(SPHelper.veh_make+" "+SPHelper.veh_model);
        customer_vehno.setText(SPHelper.customer_veh_no);
        warranty_exp_date.setText("Expires on"+" "+SPHelper.veh_valid_to);
        rl_addon_services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ServiceCompletedPage.this,AddOnServices.class);
                startActivity(intent);
            }
        });
        rl_prepaid_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ServiceCompletedPage.this,PrepaidServices.class);
                startActivity(intent);
            }
        });
        rl_lostitems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ServiceCompletedPage.this,SubmitMissingItems.class);
                startActivity(intent);
            }
        });
        rl_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_contact_support.setVisibility(View.VISIBLE);
            }
        });
        rl_wisedrive_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+SPHelper.getSPData(ServiceCompletedPage.this, SPHelper.customer_support_phoneno, "")));
                startActivity(callIntent);
                finish();
            }
        });
        rl_wisedrive_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + SPHelper.getSPData(ServiceCompletedPage.this, SPHelper.customer_support_email, "")));
                startActivity(intent);
            }
        });
        rl_transparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_contact_support.setVisibility(View.GONE);
            }
        });
        rl_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ServiceCompletedPage.this,CustomerFeedBack.class);
                startActivity(intent);
            }
        });
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SPHelper.by.equals("add")){
                    Intent intent=new Intent(ServiceCompletedPage.this,AdditionalServicePage.class);
                    startActivity(intent);
                }
                else{
                    Intent intent=new Intent(ServiceCompletedPage.this,VehiclePackageDetails.class);
                    startActivity(intent);
                }
            }
        });
        if(SPHelper.by.equals("add")){
            rl_label.setVisibility(View.GONE);
            get_Add_services_details();
        }else{
            get_services_details();
            rl_label.setVisibility(View.VISIBLE);
        }
        get_service_completion_date();
    }
    public  void get_service_completion_date(){
        if(!Connectivity.isNetworkConnected(ServiceCompletedPage.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }else
        {
            progressDialog.show();
            Call<AppResponse> call =  apiInterface.get_servicecompletion_date(SPHelper.service_id);
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
                           String completed_date=appResponse.getResponseModel().getServicecompletiondate().getService_completed_date();
                            service_completed_date.setText(completed_date);
                            String postpaid_count=appResponse.getResponseModel().getServicecompletiondate().getPostpaid_service_count();
                            String prepaid_count=appResponse.getResponseModel().getServicecompletiondate().getPrepaid_service_count();
                            if(postpaid_count.equals("0") ||postpaid_count==null){
                                rl_addon_services.setVisibility(View.GONE);
                            }else{
                                rl_addon_services.setVisibility(View.VISIBLE);
                            }
                            if(prepaid_count.equals("0")||postpaid_count==null){
                                rl_prepaid_service.setVisibility(View.GONE);
                            }else{
                                rl_prepaid_service.setVisibility(View.VISIBLE);
                            }

                        } else if (appResponse.getResponseType().equals("300")) {
                            progressDialog.dismiss();
                            Toast.makeText(ServiceCompletedPage.this, "internal server error" + "response code:" + appResponse.getResponseType(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(ServiceCompletedPage.this, "internal server error" , Toast.LENGTH_SHORT).show();
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

    private void get_services_details() {
        if (!Connectivity.isNetworkConnected(ServiceCompletedPage.this)) {
            Toast.makeText(ServiceCompletedPage.this, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
            return;
        } else {
            progressDialog.show();
            PojoServices post1 = new PojoServices(SPHelper.service_id,SPHelper.package_id);
            Call<AppResponse> call = apiInterface.get_service_details(post1);
            call.enqueue(new Callback<AppResponse>() {
                @SuppressLint({"ResourceAsColor", "UseCompatLoadingForDrawables"})
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                    System.out.print(response.body());
                    if (response.body() != null)
                    {
                        AppResponse data = response.body();
                        if (data.getResponseType().equalsIgnoreCase("200"))
                        {
                            servicelist = new ArrayList();
                            servicelist=data.getResponseModel().getServiceincludes();
                            adapter1 = new AdapterServiceStatus(servicelist, ServiceCompletedPage.this);
                            LinearLayoutManager layoutManager1 = new LinearLayoutManager(ServiceCompletedPage.this, LinearLayoutManager.VERTICAL,false);
                            rv_all_services.setLayoutManager(layoutManager1);
                            rv_all_services.setAdapter(adapter1);

                            ServiceCompletedPage.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter1.notifyDataSetChanged();
                                }
                            });

                            String happycode=data.getResponseModel().getGethappycode().getHappy_code();

                            serviceflowlist=new ArrayList<>();
                            serviceflowlist=data.getResponseModel().getServiceflow();
                            if(serviceflowlist.isEmpty()){

                            }else{
                                adapterTrackList = new AdapterTrackList(serviceflowlist, ServiceCompletedPage.this);
                                LinearLayoutManager layoutManager = new LinearLayoutManager(ServiceCompletedPage.this, LinearLayoutManager.VERTICAL, false);
                                rv_track_list.setLayoutManager(layoutManager);
                                rv_track_list.setAdapter(adapterTrackList);
                                ServiceCompletedPage.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapterTrackList.notifyDataSetChanged();
                                    }
                                });
                            }

                        } else {
                            Toast.makeText(ServiceCompletedPage.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<AppResponse> call, Throwable th) {
                    Toast.makeText(ServiceCompletedPage.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }
    }

    private void get_Add_services_details() {
        if (!Connectivity.isNetworkConnected(ServiceCompletedPage.this)) {
            Toast.makeText(ServiceCompletedPage.this, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
            return;
        } else {
            progressDialog.show();
            PojoAdditionalService post1 = new PojoAdditionalService(SPHelper.service_id);
            Call<AppResponse> call = apiInterface.getAddServiceDetails(post1);
            call.enqueue(new Callback<AppResponse>() {
                @SuppressLint({"ResourceAsColor", "UseCompatLoadingForDrawables"})
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                    System.out.print(response.body());
                    if (response.body() != null)
                    {
                        AppResponse data = response.body();
                        if (data.getResponseType().equalsIgnoreCase("200"))
                        {

                            serviceflowlist=new ArrayList<>();
                            serviceflowlist=data.getResponseModel().getServiceflow();
                            if(serviceflowlist.isEmpty()){

                            }else{
                                adapterTrackList = new AdapterTrackList(serviceflowlist, ServiceCompletedPage.this);
                                LinearLayoutManager layoutManager = new LinearLayoutManager(ServiceCompletedPage.this, LinearLayoutManager.VERTICAL, false);
                                rv_track_list.setLayoutManager(layoutManager);
                                rv_track_list.setAdapter(adapterTrackList);
                                ServiceCompletedPage.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapterTrackList.notifyDataSetChanged();
                                    }
                                });
                            }
                        } else {
                            Toast.makeText(ServiceCompletedPage.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<AppResponse> call, Throwable th) {
                    Toast.makeText(ServiceCompletedPage.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }
    }
    @Override
    public void onBackPressed()
    {
        if(rl_contact_support.getVisibility()==View.VISIBLE){
            rl_contact_support.setVisibility(View.GONE);
        }else if(SPHelper.by.equals("add")){
            Intent intent=new Intent(ServiceCompletedPage.this,AdditionalServicePage.class);
            startActivity(intent);
        }
        else{
            Intent intent=new Intent(ServiceCompletedPage.this,VehiclePackageDetails.class);
            startActivity(intent);
        }
    }

    public void onservices_include(View view) {

        if(add_services_included.getVisibility()==View.VISIBLE){
            add_services_included.setVisibility(View.GONE);
            minus_services_included.setVisibility(View.VISIBLE);
            rv_all_services.setVisibility(View.VISIBLE);
        }else {
            add_services_included.setVisibility(View.VISIBLE);
            minus_services_included.setVisibility(View.GONE);
            rv_all_services.setVisibility(View.GONE);
        }
    }

    public void ontrack_include(View view) {
        if(add_track.getVisibility()==View.VISIBLE){
            add_track.setVisibility(View.GONE);
            minus_track.setVisibility(View.VISIBLE);
            cv_service_track.setVisibility(View.VISIBLE);
        }else {
            add_track.setVisibility(View.VISIBLE);
            minus_track.setVisibility(View.GONE);
            cv_service_track.setVisibility(View.GONE);
        }
    }
}