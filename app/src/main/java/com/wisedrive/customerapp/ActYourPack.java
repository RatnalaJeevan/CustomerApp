package com.wisedrive.customerapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActYourPack extends BottomSheetDialogFragment {

    RelativeLayout rl_activate_now;
    Activity activity;
    EditText entered_act_code;
    private ApiInterface apiInterface;
    ProgressBar idPBLoading;
    TextView resend_code;
    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_act_your_pack,
                container, false);

        activity=getActivity();
        resend_code=view.findViewById(R.id.resend_code);
        idPBLoading=view.findViewById(R.id.idPBLoading);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        rl_activate_now=view.findViewById(R.id.rl_activate_now);
        entered_act_code=view.findViewById(R.id.entered_act_code);

        entered_act_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(entered_act_code.getText().toString().length()==10){
                    InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE) ;
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });
        rl_activate_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SPHelper.getSPData(activity,SPHelper.customer_id,"").equals("")){
                    Toast.makeText(activity,
                            "You donot have active package to activate",
                            Toast.LENGTH_SHORT).show();
                }else if(entered_act_code.getText().toString().equals("")){
                    Toast.makeText(activity,
                            "Enter an activation code",
                            Toast.LENGTH_SHORT).show();
                }else if(entered_act_code.getText().toString().length()<10){
                    Toast.makeText(activity,
                            "Enter a valid activation code",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    activate_account();
                }
            }
        });

        resend_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request_act_code();
            }
        });

        return  view;
    }

    public void activate_account() {
        if(!Connectivity.isNetworkConnected(activity))
        {
            Toast.makeText(activity,
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            idPBLoading.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.get_act_code(entered_act_code.getText().toString(),
                    SPHelper.getSPData(activity,SPHelper.customer_id,""));
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response)
                {
                    if (response.body()!=null)
                    {
                        idPBLoading.setVisibility(View.GONE);
                        AppResponse appResponse=response.body();
                        if (appResponse.getResponseType().equals("200"))
                        {
                            SPHelper.disc_amount=0;
                            SPHelper.coupon_code="";
                            SPHelper.coupon_type="";
                            SPHelper.actcode=entered_act_code.getText().toString();
                            Toast.makeText(activity,"Package activated successfully",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(activity, Upgrade_and_Save.class);
                            activity.startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(activity,appResponse.getResponseModel().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                    idPBLoading.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, Throwable t) {
                    idPBLoading.setVisibility(View.GONE);
                    Toast.makeText(activity,
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void hideKeybaord() {

        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    public void request_act_code()
    {
        if(!Connectivity.isNetworkConnected(activity))
        {
            Toast.makeText(activity,
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }else
        {
            idPBLoading.setVisibility(View.VISIBLE);
            Call<AppResponse> call =  apiInterface.request_activation_code( SPHelper.getSPData(activity, SPHelper.customer_phoneno, ""));
            call.enqueue(new Callback<AppResponse>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                    AppResponse appResponse = response.body();
                    if(appResponse.getResponseType()!=null) {
                        if (appResponse.getResponseType().equals("200"))
                        {
                            idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(activity,
                                    "We have sent an activation code to your registered mobile number  ",
                                    Toast.LENGTH_SHORT).show();

                        } else if (appResponse.getResponseType().equals("300")) {
                            idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(activity,
                                     appResponse.getResponseModel().getMessage(),
                                    Toast.LENGTH_SHORT).show();


                        } else if (appResponse.getResponseType().equals("500")) {

                            idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(activity,
                                    "Error" + response.code(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        idPBLoading.setVisibility(View.GONE);
                        Toast.makeText(activity,
                                "Something went wrong" ,
                                Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AppResponse> call, Throwable t) {
                    Toast.makeText(activity,
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    idPBLoading.setVisibility(View.GONE);

                }
            });
        }
    }

}