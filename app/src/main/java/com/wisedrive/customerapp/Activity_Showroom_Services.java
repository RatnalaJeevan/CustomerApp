package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wisedrive.customerapp.adapters.AdapterAddAdress;
import com.wisedrive.customerapp.adapters.Adapter_Select_Date;
import com.wisedrive.customerapp.adapters.Adapter_Showroom_Services;
import com.wisedrive.customerapp.adapters.Adapter_Tracking_page;
import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.commonclasses.Common;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.PojoAddresses;
import com.wisedrive.customerapp.pojos.PojoBookService;
import com.wisedrive.customerapp.pojos.PojoDeleteAdress;
import com.wisedrive.customerapp.pojos.Pojo_Select_Date;
import com.wisedrive.customerapp.pojos.Pojo_Showroom_services;
import com.wisedrive.customerapp.pojos.Pojo_Tracking_page;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_Showroom_Services extends AppCompatActivity
{
   public RelativeLayout rl_track_service_status, rl_transperant1,rl_select_dates,rl_state,rl_city,
           rl_transperant2,confirm,rl_select_adress,rl_transperant3;
    RecyclerView rv_showroom_services;
    Adapter_Showroom_Services adapter_showroom_services;
    ArrayList<Pojo_Showroom_services>pojo_showroom_servicesArrayList;
    private ApiInterface apiInterface;
    RecyclerView rv_select_date,rv_select_adres;
    Adapter_Select_Date adapter_select_date;
    ArrayList<Pojo_Select_Date>pojo_select_dateArrayList;
   RecyclerView rv_select_service_status;
   Adapter_Tracking_page adapter_tracking_page;
   ArrayList<Pojo_Tracking_page> pojo_tracking_pageArrayList;
   ProgressBar idPBLoading;
   TextView tv_showroom_services,expired;
   ImageView back;
   RelativeLayout rl_call;
  public TextView change_adress,selected_city,selected_state;
   public EditText cust_pincode,cust_adress,cust_location;

    public String full_pay_done="",remain_amount,city_id="",state_id="",status_id="13";
   AdapterAddAdress adapterAddAdress;
   ArrayList<PojoAddresses> pojoAddAdresses;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showroom_services);
        SPHelper.current_page="book";
        getWindow().setStatusBarColor(getColor(R.color.new_app_bg));
        selected_state=findViewById(R.id.selected_state);
        selected_city=findViewById(R.id.selected_city);
        cust_adress=findViewById(R.id.cust_adress);
        cust_location=findViewById(R.id.cust_location);
        rl_state=findViewById(R.id.rl_state);
        rl_city=findViewById(R.id.rl_city);
        cust_pincode=findViewById(R.id.cust_pincode);
        rv_select_adres=findViewById(R.id.rv_select_adres);
        rl_transperant3=findViewById(R.id.rl_transperant3);
        change_adress=findViewById(R.id.change_adress);
        rl_select_adress=findViewById(R.id.rl_select_adress);
        back=findViewById(R.id.back);
        expired=findViewById(R.id.expired);
        tv_showroom_services=findViewById(R.id.tv_showroom_services);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        confirm=findViewById(R.id.confirm);
        rl_call=findViewById(R.id.rl_call);
        idPBLoading=findViewById(R.id.idPBLoading);
        rv_showroom_services=findViewById(R.id.rv_showroom_services);
        rv_select_date=findViewById(R.id.rv_select_date);
        rv_select_service_status=findViewById(R.id.rv_select_service_status);
        rl_track_service_status=findViewById(R.id.rl_track_service_status);
        rl_transperant1=findViewById(R.id.rl_transperant1);
        rl_select_dates=findViewById(R.id.rl_select_dates);
        rl_transperant2=findViewById(R.id.rl_transperant2);
        tv_showroom_services.setText(SPHelper.product_name);

        cust_pincode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(cust_pincode.getText().toString().length()>=6)
                {
                    get_pincode_details();
                    rl_city.setVisibility(View.VISIBLE);
                    rl_state.setVisibility(View.VISIBLE);
                }else {
                    rl_city.setVisibility(View.GONE);
                    rl_state.setVisibility(View.GONE);
                }
            }
        });
        change_adress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_adress_list();
                rl_select_adress.setVisibility(View.VISIBLE);
            }
        });
        rl_transperant3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_select_adress.setVisibility(View.GONE);
            }
        });


        if(SPHelper.is_exp.equalsIgnoreCase("y")){
            expired.setVisibility(View.VISIBLE);
        }else{
            expired.setVisibility(View.GONE);
        }
        rl_transperant1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_track_service_status.setVisibility(View.GONE);
            }
        });
        rl_transperant2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rl_select_dates.setVisibility(View.GONE);
            }
        });

        rl_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+SPHelper.getSPData(Activity_Showroom_Services.this,SPHelper.customer_support_phoneno,"")));
                startActivity(callIntent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Activity_Showroom_Services.this, CustomerHomepage.class);
//                startActivity(intent);
                finish();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(SPHelper.server_date.equals(""))
                {
                    Toast.makeText(Activity_Showroom_Services.this, "Please Select service date",Toast.LENGTH_SHORT).show();
                }
                else if(cust_adress.getText().toString().equals("")){
                    Toast.makeText(Activity_Showroom_Services.this, "Please enter address",Toast.LENGTH_SHORT).show();
                }
                else if(cust_location.getText().toString().equals("")){
                    Toast.makeText(Activity_Showroom_Services.this, "Please enter location",Toast.LENGTH_SHORT).show();
                }
                else if(cust_pincode.getText().toString().equals("")||cust_pincode.getText().toString().length()<6){
                    Toast.makeText(Activity_Showroom_Services.this, "Please enter valid  pincode",Toast.LENGTH_SHORT).show();
                }
                else if(selected_state.getText().toString().equals("")||selected_city.getText().toString().equals("")){
                    Toast.makeText(Activity_Showroom_Services.this, "Please enter valid  pincode",Toast.LENGTH_SHORT).show();
                }
                else{
                    System.out.println("ids"+SPHelper.veh_id+SPHelper.getSPData(Activity_Showroom_Services.this, SPHelper.customer_id, "")+
                            adapter_showroom_services.package_id+SPHelper.service_id+"13"+SPHelper.server_date);
                   book_service();
                }
            }
        });

        Intent intent=getIntent();
        String itis=intent.getStringExtra("it_is");
        if(itis.equals("cancel"))
        {
            status_id="7";
            book_service();
        }
        getVehServiceList();
    }

    public void getVehServiceList() {
        if (!Connectivity.isNetworkConnected(Activity_Showroom_Services.this)) {
            Toast.makeText(Activity_Showroom_Services.this,
                    "Plaese Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        } else {
            idPBLoading.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.getServiceList(SPHelper.veh_id,SPHelper.product_id,SPHelper.package_id,
                    SPHelper.pack_type,SPHelper.getSPData(Activity_Showroom_Services.this,SPHelper.customer_id,""));
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                    AppResponse appResponse = response.body();
                    assert appResponse != null;
                    String response_code = appResponse.getResponseType();
                    if (response.body() != null) {
                        if (response_code.equals("200")) {
                            idPBLoading.setVisibility(View.GONE);
                            pojo_showroom_servicesArrayList = new ArrayList<>();
                            pojo_showroom_servicesArrayList = appResponse.getResponseModel().getVehicleServiceList();
                            adapter_showroom_services = new Adapter_Showroom_Services(Activity_Showroom_Services.this, pojo_showroom_servicesArrayList);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Activity_Showroom_Services.this, LinearLayoutManager.VERTICAL, false);
                            rv_showroom_services.setLayoutManager(linearLayoutManager);
                            rv_showroom_services.setAdapter(adapter_showroom_services);

                            String is_adress_exist=appResponse.getResponseModel().getAddressDetails().getCustomer_address_exists();

                            if(is_adress_exist.equalsIgnoreCase("y")){
                                change_adress.setVisibility(View.VISIBLE);
                            }else {
                                change_adress.setVisibility(View.GONE);
                            }
                            Activity_Showroom_Services.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter_showroom_services.notifyDataSetChanged();
                                }
                            });
                        } else if (response_code.equals("300")) {
                             idPBLoading.setVisibility(View.GONE);
                        }
                    } else {
                         idPBLoading.setVisibility(View.GONE);
                        Toast.makeText(Activity_Showroom_Services.this, "internal server error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                    Toast.makeText(Activity_Showroom_Services.this,
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                     idPBLoading.setVisibility(View.GONE);
                }
            });
        }
    }

    public void get_track_service() {
        if (!Connectivity.isNetworkConnected(Activity_Showroom_Services.this)) {
            Toast.makeText(Activity_Showroom_Services.this,
                    "Plaese Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        } else {
            idPBLoading.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.trackService(SPHelper.service_id);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                    AppResponse appResponse = response.body();
                    assert appResponse != null;
                    String response_code = appResponse.getResponseType();
                    if (response.body() != null) {
                        if (response_code.equals("200")) {
                             idPBLoading.setVisibility(View.GONE);
                            pojo_tracking_pageArrayList= new ArrayList<>();
                            pojo_tracking_pageArrayList=appResponse.getResponseModel().getTrackService();
                            adapter_tracking_page = new Adapter_Tracking_page(Activity_Showroom_Services.this,pojo_tracking_pageArrayList);
                            LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(Activity_Showroom_Services.this, LinearLayoutManager.VERTICAL, false);
                            rv_select_service_status.setLayoutManager(linearLayoutManager2);
                            rv_select_service_status.setAdapter(adapter_tracking_page);

                            Activity_Showroom_Services.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter_tracking_page.notifyDataSetChanged();
                                }
                            });
                        } else if (response_code.equals("300")) {
                             idPBLoading.setVisibility(View.GONE);
                        }
                    } else {
                         idPBLoading.setVisibility(View.GONE);
                        Toast.makeText(Activity_Showroom_Services.this, "internal server error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                    Toast.makeText(Activity_Showroom_Services.this,
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                     idPBLoading.setVisibility(View.GONE);
                }
            });
        }
    }

    public void getDateLists() {
        if (!Connectivity.isNetworkConnected(Activity_Showroom_Services.this)) {
            Toast.makeText(Activity_Showroom_Services.this,
                    "Plaese Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        } else {
            idPBLoading.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.getDateList(SPHelper.product_id);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                    AppResponse appResponse = response.body();
                    assert appResponse != null;
                    String response_code = appResponse.getResponseType();
                    if (response.body() != null) {
                        if (response_code.equals("200")) {
                            idPBLoading.setVisibility(View.GONE);
                            pojo_select_dateArrayList = new ArrayList<>();
                            pojo_select_dateArrayList=appResponse.getResponseModel().getDateList();
                            GridLayoutManager manager =
                                    new GridLayoutManager(Activity_Showroom_Services.this,3,
                                    GridLayoutManager.VERTICAL, false);
//                            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//                                @Override
//                                public int getSpanSize(int position) {
//                                    int f_po=position%pojo_select_dateArrayList.size();
//                                    if (f_po == 0){
//                                        return 2;
//                                    }else if(f_po==1){
//                                        return 2;
//                                    }
//                                    else{
//                                        return 3;
//                                    }
//                                }
//                            });
                            rv_select_date.setLayoutManager(manager);
                            adapter_select_date = new Adapter_Select_Date(Activity_Showroom_Services.this,pojo_select_dateArrayList);
                            rv_select_date.setAdapter(adapter_select_date);

                            Activity_Showroom_Services.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter_select_date.notifyDataSetChanged();
                                }
                            });
                        } else if (response_code.equals("300")) {
                            idPBLoading.setVisibility(View.GONE);
                        }
                    } else {
                        idPBLoading.setVisibility(View.GONE);
                        Toast.makeText(Activity_Showroom_Services.this, "internal server error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                    Toast.makeText(Activity_Showroom_Services.this,
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                     idPBLoading.setVisibility(View.GONE);
                }
            });
        }
    }

    public void get_pincode_details() {
        if(!Connectivity.isNetworkConnected(Activity_Showroom_Services.this))
        {
            Toast.makeText(Activity_Showroom_Services.this,
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            idPBLoading.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.get_pincode_list(cust_pincode.getText().toString());
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response)
                {
                    if (response.body()!=null)
                    {
                        if (response.code() == 200)
                        {
                            idPBLoading.setVisibility(View.GONE);
                            //get state and city name
                            AppResponse appResponse=response.body();

                            String cityname=appResponse.getResponseModel().getGetpincodedata().getCity_name();
                            String statename=appResponse.getResponseModel().getGetpincodedata().getState_name();
                            city_id=appResponse.getResponseModel().getGetpincodedata().getCity_id();
                            state_id=appResponse.getResponseModel().getGetpincodedata().getState_id();
                            if(cityname==null){
                                Common.CallToast(Activity_Showroom_Services.this,"Enter Valid pincode",1);
                                selected_city.setText("");
                                selected_state.setText("");
                            }else{
                                selected_city.setText(cityname);
                                selected_state.setText(statename);
                            }

                        }
                        else
                        {
                            selected_city.setText("");
                            selected_state.setText("");
                            Toast.makeText(Activity_Showroom_Services.this,"Error:"+response.code(),Toast.LENGTH_SHORT).show();
                        }
                    }
                    idPBLoading.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, Throwable t) {
                    idPBLoading.setVisibility(View.GONE);
                    Toast.makeText(Activity_Showroom_Services.this,
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void book_service() {
        {
            if (!Connectivity.isNetworkConnected(Activity_Showroom_Services.this)) {
                Toast.makeText(Activity_Showroom_Services.this,
                        "Please Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            } else {
                 idPBLoading.setVisibility(View.VISIBLE);
                 PojoBookService pojoBookService=new PojoBookService(SPHelper.veh_id,SPHelper.getSPData(Activity_Showroom_Services.this, SPHelper.customer_id, ""),
                        SPHelper.ser_pac_id,SPHelper.service_id,status_id,SPHelper.server_date,
                         SPHelper.customer_selected_address_id,cust_adress.getText().toString(),cust_pincode.getText().toString(), "",cust_location.getText().toString(),city_id,
                         selected_city.getText().toString(),state_id,selected_state.getText().toString(),"","",SPHelper.package_id,SPHelper.pack_type);
                Call<AppResponse> call = apiInterface.book_service(pojoBookService);
                call.enqueue(new Callback<AppResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                        AppResponse appResponse = response.body();
                        assert appResponse != null;
                        String response_code = appResponse.getResponseType();
                        if (response.body() != null) {
                            if (response_code.equals("200")) {
                                 idPBLoading.setVisibility(View.GONE);
//
                                 rl_select_dates.setVisibility(View.GONE);
                                 getVehServiceList();
                                Toast.makeText(Activity_Showroom_Services.this, appResponse.getResponseModel().getMessage(), Toast.LENGTH_SHORT).show();


                            } else if (response_code.equals("300")) {
                                 idPBLoading.setVisibility(View.GONE);
                                Toast.makeText(Activity_Showroom_Services.this, appResponse.getResponseModel().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                             idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(Activity_Showroom_Services.this, "internal server error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                        Toast.makeText(Activity_Showroom_Services.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                         idPBLoading.setVisibility(View.GONE);
                    }
                });
            }
        }
    }

    public void getSSLists() {
        if (!Connectivity.isNetworkConnected(Activity_Showroom_Services.this)) {
            Toast.makeText(Activity_Showroom_Services.this,
                    "Plaese Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        } else {
            idPBLoading.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.get_service_details(adapter_showroom_services.package_id,"",SPHelper.dpp_id);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                    AppResponse appResponse = response.body();
                    assert appResponse != null;
                    String response_code = appResponse.getResponseType();
                    if (response.body() != null) {
                        if (response_code.equals("200")) {
                             idPBLoading.setVisibility(View.GONE);
                             SPHelper.pojo_service_includes=appResponse.getResponseModel().getServiceincludesList();
                             full_pay_done=appResponse.getResponseModel().getPartialPaymentDetails().getFull_payment_done();
                             SPHelper.remain_amount=appResponse.getResponseModel().getPartialPaymentDetails().getRemaining_amount();

                             if(adapter_showroom_services.it_is.equals("book")){
                                 status_id="13";
                                 get_book_details();
                             }else {
                                 serv_details();
                             }
                        } else if (response_code.equals("300")) {
                            idPBLoading.setVisibility(View.GONE);
                        }
                    } else {
                        idPBLoading.setVisibility(View.GONE);
                        Toast.makeText(Activity_Showroom_Services.this, "internal server error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                    Toast.makeText(Activity_Showroom_Services.this,
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    idPBLoading.setVisibility(View.GONE);
                }
            });
        }
    }

    private void get_book_details()
    {

        if(full_pay_done.equalsIgnoreCase("n"))
        {
            PopupFullPayment bottomSheetDialogFragment = new PopupFullPayment();
            bottomSheetDialogFragment.show(getSupportFragmentManager(),
                    bottomSheetDialogFragment.getTag());
        }
        else
        {

            getDateLists();
            SPHelper.customer_selected_address_id="";
            rl_select_dates.setVisibility(View.VISIBLE);
            rl_track_service_status.setVisibility(View.GONE);
        }
    }

    public void serv_details()
    {
        if(rl_select_dates.getVisibility()==View.VISIBLE){

        }else {
            PopupShowServiceDetails bottomSheetDialogFragment = new PopupShowServiceDetails();
            bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
        }
    }


    public  void get_adress_list(){
        if(!Connectivity.isNetworkConnected(Activity_Showroom_Services.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }else
        {
           // progressDialog.show();
            Call<AppResponse> call =  apiInterface.get_address_list(SPHelper.getSPData(Activity_Showroom_Services.this, SPHelper.customer_id, ""));
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response)
                {
                    AppResponse appResponse = response.body();

                    if (response.body()!=null) {
                        assert appResponse != null;
                        if (appResponse.getResponseType().equals("200")) {
                           // progressDialog.dismiss();
                            if(appResponse.getResponseModel().getAddressList().isEmpty()||appResponse.getResponseModel().getAddressList()==null){
                               // noresults.setVisibility(View.VISIBLE);
                            }
                            pojoAddAdresses = new ArrayList<>();
                            pojoAddAdresses=appResponse.getResponseModel().getAddressList();
                            adapterAddAdress = new AdapterAddAdress(pojoAddAdresses,Activity_Showroom_Services.this);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Activity_Showroom_Services.this, LinearLayoutManager.VERTICAL, false);
                            rv_select_adres.setLayoutManager(linearLayoutManager);
                            rv_select_adres.setAdapter(adapterAddAdress);

                            Activity_Showroom_Services.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapterAddAdress.notifyDataSetChanged();
                                }
                            });

                        } else if (appResponse.getResponseType().equals("300")) {
                           // progressDialog.dismiss();
                            Toast.makeText(Activity_Showroom_Services.this, "internal server error" + "response code:" + appResponse.getResponseType(), Toast.LENGTH_SHORT).show();

                        }
                    }
                    else {
                        //progressDialog.dismiss();
                        Toast.makeText(Activity_Showroom_Services.this, "internal server error" , Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AppResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                   // progressDialog.dismiss();
                }
            });
        }
    }

    public void delete_adress() {
        {
            if (!Connectivity.isNetworkConnected(Activity_Showroom_Services.this)) {
                Toast.makeText(Activity_Showroom_Services.this,
                        "Please Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            } else {
                idPBLoading.setVisibility(View.VISIBLE);
                PojoDeleteAdress pojoDeleteAdress=new PojoDeleteAdress(SPHelper.customer_selected_address_id);
                Call<AppResponse> call = apiInterface.delete_adress(pojoDeleteAdress);
                call.enqueue(new Callback<AppResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                        AppResponse appResponse = response.body();
                        assert appResponse != null;
                        String response_code = appResponse.getResponseType();
                        if (response.body() != null) {
                            if (response_code.equals("200")) {
                                idPBLoading.setVisibility(View.GONE);
                                Toast.makeText(Activity_Showroom_Services.this, appResponse.getResponseModel().getMessage(), Toast.LENGTH_SHORT).show();
                                get_adress_list();
                            } else if (response_code.equals("300")) {
                                idPBLoading.setVisibility(View.GONE);
                                Toast.makeText(Activity_Showroom_Services.this, appResponse.getResponseModel().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(Activity_Showroom_Services.this, "internal server error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                        Toast.makeText(Activity_Showroom_Services.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        idPBLoading.setVisibility(View.GONE);
                    }
                });
            }
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent = new Intent(Activity_Showroom_Services.this, CustomerHomepage.class);
//        startActivity(intent);
        finish();
    }


}