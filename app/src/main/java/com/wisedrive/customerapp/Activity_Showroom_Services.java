package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wisedrive.customerapp.adapters.Adapter_Addons_list;
import com.wisedrive.customerapp.adapters.Adapter_Select_Date;
import com.wisedrive.customerapp.adapters.Adapter_Showroom_Services;
import com.wisedrive.customerapp.adapters.Adapter_Tracking_page;
import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.PojoBookService;
import com.wisedrive.customerapp.pojos.PojoCreateOrder;
import com.wisedrive.customerapp.pojos.Pojo_Class_Addons_List;
import com.wisedrive.customerapp.pojos.Pojo_Select_Date;
import com.wisedrive.customerapp.pojos.Pojo_Showroom_services;
import com.wisedrive.customerapp.pojos.Pojo_Tracking_page;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_Showroom_Services extends AppCompatActivity {
   public RelativeLayout rl_track_service_status, rl_transperant1,rl_select_dates,rl_transperant2,confirm;
    RecyclerView rv_showroom_services;
    Adapter_Showroom_Services adapter_showroom_services;
    ArrayList<Pojo_Showroom_services>pojo_showroom_servicesArrayList;
    private ApiInterface apiInterface;
    RecyclerView rv_select_date;
    Adapter_Select_Date adapter_select_date;
    ArrayList<Pojo_Select_Date>pojo_select_dateArrayList;
   RecyclerView rv_select_service_status;
   Adapter_Tracking_page adapter_tracking_page;
   ArrayList<Pojo_Tracking_page> pojo_tracking_pageArrayList;
   ProgressBar idPBLoading;
   TextView tv_showroom_services,expired;
   ImageView back;
   RelativeLayout rl_call;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showroom_services);
        back=findViewById(R.id.back);
        expired=findViewById(R.id.expired);
        tv_showroom_services=findViewById(R.id.tv_showroom_services);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        confirm=findViewById(R.id.confirm);
        rl_call=findViewById(R.id.rl_call);
        idPBLoading=findViewById(R.id.idPBLoading);
        rv_showroom_services=findViewById(R.id.rv_showroom_services);
        rv_select_date=findViewById(R.id.rv_select_date);
        rv_select_service_status=findViewById(R.id.rv_select_service_status);
        rl_track_service_status=findViewById(R.id.rl_track_service_status);
        rl_transperant1=findViewById(R.id.rl_transperant1);
        rl_select_dates=findViewById(R.id.rl_select_dates);
        rl_transperant2=findViewById(R.id.rl_transperant2);
        tv_showroom_services.setText(SPHelper.product_name);

        if(SPHelper.is_exp.equalsIgnoreCase("y")){
            expired.setVisibility(View.VISIBLE);
        }else{
            expired.setVisibility(View.GONE);
        }
        rl_transperant1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_track_service_status.setVisibility(View.GONE);
            }
        });
        rl_transperant2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_select_dates.setVisibility(View.GONE);
            }
        });

        rl_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+SPHelper.getSPData(Activity_Showroom_Services.this,SPHelper.customer_support_phoneno,"")));
                startActivity(callIntent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Activity_Showroom_Services.this, CustomerHomepage.class);
//                startActivity(intent);
                finish();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(adapter_select_date.server_date.equals(""))
                {
                    Toast.makeText(Activity_Showroom_Services.this,
                            "Please Select service date",
                            Toast.LENGTH_SHORT).show();
                }else{
                    System.out.println("ids"+SPHelper.veh_id+SPHelper.getSPData(Activity_Showroom_Services.this, SPHelper.customer_id, "")+
                            adapter_showroom_services.package_id+SPHelper.service_id+"13"+adapter_select_date.server_date);
                   book_service();
                }
            }
        });
        getVehServiceList();
    }

    public void getVehServiceList() {
        if (!Connectivity.isNetworkConnected(Activity_Showroom_Services.this)) {
            Toast.makeText(Activity_Showroom_Services.this,
                    "Plaese Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        } else {
            idPBLoading.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.getServiceList(SPHelper.veh_id,SPHelper.product_id,SPHelper.package_id,
                    SPHelper.pack_type);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                    AppResponse appResponse = response.body();
                    assert appResponse != null;
                    String response_code = appResponse.getResponseType();
                    if (response.body() != null) {
                        if (response_code.equals("200")) {
                            idPBLoading.setVisibility(View.GONE);
                            pojo_showroom_servicesArrayList = new ArrayList<>();
                            pojo_showroom_servicesArrayList = appResponse.getResponseModel().getVehicleServiceList();
                            adapter_showroom_services = new Adapter_Showroom_Services(Activity_Showroom_Services.this, pojo_showroom_servicesArrayList);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Activity_Showroom_Services.this, LinearLayoutManager.VERTICAL, false);
                            rv_showroom_services.setLayoutManager(linearLayoutManager);
                            rv_showroom_services.setAdapter(adapter_showroom_services);

                            Activity_Showroom_Services.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter_showroom_services.notifyDataSetChanged();
                                }
                            });
                        } else if (response_code.equals("300")) {
                             idPBLoading.setVisibility(View.GONE);
                        }
                    } else {
                         idPBLoading.setVisibility(View.GONE);
                        Toast.makeText(Activity_Showroom_Services.this, "internal server error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                    Toast.makeText(Activity_Showroom_Services.this,
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                     idPBLoading.setVisibility(View.GONE);
                }
            });
        }
    }

    public void get_track_service() {
        if (!Connectivity.isNetworkConnected(Activity_Showroom_Services.this)) {
            Toast.makeText(Activity_Showroom_Services.this,
                    "Plaese Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        } else {
            idPBLoading.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.trackService(SPHelper.service_id);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                    AppResponse appResponse = response.body();
                    assert appResponse != null;
                    String response_code = appResponse.getResponseType();
                    if (response.body() != null) {
                        if (response_code.equals("200")) {
                             idPBLoading.setVisibility(View.GONE);
                            pojo_tracking_pageArrayList= new ArrayList<>();
                            pojo_tracking_pageArrayList=appResponse.getResponseModel().getTrackService();
                            adapter_tracking_page = new Adapter_Tracking_page(Activity_Showroom_Services.this,pojo_tracking_pageArrayList);
                            LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(Activity_Showroom_Services.this, LinearLayoutManager.VERTICAL, false);
                            rv_select_service_status.setLayoutManager(linearLayoutManager2);
                            rv_select_service_status.setAdapter(adapter_tracking_page);

                            Activity_Showroom_Services.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter_tracking_page.notifyDataSetChanged();
                                }
                            });
                        } else if (response_code.equals("300")) {
                             idPBLoading.setVisibility(View.GONE);
                        }
                    } else {
                         idPBLoading.setVisibility(View.GONE);
                        Toast.makeText(Activity_Showroom_Services.this, "internal server error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                    Toast.makeText(Activity_Showroom_Services.this,
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                     idPBLoading.setVisibility(View.GONE);
                }
            });
        }
    }

    public void getDateLists() {
        if (!Connectivity.isNetworkConnected(Activity_Showroom_Services.this)) {
            Toast.makeText(Activity_Showroom_Services.this,
                    "Plaese Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        } else {
            idPBLoading.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.getDateList(SPHelper.product_id);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                    AppResponse appResponse = response.body();
                    assert appResponse != null;
                    String response_code = appResponse.getResponseType();
                    if (response.body() != null) {
                        if (response_code.equals("200")) {
                            idPBLoading.setVisibility(View.GONE);
                            pojo_select_dateArrayList = new ArrayList<>();
                            pojo_select_dateArrayList=appResponse.getResponseModel().getDateList();
                            GridLayoutManager manager =
                                    new GridLayoutManager(Activity_Showroom_Services.this,3,
                                    GridLayoutManager.VERTICAL, false);
//                            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//                                @Override
//                                public int getSpanSize(int position) {
//                                    int f_po=position%pojo_select_dateArrayList.size();
//                                    if (f_po == 0){
//                                        return 2;
//                                    }else if(f_po==1){
//                                        return 2;
//                                    }
//                                    else{
//                                        return 3;
//                                    }
//                                }
//                            });
                            rv_select_date.setLayoutManager(manager);
                            adapter_select_date = new Adapter_Select_Date(Activity_Showroom_Services.this,pojo_select_dateArrayList);
                            rv_select_date.setAdapter(adapter_select_date);

                            Activity_Showroom_Services.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter_select_date.notifyDataSetChanged();
                                }
                            });
                        } else if (response_code.equals("300")) {
                            idPBLoading.setVisibility(View.GONE);
                        }
                    } else {
                        idPBLoading.setVisibility(View.GONE);
                        Toast.makeText(Activity_Showroom_Services.this, "internal server error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                    Toast.makeText(Activity_Showroom_Services.this,
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                     idPBLoading.setVisibility(View.GONE);
                }
            });
        }
    }

    public void book_service() {
        {
            if (!Connectivity.isNetworkConnected(Activity_Showroom_Services.this)) {
                Toast.makeText(Activity_Showroom_Services.this,
                        "Please Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            } else {
                 idPBLoading.setVisibility(View.VISIBLE);
                 PojoBookService pojoBookService=new PojoBookService(SPHelper.veh_id,SPHelper.getSPData(Activity_Showroom_Services.this, SPHelper.customer_id, ""),
                        adapter_showroom_services.package_id,adapter_showroom_services.service_id,"13",adapter_select_date.server_date,"","","",
                        "","","","","","","","",SPHelper.package_id,SPHelper.pack_type);
                Call<AppResponse> call = apiInterface.book_service(pojoBookService);
                call.enqueue(new Callback<AppResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                        AppResponse appResponse = response.body();
                        assert appResponse != null;
                        String response_code = appResponse.getResponseType();
                        if (response.body() != null) {
                            if (response_code.equals("200")) {
                                 idPBLoading.setVisibility(View.GONE);
                                 rl_select_dates.setVisibility(View.GONE);
                                 getVehServiceList();

                            } else if (response_code.equals("300")) {
                                 idPBLoading.setVisibility(View.GONE);
                                Toast.makeText(Activity_Showroom_Services.this, "appResponse.getResponse().getMessage()", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                             idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(Activity_Showroom_Services.this, "internal server error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                        Toast.makeText(Activity_Showroom_Services.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                         idPBLoading.setVisibility(View.GONE);
                    }
                });
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent = new Intent(Activity_Showroom_Services.this, CustomerHomepage.class);
//        startActivity(intent);
        finish();
    }
}