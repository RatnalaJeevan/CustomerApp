package com.wisedrive.customerapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wisedrive.customerapp.AddYourCar;
import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.Pojo_Select_Make_list;

import java.util.ArrayList;

public class Adapter_select_make extends RecyclerView.Adapter<Adapter_select_make.MyViewHolder> {

    private AddYourCar myFragment;


    private int selectedPosition = -1;

    private View view;
    ArrayList<Pojo_Select_Make_list> pojo_select_make_listArrayList;

    public Adapter_select_make(AddYourCar myFragment, ArrayList<Pojo_Select_Make_list> pojo_select_make_listArrayList) {
        this.myFragment = myFragment;
        this.view = view;
        this.pojo_select_make_listArrayList = pojo_select_make_listArrayList;
    }

    public Adapter_select_make.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_select_make, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_select_make.MyViewHolder holder,final int position) {
        Pojo_Select_Make_list recyclerdata = pojo_select_make_listArrayList.get(position);
        Glide.with(myFragment).load(recyclerdata.getBrand_icon()).placeholder(R.drawable.icon_noimage).into(holder.make_logo);
        holder.brand_name.setText(recyclerdata.getCar_brand());
        if (selectedPosition == position) {
//            holder.make_logo.setSelected(true); //using selector drawable
//            holder.rl_select_make.setBackgroundResource(R.drawable.make_background_bg);

        } else {
//            holder.make_logo.setSelected(false);
//            holder.rl_select_make.setBackgroundResource(R.drawable.make_background_white);

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // holder.rl_select_make.setBackgroundResource(R.drawable.make_background_bg);
                SPHelper.carbrandid=recyclerdata.getId();
                SPHelper.brand_name=recyclerdata.getCar_brand();
                if (selectedPosition >= 0)
                    notifyItemChanged(selectedPosition);
                selectedPosition = holder.getAdapterPosition();
                notifyItemChanged(selectedPosition);
                myFragment.get_carmodel_list();
            }
        });

    }

    @Override
    public int getItemCount() {
        return pojo_select_make_listArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView make_logo;
        RelativeLayout rl_select_make;
        TextView brand_name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            make_logo = itemView.findViewById(R.id.make_logo);
            rl_select_make=(RelativeLayout)view.findViewById(R.id.rl_select_make);
            brand_name=itemView.findViewById(R.id.brand_name);
        }
    }
}






