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
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.wisedrive.customerapp.PopUpUpdateKms;
import com.wisedrive.customerapp.PopupWDBenefits;
import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.Pojo_Class_Mycar;
import com.wisedrive.customerapp.pojos.Pojo_Class_Plan_2;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Adapter_class_mycar extends PagerAdapter {
    RelativeLayout rl_mycar,rl_update;
    private DecimalFormat IndianCurrencyFormat;
    Context context;
    private View view;
    ArrayList<Pojo_Class_Mycar> pojo_class_mycarArrayList;
    TextView text_make,tv_vehno, position_car,text_transmission,text_fuel_type;
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
    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    @NonNull
    @NotNull
    @Override
    public Object instantiateItem(@NonNull @NotNull ViewGroup view, int position)
    {
        View itemView = LayoutInflater.from(context).inflate(R.layout.items_cars_list, view, false);
        IndianCurrencyFormat = new DecimalFormat("##,##,###");
        text_make = itemView.findViewById(R.id.text_make);
        tv_vehno =  itemView.findViewById(R.id.tv_vehno);
        position_car =  itemView.findViewById(R.id.position);
        text_transmission =  itemView.findViewById(R.id.text_transmission);
        text_fuel_type=  itemView.findViewById(R.id.text_fuel_type);
        car_image = itemView.findViewById(R.id.car_image);
        rl_update=itemView.findViewById(R.id.rl_update);


        Pojo_Class_Mycar list = pojo_class_mycarArrayList.get(position);

        if(pojo_class_mycarArrayList.size()>1){
           position_car.setVisibility(View.VISIBLE);
        }else {
            position_car.setVisibility(View.GONE);
        }
        text_make.setText(list.getVehicle_model()+"-"+list.getFuel_type());
        tv_vehno.setText(list.getVehicle_no().toUpperCase());
        text_transmission.setText(list.getTransmission_type());
        if(list.getOdometer()==null||list.getOdometer().equals("null")){
            text_fuel_type.setText("");
        }else {
            text_fuel_type.setText(IndianCurrencyFormat.format(Integer.parseInt(list.getOdometer())));
        }

         Glide.with(context).load(list.getBrand_icon()).placeholder(R.drawable.icon_noimage).into(car_image);



        position_car.setText((position+1)+"/"+pojo_class_mycarArrayList.size());
        if(SPHelper.is_odo_update.equalsIgnoreCase("y")){
            rl_update.setVisibility(View.VISIBLE);
        }else {
            rl_update.setVisibility(View.GONE);
        }
        rl_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.is_od_update=list.getIs_odometer_update();
                PopUpUpdateKms bottomSheetDialogFragment = new PopUpUpdateKms();
                bottomSheetDialogFragment.show(((FragmentActivity)context).getSupportFragmentManager(), bottomSheetDialogFragment.getTag());

            }
        });


        view.addView(itemView);

        return itemView;
    }

    @Override
    public float getPageWidth(int position) {
        float l=1f;
        if(pojo_class_mycarArrayList.size()>1){
            return l=0.8f;
        }
        return l;
    }

    public int getScreenWidth() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;

    }

}