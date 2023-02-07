package com.wisedrive.customerapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.microsoft.azure.storage.ServiceStats;
import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.ServiceCompletedPage;
import com.wisedrive.customerapp.ServiceStatus;
import com.wisedrive.customerapp.commonclasses.Common;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.PojoAddServiceList;

import java.util.ArrayList;

public class AdapterAddServiceList extends  RecyclerView.Adapter<AdapterAddServiceList.RecyclerViewHolder>{

    ArrayList<PojoAddServiceList> addServiceLists;
    public Context context;

    public AdapterAddServiceList(ArrayList<PojoAddServiceList> addServiceLists, Context context) {
        this.addServiceLists = addServiceLists;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterAddServiceList.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_add_services_list, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAddServiceList.RecyclerViewHolder holder, int position) {
        PojoAddServiceList recyclerdata=addServiceLists.get(position);
        holder.package_name.setText(recyclerdata.getPackage_name());
        if(recyclerdata.getStatus_id().equals("4")){
            holder.iv_arrow.setImageResource(R.drawable.check_service_completed);
        }else if(recyclerdata.getStatus_id()==null ||recyclerdata.getStatus_id().equals("")){
            holder.iv_arrow.setImageResource(R.drawable.add_service_icon);
        }
        else if(recyclerdata.getStatus_id().equals("7")){
            holder.iv_arrow.setImageResource(R.drawable.cross_red);
        }else{
            holder.iv_arrow.setImageResource(R.drawable.service_in_progress);
        }
        holder.tv_service_on.setText(Common.getDateFromString(recyclerdata.getService_on_date()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.by="add";
                SPHelper.package_name=recyclerdata.getPackage_name();
                SPHelper.service_id=recyclerdata.getService_id();
                if(recyclerdata.getStatus_id().equals("4")){
                    Intent intent=new Intent(context, ServiceCompletedPage.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }else if(recyclerdata.getStatus_id()==null ||recyclerdata.getStatus_id().equals("")){
                    Intent intent=new Intent(context, ServiceStatus.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }else{
                    Intent intent=new Intent(context, ServiceStatus.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return addServiceLists.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder
    {
        TextView package_name,tv_model,tv_service_on;
        ImageView iv_arrow;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            package_name=itemView.findViewById(R.id.package_name);
            tv_model=itemView.findViewById(R.id.tv_model);
            tv_service_on=itemView.findViewById(R.id.tv_service_on);
            iv_arrow=itemView.findViewById(R.id.iv_arrow);
        }
    }
}
