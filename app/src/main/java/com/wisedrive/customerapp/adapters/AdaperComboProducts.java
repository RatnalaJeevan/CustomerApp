package com.wisedrive.customerapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.pojos.PojoComboProducts;

import java.util.ArrayList;

public class AdaperComboProducts extends RecyclerView.Adapter<AdaperComboProducts.RecyclerViewHolder> {

    ArrayList<PojoComboProducts> comboProducts;
    Context context;

    public AdaperComboProducts(ArrayList<PojoComboProducts> comboProducts, Context context) {
        this.comboProducts = comboProducts;
        this.context = context;
    }

    @NonNull
    @Override
    public AdaperComboProducts.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_combo_products,  parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaperComboProducts.RecyclerViewHolder holder, int position) {
        PojoComboProducts recyclerdata=comboProducts.get(position);

        holder.tv_comprehensive_warranty.setText(recyclerdata.getProduct_name());
        holder.tv_comprehensive_warranty_description.setText(recyclerdata.getDescription());
        holder.validity.setText(recyclerdata.getValidity());
    }

    @Override
    public int getItemCount() {
        return comboProducts.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView tv_comprehensive_warranty,tv_comprehensive_warranty_description,validity;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_comprehensive_warranty_description=itemView.findViewById(R.id.tv_comprehensive_warranty_description);
            tv_comprehensive_warranty=itemView.findViewById(R.id.tv_comprehensive_warranty);
            validity=itemView.findViewById(R.id.validity);
        }
    }
}
