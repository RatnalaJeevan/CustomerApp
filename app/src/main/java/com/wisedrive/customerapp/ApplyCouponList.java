package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wisedrive.customerapp.adapters.AdapterCouponList;
import com.wisedrive.customerapp.adapters.Adapter_Addons_list;
import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.PojoCouponList;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApplyCouponList extends AppCompatActivity {
    ArrayList<PojoCouponList> pojoCouponLists;
    ArrayList<PojoCouponList> app_coupon_list;
    AdapterCouponList adapterCouponList;
    RecyclerView rv_coupon_list;
    private ApiInterface apiInterface;
    RelativeLayout rl_back_button;
    EditText entered_coupon_code;
    TextView apply;
    public  String coming_from="",is_upgrade="";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_coupon_list);
        apply=findViewById(R.id.apply);
        rv_coupon_list=findViewById(R.id.rv_coupon_list);
        entered_coupon_code=findViewById(R.id.entered_coupon_code);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        rl_back_button=findViewById(R.id.rl_back_button);

        getWindow().setStatusBarColor(getColor(R.color.new_app_bg));
        Intent intent=getIntent();
        coming_from=intent.getStringExtra("comingfrom");
        if(coming_from.equals("addon")){
           is_upgrade="N";
        }else{
          is_upgrade="Y";
        }
        rl_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(coming_from.equals("addon")){
                    Intent intent=new Intent(ApplyCouponList.this,Addons.class);
                    startActivity(intent);
                }else{
                    Intent intent=new Intent(ApplyCouponList.this,Upgrade_and_Save.class);
                    startActivity(intent);
                }
            }
        });

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeybaord();
                if(entered_coupon_code.getText().toString().equals("")){
                    Toast.makeText(ApplyCouponList.this,
                            "Enter a coupon code",
                            Toast.LENGTH_SHORT).show();
                }else{
                    getcoupon_details();
                }
            }
        });

        entered_coupon_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(entered_coupon_code.getText().toString().length()>5){
                    apply.setTextColor(Color.parseColor("#008000"));
                }else{
                    apply.setTextColor(Color.parseColor("#c7c7cc"));
                }
            }
        });
        get_coupon_list();

    }


    public void get_coupon_list() {
        if (!Connectivity.isNetworkConnected(ApplyCouponList.this)) {
            Toast.makeText(ApplyCouponList.this,
                    "Plaese Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        } else {
            //idPBLoading.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.get_coupon_list(SPHelper.getSPData(ApplyCouponList.this, SPHelper.lead_id, ""),
                    SPHelper.getSPData(ApplyCouponList.this, SPHelper.customer_id, ""), SPHelper.package_id,
                    SPHelper.cat_id, SPHelper.main_pack_id,SPHelper.upgrade_amount,is_upgrade);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                    AppResponse appResponse = response.body();
                    assert appResponse != null;
                    String response_code = appResponse.getResponseType();
                    if (response.body() != null) {
                        if (response_code.equals("200")) {
                            // idPBLoading.setVisibility(View.GONE);
                            pojoCouponLists = new ArrayList<>();
                            app_coupon_list=new ArrayList<>();
                            pojoCouponLists = appResponse.getResponseModel().getCouponCodeList();
//                            for(int i=0;i<pojoCouponLists.size();i++)
//                            {
//                                if(pojoCouponLists.get(i).getIs_valid().equalsIgnoreCase("y")){
//                                    PojoCouponList obj=new PojoCouponList();
//                                    obj.setCoupon_code(pojoCouponLists.get(i).getCoupon_code());
//                                    obj.setCoupon_id(pojoCouponLists.get(i).getCoupon_id());
//                                    obj.setCoupon_type(pojoCouponLists.get(i).getCoupon_type());
//                                    obj.setDescription(pojoCouponLists.get(i).getDescription());
//                                    obj.setExpiration_date(pojoCouponLists.get(i).getExpiration_date());
//                                    obj.setDiscount_amount(pojoCouponLists.get(i).getDiscount_amount());
//                                    app_coupon_list.add(obj);
//                                }
//                            }

                            adapterCouponList = new AdapterCouponList(pojoCouponLists,ApplyCouponList.this);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ApplyCouponList.this, LinearLayoutManager.VERTICAL, false);
                            rv_coupon_list.setLayoutManager(linearLayoutManager);
                            rv_coupon_list.setAdapter(adapterCouponList);

                            ApplyCouponList.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapterCouponList.notifyDataSetChanged();
                                }
                            });
                        } else if (response_code.equals("300")) {
                            // idPBLoading.setVisibility(View.GONE);
                        }
                    } else {
                        // idPBLoading.setVisibility(View.GONE);
                        Toast.makeText(ApplyCouponList.this, "internal server error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                    Toast.makeText(ApplyCouponList.this,
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    // idPBLoading.setVisibility(View.GONE);
                }
            });
        }
    }

    public void getcoupon_details() {
        if (!Connectivity.isNetworkConnected(ApplyCouponList.this)) {
            Toast.makeText(ApplyCouponList.this,
                    "Plaese Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        } else {
            //idPBLoading.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.get_coupon_details(SPHelper.getSPData(ApplyCouponList.this, SPHelper.lead_id, ""),
                    SPHelper.getSPData(ApplyCouponList.this, SPHelper.customer_id, ""), SPHelper.package_id,
                    SPHelper.cat_id, SPHelper.main_pack_id,SPHelper.upgrade_amount,entered_coupon_code.getText().toString().toUpperCase());
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                    AppResponse appResponse = response.body();
                    assert appResponse != null;
                    String response_code = appResponse.getResponseType();
                    if (response.body() != null) {
                        if (response_code.equals("200")) {
                            SPHelper.coupon_code=appResponse.getResponseModel().getCoupon_code();
                            SPHelper.coupon_id=appResponse.getResponseModel().getCoupon_id();
                            SPHelper.disc_amount=appResponse.getResponseModel().getDiscount_amount();
                            SPHelper.coupon_type=appResponse.getResponseModel().getCoupon_type();

                            // idPBLoading.setVisibility(View.GONE);

                            if(coming_from.equals("addon")){
                                Intent intent=new Intent(ApplyCouponList.this,Addons.class);
                                startActivity(intent);
                            }else{
                                Intent intent=new Intent(ApplyCouponList.this,Upgrade_and_Save.class);
                                startActivity(intent);
                            }

                        } else if (response_code.equals("300")) {
                            // idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(ApplyCouponList.this, appResponse.getResponseModel().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // idPBLoading.setVisibility(View.GONE);
                        Toast.makeText(ApplyCouponList.this, "internal server error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                    Toast.makeText(ApplyCouponList.this,
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
        if(coming_from.equals("addon")){
            Intent intent=new Intent(ApplyCouponList.this,Addons.class);
            startActivity(intent);
        }else{
            Intent intent=new Intent(ApplyCouponList.this,Upgrade_and_Save.class);
            startActivity(intent);
        }
    }

    private void hideKeybaord() {

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } }
}