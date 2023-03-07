package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cashfree.pg.api.CFPaymentGatewayService;
import com.cashfree.pg.core.api.CFSession;
import com.cashfree.pg.core.api.CFTheme;
import com.cashfree.pg.core.api.callback.CFCheckoutResponseCallback;
import com.cashfree.pg.core.api.exception.CFException;
import com.cashfree.pg.core.api.utils.CFErrorResponse;
import com.cashfree.pg.ui.api.CFDropCheckoutPayment;
import com.wisedrive.customerapp.adapters.Adapter_Addons_list;
import com.wisedrive.customerapp.adapters.Adapter_Service_List;
import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.commonclasses.Common;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.PojoCreateOrder;
import com.wisedrive.customerapp.pojos.PojoSellPackage;
import com.wisedrive.customerapp.pojos.Pojo_Class_Addons_List;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Addons extends AppCompatActivity implements CFCheckoutResponseCallback {
    ProgressBar idPBLoading;
    RecyclerView rv_addons_plan;
    Adapter_Addons_list adapter_addons_list;
    ArrayList<Pojo_Class_Addons_List> pojo_addon_list;
    public TextView tv_amount_buy,tv_total_amount,apply_coupon_label,coupon_code,tv_dis_amount,coupon_label;
    AppCompatButton pay_button;
    public RelativeLayout rl_back_button,rl_coupon_applied;
    private ApiInterface apiInterface;
    String orderID = "";
    String paymentSessionID = "";
    String payment_status = "", cforderid = "", final_amount = "";
    CFSession.Environment cfEnvironment = CFSession.Environment.PRODUCTION;
    private DecimalFormat IndianCurrencyFormat;
    public static Addons instance;
    String lead_id = "", c_id = "";
    String is_multiple_addon="N";
    public RelativeLayout rl_skip,rl_coupon_label;
    ImageView iv2;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addons);
        try {
            CFPaymentGatewayService.getInstance().setCheckoutCallback(this);
        } catch (CFException e) {
            e.printStackTrace();
        }
        instance = this;
        IndianCurrencyFormat = new DecimalFormat("##,##,###");
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        idPBLoading=findViewById(R.id.idPBLoading);
        tv_dis_amount=findViewById(R.id.tv_dis_amount);
        coupon_label=findViewById(R.id.coupon_label);
        iv2=findViewById(R.id.iv2);
        coupon_code=findViewById(R.id.coupon_code);
        rl_coupon_applied=findViewById(R.id.rl_coupon_applied);
        apply_coupon_label=findViewById(R.id.apply_coupon_label);
        tv_total_amount=findViewById(R.id.tv_total_amount);
        rl_coupon_label=findViewById(R.id.rl_coupon_label);
        rl_skip=findViewById(R.id.rl_skip);
        rv_addons_plan = findViewById(R.id.rv_addons_plan);
        tv_amount_buy = findViewById(R.id.tv_amount_buy);
        pay_button = findViewById(R.id.pay_button);

        int y = (int) SPHelper.final_amount;
        final_amount = IndianCurrencyFormat.format(y);
        tv_amount_buy.setText(final_amount);
        tv_total_amount.setText(IndianCurrencyFormat.format((int)(SPHelper.final_amount-SPHelper.disc_amount)));

        if(!SPHelper.coupon_code.equals("")){
            rl_coupon_applied.setVisibility(View.VISIBLE);
            rl_coupon_label.setVisibility(View.INVISIBLE);
            coupon_code.setText(SPHelper.coupon_code.toUpperCase());
            coupon_label.setVisibility(View.VISIBLE);
            tv_dis_amount.setText("-"+String.valueOf((int)SPHelper.disc_amount));
        }else{
            rl_coupon_applied.setVisibility(View.INVISIBLE);
            rl_coupon_label.setVisibility(View.VISIBLE);
            coupon_label.setVisibility(View.GONE);
        }

        pay_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = 0;
                SPHelper.selected_addon_id = "";
                for (int i = 0; i < SPHelper.addon_list.size(); i++)
                {
                    if (SPHelper.addon_list.get(i).getIsSelected().equalsIgnoreCase("y"))
                    {
                        count = count + 1;
                        if (count == 1) {
                            is_multiple_addon="N";
                            SPHelper.selected_addon_id = SPHelper.addon_list.get(i).getAddon_id();
                        } else {
                            is_multiple_addon="Y";
                            SPHelper.selected_addon_id = SPHelper.selected_addon_id + "," + SPHelper.addon_list.get(i).getAddon_id();
                        }
                    } else {
                    }
                }
                System.out.println("addon_id" + SPHelper.selected_addon_id);

                create_sessionID();
//
//                Intent intent=new Intent(Addons.this,Confirmation_Activity.class);
//                startActivity(intent);
            }
        });

        rl_coupon_label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(Addons.this, ApplyCouponList.class);
                    intent.putExtra("comingfrom","addon");
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
        rl_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.disc_amount=0;
                SPHelper.final_amount = SPHelper.pack_amount;
                SPHelper.selected_addon_id = "";
                is_multiple_addon="N";
                create_sessionID();
            }
        });
        rl_back_button = findViewById(R.id.rl_back_button);
        rl_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Addons.this, Warranty_Description.class);
                startActivity(intent);
            }
        });

        getAddonList();
    }

    public void getAddonList() {
        if (!Connectivity.isNetworkConnected(Addons.this)) {
            Toast.makeText(Addons.this,
                    "Plaese Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        } else {
            idPBLoading.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.getAddonList(SPHelper.getSPData(Addons.this, SPHelper.lead_id, ""),
                    SPHelper.getSPData(Addons.this, SPHelper.customer_id, ""), SPHelper.package_id,
                    SPHelper.cat_id, SPHelper.main_pack_id);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                    AppResponse appResponse = response.body();
                    assert appResponse != null;
                    String response_code = appResponse.getResponseType();
                    if (response.body() != null) {
                        if (response_code.equals("200")) {
                             idPBLoading.setVisibility(View.GONE);

                            pojo_addon_list = new ArrayList<>();
                            pojo_addon_list = appResponse.getResponseModel().getAddonList();
                            if(SPHelper.addon_list.isEmpty()){
                                SPHelper.addon_list=pojo_addon_list;
                            }
                            adapter_addons_list = new Adapter_Addons_list(Addons.this, SPHelper.addon_list);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Addons.this, LinearLayoutManager.VERTICAL, false);
                            rv_addons_plan.setLayoutManager(linearLayoutManager);
                            rv_addons_plan.setAdapter(adapter_addons_list);

                            Addons.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter_addons_list.notifyDataSetChanged();
                                }
                            });
                        } else if (response_code.equals("300")) {
                             idPBLoading.setVisibility(View.GONE);
                        }
                    } else {
                         idPBLoading.setVisibility(View.GONE);
                        Toast.makeText(Addons.this, "internal server error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                    Toast.makeText(Addons.this,
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                     idPBLoading.setVisibility(View.GONE);
                }
            });
        }
    }

    public void create_sessionID() {
        {
            if (!Connectivity.isNetworkConnected(Addons.this)) {
                Toast.makeText(Addons.this,
                        "Please Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            } else {
                  idPBLoading.setVisibility(View.VISIBLE);
                lead_id = SPHelper.getSPData(Addons.this, SPHelper.lead_id, "");
                c_id = SPHelper.getSPData(Addons.this, SPHelper.customer_id, "");
                String no = SPHelper.getSPData(Addons.this, SPHelper.customer_phoneno, "");
                String email = SPHelper.getSPData(Addons.this, SPHelper.cust_mail, "");
                String name = SPHelper.getSPData(Addons.this, SPHelper.cust_name, "");
                if (c_id.equals("")) {
                    c_id = lead_id;
                }
                if (email == null || email.equals("null")) {
                    email = "";
                }
                if (name == null || name.equals("null")) {
                    name = "";
                }
                PojoCreateOrder pojoCFSession = new PojoCreateOrder(email, no, name, SPHelper.final_amount-SPHelper.disc_amount, c_id);
                Call<AppResponse> call = apiInterface.generate_order(pojoCFSession);
                call.enqueue(new Callback<AppResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                        AppResponse appResponse = response.body();
                        assert appResponse != null;
                        String response_code = appResponse.getResponseType();
                        if (response.body() != null) {
                            if (response_code.equals("200")) {
                                 idPBLoading.setVisibility(View.GONE);
                                paymentSessionID = appResponse.getResponseModel().getCashfreeorderData().getPayment_session_id();
                                orderID = appResponse.getResponseModel().getCashfreeorderData().getOrder_id();
                                cforderid = appResponse.getResponseModel().getCashfreeorderData().getCf_order_id();

                                doDropCheckoutPayment();

                            } else if (response_code.equals("300")) {
                                  idPBLoading.setVisibility(View.GONE);
                                Toast.makeText(Addons.this, "appResponse.getResponse().getMessage()", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                             idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(Addons.this, "internal server error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                        Toast.makeText(Addons.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                         idPBLoading.setVisibility(View.GONE);
                    }
                });
            }
        }
    }

    public void doDropCheckoutPayment() {
        try {
            CFSession cfSession = new CFSession.CFSessionBuilder()
                    .setEnvironment(cfEnvironment)
                    .setPaymentSessionID(paymentSessionID)
                    .setOrderId(orderID)
                    .build();

            CFTheme cfTheme = new CFTheme.CFThemeBuilder()
                    .setNavigationBarBackgroundColor("#0057CC")
                    .setNavigationBarTextColor("#ffffff")
                    .setButtonBackgroundColor("#0057CC")
                    .setButtonTextColor("#ffffff")
                    .setPrimaryTextColor("#000000")
                    .setSecondaryTextColor("#000000")
                    .build();
            CFDropCheckoutPayment cfDropCheckoutPayment = new CFDropCheckoutPayment.CFDropCheckoutPaymentBuilder()
                    .setSession(cfSession)
                    // .setCFUIPaymentModes(cfPaymentComponent)
                    .setCFNativeCheckoutUITheme(cfTheme)
                    .build();
            CFPaymentGatewayService gatewayService = CFPaymentGatewayService.getInstance();
            gatewayService.doPayment(Addons.this, cfDropCheckoutPayment);
        } catch (CFException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void onPaymentVerify(String orderID) {
        Log.e("onPaymentVerify", "verifyPayment triggered");
        payment_status = "paid";
        buyAddOnPack();
    }

    @Override
    public void onPaymentFailure(CFErrorResponse cfErrorResponse, String orderID) {
        Log.e("onPaymentFailure " + orderID, cfErrorResponse.getMessage());
        payment_status = "not paid";
        buyAddOnPack();

    }

    public static Addons getInstance() {
        return instance;
    }

    public void buyAddOnPack() {
        {
            if (!Connectivity.isNetworkConnected(Addons.this)) {
                Toast.makeText(Addons.this,
                        "Plaese Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            } else {
                // idPBLoading.setVisibility(View.VISIBLE);

                PojoSellPackage pojoSellPackage = new PojoSellPackage(SPHelper.package_id, SPHelper.selected_addon_id, SPHelper.main_pack_id,
                        SPHelper.cat_id, SPHelper.final_amount-SPHelper.disc_amount, payment_status, "online", "", cforderid, orderID,
                        "", SPHelper.disc_amount, "", lead_id, SPHelper.getSPData(Addons.this, SPHelper.customer_id, ""),
                        SPHelper.lead_veh_id, "Y", is_multiple_addon, "", "",SPHelper.coupon_id,SPHelper.coupon_type,SPHelper.disc_amount,SPHelper.veh_id);
                Call<AppResponse> call = apiInterface.sell_package(pojoSellPackage);
                call.enqueue(new Callback<AppResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                        AppResponse appResponse = response.body();
                        assert appResponse != null;
                        String response_code = appResponse.getResponseType();
                        if (response.body() != null) {
                            if (response_code.equals("200")) {

                                SPHelper.isSuccess = "y";
                                SPHelper.veh_id=appResponse.getResponseModel().getVehicleObj().getVehicleId();
                                SPHelper.veh_no=appResponse.getResponseModel().getVehicleObj().getVehicleNum();
                                SPHelper.dpp_id=appResponse.getResponseModel().getVehicleObj().getDppId();

                                SPHelper.saveSPdata(Addons.this,SPHelper.customer_id,appResponse.getResponseModel().getVehicleObj().getCustomer_id());
                                Toast.makeText(Addons.this, "payment success", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(Addons.this,Confirmation_Activity.class);
                                intent.putExtra("refer_no",appResponse.getResponseModel().getVehicleObj().getRefNo());
                                intent.putExtra("is_warranty",appResponse.getResponseModel().getVehicleObj().getIswarranty());
                                intent.putExtra("successmsg",appResponse.getResponseModel().getVehicleObj().getSuccessmsg());
                                startActivity(intent);
                            } else if (response_code.equals("300")) {

                                SPHelper.isSuccess = "n";
                                SPHelper.cf_msg=appResponse.getResponseModel().getMessage();
                                CongratsPage bottomSheetDialogFragment = new CongratsPage();
                                bottomSheetDialogFragment.coming_from="addon";
                                bottomSheetDialogFragment.show(Addons.this.getSupportFragmentManager(), "CongratsPage");
                               // Toast.makeText(Addons.this, "payment failure", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            //idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(Addons.this, "internal server error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                        Toast.makeText(Addons.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        // idPBLoading.setVisibility(View.GONE);
                    }
                });
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Addons.this, Warranty_Description.class);
        startActivity(intent);
    }
}