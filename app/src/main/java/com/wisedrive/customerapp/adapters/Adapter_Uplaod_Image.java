package com.wisedrive.customerapp.adapters;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.Add_New_Car;
import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.pojos.Pojo_Select_Make_list;
import com.wisedrive.customerapp.pojos.Pojo_Upload_Image;

import java.util.ArrayList;

public class Adapter_Uplaod_Image extends RecyclerView.Adapter<Adapter_Uplaod_Image.MyViewHolder> {
    private final int GALLERY_REQ_CODE = 1000;
    private final int CAMERA_REQ_CODE = 100;
    Context context;
    private View view;
    ArrayList<Pojo_Upload_Image> pojo_upload_imageArrayList;

    public Adapter_Uplaod_Image(Context context, ArrayList<Pojo_Upload_Image> pojo_upload_imageArrayList) {
        this.context = context;
        this.view = view;
        this.pojo_upload_imageArrayList = pojo_upload_imageArrayList;
    }

    public Adapter_Uplaod_Image.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_photos, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Uplaod_Image.MyViewHolder holder, int position) {
        Pojo_Upload_Image list = pojo_upload_imageArrayList.get(position);
        holder.uplaod_image.setImageResource(list.getUplaod_image());
        holder.tv_image_name.setText(list.getTv_image_name());
        holder.rl_items_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context);
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
                     holder.takepicturefromgallery();
                        dialog.cancel();

                    }
                });
                TextView textView = (TextView) dialog.findViewById(R.id.textcapture);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        holder.takepicturefromcamera();
                        dialog.cancel();

                    }


                });


                dialog.show();


            }


        });


    }

    @Override
    public int getItemCount() {
        return pojo_upload_imageArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements com.wisedrive.customerapp.adapters.MyViewHolder {
        ImageView uplaod_image;
        TextView tv_image_name;
        RelativeLayout rl_items_upload;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            uplaod_image = itemView.findViewById(R.id.uplaod_image);
            tv_image_name = itemView.findViewById(R.id.tv_image_name);
            rl_items_upload = (RelativeLayout) view.findViewById(R.id.rl_items_upload);
        }

        protected void takepicturefromgallery() {
            Intent picphoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            ((Activity) context).startActivityForResult(picphoto, GALLERY_REQ_CODE);
        }

        protected void takepicturefromcamera() {
            Intent takepicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            ((Activity) context).startActivityForResult(takepicture, CAMERA_REQ_CODE);

        }

        public void ABCD(int requestCode, int resultCode, @Nullable Intent data) {

            switch (requestCode) {
                case 1:
                    if (requestCode == RESULT_OK) {
                        Uri selectedimageUri = data.getData();
                        uplaod_image.setImageURI(selectedimageUri);
                    }
                    break;
                case 2:
                    if (resultCode == RESULT_OK) {
                        Bundle bundle = data.getExtras();
                        Bitmap bitmapImage = (Bitmap) bundle.get("data");
                        uplaod_image.setImageBitmap(bitmapImage);
                    }
                    break;

            }

        }



        }





    }


