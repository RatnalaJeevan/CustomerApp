package com.wisedrive.customerapp.adapters;

import static com.wisedrive.customerapp.R.drawable.cardview_lightgrey_margined;
import static com.wisedrive.customerapp.R.drawable.cardview_lightorange_margined;
import static com.wisedrive.customerapp.R.drawable.submit_cardview;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
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
       // view.getLayoutParams().width = (int) (getScreenWidth()/2);
        if(pojoAdditionalServicesArrayList.size()>1){
            view.getLayoutParams().width = (int) (getScreenWidth()/1.5);
        }
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Additional_services.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Pojo_Additional_Services recyclerdata = pojoAdditionalServicesArrayList.get(position);
        holder.tv_additional_service_plan.setText(recyclerdata.getProduct_name());
        holder.expires_on.setText("Validity:\t"+recyclerdata.getValidity());

        Glide.with(context).load(recyclerdata.getProduct_icon()).placeholder(R.drawable.service_image).into(holder.image_logo);


        if (selectedPosition == position) {
            holder.rl_combo_plans.setBackground(context.getDrawable(cardview_lightorange_margined));
        }
        else {
            holder.rl_combo_plans.setBackground(context.getDrawable(cardview_lightgrey_margined));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                SPHelper.product_id=recyclerdata.getProduct_id();
                if (selectedPosition >= 0)
                    notifyItemChanged(selectedPosition);
                selectedPosition = holder.getAdapterPosition();
                notifyItemChanged(selectedPosition);
                SPHelper.product_name=recyclerdata.getProduct_name();
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
        RelativeLayout rl_combo_plans;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_additional_service_plan = itemView.findViewById(R.id.tv_additional_service_plan);
            image_logo = itemView.findViewById(R.id.image_logo);
            select = itemView.findViewById(R.id. select);
            expires_on=itemView.findViewById(R.id.expires_on);
            rl_combo_plans=itemView.findViewById(R.id.rl_combo_plans);
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
