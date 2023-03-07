package com.wisedrive.customerapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.Comprehensive_Warranty;
import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.PojoNewClaimList;

import java.util.ArrayList;

public class AdapterClaimList extends  RecyclerView.Adapter<AdapterClaimList.RecyclerViewHolder>{
    ArrayList<PojoNewClaimList> pojoNewClaimLists;
    Context context;

    public AdapterClaimList(ArrayList<PojoNewClaimList> pojoNewClaimLists, Context context) {
        this.pojoNewClaimLists = pojoNewClaimLists;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterClaimList.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_new_claim_list, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterClaimList.RecyclerViewHolder holder, int position) {
        PojoNewClaimList recyclerdata=pojoNewClaimLists.get(position);
        holder.claim_type.setText(recyclerdata.getClaim_name());
        holder.tv_status.setText(recyclerdata.getStatus_name());
        holder.veh_make_model.setText(recyclerdata.getVehicle_make()+"-"+recyclerdata.getVehicle_model());
        holder.veh_no.setText(recyclerdata.getVehicle_no());

         if(recyclerdata.getStatus_id().equals("17")||recyclerdata.getStatus_id().equals("18")||
         recyclerdata.getStatus_id().equals("19")){
            holder.rl_status.setBackgroundColor(ContextCompat.getColor(context, R.color.red_1));
        }
        else  {
            holder.rl_status.setBackgroundColor(ContextCompat.getColor(context, R.color.dark_green));
        }
        holder.rl_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.claim_id=recyclerdata.getClaim_id();
                ((Comprehensive_Warranty)context).get_track_list();
                ((Comprehensive_Warranty)context).rl_track_claim_status.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pojoNewClaimLists.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView veh_make_model,veh_no,claim_type,tv_status;
        RelativeLayout rl_status;
        public RecyclerViewHolder(@NonNull View itemView) {

            super(itemView);
            veh_make_model=itemView.findViewById(R.id.veh_make_model);
            veh_no=itemView.findViewById(R.id.veh_no);
            claim_type=itemView.findViewById(R.id.claim_type);
            tv_status=itemView.findViewById(R.id.tv_status);
            rl_status=itemView.findViewById(R.id.rl_status);
        }
    }
}
