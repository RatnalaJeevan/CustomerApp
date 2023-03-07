package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wisedrive.customerapp.adapters.AdapterTrackClaimPage;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.pojos.PojoTrackClaimList;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrackClaimPage extends AppCompatActivity {
    AdapterTrackClaimPage adapterTrackClaimPage;
    private ApiInterface apiInterface;
    RecyclerView rv_claim_status;
    ArrayList<PojoTrackClaimList> trackClaimLists;
    RelativeLayout rl_back;
    ImageView go_back;
    ProgressBar progress_track_claim;
    TextView claim_name,veh_name,tv_track_claim_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_claim_page);
        tv_track_claim_id=findViewById(R.id.tv_track_claim_id);
        veh_name=findViewById(R.id.veh_name);
        claim_name=findViewById(R.id.claim_name);
        progress_track_claim=findViewById(R.id.progress_track_claim);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        rl_back=findViewById(R.id.rl_back);
        rv_claim_status= findViewById(R.id.rv_claim_status);
        tv_track_claim_id.setText(SPHelper.claim_code);
        veh_name.setText(SPHelper.veh_name);
        claim_name.setText(SPHelper.claim_type);

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TrackClaimPage.this, AllClaimsPage.class);
                startActivity(intent);
            }
        });
        get_TrackList();
    }

    public  void get_TrackList(){
        if(!Connectivity.isNetworkConnected(TrackClaimPage.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }else
        {
            progress_track_claim.setVisibility(View.VISIBLE);
            Call<AppResponse> call =  apiInterface.getTrackClaim(SPHelper.claim_id);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response)
                {
                    AppResponse appResponse = response.body();

                    if (response.body()!=null) {
                        assert appResponse != null;
                        if (appResponse.getResponseType().equals("200")) {
                            progress_track_claim.setVisibility(View.GONE);
                            trackClaimLists=new ArrayList<>();
                            //trackClaimLists=appResponse.getResponseModel().getTrackClaimStatus();
                            if(trackClaimLists.isEmpty()){
                                rv_claim_status.setVisibility(View.GONE); }
                            else{

                                rv_claim_status.setVisibility(View.VISIBLE);
                                adapterTrackClaimPage = new AdapterTrackClaimPage(trackClaimLists, TrackClaimPage.this);
                                LinearLayoutManager layoutManager = new LinearLayoutManager(TrackClaimPage.this, LinearLayoutManager.VERTICAL, false);
                                rv_claim_status.setLayoutManager(layoutManager);
                                rv_claim_status.setAdapter(adapterTrackClaimPage);
                                TrackClaimPage.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapterTrackClaimPage.notifyDataSetChanged();
                                    }
                                });
                            }
                        } else if (appResponse.getResponseType().equals("300")) {
                            progress_track_claim.setVisibility(View.GONE);
                            Toast.makeText(TrackClaimPage.this, "internal server error" + "response code:" + appResponse.getResponseType(), Toast.LENGTH_SHORT).show();

                        }
                    }
                    else {
                        progress_track_claim.setVisibility(View.GONE);
                        Toast.makeText(TrackClaimPage.this, "internal server error" , Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AppResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    progress_track_claim.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(TrackClaimPage.this, AllClaimsPage.class);
        startActivity(intent);
    }
}