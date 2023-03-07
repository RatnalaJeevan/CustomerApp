package com.wisedrive.customerapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.ScheduleService;
import com.wisedrive.customerapp.SelectAddress;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.PojoAddresses;
import com.wisedrive.customerapp.pojos.PojoPackageList;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AdapterCustomerAdresses  extends  RecyclerView.Adapter<AdapterCustomerAdresses.RecyclerViewHolder>{

    ArrayList<PojoAddresses> addresslist;
    Context context;
    public  String city_name,state_name,selected_pincode;

    public AdapterCustomerAdresses(ArrayList<PojoAddresses> addresslist, Context context) {
        this.addresslist = addresslist;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public AdapterCustomerAdresses.RecyclerViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_addresses, parent, false);
        return new RecyclerViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterCustomerAdresses.RecyclerViewHolder holder, int position) {

        PojoAddresses recyclerdata=addresslist.get(position);
        SelectAddress obj=(SelectAddress)context;

        if(recyclerdata.getLandmark()==null|| recyclerdata.getLandmark().equals("")){
            recyclerdata.setLandmark("");
            holder.tv_adresses.setText(recyclerdata.getAddress_line_1()+","+recyclerdata.getAddress_line_2()+","
                    +recyclerdata.getPincode()+","+"\n"+recyclerdata.getCity()+","+recyclerdata.getState());
        }
        if(recyclerdata.getAddress_line_2()==null|| recyclerdata.getAddress_line_2().equals("")){
            holder.tv_adresses.setText(recyclerdata.getAddress_line_1()+","+recyclerdata.getLandmark()
                    +","+recyclerdata.getPincode()+","+"\n"+recyclerdata.getCity()+","+recyclerdata.getState());
        }
        else{
            holder.tv_adresses.setText(recyclerdata.getAddress_line_1()+","+recyclerdata.getAddress_line_2()+","+recyclerdata.getLandmark()
                    +","+recyclerdata.getPincode()+","+"\n"+recyclerdata.getCity()+","+recyclerdata.getState());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected_pincode=recyclerdata.getPincode();
                SPHelper.customer_selected_pincod=selected_pincode;
                SPHelper.customer_selected_address_id=recyclerdata.getId();
                SPHelper.get_customer_address=recyclerdata.getAddress_line_1()+","+recyclerdata.getAddress_line_2()+","+recyclerdata.getLandmark()
                        +","+recyclerdata.getPincode()+","+"\n"+recyclerdata.getCity()+","+recyclerdata.getState();
                //obj.check_address();
                Intent intent=new Intent(context, ScheduleService.class);
                SPHelper.is_serving="yes";
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return addresslist.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView tv_adresses;
        public RecyclerViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tv_adresses=itemView.findViewById(R.id.tv_adresses);
        }
    }

}
