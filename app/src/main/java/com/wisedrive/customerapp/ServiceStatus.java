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
import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.pojos.PojoAdditionalService;
import com.wisedrive.customerapp.pojos.PojoServiceflow;
import com.wisedrive.customerapp.pojos.PojoServices;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceStatus extends AppCompatActivity {

    CardView cv_service_track;
    ImageView add_services_included,minus_services_included,minus_track,add_track;
    TextView customer_name,customer_phoneno,customer_vehname,tv_comments,entered_comments,
            customer_vehno,warranty_exp_date,req_date,service_type;
    TextView status1,status2,status3,status4,code_4,code_3,code_2,code_1;
    RelativeLayout rl_stage1,rl_stage2,rl_stage3,rl_stage4,rl_label,rl_back,comments;
    View line1,line2,line3;
    public ImageView back,contact_support;
    RelativeLayout rl_contact_support,rl_wisedrive_contact,rl_transparent1;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    ArrayList<PojoServices> servicelist;
    ArrayList<PojoServiceflow> serviceflowlist;
    AdapterServiceStatus adapter1;
    RecyclerView rv_all_services,rv_track_list;
    AdapterTrackList adapterTrackList;
    private  String customer_support_name,customer_support_no;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_status);
        servicelist=new ArrayList<PojoServices>();
        serviceflowlist=new ArrayList<PojoServiceflow>();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        progressDialog = new ProgressDialog(ServiceStatus.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        comments=findViewById(R.id.comments);
        rl_back=findViewById(R.id.rl_back);
        rl_label=findViewById(R.id.rl_label);
        cv_service_track=(CardView)findViewById(R.id.cv_service_track);
        tv_comments= findViewById(R.id.tv_comments);
        entered_comments= findViewById(R.id.entered_comments);
        status1= findViewById(R.id.status1);
        status2= findViewById(R.id.status2);
        status3= findViewById(R.id.status3);
        status4= findViewById(R.id.status4);
        rl_stage1=findViewById(R.id.rl_stage1);
        rl_stage2=findViewById(R.id.rl_stage2);
        rl_stage3=findViewById(R.id.rl_stage3);
        rl_stage4=findViewById(R.id.rl_stage4);
        code_1= findViewById(R.id.code_1);
        code_2= findViewById(R.id.code_2);
        code_3= findViewById(R.id.code_3);
        code_4= findViewById(R.id.code_4);
        line1=(View)findViewById(R.id.line1);
        line2=(View)findViewById(R.id.line2);
        line3=(View)findViewById(R.id.line3);
        back=findViewById(R.id.back);
        add_services_included=findViewById(R.id.add_services_included);
        minus_services_included=findViewById(R.id.minus_services_included);
        add_track=findViewById(R.id.add_track);
        minus_track=findViewById(R.id.minus_track);
        contact_support=findViewById(R.id.contact_support);
        rl_transparent1=findViewById(R.id.rl_transparent1);
        rl_contact_support=findViewById(R.id.rl_contact_support);
        rl_wisedrive_contact=findViewById(R.id.rl_wisedrive_contact);
        rv_track_list=findViewById(R.id.rv_track_list);
        rv_all_services=findViewById(R.id.rv_all_services);
        req_date=findViewById(R.id.req_date);
        service_type=findViewById(R.id.service_type);
        customer_name= findViewById(R.id.customer_name);
        customer_phoneno= findViewById(R.id.customer_phoneno);
        customer_vehname= findViewById(R.id.customer_vehname);
        customer_vehno= findViewById(R.id.customer_vehno);
        warranty_exp_date= findViewById(R.id.warranty_exp_date);
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        System.out.println("vehid"+SPHelper.veh_id);
        customer_name.setText(SPHelper.customer_name);
        customer_phoneno.setText(SPHelper.customer_phoneno);
        customer_vehname.setText(SPHelper.veh_make+" "+SPHelper.veh_model);
        customer_vehno.setText(SPHelper.customer_veh_no);
        warranty_exp_date.setText("Expires on"+" "+SPHelper.veh_valid_to);
        service_type.setText(SPHelper.package_name);

        if(SPHelper.by.equals("add")){

            rl_label.setVisibility(View.GONE);
            get_Add_services_details();
        }else{
            comments.setVisibility(View.GONE);
            tv_comments.setVisibility(View.GONE);
            get_services_details();
            rl_label.setVisibility(View.VISIBLE);
        }
        contact_support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_contact_support.setVisibility(View.VISIBLE);
            }
        });
        rl_wisedrive_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+customer_support_no));
                startActivity(callIntent);
            }
        });
        rl_transparent1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                rl_contact_support.setVisibility(View.GONE);
            }
        });

    }
    //post_method
    private void get_services_details() {
        if (!Connectivity.isNetworkConnected(ServiceStatus.this)) {
            Toast.makeText(ServiceStatus.this, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
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
                            adapter1 = new AdapterServiceStatus(servicelist, ServiceStatus.this);
                            LinearLayoutManager layoutManager1 = new LinearLayoutManager(ServiceStatus.this, LinearLayoutManager.VERTICAL,false);
                            rv_all_services.setLayoutManager(layoutManager1);
                            rv_all_services.setAdapter(adapter1);

                            ServiceStatus.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter1.notifyDataSetChanged();
                                }
                            });
                             customer_support_name=data.getResponseModel().getSupportdetails().getCustomer_support_name();
                             customer_support_no=data.getResponseModel().getSupportdetails().getCustomer_support_phone_no();
                             String happycode=data.getResponseModel().getGethappycode().getHappy_code();

                            if(happycode==null ||happycode.equals("null")||happycode.isEmpty()){

                            }else{
                                code_1.setText(happycode.substring(0,1));
                                code_2.setText(happycode.substring(1,2));
                                code_3.setText(happycode.substring(2,3));
                                code_4.setText(happycode.substring(3,4));
                            }

                            serviceflowlist=new ArrayList<>();
                            serviceflowlist=data.getResponseModel().getServiceflow();
                            if(serviceflowlist.isEmpty()){

                            }else{
                                adapterTrackList = new AdapterTrackList(serviceflowlist, ServiceStatus.this);
                                LinearLayoutManager layoutManager = new LinearLayoutManager(ServiceStatus.this, LinearLayoutManager.VERTICAL, false);
                                rv_track_list.setLayoutManager(layoutManager);
                                rv_track_list.setAdapter(adapterTrackList);
                                ServiceStatus.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapterTrackList.notifyDataSetChanged();
                                    }
                                });
                            }
                            /*for(int i=0;i<serviceflowlist.size();i++)
                            {
                                String status_id= data.getResponseModel().getServiceflow().get(i).getStatus_id();
                                req_date.setText(data.getResponseModel().getServiceflow().get(i).getService_date());
                                System.out.println("Statusid"+status_id);
                                if(status_id.equals("1")){
                                    status1.setTextColor(Color.parseColor("#FF6739"));
                                    rl_stage1.setBackground(getDrawable(circle_orange));
                                    status2.setTextColor(Color.parseColor("#D3D3D3"));
                                    rl_stage2.setBackground(getDrawable(circle_lightgrey));
                                    line1.setBackgroundColor(Color.parseColor("#D3D3D3"));
                                    status3.setTextColor(Color.parseColor("#D3D3D3"));
                                    rl_stage3.setBackgroundResource( circle_lightgrey);
                                    line2.setBackgroundColor(Color.parseColor("#D3D3D3"));
                                    status4.setTextColor(Color.parseColor("#D3D3D3"));
                                    rl_stage4.setBackground(getDrawable(circle_lightgrey));
                                    line3.setBackgroundColor(Color.parseColor("#D3D3D3"));
                                }
                                 if(status_id.equals("2")){
                                     status1.setTextColor(Color.parseColor("#FF6739"));
                                     rl_stage1.setBackground(getDrawable(circle_orange));
                                     status3.setTextColor(Color.parseColor("#FF6739"));
                                     rl_stage2.setBackground(getDrawable(circle_orange));
                                     line1.setBackgroundColor(Color.parseColor("#FF6739"));
                                     status2.setTextColor(Color.parseColor("#D3D3D3"));
                                     rl_stage3.setBackgroundResource( circle_lightgrey);
                                     line2.setBackgroundColor(Color.parseColor("#D3D3D3"));
                                     status4.setTextColor(Color.parseColor("#D3D3D3"));
                                     rl_stage4.setBackground(getDrawable(circle_lightgrey));
                                     line3.setBackgroundColor(Color.parseColor("#D3D3D3"));
                                 }
                                 if(status_id.equals("3")){
                                     status1.setTextColor(Color.parseColor("#FF6739"));
                                     rl_stage1.setBackground(getDrawable(circle_orange));
                                     status2.setTextColor(Color.parseColor("#FF6739"));
                                     rl_stage2.setBackground(getDrawable(circle_orange));
                                     line1.setBackgroundColor(Color.parseColor("#FF6739"));
                                     status3.setTextColor(Color.parseColor("#FF6739"));
                                     rl_stage3.setBackgroundResource( circle_orange);
                                     line2.setBackgroundColor(Color.parseColor("#FF6739"));
                                     status4.setTextColor(Color.parseColor("#D3D3D3"));
                                     rl_stage4.setBackground(getDrawable(circle_lightgrey));
                                     line3.setBackgroundColor(Color.parseColor("#D3D3D3"));
                                 }
                                 if(status_id.equals("4")){
                                     status1.setTextColor(Color.parseColor("#FF6739"));
                                     rl_stage1.setBackground(getDrawable(circle_orange));
                                     status2.setTextColor(Color.parseColor("#FF6739"));
                                     rl_stage2.setBackground(getDrawable(circle_orange));
                                     line1.setBackgroundColor(Color.parseColor("#FF6739"));
                                     status3.setTextColor(Color.parseColor("#FF6739"));
                                     rl_stage3.setBackgroundResource( circle_orange);
                                     line2.setBackgroundColor(Color.parseColor("#FF6739"));
                                     status4.setTextColor(Color.parseColor("#FF6739"));
                                     rl_stage4.setBackground(getDrawable(circle_orange));
                                     line3.setBackgroundColor(Color.parseColor("#FF6739"));
                                 }
                             }*/

                        } else {
                            Toast.makeText(ServiceStatus.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<AppResponse> call, Throwable th) {
                    Toast.makeText(ServiceStatus.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }
    }

    private void get_Add_services_details() {
        if (!Connectivity.isNetworkConnected(ServiceStatus.this)) {
            Toast.makeText(ServiceStatus.this, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
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

                            customer_support_name=data.getResponseModel().getSupportdetails().getCustomer_support_name();
                            customer_support_no=data.getResponseModel().getSupportdetails().getCustomer_support_phone_no();
                            String happycode=data.getResponseModel().getGethappycode().getHappy_code();
                            if(happycode==null ||happycode.equals("null")||happycode.isEmpty()){

                            }else{
                                code_1.setText(happycode.substring(0,1));
                                code_2.setText(happycode.substring(1,2));
                                code_3.setText(happycode.substring(2,3));
                                code_4.setText(happycode.substring(3,4));
                            }

                            serviceflowlist=new ArrayList<>();
                            serviceflowlist=data.getResponseModel().getServiceflow();
                            if(serviceflowlist.isEmpty()){

                            }else{
                                if(serviceflowlist.get(0).getComments().equals("")){
                                    comments.setVisibility(View.GONE);
                                    tv_comments.setVisibility(View.GONE);
                                }else{
                                    comments.setVisibility(View.VISIBLE);
                                    tv_comments.setVisibility(View.VISIBLE);
                                    entered_comments.setText(serviceflowlist.get(0).getComments());
                                }
                                adapterTrackList = new AdapterTrackList(serviceflowlist, ServiceStatus.this);
                                LinearLayoutManager layoutManager = new LinearLayoutManager(ServiceStatus.this, LinearLayoutManager.VERTICAL, false);
                                rv_track_list.setLayoutManager(layoutManager);
                                rv_track_list.setAdapter(adapterTrackList);
                                ServiceStatus.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapterTrackList.notifyDataSetChanged();
                                    }
                                });
                            }
                        } else {
                            Toast.makeText(ServiceStatus.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<AppResponse> call, Throwable th) {
                    Toast.makeText(ServiceStatus.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
        }else{
           finish();
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