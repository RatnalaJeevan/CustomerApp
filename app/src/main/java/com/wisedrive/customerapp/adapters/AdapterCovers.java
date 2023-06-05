package com.wisedrive.customerapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.pojos.PojoCovers;

import java.util.ArrayList;

public class AdapterCovers extends RecyclerView.Adapter<AdapterCovers.RecyclerViewHolder> {

    ArrayList<PojoCovers> pojoCovers;
    Context context;

    public AdapterCovers(ArrayList<PojoCovers> pojoCovers, Context context) {
        this.pojoCovers = pojoCovers;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterCovers.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ittems_partial_pay_list, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCovers.RecyclerViewHolder holder, int position) {
        PojoCovers data= pojoCovers.get(position);
        holder.tv_covers.setText(data.getProduct_name());
    }

    @Override
    public int getItemCount() {
        return pojoCovers.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView tv_covers;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_covers=itemView.findViewById(R.id.tv_covers);
        }
    }
}
