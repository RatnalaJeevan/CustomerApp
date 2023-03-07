package com.wisedrive.customerapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.Warranty_Description;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.Pojo_Additional_Services;
import com.wisedrive.customerapp.pojos.Pojo_Combo_Plans;

import java.util.ArrayList;

public class Adapter_Additional_services extends RecyclerView.Adapter<Adapter_Additional_services.MyViewHolder> {
    Context context;
    private View view;
    ArrayList<Pojo_Additional_Services> pojoAdditionalServicesArrayList;
    private int selectedPosition = 0;

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
        Pojo_Additional_Services recyclerdata = pojoAdditionalServicesArrayList.get(position);
        holder.tv_additional_service_plan.setText(recyclerdata.getProduct_name());
        holder.expires_on.setText("Validity:\t"+recyclerdata.getValidity());
       // holder.image_logo.setImageResource(list.getImage_logo());
        Glide.with(context).load(recyclerdata.getProduct_icon()).placeholder(R.drawable.icon_noimage).into(holder.image_logo);

        if (selectedPosition == position) {

            holder. select.setBackgroundResource(R.drawable.select_back);
            holder.select.setTextColor(Color.parseColor("#FFFFFFFF"));

        } else {

            holder. select.setBackgroundResource(R.drawable.select_white);
            holder.select.setTextColor(Color.parseColor("#6A5FF4"));
        }
        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPHelper.product_id=recyclerdata.getProduct_id();
                if (selectedPosition >= 0)
                    notifyItemChanged(selectedPosition);
                selectedPosition = holder.getAdapterPosition();
                notifyItemChanged(selectedPosition);
                Warranty_Description.getInstance().get_pack_description();
            }
        });
    }

    @Override
    public int getItemCount() {
        return pojoAdditionalServicesArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_additional_service_plan,expires_on;
        ImageView image_logo;
        AppCompatButton select;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_additional_service_plan = itemView.findViewById(R.id.tv_additional_service_plan);
            image_logo = itemView.findViewById(R.id.image_logo);
            select = itemView.findViewById(R.id. select);
            expires_on=itemView.findViewById(R.id.expires_on);



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
