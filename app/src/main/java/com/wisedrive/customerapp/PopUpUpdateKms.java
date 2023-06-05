package com.wisedrive.customerapp;

import static android.app.Activity.RESULT_OK;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.wisedrive.customerapp.adapters.AdapterKmsData;
import com.wisedrive.customerapp.adapters.Adapter_Addons_list;
import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.commonclasses.BitmapUtility;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.RequestPermissionHandler;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.PojoKmsData;
import com.wisedrive.customerapp.pojos.PojoRequestVehInsp;
import com.wisedrive.customerapp.pojos.PojoUpdateOdometer;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopUpUpdateKms extends BottomSheetDialogFragment {
    RelativeLayout rl_update_kms,rl1,rl2,rl_update_now;
    TextView label_update,label_date;
    RecyclerView rv_kms_reading;
    AdapterKmsData adapterKmsData;
    ArrayList<PojoKmsData> pojoKmsData;
    Activity activity;
    private ApiInterface apiInterface;
    ImageView taken_odo;
    EditText selected_kms;
    Uri cam_uri;
    String it_is="",filename,onclick="",odo_url="";
    public int selectedObject=0;
    private BasicAWSCredentials credentials;
    private AmazonS3Client s3Client;
    private RequestPermissionHandler mRequestPermissionHandler;
    ProgressBar idPBLoading;
    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_pop_up_update_kms, container, false);
        activity=getActivity();
        idPBLoading=v.findViewById(R.id.idPBLoading);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        selected_kms=v.findViewById(R.id.selected_kms);
        taken_odo=v.findViewById(R.id.taken_odo);
        label_date=v.findViewById(R.id.label_date);
        rl_update_now=v.findViewById(R.id.rl_update_now);
        rl_update_kms=v.findViewById(R.id.rl_update_kms);
        rl1=v.findViewById(R.id.rl1);
        rl2=v.findViewById(R.id.rl2);
        label_update=v.findViewById(R.id.label_update);
        rv_kms_reading=v.findViewById(R.id.rv_kms_reading);
        AWSMobileClient.getInstance().initialize(activity).execute();
        credentials = new BasicAWSCredentials(SPHelper.getSPData(activity,SPHelper.awskey,""),
                SPHelper.getSPData(activity,SPHelper.awssecret,""));
        s3Client = new AmazonS3Client(credentials);
        mRequestPermissionHandler = new RequestPermissionHandler();

        if(SPHelper.is_od_update.equalsIgnoreCase("y")){
            rl_update_kms.setVisibility(View.VISIBLE);
            label_date.setVisibility(View.GONE);
        }else {
            rl_update_kms.setVisibility(View.GONE);
            label_date.setVisibility(View.VISIBLE);
        }
        rl_update_kms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opencamera();
            }
        });
        rl_update_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //take kms driven
                //call service

                if(selected_kms.getText().toString().equals("")){
                    Toast.makeText(activity, "Pleaase enter kms driven", Toast.LENGTH_SHORT).show();
                }else {
                    update_odometer();
                }

            }
        });

        get_odo_history();

        return  v;
    }

    public void get_odo_history() {
        if (!Connectivity.isNetworkConnected(activity)) {
            Toast.makeText(activity,
                    "Plaese Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        } else {
           // idPBLoading.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.get_odo_history(SPHelper.veh_id,SPHelper.lead_veh_id);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                    AppResponse appResponse = response.body();
                    assert appResponse != null;
                    String response_code = appResponse.getResponseType();
                    if (response.body() != null) {
                        if (response_code.equals("200")) {
                           // idPBLoading.setVisibility(View.GONE);

                            pojoKmsData=new ArrayList<>();
                            pojoKmsData=appResponse.getResponseModel().getOdometerHistory();
                            LinearLayoutManager l1=new LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false);
                            adapterKmsData=new AdapterKmsData(pojoKmsData,activity);
                            rv_kms_reading.setLayoutManager(l1);
                            rv_kms_reading.setAdapter(adapterKmsData);
                            String msg=appResponse.getResponseModel().getOdometermessage();

                            label_date.setText(msg);
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapterKmsData.notifyDataSetChanged();
                                }
                            });
                        } else if (response_code.equals("300")) {
                          //  idPBLoading.setVisibility(View.GONE);
                        }
                    } else {
                       // idPBLoading.setVisibility(View.GONE);
                        Toast.makeText(activity, "internal server error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                    Toast.makeText(activity,
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                   // idPBLoading.setVisibility(View.GONE);
                }
            });
        }
    }

    public void opencamera(){
        mRequestPermissionHandler.requestPermission(activity, new String[]{
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

    public void CallCamera() {

        mRequestPermissionHandler.requestPermission(activity, new String[]{
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
            cam_uri = FileProvider.getUriForFile(activity,
                    BuildConfig.APPLICATION_ID + ".provider", new File(filename));
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, cam_uri);
            startActivityForResult(takePictureIntent, 123);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
         if(resultCode==RESULT_OK&&it_is.equals("c"))
        {

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs(filename, new Pair<Integer, Integer>(2040, 1080), "/");
                    cam_uri = FileProvider.getUriForFile(activity,
                            BuildConfig.APPLICATION_ID + ".provider", new File(OriginalFileName));
                    filename = OriginalFileName;
                    taken_odo.setImageURI(cam_uri);


                    if (!Connectivity.isNetworkConnected(activity)) {

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
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
             idPBLoading.setVisibility(View.VISIBLE);
            final TransferUtility transferUtility =
                    TransferUtility.builder()
                            .context(activity)
                            .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                            .s3Client(s3Client)
                            .build();
            final String key = "Updated_kms"+"/" + imageUri.getLastPathSegment();
            final TransferObserver uploadObserver =
                    transferUtility.upload(key, new File(filename));
            uploadObserver.setTransferListener(new TransferListener() {
                @Override
                public void onStateChanged(int id, TransferState state)
                {
                    if (TransferState.COMPLETED == state) {
                        Toast.makeText(activity, "uploaded!", Toast.LENGTH_SHORT).show();
                        String finalurl = s3Client.getResourceUrl("ab-prod-container", key);
                        System.out.print(finalurl);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                idPBLoading.setVisibility(View.GONE);
                                // progressDialog.cancel();
                            }
                        });

                        odo_url=finalurl;
                        rv_kms_reading.setVisibility(View.GONE);
                        rl2.setVisibility(View.VISIBLE);
                        rl_update_now.setVisibility(View.VISIBLE);
                        rl_update_kms.setVisibility(View.GONE);

                    } else if (TransferState.FAILED == state) {

                        activity.runOnUiThread(new Runnable() {
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
                    activity.runOnUiThread(new Runnable() {
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

    public void update_odometer() {
        {
            if (!Connectivity.isNetworkConnected(activity)) {
                Toast.makeText(activity,
                        "Please Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            } else {
                idPBLoading.setVisibility(View.VISIBLE);
                String c_id = SPHelper.getSPData(activity, SPHelper.customer_id, "");

                PojoUpdateOdometer pojoUpdateOdometer=new PojoUpdateOdometer(SPHelper.veh_id,SPHelper.lead_veh_id,"",
                        c_id,selected_kms.getText().toString(),odo_url);
                Call<AppResponse> call = apiInterface.update_odo(pojoUpdateOdometer);
                call.enqueue(new Callback<AppResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                        AppResponse appResponse = response.body();
                        assert appResponse != null;
                        String response_code = appResponse.getResponseType();
                        if (response.body() != null) {
                            if (response_code.equals("200")) {
                                idPBLoading.setVisibility(View.GONE);
                                Toast.makeText(activity, appResponse.getResponseModel().getMessage(), Toast.LENGTH_SHORT).show();
                                if(SPHelper.current_page.equals("warr")){
                                    Warranty_Description.getInstance().get_pack_detailslist();
                                }else {
                                    MyCar_Fragment.get_instance().get_cars_list();
                                }
                                dismiss();
                            } else if (response_code.equals("300")) {
                                idPBLoading.setVisibility(View.GONE);
                                Toast.makeText(activity, appResponse.getResponseModel().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                             idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(activity, "internal server error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                        Toast.makeText(activity,
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        idPBLoading.setVisibility(View.GONE);
                    }
                });
            }
        }
    }
}