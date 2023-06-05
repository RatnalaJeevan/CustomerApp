package com.wisedrive.customerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Pair;
import android.view.View;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.iceteck.silicompressorr.SiliCompressor;
import com.wisedrive.customerapp.adapters.AdapterCarImgList;
import com.wisedrive.customerapp.adapters.AdapterCarVideos;
import com.wisedrive.customerapp.adapters.AdapterInspQList;
import com.wisedrive.customerapp.adapters.Adapter_Q_And_A;
import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.commonclasses.BitmapUtility;
import com.wisedrive.customerapp.commonclasses.Common;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.RequestPermissionHandler;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.PojoAddresses;
import com.wisedrive.customerapp.pojos.PojoCarImgs;
import com.wisedrive.customerapp.pojos.PojoCarVideoList;
import com.wisedrive.customerapp.pojos.PojoInsImgs;
import com.wisedrive.customerapp.pojos.PojoInsVide;
import com.wisedrive.customerapp.pojos.PojoInspAns;
import com.wisedrive.customerapp.pojos.PojoInspQ;
import com.wisedrive.customerapp.pojos.PojoSubmitSelfInsp;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelfInspection extends AppCompatActivity {

    EditText selected_kms,selected_clr;
    RelativeLayout rl_back,rl_add_car,rl_mycars;
    RecyclerView rv_q_a_list,rv_car_video_list,rv_car_img_list;
    ArrayList<PojoInspQ> pojoInspQ;
    AdapterInspQList adapterInspQList;
    private ApiInterface apiInterface;
    ArrayList<PojoCarImgs> pojoCarImgs;
    AdapterCarImgList adapterCarImgList;
    ArrayList<PojoCarVideoList> pojoCarVideoLists;
    AdapterCarVideos adapterCarVideos;
    TextView make_model,veh_no,fuel_type,mf_year;
    String filename,upload="";
    String camefrom="",on_click="";
    Uri imageUri;
    private RequestPermissionHandler mRequestPermissionHandler;
    public ArrayList<String> finalids =new ArrayList<>();
    public ArrayList<String> final_imgs =new ArrayList<>();
    public ArrayList<String> final_videos=new ArrayList<>();
    public ArrayList<String> finalids_videos =new ArrayList<>();
    public  boolean isServiceRunning = false;
    private BasicAWSCredentials credentials;
    private AmazonS3Client s3Client;
    private ProgressDialog progressDialog;

    ArrayList<PojoInspAns> pojoInspAns;
    ArrayList<PojoInsImgs> pojoInsImgs;
    ArrayList<PojoInsVide> pojoInsVides;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_inspection);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        FirebaseApp.initializeApp(this);
        AWSMobileClient.getInstance().initialize(SelfInspection.this).execute();
        credentials = new BasicAWSCredentials(SPHelper.getSPData(SelfInspection.this,SPHelper.awskey,""),
                SPHelper.getSPData(SelfInspection.this,SPHelper.awssecret,""));
        s3Client = new AmazonS3Client(credentials);
        progressDialog = new ProgressDialog(SelfInspection.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        mRequestPermissionHandler = new RequestPermissionHandler();
        rl_mycars=findViewById(R.id.rl_mycars);
        selected_clr=findViewById(R.id.selected_clr);
        selected_kms=findViewById(R.id.selected_kms);
        rv_q_a_list=findViewById(R.id.rv_q_a_list);
        rv_car_img_list=findViewById(R.id.rv_car_img_list);
        rv_car_video_list=findViewById(R.id.rv_car_video_list);
        rl_back=findViewById(R.id.rl_back);
        make_model=findViewById(R.id.make_model);
        veh_no=findViewById(R.id.veh_no);
        fuel_type=findViewById(R.id.fuel_type) ;
        mf_year=findViewById(R.id.mf_year);
        rl_add_car=findViewById(R.id.rl_add_car);

        make_model.setText(SPHelper.veh_make+"-"+SPHelper.veh_model);
        veh_no.setText(SPHelper.veh_no);
        fuel_type.setText(SPHelper.fuel_type);
        mf_year.setText(SPHelper.mnf_year);
        selected_kms.setText(SPHelper.kms_driven);
        selected_clr.setText(SPHelper.color);
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        get_qa_list();

        rl_add_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                get_total_answered();
                if(pojoInspAns.size()!=pojoInspQ.size()){
                    Common.CallToast(SelfInspection.this,"Please answer all the questions",1);
                }
                else if(selected_kms.getText().toString().equals("")){
                    Common.CallToast(SelfInspection.this,"Please enter kms driven",1);
                }else if(selected_clr.getText().toString().equals("")){
                    Common.CallToast(SelfInspection.this,"Please enter car color",1);
                }
                else if(final_imgs.size()!=pojoCarImgs.size()){
                    Common.CallToast(SelfInspection.this,"Please Upload all the images",1);
                }else if(final_videos.size()!=pojoCarVideoLists.size()){
                    Common.CallToast(SelfInspection.this,"Please Upload all the videos",1);
                } else{
                    //post method
                    post_self_insp();
                }
            }
        });

        rl_mycars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SelfInspection.this,HelpCentre.class);
                startActivity(intent);
               // finish();
            }
        });
    }

    public void get_qa_list()
    {
        if (!Connectivity.isNetworkConnected(SelfInspection.this)) {
            Toast.makeText(SelfInspection.this,
                    "Plaese Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        } else {
            //idPBLoading.setVisibility(View.VISIBLE);
            if(SPHelper.lead_veh_id==null){
                SPHelper.lead_veh_id="";
            }
            if(SPHelper.veh_id==null){
                SPHelper.veh_id="";
            }
            if(SPHelper.w_ins_id==null){
                SPHelper.w_ins_id="";
            }

            Call<AppResponse> call = apiInterface.get_part_details(SPHelper.veh_id,SPHelper.lead_veh_id,
                    "",SPHelper.w_ins_id);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                    AppResponse appResponse = response.body();
                    assert appResponse != null;
                    String response_code = appResponse.getResponseType();
                    if (response.body() != null) {
                        if (response_code.equals("200")) {
                            // idPBLoading.setVisibility(View.GONE);

                            pojoInspQ = new ArrayList<>();
                            pojoInspQ=appResponse.getResponseModel().getQuesAnsList();

                            adapterInspQList = new AdapterInspQList(pojoInspQ,SelfInspection.this);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SelfInspection.this, LinearLayoutManager.VERTICAL, false);
                            rv_q_a_list.setLayoutManager(linearLayoutManager);
                            rv_q_a_list.setAdapter(adapterInspQList);

                            pojoCarImgs = new ArrayList<>();
                            pojoCarImgs=appResponse.getResponseModel().getImageList();
                            adapterCarImgList = new AdapterCarImgList(pojoCarImgs,SelfInspection.this);
                            GridLayoutManager linearLayoutManager1 = new GridLayoutManager(SelfInspection.this,2);
                            rv_car_img_list.setLayoutManager(linearLayoutManager1);
                            rv_car_img_list.setAdapter(adapterCarImgList);

                            pojoCarVideoLists = new ArrayList<>();
                            pojoCarVideoLists=appResponse.getResponseModel().getVideoList();
                            adapterCarVideos = new AdapterCarVideos(pojoCarVideoLists,SelfInspection.this);
                            GridLayoutManager linearLayoutManager2= new GridLayoutManager(SelfInspection.this,2);
                            rv_car_video_list.setLayoutManager(linearLayoutManager2);
                            rv_car_video_list.setAdapter(adapterCarVideos);

                            SelfInspection.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapterInspQList.notifyDataSetChanged();
                                    adapterCarImgList.notifyDataSetChanged();
                                    adapterCarVideos.notifyDataSetChanged();

                                }
                            });
                        } else if (response_code.equals("300")) {
                            // idPBLoading.setVisibility(View.GONE);
                        }
                    } else {
                        // idPBLoading.setVisibility(View.GONE);
                        Toast.makeText(SelfInspection.this, "internal server error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                    Toast.makeText(SelfInspection.this,
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    // idPBLoading.setVisibility(View.GONE);
                }
            });
        }
    }


    public void CallCamera(int selectobj) {
        camefrom="img";
        on_click="c";
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
            SimpleDateFormat dateFormat = new SimpleDateFormat("-yyyy_MM_dd_HH_mm_ss_SSSSSS'.jpg'");
            String fineName = dateFormat.format(new Date());
            filename = BitmapUtility.PictUtil.getSavePath1().getPath() + "/" + fineName;
            imageUri = FileProvider.getUriForFile(SelfInspection.this,
                    com.wisedrive.customerapp.BuildConfig.APPLICATION_ID + ".provider", new File(filename));
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(takePictureIntent, selectobj);
    }

    public  void capture_video(int selectobj)
    {
        camefrom="video";
        on_click="";
        mRequestPermissionHandler.requestPermission(this, new String[]{
                Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, 123, new RequestPermissionHandler.RequestPermissionListener() {
            @Override
            public void onSuccess() {
                System.out.println("Succeed");
                openVideoCamera(selectobj);
            }
            @Override
            public void onFailed() {
                System.out.println("denied");
            }
        });

    }

    public  void openVideoCamera(int selectobj)
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            startActivityForResult(takePictureIntent, selectobj);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mRequestPermissionHandler.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && on_click.equals("c") && camefrom.equals("img"))
        {
            runOnUiThread(() -> {
                upload = "y";
                String OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs(filename, new Pair<Integer, Integer>(800, 800), "/");
                imageUri = FileProvider.getUriForFile(SelfInspection.this,
                        BuildConfig.APPLICATION_ID + ".provider", new File(OriginalFileName));
                pojoCarImgs.get(adapterCarImgList.adapter_position).setTaken_img(imageUri);
                pojoCarImgs.get(adapterCarImgList.adapter_position).setFilename(OriginalFileName);
                if(finalids.size()>0)
                {
                    for(int i=0;i<finalids.size();i++)
                    {
                        if (pojoCarImgs.get(requestCode).getImage_part_id().equalsIgnoreCase(finalids.get(i))) {
                            final_imgs.remove(i);
                            finalids.remove(pojoCarImgs.get(i).getImage_part_id());
                            break;
                        }else {

                        }
                    }
                }
                finalids.add(pojoCarImgs.get(requestCode).getImage_part_id());
                String OriginalFileName2 = BitmapUtility.PictUtil.saveImageasThumbs(OriginalFileName, new Pair<Integer, Integer>(1500, 1500), "/");
                upload_images(OriginalFileName2);
            });
        }
        else if(resultCode == RESULT_OK && data!=null && camefrom.equals("video"))
        {
            imageUri=data.getData();
            MediaPlayer mp = MediaPlayer.create(this, Uri.parse(String.valueOf(imageUri)));
            int duration = mp.getDuration();
            mp.release();
            /*convert millis to appropriate time*/
            int time_in_sec= Integer.parseInt(String.valueOf(TimeUnit.MILLISECONDS.toSeconds(duration)));
            int time_limit=Integer.parseInt(String.valueOf(pojoCarVideoLists.get(adapterCarVideos.adapter_position).getVideo_length()));

            System.out.println("timeinsec"+time_in_sec);
            if(time_in_sec<time_limit){
                Toast.makeText(SelfInspection.this,"Uploaded video should be minimum \t"+time_limit+"sec",Toast.LENGTH_SHORT).show();
            }
            else{
                pojoCarVideoLists.get(adapterCarVideos.adapter_position).setTaken_video(imageUri);
                pojoCarVideoLists.get(adapterCarVideos.adapter_position).setFilename(imageUri.getPath());
                if(finalids_videos.size()>0)
                {
                    for(int i=0;i<finalids_videos.size();i++)
                    {
                        if (pojoCarVideoLists.get(adapterCarVideos.adapter_position).getVideo_part_id().equalsIgnoreCase(finalids_videos.get(i))) {
                            final_videos.remove(i);
                            finalids_videos.remove(pojoCarVideoLists.get(i).getVideo_part_id());
                            break;
                        }else {
                        }
                    }
                }
                finalids_videos.add(pojoCarVideoLists.get(adapterCarVideos.adapter_position).getVideo_part_id());
                progressDialog.show();
                File file = new File(
                        Environment.getExternalStorageDirectory()
                                .getAbsolutePath());
                // Create compress video method
                new SelfInspection.CompressVideo().execute(
                        "false", imageUri.toString(), file.getPath());
                //uploadvideo(imageUri);

            }
        }
    }

    private class CompressVideo extends AsyncTask<String, String, String> {

        // Initialize dialog
        Dialog dialog;

        @Override protected void onPreExecute()
        {
            super.onPreExecute();
            // Display dialog
            dialog = ProgressDialog.show(
                    SelfInspection.this, "", "Uploading...");
        }

        @Override
        protected String doInBackground(String... strings)
        {
            // Initialize video path
            String videoPath = null;

            try {
                // Initialize uri
                Uri uri = Uri.parse(strings[1]);
                // Compress video
                videoPath
                        = SiliCompressor.with(SelfInspection.this)
                        .compressVideo(uri, strings[2],640,
                                360,
                                1200000);
                //640,360,1200000
                //1280,720,1500000
            }
            catch (URISyntaxException e) {
                e.printStackTrace();
            }
            // Return Video path
            return videoPath;
        }

        @Override protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            dialog.dismiss();
            File file = new File(s);
            Uri uri = Uri.fromFile(file);
            System.out.println(uri.toString());
            System.out.println("compressuri"+uri);
            float size = file.length() / 1024f;
            uploadvideo(uri);
            System.out.println(String.format("Size : %.2f KB", size));
        }
    }

    public void uploadvideo(Uri uri)
    {
        if (uri != null)
        {
            Toast.makeText(SelfInspection.this,"uploading",Toast.LENGTH_SHORT).show();
            //final StorageReference reference = FirebaseStorage.getInstance().getReference(foldername + System.currentTimeMillis() + "." + getfiletype(uri));
            final StorageReference reference = FirebaseStorage.getInstance().getReference("Cust_Veh_Video/" + System.currentTimeMillis() + "." + uri.getLastPathSegment());

            reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful()) ;
                    String downloadUri = uriTask.getResult().toString();
                    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Video");
                    HashMap<String, String> map = new HashMap<>();
                    map.put("videolink", downloadUri);
                    reference1.child("" + System.currentTimeMillis()).setValue(map);
                    final_videos.add(downloadUri);
                    adapterCarVideos.notifyDataSetChanged();
                    progressDialog.dismiss();
                    System.out.println("videourl"+downloadUri);
                    Toast.makeText(SelfInspection.this, "Video Uploaded!!", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(SelfInspection.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded " + (int) progress + "%");
                }
            });
        }
    }

    public void upload_images(final String filename)
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
            final String key = "Cust_Vehicle_Image/" + uri.getLastPathSegment();
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
                        final_imgs.add(finalurl);
                        System.out.println("image_list"+final_imgs);
                        adapterCarImgList.notifyDataSetChanged();
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

    public  void get_total_answered()
    {

        pojoInspAns=new ArrayList<PojoInspAns>();
        for (int i = 0; i <pojoInspQ.size(); i++)
        {
            for(int j=0;j<pojoInspQ.get(i).getAnswerList().size();j++){
                if(pojoInspQ.get(i).getAnswerList().get(j).getIsSelected().equalsIgnoreCase("y")){

                    PojoInspAns obj=new PojoInspAns();
                    obj.setPart_id(pojoInspQ.get(i).getQuestion_id());
                    obj.setAnswer_id(pojoInspQ.get(i).getAnswerList().get(j).getAnswer_id());
                    pojoInspAns.add(obj);
                }
            }
        }


        pojoInsImgs=new ArrayList<>();
        for (int i = 0; i <finalids.size(); i++) {
            PojoInsImgs obj1=new PojoInsImgs();
            obj1.setImage(final_imgs.get(i));
            obj1.setPart_id(finalids.get(i));
            pojoInsImgs.add(obj1);
        }

        pojoInsVides=new ArrayList<>();
        for (int i = 0; i <finalids_videos.size(); i++) {
            PojoInsVide obj2=new PojoInsVide();
            obj2.setVideo(final_videos.get(i));
            obj2.setPart_id(finalids_videos.get(i));
            pojoInsVides.add(obj2);
        }
    }

    private void post_self_insp()
    {
        if (!Connectivity.isNetworkConnected(SelfInspection.this)) {
            Toast.makeText(SelfInspection.this, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
            return;
        } else {
            progressDialog.show();
            String c_id= SPHelper.getSPData(SelfInspection.this, SPHelper.customer_id, "");
            String l_id= SPHelper.getSPData(SelfInspection.this, SPHelper.lead_id, "");
            PojoSubmitSelfInsp pojoSubmitSelfInsp=new PojoSubmitSelfInsp(c_id,SPHelper.veh_id,l_id,
                    SPHelper.lead_veh_id,SPHelper.w_ins_id,"",selected_clr.getText().toString(),
                    selected_kms.getText().toString(),pojoInsImgs,pojoInsVides,pojoInspAns);

            Call<AppResponse> call = apiInterface.self_insp(pojoSubmitSelfInsp);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                    System.out.print(response.body());
                    if (response.body() != null) {
                        AppResponse data = response.body();
                        if (data.getResponseType().equalsIgnoreCase("200")) {
                            progressDialog.dismiss();
                            SPHelper.fragment_is="cars";
                            Toast.makeText(SelfInspection.this, "You have done inspection Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(SelfInspection.this,CustomerHomepage.class);
                            startActivity(intent);
                            finish();

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(SelfInspection.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<AppResponse> call, Throwable th) {
                    Toast.makeText(SelfInspection.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }
    }

}