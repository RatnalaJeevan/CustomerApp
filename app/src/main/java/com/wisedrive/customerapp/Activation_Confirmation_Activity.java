package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.wisedrive.customerapp.adapters.Adapter_Activate_Confirmation_Page;
import com.wisedrive.customerapp.adapters.Adapter_Addons_list;
import com.wisedrive.customerapp.adapters.Adapter_Service_Includes;
import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.Pojo_Activate_Confirmation_Page;
import com.wisedrive.customerapp.pojos.Pojo_Class_Addons_List;
import com.wisedrive.customerapp.pojos.Pojo_My_Car_page_package_list;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activation_Confirmation_Activity extends AppCompatActivity {
    RecyclerView rv_warranty;
    Adapter_Activate_Confirmation_Page adapter_activate_confirmation_page;
    ArrayList<Pojo_My_Car_page_package_list> pojo_activate_confirmation_pageArrayList;
    ProgressBar idPBLoading;
    RelativeLayout rl_my_car_button;
    private ApiInterface apiInterface;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activation_confirmation);
        idPBLoading=findViewById(R.id.idPBLoading);
        rv_warranty=findViewById(R.id.rv_warranty);
        apiInterface= ApiClient.getClient().create(ApiInterface.class);
        rl_my_car_button=findViewById(R.id.rl_my_car_button);

        rl_my_car_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.fragment_is="cars";
                Intent intent=new Intent(Activation_Confirmation_Activity.this,CustomerHomepage.class);
                startActivity(intent);
            }
        });
        getPayment_confirm_page();

    }

    public  void getPayment_confirm_page()
    {
        if (!Connectivity.isNetworkConnected(Activation_Confirmation_Activity.this)) {
            Toast.makeText(Activation_Confirmation_Activity.this,
                    "Plaese Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        } else {
            idPBLoading.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.get_payment_confirmation_page(SPHelper.dpp_id);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                    AppResponse appResponse = response.body();
                    assert appResponse != null;
                    String response_code = appResponse.getResponseType();
                    if (response.body() != null) {
                        if (response_code.equals("200"))
                        {
                            idPBLoading.setVisibility(View.GONE);

                            pojo_activate_confirmation_pageArrayList = new ArrayList<>();
                            pojo_activate_confirmation_pageArrayList=appResponse.getResponseModel().getConfirmationList();
                            adapter_activate_confirmation_page = new  Adapter_Activate_Confirmation_Page(Activation_Confirmation_Activity.this,pojo_activate_confirmation_pageArrayList);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Activation_Confirmation_Activity.this, LinearLayoutManager.VERTICAL, false);
                            rv_warranty.setLayoutManager(linearLayoutManager);
                            rv_warranty.setAdapter(adapter_activate_confirmation_page);

                            Activation_Confirmation_Activity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter_activate_confirmation_page.notifyDataSetChanged();
                                }
                            });

                        } else if (response_code.equals("300")) {
                            idPBLoading.setVisibility(View.GONE);
                        }
                    } else {
                        idPBLoading.setVisibility(View.GONE);
                        Toast.makeText(Activation_Confirmation_Activity.this, "internal server error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                    Toast.makeText(Activation_Confirmation_Activity.this,
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    idPBLoading.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SPHelper.fragment_is="cars";
        Intent intent=new Intent(Activation_Confirmation_Activity.this,CustomerHomepage.class);
        startActivity(intent);
    }
}