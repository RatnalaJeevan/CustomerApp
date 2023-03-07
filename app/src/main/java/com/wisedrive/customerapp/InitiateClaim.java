package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wisedrive.customerapp.adapters.AdapterClaimtypes;
import com.wisedrive.customerapp.adapters.AdapterSymptoms;
import com.wisedrive.customerapp.commonclasses.Common;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.pojos.PojoClaimTypes;
import com.wisedrive.customerapp.pojos.PojoInitiateClaim;
import com.wisedrive.customerapp.pojos.PojoSelectedSymptoms;
import com.wisedrive.customerapp.pojos.PojoSymptoms;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InitiateClaim extends AppCompatActivity
{
    private ApiInterface apiInterface;
    public AdapterClaimtypes adapterClaimtypes;
    ArrayList<PojoClaimTypes> claimTypes;
    RecyclerView rv_service_type;
    public AdapterSymptoms adapterSymptoms;
    ArrayList<PojoSymptoms> symptoms;
    ArrayList<PojoSelectedSymptoms> arrayList;
    RecyclerView rv_symptoms_list;
    DatePickerDialog date_picker;
    TextView tv_entered_breakdowndate;
    Spinner selected_veh_no;
    public String veh_no,veh_id="",serverdate="";
    ArrayList<String> vehno_list,vehid;
    RelativeLayout rl_back;
    ProgressBar progress,progress1,progress_add_claim;
    EditText breakdown_place,comments_claim;
    private static InitiateClaim instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initiate_claim);
        instance=this;
        comments_claim=findViewById(R.id.comments_claim);
        breakdown_place=findViewById(R.id.breakdown_place);
        progress_add_claim=findViewById(R.id.progress_add_claim);
        progress=findViewById(R.id.progress);
        progress1=findViewById(R.id.progress1);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        rl_back=findViewById(R.id.rl_back);
        selected_veh_no=findViewById(R.id.selected_veh_no);
        tv_entered_breakdowndate=findViewById(R.id.tv_entered_breakdowndate);
        rv_service_type=findViewById(R.id.rv_service_type);
        rv_symptoms_list=findViewById(R.id.rv_symptoms_list);
        claimTypes=new ArrayList<>();
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(InitiateClaim.this, AllClaimsPage.class);
                startActivity(intent);
            }
        });
        get_claim_type();
        get_vehno_details();
        selected_veh_no.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                TextView tv = (TextView) view;
                String item = parent.getItemAtPosition(position).toString();
                if (position == 0) {
                    tv.setTextColor(Color.rgb(191, 189, 184));
                    tv.setTextSize(15);
                    tv.setTypeface(Typeface.DEFAULT);
                } else {
                    tv.setTextColor(Color.rgb(0, 0, 0));
                    tv.setTextSize(15);
                    tv.setTypeface(Typeface.DEFAULT);
                }
                if (selected_veh_no.getSelectedItemPosition() >= 0) {
                    veh_no = vehno_list.get(position);
                    veh_id=vehid.get(position);
                    System.out.println("vehno"+veh_no);
                    System.out.println("vehid"+veh_id);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void get_vehno_details() {
        vehno_list=new ArrayList<>();
        vehid=new ArrayList<>();
        vehno_list.add("Select Vehicle No.");
        vehid.add(null);
        for(int i=0;i<SPHelper.vehDetailsList.size();i++)
        {
            vehno_list.add(SPHelper.vehDetailsList.get(i).getVehicle_no());
            vehid.add(SPHelper.vehDetailsList.get(i).getVehicle_id());
        }

        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, vehno_list);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selected_veh_no.setAdapter(dataAdapter3);
    }

    public void onbreakdowndate(View view) {
            final Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);
            date_picker = new DatePickerDialog(InitiateClaim.this,
                    new DatePickerDialog.OnDateSetListener()
                    {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                            serverdate=year+"-"+(monthOfYear + 1) +"-"+dayOfMonth;
                            tv_entered_breakdowndate.setText(Common.getDateFromString(serverdate));
                        }
                    }, year, month, day);
            date_picker.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
            date_picker.show();

    }

    public  void get_claim_type(){
        if(!Connectivity.isNetworkConnected(InitiateClaim.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }else
        {
            progress.setVisibility(View.VISIBLE);

            Call<AppResponse> call =  apiInterface.getClaimType();
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response)
                {
                    AppResponse appResponse = response.body();

                    if (response.body()!=null) {
                        assert appResponse != null;
                        if (appResponse.getResponseType().equals("200")) {
                            progress.setVisibility(View.GONE);
                            claimTypes = new ArrayList<>();
                           // claimTypes=appResponse.getResponseModel().getClaimType();
                            adapterClaimtypes=new AdapterClaimtypes(claimTypes, InitiateClaim.this);
                            GridLayoutManager layoutManager1 = new GridLayoutManager(InitiateClaim.this, 2);
                            rv_service_type.setLayoutManager(layoutManager1);
                            rv_service_type.setAdapter(adapterClaimtypes);

                            InitiateClaim.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapterClaimtypes.notifyDataSetChanged();

                                }
                            });
                        } else if (appResponse.getResponseType().equals("300")) {
                            progress.setVisibility(View.GONE);
                            Toast.makeText(InitiateClaim.this, "internal server error" + "response code:" + appResponse.getResponseType(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        progress.setVisibility(View.GONE);
                        Toast.makeText(InitiateClaim.this, "internal server error" , Toast.LENGTH_SHORT).show();
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

    public  void get_claim_symptoms(){
        if(!Connectivity.isNetworkConnected(InitiateClaim.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }else
        {
            progress1.setVisibility(View.VISIBLE);

            Call<AppResponse> call =  apiInterface.getClaimSymptoms(SPHelper.claim_type_id);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response)
                {
                    AppResponse appResponse = response.body();

                    if (response.body()!=null) {
                        assert appResponse != null;
                        if (appResponse.getResponseType().equals("200")) {
                            progress1.setVisibility(View.GONE);
                            symptoms=new ArrayList<>();
                            symptoms=appResponse.getResponseModel().getClaimSymptoms();
                            adapterSymptoms=new AdapterSymptoms(symptoms, InitiateClaim.this);
                            GridLayoutManager layoutManager2 = new GridLayoutManager(InitiateClaim.this, 3);
                            rv_symptoms_list.setLayoutManager(layoutManager2);
                            rv_symptoms_list.setAdapter(adapterSymptoms);

                            InitiateClaim.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapterClaimtypes.notifyDataSetChanged();

                                }
                            });
                        } else if (appResponse.getResponseType().equals("300")) {
                            progress1.setVisibility(View.GONE);
                            Toast.makeText(InitiateClaim.this, "internal server error" + "response code:" + appResponse.getResponseType(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        progress1.setVisibility(View.GONE);
                        Toast.makeText(InitiateClaim.this, "internal server error" , Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AppResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    progress1.setVisibility(View.GONE);
                }
            });
        }
    }

    public  void getselected_symptoms()
    {
        arrayList=new ArrayList<>();
        for (int i = 0; i < symptoms.size(); i++)
        {
            if(symptoms.get(i).getIsSelected().equals("y")){
                PojoSelectedSymptoms pojoSymptoms=new PojoSelectedSymptoms();
                pojoSymptoms.setSymptom_id(symptoms.get(i).getSymptom_id());
                arrayList.add(pojoSymptoms);
            }
        }
        System.out.println("symptomns"+arrayList);
    }

    private void post_initiate_claim() {
        if (!Connectivity.isNetworkConnected(InitiateClaim.this)) {
            Toast.makeText(InitiateClaim.this, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
            return;
        } else {
            progress_add_claim.setVisibility(View.VISIBLE);
            PojoInitiateClaim post1 = new PojoInitiateClaim(veh_id, SPHelper.getSPData(InitiateClaim.this,SPHelper.customer_id,""),
                    SPHelper.claim_type_id, breakdown_place.getText().toString(),serverdate,
                    comments_claim.getText().toString(),arrayList);
            Call<AppResponse> call = apiInterface.post_add_claim(post1);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                    System.out.print(response.body());
                    if (response.body() != null)
                    {
                        AppResponse data = response.body();
                        if (data.getResponseType().equalsIgnoreCase("200")) {
                            progress_add_claim.setVisibility(View.GONE);
                            Toast.makeText(InitiateClaim.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(InitiateClaim.this,AllClaimsPage.class);
                            startActivity(intent);
                            finish();

                        } else {
                            progress_add_claim.setVisibility(View.GONE);
                            Toast.makeText(InitiateClaim.this, data.getResponseModel().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    progress_add_claim.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<AppResponse> call, Throwable th) {
                    Toast.makeText(InitiateClaim.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    progress_add_claim.setVisibility(View.GONE);
                }
            });
        }
    }
    public static InitiateClaim getInstance() {
        return instance;
    }


    public void on_InitiateClaim(View view) {

        if(veh_id==null){

            Toast.makeText(InitiateClaim.this,"Select your vehicle",Toast.LENGTH_SHORT).show();
        }else if(veh_id.equals("")){
            Toast.makeText(InitiateClaim.this,"Select your vehicle",Toast.LENGTH_SHORT).show();
        }else if(SPHelper.claim_type_id.equals("")){
            Toast.makeText(InitiateClaim.this,"Select claim type",Toast.LENGTH_SHORT).show();
        }else if(arrayList.isEmpty()){
            Toast.makeText(InitiateClaim.this,"Select symptoms",Toast.LENGTH_SHORT).show();
        } else if(breakdown_place.getText().toString().equals("")){
            Toast.makeText(InitiateClaim.this,"Select place of the breakdown",Toast.LENGTH_SHORT).show();
        }else if(serverdate.equals("")){
            Toast.makeText(InitiateClaim.this,"Select date of the breakdown",Toast.LENGTH_SHORT).show();
        }else {
            post_initiate_claim();
        }


    }
}