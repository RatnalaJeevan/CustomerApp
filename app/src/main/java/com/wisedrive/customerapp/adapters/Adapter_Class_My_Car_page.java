package com.wisedrive.customerapp.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wisedrive.customerapp.CustomerHomepage;
import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.Pojo_Class_Mycar;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Adapter_Class_My_Car_page extends RecyclerView.Adapter<Adapter_Class_My_Car_page.MyViewHolder> {
    private DecimalFormat IndianCurrencyFormat;
    Context context;
    private View view;
    ArrayList<Pojo_Class_Mycar> pojo_class_mycarArrayList;

    public Adapter_Class_My_Car_page(Context context, ArrayList<Pojo_Class_Mycar> pojo_class_mycarArrayList) {
        this.context = context;
        this.pojo_class_mycarArrayList = pojo_class_mycarArrayList;
    }

    @Override
    public Adapter_Class_My_Car_page.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_class_mycar, null);
        // view.getLayoutParams().width = (int) (getScreenWidth()/1.2);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Class_My_Car_page.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Pojo_Class_Mycar list = pojo_class_mycarArrayList.get(position);
        IndianCurrencyFormat = new DecimalFormat("##,##,###");
        Glide.with(context).load(list.getImage()).placeholder(R.drawable.blue_car_image).into(holder.car_image);
        holder.text_make.setText(list.getVehicle_make()+"-");
        holder.text_model.setText(list.getVehicle_model()+"-"+list.getVehicle_no().toUpperCase());
        if(list.getOdometer()==null||list.getOdometer().equals("null")||list.getOdometer().equals("")){
            holder.text_kms.setText("");
        }else {
            holder.text_kms.setText(IndianCurrencyFormat.format(Integer.parseInt(list.getOdometer()))+"\tkms");
        }

        holder.text_transmission.setText(list.getTransmission_type());
        holder.text_fuel_type.setText(list.getFuel_type());
        holder.veh_no.setText(list.getVehicle_no().toUpperCase(Locale.ROOT));
        Glide.with(context).load(list.getBrand_icon()).placeholder(R.drawable.blue_car_image).into(holder.image_my_cars);

        holder.rl_mycar.setBackgroundResource(R.drawable.gradients_mycars);
        holder.rl_mycar.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.car_background));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.fragment_is="cars";
                Intent intent=new Intent(context, CustomerHomepage.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pojo_class_mycarArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView text_make,text_model, text_kms,text_transmission,text_fuel_type,veh_no;
        ImageView car_image,image_kms,image_transmission,image_fuel,image_my_cars;
        RelativeLayout rl_mycar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text_make = itemView.findViewById(R.id.text_make);
            text_model =itemView.findViewById(R.id.text_model);
            text_kms =  itemView.findViewById(R.id.text_kms);
            text_transmission =itemView.findViewById(R.id.text_transmission);
            text_fuel_type=  itemView.findViewById(R.id.text_fuel_type);
            car_image = itemView.findViewById(R.id.car_image);
            image_kms = itemView.findViewById(R.id.image_kms);
            image_transmission = itemView.findViewById(R.id.image_transmission);
            image_fuel=itemView.findViewById(R.id.image_fuel);
            rl_mycar=itemView.findViewById(R.id.rl_mycar);
            veh_no=itemView.findViewById(R.id.veh_no);
            image_my_cars=itemView.findViewById(R.id.image_my_cars);
        }
    }

}
