package com.wisedrive.customerapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.VehiclePackageDetails;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.PojoVehDetails;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SlideAdapter extends PagerAdapter
{
    ArrayList<PojoVehDetails> vehdetailslist;
    Context context;
    LayoutInflater inflater;
    public TextView customer_name,customer_phoneno,customer_vehname,customer_vehno,warranty_expiry_date;
   public ImageView veh_img;
    public SlideAdapter(ArrayList<PojoVehDetails> vehdetailslist, Context context) {
        this.vehdetailslist = vehdetailslist;
        this.context = context;
    }

    @Override
    public int getCount() {
        return vehdetailslist.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull @NotNull View view, @NonNull @NotNull Object object) {

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
    public Object instantiateItem(@NonNull @NotNull ViewGroup itemView, int position) {

        View viewLayout = LayoutInflater.from(context).inflate(R.layout.items_veh_details, itemView, false);

        customer_name=viewLayout.findViewById(R.id.customer_name);
        customer_phoneno=viewLayout.findViewById(R.id.customer_phoneno);
        customer_vehname=viewLayout.findViewById(R.id.customer_vehname);
        customer_vehno=viewLayout.findViewById(R.id.customer_vehno);
        warranty_expiry_date=viewLayout.findViewById(R.id.warranty_expiry_date);
        veh_img=viewLayout.findViewById(R.id.veh_img);

        PojoVehDetails recyclerdata=vehdetailslist.get(position);

        SPHelper.customer_name=vehdetailslist.get(0).getCustomer_name();
        SPHelper.customer_phoneno=vehdetailslist.get(0).getPhone_no();
        SPHelper.veh_model=vehdetailslist.get(0).getVehicle_model();
        SPHelper.veh_make=vehdetailslist.get(0).getVehicle_make();
        SPHelper.veh_valid_to=vehdetailslist.get(0).getValid_to();
        SPHelper.veh_id= vehdetailslist.get(0).getVehicle_id();
        SPHelper.customer_veh_no=vehdetailslist.get(0).getVehicle_no();
        customer_name.setText(recyclerdata.getCustomer_name());
        customer_phoneno.setText(recyclerdata.getPhone_no());
        SPHelper.saveSPdata(context.getApplicationContext(), SPHelper.customer_phoneno, recyclerdata.getPhone_no());
        customer_vehname.setText(recyclerdata.getVehicle_make()+" "+recyclerdata.getVehicle_model());
        warranty_expiry_date.setText(recyclerdata.getValid_to());
        customer_vehno.setText(recyclerdata.getVehicle_no());
        if (recyclerdata.getBrand_icon() != null && !recyclerdata.getBrand_icon().isEmpty() && !recyclerdata.getBrand_icon().equals("null")) {
            Glide.with(context).load(recyclerdata.getBrand_icon()).placeholder(R.drawable.car_image2x).into(veh_img);
        }
        itemView.addView(viewLayout);
        return viewLayout;
    }
}
