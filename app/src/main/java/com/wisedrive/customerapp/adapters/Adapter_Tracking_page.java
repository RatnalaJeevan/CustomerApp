package com.wisedrive.customerapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
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
        holder.tv_status.setText(list.getStatus_name());
        holder.tv_request_description.setText(list.getDescription());
        holder.tv_date.setText(list.getService_flow_date());

        Glide.with(context).load(list.getIcon()).placeholder(R.drawable.icon_noimage).into(holder.imv_request);

       if(list.getOtp()==null||list.getOtp().equals("null")||list.getOtp().equals("")){
           holder.rl_otp.setVisibility(View.GONE);
           holder.view_request.setVisibility(View.VISIBLE);
           holder.view_request1.setVisibility(View.INVISIBLE);
       }else{
           holder.view_request.setVisibility(View.INVISIBLE);
           holder.view_request1.setVisibility(View.VISIBLE);
           holder.rl_otp.setVisibility(View.VISIBLE);
           holder.tv_otp.setText(list.getOtp());
       }

        if(position==pojo_tracking_pageArrayList.size()-1){
            holder.view_request.setVisibility(View.INVISIBLE);
            holder.view_request1.setVisibility(View.INVISIBLE);
        }else{
            holder.view_request.setVisibility(View.VISIBLE);
            holder.view_request1.setVisibility(View.VISIBLE);
        }
       if(list.getIs_active().equalsIgnoreCase("y")){
           if(list.getService_status_id().equals("8")){
               holder. tv_status.setTextColor(Color.parseColor("#FF3B30"));
               holder. tv_request_description.setTextColor(Color.parseColor("#FF3B30"));
               holder. tv_date.setTextColor(Color.parseColor("#FF3B30"));
               holder.view_request.setBackgroundResource(R.color.red_1);
               holder.view_request1.setBackgroundResource(R.color.red_1);

           }else{
               holder. tv_status.setTextColor(Color.parseColor("#008000"));
               holder. tv_request_description.setTextColor(Color.parseColor("#008000"));
               holder. tv_date.setTextColor(Color.parseColor("#008000"));
               holder.view_request.setBackgroundResource(R.color.green);
               holder.view_request1.setBackgroundResource(R.color.green);
           }

       }else{
           holder.  tv_status.setTextColor(Color.parseColor("#aeaeb2"));
           holder. tv_request_description.setTextColor(Color.parseColor("#aeaeb2"));
           holder. tv_date.setTextColor(Color.parseColor("#aeaeb2"));
           holder.view_request.setBackgroundResource(R.color.middle_gray);
           holder.view_request1.setBackgroundResource(R.color.green);
       }

    }

    @Override
    public int getItemCount() {
        return  pojo_tracking_pageArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_status,tv_request_description,tv_date,tv_otp;
        ImageView imv_request;
        View view_request,view_request1;
        RelativeLayout rl_otp;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_status= itemView.findViewById(R.id.tv_status);
            tv_request_description= itemView.findViewById(R.id.tv_request_description);
            tv_date= itemView.findViewById(R.id.tv_date);
            imv_request= itemView.findViewById(R.id.imv_request);
            view_request=itemView.findViewById(R.id.view_request);
            view_request1=itemView.findViewById(R.id.view_request1);
            tv_otp=itemView.findViewById(R.id.tv_otp);
            rl_otp=itemView.findViewById(R.id.rl_otp);
        }
    }
}
