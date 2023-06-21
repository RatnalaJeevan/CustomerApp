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
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.ms.square.android.expandabletextview.ExpandableTextView;
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
    private static  final int MAX_LINES_COLLAPSED =2;
    private boolean isExpanded = false;
    private DecimalFormat IndianCurrencyFormat;
    private ApiInterface apiInterface;
    TextView pay_button,act_button,renew_button;
    private RecyclerView recyclerView;
    Adapter_Service_List adapter_service_list;
    ArrayList<Pojo_Service_list> pojo_service_listArrayList;
    NestedScrollView scroll_view;
    RelativeLayout rl_back_button,rl_edit;
    TextView text_heading;
    ReadMoreTextView description_lines;
    ArrayList<Pojo_Class_Mycar> pojo_class_mycarArrayList=new ArrayList<>();
    Adapter_class_mycar adapter_class_mycar;
    RecyclerView rv_additional_plan;
    Adapter_Additional_services adapter_additional_services;
    ArrayList<Pojo_Additional_Services>pojoAdditionalServicesArrayList;
    ViewPager view_pager_2;
    ProgressBar idPBLoading;
    TextView text_amount,inclued,label_description,active;
    String is_pack_exist="",is_upgrade="";
    RelativeLayout rl_add_car;
    ExpandableTextView expTv1;
    public  static  Warranty_Description instance;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.warranty_description);
        SPHelper.current_page="warr";
        instance=this;
        getWindow().setStatusBarColor(getColor(R.color.new_app_bg));
        IndianCurrencyFormat = new DecimalFormat("##,##,###");
        renew_button=findViewById(R.id.renew_button);
        act_button=findViewById(R.id.act_button);
        rl_edit=findViewById(R.id.rl_edit);
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
        active=findViewById(R.id.active);
        recyclerView = findViewById(R.id.recycler_service_list);
        rl_back_button = findViewById(R.id.rl_back_button);

        rl_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Warranty_Description.this, CustomerHomepage.class);
                startActivity(intent);
            }
        });

        rl_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.it_is="warr";
                PopupSelectPack bottomSheetDialogFragment = new PopupSelectPack();
                bottomSheetDialogFragment.show(Warranty_Description.this.getSupportFragmentManager(), "Popup");
            }
        });

        pay_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.is_upgrade="n";
                SPHelper.sel_upgrade_pac_id="";
                SPHelper.is_partial_pay="";
                SPHelper.addon_list=new ArrayList<>();
                SPHelper.disc_amount=0;
                SPHelper.upgrade_amount=SPHelper.pack_amount;
                SPHelper.add_on_amount=0;
                SPHelper.coupon_code="";
                SPHelper.coupon_type="";
                SPHelper.sel_addon_list=new ArrayList<>();
                SPHelper.upgrade_list=new ArrayList<>();
                SPHelper.gone_to="";
                Intent intent=new Intent(Warranty_Description.this,Addons.class);
                startActivity(intent);
            }
        });

        renew_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.addon_list=new ArrayList<>();

                SPHelper.sel_upgrade_pac_id="";
                SPHelper.is_partial_pay="";
                SPHelper.disc_amount=0;
                SPHelper.upgrade_amount=SPHelper.pack_amount;
                SPHelper.is_upgrade="n";
                SPHelper.add_on_amount=0;
                SPHelper.coupon_code="";
                SPHelper.coupon_type="";
                SPHelper.sel_addon_list=new ArrayList<>();
                SPHelper.upgrade_list=new ArrayList<>();
                SPHelper.gone_to="";
                Intent intent=new Intent(Warranty_Description.this,Addons.class);
                startActivity(intent);
            }
        });
        act_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SPHelper.addon_list=new ArrayList<>();
                SPHelper.sel_upgrade_pac_id="";
                SPHelper.is_partial_pay="";
                SPHelper.disc_amount=0;
                SPHelper.is_upgrade="y";
                SPHelper.upgrade_amount=0;
                SPHelper.is_ok_partial_pay="n";
                SPHelper.add_on_amount=0;
                SPHelper.coupon_code="";
                SPHelper.coupon_type="";
                SPHelper.sel_addon_list=new ArrayList<>();
                SPHelper.upgrade_list=new ArrayList<>();
                SPHelper.gone_to="";
                Intent intent=new Intent(Warranty_Description.this,Addons.class);
                startActivity(intent);
            }
        });

        active.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.is_upgrade="";
                SPHelper.addon_list=new ArrayList<>();
                SPHelper.is_ok_partial_pay="n";
                SPHelper.sel_upgrade_pac_id="";
                SPHelper.is_partial_pay="";
                SPHelper.disc_amount=0;
                SPHelper.upgrade_amount=0;
                SPHelper.add_on_amount=0;
                SPHelper.coupon_code="";
                SPHelper.coupon_type="";
                SPHelper.sel_addon_list=new ArrayList<>();
                SPHelper.upgrade_list=new ArrayList<>();
                SPHelper.gone_to="";
                Intent intent=new Intent(Warranty_Description.this,Addons.class);
                startActivity(intent);
            }
        });


        rl_add_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.carmodelid="";
                SPHelper.carbrandid="";
                Intent intent=new Intent(Warranty_Description.this,EnterCarDetails.class);
                startActivity(intent);
            }
        });
        get_pack_detailslist();

    }

    public  void get_pack_detailslist()
    {
        text_heading.setText(SPHelper.package_name);
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
                                rl_edit.setVisibility(View.GONE);
                            }else {
                                scroll_view.setVisibility(View.VISIBLE);
                                rl_add_car.setVisibility(View.GONE);
                                rl_edit.setVisibility(View.VISIBLE);
                                SPHelper.cat_id = pojo_class_mycarArrayList.get(0).getCategory_id();
                                SPHelper.veh_no = pojo_class_mycarArrayList.get(0).getVehicle_no();
                                String veh_id = pojo_class_mycarArrayList.get(0).getVehicle_id();
                                is_pack_exist = pojo_class_mycarArrayList.get(0).getPackExist();
                                is_upgrade=pojo_class_mycarArrayList.get(0).getUpgradeDetails().getIs_upgrade();
                                if (veh_id == null || veh_id.equals("null")) {
                                    SPHelper.veh_id = "";
                                } else {
                                    SPHelper.veh_id = veh_id;
                                }

                                if (is_pack_exist.equalsIgnoreCase("y"))
                                {

                                    if(is_upgrade==null ||is_upgrade.equals("null")){
                                        pay_button.setVisibility(View.GONE);
                                        act_button.setVisibility(View.VISIBLE);
                                        active.setVisibility(View.GONE);
                                    }else if(is_upgrade.equalsIgnoreCase("y")){
                                        pay_button.setVisibility(View.GONE);
                                        active.setVisibility(View.GONE);
                                        act_button.setVisibility(View.VISIBLE);
                                    }else {
                                        pay_button.setVisibility(View.GONE);
                                        active.setVisibility(View.VISIBLE);
                                        act_button.setVisibility(View.GONE);
                                    }
                                }
                                else {
                                    active.setVisibility(View.GONE);
                                    pay_button.setVisibility(View.VISIBLE);
                                    act_button.setVisibility(View.GONE);
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



                                Warranty_Description.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter_class_mycar.notifyDataSetChanged();
                                    }
                                });
                                get_product_list();
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

    public  void get_product_list()
    {
        if (!Connectivity.isNetworkConnected(Warranty_Description.this)) {
            Toast.makeText(Warranty_Description.this,
                    "Plaese Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        } else {
            idPBLoading.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.get_product_list(SPHelper.package_id);
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

                            pojoAdditionalServicesArrayList = new ArrayList<>();
                            pojoAdditionalServicesArrayList = appResponse.getResponseModel().getProductList();
                            SPHelper.product_name=pojoAdditionalServicesArrayList.get(0).getProduct_name();
                            SPHelper.product_id = pojoAdditionalServicesArrayList.get(0).getProduct_id();
                            adapter_additional_services = new Adapter_Additional_services(Warranty_Description.this, pojoAdditionalServicesArrayList);
                            LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(Warranty_Description.this, LinearLayoutManager.HORIZONTAL, false);
                            rv_additional_plan.setLayoutManager(linearLayoutManager3);
                            rv_additional_plan.setAdapter(adapter_additional_services);

                            Warranty_Description.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter_additional_services.notifyDataSetChanged();
                                }
                            });

                            get_pack_description();

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
                                description_lines.setTrimExpandedText("Read less");
                                description_lines.setTrimCollapsedText("Read more");
                                description_lines.setColorClickableText(Color.parseColor("#0619c3"));
                                description_lines.setText(dscrption);
                            }

                            String is_pack_exp=appResponse.getResponseModel().getPackExpiry().getIs_expired();
                            if(is_pack_exp==null)
                            {
                                renew_button.setVisibility(View.GONE);
                            }
                            else if (is_pack_exp.equalsIgnoreCase("y"))
                            {

                                renew_button.setVisibility(View.VISIBLE);
                                act_button.setVisibility(View.GONE);
                                active.setVisibility(View.GONE);
                                pay_button.setVisibility(View.GONE);

                            } else {
                                renew_button.setVisibility(View.GONE);
                               // act_button.setText("Pay Now");
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
                            String is_eligible=appResponse.getResponseModel().getPayAsyouGoEligibility().getIs_eligible();
                            double per_amount=appResponse.getResponseModel().getPayAsyouGoEligibility().getPercentage_amount_to_pay();
                            SPHelper.is_ok_partial_pay=is_eligible;
                            SPHelper.per_amount=per_amount;
                            SPHelper.final_per=SPHelper.per_amount;


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
        is_upgrade=pojo_class_mycarArrayList.get(position).getUpgradeDetails().getIs_upgrade();
        if (is_pack_exist.equalsIgnoreCase("y"))
        {
            if(is_upgrade==null ||is_upgrade.equals("null")){
                pay_button.setVisibility(View.GONE);
                act_button.setVisibility(View.VISIBLE);
                active.setVisibility(View.GONE);
            }else if(is_upgrade.equalsIgnoreCase("y")){
                pay_button.setVisibility(View.GONE);
                active.setVisibility(View.GONE);
                act_button.setVisibility(View.VISIBLE);
            }else {
                pay_button.setVisibility(View.GONE);
                active.setVisibility(View.VISIBLE);
                act_button.setVisibility(View.GONE);
            }
        } else {
            active.setVisibility(View.GONE);
            pay_button.setVisibility(View.VISIBLE);
            act_button.setVisibility(View.GONE);
        }
        get_product_list();
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







