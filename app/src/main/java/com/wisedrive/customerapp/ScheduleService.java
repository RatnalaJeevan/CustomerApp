package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.pojos.PojoAddService;
import com.wisedrive.customerapp.pojos.PojoScheduleAdress;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ScheduleService extends AppCompatActivity
{
    EditText selected_service;
    RelativeLayout rl_select_service,rl_back;
    DatePickerDialog date_picker;
    EditText entered_comments;
    TextView service_type,thanks,selected_adress;
    RelativeLayout rl_wisedrive_contact,rl_select_adress;
    public  String serverdate,servermonth,serveryear,service_on;
    private static final String TAG = "MainActivity";
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    public ImageView back,customer_profile_page;
    TextView add_address,destination_address,tv_entered_servicedate,customer_name,customer_phoneno,customer_vehname,customer_vehno,warranty_exp_date;
    RelativeLayout rl_schedule_service,rl_contact_support,service_date_rl;
    RelativeLayout rl_service_req_received,rl_transparent,rl_transparent1;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_service);

        System.out.println("vehid"+SPHelper.veh_id);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        progressDialog = new ProgressDialog(ScheduleService.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        rl_back=findViewById(R.id.rl_back);
        rl_select_service=findViewById(R.id.rl_select_service);
        rl_select_adress=findViewById(R.id.rl_select_adress);
        selected_service=findViewById(R.id.selected_service);
        thanks=findViewById(R.id.thanks);
        selected_adress=findViewById(R.id.selected_adress);
        customer_profile_page=findViewById(R.id.customer_profile_page);
        entered_comments=findViewById(R.id.entered_comments);
        add_address= findViewById(R.id.add_address);
        service_type= findViewById(R.id.service_type);
        tv_entered_servicedate= findViewById(R.id.tv_entered_servicedate);
        destination_address= findViewById(R.id.destination_address);
        customer_name= findViewById(R.id.customer_name);
        customer_phoneno= findViewById(R.id.customer_phoneno);
        customer_vehname= findViewById(R.id.customer_vehname);
        customer_vehno= findViewById(R.id.customer_vehno);
        warranty_exp_date= findViewById(R.id.warranty_exp_date);
        rl_wisedrive_contact= findViewById(R.id.rl_wisedrive_contact);
        rl_contact_support= findViewById(R.id.rl_contact_support);
        rl_schedule_service= findViewById(R.id.rl_schedule_service);
        rl_service_req_received= findViewById(R.id.rl_service_req_received);
        rl_transparent= findViewById(R.id.rl_transparent);
        rl_transparent1= findViewById(R.id.rl_transparent1);
        service_date_rl= findViewById(R.id.service_date_rl);
        customer_name.setText(SPHelper.customer_name);
        customer_phoneno.setText(SPHelper.customer_phoneno);
        customer_vehname.setText(SPHelper.veh_make+" "+SPHelper.veh_model);
        customer_vehno.setText(SPHelper.customer_veh_no);
        warranty_exp_date.setText("Expires on"+" "+SPHelper.veh_valid_to);
        service_type.setText(SPHelper.package_name);

        System.out.println("VEhID"+SPHelper.veh_id);

        if(SPHelper.from.equals("req")){
            rl_select_service.setVisibility(View.VISIBLE);
            service_type.setVisibility(View.GONE);
        }else{
            rl_select_service.setVisibility(View.GONE);
            service_type.setVisibility(View.VISIBLE);
        }
        if(SPHelper.is_serving.equals("yes")){
            selected_adress.setText(SPHelper.get_customer_address);
            selected_adress.setVisibility(View.VISIBLE);
            add_address.setVisibility(View.GONE);
            //get_nearest_service_centre_details();
        }else {
            add_address.setVisibility(View.VISIBLE);
            selected_adress.setVisibility(View.GONE);
        }
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.is_serving="";
                finish();
            }
        });
        rl_schedule_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(selected_adress.getText().toString().equals("")){
                    Toast.makeText(ScheduleService.this, "Please select  your address" , Toast.LENGTH_SHORT).show();
                }
                else if(tv_entered_servicedate.getText().toString().equals("")){
                    Toast.makeText(ScheduleService.this, "Please select your service date" , Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(SPHelper.from.equals("req"))
                    {
                       if(selected_service.getText().toString().equals("")){
                            Toast.makeText(ScheduleService.this, "Please enter service  type" , Toast.LENGTH_SHORT).show();
                        }else{
                           post_req_add_service();
                       }
                    }else{
                        post_schedule_service();
                    }
                }
            }
        });
        rl_transparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // rl_service_req_received.setVisibility(View.GONE);
            }
        });
        customer_profile_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_contact_support.setVisibility(View.VISIBLE);
            }
        });
        rl_transparent1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_contact_support.setVisibility(View.GONE);
            }
        });

        rl_select_adress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScheduleService.this, SelectAddress.class);
                startActivity(intent);
            }
        });
        thanks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SPHelper.from.equals("req")){
                    Intent intent = new Intent(ScheduleService.this, AdditionalServicePage.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(ScheduleService.this, VehiclePackageDetails.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
        service_date_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                date_picker = new DatePickerDialog(ScheduleService.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                tv_entered_servicedate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                serverdate=String.valueOf(dayOfMonth);
                                servermonth= String.valueOf((monthOfYear+1));
                                serveryear=String.valueOf(year);
                                service_on=serveryear+"-"+servermonth+"-"+serverdate;
                                System.out.println("service_on"+service_on);

                            }
                        }, year, month, day);
                date_picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                date_picker.show();
            }
        });
        rl_wisedrive_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+SPHelper.getSPData(ScheduleService.this, SPHelper.customer_support_phoneno, "")));
                startActivity(callIntent);
                finish();
            }
        });
    }

    private void post_schedule_service() {
        if (!Connectivity.isNetworkConnected(ScheduleService.this)) {
            Toast.makeText(ScheduleService.this, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
            return;
        } else {
            progressDialog.show();
            PojoScheduleAdress post=new PojoScheduleAdress
            (SPHelper.veh_id,SPHelper.getSPData(ScheduleService.this,SPHelper.customer_id,""),
            SPHelper.package_id,SPHelper.service_id,"",service_on,SPHelper.customer_selected_address_id,
            "","","","","","","","",
            entered_comments.getText().toString(),"");
            Call<AppResponse> call = apiInterface.post_schedule_adress(post);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                    System.out.print(response.body());
                    if (response.body() != null) {
                        AppResponse data = response.body();
                        if (data.getResponseType().equalsIgnoreCase("200")) {
                            rl_service_req_received.setVisibility(View.VISIBLE);

                        } else {
                            Toast.makeText(ScheduleService.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<AppResponse> call, Throwable th) {
                    Toast.makeText(ScheduleService.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }
    }

    private void post_req_add_service() {
        if (!Connectivity.isNetworkConnected(ScheduleService.this)) {
            Toast.makeText(ScheduleService.this, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
            return;
        } else {
            progressDialog.show();
            PojoAddService pojoAddService=new PojoAddService
                    (SPHelper.veh_id,SPHelper.getSPData(ScheduleService.this,SPHelper.customer_id,""),
                            selected_service.getText().toString(),service_on,SPHelper.customer_selected_address_id,"","","","",
                            "","","","",entered_comments.getText().toString(),"");
            Call<AppResponse> call = apiInterface.post_schedule_add_service(pojoAddService);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                    System.out.print(response.body());
                    if (response.body() != null) {
                        AppResponse data = response.body();
                        if (data.getResponseType().equalsIgnoreCase("200")) {
                            rl_service_req_received.setVisibility(View.VISIBLE);
                           // Toast.makeText(ScheduleService.this, data.getMessage(), Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(ScheduleService.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<AppResponse> call, Throwable th) {
                    Toast.makeText(ScheduleService.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }
    }
    @Override
    public void onBackPressed()
    {
        SPHelper.is_serving="";
        if(rl_contact_support.getVisibility()==View.VISIBLE){
            rl_contact_support.setVisibility(View.GONE);
        }else if(SPHelper.from.equals("req")){
            Intent intent=new Intent(ScheduleService.this,AdditionalServicePage.class);
            startActivity(intent);
        }
        else{
            Intent intent=new Intent(ScheduleService.this,VehiclePackageDetails.class);
            startActivity(intent);
        }
    }
}