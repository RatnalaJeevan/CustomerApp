package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wisedrive.customerapp.adapters.AdapterCustomerVehicleList;
import com.wisedrive.customerapp.adapters.AdapterRSAPackageDetails;
import com.wisedrive.customerapp.adapters.AdapterVehDetails;
import com.wisedrive.customerapp.adapters.SlideAdapter;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.AppResponse;
import com.wisedrive.customerapp.pojos.PojoVehDetails;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import org.w3c.dom.Text;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerProfile extends AppCompatActivity {
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    RelativeLayout add_vehicle,rl_customer_veh_list,rl_contact_support,rl_transparent_contact,rl_claim,
            rl_wisedrive_contact,rl_wisedrive_email;
    TextView yes,no,subscription,help_support,customername,customerno,privacy_policy,tnc,copyright,warranty_policy
            ,add_services;
    RecyclerView rv_veh_list;
    private  Dialog dialog;
    ImageView cancel,rl_transparent;
    TextView logout,version,version_name;
    ArrayList<PojoVehDetails> vehDetails;
    RecyclerView rv_carimagelist;
    AdapterVehDetails adapterVehDetails;
    AdapterCustomerVehicleList adapterCustomerVehicleList;
    ProgressBar progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);
        SPHelper.sharedPreferenceInitialization(CustomerProfile.this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        progressDialog = new ProgressDialog(CustomerProfile.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progress=findViewById(R.id.progress); 
        rv_veh_list=findViewById(R.id.rv_veh_list);
        rl_claim=findViewById(R.id.rl_claim);
        add_vehicle= findViewById(R.id.add_vehicle);
        rl_customer_veh_list= findViewById(R.id.rl_customer_veh_list);
        rl_contact_support= findViewById(R.id.rl_contact_support);
        rl_transparent_contact= findViewById(R.id.rl_transparent_contact);
        rl_wisedrive_contact= findViewById(R.id.rl_wisedrive_contact);
        rl_wisedrive_email= findViewById(R.id.rl_wisedrive_email);
        rl_transparent= findViewById(R.id.rl_transparent);
        cancel=findViewById(R.id.cancel);
        version_name=findViewById(R.id.version_name);
        customername= findViewById(R.id.customername);
        customerno= findViewById(R.id.customerno);
        help_support= findViewById(R.id.help_support);
        logout= findViewById(R.id.logout);
        version= findViewById(R.id.version);
        subscription= findViewById(R.id.subscription);
        privacy_policy= findViewById(R.id.privacy_policy);
        add_services=findViewById(R.id.add_services);
        tnc= findViewById(R.id.tnc);
        copyright= findViewById(R.id.copyright);
        warranty_policy= findViewById(R.id.warranty_policy);
        rv_carimagelist=findViewById(R.id.rv_carimagelist);
        vehDetails=new ArrayList<PojoVehDetails>();

        customername.setText(SPHelper.customer_name);
        customerno.setText(SPHelper.getSPData(CustomerProfile.this, SPHelper.customer_phoneno, ""));

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CustomerProfile.this,VehiclePackageDetails.class);
                startActivity(intent);
            }
        });
        rl_claim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CustomerProfile.this,AllClaimsPage.class);
                startActivity(intent);
            }
        });
        get_customer_vehicle_details();
        get_web_links();
        dialog = new Dialog(CustomerProfile.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.popup_logout_dialog);
        dialog.setCancelable(true);

        yes=dialog.findViewById(R.id.yes) ;
        yes.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                SPHelper.saveSPdata(CustomerProfile.this, SPHelper.package_activated, "");
                SPHelper.saveSPdata(CustomerProfile.this, SPHelper.otp_activated, "");
                SPHelper.saveSPdata(CustomerProfile.this, SPHelper.customer_phoneno, "");
                SPHelper.saveSPdata(CustomerProfile.this, SPHelper.customer_support_phoneno, "");
                SPHelper.saveSPdata(CustomerProfile.this, SPHelper.customer_support_email, "");
                SPHelper.saveSPdata(CustomerProfile.this, SPHelper.awssecret, "");
                SPHelper.saveSPdata(CustomerProfile.this, SPHelper.awskey, "");
                SPHelper.saveSPdata(CustomerProfile.this, SPHelper.comet_authkey, "");
                SPHelper.saveSPdata(CustomerProfile.this, SPHelper.comet_region, "");
                SPHelper.saveSPdata(CustomerProfile.this, SPHelper.comet_appid, "");

                Intent i = new Intent(CustomerProfile.this,LoginNewPage.class);
                startActivity(i);
                finish();
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
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
        add_vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CustomerProfile.this,PackageActivation.class);
                SPHelper.comingfrom="customerpage";
                startActivity(intent);
            }
        });
        help_support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_contact_support.setVisibility(View.VISIBLE);

            }
        });
        rl_wisedrive_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+SPHelper.getSPData(CustomerProfile.this, SPHelper.customer_support_phoneno, "")));
                startActivity(callIntent);
            }
        });
        rl_wisedrive_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" +SPHelper.getSPData(CustomerProfile.this, SPHelper.customer_support_email, "") ));
                startActivity(intent);
            }
        });
        rl_transparent_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_contact_support.setVisibility(View.GONE);
            }
        });
        subscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_customer_vehicle_details();
                rl_customer_veh_list.setVisibility(View.VISIBLE);
                logout.setVisibility(View.GONE);
                version.setVisibility(View.GONE);
                version_name.setVisibility(View.GONE);
            }
        });

        rl_transparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_customer_veh_list.setVisibility(View.GONE);
                logout.setVisibility(View.VISIBLE);
                version.setVisibility(View.VISIBLE);
                version_name.setVisibility(View.VISIBLE);
            }
        });
        privacy_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.comingfrom="pp";
                //get_web_links();
                Intent intent=new Intent(CustomerProfile.this,WebPage.class);
                startActivity(intent);
            }
        });
        tnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.comingfrom="tnc";
                //get_web_links();
                Intent intent=new Intent(CustomerProfile.this,WebPage.class);
                startActivity(intent);
            }
        });
        copyright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SPHelper.copyrights==null|| SPHelper.copyrights.equals("")){
                    Toast.makeText(CustomerProfile.this, "web page will be coming soon" , Toast.LENGTH_SHORT).show();
                }else{
                    SPHelper.comingfrom="copy";
                    //get_web_links();
                    Intent intent=new Intent(CustomerProfile.this,WebPage.class);
                    startActivity(intent);
                }
            }
        });
        warranty_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CustomerProfile.this,EngineTransmissionWarranty.class);
                startActivity(intent);
            }
        });

        add_services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CustomerProfile.this,AdditionalServicePage.class);
                startActivity(intent);
            }
        });

    }
    public  void get_customer_vehicle_details(){
        if(!Connectivity.isNetworkConnected(CustomerProfile.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }else
        {
            progress.setVisibility(View.VISIBLE);
            
            Call<AppResponse> call =  apiInterface.get_veh_details(SPHelper.getSPData(CustomerProfile.this, SPHelper.customer_id, ""));
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response)
                {
                    AppResponse appResponse = response.body();

                    if (response.body()!=null) {
                        assert appResponse != null;
                        if (appResponse.getResponseType().equals("200")) {
                            progress.setVisibility(View.GONE);
                            vehDetails = new ArrayList<>();
                            vehDetails=appResponse.getResponseModel().getVehicleList();
                            SPHelper.vehDetailsList=vehDetails;
                            adapterCustomerVehicleList = new AdapterCustomerVehicleList(vehDetails, CustomerProfile.this);
                            LinearLayoutManager layoutManager1 = new LinearLayoutManager(CustomerProfile.this, LinearLayoutManager.HORIZONTAL, false);
                            rv_carimagelist.setLayoutManager(layoutManager1);
                            rv_carimagelist.setAdapter(adapterCustomerVehicleList);

                            adapterVehDetails = new AdapterVehDetails(vehDetails, CustomerProfile.this);
                            LinearLayoutManager layoutManager2 = new LinearLayoutManager(CustomerProfile.this, LinearLayoutManager.VERTICAL, false);
                            rv_veh_list.setLayoutManager(layoutManager2);
                            rv_veh_list.setAdapter(adapterVehDetails);

                            CustomerProfile.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapterCustomerVehicleList.notifyDataSetChanged();
                                    adapterVehDetails.notifyDataSetChanged();

                                }
                            });
                        } else if (appResponse.getResponseType().equals("300")) {
                            progress.setVisibility(View.GONE);
                            Toast.makeText(CustomerProfile.this, "internal server error" + "response code:" + appResponse.getResponseType(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        progress.setVisibility(View.GONE);
                        Toast.makeText(CustomerProfile.this, "internal server error" , Toast.LENGTH_SHORT).show();
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
    public  void get_web_links(){
        if(!Connectivity.isNetworkConnected(CustomerProfile.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }else
        {
            progress.setVisibility(View.VISIBLE);
            
            Call<AppResponse> call =  apiInterface.get_weblinks();
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response)
                {
                    AppResponse appResponse = response.body();

                    if (response.body()!=null) {
                        assert appResponse != null;
                        if (appResponse.getResponseType().equals("200"))
                        {
                            progress.setVisibility(View.GONE);
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

                        } else if (appResponse.getResponseType().equals("300")) {
                            progress.setVisibility(View.GONE);
                            Toast.makeText(CustomerProfile.this, "internal server error" + "response code:" + appResponse.getResponseType(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        progress.setVisibility(View.GONE);
                        Toast.makeText(CustomerProfile.this, "internal server error" , Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed()
    {
        if(rl_contact_support.getVisibility()==View.VISIBLE||rl_customer_veh_list.getVisibility()==View.VISIBLE){
            rl_contact_support.setVisibility(View.GONE);
            rl_customer_veh_list.setVisibility(View.GONE);
            logout.setVisibility(View.VISIBLE);
            version.setVisibility(View.VISIBLE);
            version_name.setVisibility(View.VISIBLE);
        }else{
            Intent intent=new Intent(CustomerProfile.this,VehiclePackageDetails.class);
            startActivity(intent);
        }
    }
}