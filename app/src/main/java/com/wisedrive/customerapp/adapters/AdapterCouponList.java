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

import com.wisedrive.customerapp.Addons;
import com.wisedrive.customerapp.ApplyCouponList;
import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.Upgrade_and_Save;
import com.wisedrive.customerapp.commonclasses.Common;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.PojoCouponList;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdapterCouponList extends RecyclerView.Adapter<AdapterCouponList.MyViewHolder>{

    private DecimalFormat IndianCurrencyFormat;
    ArrayList<PojoCouponList> couponLists;
    Context context;

    public AdapterCouponList(ArrayList<PojoCouponList> couponLists, Context context) {
        this.couponLists = couponLists;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterCouponList.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_coupon_list, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCouponList.MyViewHolder holder, int position) {
        IndianCurrencyFormat = new DecimalFormat("##,##,###");
        PojoCouponList recyclerdata=couponLists.get(position);
        holder.coupon_code.setText(recyclerdata.getCoupon_code().toUpperCase());
        holder.description.setText(recyclerdata.getDescription());
        holder.valid_days.setText("Valid :"+ Common.getDateFromString(recyclerdata.getExpiration_date()));
        holder.per_off.setText("INR \t"+IndianCurrencyFormat.format((int)recyclerdata.getDiscount_amount()));

        if(recyclerdata.getIs_valid().equalsIgnoreCase("n")){
            holder.rl_apply.setVisibility(View.INVISIBLE);
            holder.rl_not_apply.setVisibility(View.VISIBLE);
        }else{
            holder.rl_apply.setVisibility(View.VISIBLE);
            holder.rl_not_apply.setVisibility(View.INVISIBLE);
        }
        holder.rl_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.coupon_code=recyclerdata.getCoupon_code();
                SPHelper.coupon_id=recyclerdata.getCoupon_id();
                SPHelper.coupon_type= recyclerdata.getCoupon_type();
                SPHelper.disc_amount=recyclerdata.getDiscount_amount();
                if(((ApplyCouponList)context).coming_from.equals("addon")){
                    Intent intent=new Intent(context, Addons.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }else{
                    Intent intent=new Intent(context, Upgrade_and_Save.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return couponLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView coupon_code,description,valid_days,per_off;
        RelativeLayout rl_not_apply,rl_apply;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            coupon_code=itemView.findViewById(R.id.coupon_code);
            description=itemView.findViewById(R.id.description);
            valid_days=itemView.findViewById(R.id.valid_days);
            per_off=itemView.findViewById(R.id.per_off);
            rl_not_apply=itemView.findViewById(R.id.rl_not_apply);
            rl_apply=itemView.findViewById(R.id.rl_apply);
        }
    }
}
