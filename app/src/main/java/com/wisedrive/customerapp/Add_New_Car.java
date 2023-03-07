package com.wisedrive.customerapp;


import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;
import com.wisedrive.customerapp.adapters.Adapter_Select_Model;
import com.wisedrive.customerapp.adapters.Adapter_Uplaod_Image;
import com.wisedrive.customerapp.adapters.Adapter_select_make;
import com.wisedrive.customerapp.commonclasses.BitmapUtility;
import com.wisedrive.customerapp.commonclasses.Common;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.RequestPermissionHandler;
import com.wisedrive.customerapp.commonclasses.ResponseExtension;
import com.wisedrive.customerapp.commonclasses.ResponseListener;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.pojos.Pojo_Select_Make_list;
import com.wisedrive.customerapp.pojos.Pojo_Select_Model;
import com.wisedrive.customerapp.pojos.Pojo_Upload_Image;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;
import com.wisedrive.customerapp.services.ServiceURL;
import com.wisedrive.customerapp.services.WebService;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Add_New_Car extends AppCompatActivity {
    ImageView iv_ins;
    String it_is="";
    public int selectedObject=0;
    public ArrayList<String> finalids =new ArrayList<>();
    String upload="",filename,ins_url="";
    Uri cam_uri;
    ActivityResultLauncher<String> gallery_content ;
    ActivityResultLauncher<Intent> startCamera;
    public  boolean isServiceRunning = false;
    private Runnable runnableImage;
    private Handler handlerImage = new Handler();
    ProgressBar idPBLoading;
    public ArrayList<String> finalImages;
    String fuel_type="",trans_type="";
    public int cameto=1;
    public String selectedbankid="",selectedbankname="",selectedinsurancetype="",server_exp_date="";
    ArrayList<String> bankname,bankid,insurancetype;
    String final_year="";
    public  int cy,entered_year=0;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    TextView tv_next, tv_back, tv_make, tv_basic, tv_photos, tv_insurance, tv_calender;
    EditText selected_clr,selected_vehno,selected_kms,selected_owners,edit_policy_no;
    RelativeLayout rl_fuel_transmission_details, check_box_diesel, check_box_petrol,rl_back,rl_next,
            check_box_manual, check_box_automatic , rl_basic_details,rl_ins_copy,
            rl_photos, rl_insurance;
    ImageView tick_diesel, tick_petrol, tick_manual, tick_automatic,back;
    Spinner  spinner_insurance_type, spinner_select_bank;
    DatePickerDialog picker;
    AppCompatButton add_car_button;
    View view_make,view_basic,view_photos,view_insurance;
    RecyclerView recycler_view_select_make;
    Adapter_select_make adapter_select_make;
    ArrayList<Pojo_Select_Make_list> pojo_select_make_listArrayList;

    RecyclerView recycler_view_select_model;
    Adapter_Select_Model adapter_select_model;
    ArrayList<Pojo_Select_Model> pojo_select_modelArrayList;

    RecyclerView rv_veh_photos;
    Adapter_Uplaod_Image adapter_uplaod_image;
    ArrayList<Pojo_Upload_Image> pojo_upload_imageArrayList;
    public  String KEY = "",onclick="";
    public  String SECRET = "";
    private BasicAWSCredentials credentials;
    private AmazonS3Client s3Client;
    private RequestPermissionHandler mRequestPermissionHandler;
    public  static Add_New_Car instance;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_car);
        instance=this;
        init_params();


        get_carimage_list();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        rl_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameto++;
                show_pages();
            }
        });

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameto--;
                show_pages();

            }
        });

        Calendar cal = Calendar.getInstance();
        cy=cal.get(Calendar.YEAR);
        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        years.add("Select year");
        for (int i = 1996; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, years);

        Spinner spinYear = (Spinner)findViewById(R.id.spinner_year);
        spinYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinYear.getSelectedItemPosition() >= 0) {

                    final_year=years.get(position);
                    System.out.println("year"+final_year);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinYear.setAdapter(adapter);

        spinner_insurance_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(spinner_insurance_type.getSelectedItemPosition()>=0){
                    selectedinsurancetype = insurancetype.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spinner_select_bank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (spinner_select_bank.getSelectedItemPosition() >= 0) {
                    selectedbankid=bankid.get(position);
                    selectedbankname=bankname.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        check_box_diesel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fuel_type="Diesel";
                check_box_diesel.setBackgroundResource(R.drawable.checkbox_selected);
                check_box_petrol.setBackgroundResource(R.drawable.background_checkbox);
                tick_diesel.setVisibility(View.VISIBLE);
                tick_petrol.setVisibility(View.GONE);
            }
        });
        check_box_petrol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fuel_type="Petrol";
                check_box_diesel.setBackgroundResource(R.drawable.background_checkbox);
                check_box_petrol.setBackgroundResource(R.drawable.checkbox_selected);
                tick_diesel.setVisibility(View.GONE);
                tick_petrol.setVisibility(View.VISIBLE);
            }
        });

        check_box_manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trans_type="Manual";
                check_box_manual.setBackgroundResource(R.drawable.checkbox_selected);
                check_box_automatic.setBackgroundResource(R.drawable.background_checkbox);
                tick_manual.setVisibility(View.VISIBLE);
                tick_automatic.setVisibility(View.GONE);
            }
        });
        check_box_automatic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trans_type="Automatic";
                check_box_manual.setBackgroundResource(R.drawable.background_checkbox);
                check_box_automatic.setBackgroundResource(R.drawable.checkbox_selected);
                tick_manual.setVisibility(View.GONE);
                tick_automatic.setVisibility(View.VISIBLE);

            }
        });

        //make
        tv_make.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameto=1;
                show_pages();

            }
        });

        tv_basic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SPHelper.carbrandid.equals("")){
                    cameto--;
                    Common.CallToast(Add_New_Car.this, "select brand", 1);
                }else if(SPHelper.carmodelid.equals("")){
                    cameto--;
                    Common.CallToast(Add_New_Car.this, "select car model", 1);
                }else if(fuel_type.equals("")){
                    cameto--;
                    Common.CallToast(Add_New_Car.this, "select fuel type", 1);
                }else if(trans_type.equals("")){
                    cameto--;
                    Common.CallToast(Add_New_Car.this, "select transmission type", 1);
                }else if(final_year.equals("")|| final_year.equals("Select year")){
                    cameto--;
                    Common.CallToast(Add_New_Car.this, "select Year", 1);
                }else{
                    cameto=4;
                    rl_next.setVisibility(View.VISIBLE);
                    rl_back.setVisibility(View.VISIBLE);
                    recycler_view_select_make.setVisibility(View.GONE);
                    recycler_view_select_model.setVisibility(View.GONE);
                    rl_fuel_transmission_details.setVisibility(View.GONE);
                    rl_basic_details.setVisibility(View.VISIBLE);
                    rl_photos.setVisibility(View.GONE);
                    rl_insurance.setVisibility(View.GONE);
                    tv_make.setTextColor(getResources().getColor(R.color.lightgrey));
                    tv_basic.setTextColor(getResources().getColor(R.color.blue));
                    tv_photos.setTextColor(getResources().getColor(R.color.lightgrey));
                    tv_insurance.setTextColor(getResources().getColor(R.color.lightgrey));
                    view_basic.setVisibility(View.VISIBLE);
                    view_make.setVisibility(View.INVISIBLE);
                    view_photos.setVisibility(View.INVISIBLE);
                    view_insurance.setVisibility(View.INVISIBLE);
                    add_car_button.setVisibility(View.GONE);
                }


            }
        });
        tv_photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(SPHelper.carbrandid.equals("")){
                    cameto--;
                    Common.CallToast(Add_New_Car.this, "select brand", 1);
                }else if(SPHelper.carmodelid.equals("")){
                    cameto--;
                    Common.CallToast(Add_New_Car.this, "select car model", 1);
                }else if(fuel_type.equals("")){
                    cameto--;
                    Common.CallToast(Add_New_Car.this, "select fuel type", 1);
                }else if(trans_type.equals("")){
                    cameto--;
                    Common.CallToast(Add_New_Car.this, "select transmission type", 1);
                }else if(final_year.equals("")|| final_year.equals("Select year")){
                    cameto--;
                    Common.CallToast(Add_New_Car.this, "select Year", 1);
                }else if(selected_vehno.getText().toString().equals("")){
                    cameto--;
                    Common.CallToast(Add_New_Car.this, "Enter a Vehicle Number", 1);
                }else if(selected_kms.getText().toString().equals("")){
                    cameto--;
                    Common.CallToast(Add_New_Car.this, "Enter  kms driven", 1);
                }else if(selected_owners.getText().toString().equals("")){
                    cameto--;
                    Common.CallToast(Add_New_Car.this, "Enter no.of owners", 1);

                }else {
                    rl_next.setVisibility(View.VISIBLE);
                    rl_back.setVisibility(View.VISIBLE);
                    add_car_button.setVisibility(View.GONE);
                    recycler_view_select_make.setVisibility(View.GONE);
                    recycler_view_select_model.setVisibility(View.GONE);
                    tv_make.setTextColor(getResources().getColor(R.color.lightgrey));
                    tv_basic.setTextColor(getResources().getColor(R.color.lightgrey));
                    tv_photos.setTextColor(getResources().getColor(R.color.blue));
                    tv_insurance.setTextColor(getResources().getColor(R.color.lightgrey));
                    rl_fuel_transmission_details.setVisibility(View.GONE);
                    rl_basic_details.setVisibility(View.GONE);
                    rl_insurance.setVisibility(View.GONE);
                    rl_photos.setVisibility(View.VISIBLE);
                    view_photos.setVisibility(View.VISIBLE);
                    view_make.setVisibility(View.INVISIBLE);
                    view_insurance.setVisibility(View.INVISIBLE);
                    view_basic.setVisibility(View.INVISIBLE);

                }
            }
        });
        tv_insurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SPHelper.carbrandid.equals("")){
                    cameto--;
                    Common.CallToast(Add_New_Car.this, "select brand", 1);
                }else if(SPHelper.carmodelid.equals("")){
                    cameto--;
                    Common.CallToast(Add_New_Car.this, "select car model", 1);
                }else if(fuel_type.equals("")){
                    cameto--;
                    Common.CallToast(Add_New_Car.this, "select fuel type", 1);
                }else if(trans_type.equals("")){
                    cameto--;
                    Common.CallToast(Add_New_Car.this, "select transmission type", 1);
                }else if(final_year.equals("")|| final_year.equals("Select year")){
                    cameto--;
                    Common.CallToast(Add_New_Car.this, "select Year", 1);
                }else if(selected_vehno.getText().toString().equals("")){
                    cameto--;
                    Common.CallToast(Add_New_Car.this, "Enter a Vehicle Number", 1);
                }else if(selected_kms.getText().toString().equals("")){
                    cameto--;
                    Common.CallToast(Add_New_Car.this, "Enter  kms driven", 1);
                }else if(selected_owners.getText().toString().equals("")){
                    cameto--;
                    Common.CallToast(Add_New_Car.this, "Enter no.of owners", 1);

                }else {
                    cameto=6;
                    rl_next.setVisibility(View.GONE);
                    rl_back.setVisibility(View.VISIBLE);
                    add_car_button.setVisibility(View.VISIBLE);
                    rl_fuel_transmission_details.setVisibility(View.GONE);
                    rl_basic_details.setVisibility(View.GONE);
                    rl_photos.setVisibility(View.GONE);
                    rl_insurance.setVisibility(View.VISIBLE);
                    tv_make.setTextColor(getResources().getColor(R.color.lightgrey));
                    tv_basic.setTextColor(getResources().getColor(R.color.lightgrey));
                    tv_photos.setTextColor(getResources().getColor(R.color.lightgrey));
                    tv_insurance.setTextColor(getResources().getColor(R.color.blue));
                    view_insurance.setVisibility(View.VISIBLE);
                    view_photos.setVisibility(View.INVISIBLE);
                    view_make.setVisibility(View.INVISIBLE);
                    view_basic.setVisibility(View.INVISIBLE);
                }
            }
        });
        tv_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                picker = new DatePickerDialog(Add_New_Car.this,
                        new DatePickerDialog.OnDateSetListener() {

                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                tv_calender.setText(String.valueOf(dayOfMonth) + "/" + (monthOfYear + 1) + "/" + year);
                                server_exp_date=year+"-"+(monthOfYear + 1)+"-"+dayOfMonth;
                            }
                        }, mYear, mMonth, mDay);
                picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                picker.show();

            }
        });
        rl_ins_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclick="ins";
                open_dialog();
            }
        });
        add_car_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (upload.equals("y"))
                {
                   // Common.CallToast(Add_New_Car.this,"Images uplaoding",1);
                    Upload_after_check();
                } else {
                    add_car();
                }
            }
        });

        show_pages();
        get_Insurance_Details();
        get_ins_provider_list();
    }

    public void init_params(){
        progressDialog = new ProgressDialog(Add_New_Car.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        SPHelper.sharedPreferenceInitialization(Add_New_Car.this);
        SPHelper.saveSPdata(Add_New_Car.this, SPHelper.imagestaken, "");
        AWSMobileClient.getInstance().initialize(this).execute();
        credentials = new BasicAWSCredentials(SPHelper.getSPData(this,SPHelper.awskey,""),
                SPHelper.getSPData(this,SPHelper.awssecret,""));
        s3Client = new AmazonS3Client(credentials);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        mRequestPermissionHandler = new RequestPermissionHandler();
        bankname = new ArrayList<>();
        bankid = new ArrayList<>();
        insurancetype = new ArrayList<>();
        iv_ins=findViewById(R.id.iv_ins);
        rl_ins_copy=findViewById(R.id.rl_ins_copy);
        edit_policy_no=findViewById(R.id.edit_policy_no);
        idPBLoading=findViewById(R.id.idPBLoading);
        rl_back=findViewById(R.id.rl_back);
        rl_next=findViewById(R.id.rl_next);
        back=findViewById(R.id.back);
        selected_clr=findViewById(R.id.selected_clr);
        selected_vehno=findViewById(R.id.selected_vehno);
        selected_kms=findViewById(R.id.selected_kms);
        selected_owners=findViewById(R.id.selected_owners);
        recycler_view_select_make = findViewById(R.id.recycler_view_select_make);
        recycler_view_select_model = findViewById(R.id.recycler_view_select_model);
        rv_veh_photos = findViewById(R.id.recycler_view_upload_images);
        rl_fuel_transmission_details = findViewById(R.id.rl_fuel_transmission_details);
        rl_basic_details = findViewById(R.id.rl_basic_details);
        rl_photos = findViewById(R.id.rl_photos);
        rl_insurance = findViewById(R.id.rl_insurance);
        tv_next = findViewById(R.id.tv_next);
        spinner_select_bank = findViewById(R.id.spinner_select_bank);
        spinner_insurance_type=findViewById(R.id.spinner_insurance_type);
        view_basic=findViewById(R.id.view_basic);
        tv_basic = findViewById(R.id.tv_basic);
        check_box_petrol = findViewById(R.id.check_box_petrol);
        check_box_diesel = findViewById(R.id.check_box_diesel);
        check_box_manual = findViewById(R.id.check_box_manual);
        check_box_automatic = findViewById(R.id.check_box_automatic);
        tick_diesel = findViewById(R.id.tick_diesel);
        tick_petrol = findViewById(R.id.tick_petrol);
        tick_manual = findViewById(R.id.tick_manual);
        tick_automatic = findViewById(R.id.tick_automatic);
        view_make=findViewById(R.id.view_make);
        tv_make = findViewById(R.id.tv_make);
        view_photos=findViewById(R.id.view_photos);
        tv_photos = findViewById(R.id.tv_photos);
        add_car_button = findViewById(R.id.add_car_button);
        view_insurance=findViewById(R.id.view_insurance);
        tv_insurance = findViewById(R.id.tv_insurance);
        tv_calender = findViewById(R.id.tv_calender);
    }
    public void show_pages()
    {
            //carmake
            if (cameto == 1 ) {

                rl_next.setVisibility(View.VISIBLE);
                rl_back.setVisibility(View.GONE);
                add_car_button.setVisibility(View.GONE);

                tv_make.setTextColor(getResources().getColor(R.color.blue));
                tv_basic.setTextColor(getResources().getColor(R.color.lightgrey));
                tv_photos.setTextColor(getResources().getColor(R.color.lightgrey));
                tv_insurance.setTextColor(getResources().getColor(R.color.lightgrey));
                rl_fuel_transmission_details.setVisibility(View.GONE);
                rl_basic_details.setVisibility(View.GONE);
                rl_photos.setVisibility(View.GONE);
                rl_insurance.setVisibility(View.GONE);

                view_make.setVisibility(View.VISIBLE);
                view_basic.setVisibility(View.GONE);
                view_photos.setVisibility(View.GONE);
                view_insurance.setVisibility(View.GONE);

                if(!SPHelper.carbrandid.equals("")){
                    recycler_view_select_make.setVisibility(View.VISIBLE);
                    recycler_view_select_model.setVisibility(View.GONE);
                }else{
                    get_carbrands_list();
                }

            }
            //carmodel
            else if (cameto == 2 )
            {
                if (SPHelper.carbrandid.equals("")) {
                    cameto--;
                    Common.CallToast(Add_New_Car.this, "select brand", 1);
                } else {
                    if(!SPHelper.carmodelid.equals("")){
                        recycler_view_select_make.setVisibility(View.GONE);
                        recycler_view_select_model.setVisibility(View.VISIBLE);
                    }else{
                        get_carmodel_list();
                    }
                    rl_next.setVisibility(View.VISIBLE);
                    rl_back.setVisibility(View.VISIBLE);
                    tv_make.setTextColor(getResources().getColor(R.color.blue));
                    tv_basic.setTextColor(getResources().getColor(R.color.lightgrey));
                    tv_photos.setTextColor(getResources().getColor(R.color.lightgrey));
                    tv_insurance.setTextColor(getResources().getColor(R.color.lightgrey));
                    rl_fuel_transmission_details.setVisibility(View.GONE);
                    rl_basic_details.setVisibility(View.GONE);
                    rl_photos.setVisibility(View.GONE);
                    rl_insurance.setVisibility(View.GONE);
                    view_make.setVisibility(View.VISIBLE);
                    view_basic.setVisibility(View.INVISIBLE);
                    view_photos.setVisibility(View.INVISIBLE);
                    view_insurance.setVisibility(View.INVISIBLE);
                    add_car_button.setVisibility(View.GONE);
                }

            }
            //fuel petrol
            else if (cameto == 3)
            {
                if (SPHelper.carmodelid.equals("")) {
                    cameto--;
                    Common.CallToast(Add_New_Car.this, "select model", 1);
                } else {
                    rl_next.setVisibility(View.VISIBLE);
                    rl_back.setVisibility(View.VISIBLE);
                    recycler_view_select_make.setVisibility(View.GONE);
                    recycler_view_select_model.setVisibility(View.GONE);
                    tv_make.setTextColor(getResources().getColor(R.color.blue));
                    tv_basic.setTextColor(getResources().getColor(R.color.lightgrey));
                    tv_photos.setTextColor(getResources().getColor(R.color.lightgrey));
                    tv_insurance.setTextColor(getResources().getColor(R.color.lightgrey));
                    rl_fuel_transmission_details.setVisibility(View.VISIBLE);
                    rl_basic_details.setVisibility(View.GONE);
                    rl_photos.setVisibility(View.GONE);
                    rl_insurance.setVisibility(View.GONE);
                    view_make.setVisibility(View.VISIBLE);
                    view_basic.setVisibility(View.INVISIBLE);
                    view_photos.setVisibility(View.INVISIBLE);
                    view_insurance.setVisibility(View.INVISIBLE);
                    add_car_button.setVisibility(View.GONE);
                }

            }
            //basic
            else if (cameto == 4 ) {
                if(SPHelper.carbrandid.equals("")){
                    cameto--;
                    Common.CallToast(Add_New_Car.this, "select brand", 1);
                }else if(SPHelper.carmodelid.equals("")){
                    cameto--;
                    Common.CallToast(Add_New_Car.this, "select car model", 1);
                }else if(fuel_type.equals("")){
                    cameto--;
                    Common.CallToast(Add_New_Car.this, "select fuel type", 1);
                }else if(trans_type.equals("")){
                    cameto--;
                    Common.CallToast(Add_New_Car.this, "select transmission type", 1);
                }else if(final_year.equals("")|| final_year.equals("Select year")){
                    cameto--;
                    Common.CallToast(Add_New_Car.this, "select Year", 1);
                }else{
                    rl_next.setVisibility(View.VISIBLE);
                    rl_back.setVisibility(View.VISIBLE);
                    recycler_view_select_make.setVisibility(View.GONE);
                    recycler_view_select_model.setVisibility(View.GONE);
                    rl_fuel_transmission_details.setVisibility(View.GONE);
                    rl_basic_details.setVisibility(View.VISIBLE);
                    rl_photos.setVisibility(View.GONE);
                    rl_insurance.setVisibility(View.GONE);
                    tv_make.setTextColor(getResources().getColor(R.color.lightgrey));
                    tv_basic.setTextColor(getResources().getColor(R.color.blue));
                    tv_photos.setTextColor(getResources().getColor(R.color.lightgrey));
                    tv_insurance.setTextColor(getResources().getColor(R.color.lightgrey));
                    view_basic.setVisibility(View.VISIBLE);
                    view_make.setVisibility(View.INVISIBLE);
                    view_photos.setVisibility(View.INVISIBLE);
                    view_insurance.setVisibility(View.INVISIBLE);
                    add_car_button.setVisibility(View.GONE);
                }

            }
            //photos
            else if (cameto == 5 ) {
                if(SPHelper.carbrandid.equals("")){
                    cameto--;
                    Common.CallToast(Add_New_Car.this, "select brand", 1);
                }else if(SPHelper.carmodelid.equals("")){
                    cameto--;
                    Common.CallToast(Add_New_Car.this, "select car model", 1);
                }else if(fuel_type.equals("")){
                    cameto--;
                    Common.CallToast(Add_New_Car.this, "select fuel type", 1);
                }else if(trans_type.equals("")){
                    cameto--;
                    Common.CallToast(Add_New_Car.this, "select transmission type", 1);
                }else if(final_year.equals("")|| final_year.equals("Select year")){
                    cameto--;
                    Common.CallToast(Add_New_Car.this, "select Year", 1);
                }else if(selected_vehno.getText().toString().equals("")){
                    cameto--;
                    Common.CallToast(Add_New_Car.this, "Enter a Vehicle Number", 1);
                }else if(selected_kms.getText().toString().equals("")){
                    cameto--;
                    Common.CallToast(Add_New_Car.this, "Enter  kms driven", 1);
                }else if(selected_owners.getText().toString().equals("")){
                    cameto--;
                    Common.CallToast(Add_New_Car.this, "Enter no.of owners", 1);

                }else {
                    rl_next.setVisibility(View.VISIBLE);
                    rl_back.setVisibility(View.VISIBLE);
                    add_car_button.setVisibility(View.GONE);
                    recycler_view_select_make.setVisibility(View.GONE);
                    recycler_view_select_model.setVisibility(View.GONE);
                    tv_make.setTextColor(getResources().getColor(R.color.lightgrey));
                    tv_basic.setTextColor(getResources().getColor(R.color.lightgrey));
                    tv_photos.setTextColor(getResources().getColor(R.color.blue));
                    tv_insurance.setTextColor(getResources().getColor(R.color.lightgrey));
                    rl_fuel_transmission_details.setVisibility(View.GONE);
                    rl_basic_details.setVisibility(View.GONE);
                    rl_insurance.setVisibility(View.GONE);
                    rl_photos.setVisibility(View.VISIBLE);
                    view_photos.setVisibility(View.VISIBLE);
                    view_make.setVisibility(View.INVISIBLE);
                    view_insurance.setVisibility(View.INVISIBLE);
                    view_basic.setVisibility(View.INVISIBLE);

                }
            }
            //insuran
            else if (cameto == 6 ) {
                if(SPHelper.carbrandid.equals("")){
                    cameto--;
                    Common.CallToast(Add_New_Car.this, "select brand", 1);
                }else if(SPHelper.carmodelid.equals("")){
                    cameto--;
                    Common.CallToast(Add_New_Car.this, "select car model", 1);
                }else if(fuel_type.equals("")){
                    cameto--;
                    Common.CallToast(Add_New_Car.this, "select fuel type", 1);
                }else if(trans_type.equals("")){
                    cameto--;
                    Common.CallToast(Add_New_Car.this, "select transmission type", 1);
                }else if(final_year.equals("")|| final_year.equals("Select year")){
                    cameto--;
                    Common.CallToast(Add_New_Car.this, "select Year", 1);
                }else if(selected_vehno.getText().toString().equals("")){
                    cameto--;
                    Common.CallToast(Add_New_Car.this, "Enter a Vehicle Number", 1);
                }else if(selected_kms.getText().toString().equals("")){
                    cameto--;
                    Common.CallToast(Add_New_Car.this, "Enter  kms driven", 1);
                }else if(selected_owners.getText().toString().equals("")){
                    cameto--;
                    Common.CallToast(Add_New_Car.this, "Enter no.of owners", 1);

                }else {
                    rl_next.setVisibility(View.GONE);
                    rl_back.setVisibility(View.VISIBLE);
                    add_car_button.setVisibility(View.VISIBLE);
                    rl_fuel_transmission_details.setVisibility(View.GONE);
                    rl_basic_details.setVisibility(View.GONE);
                    rl_photos.setVisibility(View.GONE);
                    rl_insurance.setVisibility(View.VISIBLE);
                    tv_make.setTextColor(getResources().getColor(R.color.lightgrey));
                    tv_basic.setTextColor(getResources().getColor(R.color.lightgrey));
                    tv_photos.setTextColor(getResources().getColor(R.color.lightgrey));
                    tv_insurance.setTextColor(getResources().getColor(R.color.blue));
                    view_insurance.setVisibility(View.VISIBLE);
                    view_photos.setVisibility(View.INVISIBLE);
                    view_make.setVisibility(View.INVISIBLE);
                    view_basic.setVisibility(View.INVISIBLE);
                }
            }

    }
    public  void get_carbrands_list() {
        {
            if (!Connectivity.isNetworkConnected(Add_New_Car.this)) {
                Toast.makeText(Add_New_Car.this,
                        "Plaese Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            } else {
                // progress_bar.setVisibility(View.VISIBLE);
                Call<AppResponse> call = apiInterface.getBrandList();
                call.enqueue(new Callback<AppResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                        AppResponse appResponse = response.body();
                        assert appResponse != null;
                        String response_code = appResponse.getResponseType();
                        if (response.body() != null) {
                            if (response_code.equals("200"))
                            {
                                // progress_bar.setVisibility(View.GONE);
                                recycler_view_select_model.setVisibility(View.GONE);
                                recycler_view_select_make.setVisibility(View.VISIBLE);
                                pojo_select_make_listArrayList=new ArrayList<>();
                                pojo_select_make_listArrayList=appResponse.getResponseModel().getBrandList();
                                adapter_select_make = new Adapter_select_make(Add_New_Car.this, pojo_select_make_listArrayList);
                                GridLayoutManager layoutManager = new GridLayoutManager(Add_New_Car.this, 4);
                                recycler_view_select_make.setLayoutManager(layoutManager);
                                recycler_view_select_make.setAdapter(adapter_select_make);
                                Add_New_Car.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter_select_make.notifyDataSetChanged();
                                    }
                                });
                            } else if (response_code.equals("300")) {
                                // progress_bar.setVisibility(View.GONE);
                            }
                        } else {
                            // progress_bar.setVisibility(View.GONE);
                            Toast.makeText(Add_New_Car.this, "internal server error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                        Toast.makeText(Add_New_Car.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        // progress_bar.setVisibility(View.GONE);
                    }
                });
            }
        }
    }

    public  void get_carmodel_list()
    {
        {
            if (!Connectivity.isNetworkConnected(Add_New_Car.this)) {
                Toast.makeText(Add_New_Car.this,
                        "Plaese Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            } else {
                // progress_bar.setVisibility(View.VISIBLE);
                Call<AppResponse> call = apiInterface.getModelList(SPHelper.carbrandid);
                call.enqueue(new Callback<AppResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                        AppResponse appResponse = response.body();
                        assert appResponse != null;
                        String response_code = appResponse.getResponseType();
                        if (response.body() != null) {
                            if (response_code.equals("200"))
                            {
                                //  progress_bar.setVisibility(View.GONE);
                                recycler_view_select_model.setVisibility(View.VISIBLE);
                                recycler_view_select_make.setVisibility(View.GONE);

                                pojo_select_modelArrayList = new ArrayList<>();
                                pojo_select_modelArrayList=appResponse.getResponseModel().getModelList();

                                adapter_select_model = new Adapter_Select_Model(Add_New_Car.this, pojo_select_modelArrayList);
                                GridLayoutManager layoutManager1 = new GridLayoutManager(Add_New_Car.this, 3);
                                recycler_view_select_model.setLayoutManager(layoutManager1);
                                recycler_view_select_model.setAdapter(adapter_select_model);

                                Add_New_Car.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter_select_model.notifyDataSetChanged();
                                    }
                                });
                            } else if (response_code.equals("300")) {
                                // progress_bar.setVisibility(View.GONE);
                            }
                        } else {
                            // progress_bar.setVisibility(View.GONE);
                            Toast.makeText(Add_New_Car.this, "internal server error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                        Toast.makeText(Add_New_Car.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        //progress_bar.setVisibility(View.GONE);
                    }
                });
            }
        }
    }

    public  void get_carimage_list()
    {
        {
            if (!Connectivity.isNetworkConnected(Add_New_Car.this)) {
                Toast.makeText(Add_New_Car.this,
                        "Plaese Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            } else {
                // idPBLoading.setVisibility(View.VISIBLE);
                Call<AppResponse> call = apiInterface.get_car_imagelist();
                call.enqueue(new Callback<AppResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                        AppResponse appResponse = response.body();
                        assert appResponse != null;
                        String response_code = appResponse.getResponseType();
                        if (response.body() != null) {
                            if (response_code.equals("200"))
                            {
                                //idPBLoading.setVisibility(View.GONE);
                                pojo_upload_imageArrayList = new ArrayList<>();
                                pojo_upload_imageArrayList=appResponse.getResponseModel().getGetvehicleimages();
                                adapter_uplaod_image = new Adapter_Uplaod_Image(Add_New_Car.this, pojo_upload_imageArrayList);
                                GridLayoutManager linearLayoutManager2 = new GridLayoutManager(Add_New_Car.this, 3);
                                rv_veh_photos.setLayoutManager(linearLayoutManager2);
                                rv_veh_photos.setAdapter(adapter_uplaod_image);

                                Add_New_Car.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                       // adapterCarImagesList.notifyDataSetChanged();
                                    }
                                });
                            } else if (response_code.equals("300")) {
                                // idPBLoading.setVisibility(View.GONE);
                            }
                        } else {
                            //idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(Add_New_Car.this, "internal server error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                        Toast.makeText(Add_New_Car.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        // idPBLoading.setVisibility(View.GONE);
                    }
                });
            }
        }
    }
    private void get_Insurance_Details() {

        insurancetype.add("Select Insurance type");
        insurancetype.add("Comprehensive - Standard");
        insurancetype.add("Comprehensive - Cover");
        insurancetype.add("Third Party Liability");
        insurancetype.add("Own Damage Cover");
        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(Add_New_Car.this, android.R.layout.simple_spinner_item, insurancetype);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_insurance_type.setAdapter(dataAdapter3);
    }
    public void get_ins_provider_list() {
        if(!Connectivity.isNetworkConnected(Add_New_Car.this))
        {
            Toast.makeText(Add_New_Car.this,
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            //idPBLoading.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.getins_pro_list();
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                    if (response.body()!=null) {
                        if (response.code() == 200)
                        {
                            //idPBLoading.setVisibility(View.GONE);
                            AppResponse appResponse = response.body();
                            bankname.clear();
                            bankid.clear();

                            bankname.add("Select BankName");
                            bankid.add("0");

                            for (int i = 0; i < appResponse.getResponseModel().getProviderList().size(); i++)
                            {
                                bankname.add(appResponse.getResponseModel().getProviderList().get(i).getInsurance_provider());
                                bankid.add(appResponse.getResponseModel().getProviderList().get(i).getId());
                            }
                            Add_New_Car.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    status();
                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(Add_New_Car.this,"Error:"+response.code(),Toast.LENGTH_SHORT).show();
                        }
                    }
                    //idPBLoading.setVisibility(View.GONE);
                }
                @Override
                public void onFailure(@NotNull Call<AppResponse> call, Throwable t) {
                    // idPBLoading.setVisibility(View.GONE);
                    Toast.makeText(Add_New_Car.this,
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    public void status()
    {
        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(Add_New_Car.this, android.R.layout.simple_spinner_item, bankname);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_select_bank.setAdapter(dataAdapter3);
    }

    public void open_dialog(){
        final Dialog dialog = new Dialog(Add_New_Car.this);
        dialog.setContentView(R.layout.customized_photo_alert_dialogue);
        TextView cancel = (TextView) dialog.findViewById(R.id.textcancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        TextView textView1 = (TextView) dialog.findViewById(R.id.textgallery);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //gallery

                mRequestPermissionHandler.requestPermission(Add_New_Car.this, new String[]
                        {
                                android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
                        }, selectedObject, new RequestPermissionHandler.RequestPermissionListener()
                {
                    @Override
                    public void onSuccess() {
                        it_is = "g";
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivityForResult(pickPhoto,selectedObject);
                    }

                    @Override
                    public void onFailed() {
                        System.out.println("denied");
                    }
                });
                dialog.cancel();
            }
        });
        TextView textView = (TextView) dialog.findViewById(R.id.textcapture);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //camera
                mRequestPermissionHandler.requestPermission(Add_New_Car.this, new String[]{
                        Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, 123, new RequestPermissionHandler.RequestPermissionListener() {
                    @Override
                    public void onSuccess()
                    {
                        System.out.println("Succeed");
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                           // pickCamera();
                            it_is = "c";
                            CallCamera();
                        }

                    }
                    @Override
                    public void onFailed() {
                        System.out.println("denied");
                    }
                });

                dialog.cancel();
            }
        });
        dialog.show();
    }

    public void CallCamera() {

        mRequestPermissionHandler.requestPermission(Add_New_Car.this, new String[]{
                Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, 123, new RequestPermissionHandler.RequestPermissionListener() {
            @Override
            public void onSuccess() {
                System.out.println("Succeed");
                openCamera();
            }
            @Override
            public void onFailed() {
                System.out.println("denied");
            }
        });
    }
    void openCamera()
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(Add_New_Car.this.getPackageManager()) != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("-yyyy_MM_dd_HH_mm_ss_SSSSSS'.jpg'");
            String fineName = dateFormat.format(new Date());
            filename = BitmapUtility.PictUtil.getSavePath().getPath() + "/" + fineName;
            cam_uri = FileProvider.getUriForFile(Add_New_Car.this,
                    BuildConfig.APPLICATION_ID + ".provider", new File(filename));
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, cam_uri);
            startActivityForResult(takePictureIntent, selectedObject);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK &&it_is.equals("g"))
        {
            Uri uri=data.getData();
            SimpleDateFormat dateFormat = new SimpleDateFormat("-dd_MMM_yyyy_HH_mm_ss_SSSSSS'.jpg'");
            String fineName = dateFormat.format(new Date());
            filename = BitmapUtility.PictUtil.getSavePath1().getPath() + "/" + "Wisedrive" + fineName;
            String OriginalFileName = null;
            try {
                OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs4(Add_New_Car.this, uri, filename, new Pair<Integer, Integer>(800, 800), "/");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if(onclick.equals("ins")){
                iv_ins.setImageURI(uri);
                upload_to_s3(uri);
            }else{
                upload="y";
                pojo_upload_imageArrayList.get(adapter_uplaod_image.sel_position).setImage(uri);
                pojo_upload_imageArrayList.get(adapter_uplaod_image.sel_position).setFilename(OriginalFileName);
                adapter_uplaod_image.notifyDataSetChanged();
                idPBLoading.setVisibility(View.GONE);
            }
        }
        else if(resultCode == RESULT_OK &&it_is.equals("c"))
        {
            if (onclick.equals(""))
            {
                Add_New_Car.this.runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        upload ="y";
                        String OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs(filename, new Pair<Integer, Integer>(800, 800), "/");
                        cam_uri = FileProvider.getUriForFile(Add_New_Car.this,
                                BuildConfig.APPLICATION_ID + ".provider", new File(OriginalFileName));
                        pojo_upload_imageArrayList.get(requestCode).setImage(cam_uri);
                        pojo_upload_imageArrayList.get(requestCode).setFilename(OriginalFileName);
                        adapter_uplaod_image.notifyDataSetChanged();
                        idPBLoading.setVisibility(View.GONE);
                    }
                });
            }
            else{

                Add_New_Car.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs(filename, new Pair<Integer, Integer>(2040, 1080), "/");
                        cam_uri = FileProvider.getUriForFile(Add_New_Car.this,
                                BuildConfig.APPLICATION_ID + ".provider", new File(OriginalFileName));
                        filename = OriginalFileName;
                        iv_ins.setImageURI(cam_uri);

                        if (!Connectivity.isNetworkConnected(Add_New_Car.this)) {
                            idPBLoading.setVisibility(View.GONE);
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(Add_New_Car.this);
                            builder1.setMessage("Please retry to Submit your Details");
                            builder1.setCancelable(true);
                            builder1.setPositiveButton(
                                    "RETRY",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            validate();
                                        }
                                    });
                            AlertDialog alert11 = builder1.create();
                            alert11.show();
                            return;
                        }

                        upload_to_s3(cam_uri);
                    }
                });
            }
        }
    }

    private void validate(){}

    public  void upload_to_s3(Uri imageUri){
        try {
            idPBLoading.setVisibility(View.VISIBLE);
            final TransferUtility transferUtility =
                    TransferUtility.builder()
                            .context(Add_New_Car.this)
                            .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                            .s3Client(s3Client)
                            .build();
            final String key = "Cust_Insurance/" + imageUri.getLastPathSegment();
            final TransferObserver uploadObserver =
                    transferUtility.upload(key, new File(filename));
            uploadObserver.setTransferListener(new TransferListener() {
                @Override
                public void onStateChanged(int id, TransferState state) {
                    if (TransferState.COMPLETED == state) {
                        Toast.makeText(Add_New_Car.this, "Insurance uploaded!", Toast.LENGTH_SHORT).show();
                        String finalurl = s3Client.getResourceUrl("ab-prod-container", key);
                        System.out.print(finalurl);
                        Add_New_Car.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                idPBLoading.setVisibility(View.GONE);
                                progressDialog.cancel();
                            }
                        });
                        ins_url = finalurl;

                    } else if (TransferState.FAILED == state) {

                        Add_New_Car.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                idPBLoading.setVisibility(View.GONE);
                                progressDialog.cancel();
                            }
                        });
                    }
                }

                @Override
                public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                    float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                    int percentDone = (int) percentDonef;

                }

                @Override
                public void onError(int id, Exception ex) {
                    ex.printStackTrace();
                    Add_New_Car.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            idPBLoading.setVisibility(View.GONE);
                            progressDialog.cancel();
                        }
                    });
                }

            });
        } catch (Exception je) {

            je.printStackTrace();
        }
    }

    public ProgressDialog mDialog;
    public void Upload_after_check()
    {
        int totalImagesCount = 0;

            for (Pojo_Upload_Image check : pojo_upload_imageArrayList) {
                if (check.getImage() != null) {
                    totalImagesCount++;
                }
            }

        if (totalImagesCount < 1)
        {
            Common.CallToast(Add_New_Car.this, "Upload atleast 1 image", 3);
            return;
        }
        if (mDialog == null) {
            mDialog = new ProgressDialog(Add_New_Car.this);
            mDialog.setMessage("Please wait...");
            mDialog.setCancelable(false);
        }
        mDialog.show();
        final String CheckVerification = "N";
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                final JSONArray uploadImages = new JSONArray();

                    for (Pojo_Upload_Image imageUploadDataModel : pojo_upload_imageArrayList) {
                        if (imageUploadDataModel.getImage() != null) {
                            String OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs(imageUploadDataModel.getFilename(), new Pair<Integer, Integer>(1500, 1500), "/");
                            uploadImages.put(OriginalFileName);
                        }
                    }


                mDialog.dismiss();
                Add_New_Car.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SPHelper.saveSPdata(Add_New_Car.this, SPHelper.imagestaken, uploadImages.toString());
                        checkupload();
                    }
                });
            }
        });
    }

    public void checkupload()
    {
       // Toast.makeText(Add_New_Car.this, "check upload method started", Toast.LENGTH_SHORT).show();

        if (!Connectivity.isNetworkConnected(Add_New_Car.this)) {
            Toast.makeText(Add_New_Car.this, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
            return;
        }
        if (SPHelper.getSPData(Add_New_Car.this, SPHelper.imagestaken, "").trim().length() > 0)
        {
            try {
                JSONArray images = new JSONArray(SPHelper.getSPData(Add_New_Car.this, SPHelper.imagestaken, ""));
                if (images.length() >1||images.length()==1) {
                    finalImages = new ArrayList<>();
                    countDownStart();
                    return;
                }
            } catch (JSONException JE) {
                JE.printStackTrace();
            }
        } else {
           // Toast.makeText(Add_New_Car.this, "Please capture all 4 images", Toast.LENGTH_SHORT).show();

        }
    }
    private void countDownStart()
    {
        // Toast.makeText(Add_New_Car.this, "count down method started", Toast.LENGTH_SHORT).show();

        idPBLoading.setVisibility(View.VISIBLE);
        runnableImage = new Runnable() {
            @Override
            public void run()
            {
                try
                {
                    if (!isServiceRunning)
                    {
                        if (!Connectivity.isNetworkConnected(Add_New_Car.this))
                        {
                            handlerImage.removeCallbacks(this);
                            idPBLoading.setVisibility(View.GONE);
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(Add_New_Car.this);
                            builder1.setMessage("Please retry to Submit your Details");
                            builder1.setCancelable(true);
                            builder1.setPositiveButton("RETRY", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    countDownStart();
                                }
                            });
                            AlertDialog alert11 = builder1.create();
                            alert11.show();
                            return;
                        }
                        JSONArray images = new JSONArray(SPHelper.getSPData(Add_New_Car.this, SPHelper.imagestaken, ""));
                        if ( finalImages.size() == images.length())
                        {
                            for(int i= 0;i<finalImages.size();i++)
                            {
                                pojo_upload_imageArrayList.get(i).setImageurl(finalImages.get(i));
                            }
                            servicecallwithimage();
                        }
                        else {
                             upload(images.getString(finalImages.size()));
                        }
                    }
                    handlerImage.postDelayed(this, 3000);
                } catch (Exception e) {
                    e.printStackTrace();
                    handlerImage.postDelayed(this, 3000);
                }
            }
        };
        handlerImage.postDelayed(runnableImage, 500);
    }
    public void upload(final String filename)
    {
        // Toast.makeText(getApplicationContext(), "Image Upload started", Toast.LENGTH_SHORT).show();
        isServiceRunning = true;
        // StorageReference storageRef = storage.getReference();
        File imageFile = new File(filename);
        Uri uri = Uri.fromFile(imageFile);
        try {
            final TransferUtility transferUtility =
                    TransferUtility.builder()
                            .context(Add_New_Car.this)
                            .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                            .s3Client(s3Client)
                            .build();
            final String key = "Customer_VehicleImage/" +SPHelper.getSPData(Add_New_Car.this, SPHelper.customer_id, "")+ uri.getLastPathSegment();
            final TransferObserver uploadObserver =
                    transferUtility.upload(key, new File(filename));
            uploadObserver.setTransferListener(new TransferListener() {
                @Override
                public void onStateChanged(int id, TransferState state) {
                    if (TransferState.COMPLETED == state) {
                        //Toast.makeText(getApplicationContext(), "Upload Completed!", Toast.LENGTH_SHORT).show();
                        String finalurl = s3Client.getResourceUrl("ab-prod-container", key);
                        System.out.print(finalurl);
                        finalImages.add(finalurl);
                        isServiceRunning = false;
                    } else if (TransferState.FAILED == state) {
                        isServiceRunning = false;
                    }
                }

                @Override
                public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                    float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                    int percentDone = (int) percentDonef;

                }

                @Override
                public void onError(int id, Exception ex) {
                    ex.printStackTrace();
                    isServiceRunning = false;
                }

            });

        } catch (Exception je) {

            je.printStackTrace();
            isServiceRunning = false;
        }
    }

    public void add_car()
    {
        JSONObject params = new JSONObject();
        try {
            params.put("leadId", SPHelper.getSPData(Add_New_Car.this,SPHelper.lead_id,""));
            params.put("vehNo",selected_vehno.getText().toString());
            params.put("sellingPrice","");
            params.put("odometer",selected_kms.getText().toString());
            params.put("noOfOwner", selected_owners.getText().toString());
            params.put("color",selected_clr.getText().toString().trim());
            params.put("insType",selectedinsurancetype);
            params.put("insProvider",selectedbankname);
            params.put("policyNo",edit_policy_no.getText().toString());
            params.put("expDate",server_exp_date);
            params.put("customerId",SPHelper.getSPData(Add_New_Car.this,SPHelper.customer_id,""));
            params.put("modelId",SPHelper.carmodelid);
            params.put("fuel", fuel_type);
            params.put("manfYear",final_year);
            params.put("transType",trans_type);
            params.put("insCopy",ins_url);
            params.put("rcFront","");
            params.put("rcBack","");
            JSONArray imageraa = new JSONArray();
            params.put("imagesArr",imageraa);
            System.out.println("post_data"+params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new WebService().LoadwithUrl(Add_New_Car.this, ServiceURL.BASEURL, WebService.HttpMethod.post,
                null, WebService.RequestType.shared.REST(ServiceURL.addcarurl, WebService.RESTType.JSONBody,
                        params),
                true, 0, new ResponseListener()
                {
                    @Override
                    public void ResponseSuccess(ResponseExtension response) {
                        if (response.getResponseType().equalsIgnoreCase("200")) {
                            System.out.println(response);
                            JSONObject tktobj = response.getResponseObject();
                            try {
                                idPBLoading.setVisibility(View.GONE);
                                upload ="";
                                SPHelper.isSuccess="add";
                                SPHelper.comingfrom="added";
                                SPHelper.fragment_is="cars";
                               // Toast.makeText(Add_New_Car.this, "You have successfully added your car", Toast.LENGTH_SHORT).show();
                                SPHelper.cf_msg="You have successfully added\n"+SPHelper.brand_name+"\t"+SPHelper.car_model_name+"\t"+
                                        fuel_type+"\t"+"-"+selected_vehno.getText().toString();
                                Intent intent=new Intent(Add_New_Car.this, CustomerHomepage.class);
                                startActivity(intent);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        else if(response.getResponseType().equalsIgnoreCase("300")){
                            Toast.makeText(Add_New_Car.this, response.getResponseMessage(), Toast.LENGTH_SHORT).show();

                        }else {
                            idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(Add_New_Car.this, response.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void ResponseFailure(int responseCode, String errorMsg) {
                        idPBLoading.setVisibility(View.GONE);
                    }
                });
    }

    public void servicecallwithimage()
    {
        JSONObject params = new JSONObject();
        try {
            params.put("leadId", SPHelper.getSPData(Add_New_Car.this,SPHelper.lead_id,""));
            params.put("vehNo",selected_vehno.getText().toString());
            params.put("sellingPrice","");
            params.put("odometer",selected_kms.getText().toString());
            params.put("noOfOwner", selected_owners.getText().toString());
            params.put("color",selected_clr.getText().toString().trim());
            params.put("insType",selectedinsurancetype);
            params.put("insProvider",selectedbankname);
            params.put("policyNo",edit_policy_no.getText().toString());
            params.put("expDate",server_exp_date);
            params.put("customerId",SPHelper.getSPData(Add_New_Car.this,SPHelper.customer_id,""));
            params.put("modelId",SPHelper.carmodelid);
            params.put("fuel", fuel_type);
            params.put("manfYear",final_year);
            params.put("transType",trans_type);
            params.put("insCopy",ins_url);
            params.put("rcFront","");
            params.put("rcBack","");
            JSONArray paramsarr = new JSONArray();

            //   Toast.makeText(AddCarPage.this,"size of finl image is " + String.valueOf(finalImages.size()) ,Toast.LENGTH_SHORT).show();
            for (int i = 0; i <finalids.size(); i++)
            {
                JSONObject obbb = new JSONObject();

                obbb.put("image_type_id", finalids.get(i));
                obbb.put("image_url", finalImages.get(i));
                paramsarr.put(obbb);
            }
            params.put("imagesArr", paramsarr);
            System.out.println("post_data_with images"+params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new WebService().LoadwithUrl(Add_New_Car.this, ServiceURL.BASEURL, WebService.HttpMethod.post,
                null, WebService.RequestType.shared.REST(ServiceURL.addcarurl, WebService.RESTType.JSONBody, params), false, 0, new ResponseListener() {
            @Override
            public void ResponseSuccess(ResponseExtension response) {
                if (response.getResponseType().equalsIgnoreCase("200")) {
                    System.out.println("response"+response);
                    JSONObject tktobj = response.getResponseObject();
                    try {
                        idPBLoading.setVisibility(View.GONE);
                        isServiceRunning=true;
                        SPHelper.comingfrom="added";
                        SPHelper.isSuccess="add";
                        SPHelper.fragment_is="cars";
                        SPHelper.cf_msg="You have successfully added\n"+SPHelper.brand_name+"\t"+SPHelper.car_model_name+"\t"+
                                fuel_type+"\t"+"-"+selected_vehno.getText().toString();
                       // Toast.makeText(Add_New_Car.this, "You have successfully added your car", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(Add_New_Car.this, CustomerHomepage.class);
                        startActivity(intent);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    isServiceRunning=true;
                    idPBLoading.setVisibility(View.GONE);
                    Toast.makeText(Add_New_Car.this, response.getResponseMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void ResponseFailure(int responseCode, String errorMsg) {
                isServiceRunning=false;
                idPBLoading.setVisibility(View.GONE);
            }
        });
    }
    public static Add_New_Car getInstance() {
        return instance;
    }
}









