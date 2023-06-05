package com.wisedrive.customerapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.CustomerHomepage;
import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.PojoEWPRc;

import java.util.ArrayList;

public class AdapterEWPRCList extends RecyclerView.Adapter<AdapterEWPRCList.MyViewHolder>{

    ArrayList<PojoEWPRc> pojoEWPRcs;
    Context context;

    public AdapterEWPRCList(ArrayList<PojoEWPRc> pojoEWPRcs, Context context) {
        this.pojoEWPRcs = pojoEWPRcs;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterEWPRCList.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_recommended_list, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterEWPRCList.MyViewHolder holder, int position) {

       // holder.label3.setText(pojoEWPRcs.get(position).getRecommendation());
        holder.label3.setText(pojoEWPRcs.get(position).getVehicle_no());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.comingfrom="";
                SPHelper.fragment_is="cars";
                Intent intent=new Intent(view.getContext(), CustomerHomepage.class);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pojoEWPRcs.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView  label3;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            label3=itemView.findViewById(R.id.label3);
        }
    }
}
