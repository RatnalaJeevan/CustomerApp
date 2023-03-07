package com.wisedrive.customerapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wisedrive.customerapp.adapters.Adapter_Class_My_Car_page;
import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Profile_Fragment extends Fragment {
    String car_count="",payments_count="";
    TextView tv_my_car_count,count_payments;
    private Dialog dialog;
    TextView yes,no,c_name,c_no;
    RelativeLayout rl_my_cars,rl_logout,rl_warranty_policy,rl_bbg_policy,rl_terms_condition,rl_package_service,
            rl_privacy_policy,rl_help_support,rl_mypayments;
    Activity activity;
    private ApiInterface apiInterface;
    ProgressBar idPBLoading;
    public Profile_Fragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_, container, false);
        idPBLoading=view.findViewById(R.id.idPBLoading);
        activity=getActivity();
        rl_package_service=view.findViewById(R.id.rl_package_service);
        count_payments=view.findViewById(R.id.count_payments);
        rl_mypayments=view.findViewById(R.id.rl_mypayments);
        c_name=view.findViewById(R.id.c_name);
        c_no=view.findViewById(R.id.c_no);
        rl_warranty_policy=view.findViewById(R.id.rl_warranty_policy);
        rl_bbg_policy=view.findViewById(R.id.rl_bbg_policy);
        rl_terms_condition=view.findViewById(R.id.rl_terms_condition);
        rl_privacy_policy=view.findViewById(R.id.rl_privacy_policy);
        rl_help_support=view.findViewById(R.id.rl_help_support);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        rl_my_cars=view.findViewById(R.id.rl_my_cars);
        rl_logout=view.findViewById(R.id.rl_logout);
        tv_my_car_count=view.findViewById(R.id.tv_my_car_count);

        c_name.setText(SPHelper.getSPData(activity,SPHelper.cust_name,""));
        c_no.setText(SPHelper.getSPData(activity,SPHelper.customer_phoneno,""));
        dialog = new Dialog(activity);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.popup_logout_dialog);
        dialog.setCancelable(true);

        yes=dialog.findViewById(R.id.yes) ;
        yes.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                SPHelper.saveSPdata(activity, SPHelper.otp_activated, "");
                SPHelper.saveSPdata(activity, SPHelper.customer_id, "");
                SPHelper.saveSPdata(activity, SPHelper.lead_id, "");
                Intent intent = new Intent(getActivity(),Login_customer_app.class);
                getActivity().startActivity(intent);
                // finish();
                dialog.dismiss();
            }
        });
        no=dialog.findViewById(R.id.no) ;
        no.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dialog.cancel();
                dialog.dismiss();
            }
        });
        rl_package_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.fragment_is="plans";
                Intent intent = new Intent(getActivity(),CustomerHomepage.class);
                getActivity().startActivity(intent);
            }
        });
        rl_my_cars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(car_count==null||car_count.equals("null")||car_count.equals("0")||car_count.equals("")){
                    Toast.makeText(activity,
                            "No Cars to show,please add a car",
                            Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent=new Intent(activity,My_Cars.class);
                    startActivity(intent);
                }

            }
        });

        rl_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        rl_warranty_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.comingfrom="wp";
                Intent intent=new Intent(activity,WebPage.class);
                startActivity(intent);
            }
        });
        rl_bbg_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.comingfrom="bbg";
                Intent intent=new Intent(activity,WebPage.class);
                startActivity(intent);
            }
        });
        rl_terms_condition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.comingfrom="tnc";
                Intent intent=new Intent(activity,WebPage.class);
                startActivity(intent);
            }
        });
        rl_privacy_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.comingfrom="pp";
                Intent intent=new Intent(activity,WebPage.class);
                startActivity(intent);
            }
        });
        rl_help_support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity,HelpCentre.class);
                startActivity(intent);
            }
        });

        rl_mypayments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(payments_count==null||payments_count.equals("null")||payments_count.equals("0")||payments_count.equals("")){
                    Toast.makeText(activity,
                            "No payments to show",
                            Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent=new Intent(activity,MyPayments.class);
                    startActivity(intent);
                }

            }
        });
        get_web_links();
        get_my_carscount();
        return view;
    }

    public  void get_my_carscount()
    {
        if (!Connectivity.isNetworkConnected(activity)) {
            Toast.makeText(activity,
                    "Plaese Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        } else {
            idPBLoading.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.getVehCount(SPHelper.getSPData(activity,SPHelper.lead_id,""),
                    SPHelper.getSPData(activity,SPHelper.customer_id,""));
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
                             car_count=appResponse.getResponseModel().getMyCarCount().getCount();
                             payments_count=appResponse.getResponseModel().getPaymentCount().getCount();
                             tv_my_car_count.setText(car_count);
                             count_payments.setText(payments_count);
                        } else if (response_code.equals("300")) {
                            idPBLoading.setVisibility(View.GONE);
                        }
                    } else {
                          idPBLoading.setVisibility(View.GONE);
                        Toast.makeText(activity, "internal server error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                    Toast.makeText(activity,
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                      idPBLoading.setVisibility(View.GONE);
                }
            });
        }
    }

    public  void get_web_links(){
        if(!Connectivity.isNetworkConnected(activity))
        {
            Toast.makeText(activity,
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }else
        {
           // progress.setVisibility(View.VISIBLE);

            Call<AppResponse> call =  apiInterface.get_new_web_links();
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response)
                {
                    AppResponse appResponse = response.body();

                    if (response.body()!=null) {
                        assert appResponse != null;
                        if (appResponse.getResponseType().equals("200"))
                        {
                           // progress.setVisibility(View.GONE);
                            SPHelper.privacypolicy= appResponse.getResponseModel().getGetweblinks().getPrivacy_policy();
                            SPHelper.tnc=appResponse.getResponseModel().getGetweblinks().getTerms();
                            if(!appResponse.getResponseModel().getGetweblinks().getTerms().equals("")){
                                SPHelper.copyrights=appResponse.getResponseModel().getGetweblinks().getCopy_right();
                            }else{
                                SPHelper.copyrights="";
                            }
                            if(!appResponse.getResponseModel().getGetweblinks().getView_policy().equals("")){
                                SPHelper.viewpolicy=appResponse.getResponseModel().getGetweblinks().getView_policy();
                            }else{
                                SPHelper.viewpolicy="";
                            }
                            SPHelper.bbg_policy=appResponse.getResponseModel().getGetweblinks().getBuyback_policy();
                            SPHelper.warr_policy=appResponse.getResponseModel().getGetweblinks().getWarranty_policy();


                        } else if (appResponse.getResponseType().equals("300")) {
                            //progress.setVisibility(View.GONE);
                            Toast.makeText(activity, "internal server error" + "response code:" + appResponse.getResponseType(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        //progress.setVisibility(View.GONE);
                        Toast.makeText(activity, "internal server error" , Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AppResponse> call, Throwable t) {
                    Toast.makeText(activity,
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    //progress.setVisibility(View.GONE);
                }
            });
        }
    }
}