package com.wisedrive.customerapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.ScheduleService;
import com.wisedrive.customerapp.TrackImages;
import com.wisedrive.customerapp.pojos.PojoAllClaimList;
import com.wisedrive.customerapp.pojos.PojoTrackClaimList;

import java.util.ArrayList;

public class AdapterTrackClaimPage extends  RecyclerView.Adapter<AdapterTrackClaimPage.RecyclerViewHolder>{

    ArrayList<PojoTrackClaimList> trackClaimLists;
    Context context;

    public AdapterTrackClaimPage(ArrayList<PojoTrackClaimList> trackClaimLists, Context context) {
        this.trackClaimLists = trackClaimLists;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterTrackClaimPage.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_track_claim, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTrackClaimPage.RecyclerViewHolder holder, int position) {
        PojoTrackClaimList recyclerdata=trackClaimLists.get(position);
        holder.status.setText(recyclerdata.getStatus_name());
        holder.status_date.setText(recyclerdata.getDate_1());
        if(trackClaimLists.size()<=1){
            holder.line1.setVisibility(View.GONE);
        }else{
            if(position==trackClaimLists.size()-1){
                holder.line1.setVisibility(View.GONE);
            }else{
                holder.line1.setVisibility(View.VISIBLE);
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2=new Intent(context.getApplicationContext(), TrackImages.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent2);
            }
        });
    }

    @Override
    public int getItemCount() {
        return trackClaimLists.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView status,status_date;
        RelativeLayout rl_stage1;
        View line1;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            status=itemView.findViewById(R.id.status);
            status_date=itemView.findViewById(R.id.status_date);
            rl_stage1=itemView.findViewById(R.id.rl_stage1);
            line1=itemView.findViewById(R.id.line1);

        }
    }
}
