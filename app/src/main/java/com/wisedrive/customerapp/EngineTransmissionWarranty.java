package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EngineTransmissionWarranty extends AppCompatActivity {
    ImageView back;
    RelativeLayout rl_contact_support,rl_transparent,rl_wisedrive_contact;
    TextView automatic_tv,manual_tv,engine_tv,content,claim_warranty,view_policy_link,claim_status;
    View tv8,tv9,tv10;
    ImageView center_image;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    String onselect="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_engine_transmission_warranty);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        progressDialog = new ProgressDialog(EngineTransmissionWarranty.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        center_image=findViewById(R.id.center_image);
        rl_wisedrive_contact=findViewById(R.id.rl_wisedrive_contact);
        rl_transparent=findViewById(R.id.rl_transparent);
        rl_contact_support=findViewById(R.id.rl_contact_support);
        back= findViewById(R.id.back);
        tv8=findViewById(R.id.tv8);
        tv9=(View)findViewById(R.id.tv9);
        tv10=(View)findViewById(R.id.tv10);
        claim_status=findViewById(R.id.claim_status);
        claim_warranty=findViewById(R.id.claim_warranty);
        engine_tv=findViewById(R.id.engine_tv);
        automatic_tv=findViewById(R.id.automatic_tv);
        manual_tv=findViewById(R.id.manual_tv);
        content=findViewById(R.id.content);
        view_policy_link=findViewById(R.id.view_policy_link);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        claim_warranty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                claim_warranty.setVisibility(View.INVISIBLE);
                claim_status.setVisibility(View.VISIBLE);
                Intent intent=new Intent(EngineTransmissionWarranty.this, InitiateClaim.class);
                startActivity(intent);
               // rl_contact_support.setVisibility(View.VISIBLE);
            }
        });
        claim_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                claim_status.setVisibility(View.INVISIBLE);
                claim_warranty.setVisibility(View.VISIBLE);
                Intent intent=new Intent(EngineTransmissionWarranty.this, TrackClaimPage.class);
                startActivity(intent);
                // rl_contact_support.setVisibility(View.VISIBLE);
            }
        });

        rl_wisedrive_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+ SPHelper.getSPData(EngineTransmissionWarranty.this, SPHelper.customer_support_phoneno, "")));
                startActivity(callIntent);
                finish();
            }
        });
        rl_transparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_contact_support.setVisibility(View.GONE);
            }
        });
       get_web_links();
        view_policy_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SPHelper.viewpolicy.equals("")){
                    Toast.makeText(getApplicationContext(),
                            "Web Page coming soon!",
                            Toast.LENGTH_SHORT).show();
                }else{
                    SPHelper.comingfrom="vp";
                    Intent intent=new Intent(EngineTransmissionWarranty.this,WebPage.class);
                    startActivity(intent);
                }
            }
        });
        getcontent();
    }
    public void getcontent()
    {
        if(!Connectivity.isNetworkConnected(EngineTransmissionWarranty.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }else
        {
            progressDialog.show();
            Call<AppResponse> call =  apiInterface.get_policydetails();
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                    AppResponse appResponse = response.body();
                    String responsetype = appResponse.getResponseType();

                    if (responsetype.equals("200")) {
                        progressDialog.dismiss();
                        if(onselect.equals("")){
                            String content_eng1= appResponse.getResponseModel().getPolicydetails().get(0).getContent();
                            String image_engine1=appResponse.getResponseModel().getPolicydetails().get(0).getImage_url();
                            if ( image_engine1!= null && !image_engine1.isEmpty() && !image_engine1.equals("null")) {
                                Glide.with(getApplicationContext()).load(image_engine1).placeholder(R.drawable.icon_noimage).into(center_image);
                            }
                            content.setText(content_eng1);
                        }else if(onselect.equals("engine")){
                           String content_eng= appResponse.getResponseModel().getPolicydetails().get(0).getContent();
                            String image_engine=appResponse.getResponseModel().getPolicydetails().get(0).getImage_url();
                            if ( image_engine!= null && !image_engine.isEmpty() && !image_engine.equals("null")) {
                                Glide.with(getApplicationContext()).load(image_engine).placeholder(R.drawable.icon_noimage).into(center_image);
                            }
                            content.setText(content_eng);
                        }else if(onselect.equals("manual")){
                            String content_manual=appResponse.getResponseModel().getPolicydetails().get(1).getContent();
                                String image_manual=appResponse.getResponseModel().getPolicydetails().get(1).getImage_url();
                                if ( image_manual!= null && !image_manual.isEmpty() && !image_manual.equals("null")) {
                                    Glide.with(getApplicationContext()).load(image_manual).placeholder(R.drawable.icon_noimage).into(center_image);
                                }
                            content.setText(content_manual);
                        }else if(onselect.equals("automatic")){
                            String content_auto=appResponse.getResponseModel().getPolicydetails().get(2).getContent();
                                    String image_auto=appResponse.getResponseModel().getPolicydetails().get(2).getImage_url();
                                    if ( image_auto!= null && !image_auto.isEmpty() && !image_auto.equals("null")) {
                                        Glide.with(getApplicationContext()).load(image_auto).placeholder(R.drawable.icon_noimage).into(center_image);
                                    }
                            content.setText(content_auto);

                        }


                    } else if (responsetype.equals("300")) {
                        progressDialog.dismiss();
                        Toast.makeText(EngineTransmissionWarranty.this, "Error 300", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(EngineTransmissionWarranty.this, responsetype, Toast.LENGTH_SHORT).show();
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

    public  void on_engineselect(View view){
        onselect="engine";
        engine_tv.setTextColor(Color.parseColor("#FFFFFFFF"));
        tv8.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        manual_tv.setTextColor(Color.parseColor("#D3D3D3"));
        tv10.setBackgroundColor(Color.parseColor("#D3D3D3"));
        automatic_tv.setTextColor(Color.parseColor("#D3D3D3"));
        tv9.setBackgroundColor(Color.parseColor("#D3D3D3"));
        getcontent();
    }
    public  void onmanualselect(View view){
        onselect="manual";
        manual_tv.setTextColor(Color.parseColor("#FFFFFFFF"));
        tv10.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        engine_tv.setTextColor(Color.parseColor("#D3D3D3"));
        tv8.setBackgroundColor(Color.parseColor("#D3D3D3"));
        automatic_tv.setTextColor(Color.parseColor("#D3D3D3"));
        tv9.setBackgroundColor(Color.parseColor("#D3D3D3"));
        getcontent();
    }
    public  void onautomaticselect(View view){
        onselect="automatic";
        automatic_tv.setTextColor(Color.parseColor("#FFFFFFFF"));
        tv9.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        engine_tv.setTextColor(Color.parseColor("#D3D3D3"));
        tv8.setBackgroundColor(Color.parseColor("#D3D3D3"));
        manual_tv.setTextColor(Color.parseColor("#D3D3D3"));
        tv10.setBackgroundColor(Color.parseColor("#D3D3D3"));
        getcontent();
    }
    public  void get_web_links(){
        if(!Connectivity.isNetworkConnected(EngineTransmissionWarranty.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }else
        {
            progressDialog.show();
            Call<AppResponse> call =  apiInterface.get_weblinks();
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response)
                {
                    AppResponse appResponse = response.body();

                    if (response.body()!=null) {
                        assert appResponse != null;
                        if (appResponse.getResponseType().equals("200")) {
                            progressDialog.dismiss();
                            if(appResponse.getResponseModel().getGetweblinks().getView_policy().equals("")){
                                SPHelper.viewpolicy=appResponse.getResponseModel().getGetweblinks().getView_policy();
                            }else{
                                SPHelper.viewpolicy="";
                            }

                        } else if (appResponse.getResponseType().equals("300")) {
                            progressDialog.dismiss();
                            Toast.makeText(EngineTransmissionWarranty.this, "internal server error" + "response code:" + appResponse.getResponseType(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(EngineTransmissionWarranty.this, "internal server error" , Toast.LENGTH_SHORT).show();
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