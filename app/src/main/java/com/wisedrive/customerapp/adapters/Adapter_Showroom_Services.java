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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wisedrive.customerapp.Activity_Showroom_Services;
import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.Pojo_Showroom_services;

import java.util.ArrayList;

public class Adapter_Showroom_Services extends RecyclerView.Adapter< Adapter_Showroom_Services.MyViewHolder> {
    Context context;
    private View view;
    ArrayList<Pojo_Showroom_services> pojo_showroom_servicesArrayList;
    public String package_id="";
    public String service_id="";

    public Adapter_Showroom_Services(Context context, ArrayList<Pojo_Showroom_services>  pojo_showroom_servicesArrayList) {
        this.context = context;
        this. pojo_showroom_servicesArrayList =  pojo_showroom_servicesArrayList;
    }

    @Override
    public Adapter_Showroom_Services.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_showroom_services, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Showroom_Services.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Pojo_Showroom_services recyclerdata = pojo_showroom_servicesArrayList.get(position);

        holder.text_service_name.setText(recyclerdata.getPackage_name());
        holder.tv_description.setText(recyclerdata.getDescription());
        Glide.with(context).load(recyclerdata.getIcon_url()).placeholder(R.drawable.blue_car_image).into(holder.image_logo);

        if (recyclerdata.getStatus_id().equals("")) {
            holder.rl_status.setBackgroundColor(ContextCompat.getColor(context, R.color.blue));
            holder.rl_book_service.setVisibility(View.GONE);
            holder.tv_track.setText("BOOK NOW");
        }
        else if(recyclerdata.getStatus_id().equals("7")){
            holder.rl_status.setBackgroundColor(ContextCompat.getColor(context, R.color.red_1));
            holder.rl_book_service.setVisibility(View.VISIBLE);
            holder.tv_status.setText(recyclerdata.getStatus_name());
            holder.tv_track.setText("BOOK NOW");
        }
        else  {
            holder.rl_status.setBackgroundColor(ContextCompat.getColor(context, R.color.dark_green));
            holder.rl_book_service.setVisibility(View.VISIBLE);
            holder.tv_status.setText(recyclerdata.getStatus_name());
            holder.tv_track.setText("TRACK SERVICE");
        }

        if(SPHelper.is_exp.equalsIgnoreCase("y")){
            holder.rl_status.setBackgroundColor(ContextCompat.getColor(context, R.color.text_color));
            holder.rl_book_service.setVisibility(View.VISIBLE);
            holder.tv_status.setText(recyclerdata.getStatus_name());
            holder.tv_track.setText("BOOK NOW");
        }
        holder.rl_status.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if ( pojo_showroom_servicesArrayList.get(position).getStatus_id().equals("")||
                        recyclerdata.getStatus_id().equals("7")) {
                    package_id=recyclerdata.getPackage_id();

                    if(recyclerdata.getService_id()==null||recyclerdata.getService_id().equals("null")||
                    recyclerdata.getService_id().equals("")){
                        service_id="";
                    }else{
                        service_id=recyclerdata.getService_id();
                    }
                    if(SPHelper.is_exp.equalsIgnoreCase("y")){

                    }else {
                        ((Activity_Showroom_Services)context).getDateLists();
                        ((Activity_Showroom_Services)context).rl_select_dates.setVisibility(View.VISIBLE);
                        ((Activity_Showroom_Services)context).rl_track_service_status.setVisibility(View.GONE);
                    }


                } else  {
                    SPHelper.service_id=recyclerdata.getService_id();
                    ((Activity_Showroom_Services)context).get_track_service();
                    ((Activity_Showroom_Services)context).rl_select_dates.setVisibility(View.GONE);
                    ((Activity_Showroom_Services)context).rl_track_service_status.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return pojo_showroom_servicesArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView text_service_name,tv_description,tv_booked_on,tv_status,tv_track;
        ImageView image_logo;
        RelativeLayout rl_book_service,rl_status;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            text_service_name = itemView.findViewById(R.id.text_service_name);
            tv_description= itemView.findViewById(R.id.tv_description);
            tv_booked_on = itemView.findViewById(R.id.tv_booked_on);
            tv_status = itemView.findViewById(R.id.tv_status);
            tv_track = itemView.findViewById(R.id.tv_track);
            image_logo = itemView.findViewById(R.id.image_logo);
            rl_book_service=itemView.findViewById(R.id.rl_book_service);
            rl_status=itemView.findViewById(R.id.rl_status);



        }
    }
}
