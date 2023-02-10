package com.wisedrive.customerapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.pojos.Pojo_Activate_Confirmation_Page;

import java.util.ArrayList;

public class Adapter_Activate_Confirmation_Page extends RecyclerView.Adapter<Adapter_Activate_Confirmation_Page.MyViewHolder> {
    Context context;
    private View view;
    ArrayList<Pojo_Activate_Confirmation_Page> pojo_activate_confirmation_pageArrayList;

    public Adapter_Activate_Confirmation_Page(Context context, ArrayList<Pojo_Activate_Confirmation_Page> pojo_activate_confirmation_pageArrayList) {
        this.context = context;
        this.pojo_activate_confirmation_pageArrayList = pojo_activate_confirmation_pageArrayList;
    }

    @Override
    public Adapter_Activate_Confirmation_Page.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_cofirmation, null);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Activate_Confirmation_Page.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Pojo_Activate_Confirmation_Page list = pojo_activate_confirmation_pageArrayList.get(position);
        holder.imv_image.setImageResource(list.getImv_image());
        holder. tv_warranty_name.setText(list.getTv_warranty_name());
        holder.tv_expire_date.setText(list.getTv_expire_date());


            }




    @Override
    public int getItemCount() {
        return pojo_activate_confirmation_pageArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imv_image;
        TextView tv_warranty_name,tv_expire_date;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imv_image  = itemView.findViewById(R.id.imv_image);
            tv_warranty_name = (TextView) itemView.findViewById(R.id.tv_warranty_name);
            tv_expire_date = (TextView) itemView.findViewById(R.id.tv_expire_date);



        }

    }



    }

