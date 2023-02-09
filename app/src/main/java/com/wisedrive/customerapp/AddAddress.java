package com.wisedrive.customerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wisedrive.customerapp.adapters.AdapterCustomerAdresses;
import com.wisedrive.customerapp.adapters.AdapterPackageDetails;
import com.wisedrive.customerapp.adapters.AdapterRSAPackageDetails;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.AppResponse;
import com.wisedrive.customerapp.pojos.PojoAddresses;
import com.wisedrive.customerapp.pojos.PojoPackageList;
import com.wisedrive.customerapp.pojos.PojoPeriodicMaintenanceServices;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAddress extends AppCompatActivity  {
    ImageView back,cancel_btn;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    TextView add_address_submit;
    EditText entered_adrs1,entered_location,entered_landmark,entered_pincode,entered_city,entered_state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        progressDialog = new ProgressDialog(AddAddress.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        back=findViewById(R.id.back);
        add_address_submit=findViewById(R.id.add_address_submit);
        cancel_btn=findViewById(R.id.cancel_btn);
        entered_adrs1=findViewById(R.id.entered_adrs1);
        entered_location=findViewById(R.id.entered_adrs2);
        entered_landmark=findViewById(R.id.entered_landmark);
        entered_pincode=findViewById(R.id.entered_pincode);
        entered_city=findViewById(R.id.entered_city);
        entered_state=findViewById(R.id.entered_state);
        entered_state.setFocusable(false);
        entered_city.setFocusable(false);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AddAddress.this,SelectAddress.class);
                startActivity(intent);
                finish();
            }
        });

        add_address_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((entered_adrs1.getText().toString().equals(""))){
                    Toast.makeText(AddAddress.this,"Enter address",Toast.LENGTH_SHORT).show();
                }else if((entered_location.getText().toString().equals(""))){
                    Toast.makeText(AddAddress.this,"Enter location",Toast.LENGTH_SHORT).show();
                }else if((entered_pincode.getText().toString().equals(""))){
                    Toast.makeText(AddAddress.this,"Enter pincode",Toast.LENGTH_SHORT).show();
                }
                else if((entered_pincode.length()<6)){
                    Toast.makeText(AddAddress.this," Invalid Pincode",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    post_customer_address();
                }
            }
        });
        entered_pincode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

            }
        });
        entered_pincode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(entered_pincode.getText().toString().trim().length() >0||entered_pincode.getText().toString().trim().length() <6){
                    cancel_btn.setVisibility(View.VISIBLE);
                    entered_city.setText("");
                    entered_state.setText("");
                }
                if (entered_pincode.getText().toString().trim().length() == 6) {
                    hideKeybaord();
                    cancel_btn.setVisibility(View.GONE);
                    entered_state.setFocusable(false);
                    entered_city.setFocusable(false);
                    get_pincode_details();
                }
            }
        });
    }

    private void post_customer_address() {
        if (!Connectivity.isNetworkConnected(AddAddress.this)) {
            Toast.makeText(AddAddress.this, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
            return;
        } else {
            progressDialog.show();
            PojoAddresses post1 = new PojoAddresses(entered_adrs1.getText().toString().trim(),
                    "",entered_landmark.getText().toString().trim(),
                    entered_pincode.getText().toString().trim(),
                    entered_city.getText().toString().trim(),entered_state.getText().toString().trim(),
                    SPHelper.getSPData(AddAddress.this, SPHelper.customer_id, ""),
                    entered_location.getText().toString().trim());
            Call<AppResponse> call = apiInterface.post_address(post1);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                    System.out.print(response.body());
                    if (response.body() != null) {
                        AppResponse data = response.body();
                        if (data.getResponseType().equalsIgnoreCase("200")) {
                            progressDialog.dismiss();
                            Toast.makeText(AddAddress.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(AddAddress.this,SelectAddress.class);
                            startActivity(intent);
                            finish();

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(AddAddress.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<AppResponse> call, Throwable th) {
                    Toast.makeText(AddAddress.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }
    }

    private void hideKeybaord() {
        InputMethodManager inputManager = (InputMethodManager) AddAddress.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(AddAddress.this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

    }
    public  void oncancelSelect(View view){
        hideKeybaord();
        entered_pincode.setText("");
        cancel_btn.setVisibility(View.GONE);
        entered_city.setText("");
        entered_state.setText("");

    }
    public  void get_pincode_details(){
        if(!Connectivity.isNetworkConnected(AddAddress.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }else
        {
            progressDialog.show();

            Call<AppResponse> call =  apiInterface.get_pincode_list(entered_pincode.getText().toString().trim());
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response)
                {
                    AppResponse appResponse = response.body();

                    if (response.body()!=null) {
                        assert appResponse != null;
                        if (appResponse.getResponseType().equals("200")) {
                            progressDialog.dismiss();
                            entered_city.setText(appResponse.getResponseModel().getGetpincodedata().getCity_name());
                            entered_state.setText(appResponse.getResponseModel().getGetpincodedata().getState_name());


                        } else if (appResponse.getResponseType().equals("300")) {
                            progressDialog.dismiss();
                            Toast.makeText(AddAddress.this, "internal server error" + "response code:" + appResponse.getResponseType(), Toast.LENGTH_SHORT).show();

                        }
                    }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(AddAddress.this, "internal server error" , Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AppResponse> call, Throwable t) {

                    Toast.makeText(getApplicationContext(),
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }
    }

}