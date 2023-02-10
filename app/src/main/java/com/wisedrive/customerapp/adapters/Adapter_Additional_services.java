package com.wisedrive.customerapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.pojos.Pojo_Additional_Services;
import com.wisedrive.customerapp.pojos.Pojo_Combo_Plans;

import java.util.ArrayList;

public class Adapter_Additional_services extends RecyclerView.Adapter<Adapter_Additional_services.MyViewHolder> {
    Context context;
    private View view;
    ArrayList<Pojo_Additional_Services> pojoAdditionalServicesArrayList;

    public Adapter_Additional_services(Context context, ArrayList<Pojo_Additional_Services> pojoAdditionalServicesArrayList) {
        this.context = context;
        this.pojoAdditionalServicesArrayList=pojoAdditionalServicesArrayList;
    }

    @Override
    public Adapter_Additional_services.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_additional_services, parent,false);
        view.getLayoutParams().width = (int) (getScreenWidth()/2);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Additional_services.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Pojo_Additional_Services list = pojoAdditionalServicesArrayList.get(position);
        holder.tv_additional_service_plan.setText(list.getTv_additional_service_plan());
        holder.image_logo.setImageResource(list.getImage_logo());


    }

    @Override
    public int getItemCount() {
        return pojoAdditionalServicesArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_additional_service_plan;
        ImageView image_logo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_additional_service_plan = itemView.findViewById(R.id.tv_additional_service_plan);
            image_logo = itemView.findViewById(R.id.image_logo);


        }
    }
    public int getScreenWidth() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;

    }



}
