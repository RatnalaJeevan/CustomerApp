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
import com.wisedrive.customerapp.pojos.PojoServiceDetails;

import java.util.ArrayList;

public class AdapterServiceDetails extends RecyclerView.Adapter<AdapterServiceDetails.RecyclerViewHolder> {
    ArrayList<PojoServiceDetails> serviceDetails;
    Context context;

    public AdapterServiceDetails(ArrayList<PojoServiceDetails> serviceDetails, Context context) {
        this.serviceDetails = serviceDetails;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterServiceDetails.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_service_details,  parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterServiceDetails.RecyclerViewHolder holder, int position) {
        PojoServiceDetails recyclerdata=serviceDetails.get(position);
        holder.tv_general_service_name.setText(recyclerdata.getPackage_name());
        holder.tv_general_service_description.setText(recyclerdata.getPackage_description());
        Glide.with(context).load(recyclerdata.getIcon_url()).placeholder(R.drawable.icon_noimage).into(holder.image_1);


    }

    @Override
    public int getItemCount() {
        return serviceDetails.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView tv_general_service_name,tv_general_service_description;
        ImageView image_1;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_general_service_description=itemView.findViewById(R.id.tv_general_service_description);
            tv_general_service_name=itemView.findViewById(R.id.tv_general_service_name);
            image_1=itemView.findViewById(R.id.image_1);
        }
    }
}
