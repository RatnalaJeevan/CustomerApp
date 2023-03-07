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

import com.bumptech.glide.Glide;
import com.wisedrive.customerapp.Add_New_Car;
import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.pojos.Pojo_Select_Make_list;
import com.wisedrive.customerapp.pojos.Pojo_Upload_Image;

import java.util.ArrayList;

public class Adapter_Uplaod_Image extends RecyclerView.Adapter<Adapter_Uplaod_Image.MyViewHolder> {

    Context context;
    private View view;
    ArrayList<Pojo_Upload_Image> pojo_upload_imageArrayList;
    public int sel_position=0;
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
    public void onBindViewHolder(@NonNull Adapter_Uplaod_Image.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Pojo_Upload_Image recyclerdata = pojo_upload_imageArrayList.get(position);

        holder.tv_image_name.setText(recyclerdata.getName());
        if (recyclerdata.getImage() == null) {
            //uploaded_image.setImageURI(carImageLists.get(i).getImage());
        } else {
            holder.uplaod_image.setImageURI(recyclerdata.getImage());
        }
//        if (recyclerdata.getVehicle_images() != null && !carImageLists.get(position).getVehicle_images().isEmpty() && !carImageLists.get(position).getVehicle_images().equals("null")) {
//            Glide.with(context).load(carImageLists.get(position).getVehicle_images()).placeholder(R.drawable.icon_noimage).into(holder.car_image_position);
//        }
        holder.rl_items_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sel_position=position;
                Add_New_Car.getInstance().selectedObject=position;
                Add_New_Car.getInstance().onclick="";
                Add_New_Car.getInstance().finalids.add(recyclerdata.getId());
                Add_New_Car.getInstance().open_dialog();
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

        }
    }



