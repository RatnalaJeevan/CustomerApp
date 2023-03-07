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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.PojoVehDetails;
import com.wisedrive.customerapp.pojos.Pojo_Class_Mycar;
import com.wisedrive.customerapp.pojos.Pojo_Class_Plan_2;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Adapter_class_mycar extends PagerAdapter {
    private DecimalFormat IndianCurrencyFormat;
    Context context;
    private View view;
    ArrayList<Pojo_Class_Mycar> pojo_class_mycarArrayList;
    TextView text_make,text_model, text_kms,text_transmission,text_fuel_type;
    ImageView car_image,image_kms,image_transmission,image_fuel;
    public Adapter_class_mycar(Context context, ArrayList<Pojo_Class_Mycar> pojo_class_mycarArrayList) {
        this.context = context;
        this.pojo_class_mycarArrayList = pojo_class_mycarArrayList;
    }


    @Override
    public int getCount() {
        return pojo_class_mycarArrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ( object);
    }

    @Override
    public void destroyItem(ViewGroup itemView, int position, Object view) {
        itemView.removeView((View) view);
    }
    @SuppressLint("SetTextI18n")
    @NonNull
    @NotNull
    @Override
    public Object instantiateItem(@NonNull @NotNull ViewGroup view, int position) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.items_class_mycar, view, false);
        IndianCurrencyFormat = new DecimalFormat("##,##,###");
        text_make = itemView.findViewById(R.id.text_make);
        text_model =  itemView.findViewById(R.id.text_model);
        text_kms =  itemView.findViewById(R.id.text_kms);
        text_transmission =  itemView.findViewById(R.id.text_transmission);
        text_fuel_type=  itemView.findViewById(R.id.text_fuel_type);
        car_image = itemView.findViewById(R.id.car_image);
        image_kms = itemView.findViewById(R.id.image_kms);
        image_transmission = itemView.findViewById(R.id.image_transmission);
        image_fuel=itemView.findViewById(R.id.image_fuel);

        Pojo_Class_Mycar list = pojo_class_mycarArrayList.get(position);
        text_make.setText(list.getVehicle_make()+"-");
        text_model.setText(list.getVehicle_model()+"-"+list.getVehicle_no().toUpperCase());
        text_kms.setText(IndianCurrencyFormat.format(Integer.parseInt(list.getOdometer()))+"\tkms");
        text_transmission.setText(list.getTransmission_type());
        text_fuel_type.setText(list.getFuel_type());
        Glide.with(context).load(list.getImage()).placeholder(R.drawable.blue_car_image).into(car_image);


        // car_image.setImageResource(list.getCar_image());
        //image_kms.setImageResource(list.getK());
        //image_transmission.setImageResource(list.getImage_transmission());
       // image_fuel.setImageResource(list.getImage_fuel());
        view.addView(itemView);
        return itemView;
    }



}