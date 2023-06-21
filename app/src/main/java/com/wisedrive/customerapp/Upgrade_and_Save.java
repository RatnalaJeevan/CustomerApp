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
import com.wisedrive.customerapp.adapters.Adapter_Q_And_A;
import com.wisedrive.customerapp.adapters.Adapter_Service_Includes;
import com.wisedrive.customerapp.adapters.Adapter_Upgrade_Save;
import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.PojoCreateOrder;
import com.wisedrive.customerapp.pojos.PojoSellPackage;
import com.wisedrive.customerapp.pojos.PojoUpgradePackage;
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

public class Upgrade_and_Save extends AppCompatActivity implements CFCheckoutResponseCallback {
    String lead_id,c_id;
    private ApiInterface apiInterface;
    RecyclerView rv_upgrade;
    Adapter_Upgrade_Save adapter_upgrade_save;
    ArrayList<Pojo_Upgrade_Save> pojo_upgrade_saveArrayList;
    ArrayList<Pojo_Upgrade_Save> pojo1;
    String actcode="";
    RelativeLayout rl_back,rl_coupon_label,rl_coupon_applied;
    TextView tv_amount_buy,tv_upgrade_save,tv_dis_amount,tv_total_amount,coupon_label,coupon_code,pay,
            ok,description,tvamount,tv_disamount,label_save;
    private DecimalFormat IndianCurrencyFormat;
    NestedScrollView nsv;
    RelativeLayout rl_no_upgrade,rl3;
    String orderID = "";
    String paymentSessionID = "";
    String payment_status = "", cforderid = "", final_amount = "";
    CFSession.Environment cfEnvironment = CFSession.Environment.PRODUCTION;
    ProgressBar idPBLoading;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade_and_save);
        getWindow().setStatusBarColor(getColor(R.color.white));
        try {
            CFPaymentGatewayService.getInstance().setCheckoutCallback(this);
        } catch (CFException e) {
            e.printStackTrace();
        }
        idPBLoading=findViewById(R.id.idPBLoading);
        rl3=findViewById(R.id.rl3);
        pay=findViewById(R.id.pay);
        label_save=findViewById(R.id.label_save);
        tv_disamount=findViewById(R.id.tv_disamount);
        rl_no_upgrade=findViewById(R.id.rl_no_upgrade);
        nsv=findViewById(R.id.nsv);
        ok=findViewById(R.id.ok);
        description=findViewById(R.id.description);
        IndianCurrencyFormat = new DecimalFormat("##,##,###");
        coupon_code=findViewById(R.id.coupon_code);
        coupon_label=findViewById(R.id.coupon_label);
        rl_coupon_applied=findViewById(R.id.rl_coupon_applied);
        tv_upgrade_save=findViewById(R.id.tv_upgrade_save);
        tv_amount_buy=findViewById(R.id.tv_amount_buy);
        tv_dis_amount=findViewById(R.id.tv_dis_amount);
        tv_total_amount=findViewById(R.id.tv_total_amount);
        rv_upgrade=findViewById(R.id.rv_upgrade);
        rl_back=findViewById(R.id.rl_back);
        tvamount=findViewById(R.id.tvamount);
        apiInterface= ApiClient.getClient().create(ApiInterface.class);

        rl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+SPHelper.getSPData(Upgrade_and_Save.this,SPHelper.customer_support_phoneno,"")));
                startActivity(callIntent);
            }
        });


        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create_sessionID();
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
                            double og_amount=appResponse.getResponseModel().getUpgradedescription().getOriginal_price();
                            String des=appResponse.getResponseModel().getUpgradedescription().getUpselling_package_description();
                            SPHelper.msg=appResponse.getResponseModel().getUpgradedescription().getMsg();
                            SPHelper.cat_id= appResponse.getResponseModel().getUpgradedescription().getCategory_id();
                            SPHelper.main_pack_id=appResponse.getResponseModel().getUpgradedescription().getMain_package_id();
                            SPHelper.package_id=appResponse.getResponseModel().getUpgradedescription().getPackage_id();
                            SPHelper.veh_id=appResponse.getResponseModel().getUpgradedescription().getVehicle_id();

                            SPHelper.final_amount=amount;
                            tvamount.setText(IndianCurrencyFormat.format((int) (SPHelper.final_amount)));
                            tv_disamount.setText(IndianCurrencyFormat.format((int) (og_amount)));
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


    public void create_sessionID() {
        {
            if (!Connectivity.isNetworkConnected(Upgrade_and_Save.this)) {
                Toast.makeText(Upgrade_and_Save.this,
                        "Please Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            } else {
                idPBLoading.setVisibility(View.VISIBLE);
                lead_id = SPHelper.getSPData(Upgrade_and_Save.this, SPHelper.lead_id, "");
                c_id = SPHelper.getSPData(Upgrade_and_Save.this, SPHelper.customer_id, "");
                String no = SPHelper.getSPData(Upgrade_and_Save.this, SPHelper.customer_phoneno, "");
                String email = SPHelper.getSPData(Upgrade_and_Save.this, SPHelper.cust_mail, "");
                String name = SPHelper.getSPData(Upgrade_and_Save.this, SPHelper.cust_name, "");

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
                                Toast.makeText(Upgrade_and_Save.this, "appResponse.getResponse().getMessage()", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(Upgrade_and_Save.this, "internal server error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                        Toast.makeText(Upgrade_and_Save.this,
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
            gatewayService.doPayment(Upgrade_and_Save.this, cfDropCheckoutPayment);
        } catch (CFException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void onPaymentVerify(String orderID) {
        payment_status = "paid";
        upgrade_pack();
    }

    @Override
    public void onPaymentFailure(CFErrorResponse cfErrorResponse, String orderID) {
        payment_status = "not paid";
        upgrade_pack();
    }

    public void upgrade_pack() {
        {
            if (!Connectivity.isNetworkConnected(Upgrade_and_Save.this)) {
                Toast.makeText(Upgrade_and_Save.this,
                        "Plaese Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            } else {
                // idPBLoading.setVisibility(View.VISIBLE);

                if(SPHelper.veh_id==null||SPHelper.veh_id.equals("null")){
                    SPHelper.veh_id="";
                }
                if(SPHelper.lead_veh_id==null||SPHelper.lead_veh_id.equals("null")){
                    SPHelper.lead_veh_id="";
                }
//                PojoUpgradePackage pojoSellPackage = new PojoUpgradePackage(SPHelper.package_id, "", SPHelper.main_pack_id,
//                        SPHelper.cat_id, SPHelper.final_amount-SPHelper.disc_amount, payment_status, "online", "", cforderid, orderID,
//                        "", SPHelper.disc_amount, "", lead_id, SPHelper.veh_id,
//                        c_id,SPHelper.lead_veh_id,"Y",
//                        "N", "", "",SPHelper.coupon_id,SPHelper.coupon_type,SPHelper.disc_amount);

                PojoSellPackage pojo_upgradee = new PojoSellPackage(SPHelper.package_id, "", SPHelper.main_pack_id,
                        SPHelper.cat_id, SPHelper.final_amount, payment_status, "online", "", cforderid, orderID,
                        "", SPHelper.disc_amount, "", lead_id, c_id,
                        SPHelper.lead_veh_id, "Y", "N", "", "",
                        SPHelper.coupon_id,SPHelper.coupon_type,0,SPHelper.veh_id,
                        "","","","N",
                        SPHelper.final_amount,0,"Y");
                Call<AppResponse> call = apiInterface.sell_package(pojo_upgradee);
                call.enqueue(new Callback<AppResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                        AppResponse appResponse = response.body();
                        assert appResponse != null;
                        String response_code = appResponse.getResponseType();
                        if (response.body() != null) {
                            if (response_code.equals("200")) {
                                SPHelper.isSuccess = "y";
                                SPHelper.dpp_id=appResponse.getResponseModel().getVehicleObj().getDppId();
                                Intent intent=new Intent(Upgrade_and_Save.this,Activation_Confirmation_Activity.class);
                                startActivity(intent);
                            } else if (response_code.equals("300")) {

                                SPHelper.isSuccess = "n";
                                SPHelper.cf_msg=appResponse.getResponseModel().getMessage();
                                CongratsPage bottomSheetDialogFragment = new CongratsPage();
                                bottomSheetDialogFragment.coming_from="upgrade";
                                bottomSheetDialogFragment.show(Upgrade_and_Save.this.getSupportFragmentManager(), "CongratsPage");
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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Upgrade_and_Save.this, CustomerHomepage.class);
        startActivity(intent);
    }
}