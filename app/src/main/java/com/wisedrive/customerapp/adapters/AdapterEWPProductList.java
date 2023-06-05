package com.wisedrive.customerapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.pojos.PojoEWPProducts;

import java.util.ArrayList;

public class AdapterEWPProductList extends RecyclerView.Adapter<AdapterEWPProductList.MyViewHolder>{

    ArrayList<PojoEWPProducts> pojoEWPProducts;
    Context context;
    public AdapterEWPProductList(ArrayList<PojoEWPProducts> pojoEWPProducts, Context context) {
        this.pojoEWPProducts = pojoEWPProducts;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterEWPProductList.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_combo_products,  parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterEWPProductList.MyViewHolder holder, int position) {
        holder.tv_comprehensive_warranty.setText(pojoEWPProducts.get(position).getP_name()+"\nValidity: 1 year");

    }

    @Override
    public int getItemCount() {
        return pojoEWPProducts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_comprehensive_warranty;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_comprehensive_warranty=itemView.findViewById(R.id.tv_comprehensive_warranty);
        }
    }
}
