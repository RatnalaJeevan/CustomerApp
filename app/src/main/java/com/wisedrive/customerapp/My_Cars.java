package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.wisedrive.customerapp.adapters.Adapter_Additional_services;
import com.wisedrive.customerapp.adapters.Adapter_Class_My_Car_page;
import com.wisedrive.customerapp.adapters.Adapter_class_mycar;
import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.Pojo_Class_Mycar;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class My_Cars extends AppCompatActivity {
    private RecyclerView recycler_mycars;
    Adapter_Class_My_Car_page adapter_class_my_car_page;
    ArrayList<Pojo_Class_Mycar> pojo_class_mycarArrayList;
    private ApiInterface apiInterface;
    Context context;
    View view;
    RelativeLayout rl_add_car_button,rl_back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cars);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        recycler_mycars=findViewById(R.id.recycler_mycars);
        rl_add_car_button=findViewById(R.id.rl_add_car_button);
        rl_back_button=findViewById(R.id.rl_back_button);
        rl_add_car_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.carmodelid="";
                SPHelper.carbrandid="";
                Intent intent=new Intent(My_Cars.this,Add_New_Car.class);
                startActivity(intent);
            }
        });
        rl_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        get_my_cars_list();
    }

    public  void get_my_cars_list()
    {
        if (!Connectivity.isNetworkConnected(My_Cars.this)) {
            Toast.makeText(My_Cars.this,
                    "Plaese Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        } else {
            //idPBLoading.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.getVehList(SPHelper.getSPData(My_Cars.this,SPHelper.lead_id,""),
                    SPHelper.getSPData(My_Cars.this,SPHelper.customer_id,""));
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                    AppResponse appResponse = response.body();
                    assert appResponse != null;
                    String response_code = appResponse.getResponseType();
                    if (response.body() != null) {
                        if (response_code.equals("200"))
                        {
                           // idPBLoading.setVisibility(View.GONE);
                            pojo_class_mycarArrayList = new ArrayList<>();
                            pojo_class_mycarArrayList=appResponse.getResponseModel().getVehicleList();
                            adapter_class_my_car_page= new Adapter_Class_My_Car_page(My_Cars.this, pojo_class_mycarArrayList);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(My_Cars.this, LinearLayoutManager.VERTICAL, false);
                            recycler_mycars.setLayoutManager(linearLayoutManager);
                            recycler_mycars.setAdapter(adapter_class_my_car_page);

                            My_Cars.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter_class_my_car_page.notifyDataSetChanged();
                                }
                            });


                        } else if (response_code.equals("300")) {
                            //idPBLoading.setVisibility(View.GONE);
                        }
                    } else {
                       // idPBLoading.setVisibility(View.GONE);
                        Toast.makeText(My_Cars.this, "internal server error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                    Toast.makeText(My_Cars.this,
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                   // idPBLoading.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}