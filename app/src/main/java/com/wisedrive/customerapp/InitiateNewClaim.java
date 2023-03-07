package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Pair;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;
import com.wisedrive.customerapp.adapters.Adapter_Claim_Type_New_Cus_App;
import com.wisedrive.customerapp.adapters.Adapter_Initiate_Claims_Photos;
import com.wisedrive.customerapp.adapters.Adapter_Select_Your_Vehicle_No;
import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.commonclasses.BitmapUtility;
import com.wisedrive.customerapp.commonclasses.Common;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.RequestPermissionHandler;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.PojoAddNewClaim;
import com.wisedrive.customerapp.pojos.PojoAddedImages;
import com.wisedrive.customerapp.pojos.Pojo_Claim_Type_New_Cus_App;
import com.wisedrive.customerapp.pojos.Pojo_Select_Your_Vehicle_no;
import com.wisedrive.customerapp.pojos.Pojo_initiate_Claims_Photos;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InitiateNewClaim extends AppCompatActivity
{

    public  boolean isServiceRunning = false;
    private ProgressDialog progressDialog;
    String filename,upload="",server_date="";
    String foldername="EngineImage/";
    Uri imageUri;
    String it_is="";
    private BasicAWSCredentials credentials;
    private AmazonS3Client s3Client;
    private RequestPermissionHandler mRequestPermissionHandler;
    public ArrayList<String> finalids =new ArrayList<>();
    public ArrayList<String> imagelist =new ArrayList<>();
    public int selectedObject=0;
    private ApiInterface apiInterface;
    public RelativeLayout rl_symptoms_of_issue,rl_back_button,rl;
    RecyclerView rv_select_vehicle_no;
    Adapter_Select_Your_Vehicle_No adapter_select_your_vehicle_no;
    ArrayList<Pojo_Select_Your_Vehicle_no> pojo_select_your_vehicle_noArrayList;
    Context context;
    View view;
    TextView tv_calender;
    DatePickerDialog picker;

    RecyclerView rv_select_claim_type;
    Adapter_Claim_Type_New_Cus_App adapter_claim_type;
    ArrayList<Pojo_Claim_Type_New_Cus_App> pojo_claim_typeArrayList;
    EditText place_of_breakdown,more_details;

    RecyclerView rv_photos;
    Adapter_Initiate_Claims_Photos adapter_initiate_claims_photos;
    ArrayList<Pojo_initiate_Claims_Photos> pojo_initiate_claims_photosArrayList;
    ArrayList<PojoAddedImages> addedImages;
    AppCompatButton submit;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initiate_claim_new_customer_app);
        submit=findViewById(R.id.submit);
        rl=findViewById(R.id.rl);
        place_of_breakdown=findViewById(R.id.place_of_breakdown);
        more_details=findViewById(R.id.more_details);
        progressDialog = new ProgressDialog(InitiateNewClaim.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        rv_select_vehicle_no=findViewById(R.id.rv_select_vehicle_no);
        AWSMobileClient.getInstance().initialize(this).execute();
        credentials = new BasicAWSCredentials(SPHelper.getSPData(this,SPHelper.awskey,""),
                SPHelper.getSPData(this,SPHelper.awssecret,""));
        s3Client = new AmazonS3Client(credentials);
        mRequestPermissionHandler = new RequestPermissionHandler();

        rv_select_claim_type=findViewById(R.id.rv_select_claim_type);
        rv_photos=findViewById(R.id.rv_photos);
        rl_symptoms_of_issue=findViewById(R.id.rl_symptoms_of_issue);
        rl_symptoms_of_issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(SPHelper.claim_type_id.equals("")){
                    Toast.makeText(InitiateNewClaim.this, "Select claim type", Toast.LENGTH_SHORT).show();

                }else{
                    Intent intent=new Intent(InitiateNewClaim.this,Activity_Q_And_A.class);
                    startActivity(intent);
                }

            }
        });

        tv_calender=findViewById(R.id.tv_calender);
        tv_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                picker = new DatePickerDialog(InitiateNewClaim.this,
                        new DatePickerDialog.OnDateSetListener() {


                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                server_date=year+"-"+(monthOfYear + 1)+"-"+dayOfMonth;
                                tv_calender.setText(Common.getDateFromString(server_date));
                              //  Toast.makeText(InitiateNewClaim.this, "Date Added Successfully", Toast.LENGTH_LONG).show();
                            }
                        }, mYear, mMonth, mDay);
                picker.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
                picker.show();

            }
        });
        rl_back_button=findViewById(R.id.rl_back_button);
        rl_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(InitiateNewClaim.this,Comprehensive_Warranty.class);
                startActivity(intent);
                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                get_image_list();

                if(SPHelper.selcted_veh_id.equals("")){
                    Toast.makeText(InitiateNewClaim.this,
                            "Select a vehicle",
                            Toast.LENGTH_SHORT).show();
                }else if(SPHelper.claim_type_id.equals("")){
                    Toast.makeText(InitiateNewClaim.this,
                            "Select claim type",
                            Toast.LENGTH_SHORT).show();
                }
                else if(SPHelper.answer_details.isEmpty()){
                    Toast.makeText(InitiateNewClaim.this,
                            "Select symptoms ",
                            Toast.LENGTH_SHORT).show();
                }
                else if(SPHelper.answer_details.size()!=SPHelper.qa_list.size()){
                    Toast.makeText(InitiateNewClaim.this,
                            "Select all the symptoms",
                            Toast.LENGTH_SHORT).show();
                }
                else if(server_date.equals("")){
                    Toast.makeText(InitiateNewClaim.this,
                            "Enter date of breakdown",
                            Toast.LENGTH_SHORT).show();
                }
                else if(place_of_breakdown.getText().toString().equals("")){
                    Toast.makeText(InitiateNewClaim.this,
                            "Enter place of breakdown",
                            Toast.LENGTH_SHORT).show();
                }
                else if(addedImages.isEmpty()){
                    Toast.makeText(InitiateNewClaim.this,
                            "Add all vehicle images",
                            Toast.LENGTH_SHORT).show();
                }
                else if(addedImages.size()!=pojo_initiate_claims_photosArrayList.size()){
                    Toast.makeText(InitiateNewClaim.this,
                            "Add all vehicle images",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    initiate_claim();
                }

            }
        });
        //get_claim_types();
        getVehList();
        get_veh_image_list();

 }

    public  void get_image_list(){
        addedImages=new ArrayList<>();
        for(int j=0;j<finalids.size();j++)
        {
            PojoAddedImages obj = new PojoAddedImages();
            obj.setImage_type_id(finalids.get(j));
            obj.setImage(imagelist.get(j));
            addedImages.add(obj);
        }

        System.out.println("size1"+addedImages.size());
        System.out.println("size2"+finalids.size());
        System.out.println("size3"+pojo_select_your_vehicle_noArrayList.size());
    }

    public void getVehList() {
        if (!Connectivity.isNetworkConnected(InitiateNewClaim.this)) {
            Toast.makeText(InitiateNewClaim.this,
                    "Plaese Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        } else {
            //idPBLoading.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.getNewClaimVehList(
                    SPHelper.getSPData(InitiateNewClaim.this, SPHelper.customer_id, ""));
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                    AppResponse appResponse = response.body();
                    assert appResponse != null;
                    String response_code = appResponse.getResponseType();
                    if (response.body() != null) {
                        if (response_code.equals("200")) {
                            // idPBLoading.setVisibility(View.GONE);

                            pojo_select_your_vehicle_noArrayList = new ArrayList<>();
                            pojo_select_your_vehicle_noArrayList = appResponse.getResponseModel().getClaimVehicleList();
                            adapter_select_your_vehicle_no = new Adapter_Select_Your_Vehicle_No(InitiateNewClaim.this, pojo_select_your_vehicle_noArrayList);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(InitiateNewClaim.this, LinearLayoutManager.HORIZONTAL, false);
                            rv_select_vehicle_no.setLayoutManager(linearLayoutManager);
                            rv_select_vehicle_no.setAdapter(adapter_select_your_vehicle_no);

                            InitiateNewClaim.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter_select_your_vehicle_no.notifyDataSetChanged();
                                }
                            });
                        } else if (response_code.equals("300")) {
                            // idPBLoading.setVisibility(View.GONE);
                        }
                    } else {
                        // idPBLoading.setVisibility(View.GONE);
                        Toast.makeText(InitiateNewClaim.this, "internal server error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                    Toast.makeText(InitiateNewClaim.this,
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    // idPBLoading.setVisibility(View.GONE);
                }
            });
        }
    }

    public void get_claim_types() {
        if (!Connectivity.isNetworkConnected(InitiateNewClaim.this)) {
            Toast.makeText(InitiateNewClaim.this,
                    "Plaese Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        } else {
            //idPBLoading.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.get_new_claim_type(SPHelper.selcted_veh_id);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                    AppResponse appResponse = response.body();
                    assert appResponse != null;
                    String response_code = appResponse.getResponseType();
                    if (response.body() != null) {
                        if (response_code.equals("200")) {
                            // idPBLoading.setVisibility(View.GONE);
                            pojo_claim_typeArrayList = new ArrayList<>();
                            pojo_claim_typeArrayList=appResponse.getResponseModel().getClaimType();
                            adapter_claim_type = new Adapter_Claim_Type_New_Cus_App(InitiateNewClaim.this,pojo_claim_typeArrayList);
                            GridLayoutManager linearLayoutManager1 = new GridLayoutManager(InitiateNewClaim.this, 2);
                            rv_select_claim_type.setLayoutManager(linearLayoutManager1);
                            rv_select_claim_type.setAdapter(adapter_claim_type);


                            InitiateNewClaim.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter_claim_type.notifyDataSetChanged();
                                }
                            });
                        } else if (response_code.equals("300")) {
                            // idPBLoading.setVisibility(View.GONE);
                        }
                    } else {
                        // idPBLoading.setVisibility(View.GONE);
                        Toast.makeText(InitiateNewClaim.this, "internal server error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                    Toast.makeText(InitiateNewClaim.this,
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    // idPBLoading.setVisibility(View.GONE);
                }
            });
        }
    }

    public void get_veh_image_list() {
        addedImages=new ArrayList<>();
        finalids=new ArrayList<>();
        imagelist=new ArrayList<>();
        if (!Connectivity.isNetworkConnected(InitiateNewClaim.this)) {
            Toast.makeText(InitiateNewClaim.this,
                    "Plaese Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        } else {
            //idPBLoading.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.get_claim_images();
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                    AppResponse appResponse = response.body();
                    assert appResponse != null;
                    String response_code = appResponse.getResponseType();
                    if (response.body() != null) {
                        if (response_code.equals("200")) {
                            // idPBLoading.setVisibility(View.GONE);
                            pojo_initiate_claims_photosArrayList = new ArrayList<>();
                            pojo_initiate_claims_photosArrayList=appResponse.getResponseModel().getClaimImages();
                            adapter_initiate_claims_photos = new  Adapter_Initiate_Claims_Photos(InitiateNewClaim.this, pojo_initiate_claims_photosArrayList);
                            GridLayoutManager linearLayoutManager2 = new GridLayoutManager(InitiateNewClaim.this, 3);
                            rv_photos.setLayoutManager(linearLayoutManager2);
                            rv_photos.setAdapter(adapter_initiate_claims_photos);


                            InitiateNewClaim.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter_initiate_claims_photos.notifyDataSetChanged();
                                }
                            });
                        } else if (response_code.equals("300")) {
                            // idPBLoading.setVisibility(View.GONE);
                        }
                    } else {
                        // idPBLoading.setVisibility(View.GONE);
                        Toast.makeText(InitiateNewClaim.this, "internal server error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                    Toast.makeText(InitiateNewClaim.this,
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    // idPBLoading.setVisibility(View.GONE);
                }
            });
        }
    }

    public void open_dialog(){
        final Dialog dialog = new Dialog(InitiateNewClaim.this);
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

                mRequestPermissionHandler.requestPermission(InitiateNewClaim.this, new String[]
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
                mRequestPermissionHandler.requestPermission(InitiateNewClaim.this, new String[]{
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
                            CallCamera(selectedObject);
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


    public void CallCamera(int selectobj) {
        mRequestPermissionHandler.requestPermission(this, new String[]{
                Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, 123, new RequestPermissionHandler.RequestPermissionListener() {
            @Override
            public void onSuccess() {
                System.out.println("Succeed");
                openCamera(selectobj);
            }
            @Override
            public void onFailed() {
                System.out.println("denied");
            }
        });
    }
    void openCamera(int selectobj)
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("-yyyy_MM_dd_HH_mm_ss_SSSSSS'.jpg'");
            String fineName = dateFormat.format(new Date());
            filename = BitmapUtility.PictUtil.getSavePath1().getPath() + "/"  + fineName;
            imageUri = FileProvider.getUriForFile(InitiateNewClaim.this,
                    com.wisedrive.customerapp.BuildConfig.APPLICATION_ID + ".provider", new File(filename));
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(takePictureIntent, selectobj);
        }
    }
    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && it_is.equals("c") )
        {
            runOnUiThread(() -> {
                upload ="y";
                String OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs(filename, new Pair<Integer, Integer>(800, 800), "/");
                imageUri = FileProvider.getUriForFile(InitiateNewClaim.this,
                        BuildConfig.APPLICATION_ID + ".provider", new File(OriginalFileName));
                pojo_initiate_claims_photosArrayList.get(requestCode).setImage(imageUri);

                pojo_initiate_claims_photosArrayList.get(requestCode).setFilename(OriginalFileName);
                if(finalids.size()>0){
                    for(int i=0;i<finalids.size();i++)
                    {
                        if (pojo_initiate_claims_photosArrayList.get(requestCode).getImage_type_id().equalsIgnoreCase(finalids.get(i))) {
                            imagelist.remove(i);
                            finalids.remove(pojo_initiate_claims_photosArrayList.get(i).getImage_type_id());
                            break;
                        }else {

                        }
                    }
                }
                finalids.add(pojo_initiate_claims_photosArrayList.get(requestCode).getImage_type_id());
                String OriginalFileName2 = BitmapUtility.PictUtil.saveImageasThumbs(OriginalFileName, new Pair<Integer, Integer>(1500, 1500), "/");
                upload(OriginalFileName2);
            });
        }
        if ( resultCode == RESULT_OK && it_is.equals("g")) {
            Uri imageUri = data.getData();
            SimpleDateFormat dateFormat = new SimpleDateFormat("-dd_MMM_yyyy_HH_mm_ss_SSSSSS'.jpg'");
            String fineName = dateFormat.format(new Date());
            filename = BitmapUtility.PictUtil.getSavePath1().getPath() + "/" + "Wisedrive" + fineName;
            String OriginalFileName = null;
            try {
                OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs4(InitiateNewClaim.this, imageUri,
                        filename, new Pair<Integer, Integer>(800, 800), "/");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //imageUri = OriginalFileName;
            pojo_initiate_claims_photosArrayList.get(adapter_initiate_claims_photos.adapter_position).setImage(imageUri);
            pojo_initiate_claims_photosArrayList.get(adapter_initiate_claims_photos.adapter_position).setFilename(OriginalFileName);
            if(finalids.size()>0){
                for(int i=0;i<finalids.size();i++)
                {
                    if (pojo_initiate_claims_photosArrayList.get(adapter_initiate_claims_photos.adapter_position).getImage_type_id().equalsIgnoreCase(finalids.get(i))) {
                        imagelist.remove(i);
                        finalids.remove(pojo_initiate_claims_photosArrayList.get(i).getImage_type_id());
                        break;
                    }else {

                    }
                }
            }
            finalids.add(pojo_initiate_claims_photosArrayList.get(adapter_initiate_claims_photos.adapter_position).getImage_type_id());
            String OriginalFileName2 = BitmapUtility.PictUtil.saveImageasThumbs(OriginalFileName, new Pair<>(1500, 1500), "/");
            upload(OriginalFileName2);
        }

    }

    public void upload(final String filename)
    {
        progressDialog.show();
        isServiceRunning = true;
        File imageFile = new File(filename);
        Uri uri = Uri.fromFile(imageFile);
        try {
            final TransferUtility transferUtility =
                    TransferUtility.builder()
                            .context(getApplicationContext())
                            .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                            .s3Client(s3Client)
                            .build();
            final String key = foldername +SPHelper.getSPData(InitiateNewClaim.this, SPHelper.customer_id, "")+ uri.getLastPathSegment();
            final TransferObserver uploadObserver =
                    transferUtility.upload(key, new File(filename));
            uploadObserver.setTransferListener(new TransferListener() {
                @Override
                public void onStateChanged(int id, TransferState state) {
                    if (TransferState.COMPLETED == state) {
                        Toast.makeText(getApplicationContext(), "Image uploaded!", Toast.LENGTH_SHORT).show();
                        String finalurl = s3Client.getResourceUrl("ab-prod-container", key);
                        runOnUiThread(() -> {
                            progressDialog.dismiss();
                            progressDialog.cancel();
                        });
                        imagelist.add(finalurl);
                        System.out.println("image_list"+imagelist);
                        adapter_initiate_claims_photos.notifyDataSetChanged();
                        isServiceRunning = false;
                    } else if (TransferState.FAILED == state) {
                        isServiceRunning = false;
                    }
                }
                @Override
                public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                    //float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;

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


    public void initiate_claim() {
        {
            if (!Connectivity.isNetworkConnected(InitiateNewClaim
                    .this)) {
                Toast.makeText(InitiateNewClaim.this,
                        "Please Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            } else {
                //idPBLoading.setVisibility(View.VISIBLE);

                String c_id = SPHelper.getSPData(InitiateNewClaim.this, SPHelper.customer_id, "");
                PojoAddNewClaim pojoAddNewClaim=new PojoAddNewClaim(SPHelper.selcted_veh_id,c_id,SPHelper.claim_type_id,place_of_breakdown.getText().toString(),
                        server_date,more_details.getText().toString(),"","","",SPHelper.answer_details,addedImages);
                Call<AppResponse> call = apiInterface.add_new_claim(pojoAddNewClaim);
                call.enqueue(new Callback<AppResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                        AppResponse appResponse = response.body();
                        assert appResponse != null;
                        String response_code = appResponse.getResponseType();
                        if (response.body() != null) {
                            if (response_code.equals("200")) {
                                //idPBLoading.setVisibility(View.GONE);
                                Toast.makeText(InitiateNewClaim.this, "Claim initiated successfully", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(InitiateNewClaim.this, Comprehensive_Warranty.class);
                                startActivity(intent);
                                finish();
                            } else if (response_code.equals("300")) {
                                //idPBLoading.setVisibility(View.GONE);
                                Toast.makeText(InitiateNewClaim.this, appResponse.getResponseModel().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                           // idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(InitiateNewClaim.this, "internal server error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                        Toast.makeText(InitiateNewClaim.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        //idPBLoading.setVisibility(View.GONE);
                    }
                });
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(InitiateNewClaim.this,Comprehensive_Warranty.class);
        startActivity(intent);
        finish();
    }
}
