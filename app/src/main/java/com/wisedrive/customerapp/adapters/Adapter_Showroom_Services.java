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

import com.wisedrive.customerapp.Activity_Showroom_Services;
import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.pojos.Pojo_Showroom_services;

import java.util.ArrayList;

public class Adapter_Showroom_Services extends RecyclerView.Adapter< Adapter_Showroom_Services.MyViewHolder> {
    Context context;
    private View view;
    ArrayList<Pojo_Showroom_services> pojo_showroom_servicesArrayList;

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
        Pojo_Showroom_services list = pojo_showroom_servicesArrayList.get(position);
        holder.text_service_name.setText(list.getText_service_name());
        holder.tv_description.setText(list.getTv_description());
        holder.tv_booked_on.setText(list.getTv_booked_on());
        holder.tv_date.setText(list.getTv_date());
        holder.tv_track.setText(list.getTv_track());
        holder.image_logo.setImageResource(list.getImage_logo());
        if ( pojo_showroom_servicesArrayList.get(position).getId().equals("1")) {
            holder.rl_status.setBackgroundColor(ContextCompat.getColor(context, R.color.blue));


        } else if (pojo_showroom_servicesArrayList.get(position).getId().equals("2")) {
            holder.rl_status.setBackgroundColor(ContextCompat.getColor(context, R.color.green));




        }
        holder.rl_book_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity_Showroom_Services)context).rl_transperant.setVisibility(View.VISIBLE);
                ((Activity_Showroom_Services)context).rl_transperant_date_page.setVisibility(View.VISIBLE);
                ((Activity_Showroom_Services)context).rl_select_date.setVisibility(View.VISIBLE);
                ((Activity_Showroom_Services)context).rl_transperant_select_status_page.setVisibility(View.INVISIBLE);
                ((Activity_Showroom_Services)context).rl_track_service_status.setVisibility(View.INVISIBLE);

                holder.tv_track.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((Activity_Showroom_Services)context).rl_transperant.setVisibility(View.VISIBLE);
                        ((Activity_Showroom_Services)context).rl_transperant_date_page.setVisibility(View.INVISIBLE);
                        ((Activity_Showroom_Services)context).rl_select_date.setVisibility(View.INVISIBLE);
                        ((Activity_Showroom_Services)context).rl_transperant_select_status_page.setVisibility(View.VISIBLE);
                        ((Activity_Showroom_Services)context).rl_track_service_status.setVisibility(View.VISIBLE);


                    }
                });


            }
        });
       

    }

    @Override
    public int getItemCount() {
        return pojo_showroom_servicesArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView text_service_name,tv_description,tv_booked_on,tv_date,tv_track;
        ImageView image_logo;
        RelativeLayout rl_book_service,rl_status;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            text_service_name = itemView.findViewById(R.id.text_service_name);
            tv_description= itemView.findViewById(R.id.tv_description);
            tv_booked_on = itemView.findViewById(R.id.tv_booked_on);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_track = itemView.findViewById(R.id.tv_track);
            image_logo = itemView.findViewById(R.id.image_logo);
            rl_book_service=itemView.findViewById(R.id.rl_book_service);
            rl_status=itemView.findViewById(R.id.rl_status);



        }
    }
}
