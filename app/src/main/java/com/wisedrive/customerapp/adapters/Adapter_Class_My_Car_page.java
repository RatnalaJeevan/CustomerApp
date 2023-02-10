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
import com.wisedrive.customerapp.pojos.Pojo_Class_Mycar;

import java.util.ArrayList;

public class Adapter_Class_My_Car_page extends RecyclerView.Adapter<Adapter_Class_My_Car_page.MyViewHolder> {
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
        holder.text_make.setText(list.getText_make());
        holder.text_model.setText(list.getText_model());
        holder.text_kms.setText(list.getText_kms());
        holder.text_transmission.setText(list.getText_transmission());
        holder.text_fuel_type.setText(list.getText_fuel_type());
        holder.car_image.setImageResource(list.getCar_image());
        holder.image_kms.setImageResource(list.getImage_kms());
        holder.image_transmission.setImageResource(list.getImage_transmission());
        holder.image_fuel.setImageResource(list.getImage_fuel());

    }

    @Override
    public int getItemCount() {
        return pojo_class_mycarArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView text_make,text_model, text_kms,text_transmission,text_fuel_type;
        ImageView car_image,image_kms,image_transmission,image_fuel;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text_make = itemView.findViewById(R.id.text_make);
            text_model = (TextView) itemView.findViewById(R.id.text_model);
            text_kms = (TextView) itemView.findViewById(R.id.text_kms);
            text_transmission = (TextView) itemView.findViewById(R.id.text_transmission);
            text_fuel_type= (TextView) itemView.findViewById(R.id.text_fuel_type);
            car_image = itemView.findViewById(R.id.car_image);
            image_kms = itemView.findViewById(R.id.image_kms);
            image_transmission = itemView.findViewById(R.id.image_transmission);
            image_fuel=itemView.findViewById(R.id.image_fuel);


        }
    }

}
