package com.wisedrive.customerapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.pojos.Pojo_Select_Date;
import com.wisedrive.customerapp.pojos.Pojo_Tracking_page;

import java.util.ArrayList;

public class Adapter_Tracking_page extends RecyclerView.Adapter<Adapter_Tracking_page.MyViewHolder> {
    Context context;
    private View view;
    ArrayList<Pojo_Tracking_page> pojo_tracking_pageArrayList;

    public Adapter_Tracking_page(Context context, ArrayList<Pojo_Tracking_page>  pojo_tracking_pageArrayList) {
        this.context = context;
        this. pojo_tracking_pageArrayList= pojo_tracking_pageArrayList;
    }

    @Override
    public Adapter_Tracking_page.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_track_page, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Tracking_page.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Pojo_Tracking_page list =  pojo_tracking_pageArrayList.get(position);
        holder.tv_request.setText(list.getTv_request());
        holder.tv_request_description.setText(list.getTv_request_description());
        holder.tv_date.setText(list.getTv_date());
        holder.imv_request.setImageResource(list.getImv_request());
        holder.tv_otp.setText(list.getTv_otp());
        if (pojo_tracking_pageArrayList.get(position).getId().equals("1")) {
            holder.  tv_request.setTextColor(Color.parseColor("#008000"));
            holder. tv_request_description.setTextColor(Color.parseColor("#008000"));
            holder. tv_date.setTextColor(Color.parseColor("#008000"));
            holder.view_request.setBackgroundResource(R.color.green);




        } else if (pojo_tracking_pageArrayList.get(position).getId().equals("2")) {
            holder.  tv_request.setTextColor(Color.parseColor("#008000"));
            holder. tv_request_description.setTextColor(Color.parseColor("#008000"));
            holder. tv_date.setTextColor(Color.parseColor("#008000"));
            holder.view_request.setBackgroundResource(R.color.green);

        } else if (pojo_tracking_pageArrayList.get(position).getId().equals("3")) {
            holder.  tv_request.setTextColor(Color.parseColor("#f68b33"));
            holder. tv_request_description.setTextColor(Color.parseColor("#f68b33"));
            holder. tv_date.setTextColor(Color.parseColor("#f68b33"));
            holder.view_request.setBackgroundResource(R.color.orange);

        } else if (pojo_tracking_pageArrayList.get(position).getId().equals("4")) {
            holder.  tv_request.setTextColor(Color.parseColor("#aeaeb2"));
            holder. tv_request_description.setTextColor(Color.parseColor("#aeaeb2"));
            holder. tv_date.setTextColor(Color.parseColor("#aeaeb2"));
            holder.view_request.setBackgroundResource(R.color.middle_gray);






        }






    }

    @Override
    public int getItemCount() {
        return  pojo_tracking_pageArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_request,tv_request_description,tv_date,tv_otp;
        ImageView imv_request;
        View view_request;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_request= itemView.findViewById(R.id.tv_request);
            tv_request_description= itemView.findViewById(R.id.tv_request_description);
            tv_date= itemView.findViewById(R.id.tv_date);
            imv_request= itemView.findViewById(R.id.imv_request);
            view_request=itemView.findViewById(R.id.view_request);
            tv_otp=itemView.findViewById(R.id.tv_otp);

        }
    }
}
