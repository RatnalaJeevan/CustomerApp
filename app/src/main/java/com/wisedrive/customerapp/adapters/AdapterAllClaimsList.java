package com.wisedrive.customerapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.ServiceStatus;
import com.wisedrive.customerapp.TrackClaimPage;
import com.wisedrive.customerapp.commonclasses.Common;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.PojoAllClaimList;

import java.util.ArrayList;

public class AdapterAllClaimsList extends  RecyclerView.Adapter<AdapterAllClaimsList.RecyclerViewHolder> {

    ArrayList<PojoAllClaimList> allClaimLists;
    Context context;

    public AdapterAllClaimsList(ArrayList<PojoAllClaimList> allClaimLists, Context context) {
        this.allClaimLists = allClaimLists;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterAllClaimsList.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_all_claimslist, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAllClaimsList.RecyclerViewHolder holder, int position) {

        PojoAllClaimList recyclerdata=allClaimLists.get(position);
        holder.claim_status.setText(recyclerdata.getStatus_name());
        holder.veh_name.setText(recyclerdata.getCar_model());
        holder.veh_no.setText(recyclerdata.getVehicle_no());
        holder.claim_type.setText(recyclerdata.getClaim_type());
        holder.tv_dob.setText(Common.getDateFromString(recyclerdata.getBreakdown_date()));
        holder.tv_pob.setText(recyclerdata.getBreakdown_place());
        holder.tv_claim_req_on.setText(Common.getDateFromString(recyclerdata.getRequested_on()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.claim_id=recyclerdata.getClaim_id();
                SPHelper.claim_code=recyclerdata.getClaim_code();
                SPHelper.veh_name=recyclerdata.getCar_brand()+"-"+recyclerdata.getCar_model();
                SPHelper.claim_type=recyclerdata.getClaim_type();
                Intent intent=new Intent(context, TrackClaimPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allClaimLists.size();
    }

    public class RecyclerViewHolder extends  RecyclerView.ViewHolder{
        TextView veh_name,veh_no,claim_type,tv_claim_req_on,claim_status,tv_dob,tv_pob;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            veh_name=itemView.findViewById(R.id.veh_name);
            veh_no=itemView.findViewById(R.id.veh_no);
            claim_type=itemView.findViewById(R.id.claim_type);
            tv_claim_req_on=itemView.findViewById(R.id.tv_claim_req_on);
            claim_status=itemView.findViewById(R.id.claim_status);
            tv_dob=itemView.findViewById(R.id.tv_dob);
            tv_pob=itemView.findViewById(R.id.tv_pob);
        }
    }
}
