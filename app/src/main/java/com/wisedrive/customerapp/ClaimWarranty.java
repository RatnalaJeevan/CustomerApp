package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wisedrive.customerapp.adapters.AdapterClaimList;
import com.wisedrive.customerapp.adapters.AdapterTrackClaimList;
import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.PojoNewClaimList;
import com.wisedrive.customerapp.pojos.PojoTrackClaims;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClaimWarranty extends AppCompatActivity {
   public RelativeLayout rl_request_claim,rl_back,rl_track_claim_status,rl_transperant1,rl_imv,rl_call,rl_req_claim;
    private ApiInterface apiInterface;
    RecyclerView rv_claim_list;
    ArrayList<PojoNewClaimList> newClaimLists;
    AdapterClaimList adapterClaimList;
    RecyclerView rv_claim_status;
    ArrayList<PojoTrackClaims> trackClaims;
    AdapterTrackClaimList adapterTrackClaimList;
    TextView tv_showroom_services,expired,label_exp;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comprehensive_warranty);
        getWindow().setStatusBarColor(getColor(R.color.new_app_bg));
        label_exp=findViewById(R.id.label_exp);
        rl_req_claim=findViewById(R.id.rl_req_claim);
        rl_call=findViewById(R.id.rl_call);
        rl_imv=findViewById(R.id.rl_imv);
        expired=findViewById(R.id.expired);
        tv_showroom_services=findViewById(R.id.tv_showroom_services);
        rv_claim_status=findViewById(R.id.rv_claim_status);
        rl_track_claim_status=findViewById(R.id.rl_track_claim_status);
        rl_transperant1=findViewById(R.id.rl_transperant1);
        rl_request_claim=findViewById(R.id.rl_request_claim);
        rv_claim_list=findViewById(R.id.rv_claim_list);
        rl_back=findViewById(R.id.rl_back);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
       // tv_showroom_services.setText(SPHelper.product_name);

        rl_request_claim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPHelper.claim_type_id="";
                SPHelper.qa_list=new ArrayList<>();
                SPHelper.answer_details=new ArrayList<>();
                SPHelper.answerlist=new ArrayList<>();
                Intent intent=new Intent(ClaimWarranty.this, InitiateNewClaim.class);
                startActivity(intent);
                finish();
            }
        });

        rl_req_claim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPHelper.claim_type_id="";
                SPHelper.qa_list=new ArrayList<>();
                SPHelper.answer_details=new ArrayList<>();
                SPHelper.answerlist=new ArrayList<>();
                Intent intent=new Intent(ClaimWarranty.this, InitiateNewClaim.class);
                startActivity(intent);
                finish();
            }
        });
        rl_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+SPHelper.getSPData(ClaimWarranty.this,SPHelper.customer_support_phoneno,"")));
                startActivity(callIntent);
            }
        });
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(Comprehensive_Warranty.this, CustomerHomepage.class);
//                startActivity(intent);
                finish();

            }
        });
        rl_transperant1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_track_claim_status.setVisibility(View.GONE);
            }
        });
        get_claim_list();
    }

    public void get_claim_list() {
        if (!Connectivity.isNetworkConnected(ClaimWarranty.this)) {
            Toast.makeText(ClaimWarranty.this,
                    "Plaese Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        } else {
            //idPBLoading.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.getNewClaimList(SPHelper.veh_id);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                    AppResponse appResponse = response.body();
                    assert appResponse != null;
                    String response_code = appResponse.getResponseType();
                    if (response.body() != null) {
                        if (response_code.equals("200")) {
                            // idPBLoading.setVisibility(View.GONE);
                            newClaimLists = new ArrayList<>();
                            newClaimLists=appResponse.getResponseModel().getClaimList();

                            if(newClaimLists.isEmpty())
                            {

                                rv_claim_list.setVisibility(View.GONE);

                                if(SPHelper.is_exp.equalsIgnoreCase("y"))
                                {
                                    expired.setVisibility(View.VISIBLE);
                                    rl_request_claim.setVisibility(View.INVISIBLE);
                                    label_exp.setVisibility(View.VISIBLE);
                                    label_exp.setText("Sorry! Your "+SPHelper.product_name+" package has been expired.");
                                }else{
                                    label_exp.setVisibility(View.GONE);
                                    rl_imv.setVisibility(View.VISIBLE);
                                    expired.setVisibility(View.INVISIBLE);
                                    rl_request_claim.setVisibility(View.INVISIBLE);
                                }
                            }else{
                                label_exp.setVisibility(View.GONE);
                                rl_imv.setVisibility(View.GONE);
                                rv_claim_list.setVisibility(View.VISIBLE);
                                if(SPHelper.is_exp.equalsIgnoreCase("y"))
                                {
                                    expired.setVisibility(View.VISIBLE);
                                    rl_request_claim.setVisibility(View.INVISIBLE);
                                }else{
                                    expired.setVisibility(View.INVISIBLE);
                                    rl_request_claim.setVisibility(View.VISIBLE);
                                }

                                adapterClaimList = new AdapterClaimList(newClaimLists, ClaimWarranty.this);
                                LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(ClaimWarranty.this, LinearLayoutManager.VERTICAL,false);
                                rv_claim_list.setLayoutManager(linearLayoutManager1);
                                rv_claim_list.setAdapter(adapterClaimList);


                                ClaimWarranty.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapterClaimList.notifyDataSetChanged();
                                    }
                                });
                            }

                        } else if (response_code.equals("300")) {
                            // idPBLoading.setVisibility(View.GONE);
                        }
                    } else {
                        // idPBLoading.setVisibility(View.GONE);
                        Toast.makeText(ClaimWarranty.this, "internal server error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                    Toast.makeText(ClaimWarranty.this,
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    // idPBLoading.setVisibility(View.GONE);
                }
            });
        }
    }

    public void get_track_list() {
        if (!Connectivity.isNetworkConnected(ClaimWarranty.this)) {
            Toast.makeText(ClaimWarranty.this,
                    "Plaese Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        } else {
            //idPBLoading.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.track_claims(SPHelper.claim_id);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                    AppResponse appResponse = response.body();
                    assert appResponse != null;
                    String response_code = appResponse.getResponseType();
                    if (response.body() != null) {
                        if (response_code.equals("200")) {
                            // idPBLoading.setVisibility(View.GONE);

                            trackClaims = new ArrayList<>();
                            trackClaims=appResponse.getResponseModel().getTrackClaimStatus();
                            adapterTrackClaimList = new AdapterTrackClaimList(trackClaims, ClaimWarranty.this);
                            LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(ClaimWarranty.this, LinearLayoutManager.VERTICAL,false);
                            rv_claim_status.setLayoutManager(linearLayoutManager1);
                            rv_claim_status.setAdapter(adapterTrackClaimList);

                            ClaimWarranty.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapterTrackClaimList.notifyDataSetChanged();
                                }
                            });
                        } else if (response_code.equals("300")) {
                            // idPBLoading.setVisibility(View.GONE);
                        }
                    } else {
                        // idPBLoading.setVisibility(View.GONE);
                        Toast.makeText(ClaimWarranty.this, "internal server error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                    Toast.makeText(ClaimWarranty.this,
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    // idPBLoading.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent=new Intent(Comprehensive_Warranty.this, CustomerHomepage.class);
//        startActivity(intent);
        finish();
    }
}