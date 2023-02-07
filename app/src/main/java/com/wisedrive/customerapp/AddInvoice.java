package com.wisedrive.customerapp;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.wisedrive.customerapp.commonclasses.BitmapUtility;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.RequestPermissionHandler;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.AppResponse;
import com.wisedrive.customerapp.pojos.PojoImageArray;
import com.wisedrive.customerapp.pojos.PojoSubmitItems;
import com.wisedrive.customerapp.pojos.PojoUploadInvoice;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddInvoice extends AppCompatActivity {
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    RelativeLayout rl_back,rl_upload1,rl_upload2,rl_upload3,rl_upload4;
    ImageView iv1,iv2,iv3,iv4;
    String setimage,filename,invoice_img1="",invoice_img2="",invoice_img3="",invoice_img4="";
    int fromWhere=0;
    Uri imageUri;
    public  boolean isServiceRunning = true;
    private BasicAWSCredentials credentials;
    private AmazonS3Client s3Client;
    private RequestPermissionHandler mRequestPermissionHandler;
    TextView tv_upload;
    ArrayList<PojoImageArray> pojoImageArray;
    PojoImageArray obj=new PojoImageArray();
    public ArrayList<String> finalimgs =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_invoice);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        progressDialog = new ProgressDialog(AddInvoice.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        mRequestPermissionHandler = new RequestPermissionHandler();
        AWSMobileClient.getInstance().initialize(AddInvoice.this).execute();
        credentials = new BasicAWSCredentials
                (SPHelper.getSPData(AddInvoice.this,SPHelper.awskey,""),
                        SPHelper.getSPData(AddInvoice.this,SPHelper.awssecret,""));
//        credentials = new BasicAWSCredentials
//                ("AKIAV332MX7U2NTHENOS", "sc6BnWAUBej4YRkUoV/su1hUu6W30poqM803hQic");
        s3Client = new AmazonS3Client(credentials);
        rl_back=findViewById(R.id.rl_back);
        tv_upload=findViewById(R.id.tv_upload);
        rl_upload3=findViewById(R.id.rl_upload3);
        rl_upload4=findViewById(R.id.rl_upload4);
        rl_upload1=findViewById(R.id.rl_upload1);
        rl_upload2=findViewById(R.id.rl_upload2);
        iv1=findViewById(R.id.iv1);
        iv2=findViewById(R.id.iv2);
        iv3=findViewById(R.id.iv3);
        iv4=findViewById(R.id.iv4);
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        rl_upload1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                setimage="1";
                opendialog(view);
            }
        });
        rl_upload2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setimage="2";
                opendialog(view);
            }
        });
        rl_upload3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setimage="3";
                opendialog(view);
            }
        });
        rl_upload4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setimage="4";
                opendialog(view);
            }
        });
        tv_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(finalimgs.isEmpty()){
                    Toast.makeText(AddInvoice.this,"upload atleast 1 image",Toast.LENGTH_SHORT).show();
                }else{
                   upload_invoice();
                }
            }
        });
    }

    public void opendialog(View view){
        final Dialog dialog4 = new Dialog(AddInvoice.this);
        dialog4.setCancelable(true);
        View view4 = AddInvoice.this.getLayoutInflater().inflate(R.layout.dialog_camera_options, null);
        dialog4.setContentView(view4);
        RelativeLayout rl_camera4 = view4.findViewById(R.id.rl_camera);
        RelativeLayout rl_gallery4 = view4.findViewById(R.id.rl_gallery);

        rl_camera4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(setimage.equals("1")){
                        fromWhere=1;
                    }else if(setimage.equals("2")){
                        fromWhere=2;
                    }
                    else if(setimage.equals("3")){
                        fromWhere=3;
                    }
                    else if(setimage.equals("4")){
                        fromWhere=4;
                    }
                   
                    getCameraPermissions(fromWhere);
                }
                dialog4.dismiss();
            }
        });
        rl_gallery4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(setimage.equals("1")){
                        fromWhere=10;
                    }else if(setimage.equals("2")){
                        fromWhere=20;
                    }
                    else if(setimage.equals("3")){
                        fromWhere=30;
                    }
                    else if(setimage.equals("4")){
                        fromWhere=40;
                    }
                    
                    getCameraPermissions(fromWhere);
                }
                dialog4.dismiss();
            }
        });
        dialog4.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getCameraPermissions( int fromWhere)
    {
        mRequestPermissionHandler.requestPermission(AddInvoice.this, new String[]
                {
                        android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, fromWhere, new RequestPermissionHandler.RequestPermissionListener() {
            @Override
            public void onSuccess() {
                System.out.println("Succeed");
                if (fromWhere == 1) {
                    CallCamera();
                } else if (fromWhere == 10) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivityForResult(pickPhoto, 200);

                } else if (fromWhere == 2) {

                    CallCamera();
                } else if (fromWhere == 20) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivityForResult(pickPhoto, 400);

                } else if (fromWhere == 3) {

                    CallCamera();
                } else if (fromWhere == 30) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivityForResult(pickPhoto, 600);

                } else if (fromWhere == 4) {

                    CallCamera();
                } else if (fromWhere == 40) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivityForResult(pickPhoto, 800);
                }
            }

            @Override
            public void onFailed() {
                System.out.println("denied");
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void CallCamera() {
        mRequestPermissionHandler.requestPermission(AddInvoice.this, new String[]{
                android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
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


    @RequiresApi(api = Build.VERSION_CODES.M)
    void openCamera() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            SimpleDateFormat dateFormat = new SimpleDateFormat("-yyyy_MM_dd_HH_mm_ss_SSSSSS'.jpg'");
            String fineName = dateFormat.format(new Date());
            filename = BitmapUtility.PictUtil.getSavePath().getPath() + "/" + fineName;
            imageUri = FileProvider.getUriForFile(AddInvoice.this,
                    BuildConfig.APPLICATION_ID + ".provider",
                    new File(filename));
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            //startActivityForResult(takePictureIntent, 100);
            if(setimage.equals("1")){
                startActivityForResult(takePictureIntent, 100);
            }else if(setimage.equals("2")){
                startActivityForResult(takePictureIntent, 300);
            }
            else if(setimage.equals("3")){
                startActivityForResult(takePictureIntent, 500);
            }
            else if(setimage.equals("4")){
                startActivityForResult(takePictureIntent, 700);
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //1
        if (requestCode == 100 && resultCode == RESULT_OK )
        {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs(filename, new Pair<Integer, Integer>(2040, 1080), "/");
                    imageUri = FileProvider.getUriForFile(AddInvoice.this,
                            BuildConfig.APPLICATION_ID + ".provider", new File(OriginalFileName));
                    filename = OriginalFileName;
                    iv1.setVisibility(View.VISIBLE);
                    iv1.setImageURI(imageUri);

                    if (!Connectivity.isNetworkConnected(AddInvoice.this)) {
                        progressDialog.dismiss();
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(AddInvoice.this);
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
                    File imageFile = new File(filename);

                    Uri uri = Uri.fromFile(imageFile);

                    try {
                        progressDialog.show();
                        final TransferUtility transferUtility =
                                TransferUtility.builder()
                                        .context(getApplicationContext())
                                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                                        .s3Client(s3Client)
                                        .build();
                        final String key = "RCFront/" + imageUri.getLastPathSegment();
                        final TransferObserver uploadObserver =
                                transferUtility.upload(key, new File(filename));
                        uploadObserver.setTransferListener(new TransferListener() {
                            @Override
                            public void onStateChanged(int id, TransferState state) {
                                if (TransferState.COMPLETED == state) {
                                    Toast.makeText(getApplicationContext(), "Upload Completed!", Toast.LENGTH_SHORT).show();
                                    String finalurl = s3Client.getResourceUrl("ab-prod-container", key);
                                    System.out.print(finalurl);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressDialog.dismiss();
                                            progressDialog.cancel();
                                        }
                                    });
                                    invoice_img1 = finalurl;
                                    finalimgs.add(finalurl);

                                } else if (TransferState.FAILED == state) {

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressDialog.dismiss();
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
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressDialog.dismiss();
                                        progressDialog.cancel();
                                    }
                                });
                            }

                        });
                    } catch (Exception je) {

                        je.printStackTrace();
                    }
                    // uploadImageAmazon();
                    /*camera_hide_view.setVisibility(View.INVISIBLE);
                    isimagepresent=true;*/

                }
            });
        }
        else if (requestCode == 200 && data!=null)
        {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Uri imageUri = data.getData();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("-yyyy_MM_dd_HH_mm_ss_SSSSSS'.jpg'");
                    String fineName = dateFormat.format(new Date());
                    filename = BitmapUtility.PictUtil.getSavePath().getPath() + "/" + SPHelper.getSPData(AddInvoice.this, "rc", "") + fineName;
                    Uri OriginalFileName = null;
                    try {
                        OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs3(AddInvoice.this, imageUri, filename, new Pair<Integer, Integer>(2040, 1080), "/");
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    imageUri = OriginalFileName;
                    iv1.setVisibility(View.VISIBLE);
                    iv1.setImageURI(imageUri);
                    if (!Connectivity.isNetworkConnected(AddInvoice.this)) {
                        progressDialog.dismiss();
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(AddInvoice.this);
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
                    File imageFile = new File(filename);

                    Uri uri = Uri.fromFile(imageFile);

                    try {
                        progressDialog.show();
                        final TransferUtility transferUtility =
                                TransferUtility.builder()
                                        .context(getApplicationContext())
                                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                                        .s3Client(s3Client)
                                        .build();
                        final String key = "RCFront/" + imageUri.getLastPathSegment();
                        final TransferObserver uploadObserver =
                                transferUtility.upload(key, new File(filename));
                        uploadObserver.setTransferListener(new TransferListener() {
                            @Override
                            public void onStateChanged(int id, TransferState state) {
                                if (TransferState.COMPLETED == state) {
                                    Toast.makeText(getApplicationContext(), "Upload Completed!", Toast.LENGTH_SHORT).show();
                                    String finalurl = s3Client.getResourceUrl("ab-prod-container", key);
                                    System.out.print(finalurl);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressDialog.dismiss();
                                            progressDialog.cancel();
                                        }
                                    });
                                    invoice_img1 = finalurl;
                                    finalimgs.add(finalurl);

                                } else if (TransferState.FAILED == state) {

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressDialog.dismiss();
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
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressDialog.dismiss();
                                        progressDialog.cancel();
                                    }
                                });
                            }

                        });
                    } catch (Exception je) {

                        je.printStackTrace();
                    }
                    // uploadImageAmazon();
                    /*camera_hide_view.setVisibility(View.INVISIBLE);
                    isimagepresent=true;*/

                }
            });
        }
        //2
        else if (requestCode == 300 && resultCode == RESULT_OK) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs(filename, new Pair<Integer, Integer>(2040, 1080), "/");
                    imageUri = FileProvider.getUriForFile(AddInvoice.this,
                            BuildConfig.APPLICATION_ID + ".provider", new File(OriginalFileName));
                    filename = OriginalFileName;
                    iv2.setVisibility(View.VISIBLE);
                    iv2.setImageURI(imageUri);
                    if (!Connectivity.isNetworkConnected(AddInvoice.this)) {
                        progressDialog.dismiss();
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(AddInvoice.this);
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
                    File imageFile = new File(filename);

                    Uri uri = Uri.fromFile(imageFile);

                    try {
                        progressDialog.show();
                        final TransferUtility transferUtility =
                                TransferUtility.builder()
                                        .context(getApplicationContext())
                                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                                        .s3Client(s3Client)
                                        .build();
                        final String key = "RCBack/" + imageUri.getLastPathSegment();
                        final TransferObserver uploadObserver =
                                transferUtility.upload(key, new File(filename));
                        uploadObserver.setTransferListener(new TransferListener() {
                            @Override
                            public void onStateChanged(int id, TransferState state) {
                                if (TransferState.COMPLETED == state) {
                                    Toast.makeText(getApplicationContext(), "Upload Completed!", Toast.LENGTH_SHORT).show();
                                    String finalurl = s3Client.getResourceUrl("ab-prod-container", key);
                                    System.out.print(finalurl);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressDialog.dismiss();
                                            progressDialog.cancel();
                                        }
                                    });
                                    invoice_img2 = finalurl;
                                    finalimgs.add(finalurl);

                                } else if (TransferState.FAILED == state) {

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressDialog.dismiss();
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
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressDialog.dismiss();
                                        progressDialog.cancel();
                                    }
                                });
                            }

                        });
                    } catch (Exception je) {

                        je.printStackTrace();
                    }
                    // uploadImageAmazon();
                    /*camera_hide_view.setVisibility(View.INVISIBLE);
                    isimagepresent=true;*/

                }
            });

        }
        else if (requestCode == 400 && data!=null)  {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Uri imageUri = data.getData();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("-yyyy_MM_dd_HH_mm_ss_SSSSSS'.jpg'");
                    String fineName = dateFormat.format(new Date());
                    filename = BitmapUtility.PictUtil.getSavePath().getPath() + "/" + SPHelper.getSPData(AddInvoice.this, "rc", "") + fineName;
                    Uri OriginalFileName = null;
                    try {
                        OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs3(AddInvoice.this, imageUri, filename, new Pair<Integer, Integer>(2040, 1080), "/");
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    imageUri = OriginalFileName;
                    iv2.setVisibility(View.VISIBLE);
                    iv2.setImageURI(imageUri);
                    if (!Connectivity.isNetworkConnected(AddInvoice.this)) {
                        progressDialog.dismiss();
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(AddInvoice.this);
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
                    File imageFile = new File(filename);

                    Uri uri = Uri.fromFile(imageFile);

                    try {
                        progressDialog.show();
                        final TransferUtility transferUtility =
                                TransferUtility.builder()
                                        .context(getApplicationContext())
                                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                                        .s3Client(s3Client)
                                        .build();
                        final String key = "RCBack/" + imageUri.getLastPathSegment();
                        final TransferObserver uploadObserver =
                                transferUtility.upload(key, new File(filename));
                        uploadObserver.setTransferListener(new TransferListener() {
                            @Override
                            public void onStateChanged(int id, TransferState state) {
                                if (TransferState.COMPLETED == state) {
                                    Toast.makeText(getApplicationContext(), "Upload Completed!", Toast.LENGTH_SHORT).show();
                                    String finalurl = s3Client.getResourceUrl("ab-prod-container", key);
                                    System.out.print(finalurl);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressDialog.dismiss();
                                            progressDialog.cancel();
                                        }
                                    });
                                    invoice_img2 = finalurl;
                                    finalimgs.add(finalurl);

                                } else if (TransferState.FAILED == state) {

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressDialog.dismiss();
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
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressDialog.dismiss();
                                        progressDialog.cancel();
                                    }
                                });
                            }

                        });
                    } catch (Exception je) {

                        je.printStackTrace();
                    }
                    // uploadImageAmazon();
                    /*camera_hide_view.setVisibility(View.INVISIBLE);
                    isimagepresent=true;*/

                }
            });
        }
        //3
        else if (requestCode == 500 && resultCode == RESULT_OK) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs(filename, new Pair<Integer, Integer>(2040, 1080), "/");
                    imageUri = FileProvider.getUriForFile(AddInvoice.this,
                            BuildConfig.APPLICATION_ID + ".provider", new File(OriginalFileName));
                    filename = OriginalFileName;
                    iv3.setVisibility(View.VISIBLE);
                    iv3.setImageURI(imageUri);
                    if (!Connectivity.isNetworkConnected(AddInvoice.this)) {
                        progressDialog.dismiss();
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(AddInvoice.this);
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
                    File imageFile = new File(filename);

                    Uri uri = Uri.fromFile(imageFile);

                    try {
                        progressDialog.show();
                        final TransferUtility transferUtility =
                                TransferUtility.builder()
                                        .context(getApplicationContext())
                                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                                        .s3Client(s3Client)
                                        .build();
                        final String key = "AadharFront/" + imageUri.getLastPathSegment();
                        final TransferObserver uploadObserver =
                                transferUtility.upload(key, new File(filename));
                        uploadObserver.setTransferListener(new TransferListener() {
                            @Override
                            public void onStateChanged(int id, TransferState state) {
                                if (TransferState.COMPLETED == state) {
                                    Toast.makeText(getApplicationContext(), "Upload Completed!", Toast.LENGTH_SHORT).show();
                                    String finalurl = s3Client.getResourceUrl("ab-prod-container", key);
                                    System.out.print(finalurl);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressDialog.dismiss();
                                            progressDialog.cancel();
                                        }
                                    });
                                    invoice_img3 = finalurl;
                                    finalimgs.add(finalurl);

                                } else if (TransferState.FAILED == state) {

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressDialog.dismiss();
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
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressDialog.dismiss();
                                        progressDialog.cancel();
                                    }
                                });
                            }

                        });
                    } catch (Exception je) {

                        je.printStackTrace();
                    }
                    // uploadImageAmazon();
                    /*camera_hide_view.setVisibility(View.INVISIBLE);
                    isimagepresent=true;*/

                }
            });

        }
        else if (requestCode == 600 && data!=null)  {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Uri imageUri = data.getData();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("-yyyy_MM_dd_HH_mm_ss_SSSSSS'.jpg'");
                    String fineName = dateFormat.format(new Date());
                    filename = BitmapUtility.PictUtil.getSavePath().getPath() + "/" + SPHelper.getSPData(AddInvoice.this, "rc", "") + fineName;
                    Uri OriginalFileName = null;
                    try {
                        OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs3(AddInvoice.this, imageUri, filename, new Pair<Integer, Integer>(2040, 1080), "/");
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    imageUri = OriginalFileName;
                    iv3.setVisibility(View.VISIBLE);
                    iv3.setImageURI(imageUri);
                    if (!Connectivity.isNetworkConnected(AddInvoice.this)) {
                        progressDialog.dismiss();
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(AddInvoice.this);
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
                    File imageFile = new File(filename);

                    Uri uri = Uri.fromFile(imageFile);

                    try {
                        progressDialog.show();
                        final TransferUtility transferUtility =
                                TransferUtility.builder()
                                        .context(getApplicationContext())
                                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                                        .s3Client(s3Client)
                                        .build();
                        final String key = "AadharFront/" + imageUri.getLastPathSegment();
                        final TransferObserver uploadObserver =
                                transferUtility.upload(key, new File(filename));
                        uploadObserver.setTransferListener(new TransferListener() {
                            @Override
                            public void onStateChanged(int id, TransferState state) {
                                if (TransferState.COMPLETED == state) {
                                    Toast.makeText(getApplicationContext(), "Upload Completed!", Toast.LENGTH_SHORT).show();
                                    String finalurl = s3Client.getResourceUrl("ab-prod-container", key);
                                    System.out.print(finalurl);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressDialog.dismiss();
                                            progressDialog.cancel();
                                        }
                                    });
                                    invoice_img3 = finalurl;
                                    finalimgs.add(finalurl);

                                } else if (TransferState.FAILED == state) {

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressDialog.dismiss();
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
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressDialog.dismiss();
                                        progressDialog.cancel();
                                    }
                                });
                            }

                        });
                    } catch (Exception je) {

                        je.printStackTrace();
                    }
                    // uploadImageAmazon();
                    /*camera_hide_view.setVisibility(View.INVISIBLE);
                    isimagepresent=true;*/

                }
            });
        }
        //aadharback
        else if (requestCode == 700 && resultCode == RESULT_OK) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs(filename, new Pair<Integer, Integer>(2040, 1080), "/");
                    imageUri = FileProvider.getUriForFile(AddInvoice.this,
                            BuildConfig.APPLICATION_ID + ".provider", new File(OriginalFileName));
                    filename = OriginalFileName;
                    iv4.setVisibility(View.VISIBLE);
                    iv4.setImageURI(imageUri);
                    if (!Connectivity.isNetworkConnected(AddInvoice.this)) {
                        progressDialog.dismiss();
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(AddInvoice.this);
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
                    File imageFile = new File(filename);

                    Uri uri = Uri.fromFile(imageFile);

                    try {
                        progressDialog.show();
                        final TransferUtility transferUtility =
                                TransferUtility.builder()
                                        .context(getApplicationContext())
                                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                                        .s3Client(s3Client)
                                        .build();
                        final String key = "AadharBack/" + imageUri.getLastPathSegment();
                        final TransferObserver uploadObserver =
                                transferUtility.upload(key, new File(filename));
                        uploadObserver.setTransferListener(new TransferListener() {
                            @Override
                            public void onStateChanged(int id, TransferState state) {
                                if (TransferState.COMPLETED == state) {
                                    Toast.makeText(getApplicationContext(), "Upload Completed!", Toast.LENGTH_SHORT).show();
                                    String finalurl = s3Client.getResourceUrl("ab-prod-container", key);
                                    System.out.print(finalurl);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressDialog.dismiss();
                                            progressDialog.cancel();
                                        }
                                    });
                                    invoice_img4 = finalurl;
                                    finalimgs.add(finalurl);

                                } else if (TransferState.FAILED == state) {

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressDialog.dismiss();
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
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressDialog.dismiss();
                                        progressDialog.cancel();
                                    }
                                });
                            }

                        });
                    } catch (Exception je) {

                        je.printStackTrace();
                    }
                    // uploadImageAmazon();
                    /*camera_hide_view.setVisibility(View.INVISIBLE);
                    isimagepresent=true;*/

                }
            });
        }
        else if (requestCode == 800 && data!=null)  {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Uri imageUri = data.getData();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("-yyyy_MM_dd_HH_mm_ss_SSSSSS'.jpg'");
                    String fineName = dateFormat.format(new Date());
                    filename = BitmapUtility.PictUtil.getSavePath().getPath() + "/" + SPHelper.getSPData(AddInvoice.this, "rc", "") + fineName;
                    Uri OriginalFileName = null;
                    try {
                        OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs3(AddInvoice.this, imageUri, filename, new Pair<Integer, Integer>(2040, 1080), "/");
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    imageUri = OriginalFileName;
                    iv4.setVisibility(View.VISIBLE);
                    iv4.setImageURI(imageUri);
                    if (!Connectivity.isNetworkConnected(AddInvoice.this)) {
                        progressDialog.dismiss();
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(AddInvoice.this);
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
                    File imageFile = new File(filename);

                    Uri uri = Uri.fromFile(imageFile);

                    try {
                        progressDialog.show();
                        final TransferUtility transferUtility =
                                TransferUtility.builder()
                                        .context(getApplicationContext())
                                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                                        .s3Client(s3Client)
                                        .build();
                        final String key = "AadharBack/" + imageUri.getLastPathSegment();
                        final TransferObserver uploadObserver =
                                transferUtility.upload(key, new File(filename));
                        uploadObserver.setTransferListener(new TransferListener() {
                            @Override
                            public void onStateChanged(int id, TransferState state) {
                                if (TransferState.COMPLETED == state) {
                                    Toast.makeText(getApplicationContext(), "Upload Completed!", Toast.LENGTH_SHORT).show();
                                    String finalurl = s3Client.getResourceUrl("ab-prod-container", key);
                                    System.out.print(finalurl);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressDialog.dismiss();
                                            progressDialog.cancel();
                                        }
                                    });
                                    invoice_img4 = finalurl;
                                    finalimgs.add(finalurl);

                                } else if (TransferState.FAILED == state) {

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressDialog.dismiss();
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
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressDialog.dismiss();
                                        progressDialog.cancel();
                                    }
                                });
                            }

                        });
                    } catch (Exception je) {

                        je.printStackTrace();
                    }
                    // uploadImageAmazon();
                    /*camera_hide_view.setVisibility(View.INVISIBLE);
                    isimagepresent=true;*/

                }
            });
        }
    }

    private void validate()
    {
    }

    private void upload_invoice() {
        if (!Connectivity.isNetworkConnected(AddInvoice.this)) {
            Toast.makeText(AddInvoice.this, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
            return;
        } else {
            progressDialog.show();

            pojoImageArray=new ArrayList<PojoImageArray>();
            for (int i = 0; i <finalimgs.size(); i++)
            {
                PojoImageArray obj=new PojoImageArray();
                        obj.setInvoice_image(finalimgs.get(i));
                        pojoImageArray.add(obj);

            }
            PojoUploadInvoice post1=new PojoUploadInvoice(SPHelper.veh_id,
                    SPHelper.getSPData(AddInvoice.this,SPHelper.customer_id,""),pojoImageArray);
            Call<AppResponse> call = apiInterface.upload_invoice(post1);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                    System.out.print(response.body());
                    if (response.body() != null)
                    {
                        AppResponse data = response.body();
                        if (data.getResponseType().equalsIgnoreCase("200")) {
                            Toast.makeText(AddInvoice.this, "Your Invoice has been uploaded sucessfully", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(AddInvoice.this,AdditionalServicePage.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(AddInvoice.this, data.getResponseType(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<AppResponse> call, Throwable th) {
                    Toast.makeText(AddInvoice.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }
    }
}