package com.wisedrive.customerapp.adapters;

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
import com.wisedrive.customerapp.pojos.PojoVehDetails;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AdapterCustomerVehicleList  extends  RecyclerView.Adapter<AdapterCustomerVehicleList.RecyclerViewHolder>{
    ArrayList<PojoVehDetails> vehdetailslist;
    Context context;

    public AdapterCustomerVehicleList(ArrayList<PojoVehDetails> vehdetailslist, Context context) {
        this.vehdetailslist = vehdetailslist;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public AdapterCustomerVehicleList.RecyclerViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_vehicle_imagelists,parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterCustomerVehicleList.RecyclerViewHolder holder, int position)
    {
        PojoVehDetails recyclerdata=vehdetailslist.get(position);
        holder.carname.setText(recyclerdata.getVehicle_model());
            Glide.with(context).load(recyclerdata.getBrand_icon()).placeholder(R.drawable.icon_noimage).into(holder.car_image);
    }

    @Override
    public int getItemCount() {
        return vehdetailslist.size();
    }

    public class RecyclerViewHolder extends  RecyclerView.ViewHolder {
        ImageView car_image;
        TextView carname;
        public RecyclerViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            carname=itemView.findViewById(R.id.carname);
            car_image=itemView.findViewById(R.id.car_image);
        }
    }
}
