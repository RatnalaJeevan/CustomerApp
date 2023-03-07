package com.wisedrive.customerapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.pojos.PojoServices;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AdapterServiceStatus extends  RecyclerView.Adapter<AdapterServiceStatus.RecyclerViewHolder>{
    ArrayList<PojoServices> servicelist;
    Context context;

    public AdapterServiceStatus(ArrayList<PojoServices> servicelist, Context context) {
        this.servicelist = servicelist;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public AdapterServiceStatus.RecyclerViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_all_services,parent,false);
        //view.getLayoutParams().width = (int) (getScreenWidth() / 1.1);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterServiceStatus.RecyclerViewHolder holder, int position) {

        PojoServices recyclerdata=servicelist.get(position);
        holder.activity_name.setText(recyclerdata.getActivity_name());

    }

    @Override
    public int getItemCount() {
        return servicelist.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView activity_name;
        public RecyclerViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            activity_name=itemView.findViewById(R.id.tv_activity);
        }
    }
}
