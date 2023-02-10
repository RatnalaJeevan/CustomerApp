package com.wisedrive.customerapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.Activity_Showroom_Services;
import com.wisedrive.customerapp.Comprehensive_Warranty;
import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.pojos.Pojo_My_Car_page_package_list;

import java.util.ArrayList;

public class Adapter_My_Car_Page_Package_list extends RecyclerView.Adapter<Adapter_My_Car_Page_Package_list.MyViewHolder> {
    Context context;
    private View view;
    ArrayList<Pojo_My_Car_page_package_list> pojo_my_car_warranty_listArrayList;

    public Adapter_My_Car_Page_Package_list(Context context, ArrayList<Pojo_My_Car_page_package_list> pojo_my_car_warranty_listArrayList) {
        this.context = context;
        this.pojo_my_car_warranty_listArrayList = pojo_my_car_warranty_listArrayList;
    }

    @Override
    public Adapter_My_Car_Page_Package_list.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_my_car_package_list, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_My_Car_Page_Package_list.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Pojo_My_Car_page_package_list list = pojo_my_car_warranty_listArrayList.get(position);
        holder.text_warranty_name.setText(list.getText_warranty_name());
        holder.tv_description.setText(list.getTv_description());
        holder.image_logo.setImageResource(list.getImage_logo());
        holder.right_icon .setImageResource(list.getRight_icon());
        //  if ( pojo_my_car_warranty_listArrayList.get(position)) {
        if (pojo_my_car_warranty_listArrayList.get(position).getId().equals("1")) {
            holder.relative_layout_services.setBackgroundResource(R.drawable.blue_background);
            holder. text_warranty_name.setTextColor(Color.parseColor("#0619c3"));
            holder.tv_description.setTextColor(Color.parseColor("#0619c3"));



        } else if (pojo_my_car_warranty_listArrayList.get(position).getId().equals("2")) {
            holder.relative_layout_services.setBackgroundResource(R.drawable.green_back);
            holder. text_warranty_name.setTextColor(Color.parseColor("#4bae4f"));
            holder. tv_description.setTextColor(Color.parseColor("#4bae4f"));




        } else if (pojo_my_car_warranty_listArrayList.get(position).getId().equals("3")) {
            holder.relative_layout_services.setBackgroundResource(R.drawable.orange_background);
           holder. text_warranty_name.setTextColor(Color.parseColor("#ff6739"));
            holder. tv_description.setTextColor(Color.parseColor("#ff6739"));



        }
        holder.relative_layout_services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent;
                switch ( position){
                    case 0:
                        intent =  new Intent(context, Comprehensive_Warranty.class);
                        break;

                    case 1:
                        intent =  new Intent(context, Activity_Showroom_Services.class);
                        break;

                    default:
                        intent =  new Intent(context, Activity_Showroom_Services.class);
                        break;
                }
                context.startActivity(intent);
            }


        });


    }

    @Override
    public int getItemCount() {
        return pojo_my_car_warranty_listArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView text_warranty_name,tv_description;
        ImageView image_logo,right_icon;
        RelativeLayout relative_layout_services;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();

            text_warranty_name = itemView.findViewById(R.id.text_warranty_name);
            tv_description= itemView.findViewById(R.id.tv_description);
            image_logo = itemView.findViewById(R.id.image_logo);
            right_icon = itemView.findViewById(R.id.right_icon);
            relative_layout_services= (RelativeLayout) itemView.findViewById(R.id.relative_layout_services);


        }
    }
}
