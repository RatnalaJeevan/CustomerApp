package com.wisedrive.customerapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.pojos.PojoPaidAddonList;

import java.util.ArrayList;

public class AdapterPaidAddonList extends RecyclerView.Adapter<AdapterPaidAddonList.MyViewHolder> {
    Context context;
    private View view;
    ArrayList<PojoPaidAddonList> pojoServiceListArrayList;

    public AdapterPaidAddonList(Context context, ArrayList<PojoPaidAddonList> pojoServiceListArrayList) {
        this.context = context;
        this.view = view;
        this.pojoServiceListArrayList = pojoServiceListArrayList;
    }

    @Override
    public AdapterPaidAddonList.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_paid_addon_list, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPaidAddonList.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        PojoPaidAddonList list = pojoServiceListArrayList.get(position);
        holder.tv_servicename.setText(list.getAddon_name());


    }

    @Override
    public int getItemCount() {
        return pojoServiceListArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_servicename;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_servicename = itemView.findViewById(R.id.tv_servicename);


        }
    }
}
