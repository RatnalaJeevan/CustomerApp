package com.wisedrive.customerapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.pojos.PojoSpecificPacList;
import com.wisedrive.customerapp.pojos.PojoWarrantyList;

import java.util.ArrayList;

public class AdapterWarrantyList extends  RecyclerView.Adapter<AdapterWarrantyList.RecyclerViewHolder>{

    ArrayList<PojoSpecificPacList> pojoSpecificPacLists;
    Context context;

    public AdapterWarrantyList(ArrayList<PojoSpecificPacList> pojoWarrantyLists, Context context) {
        this.pojoSpecificPacLists = pojoWarrantyLists;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterWarrantyList.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_warranty_list,parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterWarrantyList.RecyclerViewHolder holder, int position) {
        PojoSpecificPacList data=pojoSpecificPacLists.get(position);
        holder.validity.setText(data.getValidity());
        holder.pack_name.setText(pojoSpecificPacLists.get(position).getProduct_name());


    }

    @Override
    public int getItemCount() {
        return pojoSpecificPacLists.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView pack_name,validity;
        ImageView iv2;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            pack_name=itemView.findViewById(R.id.pack_name);
            validity=itemView.findViewById(R.id.validity);
            iv2=itemView.findViewById(R.id.iv2);
        }
    }
}
