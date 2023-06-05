package com.wisedrive.customerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Pair;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.wisedrive.customerapp.adapters.AdapterDocsImg;
import com.wisedrive.customerapp.adapters.Adapter_Select_Your_Vehicle_No;
import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.commonclasses.BitmapUtility;
import com.wisedrive.customerapp.commonclasses.Common;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.RequestPermissionHandler;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.PojoDocs;
import com.wisedrive.customerapp.pojos.PojoRequestVehInsp;
import com.wisedrive.customerapp.pojos.PojoUpdateDocs;
import com.wisedrive.customerapp.pojos.Pojo_Select_Your_Vehicle_no;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyDocs extends AppCompatActivity
{
    File root;
    File pdf;
    Uri cam_uri;
    String it_is="",filename,onclick="",doc_url="";
    public int selectedObject=0;
    private BasicAWSCredentials credentials;
    private AmazonS3Client s3Client;
    private RequestPermissionHandler mRequestPermissionHandler;
    RelativeLayout rl_back_button,rl_purchase_pac,rl_purchase;
    AdapterDocsImg adapterDocsImg;
    ArrayList<PojoDocs> pojoDocs;
    RecyclerView rv_docs,rv_veh_list;
    private ApiInterface apiInterface;
    Adapter_Select_Your_Vehicle_No adapter_select_your_vehicle_no;
    ArrayList<Pojo_Select_Your_Vehicle_no> pojo_select_your_vehicle_noArrayList;
    ProgressBar idPBLoading;
    public static MyDocs instance;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_docs);
        instance=this;
        idPBLoading=findViewById(R.id.idPBLoading);
        SPHelper.this_is="doc";
        AWSMobileClient.getInstance().initialize(this).execute();
        credentials = new BasicAWSCredentials(SPHelper.getSPData(this,SPHelper.awskey,""),
                SPHelper.getSPData(this,SPHelper.awssecret,""));
        s3Client = new AmazonS3Client(credentials);
        mRequestPermissionHandler = new RequestPermissionHandler();
        FirebaseApp.initializeApp(this);
        rl_purchase=findViewById(R.id.rl_purchase);
        rl_purchase_pac=findViewById(R.id.rl_purchase_pac);
        rv_veh_list=findViewById(R.id.rv_veh_list);
        rv_docs=findViewById(R.id.rv_docs);
        rl_back_button=findViewById(R.id.rl_back_button);
        getWindow().setStatusBarColor(getColor(R.color.new_app_bg));
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        root = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath());
        pdf = new File(root, "PDF");
        rl_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        rl_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.comingfrom="";
                SPHelper.fragment_is="plans";
                Intent intent=new Intent(MyDocs.this,CustomerHomepage.class);
                startActivity(intent);
            }
        });
        if(SPHelper.getSPData(MyDocs.this, SPHelper.customer_id, "").equals("")){
            rv_veh_list.setVisibility(View.GONE);
            rl_purchase_pac.setVisibility(View.VISIBLE);
        }else {
            getVehList();
        }

    }

    public void getVehList() {
        if (!Connectivity.isNetworkConnected(MyDocs.this)) {
            Toast.makeText(MyDocs.this,
                    "Plaese Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        } else {
            //idPBLoading.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.get_doc_veh_list(
                    SPHelper.getSPData(MyDocs.this, SPHelper.customer_id, ""));
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
                            pojo_select_your_vehicle_noArrayList = appResponse.getResponseModel().getDocumentVehicleList();

                            if(pojo_select_your_vehicle_noArrayList.isEmpty()){
                                rv_veh_list.setVisibility(View.GONE);
                                rl_purchase_pac.setVisibility(View.VISIBLE);
                            }else {
                                rv_veh_list.setVisibility(View.VISIBLE);
                                rl_purchase_pac.setVisibility(View.GONE);
                            }
                            adapter_select_your_vehicle_no = new Adapter_Select_Your_Vehicle_No(MyDocs.this, pojo_select_your_vehicle_noArrayList);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyDocs.this, LinearLayoutManager.HORIZONTAL, false);
                            rv_veh_list.setLayoutManager(linearLayoutManager);
                            rv_veh_list.setAdapter(adapter_select_your_vehicle_no);

                            MyDocs.this.runOnUiThread(new Runnable() {
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
                        Toast.makeText(MyDocs.this, "internal server error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                    Toast.makeText(MyDocs.this,
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    // idPBLoading.setVisibility(View.GONE);
                }
            });
        }
    }

    public void opencamera(){
        mRequestPermissionHandler.requestPermission(MyDocs.this, new String[]{
                Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, 123, new RequestPermissionHandler.RequestPermissionListener() {
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

    }

    public void open_gallery(){

        mRequestPermissionHandler.requestPermission(MyDocs.this, new String[]
                {
                        android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, selectedObject, new RequestPermissionHandler.RequestPermissionListener()
        {
            @Override
            public void onSuccess() {
                it_is = "g";
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                String[] mimeTypes = {"image/*", "application/pdf"};
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent,selectedObject);
            }

            @Override
            public void onFailed() {
                System.out.println("denied");
            }
        });
    }

    public void CallCamera() {

        mRequestPermissionHandler.requestPermission(MyDocs.this, new String[]{
                Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, 123, new RequestPermissionHandler.RequestPermissionListener() {
            @Override
            public void onSuccess() {
                System.out.println("Succeed");
                open_Camera();
            }
            @Override
            public void onFailed() {
                System.out.println("denied");
            }
        });
    }
    public void open_Camera()
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            SimpleDateFormat dateFormat = new SimpleDateFormat("-yyyy_MM_dd_HH_mm_ss_SSSSSS'.jpg'");
            String fineName = dateFormat.format(new Date());
            filename = BitmapUtility.PictUtil.getSavePath().getPath() + "/" + fineName;
            cam_uri = FileProvider.getUriForFile(MyDocs.this,
                    BuildConfig.APPLICATION_ID + ".provider", new File(filename));
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, cam_uri);
            startActivityForResult(takePictureIntent, 123);
    }
    ProgressDialog dialog;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == selectedObject && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            Uri uri = data.getData();
            // Handle the selected PDF file here
            String type = getContentResolver().getType(uri);

                if (type != null && type.startsWith("image/"))
                {
                    // Handle the selected image file here


                    SimpleDateFormat dateFormat = new SimpleDateFormat("-dd_MMM_yyyy_HH_mm_ss_SSSSSS'.jpg'");
                    String fineName = dateFormat.format(new Date());
                    filename = BitmapUtility.PictUtil.getSavePath1().getPath() + "/" + "Wisedrive" + fineName;
                    String OriginalFileName = null;
                    try {
                        OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs4(MyDocs.this, uri, filename, new Pair<Integer, Integer>(800, 800), "/");
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                      //  iv_ins_copy.setImageURI(uri);

                    if(SPHelper.doc_edited.equals("y"))
                    {
                        SPHelper.sel_uri=uri;
                        ShowDocImg pic=new ShowDocImg();
                        pic.show(((FragmentActivity)MyDocs.this).getSupportFragmentManager(), pic.getTag());
                    }
                    else{
                        upload_to_s3(uri);
                     }

                }
                else if (type != null && type.equals("application/pdf"))
                {
                    // Handle the selected PDF file here

                        upload_pdf(uri);


                } else {
                    // Unsupported file type
                   // Toast.makeText(MyDocs.this,"camera",Toast.LENGTH_SHORT).show();
                }

        }
        else if(resultCode == RESULT_OK&&it_is.equals("c"))
        {

            MyDocs.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs(filename, new Pair<Integer, Integer>(2040, 1080), "/");
                    cam_uri = FileProvider.getUriForFile(MyDocs.this,
                            BuildConfig.APPLICATION_ID + ".provider", new File(OriginalFileName));
                    filename = OriginalFileName;
                    //setimageuri

                    if (!Connectivity.isNetworkConnected(MyDocs.this)) {

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(MyDocs.this);
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

                    if(SPHelper.doc_edited.equals("y")){
                        SPHelper.sel_uri=cam_uri;
                        ShowDocImg pic=new ShowDocImg();
                        pic.show(((FragmentActivity)MyDocs.this).getSupportFragmentManager(), pic.getTag());
                    }
                    else{
                        upload_to_s3(cam_uri);
                    }

                }
            });
        }
    }

    public void upload_pdf(Uri uri)
    {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Uploading");
        dialog.show();
        final String timestamp = "" + System.currentTimeMillis();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        final String messagePushID = timestamp;
        final StorageReference filepath = storageReference.child(SPHelper.doc_name+"/" + "." + "pdf");
        filepath.putFile(uri).continueWithTask(new Continuation()
        {
            @Override
            public Object then(@NonNull Task task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return filepath.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    dialog.dismiss();
                    Uri uri = task.getResult();
                    // String myurl;
                    doc_url = uri.toString();
                    System.out.print("myurl"+doc_url);
                    // tv_pdf.setVisibility(View.VISIBLE);
                    // tv_pdf.setText(SPHelper.vehid + "obd report " + "." + "pdf");
                    // iv_obd.setVisibility(View.GONE);
                    // Common.CallToast(MyDocs.this, "Uploaded Successfully",3);

                    if(SPHelper.doc_edited.equals("y"))
                    {
                        SPHelper.sel_uri= Uri.parse(doc_url);
                        System.out.println("pdf"+SPHelper.sel_uri);
                        ShowDocImg pic=new ShowDocImg();
                        pic.show(((FragmentActivity)MyDocs.this).getSupportFragmentManager(), pic.getTag());
                    }
                    else{
                        update_doc();
                    }


                } else {
                    dialog.dismiss();
                    Toast.makeText(MyDocs.this, "UploadedFailed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void validate(){}
    public  void upload_to_s3(Uri imageUri){
        try {
             idPBLoading.setVisibility(View.VISIBLE);
            final TransferUtility transferUtility =
                    TransferUtility.builder()
                            .context(MyDocs.this)
                            .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                            .s3Client(s3Client)
                            .build();
            final String key =  SPHelper.doc_name+"/" + imageUri.getLastPathSegment();
            final TransferObserver uploadObserver =
                    transferUtility.upload(key, new File(filename));
            uploadObserver.setTransferListener(new TransferListener() {
                @Override
                public void onStateChanged(int id, TransferState state) {
                    if (TransferState.COMPLETED == state) {
                       // Toast.makeText(MyDocs.this,  SPHelper.doc_name+"\tuploaded!", Toast.LENGTH_SHORT).show();
                        String finalurl = s3Client.getResourceUrl("ab-prod-container", key);
                        System.out.print("doc_url"+finalurl);
                        MyDocs.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                idPBLoading.setVisibility(View.GONE);
                                // progressDialog.cancel();
                            }
                        });

                        doc_url=finalurl;
                        update_doc();
                    } else if (TransferState.FAILED == state) {

                        MyDocs.this.runOnUiThread(new Runnable() {
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
                    MyDocs.this.runOnUiThread(new Runnable() {
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



    public void get_docs_list() {
        rv_docs.setVisibility(View.VISIBLE);
        if (!Connectivity.isNetworkConnected(MyDocs.this)) {
            Toast.makeText(MyDocs.this,
                    "Plaese Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        } else {
            //idPBLoading.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.get_doc_list(SPHelper.selcted_veh_id,
                    SPHelper.getSPData(MyDocs.this, SPHelper.customer_id, ""),"",
                    SPHelper.getSPData(MyDocs.this,SPHelper.lead_id,""));
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                    AppResponse appResponse = response.body();
                    assert appResponse != null;
                    String response_code = appResponse.getResponseType();
                    if (response.body() != null) {
                        if (response_code.equals("200")) {
                            // idPBLoading.setVisibility(View.GONE);

                            pojoDocs=new ArrayList<>();
                            pojoDocs=appResponse.getResponseModel().getDocumentList();
                            adapterDocsImg=new AdapterDocsImg(pojoDocs,MyDocs.this);
                            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(MyDocs.this,LinearLayoutManager.VERTICAL,false);
                            rv_docs.setLayoutManager(linearLayoutManager);
                            rv_docs.setAdapter(adapterDocsImg);

                            MyDocs.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapterDocsImg.notifyDataSetChanged();
                                }
                            });
                        } else if (response_code.equals("300")) {
                            // idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(MyDocs.this, appResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // idPBLoading.setVisibility(View.GONE);
                        Toast.makeText(MyDocs.this, "internal server error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                    Toast.makeText(MyDocs.this,
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    // idPBLoading.setVisibility(View.GONE);
                }
            });
        }
    }

    public void update_doc() {
        {
            if (!Connectivity.isNetworkConnected(MyDocs.this)) {
                Toast.makeText(MyDocs.this,
                        "Please Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            } else {
                idPBLoading.setVisibility(View.VISIBLE);
                String lead_id = SPHelper.getSPData(MyDocs.this, SPHelper.lead_id, "");
                String c_id = SPHelper.getSPData(MyDocs.this, SPHelper.customer_id, "");
                PojoUpdateDocs pojoUpdateDocs=new PojoUpdateDocs(SPHelper.selcted_veh_id,c_id,"",lead_id,SPHelper.doc_id,doc_url);
                Call<AppResponse> call = apiInterface.update_doc(pojoUpdateDocs);
                call.enqueue(new Callback<AppResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                        AppResponse appResponse = response.body();
                        assert appResponse != null;
                        String response_code = appResponse.getResponseType();
                        if (response.body() != null) {
                            if (response_code.equals("200"))
                            {
                                SPHelper.sel_uri= Uri.parse("");
                                SPHelper.doc_edited="";
                                idPBLoading.setVisibility(View.GONE);
                                Toast.makeText(MyDocs.this, appResponse.getResponseModel().getMessage(), Toast.LENGTH_SHORT).show();
                                get_docs_list();
                            } else if (response_code.equals("300")) {
                                idPBLoading.setVisibility(View.GONE);
                                Toast.makeText(MyDocs.this, appResponse.getResponseModel().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(MyDocs.this, "internal server error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                        Toast.makeText(MyDocs.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        idPBLoading.setVisibility(View.GONE);
                    }
                });
            }
        }
    }

    public static MyDocs getInstance() {
        return instance;
    }
}