package com.wisedrive.customerapp.adapters;

import static com.wisedrive.customerapp.R.drawable.cardview_lightgrey_margined;
import static com.wisedrive.customerapp.R.drawable.cardview_lightorange_margined;
import static com.wisedrive.customerapp.R.drawable.rounded_background;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.PopupSelectPack;
import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.Warranty_Description;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.PojoPlansList;

import java.util.ArrayList;

public class AdapterCPlansList extends RecyclerView.Adapter<AdapterCPlansList.MyViewHolder>{

    ArrayList<PojoPlansList> pojoPlansLists;
    Activity context;
    public int selectedPosition = 0;

    public PopupSelectPack bottomSheetFragment;

    public AdapterCPlansList(PopupSelectPack bottomSheetFragment) {
        this.bottomSheetFragment = bottomSheetFragment;
    }
    public AdapterCPlansList(ArrayList<PojoPlansList> pojoPlansLists, Activity context) {
        this.pojoPlansLists = pojoPlansLists;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterCPlansList.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_cu_plans_list, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCPlansList.MyViewHolder holder, int position) {
       // PojoPlansList data=pojoPlansLists.get(position);
       // holder.text_plans.setText(pojoPlansLists.get(position).getPlan_name());
        if (selectedPosition == position) {
            holder.rl_plan.setBackground(context.getDrawable(rounded_background));
            holder.rl_plan.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.black));
            holder.text_plans.setTextColor(ContextCompat.getColorStateList(context, R.color.white));
        }
        else {
            holder.rl_plan.setBackground(context.getDrawable(rounded_background));
            holder.rl_plan.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.background));
            ContextCompat.getColorStateList(context, R.color.lightgrey);
            holder.text_plans.setTextColor(ContextCompat.getColorStateList(context, R.color.cement));
            //holder.rl_combo_plans.setBackground(context.getDrawable(cardview_lightgrey_margined));
        }
        holder.text_plans.setText("Plan "+String.valueOf(position+1));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // SPHelper.plan_id=data.getPlan_id();
                if (selectedPosition >= 0)
                    notifyItemChanged(selectedPosition);
                selectedPosition = holder.getAdapterPosition();
                notifyItemChanged(selectedPosition);
                PopupSelectPack.getInstance().get_specific_pro_list();
            }
        });
    }

    @Override
    public int getItemCount() {
        return SPHelper.plan_size;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text_plans;
        RelativeLayout rl_plan;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text_plans=itemView.findViewById(R.id.text_plans);
            rl_plan=itemView.findViewById(R.id.rl_plan);
        }
    }
}
