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

import com.bumptech.glide.Glide;
import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.PojoPackageList;
import com.wisedrive.customerapp.pojos.PojoVehDetails;

import java.util.ArrayList;

public class AdapterVehDetails extends  RecyclerView.Adapter<AdapterVehDetails.RecyclerViewHolder>{
    @NonNull
    ArrayList<PojoVehDetails> vehdetailslist;
    Context context;

    public AdapterVehDetails(@NonNull ArrayList<PojoVehDetails> vehdetailslist,Context context) {
        this.vehdetailslist = vehdetailslist;
        this.context = context;
    }

    @Override
    public AdapterVehDetails.RecyclerViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_veh_details,parent,false);
        //view.getLayoutParams().width = (int) (getScreenWidth() / 1.1);
        return new RecyclerViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull  AdapterVehDetails.RecyclerViewHolder holder, int position) {
        PojoVehDetails recyclerdata=vehdetailslist.get(position);
         SPHelper.veh_id= recyclerdata.getVehicle_id();
        holder.customer_name.setText(recyclerdata.getCustomer_name());
        holder.customer_phoneno.setText(recyclerdata.getPhone_no());
        holder.customer_vehname.setText(recyclerdata.getVehicle_make()+" "+recyclerdata.getVehicle_model());
        holder.warranty_expiry_date.setText(recyclerdata.getValid_to());
        holder.customer_vehno.setText(recyclerdata.getVehicle_no());
        if (recyclerdata.getBrand_icon() != null && !recyclerdata.getBrand_icon().isEmpty() && !recyclerdata.getBrand_icon().equals("null")) {
            Glide.with(context).load(recyclerdata.getBrand_icon()).placeholder(R.drawable.car_image2x).into(holder.veh_img);
        }
        //holder.veh_img.setImageResource(recyclerdata.getVeh_img());
    }

    @Override
    public int getItemCount() {
        return vehdetailslist.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView customer_name,customer_phoneno,customer_vehname,customer_vehno,warranty_expiry_date;
        ImageView veh_img;
        public RecyclerViewHolder(@NonNull  View itemView) {
            super(itemView);
            customer_name=itemView.findViewById(R.id.customer_name);
            customer_phoneno=itemView.findViewById(R.id.customer_phoneno);
            customer_vehname=itemView.findViewById(R.id.customer_vehname);
            customer_vehno=itemView.findViewById(R.id.customer_vehno);
            warranty_expiry_date=itemView.findViewById(R.id.warranty_expiry_date);
            veh_img=itemView.findViewById(R.id.veh_img);
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
