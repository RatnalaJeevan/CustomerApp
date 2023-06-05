package com.wisedrive.customerapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.PojoSelAddOnn;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdapterSelAddons extends RecyclerView.Adapter<AdapterSelAddons.RecyclerViewHolder> {
    private DecimalFormat IndianCurrencyFormat;
    ArrayList<PojoSelAddOnn> selAddOnns;
    Context context;

    public AdapterSelAddons(ArrayList<PojoSelAddOnn> selAddOnns, Context context) {
        this.selAddOnns = selAddOnns;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterSelAddons.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View  view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_sel_add_onlist, parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSelAddons.RecyclerViewHolder holder, int position) {
        IndianCurrencyFormat = new DecimalFormat("##,##,###");
        PojoSelAddOnn data=selAddOnns.get(position);
        holder.tv_sst.setText(data.getAdd_on_naame());
        holder.tv_add3.setText(IndianCurrencyFormat.format((int) data.getAmount()));
       // SPHelper.add_on_amount=0;



    }

    @Override
    public int getItemCount() {
        return selAddOnns.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView tv_sst,tv_add3;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_add3=itemView.findViewById(R.id.tv_add3);
            tv_sst=itemView.findViewById(R.id.tv_sst);
        }
    }
}
