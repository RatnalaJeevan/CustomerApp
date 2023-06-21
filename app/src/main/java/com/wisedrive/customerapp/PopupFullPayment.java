package com.wisedrive.customerapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.wisedrive.customerapp.adapters.AdapterSSList;
import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.PojoCreateOrder;
import com.wisedrive.customerapp.pojos.PojoPayParttial;
import com.wisedrive.customerapp.pojos.PojoSellPackage;
import com.wisedrive.customerapp.pojos.Pojo_Service_Includes;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopupFullPayment extends BottomSheetDialogFragment implements CFCheckoutResponseCallback {
    String orderID = "";
    String lead_id = "", c_id = "";
    String paymentSessionID = "";
    String payment_status = "", cforderid = "", final_amount = "";
    CFSession.Environment cfEnvironment = CFSession.Environment.PRODUCTION;
    TextView label1;
    Activity activity;
    private ApiInterface apiInterface;
    RelativeLayout confirm;
    RecyclerView rv_ss_list;
    ArrayList<Pojo_Service_Includes> pojo_service_includes;
    AdapterSSList adapterSSList;
    private DecimalFormat IndianCurrencyFormat;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_popup_full_payment, container, false);
        IndianCurrencyFormat = new DecimalFormat("##,##,###");

        label1=v.findViewById(R.id.label1);
        activity=getActivity();
        rv_ss_list=v.findViewById(R.id.rv_ss_list);
        confirm=v.findViewById(R.id.confirm);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        label1.setText("Rs "+IndianCurrencyFormat.format((int) SPHelper.remain_amount));
        try {
            CFPaymentGatewayService.getInstance().setCheckoutCallback(this);
        } catch (CFException e) {
            e.printStackTrace();
        }

        LinearLayoutManager l1=new LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false);
        adapterSSList=new AdapterSSList(SPHelper.pojo_service_includes,activity);
        rv_ss_list.setLayoutManager(l1);
        rv_ss_list.setAdapter(adapterSSList);
        adapterSSList.notifyDataSetChanged();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create_sessionID();
            }
        });
        return  v;
    }

    public void create_sessionID() {
        {
            if (!Connectivity.isNetworkConnected(activity)) {
                Toast.makeText(activity,
                        "Please Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            } else {
                //idPBLoading.setVisibility(View.VISIBLE);
                lead_id = SPHelper.getSPData(activity, SPHelper.lead_id, "");
                c_id = SPHelper.getSPData(activity, SPHelper.customer_id, "");
                String no = SPHelper.getSPData(activity, SPHelper.customer_phoneno, "");
                String email = SPHelper.getSPData(activity, SPHelper.cust_mail, "");
                String name = SPHelper.getSPData(activity, SPHelper.cust_name, "");
                if (c_id.equals("")) {
                    c_id = lead_id;
                }
                if (email == null || email.equals("null")) {
                    email = "";
                }
                if (name == null || name.equals("null")) {
                    name = "";
                }

                PojoCreateOrder pojoCFSession = new PojoCreateOrder(email, no, name, SPHelper.remain_amount, c_id);
                Call<AppResponse> call = apiInterface.generate_order(pojoCFSession);
                call.enqueue(new Callback<AppResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                        AppResponse appResponse = response.body();
                        assert appResponse != null;
                        String response_code = appResponse.getResponseType();
                        if (response.body() != null) {
                            if (response_code.equals("200")) {
                               // idPBLoading.setVisibility(View.GONE);
                                paymentSessionID = appResponse.getResponseModel().getCashfreeorderData().getPayment_session_id();
                                orderID = appResponse.getResponseModel().getCashfreeorderData().getOrder_id();
                                cforderid = appResponse.getResponseModel().getCashfreeorderData().getCf_order_id();
                                doDropCheckoutPayment();

                            } else if (response_code.equals("300")) {
                               // idPBLoading.setVisibility(View.GONE);
                                Toast.makeText(activity, "appResponse.getResponse().getMessage()", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                           // idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(activity, "internal server error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                        Toast.makeText(activity,
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                       // idPBLoading.setVisibility(View.GONE);
                    }
                });
            }
        }
    }

    public void doDropCheckoutPayment()
    {
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
            gatewayService.doPayment(activity, cfDropCheckoutPayment);
        } catch (CFException exception) {
            exception.printStackTrace();
        }
    }

    public void pay_remain_amount() {
        {
            if (!Connectivity.isNetworkConnected(activity)) {
                Toast.makeText(activity,
                        "Plaese Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            } else {
                // idPBLoading.setVisibility(View.VISIBLE);

                PojoPayParttial pojocomplete=new PojoPayParttial(SPHelper.dpp_id,SPHelper.remain_amount,payment_status,"online","",
                        cforderid,orderID,"",0,"");
                Call<AppResponse> call = apiInterface.complete_partial(pojocomplete);
                call.enqueue(new Callback<AppResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                        AppResponse appResponse = response.body();
                        assert appResponse != null;
                        String response_code = appResponse.getResponseType();
                        if (response.body() != null)
                        {
                            if (response_code.equals("200")) {

                                dismiss();
                                Toast.makeText(activity, appResponse.getResponseModel().getMessage(), Toast.LENGTH_SHORT).show();
                                if(SPHelper.fragment_is.equals("plans")){
                                    Intent intent=new Intent(activity,CustomerHomepage.class);
                                    startActivity(intent);
                                }else {
                                    ((Activity_Showroom_Services)activity).getVehServiceList();
                                }

//
                            } else if (response_code.equals("300")) {
                                Toast.makeText(activity, appResponse.getResponseModel().getMessage(), Toast.LENGTH_SHORT).show();
                           }
                        } else {
                            //idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(activity, "internal server error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                        Toast.makeText(activity,
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        // idPBLoading.setVisibility(View.GONE);
                    }
                });
            }
        }
    }
    @Override
    public void onPaymentVerify(String orderID) {
        payment_status = "paid";
        pay_remain_amount();
    }

    @Override
    public void onPaymentFailure(CFErrorResponse cfErrorResponse, String orderID) {
        payment_status = "not paid";
        pay_remain_amount();
    }

}