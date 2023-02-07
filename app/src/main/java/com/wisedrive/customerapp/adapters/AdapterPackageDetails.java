package com.wisedrive.customerapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.ScheduleService;
import com.wisedrive.customerapp.ServiceStatus;
import com.wisedrive.customerapp.ServiceCompletedPage;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.PojoPackageList;

import java.util.ArrayList;

public class AdapterPackageDetails extends  RecyclerView.Adapter<AdapterPackageDetails.RecyclerViewHolder>{
    ArrayList<PojoPackageList> pmpserviceslist;
    Context context;

    public AdapterPackageDetails(ArrayList<PojoPackageList> pmpserviceslist, Context context) {
        this.pmpserviceslist = pmpserviceslist;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_periodic_maintenance, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //VehiclePackageDetails obj=(VehiclePackageDetails) context;
        PojoPackageList recyclerdata=pmpserviceslist.get(position);
        holder.package_name.setText(recyclerdata.getPackage_name());
        if(recyclerdata.getStatus_id()==null ||recyclerdata.getStatus_id().equals("") ||
                recyclerdata.getStatus_id().equals("7")){
            holder.status_url.setImageResource(R.drawable.add_service_icon);
        }else if(recyclerdata.getStatus_id().equals("4")){
            holder.status_url.setImageResource(R.drawable.check_service_completed);
        }else{
            holder.status_url.setImageResource(R.drawable.service_in_progress);
        }
        if (recyclerdata.getIcon_url() != null && !recyclerdata.getIcon_url().isEmpty() && !recyclerdata.getIcon_url().equals("null")) {
            Glide.with(context).load(recyclerdata.getIcon_url()).placeholder(R.drawable.icon_noimage).into(holder.icon_url);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.package_name=recyclerdata.getPackage_name();
                if( recyclerdata.getStatus_id()==null ||recyclerdata.getStatus_id().equals("")||
                        recyclerdata.getStatus_id().equals("7"))
                {
                    SPHelper.package_id=recyclerdata.getPackage_id();
                    if(recyclerdata.getService_id()==null){
                        SPHelper.service_id="";
                    }else{
                        SPHelper.service_id=recyclerdata.getService_id();
                    }
                    SPHelper.is_serving="";
                    SPHelper.from="";
                    Intent intent2=new Intent(context.getApplicationContext(), ScheduleService.class);
                    intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent2);
                }
                else if(recyclerdata.getStatus_id().equals("4")){
                    SPHelper.package_id=recyclerdata.getPackage_id();
                    SPHelper.service_id=recyclerdata.getService_id();
                    SPHelper.by="";
                    Intent intent2=new Intent(context.getApplicationContext(), ServiceCompletedPage.class);
                    intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent2);
                }
                else{
                        SPHelper.package_id=recyclerdata.getPackage_id();
                        SPHelper.service_id=recyclerdata.getService_id();
                        SPHelper.by="";
                    Intent intent=new Intent(context.getApplicationContext(), ServiceStatus.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return pmpserviceslist.size();
    }

    public class RecyclerViewHolder  extends RecyclerView.ViewHolder {
        ImageView icon_url,status_url;
        TextView package_name;
        public RecyclerViewHolder(@NonNull  View itemView) {
            super(itemView);
            icon_url=itemView.findViewById(R.id.icon_url);
            status_url=itemView.findViewById(R.id.status_url);
            package_name=itemView.findViewById(R.id.package_name);
        }
    }
}
