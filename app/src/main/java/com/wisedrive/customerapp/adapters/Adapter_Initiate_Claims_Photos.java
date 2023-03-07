package com.wisedrive.customerapp.adapters;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.Activity_Showroom_Services;
import com.wisedrive.customerapp.Add_New_Car;
import com.wisedrive.customerapp.InitiateNewClaim;
import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.pojos.Pojo_Upload_Image;
import com.wisedrive.customerapp.pojos.Pojo_initiate_Claims_Photos;

import java.util.ArrayList;

public class Adapter_Initiate_Claims_Photos extends RecyclerView.Adapter< Adapter_Initiate_Claims_Photos.MyViewHolder> {
    private final int GALLERY_REQ_CODE = 1000;
    private final int CAMERA_REQ_CODE = 100;
    Context context;
    private View view;
    public int adapter_position=0;
    ArrayList<Pojo_initiate_Claims_Photos> pojo_initiate_claims_photosArrayList;

    public Adapter_Initiate_Claims_Photos(Context context, ArrayList<Pojo_initiate_Claims_Photos> pojo_initiate_claims_photosArrayList) {
        this.context = context;
        this.view = view;
        this.pojo_initiate_claims_photosArrayList = pojo_initiate_claims_photosArrayList;
    }

    public Adapter_Initiate_Claims_Photos.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_initiate_claims_photos, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Initiate_Claims_Photos.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Pojo_initiate_Claims_Photos list =pojo_initiate_claims_photosArrayList.get(position);

        if (list.getImage() == null) {
            //uploaded_image.setImageURI(carImageLists.get(i).getImage());
        } else {
            holder.uplaod_image.setImageURI(list.getImage());
        }
        holder.tv_image_name.setText(list.getImage_type());
        holder.rl_items_upload.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                adapter_position=position;
                ((InitiateNewClaim)context).selectedObject=position;
                ((InitiateNewClaim)context).open_dialog();

            }

        });


    }

    @Override
    public int getItemCount() {
        return pojo_initiate_claims_photosArrayList.size();
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
