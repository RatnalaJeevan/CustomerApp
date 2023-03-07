package com.wisedrive.customerapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.commonclasses.Common;
import com.wisedrive.customerapp.pojos.PojoTrackClaims;
import java.util.ArrayList;

public class AdapterTrackClaimList extends RecyclerView.Adapter<AdapterTrackClaimList.MyViewHolder>{

    ArrayList<PojoTrackClaims> claims;
    Context context;

    public AdapterTrackClaimList(ArrayList<PojoTrackClaims> claims, Context context) {
        this.claims = claims;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterTrackClaimList.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_track_page, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTrackClaimList.MyViewHolder holder, int position) {
        PojoTrackClaims list=claims.get(position);
        holder.tv_status.setText(list.getStatus());
        holder.tv_request_description.setText(list.getDescription());
        holder.tv_date.setText(Common.getDateFromString(list.getCreated_on_date()));

        if(list.getOtp()==null||list.getOtp().equals("null")||list.getOtp().equals("")){
            holder.rl_otp.setVisibility(View.GONE);
            holder.view_request.setVisibility(View.VISIBLE);
            holder.view_request1.setVisibility(View.INVISIBLE);
        }else{

            holder.view_request.setVisibility(View.INVISIBLE);
            holder.view_request1.setVisibility(View.VISIBLE);
            holder.rl_otp.setVisibility(View.VISIBLE);
            holder.tv_otp.setText(list.getOtp());
        }
        if(position==claims.size()-1){
            holder.view_request.setVisibility(View.INVISIBLE);
            holder.view_request1.setVisibility(View.INVISIBLE);
        }else{
            holder.view_request.setVisibility(View.VISIBLE);
            holder.view_request1.setVisibility(View.VISIBLE);
        }

        if(list.getStatus_id().equals("17")||list.getStatus().equals("18")||list.getStatus_id().equals("19")){
            holder.view_request1.setBackgroundResource(R.color.red_1);
            holder.tv_date.setTextColor(Color.parseColor("#FF3B30"));
            holder.tv_status.setTextColor(Color.parseColor("#FF3B30"));
            holder.tv_request_description.setTextColor(Color.parseColor("#FF3B30"));
            holder.view_request.setBackgroundResource(R.color.red_1);
        }else{
            holder.view_request1.setBackgroundResource(R.color.green);
            holder.view_request.setBackgroundResource(R.color.green);
            holder.tv_date.setTextColor(Color.parseColor("#008000"));
            holder.tv_status.setTextColor(Color.parseColor("#008000"));
            holder.tv_request_description.setTextColor(Color.parseColor("#008000"));

        }
    }

    @Override
    public int getItemCount() {
        return claims.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_status,tv_date,tv_otp,tv_request_description;
        View    view_request,view_request1;
        RelativeLayout rl_otp;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_status=itemView.findViewById(R.id.tv_status);
            tv_date=itemView.findViewById(R.id.tv_date);
            tv_otp=itemView.findViewById(R.id.tv_otp);
            view_request=itemView.findViewById(R.id.view_request);
            view_request1=itemView.findViewById(R.id.view_request1);
            tv_request_description=itemView.findViewById(R.id.tv_request_description);
            rl_otp=itemView.findViewById(R.id.rl_otp);
        }
    }
}
