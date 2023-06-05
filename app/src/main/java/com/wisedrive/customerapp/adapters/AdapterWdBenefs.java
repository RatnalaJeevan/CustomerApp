package com.wisedrive.customerapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.commonclasses.ResponseModel;

import java.util.ArrayList;

public class AdapterWdBenefs extends RecyclerView.Adapter<AdapterWdBenefs.RecyclerVieewHolder>
{
    ArrayList<ResponseModel.PojoWarrantyBenefits> pojoWarrantyBenefits;
    Context context;

    public AdapterWdBenefs(ArrayList<ResponseModel.PojoWarrantyBenefits> pojoWarrantyBenefits, Context context) {
        this.pojoWarrantyBenefits = pojoWarrantyBenefits;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterWdBenefs.RecyclerVieewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_wd_benefit_list,  parent,false);
        return new RecyclerVieewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterWdBenefs.RecyclerVieewHolder holder, int position) {
        ResponseModel.PojoWarrantyBenefits data=pojoWarrantyBenefits.get(position);
        holder.tv5.setText(data.getName());
        holder.tv6.setText(data.getDescription());
    }

    @Override
    public int getItemCount() {
        return pojoWarrantyBenefits.size();
    }

    public class RecyclerVieewHolder extends RecyclerView.ViewHolder {
        TextView tv5,tv6;
        public RecyclerVieewHolder(@NonNull View itemView) {
            super(itemView);
            tv6=itemView.findViewById(R.id.tv6);
            tv5=itemView.findViewById(R.id.tv5);
        }
    }
}
