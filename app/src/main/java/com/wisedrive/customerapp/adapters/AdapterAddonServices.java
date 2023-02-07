package com.wisedrive.customerapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.AddOnServices;
import com.wisedrive.customerapp.PrepaidServices;
import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.pojos.PojoAddOnServices;
import com.wisedrive.customerapp.pojos.PojoPrepaidServices;

import java.util.ArrayList;

public class AdapterAddonServices extends  RecyclerView.Adapter<AdapterAddonServices.RecyclerViewHolder>{
    ArrayList<PojoAddOnServices> addOnServiceslist;
    Context context;

    public AdapterAddonServices(ArrayList<PojoAddOnServices> addOnServiceslist, Context context) {
        this.addOnServiceslist = addOnServiceslist;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterAddonServices.RecyclerViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_addonservices, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  AdapterAddonServices.RecyclerViewHolder holder, int position) {

        AddOnServices obj=(AddOnServices) context;
        PojoAddOnServices recyclerdata=addOnServiceslist.get(position);
        holder.tv_services.setText(recyclerdata.getPart_name());
        holder.partno.setText(recyclerdata.getPart_no());
        holder.part_amount_paid.setText(recyclerdata.getFinal_amount());
    }

    @Override
    public int getItemCount() {
        return addOnServiceslist.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView iv1;
        TextView tv_services,partno,part_amount_paid;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            partno=itemView.findViewById(R.id.partno);
            tv_services=itemView.findViewById(R.id.tv_services);
            part_amount_paid=itemView.findViewById(R.id.part_amount_paid);
            iv1=itemView.findViewById(R.id.iv1);
        }
    }
}
