package com.wisedrive.customerapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.InitiateClaim;
import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.PojoAddresses;
import com.wisedrive.customerapp.pojos.PojoClaimTypes;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AdapterClaimtypes extends  RecyclerView.Adapter<AdapterClaimtypes.RecyclerViewHolder>{
    ArrayList<PojoClaimTypes> claimTypes;
    Context context;

    public AdapterClaimtypes(ArrayList<PojoClaimTypes> claimTypes, Context context) {
        this.claimTypes = claimTypes;
        this.context = context;
    }
    @NonNull
    @NotNull
    @Override
    public AdapterClaimtypes.RecyclerViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_claim_type, parent, false);
        return new RecyclerViewHolder(view);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterClaimtypes.RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        PojoClaimTypes recyclerdata=claimTypes.get(position);
        InitiateClaim obj=(InitiateClaim) context;
        holder.claim_type.setText(recyclerdata.getClaim_name());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.claim_type_id=recyclerdata.getClaim_type_id();
                InitiateClaim.getInstance().get_claim_symptoms();
                for (int i = 0; i < claimTypes.size(); i++)
                {
                    if (i == position) {
                        claimTypes.get(i).setIsSelected("y");
                    } else {
                        claimTypes.get(i).setIsSelected("n");
                    }
                }
                obj.adapterClaimtypes.notifyDataSetChanged();
            }
        });
        if (claimTypes.get(position).getIsSelected().equals("y"))
        {
            holder.rl_claim_type.setBackground(context.getDrawable(R.drawable.cardview_dealership));
            holder.claim_type.setTextColor(Color.parseColor("#FFFFFFFF"));
        } else {
            holder.rl_claim_type.setBackground(context.getDrawable(R.drawable.cardview_lightgrey_margined));
            holder.claim_type.setTextColor(Color.parseColor("#FF000000"));
        }
    }

    @Override
    public int getItemCount() {
        return claimTypes.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView claim_type;
        RelativeLayout rl_claim_type;
        public RecyclerViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            claim_type=itemView.findViewById(R.id.claim_type);
            rl_claim_type=itemView.findViewById(R.id.rl_claim_type);
        }
    }
}
