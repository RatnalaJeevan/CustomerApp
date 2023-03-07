package com.wisedrive.customerapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.commonclasses.Common;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Activate_Fragment extends Fragment {
    RelativeLayout rl_activate_now;
    Activity activity;
    EditText entered_act_code;
    private ApiInterface apiInterface;
    ProgressBar idPBLoading;
    public Activate_Fragment() {

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_activate_, container, false);
        activity=getActivity();
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
                    hideKeybaord();
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

        return view;
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
        } }
}