package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wisedrive.customerapp.adapters.Adapter_Service_Includes;
import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.Pojo_My_Car_page_package_list;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Confirmation_Activity extends AppCompatActivity {
    private DecimalFormat IndianCurrencyFormat;
    RecyclerView rv_service_incluedes_list;
    Adapter_Service_Includes adapter_service_includes;
    ArrayList<Pojo_My_Car_page_package_list>pojo_service_includesArrayList;
    private ApiInterface apiInterface;
    RelativeLayout request_inspection,rl_i_ll_do_later,relative_layout,rl_my_car_button,rl_description;
    TextView tv_amount,number,tv_descptn;
    ProgressBar idPBLoading;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        IndianCurrencyFormat = new DecimalFormat("##,##,###");
        rl_description=findViewById(R.id.rl_description);
        tv_descptn=findViewById(R.id.tv_descptn);
        idPBLoading=findViewById(R.id.idPBLoading);
        number=findViewById(R.id.number);
        apiInterface= ApiClient.getClient().create(ApiInterface.class);
        tv_amount=findViewById(R.id.tv_amount);
        relative_layout=findViewById(R.id.relative_layout);
        rv_service_incluedes_list=findViewById(R.id.rv_service_incluedes_list);
        rl_my_car_button=findViewById(R.id.rl_my_car_button);
        request_inspection=findViewById(R.id.request_inspection);
        rl_i_ll_do_later=findViewById(R.id.rl_i_ll_do_later);
        Intent intent=getIntent();
        String ref_no=intent.getStringExtra("refer_no");
        String iswarranty=intent.getStringExtra("is_warranty");
        String succ_msg=intent.getStringExtra("successmsg");
        number.setText(ref_no);
        if(succ_msg.isEmpty()){
            rl_description.setVisibility(View.GONE);
        }else {
            rl_description.setVisibility(View.VISIBLE);
            tv_descptn.setText(succ_msg);
        }

        if(iswarranty.equalsIgnoreCase("y")){
            relative_layout.setVisibility(View.VISIBLE);
            rl_my_car_button.setVisibility(View.INVISIBLE);
        }else{
            relative_layout.setVisibility(View.INVISIBLE);
            rl_my_car_button.setVisibility(View.VISIBLE);
        }
        rl_my_car_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.fragment_is="cars";
                Intent intent=new Intent(Confirmation_Activity.this,CustomerHomepage.class);
                startActivity(intent);
            }
        });
        request_inspection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Confirmation_Activity.this,Request_Inspection.class);
                startActivity(intent);
            }
        });

        tv_amount.setText(IndianCurrencyFormat.format((int)( SPHelper.final_amount-SPHelper.disc_amount)));
        rl_i_ll_do_later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Confirmation_Activity.this, CustomerHomepage.class);
                startActivity(intent);
            }
        });

        getPayment_confirm_page();
    }

    public  void getPayment_confirm_page()
    {
        if (!Connectivity.isNetworkConnected(Confirmation_Activity.this)) {
            Toast.makeText(Confirmation_Activity.this,
                    "Plaese Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        } else {
            //idPBLoading.setVisibility(View.VISIBLE);
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
                            pojo_service_includesArrayList= new ArrayList<>();
                            pojo_service_includesArrayList=appResponse.getResponseModel().getConfirmationList();
                            adapter_service_includes = new Adapter_Service_Includes(Confirmation_Activity.this, pojo_service_includesArrayList);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Confirmation_Activity.this, LinearLayoutManager.VERTICAL, false);
                            rv_service_incluedes_list.setLayoutManager(linearLayoutManager);
                            rv_service_incluedes_list.setAdapter(adapter_service_includes);

                                Confirmation_Activity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter_service_includes.notifyDataSetChanged();
                                    }
                                });

                        } else if (response_code.equals("300")) {
                             idPBLoading.setVisibility(View.GONE);
                        }
                    } else {
                         idPBLoading.setVisibility(View.GONE);
                        Toast.makeText(Confirmation_Activity.this, "internal server error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                    Toast.makeText(Confirmation_Activity.this,
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
        SPHelper.fragment_is="plans";
        Intent intent=new Intent(Confirmation_Activity.this, CustomerHomepage.class);
        startActivity(intent);
    }
}