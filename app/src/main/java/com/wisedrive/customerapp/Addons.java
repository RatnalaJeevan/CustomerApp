package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.j256.ormlite.stmt.query.In;
import com.wisedrive.customerapp.adapters.AdapterSelAddons;
import com.wisedrive.customerapp.adapters.AdapterUpgrade;
import com.wisedrive.customerapp.adapters.Adapter_Addons_list;
import com.wisedrive.customerapp.adapters.Adapter_Service_List;
import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.commonclasses.Common;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.PojoCreateOrder;
import com.wisedrive.customerapp.pojos.PojoSelAddOnn;
import com.wisedrive.customerapp.pojos.PojoSellPackage;
import com.wisedrive.customerapp.pojos.PojoUpgrade;
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
    double c_paying_amout;
    RelativeLayout rl_add_ser,rl_upgrades;
    TextView tv1,tv2,tv_partial,amount_saved,no_result;
    View v1,v2;
    ProgressBar idPBLoading;
    RecyclerView rv_addons_plan,rv_upgrade_list;
    Adapter_Addons_list adapter_addons_list;
    ArrayList<Pojo_Class_Addons_List> pojo_addon_list=new ArrayList<>();
    public TextView tv_amount_buy,tv_total_amount,apply_coupon_label,coupon_code,tv_dis_amount,coupon_label;
    TextView pay_button,pay_partial;
    public RelativeLayout rl_back_button,rl_coupon_applied;
    private ApiInterface apiInterface;
    String orderID = "";
    String paymentSessionID = "";
    String payment_status = "", cforderid = "", final_amount = "",paack_id="";
    CFSession.Environment cfEnvironment = CFSession.Environment.SANDBOX;
    private DecimalFormat IndianCurrencyFormat;
    public static Addons instance;
    String lead_id = "", c_id = "";
    String is_multiple_addon="N";
    public RelativeLayout rl_skip,rl_coupon_label,rl_disc;
    ImageView iv2;
    AdapterUpgrade adapterUpgrade;
    ArrayList<PojoUpgrade> pojoUpgrades;
    RecyclerView rv_sel_addon_list;
    ArrayList<PojoSelAddOnn> selec_add_ons_list;
    AdapterSelAddons adapterSelAddons;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addons);
        getWindow().setStatusBarColor(getColor(R.color.new_app_bg));
        try {
            CFPaymentGatewayService.getInstance().setCheckoutCallback(this);
        } catch (CFException e) {
            e.printStackTrace();
        }
        instance = this;
        IndianCurrencyFormat = new DecimalFormat("##,##,###");
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        rl_disc=findViewById(R.id.rl_disc);
        no_result=findViewById(R.id.no_result);
        amount_saved=findViewById(R.id.amount_saved);
        rv_sel_addon_list=findViewById(R.id.rv_sel_addon_list);
        tv_partial=findViewById(R.id.tv_partial);
        rv_upgrade_list=findViewById(R.id.rv_upgrade_list);
        rl_upgrades=findViewById(R.id.rl_upgrades);
        rl_add_ser=findViewById(R.id.rl_add_ser);
        tv2=findViewById(R.id.tv2);
        tv1=findViewById(R.id.tv1);
        v1=findViewById(R.id.v1);
        v2=findViewById(R.id.v2);
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


        if(!SPHelper.coupon_code.equals("")){
            rl_coupon_applied.setVisibility(View.VISIBLE);
            rl_coupon_label.setVisibility(View.GONE);
            coupon_code.setText(SPHelper.coupon_code.toUpperCase());
            coupon_label.setVisibility(View.VISIBLE);
            rl_disc.setVisibility(View.VISIBLE);
            tv_dis_amount.setText(String.valueOf((int)SPHelper.disc_amount));
            amount_saved.setText(String.valueOf((int)SPHelper.disc_amount));

        }else{
            rl_coupon_applied.setVisibility(View.GONE);
            rl_coupon_label.setVisibility(View.VISIBLE);
            rl_disc.setVisibility(View.GONE);
            coupon_label.setVisibility(View.GONE);
            SPHelper.gone_to="";
        }
        get_update_amounnt();
        if(SPHelper.is_upgrade.equals("y"))
        {
            tv1.setTextColor(Color.parseColor("#D3D3D3"));
            tv2.setTextColor(Color.parseColor("#252a40"));
            v2.setVisibility(View.VISIBLE);
            v1.setVisibility(View.GONE);
            rv_addons_plan.setVisibility(View.GONE);
            rv_upgrade_list.setVisibility(View.VISIBLE);

            gett_upgrade_list();

        }else if(SPHelper.is_upgrade.equals("")){
            rl_upgrades.setVisibility(View.GONE);
            no_result.setVisibility(View.GONE);
        }
        else {
            no_result.setVisibility(View.GONE);
            tv1.setTextColor(Color.parseColor("#252a40"));
            tv2.setTextColor(Color.parseColor("#D3D3D3"));
            v1.setVisibility(View.VISIBLE);
            v2.setVisibility(View.GONE);
            rv_addons_plan.setVisibility(View.VISIBLE);
            rv_upgrade_list.setVisibility(View.GONE);


        }

        tv_partial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_coupon_applied.setVisibility(View.GONE);
                rl_coupon_label.setVisibility(View.VISIBLE);
                coupon_label.setVisibility(View.GONE);
                SPHelper.disc_amount=0;
                SPHelper.coupon_code="";
                SPHelper.coupon_id="";
                SPHelper.coupon_type="";
                rl_disc.setVisibility(View.GONE);
                tv_dis_amount.setText("0");
                tv_total_amount.setText(IndianCurrencyFormat.format((int) ((SPHelper.upgrade_amount+SPHelper.add_on_amount)-SPHelper.disc_amount)));

                PopupShowPartialAmount bottomSheetDialogFragment = new PopupShowPartialAmount();
                bottomSheetDialogFragment.show(Addons.this.getSupportFragmentManager(), "Popup");
            }
        });

        pay_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = 0;
                SPHelper.selected_addon_id = "";
                SPHelper.is_part="N";
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

                if((SPHelper.add_on_amount+SPHelper.upgrade_amount)==0){
                    Toast.makeText(Addons.this,"Please select package",Toast.LENGTH_SHORT).show();
                }else {
                    create_sessionID();
                }


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
                rl_coupon_applied.setVisibility(View.GONE);
                rl_coupon_label.setVisibility(View.VISIBLE);
                coupon_label.setVisibility(View.GONE);
                SPHelper.disc_amount=0;
                SPHelper.coupon_code="";
                SPHelper.coupon_id="";
                SPHelper.coupon_type="";
                SPHelper.gone_to="";
                rl_disc.setVisibility(View.GONE);
                tv_dis_amount.setText("0");
                tv_total_amount.setText(IndianCurrencyFormat.format((int) ((SPHelper.upgrade_amount+SPHelper.add_on_amount)-SPHelper.disc_amount)));
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


        rl_add_ser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv1.setTextColor(Color.parseColor("#252a40"));
                tv2.setTextColor(Color.parseColor("#D3D3D3"));
                v1.setVisibility(View.VISIBLE);
                v2.setVisibility(View.GONE);
                rv_addons_plan.setVisibility(View.VISIBLE);
                rv_upgrade_list.setVisibility(View.GONE);
                no_result.setVisibility(View.GONE);
            }
        });

        rl_upgrades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv1.setTextColor(Color.parseColor("#D3D3D3"));
                tv2.setTextColor(Color.parseColor("#252a40"));
                v2.setVisibility(View.VISIBLE);
                v1.setVisibility(View.GONE);
                rv_addons_plan.setVisibility(View.GONE);
                rv_upgrade_list.setVisibility(View.VISIBLE);
                gett_upgrade_list();
            }
        });



        getAddonList();
        get_sel_add_on_list();

    }

    public  void get_sel_add_on_list()
    {

        selec_add_ons_list = new ArrayList<>();
       // SPHelper.sel_addon_list=new ArrayList<>();
        for(int i=0;i<SPHelper.addon_list.size();i++)
        {

            if(SPHelper.addon_list.get(i).getIsSelected().equalsIgnoreCase("y"))
            {
                PojoSelAddOnn pojoSelAddOnn=new PojoSelAddOnn();
                pojoSelAddOnn.setAdd_on_naame(SPHelper.addon_list.get(i).getAddon_name());
                pojoSelAddOnn.setAmount(SPHelper.addon_list.get(i).getFinal_price());
                selec_add_ons_list.add(pojoSelAddOnn);

            }
            else {
                selec_add_ons_list.remove(SPHelper.addon_list.get(i).getAddon_name());
                selec_add_ons_list.remove(SPHelper.addon_list.get(i).getFinal_price());
            }
        }

        SPHelper.sel_addon_list=selec_add_ons_list;

        System.out.println("seelected"+SPHelper.sel_addon_list);
        adapterSelAddons = new AdapterSelAddons(SPHelper.sel_addon_list,Addons.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Addons.this, LinearLayoutManager.VERTICAL, false);
        rv_sel_addon_list.setLayoutManager(linearLayoutManager);
        rv_sel_addon_list.setAdapter(adapterSelAddons);

        Addons.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapterSelAddons.notifyDataSetChanged();
            }
        });


    }
    public void getAddonList()
    {
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
                            if(SPHelper.addon_list.isEmpty())
                            {
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

    public void create_sessionID()
    {
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

                if(SPHelper.is_part.equals("Y")){
                     c_paying_amout=SPHelper.part_amount+SPHelper.add_on_amount;
                    SPHelper.coupon_id="";
                    SPHelper.coupon_type="";
                    SPHelper.disc_amount=0;
                    SPHelper.coupon_code="" ;
                    SPHelper.coupon_code_type_id="";

                }else
                {
                    c_paying_amout=(SPHelper.upgrade_amount+SPHelper.add_on_amount)-SPHelper.disc_amount;
                }

                PojoCreateOrder pojoCFSession = new PojoCreateOrder(email, no, name, c_paying_amout, c_id);
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
        buy_pack();
    }

    @Override
    public void onPaymentFailure(CFErrorResponse cfErrorResponse, String orderID) {
        Log.e("onPaymentFailure " + orderID, cfErrorResponse.getMessage());
        payment_status = "not paid";
        buyAddOnPack();
        buy_pack();

    }

    public static Addons getInstance() {
        return instance;
    }

    public  void buy_pack()
    {
        System.out.println("pack_id"+SPHelper.package_id+","+"add_on_id"+SPHelper.selected_addon_id+","+"main_pack"+SPHelper.main_pack_id+
                ","+"cat_id"+SPHelper.cat_id+",final_amount"+(SPHelper.final_amount-SPHelper.disc_amount)+",status"+payment_status+
                ",online"+"cforder_id"+cforderid+",orderid"+orderID+"disc"+SPHelper.disc_amount+",leadid"+lead_id+
                "lead_veh_id"+SPHelper.lead_veh_id+","+"coupon_id"+SPHelper.coupon_id+"coupon_typew"+
                SPHelper.coupon_type+"disc amount"+SPHelper.disc_amount+"veh_id"+SPHelper.veh_id+
                "coupon_code"+SPHelper.coupon_code+"coupon_type id"+SPHelper.coupon_code_type_id);
        if(SPHelper.sel_upgrade_pac_id.equals("")){
            paack_id=SPHelper.package_id;
        }else {
            paack_id=SPHelper.sel_upgrade_pac_id;
        }

        PojoSellPackage pojoSellPackage = new PojoSellPackage(paack_id, SPHelper.selected_addon_id, SPHelper.main_pack_id,
                SPHelper.cat_id, c_paying_amout, payment_status, "online", "", cforderid, orderID,
                "", SPHelper.disc_amount, "", lead_id, SPHelper.getSPData(Addons.this, SPHelper.customer_id, ""),
                SPHelper.lead_veh_id, "Y", is_multiple_addon, "", "",SPHelper.coupon_id,SPHelper.coupon_type,SPHelper.disc_amount,SPHelper.veh_id,
                SPHelper.coupon_code,SPHelper.coupon_code_type_id,"",SPHelper.is_part,
                SPHelper.pack_amount,SPHelper.part_amount);

        Log.d("print","object"+pojoSellPackage.toString());
    }
    public void buyAddOnPack() {
        {
            if (!Connectivity.isNetworkConnected(Addons.this)) {
                Toast.makeText(Addons.this,
                        "Plaese Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            } else {
                // idPBLoading.setVisibility(View.VISIBLE);
                if(SPHelper.sel_upgrade_pac_id.equals("")){
                     paack_id=SPHelper.package_id;
                }else {
                    paack_id=SPHelper.sel_upgrade_pac_id;
                }

                PojoSellPackage pojoSellPackage = new PojoSellPackage(paack_id, SPHelper.selected_addon_id, SPHelper.main_pack_id,
                        SPHelper.cat_id, c_paying_amout, payment_status, "online", "", cforderid, orderID,
                        "", SPHelper.disc_amount, "", lead_id, SPHelper.getSPData(Addons.this, SPHelper.customer_id, ""),
                        SPHelper.lead_veh_id, "Y", is_multiple_addon, "", "",SPHelper.coupon_id,SPHelper.coupon_type,SPHelper.disc_amount,SPHelper.veh_id,
                        SPHelper.coupon_code,SPHelper.coupon_code_type_id,"",SPHelper.is_part,
                        SPHelper.pack_amount,SPHelper.part_amount);
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
                                intent.putExtra("insp_btn",appResponse.getResponseModel().getVehicleObj().getInspectionButton());
                                intent.putExtra("final_amount",c_paying_amout);
                                startActivity(intent);
                            } else if (response_code.equals("300")) {

                                SPHelper.isSuccess = "n";
                                SPHelper.addon_list=new ArrayList<>();
                               // SPHelper.upgrade_amount=SPHelper.pack_amount;
                                SPHelper.add_on_amount=0;
                                SPHelper.sel_addon_list=new ArrayList<>();
                                SPHelper.upgrade_list=new ArrayList<>();

                                getAddonList();
                                get_update_amounnt();
                                get_sel_add_on_list();
                               // gett_upgrade_list();
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


    public void gett_upgrade_list()
    {
        if (!Connectivity.isNetworkConnected(Addons.this)) {
            Toast.makeText(Addons.this,
                    "Plaese Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        } else {
            idPBLoading.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.get_upgrade_pac_list(SPHelper.getSPData(Addons.this, SPHelper.lead_id, ""),
                    SPHelper.getSPData(Addons.this, SPHelper.customer_id, ""), SPHelper.veh_id,SPHelper.package_id,
                    SPHelper.cat_id, SPHelper.is_upgrade);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                    AppResponse appResponse = response.body();
                    assert appResponse != null;
                    String response_code = appResponse.getResponseType();
                    if (response.body() != null) {
                        if (response_code.equals("200")) {
                            idPBLoading.setVisibility(View.GONE);

                            pojoUpgrades=new ArrayList<>();
                            pojoUpgrades=appResponse.getResponseModel().getUpgradePackList();
                            if(SPHelper.upgrade_list.isEmpty())
                            {
                                SPHelper.upgrade_list=pojoUpgrades;
                            }
                            if(SPHelper.upgrade_list.isEmpty()){
                                rv_upgrade_list.setVisibility(View.GONE);
                                no_result.setVisibility(View.VISIBLE);
                            }else {
                                rv_upgrade_list.setVisibility(View.VISIBLE);
                                no_result.setVisibility(View.GONE);
                            }
                            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(Addons.this,LinearLayoutManager.VERTICAL,false);
                            adapterUpgrade=new AdapterUpgrade(SPHelper.upgrade_list,Addons.this);
                            rv_upgrade_list.setLayoutManager(linearLayoutManager);
                            rv_upgrade_list.setAdapter(adapterUpgrade);
                            Addons.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapterUpgrade.notifyDataSetChanged();
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


    public  void get_update_amounnt()
    {

        if (SPHelper.is_partial_pay == null) {

            tv_partial.setVisibility(View.INVISIBLE);
        }
        else if(SPHelper.is_partial_pay.equalsIgnoreCase("y"))
        {
            tv_partial.setVisibility(View.VISIBLE);

        }
        else {
            if(SPHelper.is_ok_partial_pay==null||SPHelper.is_ok_partial_pay.equals("null")||
                    SPHelper.is_ok_partial_pay.equalsIgnoreCase("n"))
            {
                tv_partial.setVisibility(View.INVISIBLE);
            }else {
                tv_partial.setVisibility(View.VISIBLE);
            }
        }


        System.out.println("pack_amounnt"+SPHelper.pack_amount);
        System.out.println("upgrade_amount"+SPHelper.upgrade_amount);
        System.out.println("add_onamount"+SPHelper.add_on_amount);
        System.out.println("disc_amount"+SPHelper.disc_amount);
        System.out.println("SPHelper.is_upgrade"+SPHelper.is_upgrade);



        System.out.println("tv_amount"+tv_amount_buy.getText().toString());


        tv_amount_buy.setText((IndianCurrencyFormat.format((int) (SPHelper.upgrade_amount))));
        tv_total_amount.setText((IndianCurrencyFormat.format((int)
                ((SPHelper.upgrade_amount+SPHelper.add_on_amount)-SPHelper.disc_amount))));

        if(tv_amount_buy.getText().toString().equals("0")){
            rl_coupon_label.setVisibility(View.GONE);
        }else {
            rl_coupon_label.setVisibility(View.VISIBLE);
        }

    }
}