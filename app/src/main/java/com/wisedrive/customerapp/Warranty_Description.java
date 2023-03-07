package com.wisedrive.customerapp;

import static java.lang.String.valueOf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.wisedrive.customerapp.adapters.Adapter_Additional_services;
import com.wisedrive.customerapp.adapters.Adapter_Service_List;
import com.wisedrive.customerapp.adapters.Adapter_class_mycar;
import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.Pojo_Additional_Services;
import com.wisedrive.customerapp.pojos.Pojo_Class_Mycar;
import com.wisedrive.customerapp.pojos.Pojo_Service_list;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Warranty_Description extends AppCompatActivity implements ViewPager.OnPageChangeListener
{
    private DecimalFormat IndianCurrencyFormat;
    private ApiInterface apiInterface;
    AppCompatButton pay_button;
    private RecyclerView recyclerView;
    Adapter_Service_List adapter_service_list;
    ArrayList<Pojo_Service_list> pojo_service_listArrayList;
    NestedScrollView scroll_view;
    RelativeLayout rl_back_button;
    TextView text_heading,description_lines;
    ArrayList<Pojo_Class_Mycar> pojo_class_mycarArrayList=new ArrayList<>();
    Adapter_class_mycar adapter_class_mycar;
    RecyclerView rv_additional_plan;
    Adapter_Additional_services adapter_additional_services;
    ArrayList<Pojo_Additional_Services>pojoAdditionalServicesArrayList;
    ViewPager view_pager_2;
    ProgressBar idPBLoading;
    TextView text_amount,inclued,label_description;
    String is_pack_exist="";
    RelativeLayout rl_add_car;
    public  static  Warranty_Description instance;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.warranty_description);
        instance=this;
        IndianCurrencyFormat = new DecimalFormat("##,##,###");
        description_lines=findViewById(R.id.description_lines);
        label_description=findViewById(R.id.label_description);
        inclued=findViewById(R.id.inclued);
        rl_add_car=findViewById(R.id.rl_add_car);
        text_amount=findViewById(R.id.text_amount);
        idPBLoading=findViewById(R.id.idPBLoading);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        view_pager_2=findViewById(R.id.view_pager_2);
        scroll_view=findViewById(R.id.scroll_view);
        text_heading=findViewById(R.id.text_heading);
        rv_additional_plan = findViewById(R.id.rv_additional_plan);
        pay_button=findViewById(R.id.pay_button);
        text_heading.setText(SPHelper.package_name);
        recyclerView = findViewById(R.id.recycler_service_list);
        rl_back_button = findViewById(R.id.rl_back_button);
        rl_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Warranty_Description.this, CustomerHomepage.class);
                startActivity(intent);
            }
        });


        pay_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.addon_list=new ArrayList<>();
                SPHelper.disc_amount=0;
                SPHelper.coupon_code="";
                SPHelper.coupon_type="";
                Intent intent=new Intent(Warranty_Description.this,Addons.class);
                startActivity(intent);
            }
        });


        rl_add_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.carmodelid="";
                SPHelper.carbrandid="";
                Intent intent=new Intent(Warranty_Description.this,Add_New_Car.class);
                startActivity(intent);
            }
        });
        get_pack_detailslist();

    }

    public  void get_pack_detailslist()
    {
        if (!Connectivity.isNetworkConnected(Warranty_Description.this)) {
            Toast.makeText(Warranty_Description.this,
                    "Plaese Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        } else {
            idPBLoading.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.getPackDetails(SPHelper.getSPData(Warranty_Description.this,SPHelper.lead_id,""),
                    SPHelper.getSPData(Warranty_Description.this,SPHelper.customer_id,""),SPHelper.package_id);
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
                            pojo_class_mycarArrayList = new ArrayList<>();
                            pojo_class_mycarArrayList=appResponse.getResponseModel().getMyCars();
                            if(pojo_class_mycarArrayList.isEmpty()){
                                scroll_view.setVisibility(View.GONE);
                                rl_add_car.setVisibility(View.VISIBLE);
                            }else {
                                scroll_view.setVisibility(View.VISIBLE);
                                rl_add_car.setVisibility(View.GONE);
                                SPHelper.cat_id = pojo_class_mycarArrayList.get(0).getCategory_id();
                                SPHelper.veh_no = pojo_class_mycarArrayList.get(0).getVehicle_no();
                                String veh_id = pojo_class_mycarArrayList.get(0).getVehicle_id();
                                is_pack_exist = pojo_class_mycarArrayList.get(0).getPackExist();
                                if (veh_id == null || veh_id.equals("null")) {
                                    SPHelper.veh_id = "";
                                } else {
                                    SPHelper.veh_id = veh_id;
                                }
                                if (is_pack_exist.equalsIgnoreCase("y")) {
                                    pay_button.setVisibility(View.GONE);
                                } else {
                                    pay_button.setVisibility(View.VISIBLE);
                                }

                                if (SPHelper.cat_id == null || SPHelper.cat_id.equals("null")) {
                                    SPHelper.cat_id = "0";
                                }
                                if (pojo_class_mycarArrayList.get(0).getLead_veicle_id() == null ||
                                        pojo_class_mycarArrayList.get(0).getLead_veicle_id().equals("null")) {
                                    SPHelper.lead_veh_id = "";
                                } else {
                                    SPHelper.lead_veh_id = pojo_class_mycarArrayList.get(0).getLead_veicle_id();
                                }
                                adapter_class_mycar = new Adapter_class_mycar(Warranty_Description.this, pojo_class_mycarArrayList);
                                view_pager_2.setCurrentItem(0);
                                view_pager_2.setAdapter(adapter_class_mycar);
                                view_pager_2.setOnPageChangeListener(Warranty_Description.this);

                                pojoAdditionalServicesArrayList = new ArrayList<>();
                                pojoAdditionalServicesArrayList = appResponse.getResponseModel().getProductList();
                                SPHelper.product_id = pojoAdditionalServicesArrayList.get(0).getProduct_id();
                                adapter_additional_services = new Adapter_Additional_services(Warranty_Description.this, pojoAdditionalServicesArrayList);
                                LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(Warranty_Description.this, LinearLayoutManager.HORIZONTAL, false);
                                rv_additional_plan.setLayoutManager(linearLayoutManager3);
                                rv_additional_plan.setAdapter(adapter_additional_services);

                                Warranty_Description.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter_class_mycar.notifyDataSetChanged();
                                        adapter_additional_services.notifyDataSetChanged();
                                    }
                                });
                                get_pack_description();
                            }

                        } else if (response_code.equals("300")) {
                            idPBLoading.setVisibility(View.GONE);
                        }
                    } else {
                        idPBLoading.setVisibility(View.GONE);
                        Toast.makeText(Warranty_Description.this, "internal server error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                    Toast.makeText(Warranty_Description.this,
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    idPBLoading.setVisibility(View.GONE);
                }
            });
        }
    }

    public  void get_pack_description()
    {
        if (!Connectivity.isNetworkConnected(Warranty_Description.this)) {
            Toast.makeText(Warranty_Description.this,
                    "Plaese Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        } else {
            idPBLoading.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.getPackDescription(SPHelper.getSPData(Warranty_Description.this,SPHelper.lead_id,""),
                    SPHelper.getSPData(Warranty_Description.this,SPHelper.customer_id,""),SPHelper.package_id,
                    SPHelper.product_id,SPHelper.main_pack_id,SPHelper.cat_id,SPHelper.veh_id);
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
                            SPHelper.pack_amount=appResponse.getResponseModel().getPriceDetails().getFinal_price();
                            text_amount.setText(IndianCurrencyFormat.format((int)SPHelper.pack_amount));
                            SPHelper.final_amount=SPHelper.pack_amount;
                            String dscrption=appResponse.getResponseModel().getDescription().getDescription();
                            if(dscrption==null||dscrption.equals("null")||dscrption.equals("")){
                                label_description.setVisibility(View.GONE);
                                description_lines.setVisibility(View.GONE);
                            }else{
                                label_description.setVisibility(View.VISIBLE);
                                description_lines.setVisibility(View.VISIBLE);
                                description_lines.setText(dscrption);
                            }

                            String is_pack_exp=appResponse.getResponseModel().getPackExpiry().getIs_expired();
                            if(is_pack_exp==null){
                                pay_button.setText("Pay Now");
                            }
                            else if (is_pack_exp.equalsIgnoreCase("y")) {
                                pay_button.setText("Renew");
                            } else {
                                pay_button.setText("Pay Now");
                            }
                            pojo_service_listArrayList = new ArrayList<>();
                            pojo_service_listArrayList=appResponse.getResponseModel().getServiceDetails();
                            if(pojo_service_listArrayList.isEmpty()){
                                inclued.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.GONE);
                            }else{
                                inclued.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.VISIBLE);
                                adapter_service_list = new Adapter_Service_List(Warranty_Description.this, pojo_service_listArrayList);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Warranty_Description.this, LinearLayoutManager.VERTICAL, false);
                                recyclerView.setLayoutManager(linearLayoutManager);
                                recyclerView.setAdapter(adapter_service_list);

                                Warranty_Description.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter_service_list.notifyDataSetChanged();
                                    }
                                });
                            }

                        } else if (response_code.equals("300")) {
                             idPBLoading.setVisibility(View.GONE);
                        }
                    } else {
                         idPBLoading.setVisibility(View.GONE);
                        Toast.makeText(Warranty_Description.this, "internal server error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                    Toast.makeText(Warranty_Description.this,
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                     idPBLoading.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    public  static Warranty_Description getInstance() {
        return instance;
    }

    @Override
    public void onPageSelected(int position) {

        if(pojo_class_mycarArrayList.get(position).getCategory_id()==null||
                pojo_class_mycarArrayList.get(position).getCategory_id().equals("null")){
            SPHelper.cat_id="0";
        }else{
            SPHelper.cat_id=pojo_class_mycarArrayList.get(position).getCategory_id();
        }
        if(pojo_class_mycarArrayList.get(position).getLead_veicle_id()==null||
                pojo_class_mycarArrayList.get(position).getLead_veicle_id().equals("null")){
            SPHelper.lead_veh_id="";
        }else{
            SPHelper.lead_veh_id=pojo_class_mycarArrayList.get(position).getLead_veicle_id();
        }

        SPHelper.veh_no=pojo_class_mycarArrayList.get(position).getVehicle_no();
        String veh_id=pojo_class_mycarArrayList.get(position).getVehicle_id();
        if(veh_id==null||veh_id.equals("null")){
            SPHelper.veh_id="";
        }else{
            SPHelper.veh_id=veh_id;
        }

        is_pack_exist=pojo_class_mycarArrayList.get(position).getPackExist();

        if(is_pack_exist.equalsIgnoreCase("y")){
            pay_button.setVisibility(View.GONE);
        }else{
            pay_button.setVisibility(View.VISIBLE);
        }
        get_pack_description();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Warranty_Description.this, CustomerHomepage.class);
        startActivity(intent);
    }
}







