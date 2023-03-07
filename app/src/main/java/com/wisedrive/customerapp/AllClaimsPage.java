package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wisedrive.customerapp.adapters.AdapterAllClaimsList;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.pojos.PojoAllClaimList;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllClaimsPage extends AppCompatActivity {
    TextView no_result;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    AdapterAllClaimsList adapterAllClaimsList ;
    ArrayList<PojoAllClaimList> allClaimLists;
    RelativeLayout initiate_claim,rl_back;
    RecyclerView rv_all_claim_list;
    EditText search_claim;
    ProgressBar progress;
    ImageView search_bar_img,cancel_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_claims_page);
        no_result=findViewById(R.id.no_result);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        progress=findViewById(R.id.progress);
        cancel_btn=findViewById(R.id.cancel_btn);
        search_bar_img=findViewById(R.id.cancel_btn);
        search_claim=findViewById(R.id.search_claim);
        rl_back=findViewById(R.id.rl_back);
        initiate_claim= findViewById(R.id.initiate_claim);
        rv_all_claim_list=findViewById(R.id.rv_all_claim_list);
        initiate_claim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AllClaimsPage.this, InitiateClaim.class);
                startActivity(intent);
            }
        });
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AllClaimsPage.this, CustomerProfile.class);
                startActivity(intent);
            }
        });
        get_ClaimList();
        search();
    }

    private void search() {
        search_claim.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(search_claim.getText().toString().trim().length()>=2)
                {
                    rv_all_claim_list.setVisibility(View.VISIBLE);
                    search_bar_img.setVisibility(View.GONE);
                    cancel_btn.setVisibility(View.VISIBLE);
                    get_ClaimList();
                }else {
                    rv_all_claim_list.setVisibility(View.GONE);
                    no_result.setVisibility(View.GONE);
                    get_ClaimList();
                }
            }
        });
    }

    public  void get_ClaimList(){
        if(!Connectivity.isNetworkConnected(AllClaimsPage.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }else
        {
            progress.setVisibility(View.VISIBLE);
            Call<AppResponse> call =  apiInterface.getClaimList(SPHelper.getSPData(AllClaimsPage.this,SPHelper.customer_id,""),"","","","","",
                    search_claim.getText().toString());
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response)
                {
                    AppResponse appResponse = response.body();

                    if (response.body()!=null) {
                        assert appResponse != null;
                        if (appResponse.getResponseType().equals("200")) {
                            progress.setVisibility(View.GONE);
                            allClaimLists=new ArrayList<>();
                           // allClaimLists=appResponse.getResponseModel().getClaimList();
                            if(allClaimLists.isEmpty()){
                                no_result.setVisibility(View.VISIBLE);
                                rv_all_claim_list.setVisibility(View.GONE);
                            }
                            else{
                                no_result.setVisibility(View.GONE);
                                rv_all_claim_list.setVisibility(View.VISIBLE);
                                adapterAllClaimsList=new AdapterAllClaimsList(allClaimLists,AllClaimsPage.this);
                                LinearLayoutManager layoutManager = new LinearLayoutManager(AllClaimsPage.this, LinearLayoutManager.VERTICAL, false);
                                rv_all_claim_list.setLayoutManager(layoutManager);
                                rv_all_claim_list.setAdapter(adapterAllClaimsList);
                                AllClaimsPage.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapterAllClaimsList.notifyDataSetChanged();
                                    }
                                });
                            }
                        } else if (appResponse.getResponseType().equals("300")) {
                            progress.setVisibility(View.GONE);
                            Toast.makeText(AllClaimsPage.this, "internal server error" + "response code:" + appResponse.getResponseType(), Toast.LENGTH_SHORT).show();

                        }
                    }
                    else {
                        progress.setVisibility(View.GONE);
                        Toast.makeText(AllClaimsPage.this, "internal server error" , Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AppResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    progress.setVisibility(View.GONE);
                }
            });
        }
    }

    public void oncancelSelect(View view) {
        hideKeybaord();
        search_claim.setText("");
        search_bar_img.setVisibility(View.VISIBLE);
        cancel_btn.setVisibility(View.GONE);
    }
    private void hideKeybaord() {
        InputMethodManager inputManager = (InputMethodManager) AllClaimsPage.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(AllClaimsPage.this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

    }
}