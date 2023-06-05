package com.wisedrive.customerapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.Activity_Showroom_Services;
import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.PojoAddAdress;
import com.wisedrive.customerapp.pojos.PojoAddresses;

import java.util.ArrayList;

public class AdapterAddAdress extends RecyclerView.Adapter<AdapterAddAdress.RecyclerrViewHolder> {
    ArrayList<PojoAddresses> pojoAddAdresses;
    Context context;

    public AdapterAddAdress(ArrayList<PojoAddresses> pojoAddAdresses, Context context) {
        this.pojoAddAdresses = pojoAddAdresses;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterAddAdress.RecyclerrViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_select_adress, parent,false);
        return new RecyclerrViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAddAdress.RecyclerrViewHolder holder, int position) {
        PojoAddresses recyclerdata=pojoAddAdresses.get(position);
        holder.location.setText(recyclerdata.getAddress_line_1());
        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.customer_selected_address_id=recyclerdata.getId();
                ((Activity_Showroom_Services)context).cust_adress.setText(recyclerdata.getAddress_line_1());
                ((Activity_Showroom_Services)context).cust_location.setText(recyclerdata.getLocation());
                ((Activity_Showroom_Services)context).cust_pincode.setText(recyclerdata.getPincode());
                ((Activity_Showroom_Services)context).rl_select_adress.setVisibility(View.GONE);

            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.customer_selected_address_id=recyclerdata.getId();
                ((Activity_Showroom_Services)context).cust_adress.setText(recyclerdata.getAddress_line_1());
                ((Activity_Showroom_Services)context).cust_location.setText(recyclerdata.getLocation());
                ((Activity_Showroom_Services)context).cust_pincode.setText(recyclerdata.getPincode());
                ((Activity_Showroom_Services)context).rl_select_adress.setVisibility(View.GONE);

            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SPHelper.customer_selected_address_id=recyclerdata.getId();
                ((Activity_Showroom_Services)context).delete_adress();
            }
        });
    }

    @Override
    public int getItemCount() {
        return pojoAddAdresses.size();
    }

    public class RecyclerrViewHolder extends RecyclerView.ViewHolder
    {
        TextView location,edit,select,delete;
        public RecyclerrViewHolder(@NonNull View itemView) {
            super(itemView);
            location=itemView.findViewById(R.id.location);
            select=itemView.findViewById(R.id.select);
            edit=itemView.findViewById(R.id.edit);
            delete=itemView.findViewById(R.id.delete);
        }
    }
}
