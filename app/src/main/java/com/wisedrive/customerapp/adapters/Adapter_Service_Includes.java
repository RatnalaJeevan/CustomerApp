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

import com.bumptech.glide.Glide;
import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.commonclasses.Common;
import com.wisedrive.customerapp.pojos.Pojo_My_Car_page_package_list;
import com.wisedrive.customerapp.pojos.Pojo_Service_Includes;
import com.wisedrive.customerapp.pojos.Pojo_Service_list;

import java.util.ArrayList;

public class Adapter_Service_Includes extends RecyclerView.Adapter<Adapter_Service_Includes.MyViewHolder> {
    Context context;
    private View view;
    ArrayList<Pojo_My_Car_page_package_list> pojo_service_includesArrayList;

    public Adapter_Service_Includes(Context context, ArrayList<Pojo_My_Car_page_package_list> pojo_service_includesArrayList) {
        this.context = context;
        this.pojo_service_includesArrayList = pojo_service_includesArrayList;
    }

    @Override
    public Adapter_Service_Includes.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_service_includes, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Service_Includes.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Pojo_My_Car_page_package_list list =pojo_service_includesArrayList.get(position);
        holder.text_service_name.setText(list.getProduct_name());
        holder.tv_description.setText(list.getDescription());
        holder.tv_exp_on.setText(Common.getDateFromString(list.getValid_to()));
        //holder.image_logo.setImageResource(list.getImage_logo());
        Glide.with(context).load(list.getProduct_icon()).placeholder(R.drawable.service_image).into(holder.image_logo);


    }

    @Override
    public int getItemCount() {
        return pojo_service_includesArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView text_service_name,tv_description,tv_exp_on;
        ImageView image_logo;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            text_service_name = itemView.findViewById(R.id.text_service_name);
            tv_description= itemView.findViewById(R.id.tv_description);
            image_logo = itemView.findViewById(R.id.image_logo);
            tv_exp_on=itemView.findViewById(R.id.tv_exp_on);



        }
    }
}
