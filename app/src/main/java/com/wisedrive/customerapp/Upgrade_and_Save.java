package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wisedrive.customerapp.adapters.Adapter_Q_And_A;
import com.wisedrive.customerapp.adapters.Adapter_Service_Includes;
import com.wisedrive.customerapp.adapters.Adapter_Upgrade_Save;
import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.PojoSellPackage;
import com.wisedrive.customerapp.pojos.Pojo_Q_And_A;
import com.wisedrive.customerapp.pojos.Pojo_Upgrade_Save;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Upgrade_and_Save extends AppCompatActivity {
    private ApiInterface apiInterface;
    RecyclerView rv_upgrade;
    Adapter_Upgrade_Save adapter_upgrade_save;
    ArrayList<Pojo_Upgrade_Save> pojo_upgrade_saveArrayList;
    ArrayList<Pojo_Upgrade_Save> pojo1;
    String actcode="";
    RelativeLayout rl_upgrade_button,rl_back,rl_coupon_label,rl_coupon_applied;
    TextView tv_amount_buy,tv_upgrade_save,tv_dis_amount,tv_total_amount,coupon_label,coupon_code,
            ok,description,call_me;
    AppCompatButton pay_button;
    private DecimalFormat IndianCurrencyFormat;
    NestedScrollView nsv;
    RelativeLayout rl_no_upgrade;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade_and_save);
        call_me=findViewById(R.id.call_me);
        pay_button=findViewById(R.id.pay_button);
        rl_no_upgrade=findViewById(R.id.rl_no_upgrade);
        nsv=findViewById(R.id.nsv);
        ok=findViewById(R.id.ok);
        description=findViewById(R.id.description);
        IndianCurrencyFormat = new DecimalFormat("##,##,###");
        coupon_code=findViewById(R.id.coupon_code);
        coupon_label=findViewById(R.id.coupon_label);
        rl_coupon_label=findViewById(R.id.rl_coupon_label);
        rl_coupon_applied=findViewById(R.id.rl_coupon_applied);
        tv_upgrade_save=findViewById(R.id.tv_upgrade_save);
        tv_amount_buy=findViewById(R.id.tv_amount_buy);
        tv_dis_amount=findViewById(R.id.tv_dis_amount);
        tv_total_amount=findViewById(R.id.tv_total_amount);
        rv_upgrade=findViewById(R.id.rv_upgrade);
        rl_upgrade_button=findViewById(R.id.rl_upgrade_button);
        rl_back=findViewById(R.id.rl_back);
        apiInterface= ApiClient.getClient().create(ApiInterface.class);

        call_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+SPHelper.getSPData(Upgrade_and_Save.this,SPHelper.customer_support_phoneno,"")));
                startActivity(callIntent);
            }
        });
        if(!SPHelper.coupon_code.equals("")){
            rl_coupon_applied.setVisibility(View.VISIBLE);
            rl_coupon_label.setVisibility(View.INVISIBLE);
            coupon_code.setText(SPHelper.coupon_code.toUpperCase());
            coupon_label.setVisibility(View.VISIBLE);
            tv_total_amount.setText(IndianCurrencyFormat.format((int) (SPHelper.final_amount-SPHelper.disc_amount)));
            tv_dis_amount.setText("-"+String.valueOf((int)SPHelper.disc_amount));
        }else{
            rl_coupon_applied.setVisibility(View.INVISIBLE);
            rl_coupon_label.setVisibility(View.VISIBLE);
            coupon_label.setVisibility(View.GONE);
        }


        pay_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Upgrade_and_Save.this,Recommended_Activity.class);
                startActivity(intent);
            }
        });


        rl_coupon_label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Upgrade_and_Save.this, ApplyCouponList.class);
                intent.putExtra("comingfrom","upgrade");
                startActivity(intent);
            }
        });

        rl_coupon_applied.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //remove coupon
                rl_coupon_applied.setVisibility(View.INVISIBLE);
                rl_coupon_label.setVisibility(View.VISIBLE);
                coupon_label.setVisibility(View.GONE);
                SPHelper.disc_amount=0;
                SPHelper.coupon_code="";
                SPHelper.coupon_id="";
                SPHelper.coupon_type="";
                tv_dis_amount.setText("0");
                tv_total_amount.setText(IndianCurrencyFormat.format((int) SPHelper.final_amount));
            }
        });
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Upgrade_and_Save.this, CustomerHomepage.class);
                startActivity(intent);
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.fragment_is="cars";
                Intent intent = new Intent(Upgrade_and_Save.this, CustomerHomepage.class);
                startActivity(intent);
            }
        });

        get_paack_list();
    }

    public  void get_paack_list()
    {
        if (!Connectivity.isNetworkConnected(Upgrade_and_Save.this)) {
            Toast.makeText(Upgrade_and_Save.this,
                    "Plaese Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            //idPBLoading.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.get_pack_details(SPHelper.actcode);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                    AppResponse appResponse = response.body();
                    assert appResponse != null;
                    String response_code = appResponse.getResponseType();
                    if (response.body() != null) {
                        if (response_code.equals("200"))
                        {
                            nsv.setVisibility(View.VISIBLE);
                            rl_no_upgrade.setVisibility(View.GONE);
                            //idPBLoading.setVisibility(View.GONE);
                            pojo_upgrade_saveArrayList = new ArrayList<>();
                            pojo1=new ArrayList<>();
                            pojo1=appResponse.getResponseModel().getUpgradeproductList();
                            pojo1.addAll(appResponse.getResponseModel().getUpgradeservicesList());
                            pojo_upgrade_saveArrayList.addAll(pojo1);

                            adapter_upgrade_save = new Adapter_Upgrade_Save(Upgrade_and_Save.this, pojo_upgrade_saveArrayList);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Upgrade_and_Save.this,
                            LinearLayoutManager.VERTICAL, false);
                            rv_upgrade.setLayoutManager(linearLayoutManager);
                            rv_upgrade.setAdapter(adapter_upgrade_save);
                            double amount=appResponse.getResponseModel().getUpgradedescription().getFinal_price();
                            String des=appResponse.getResponseModel().getUpgradedescription().getUpselling_package_description();
                            SPHelper.msg=appResponse.getResponseModel().getUpgradedescription().getMsg();
                            SPHelper.cat_id= appResponse.getResponseModel().getUpgradedescription().getCategory_id();
                            SPHelper.main_pack_id=appResponse.getResponseModel().getUpgradedescription().getMain_package_id();
                            SPHelper.package_id=appResponse.getResponseModel().getUpgradedescription().getPackage_id();
                            SPHelper.veh_id=appResponse.getResponseModel().getUpgradedescription().getVehicle_id();

                            tv_amount_buy.setText(IndianCurrencyFormat.format((int) amount));
                            SPHelper.final_amount=amount;
                            tv_total_amount.setText(IndianCurrencyFormat.format((int) (SPHelper.final_amount-SPHelper.disc_amount)));
                            tv_upgrade_save.setText(des);
                            Upgrade_and_Save.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter_upgrade_save.notifyDataSetChanged();
                                }
                            });

                        } else if (response_code.equals("300")) {
                            //idPBLoading.setVisibility(View.GONE);
                            description.setText(appResponse.getResponseModel().getMessage());
                            nsv.setVisibility(View.GONE);
                            rl_no_upgrade.setVisibility(View.VISIBLE);
                        }
                    } else {
                        //idPBLoading.setVisibility(View.GONE);
                        Toast.makeText(Upgrade_and_Save.this, "internal server error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                    Toast.makeText(Upgrade_and_Save.this,
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
        Intent intent = new Intent(Upgrade_and_Save.this, CustomerHomepage.class);
        startActivity(intent);
    }
}