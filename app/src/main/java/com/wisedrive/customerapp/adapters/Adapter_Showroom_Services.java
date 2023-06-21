package com.wisedrive.customerapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.wisedrive.customerapp.ServiceTrackPage;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.Pojo_Showroom_services;

import java.util.ArrayList;

public class Adapter_Showroom_Services extends RecyclerView.Adapter< Adapter_Showroom_Services.MyViewHolder> {
    Context context;
    private View view;
    ArrayList<Pojo_Showroom_services> pojo_showroom_servicesArrayList;
    public String package_id="";
    public String service_id="", it_is="";
    public  int sel_pos=0;
    public RelativeLayout rl_bottom;
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
       // rl_bottom.setClickable(false);

        holder.text_service_name.setText(recyclerdata.getPackage_name());
        holder.tv_description.setText(recyclerdata.getDescription());
        Glide.with(context).load(recyclerdata.getIcon_url()).placeholder(R.drawable.icon_noimage).into(holder.image_logo);

        if (recyclerdata.getStatus_id().equals(""))
        {
            holder.tv_status.setText("Pending");
            holder. tv_status.setTextColor(Color.parseColor("#5d2de7"));
            if(SPHelper.is_exp.equalsIgnoreCase("y"))
            {
                holder.rl_status.setBackgroundTintList(ContextCompat.getColorStateList(context,R.color.red));
                holder.tv_track.setText("EXPIRED");
            }else {
                holder.rl_status.setBackgroundTintList(ContextCompat.getColorStateList(context,R.color.black));
                holder.tv_track.setText("Book Now");
                holder.arrow.setImageResource(R.drawable.add_white);
            }

        }
        else if(recyclerdata.getStatus_id().equals("7"))
        {
            holder.tv_status.setText(recyclerdata.getStatus_name());
            holder. tv_status.setTextColor(Color.parseColor("#FF3B30"));
            if(SPHelper.is_exp.equalsIgnoreCase("y"))
            {
                holder.rl_status.setBackgroundTintList(ContextCompat.getColorStateList(context,R.color.red));
                holder.tv_track.setText("EXPIRED");
            }else {
                holder.rl_status.setBackgroundTintList(ContextCompat.getColorStateList(context,R.color.black));
                holder.tv_track.setText("Book Now");
                holder.arrow.setImageResource(R.drawable.add_white);
            }

        }
        else  {

            holder.rl_status.setBackgroundTintList(ContextCompat.getColorStateList(context,R.color.dar_or));
            holder.tv_status.setText(recyclerdata.getStatus_name());
            holder. tv_status.setTextColor(Color.parseColor("#008000"));
            holder.tv_track.setText("Track");
            holder.arrow.setImageResource(R.drawable.d_arrow_white);
        }



        holder.rl_status.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                package_id=recyclerdata.getPackage_id();
                SPHelper.ser_pac_id=recyclerdata.getPackage_id();
                if ( pojo_showroom_servicesArrayList.get(position).getStatus_id().equals("")||
                        recyclerdata.getStatus_id().equals("7"))
                {

                    if(recyclerdata.getService_id()==null||recyclerdata.getService_id().equals("null")||
                    recyclerdata.getService_id().equals("")){
                        service_id="";
                        SPHelper.service_id="";
                    }
                    else{
                        service_id=recyclerdata.getService_id();
                        SPHelper.service_id=recyclerdata.getService_id();
                    }
                    if(SPHelper.is_exp.equalsIgnoreCase("y"))
                    {

                    }else
                    {

                            SPHelper.server_date="";
                            it_is = "book";
                            ((Activity_Showroom_Services)context).cust_adress.setText("");
                            ((Activity_Showroom_Services)context).cust_location.setText("");
                            ((Activity_Showroom_Services)context).cust_pincode.setText("");
                            ((Activity_Showroom_Services)context).getSSLists();

                    }
                } else  {
                    SPHelper.ser_status_id=recyclerdata.getStatus_id();
                    SPHelper.service_id=recyclerdata.getService_id();
                    Intent intent=new Intent(context,ServiceTrackPage.class) ;
                    context.startActivity(intent);
                    ((Activity_Showroom_Services) context).finish();
                    //((Activity_Showroom_Services)context).get_track_service();
                   // ((Activity_Showroom_Services)context).rl_select_dates.setVisibility(View.GONE);
                   // ((Activity_Showroom_Services)context).rl_track_service_status.setVisibility(View.VISIBLE);
                }
            }
        });

        rl_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                package_id=recyclerdata.getPackage_id();
                SPHelper.package_name=recyclerdata.getPackage_name();
                it_is="service";
                ((Activity_Showroom_Services)context).getSSLists();
             }
        });
    }


    @Override
    public int getItemCount() {
        return pojo_showroom_servicesArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView text_service_name,tv_description,tv_booked_on,tv_status,tv_track;
        ImageView image_logo,arrow;
        RelativeLayout rl_status;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            text_service_name = itemView.findViewById(R.id.text_service_name);
            tv_description= itemView.findViewById(R.id.tv_description);
           // tv_booked_on = itemView.findViewById(R.id.tv_booked_on);
            tv_status = itemView.findViewById(R.id.tv_status);
            tv_track = itemView.findViewById(R.id.tv_track);
            image_logo = itemView.findViewById(R.id.image_logo);

            rl_status=itemView.findViewById(R.id.rl_status);
            arrow=itemView.findViewById(R.id.arrow);
            rl_bottom=itemView.findViewById(R.id.rl_bottom);
        }
    }
}
