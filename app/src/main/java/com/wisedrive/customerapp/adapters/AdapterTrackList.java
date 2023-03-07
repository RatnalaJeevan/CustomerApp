package com.wisedrive.customerapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.pojos.PojoAddOnServices;
import com.wisedrive.customerapp.pojos.PojoServiceflow;

import java.util.ArrayList;

public class AdapterTrackList  extends  RecyclerView.Adapter<AdapterTrackList.RecyclerViewHolder>{
    int size=0;
    ArrayList<PojoServiceflow> trackLists;
    Context context;

    public AdapterTrackList(ArrayList<PojoServiceflow> trackLists, Context context) {
        this.trackLists = trackLists;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterTrackList.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_track_list, parent,
                false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTrackList.RecyclerViewHolder holder, int position) {
        holder.status.setText(trackLists.get(position).getStatus_name());
        holder.status_date.setText(trackLists.get(position).getService_date());
        size=trackLists.size();
        if(size<=1){
            holder.line1.setVisibility(View.GONE);
        }else{
            if(position==size-1){
                holder.line1.setVisibility(View.GONE);
            }else{
                holder.line1.setVisibility(View.VISIBLE);
            }
        }

        if(trackLists.get(position).getStatus_name().equalsIgnoreCase("requested")){
            holder.line1.setBackground(context.getDrawable(R.color.blue));
            holder.rl_stage1.setBackground(context.getDrawable(R.drawable.circle_orange));
            holder.rl_stage1.setBackgroundTintList(ContextCompat.getColorStateList(context.getApplicationContext(), R.color.blue));
        }else if(trackLists.get(position).getStatus_name().equalsIgnoreCase("in progress")){
            holder.line1.setBackground(context.getDrawable(R.color.orange));
            holder.rl_stage1.setBackground(context.getDrawable(R.drawable.circle_orange));

        }else if(trackLists.get(position).getStatus_name().equalsIgnoreCase("cancelled")){
            holder.line1.setBackground(context.getDrawable(R.color.red));
            holder.rl_stage1.setBackground(context.getDrawable(R.drawable.circle_orange));
            holder.rl_stage1.setBackgroundTintList(ContextCompat.getColorStateList(context.getApplicationContext(), R.color.red));

        }else if(trackLists.get(position).getStatus_name().equalsIgnoreCase("delivered")){
            holder.line1.setBackground(context.getDrawable(R.color.green));
            holder.rl_stage1.setBackground(context.getDrawable(R.drawable.circle_orange));
            holder.rl_stage1.setBackgroundTintList(ContextCompat.getColorStateList(context.getApplicationContext(), R.color.green));

        }else{
            holder.line1.setBackground(context.getDrawable(R.color.blue));
            holder.rl_stage1.setBackground(context.getDrawable(R.drawable.circle_orange));
            holder.rl_stage1.setBackgroundTintList(ContextCompat.getColorStateList(context.getApplicationContext(), R.color.blue));
        }
    }

    @Override
    public int getItemCount() {
        return trackLists.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView status,status_date;
        View line1;
        RelativeLayout rl_stage1;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            status=itemView.findViewById(R.id.status);
            status_date=itemView.findViewById(R.id.status_date);
            line1=itemView.findViewById(R.id.line1);
            rl_stage1=itemView.findViewById(R.id.rl_stage1);
        }
    }
}
