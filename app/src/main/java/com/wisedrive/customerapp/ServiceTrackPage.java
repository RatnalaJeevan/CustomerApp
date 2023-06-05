package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wisedrive.customerapp.adapters.Adapter_Tracking_page;
import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.commonclasses.Common;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.Pojo_Tracking_page;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceTrackPage extends AppCompatActivity {
    TextView yes,no,tv_date,tv_service_on,service_name,service_advs_name;
    private  Dialog dialog;
    RecyclerView rv_select_service_status;
    Adapter_Tracking_page adapter_tracking_page;
    ArrayList<Pojo_Tracking_page> pojo_tracking_pageArrayList;
    private ApiInterface apiInterface;
    RelativeLayout rl_transperant1,rl_top,rl_cancel,rl_label;
    ImageView image_logo;
    String phone_no="";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_track_page);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        rl_label=findViewById(R.id.rl_label);
        image_logo=findViewById(R.id.image_logo);
        service_name=findViewById(R.id.service_name);
        rv_select_service_status=findViewById(R.id.rv_select_service_status);
        rl_transperant1=findViewById(R.id.rl_transperant1);
        rl_cancel=findViewById(R.id.rl_cancel);
        rl_top=findViewById(R.id.rl_top);
        tv_date=findViewById(R.id.tv_date);
        tv_service_on=findViewById(R.id.tv_service_on);
        service_advs_name=findViewById(R.id.service_advs_name);
        rl_transperant1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ServiceTrackPage.this,Activity_Showroom_Services.class);
                i.putExtra("it_is","");
                startActivity(i);
                finish();
            }
        });

        if(SPHelper.is_exp.equalsIgnoreCase("y"))
        {
            rl_top.setVisibility(View.GONE);
            rl_cancel.setVisibility(View.GONE);
        }
        else {
            if( SPHelper.ser_status_id.equals("13")||
                    SPHelper.ser_status_id.equals("2")
                    )
            {
                rl_top.setVisibility(View.GONE);
                rl_cancel.setVisibility(View.VISIBLE);
            }
            else {
                rl_top.setVisibility(View.VISIBLE);
                rl_cancel.setVisibility(View.GONE);
            }
        }


        dialog = new Dialog(ServiceTrackPage.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.popup_cancel_service);
        dialog.setCancelable(true);

        yes=dialog.findViewById(R.id.yes) ;
        yes.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Intent i = new Intent(ServiceTrackPage.this,Activity_Showroom_Services.class);
                i.putExtra("it_is","cancel");
                startActivity(i);
                finish();
                dialog.dismiss();
            }
        });
        no=dialog.findViewById(R.id.no) ;
        no.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dialog.cancel();
                dialog.dismiss();
            }
        });

        rl_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
        rl_label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+phone_no));
                startActivity(callIntent);
            }
        });
        get_track_service();
    }

    public void get_track_service() {
        if (!Connectivity.isNetworkConnected(ServiceTrackPage.this)) {
            Toast.makeText(ServiceTrackPage.this,
                    "Plaese Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        } else {
           // idPBLoading.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.trackService(SPHelper.service_id);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                    AppResponse appResponse = response.body();
                    assert appResponse != null;
                    String response_code = appResponse.getResponseType();
                    if (response.body() != null) {
                        if (response_code.equals("200")) {
                           // idPBLoading.setVisibility(View.GONE);
                            pojo_tracking_pageArrayList= new ArrayList<>();
                            pojo_tracking_pageArrayList=appResponse.getResponseModel().getTrackService();
                            adapter_tracking_page = new Adapter_Tracking_page(ServiceTrackPage.this,pojo_tracking_pageArrayList);
                            LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(ServiceTrackPage.this, LinearLayoutManager.VERTICAL, false);
                            rv_select_service_status.setLayoutManager(linearLayoutManager2);
                            rv_select_service_status.setAdapter(adapter_tracking_page);

                            ServiceTrackPage.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter_tracking_page.notifyDataSetChanged();
                                }
                            });

                            String s_date=appResponse.getResponseModel().getServiceAdvisorDetails().getService_on_date();
                            String pack_name=appResponse.getResponseModel().getServiceAdvisorDetails().getPackage_name();
                            String ser_location=appResponse.getResponseModel().getServiceAdvisorDetails().getService_assigned_mgs_name();
                            String ser_type_name=appResponse.getResponseModel().getServiceAdvisorDetails().getService_type_name();
                            String ser_adv_name=appResponse.getResponseModel().getServiceAdvisorDetails().getService_assigned_by_name();
                             phone_no=appResponse.getResponseModel().getServiceAdvisorDetails().getService_assigned_by_no();
                            String image=appResponse.getResponseModel().getServiceAdvisorDetails().getImage();

                            if(SPHelper.ser_status_id.equals("13")){

                            }else {
                                Glide.with(ServiceTrackPage.this).load(image).placeholder(R.drawable.icon_noimage).into(image_logo);
                                service_advs_name.setText(ser_adv_name);
                                tv_date.setText(Common.getDateFromString(s_date));
                                service_name.setText(pack_name);
                                if(ser_type_name.equalsIgnoreCase("showroom")){
                                    tv_service_on.setText(ser_location);
                                }else {
                                    tv_service_on.setText("");
                                }
                            }


                        } else if (response_code.equals("300")) {
                            //idPBLoading.setVisibility(View.GONE);
                        }
                    } else {
                       // idPBLoading.setVisibility(View.GONE);
                        Toast.makeText(ServiceTrackPage.this, "internal server error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                    Toast.makeText(ServiceTrackPage.this,
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                   // idPBLoading.setVisibility(View.GONE);
                }
            });
        }
    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent(ServiceTrackPage.this,Activity_Showroom_Services.class);
        i.putExtra("it_is","");
        startActivity(i);
        finish();
    }
}