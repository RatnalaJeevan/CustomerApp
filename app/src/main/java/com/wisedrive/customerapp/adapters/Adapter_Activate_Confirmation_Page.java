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
import com.wisedrive.customerapp.commonclasses.Common;
import com.wisedrive.customerapp.pojos.Pojo_Activate_Confirmation_Page;
import com.wisedrive.customerapp.pojos.Pojo_My_Car_page_package_list;

import java.util.ArrayList;

public class Adapter_Activate_Confirmation_Page extends RecyclerView.Adapter<Adapter_Activate_Confirmation_Page.MyViewHolder> {
    Context context;
    private View view;
    ArrayList<Pojo_My_Car_page_package_list> pojo_activate_confirmation_pageArrayList;

    public Adapter_Activate_Confirmation_Page(Context context, ArrayList<Pojo_My_Car_page_package_list> pojo_activate_confirmation_pageArrayList) {
        this.context = context;
        this.pojo_activate_confirmation_pageArrayList = pojo_activate_confirmation_pageArrayList;
    }

    @Override
    public Adapter_Activate_Confirmation_Page.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_cofirmation, null);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Activate_Confirmation_Page.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Pojo_My_Car_page_package_list list = pojo_activate_confirmation_pageArrayList.get(position);
        //holder.imv_image.setImageResource(list.getImv_image());
        holder. tv_warranty_name.setText(list.getProduct_name());
        holder.tv_expire_date.setText(Common.getDateFromString(list.getValid_to()));
        Glide.with(context).load(list.getProduct_icon()).placeholder(R.drawable.service_image).into(holder.imv_image);
         }


    @Override
    public int getItemCount() {
        return pojo_activate_confirmation_pageArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imv_image;
        TextView tv_warranty_name,tv_expire_date;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imv_image  = itemView.findViewById(R.id.imv_image);
            tv_warranty_name = (TextView) itemView.findViewById(R.id.tv_warranty_name);
            tv_expire_date = (TextView) itemView.findViewById(R.id.tv_expire_date);
        }
    }

    }

