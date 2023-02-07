package com.wisedrive.customerapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.PrepaidServices;
import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.VehiclePackageDetails;
import com.wisedrive.customerapp.pojos.PojoPeriodicMaintenanceServices;
import com.wisedrive.customerapp.pojos.PojoPrepaidServices;

import java.util.ArrayList;

public class AdapterPrePaidServices extends  RecyclerView.Adapter<AdapterPrePaidServices.RecyclerViewHolder>{
    @NonNull
    ArrayList<PojoPrepaidServices> prepaidserviceslist;
    Context context;

    public AdapterPrePaidServices(@NonNull ArrayList<PojoPrepaidServices> prepaidserviceslist, Context context) {
        this.prepaidserviceslist = prepaidserviceslist;
        this.context = context;
    }

    @Override
    public AdapterPrePaidServices.RecyclerViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_prepaid_services, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  AdapterPrePaidServices.RecyclerViewHolder holder, int position) {
        PojoPrepaidServices recyclerdata=prepaidserviceslist.get(position);
        holder.tv_services.setText(recyclerdata.getPart_name());
        holder.partno.setText(recyclerdata.getPart_no());
    }

    @Override
    public int getItemCount() {
        return prepaidserviceslist.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_services,partno;
        ImageView iv1;
        public RecyclerViewHolder(@NonNull  View itemView) {
            super(itemView);
            partno=itemView.findViewById(R.id.partno);
            tv_services=itemView.findViewById(R.id.tv_services);
            iv1=itemView.findViewById(R.id.iv1);
        }
    }
}
