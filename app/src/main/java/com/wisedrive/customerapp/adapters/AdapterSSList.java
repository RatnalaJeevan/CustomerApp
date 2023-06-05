package com.wisedrive.customerapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.pojos.Pojo_Service_Includes;

import java.util.ArrayList;

public class AdapterSSList extends RecyclerView.Adapter<AdapterSSList.RecyclerViewHolder>
{
    ArrayList<Pojo_Service_Includes> pojo_service_includes;
    Context context;

    public AdapterSSList(ArrayList<Pojo_Service_Includes> pojo_service_includes, Context context) {
        this.pojo_service_includes = pojo_service_includes;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterSSList.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_ss_list, null);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSSList.RecyclerViewHolder holder, int position) {
        Pojo_Service_Includes data=pojo_service_includes.get(position);
        if(data.getActivity_name()==null){
            holder.tv_service.setText(data.getProduct_name());
        }else {
            holder.tv_service.setText(data.getActivity_name());
        }

    }

    @Override
    public int getItemCount() {
        return pojo_service_includes.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView tv_service;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_service=itemView.findViewById(R.id.tv_service);
        }
    }
}
