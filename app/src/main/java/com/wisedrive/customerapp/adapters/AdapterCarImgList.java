package com.wisedrive.customerapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.SelfInspection;
import com.wisedrive.customerapp.pojos.PojoCarImgs;

import java.util.ArrayList;

public class AdapterCarImgList extends RecyclerView.Adapter<AdapterCarImgList.ViewHolder> {

    ArrayList<PojoCarImgs> pojoCarImgs;
    Context context;
    public int adapter_position=0;
    public AdapterCarImgList(ArrayList<PojoCarImgs> pojoCarImgs, Context context) {
        this.pojoCarImgs = pojoCarImgs;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterCarImgList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_car_img_list, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCarImgList.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        PojoCarImgs data= pojoCarImgs.get(position);
        holder.label_name.setText(pojoCarImgs.get(position).getPart_name());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter_position=position;
                ((SelfInspection)context).CallCamera(position);
            }
        });

        if (pojoCarImgs.get(position).getTaken_img() == null)
        {
            Glide.with(context).load(pojoCarImgs.get(position).getSample_image()).placeholder(R.drawable.icon_noimage).into(holder.iv_cw);

        } else {
            holder.sel_aadhar_front.setImageURI(pojoCarImgs.get(position).getTaken_img());
        }
    }

    @Override
    public int getItemCount() {
        return pojoCarImgs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView label_name;
        ImageView iv_cw,sel_aadhar_front;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            label_name=itemView.findViewById(R.id.label_name);
            iv_cw=itemView.findViewById(R.id.iv_cw);
            sel_aadhar_front=itemView.findViewById(R.id.sel_aadhar_front);
        }
    }


}
