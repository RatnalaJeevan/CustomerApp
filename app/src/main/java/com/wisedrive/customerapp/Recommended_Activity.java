package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.commonclasses.Common;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.PojoCreateOrder;
import com.wisedrive.customerapp.pojos.PojoSellPackage;
import com.wisedrive.customerapp.pojos.PojoUpgradePackage;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Recommended_Activity extends AppCompatActivity implements CFCheckoutResponseCallback {
    RelativeLayout rl_upgrade_save_button,rl_no_thanks;
    String lead_id,c_id;
    private ApiInterface apiInterface;
    String orderID = "";
    String paymentSessionID = "";
    String payment_status = "", cforderid = "", final_amount = "";
    CFSession.Environment cfEnvironment = CFSession.Environment.SANDBOX;
    ProgressBar idPBLoading;
    TextView do_u_know;
    private static Recommended_Activity instance;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended);
        instance=this;
        do_u_know=findViewById(R.id.do_u_know);
        idPBLoading=findViewById(R.id.idPBLoading);
        rl_no_thanks=findViewById(R.id.rl_no_thanks);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        try {
            CFPaymentGatewayService.getInstance().setCheckoutCallback(this);
        } catch (CFException e) {
            e.printStackTrace();
        }
        rl_upgrade_save_button=findViewById(R.id.rl_upgrade_save_button);
        rl_upgrade_save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create_sessionID();
            }
        });
        do_u_know.setText(SPHelper.msg);
        rl_no_thanks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.fragment_is="cars";
                Intent intent=new Intent(Recommended_Activity.this,CustomerHomepage.class);
                startActivity(intent);
            }
        });
    }

    public void create_sessionID() {
        {
            if (!Connectivity.isNetworkConnected(Recommended_Activity.this)) {
                Toast.makeText(Recommended_Activity.this,
                        "Please Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            } else {
                idPBLoading.setVisibility(View.VISIBLE);
                lead_id = SPHelper.getSPData(Recommended_Activity.this, SPHelper.lead_id, "");
                c_id = SPHelper.getSPData(Recommended_Activity.this, SPHelper.customer_id, "");
                String no = SPHelper.getSPData(Recommended_Activity.this, SPHelper.customer_phoneno, "");
                String email = SPHelper.getSPData(Recommended_Activity.this, SPHelper.cust_mail, "");
                String name = SPHelper.getSPData(Recommended_Activity.this, SPHelper.cust_name, "");

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
                                Toast.makeText(Recommended_Activity.this, "appResponse.getResponse().getMessage()", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                             idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(Recommended_Activity.this, "internal server error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                        Toast.makeText(Recommended_Activity.this,
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
            gatewayService.doPayment(Recommended_Activity.this, cfDropCheckoutPayment);
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
            if (!Connectivity.isNetworkConnected(Recommended_Activity.this)) {
                Toast.makeText(Recommended_Activity.this,
                        "Plaese Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            } else {
                // idPBLoading.setVisibility(View.VISIBLE);

                PojoUpgradePackage pojoSellPackage = new PojoUpgradePackage(SPHelper.package_id, "", SPHelper.main_pack_id,
                        SPHelper.cat_id, SPHelper.final_amount-SPHelper.disc_amount, payment_status, "online", "", cforderid, orderID,
                        "", SPHelper.disc_amount, "", lead_id, SPHelper.veh_id,
                        c_id, SPHelper.veh_id,"Y",
                        "N", "", "",SPHelper.coupon_id,SPHelper.coupon_type,SPHelper.disc_amount);
                Call<AppResponse> call = apiInterface.upgrade_package(pojoSellPackage);
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
                                  Intent intent=new Intent(Recommended_Activity.this,Activation_Confirmation_Activity.class);
                                  startActivity(intent);
                            } else if (response_code.equals("300")) {

                                SPHelper.isSuccess = "n";
                                SPHelper.cf_msg=appResponse.getResponseModel().getMessage();
                                CongratsPage bottomSheetDialogFragment = new CongratsPage();
                                bottomSheetDialogFragment.coming_from="upgrade";
                                bottomSheetDialogFragment.show(Recommended_Activity.this.getSupportFragmentManager(), "CongratsPage");
                            }
                        } else {
                            //idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(Recommended_Activity.this, "internal server error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                        Toast.makeText(Recommended_Activity.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        // idPBLoading.setVisibility(View.GONE);
                    }
                });
            }
        }
    }

    public static Recommended_Activity getInstance(){
        return instance;
    }

}