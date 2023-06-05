package com.wisedrive.customerapp;

import androidx.annotation.NonNull;
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
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;
import com.wisedrive.customerapp.adapters.Adapter_Select_Date;
import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.commonclasses.BitmapUtility;
import com.wisedrive.customerapp.commonclasses.Common;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.RequestPermissionHandler;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.PojoRequestVehInsp;
import com.wisedrive.customerapp.pojos.Pojo_Select_Date;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Request_Inspection extends AppCompatActivity
{
    RecyclerView rv_select_date;
    AppCompatButton submit;
    TimePickerDialog t_picker;
    DatePickerDialog date_picker;
    String mobile_no_pattern="^[6-9][0-9]{9}$";
    String it_is="",filename,onclick="",rc_front_url="",rc_back_url="",
            aadhar_frnt_url="",aadhar_back_url="",ins_url="",
            server_date="",city_id,state_id,newtime="";
    Uri cam_uri;
    public int selectedObject=0;
    RelativeLayout rl_back,rl_select_time,rl_state,rl_city,rl_ac_fnt,rl_ac_bc,rl_rc_fnt,rl_rc_bc,rl_ins;
    ImageView iv_rc_front, iv_rc_back, iv_aadhar_front, iv_aadhar_back, iv_ins_copy,
            sel_aadhar_front,sel_aadhar_back,sel_rc_fnt,sel_rc_back,sel_ins;

    TextView selected_city,selected_state,select_date,select_time,label1,label2,label3,label4,label5;
    private ApiInterface apiInterface;
    EditText cust_pincode,entered_name,entered_no,cust_location,cust_adress,cust_adress2,entered_veh_no,cust_location1;
    private BasicAWSCredentials credentials;
    private AmazonS3Client s3Client;
    private RequestPermissionHandler mRequestPermissionHandler;
    ProgressBar idPBLoading;
    Adapter_Select_Date adapter_select_date;
    ArrayList<Pojo_Select_Date> pojo_select_dateArrayList;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_inspection);
        SPHelper.current_page="req";
        Request_Inspection.this.getWindow().setStatusBarColor(Request_Inspection.this.getColor(R.color.white));
        label1=findViewById(R.id.label1);
        label2=findViewById(R.id.label2);
        label3=findViewById(R.id.label3);
        label4=findViewById(R.id.label4);
        label5=findViewById(R.id.label5);
        sel_aadhar_front=findViewById(R.id.sel_aadhar_front);
        sel_aadhar_back=findViewById(R.id.sel_aadhar_back);
        sel_rc_fnt=findViewById(R.id.sel_rc_fnt);
        sel_rc_back=findViewById(R.id.sel_rc_back);
        sel_ins=findViewById(R.id.sel_ins);
        cust_location1=findViewById(R.id.cust_location1);
        rl_ac_fnt=findViewById(R.id.rl_ac_fnt);
        rl_ac_bc=findViewById(R.id.rl_ac_bc);
        rl_rc_fnt=findViewById(R.id.rl_rc_fnt);
        rl_rc_bc=findViewById(R.id.rl_rc_bc);
        rl_ins=findViewById(R.id.rl_ins);
        rl_city=findViewById(R.id.rl_city);
        rl_state=findViewById(R.id.rl_state);
        rv_select_date=findViewById(R.id.rv_select_date);
        idPBLoading=findViewById(R.id.idPBLoading);
        select_time=findViewById(R.id.select_time);
        rl_select_time=findViewById(R.id.rl_select_time);
        submit=findViewById(R.id.submit);
        select_date=findViewById(R.id.select_date);
        entered_veh_no=findViewById(R.id.entered_veh_no);
        entered_name=findViewById(R.id.entered_name);
        entered_no=findViewById(R.id.entered_no);
        cust_location=findViewById(R.id.cust_location);
        cust_adress=findViewById(R.id.cust_adress);
        cust_adress2=findViewById(R.id.cust_adress2);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        AWSMobileClient.getInstance().initialize(this).execute();
        credentials = new BasicAWSCredentials(SPHelper.getSPData(this,SPHelper.awskey,""),
                SPHelper.getSPData(this,SPHelper.awssecret,""));
        s3Client = new AmazonS3Client(credentials);
        mRequestPermissionHandler = new RequestPermissionHandler();
        selected_city=findViewById(R.id.selected_city);
        selected_state=findViewById(R.id.selected_state);
        rl_back = findViewById(R.id.rl_back);
        iv_rc_front = findViewById(R.id.iv_rc_front);
        iv_rc_back = findViewById(R.id.iv_rc_back);
        iv_aadhar_front = findViewById(R.id.iv_aadhar_front);
        iv_aadhar_back = findViewById(R.id.iv_aadhar_back);
        iv_ins_copy = findViewById(R.id.iv_ins_copy);
        cust_pincode=findViewById(R.id.cust_pincode);
        entered_veh_no.setText(SPHelper.veh_no.toUpperCase());
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cust_pincode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                rl_state.setVisibility(View.GONE);
                rl_city.setVisibility(View.GONE);
            }
            @Override
            public void afterTextChanged(Editable editable)
            {
                if(cust_pincode.getText().toString().length()==6){
                    hideKeybaord();
                    get_pincode_details();
                    rl_state.setVisibility(View.VISIBLE);
                    rl_city.setVisibility(View.VISIBLE);

                }else if(cust_pincode.getText().toString().length()<6){
                    selected_city.setText("");
                    selected_state.setText("");
                    rl_state.setVisibility(View.GONE);
                    rl_city.setVisibility(View.GONE);
                }
            }
        });

        rl_rc_fnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclick="RcFront";
                open_dialog();
            }
        });
        rl_rc_bc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclick="RcBack";
                open_dialog();
            }
        });
        rl_ac_fnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclick="AdharFront";
                open_dialog();
            }
        });
        rl_ac_bc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclick="AdharBack";
                open_dialog();
            }
        });
        rl_ins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclick="Insurance";
                open_dialog();
            }
        });

        select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                date_picker = new DatePickerDialog(Request_Inspection.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                            {
                                select_date.setText(Common.getdate(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year));
                                server_date=year+"-"+(monthOfYear + 1)+"-"+dayOfMonth;
                            }
                        }, year, month, day);
                date_picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                date_picker.show();
            }
        });
        rl_select_time.setOnClickListener(view -> {

            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            int seconds=mcurrentTime.get(Calendar.SECOND);
            t_picker = new TimePickerDialog(Request_Inspection.this, new TimePickerDialog.OnTimeSetListener()
            {
                @Override
                public void onTimeSet(TimePicker timePicker, int hourOfDay, int selectedMinute)
                {
                    String time = hour+":"+minute;
                    newtime=hour+":"+minute+":"+seconds;
                    if(hourOfDay>=0 && hourOfDay<12){
                        time =hourOfDay+":"+selectedMinute+" "+"AM";
                        newtime=hourOfDay+":"+selectedMinute+":"+seconds;
                    }
                    else {
                        if(hourOfDay == 12){
                            time =hourOfDay+":"+selectedMinute+" "+"PM";
                            newtime=hourOfDay+":"+selectedMinute+":"+seconds;
                        } else{
                            hourOfDay = hourOfDay -12;
                            time =hourOfDay +":"+selectedMinute+" "+"PM";
                            newtime=(hourOfDay+12)+":"+selectedMinute+":"+seconds;
                        }
                    }
                    select_time.setText(time);
                }
            },hour,minute,false);
            t_picker.setTitle("Select Time");
            t_picker.show();
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //dialog.show();
                if(SPHelper.server_date.equals("")){
                    Toast.makeText(Request_Inspection.this,
                            " Select Inspection Date",
                            Toast.LENGTH_SHORT).show();
                }
                else if(adapter_select_date.newtime.equals("")){
                    Toast.makeText(Request_Inspection.this,
                            " Select Inspection Time",
                            Toast.LENGTH_SHORT).show();
                }

                else   if(cust_adress2.getText().toString().equals(""))
                {
                    Toast.makeText(Request_Inspection.this,
                            " enter address",
                            Toast.LENGTH_SHORT).show();
                }
                else if(cust_location1.getText().toString().equals("")){
                    Toast.makeText(Request_Inspection.this,
                            "Enter Location",
                            Toast.LENGTH_SHORT).show();
                }

                else if(cust_pincode.getText().toString().length()<6
                        ||selected_city.getText().toString().equals("")
                        ||selected_state.getText().toString().equals("")){
                    Toast.makeText(Request_Inspection.this,
                            " enter valid pincode",
                            Toast.LENGTH_SHORT).show();
                }
                else if(rc_front_url.equals("") ){
                    Toast.makeText(Request_Inspection.this,
                            " Upload RC front photo",
                            Toast.LENGTH_SHORT).show();
                }
                else if( rc_back_url.equals("")){
                    Toast.makeText(Request_Inspection.this,
                            " Upload RC back photo",
                            Toast.LENGTH_SHORT).show();
                }
                else if(aadhar_frnt_url.equals("") ){
                    Toast.makeText(Request_Inspection.this,
                            " Upload aadhar front photo",
                            Toast.LENGTH_SHORT).show();
                }
                else if(aadhar_back_url.equals("")){
                    Toast.makeText(Request_Inspection.this,
                            " Upload Aadhar back photo",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    req_insp();
                }
            }
        });

        getDateLists();
    }

    public void getDateLists() {
        if (!Connectivity.isNetworkConnected(Request_Inspection.this)) {
            Toast.makeText(Request_Inspection.this,
                    "Plaese Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        } else {
            idPBLoading.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.getDateList("1");
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
                                    new GridLayoutManager(Request_Inspection.this,3,
                                            GridLayoutManager.VERTICAL, false);
                            rv_select_date.setLayoutManager(manager);
                            adapter_select_date = new Adapter_Select_Date(Request_Inspection.this,pojo_select_dateArrayList);
                            rv_select_date.setAdapter(adapter_select_date);

                            Request_Inspection.this.runOnUiThread(new Runnable() {
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
                        Toast.makeText(Request_Inspection.this, "internal server error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                    Toast.makeText(Request_Inspection.this,
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    idPBLoading.setVisibility(View.GONE);
                }
            });
        }
    }
    public void get_pincode_details() {
        if(!Connectivity.isNetworkConnected(Request_Inspection.this))
        {
            Toast.makeText(Request_Inspection.this,
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
                                Common.CallToast(Request_Inspection.this,"Enter Valid pincode",1);
                                selected_city.setText("");
                                selected_state.setText("");
                            }else{
                                selected_city.setText(cityname);
                                selected_state.setText(statename);
                            }

                        }
                        else
                        {
                            Toast.makeText(Request_Inspection.this,"Error:"+response.code(),Toast.LENGTH_SHORT).show();
                        }
                    }
                    idPBLoading.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, Throwable t) {
                     idPBLoading.setVisibility(View.GONE);
                    Toast.makeText(Request_Inspection.this,
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void open_dialog(){
        final Dialog dialog = new Dialog(Request_Inspection.this);
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
            public void onClick(View view)
            {
                //gallery

                mRequestPermissionHandler.requestPermission(Request_Inspection.this, new String[]
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
                mRequestPermissionHandler.requestPermission(Request_Inspection.this, new String[]{
                        Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, 123, new RequestPermissionHandler.RequestPermissionListener()
                {
                    @Override
                    public void onSuccess()
                    {
                        System.out.println("Succeed");
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                            // pickCamera();
                            it_is = "c";
                            CallCamera();

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

        mRequestPermissionHandler.requestPermission(Request_Inspection.this, new String[]{
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
            SimpleDateFormat dateFormat = new SimpleDateFormat("-yyyy_MM_dd_HH_mm_ss_SSSSSS'.jpg'");
            String fineName = dateFormat.format(new Date());
            filename = BitmapUtility.PictUtil.getSavePath().getPath() + "/" + fineName;
            cam_uri = FileProvider.getUriForFile(Request_Inspection.this,
                    BuildConfig.APPLICATION_ID + ".provider", new File(filename));
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, cam_uri);
            startActivityForResult(takePictureIntent, selectedObject);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mRequestPermissionHandler.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK &&it_is.equals("g"))
        {
            idPBLoading.setVisibility(View.VISIBLE);
            Uri uri=data.getData();
            SimpleDateFormat dateFormat = new SimpleDateFormat("-dd_MMM_yyyy_HH_mm_ss_SSSSSS'.jpg'");
            String fineName = dateFormat.format(new Date());
            filename = BitmapUtility.PictUtil.getSavePath1().getPath() + "/" + "Wisedrive" + fineName;
            String OriginalFileName = null;
            try {
                OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs4(Request_Inspection.this, uri, filename, new Pair<Integer, Integer>(800, 800), "/");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if(onclick.equals("RcFront"))
            {
                sel_rc_fnt.setImageURI(uri);
                iv_rc_front.setVisibility(View.GONE);
                label1.setVisibility(View.GONE);
            }else if(onclick.equals("RcBack")){
                sel_rc_back.setImageURI(uri);
                iv_rc_back.setVisibility(View.GONE);
                label2.setVisibility(View.GONE);
            }else if(onclick.equals("AdharFront")){
                sel_aadhar_front.setImageURI(uri);
                iv_aadhar_front.setVisibility(View.GONE);
                label3.setVisibility(View.GONE);
            }else if(onclick.equals("AdharBack")){
                sel_aadhar_back.setImageURI(uri);
                iv_aadhar_back.setVisibility(View.GONE);
                label4.setVisibility(View.GONE);
            }else {
                sel_ins.setImageURI(uri);
                iv_ins_copy.setVisibility(View.GONE);
                label5.setVisibility(View.GONE);
            }
                upload_to_s3(uri);

        }
        else if(resultCode==RESULT_OK&&it_is.equals("c"))
        {
            idPBLoading.setVisibility(View.VISIBLE);
            Request_Inspection.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs(filename, new Pair<Integer, Integer>(2040, 1080), "/");
                    cam_uri = FileProvider.getUriForFile(Request_Inspection.this,
                            BuildConfig.APPLICATION_ID + ".provider", new File(OriginalFileName));
                    filename = OriginalFileName;
                    if(onclick.equals("RcFront"))
                    {
                        sel_rc_fnt.setImageURI(cam_uri);
                        iv_rc_front.setVisibility(View.GONE);
                        label1.setVisibility(View.GONE);
                    }else if(onclick.equals("RcBack")){
                        sel_rc_back.setImageURI(cam_uri);
                        iv_rc_back.setVisibility(View.GONE);
                        label2.setVisibility(View.GONE);
                    }else if(onclick.equals("AdharFront")){
                        sel_aadhar_front.setImageURI(cam_uri);
                        iv_aadhar_front.setVisibility(View.GONE);
                        label3.setVisibility(View.GONE);
                    }else if(onclick.equals("AdharBack")){
                        sel_aadhar_back.setImageURI(cam_uri);
                        iv_aadhar_back.setVisibility(View.GONE);
                        label4.setVisibility(View.GONE);
                    }else {
                        sel_ins.setImageURI(cam_uri);
                        iv_ins_copy.setVisibility(View.GONE);
                        label5.setVisibility(View.GONE);
                    }

                    if (!Connectivity.isNetworkConnected(Request_Inspection.this)) {

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(Request_Inspection.this);
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

    private void validate(){}
    public  void upload_to_s3(Uri imageUri){
        try {
           // idPBLoading.setVisibility(View.VISIBLE);
            final TransferUtility transferUtility =
                    TransferUtility.builder()
                            .context(Request_Inspection.this)
                            .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                            .s3Client(s3Client)
                            .build();
            final String key = onclick+"/" + imageUri.getLastPathSegment();
            final TransferObserver uploadObserver =
                    transferUtility.upload(key, new File(filename));
            uploadObserver.setTransferListener(new TransferListener() {
                @Override
                public void onStateChanged(int id, TransferState state) {
                    if (TransferState.COMPLETED == state) {
                        Toast.makeText(Request_Inspection.this, onclick+"\tuploaded!", Toast.LENGTH_SHORT).show();
                        String finalurl = s3Client.getResourceUrl("ab-prod-container", key);
                        System.out.print(finalurl);
                        Request_Inspection.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                idPBLoading.setVisibility(View.GONE);
                               // progressDialog.cancel();
                            }
                        });

                        if(onclick.equals("RcFront")){
                            rc_front_url=finalurl;

                        }else if(onclick.equals("RcBack")){
                            rc_back_url=finalurl;
                        }else if(onclick.equals("AdharFront")){
                            aadhar_frnt_url=finalurl;
                        }else if(onclick.equals("AdharBack")){
                            aadhar_back_url=finalurl;
                        }else {
                            ins_url=finalurl;
                        }

                    } else if (TransferState.FAILED == state) {

                        Request_Inspection.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                idPBLoading.setVisibility(View.GONE);
                               // progressDialog.cancel();
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
                    Request_Inspection.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            idPBLoading.setVisibility(View.GONE);
                           // progressDialog.cancel();
                        }
                    });
                }

            });
        } catch (Exception je) {

            je.printStackTrace();
        }
    }

    public void req_insp() {
        {
            if (!Connectivity.isNetworkConnected(Request_Inspection
                    .this)) {
                Toast.makeText(Request_Inspection.this,
                        "Please Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            } else {
                  idPBLoading.setVisibility(View.VISIBLE);
                String lead_id = SPHelper.getSPData(Request_Inspection.this, SPHelper.lead_id, "");
                String c_id = SPHelper.getSPData(Request_Inspection.this, SPHelper.customer_id, "");
                String c_no=SPHelper.getSPData(Request_Inspection.this, SPHelper.customer_phoneno, "");
                PojoRequestVehInsp pojoRequestVehInsp=new PojoRequestVehInsp(lead_id,c_id,
                        entered_veh_no.getText().toString(),c_no,cust_location1.getText().toString()
                        ,cust_adress2.getText().toString(),SPHelper.server_date,rc_back_url,rc_front_url,aadhar_frnt_url,aadhar_back_url,ins_url,adapter_select_date.newtime,
                        cust_pincode.getText().toString(),city_id,state_id);
                Call<AppResponse> call = apiInterface.req_insp(pojoRequestVehInsp);
                call.enqueue(new Callback<AppResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                        AppResponse appResponse = response.body();
                        assert appResponse != null;
                        String response_code = appResponse.getResponseType();
                        if (response.body() != null) {
                            if (response_code.equals("200")) {
                                idPBLoading.setVisibility(View.GONE);
                                SPHelper.fragment_is="cars";
                                Toast.makeText(Request_Inspection.this, "Inspection Requested successfully", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(Request_Inspection.this, CustomerHomepage.class);
                                startActivity(intent);
                            } else if (response_code.equals("300")) {
                                 idPBLoading.setVisibility(View.GONE);
                                 Toast.makeText(Request_Inspection.this, appResponse.getResponseModel().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                              idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(Request_Inspection.this, "internal server error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                        Toast.makeText(Request_Inspection.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                         idPBLoading.setVisibility(View.GONE);
                    }
                });
            }
        }
    }

    private void hideKeybaord() {

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } }

    @Override
    public void onBackPressed() {
        finish();
    }
}






















