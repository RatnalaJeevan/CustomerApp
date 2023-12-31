package com.wisedrive.customerapp.adapters;


import android.annotation.SuppressLint;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.wisedrive.customerapp.InitiateNewClaim;
import com.wisedrive.customerapp.R;
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
            holder.uplaod_image.setVisibility(View.VISIBLE);
            holder.label1.setVisibility(View.VISIBLE);
        } else {
            holder.uplaod_image.setVisibility(View.GONE);
            holder.label1.setVisibility(View.GONE);
            holder.sel_aadhar_front.setImageURI(list.getImage());
        }
        holder.tv_image_name.setText(list.getImage_type());
        holder.itemView.setOnClickListener(new View.OnClickListener()
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
        ImageView uplaod_image,sel_aadhar_front;
        TextView tv_image_name,label1;
        RelativeLayout rl_items_upload;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            label1=itemView.findViewById(R.id.label1);
            uplaod_image = itemView.findViewById(R.id.uplaod_image);
            tv_image_name = itemView.findViewById(R.id.tv_image_name);
            sel_aadhar_front = itemView.findViewById(R.id.sel_aadhar_front);
        }

    }

}
