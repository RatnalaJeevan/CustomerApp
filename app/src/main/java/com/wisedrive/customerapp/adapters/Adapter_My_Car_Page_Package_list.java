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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wisedrive.customerapp.Activity_Showroom_Services;
import com.wisedrive.customerapp.Comprehensive_Warranty;
import com.wisedrive.customerapp.FragmentPopoup;
import com.wisedrive.customerapp.MyCar_Fragment;
import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.commonclasses.Common;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.Pojo_My_Car_page_package_list;

import java.util.ArrayList;

public class Adapter_My_Car_Page_Package_list extends RecyclerView.Adapter<Adapter_My_Car_Page_Package_list.MyViewHolder> {
    Context context;
    private View view;
    ArrayList<Pojo_My_Car_page_package_list> pojo_my_car_warranty_listArrayList;

    String is_pack_act="";
    public Adapter_My_Car_Page_Package_list(Context context, ArrayList<Pojo_My_Car_page_package_list> pojo_my_car_warranty_listArrayList) {
        this.context = context;
        this.pojo_my_car_warranty_listArrayList = pojo_my_car_warranty_listArrayList;
    }

    @Override
    public Adapter_My_Car_Page_Package_list.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_my_car_package_list, null);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Adapter_My_Car_Page_Package_list.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Pojo_My_Car_page_package_list recyclerdata = pojo_my_car_warranty_listArrayList.get(position);
        holder.text_warranty_name.setText(recyclerdata.getProduct_name());
        String pend_count=recyclerdata.getServiceCount().getPending_count();
        is_pack_act= recyclerdata.getIs_active();


        if(is_pack_act==null||is_pack_act.equals("null")){
            holder.label_inactive.setVisibility(View.VISIBLE);
            holder.tv_description.setVisibility(View.GONE);
            holder.expires_on.setVisibility(View.GONE);
        }
        else if(is_pack_act.equalsIgnoreCase("n")){
            holder.label_inactive.setVisibility(View.VISIBLE);
            holder.label_inactive.setText("INACTIVE");
            holder.tv_description.setVisibility(View.GONE);
            holder.expires_on.setVisibility(View.VISIBLE);
        }else{
            holder.label_inactive.setVisibility(View.GONE);
            holder.expires_on.setVisibility(View.VISIBLE);
            if(pend_count==null||pend_count.equals("null")||pend_count.equals("")){
                holder.tv_description.setVisibility(View.GONE);
            }else{
                holder.tv_description.setVisibility(View.VISIBLE);
                holder.tv_description.setText(recyclerdata.getServiceCount().getPending_count()+"\t "+
                recyclerdata.getServiceCount().getProduct_name()+"\tpending");
            }
        }

        if(recyclerdata.getExpired().equalsIgnoreCase("y")){
            holder.label_inactive.setVisibility(View.VISIBLE);
            holder.label_inactive.setText("EXPIRED");
            holder.tv_description.setVisibility(View.GONE);
            holder.expires_on.setVisibility(View.VISIBLE);
        }
        Glide.with(context).load(recyclerdata.getProduct_icon()).placeholder(R.drawable.service_image).into(holder.image_logo);


        if(recyclerdata.getColor().equals("dce4ff")){
            holder.relative_layout_services.setBackgroundResource(R.drawable.blue_background);
            holder.right_icon.setImageResource(R.drawable.right_arrow_blue);
            holder.text_warranty_name.setTextColor(Color.parseColor("#0619c3"));
            holder.tv_description.setTextColor(Color.parseColor("#0619c3"));
        }else if(recyclerdata.getColor().equals("d7ffd7")){
            holder.relative_layout_services.setBackgroundResource(R.drawable.blue_background);
            holder.relative_layout_services.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.light_green));
            holder.right_icon.setImageResource(R.drawable.right_icin_green);
            holder.text_warranty_name.setTextColor(Color.parseColor("#4BAE4F"));
            holder. tv_description.setTextColor(Color.parseColor("#4BAE4F"));
        }else{
            holder.relative_layout_services.setBackgroundResource(R.drawable.orange_background);
            holder.right_icon.setImageResource(R.drawable.right_icon_orange);
            holder. text_warranty_name.setTextColor(Color.parseColor("#ff6739"));
            holder. tv_description.setTextColor(Color.parseColor("#ff6739"));
        }
        if(recyclerdata.getExpiry_date()==null||recyclerdata.getExpiry_date().equals("")){
            holder.expires_on.setVisibility(View.GONE);
        }else{
            holder.expires_on.setVisibility(View.VISIBLE);
            holder.expires_on.setText("Expires on:\t"+ Common.getDateFromString(recyclerdata.getExpiry_date()));
        }


        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                SPHelper.pack_type=recyclerdata.getPackage_type();
                SPHelper.product_id= recyclerdata.getProduct_id();
                SPHelper.product_name=recyclerdata.getProduct_name();
                SPHelper.package_id=recyclerdata.getPackage_id();
                is_pack_act= recyclerdata.getIs_active();
                if(is_pack_act==null||is_pack_act.equals("null")){

                }
              else  if(is_pack_act.equalsIgnoreCase("n"))
               {
                   FragmentPopoup bottomSheetDialogFragment = new FragmentPopoup();
                   bottomSheetDialogFragment.insp_req=recyclerdata.getInspectionRequested();
                   bottomSheetDialogFragment.act_code=recyclerdata.getAcivationCode();
                   bottomSheetDialogFragment.error_msg=recyclerdata.getError_msg();
                   bottomSheetDialogFragment.show(((FragmentActivity)context).getSupportFragmentManager(), bottomSheetDialogFragment.getTag());

               }
               else{
                    SPHelper.is_exp=recyclerdata.getExpired();
                   if(recyclerdata.getIs_service().equalsIgnoreCase("y")){
                       Intent intent =  new Intent(context, Activity_Showroom_Services.class);
                       context.startActivity(intent);
                   }else{
                       Intent intent =  new Intent(context, Comprehensive_Warranty.class);
                       intent.putExtra("is_active",is_pack_act);
                       context.startActivity(intent);
                   }
               }
            }
        });

    }

    @Override
    public int getItemCount() {
        return pojo_my_car_warranty_listArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView text_warranty_name,tv_description,expires_on,label_inactive;
        ImageView image_logo,right_icon;
        RelativeLayout relative_layout_services;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            expires_on=itemView.findViewById(R.id.expires_on);
            text_warranty_name = itemView.findViewById(R.id.text_warranty_name);
            tv_description= itemView.findViewById(R.id.tv_description);
            image_logo = itemView.findViewById(R.id.image_logo);
            right_icon = itemView.findViewById(R.id.right_icon);
            relative_layout_services= itemView.findViewById(R.id.relative_layout_services);
            label_inactive=itemView.findViewById(R.id.label_inactive);


        }
    }
}
