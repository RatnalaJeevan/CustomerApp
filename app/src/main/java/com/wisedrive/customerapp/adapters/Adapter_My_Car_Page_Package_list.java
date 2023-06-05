package com.wisedrive.customerapp.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wisedrive.customerapp.ActYourPack;
import com.wisedrive.customerapp.Activity_Showroom_Services;
import com.wisedrive.customerapp.ClaimWarranty;
import com.wisedrive.customerapp.FragmentPopoup;
import com.wisedrive.customerapp.MyCar_Fragment;
import com.wisedrive.customerapp.PopupChooseTypeInsp;
import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.commonclasses.Common;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.Pojo_My_Car_page_package_list;

import java.util.ArrayList;

public class Adapter_My_Car_Page_Package_list extends RecyclerView.Adapter<Adapter_My_Car_Page_Package_list.MyViewHolder> {
    Context context;
    Activity activity;
    private View view;
    ArrayList<Pojo_My_Car_page_package_list> pojo_my_car_warranty_listArrayList;

    String is_pack_act="",isblur="n",is_exp="";
    public Adapter_My_Car_Page_Package_list(Activity activity, ArrayList<Pojo_My_Car_page_package_list> pojo_my_car_warranty_listArrayList) {
        this.activity = activity;
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


        Glide.with(context).load(recyclerdata.getProduct_icon()).placeholder(R.drawable.service_image).into(holder.image_logo);

        for (int i=0;i<pojo_my_car_warranty_listArrayList.size();i++)
        {
            if(pojo_my_car_warranty_listArrayList.get(i).getExpired().equalsIgnoreCase("y")){
                is_exp="y";
            }else {
                is_exp="n";
                break;
            }
        }

        if(is_exp.equals("y")){
            holder.rl2.setVisibility(View.GONE);
        }
        else {
            if(position==0)
            {
                if(SPHelper.d_id==null||SPHelper.d_id.equals("null")||SPHelper.d_id.equals(""))
                {
                    MyCar_Fragment.get_instance().rl1.setVisibility(View.GONE);
                    if(recyclerdata.getIs_active().equalsIgnoreCase("n")&&
                            recyclerdata.getIs_warranty().equalsIgnoreCase("y"))
                    {

                        holder.text_name1.setText(recyclerdata.getDisplaymessage());
                        if(recyclerdata.getButtonName().equals("")){
                            holder.rl_cancel1.setVisibility(View.GONE);
                            holder.rl2.setVisibility(View.GONE);
                        }else {
                            holder.rl2.setVisibility(View.VISIBLE);
                            holder.rl_cancel1.setVisibility(View.VISIBLE);
                            holder.schedule_insp.setText(recyclerdata.getButtonName());
                        }

                        isblur="n";
                    }
                    else {
                        holder.rl2.setVisibility(View.GONE);
                        isblur="n";
                    }
                }
                else {
                    if(recyclerdata.getIs_active().equalsIgnoreCase("n"))
                    {
                        MyCar_Fragment.get_instance().rl1.setVisibility(View.VISIBLE);
                        MyCar_Fragment.get_instance().text_name.setText(recyclerdata.getDisplaymessage());
                        MyCar_Fragment.get_instance().act_warr.setText(recyclerdata.getButtonName());
                        isblur="y";

                    }else {
                        MyCar_Fragment.get_instance().rl1.setVisibility(View.GONE);
                        isblur="n";
                        if(recyclerdata.getInspectionRequested().equals("n"))
                        {

                            holder.text_name1.setText(recyclerdata.getDisplaymessage());
                            if(recyclerdata.getButtonName().equals("")){
                                holder.rl_cancel1.setVisibility(View.GONE);
                                holder.rl2.setVisibility(View.GONE);
                            }else {
                                holder.rl2.setVisibility(View.VISIBLE);
                                holder.rl_cancel1.setVisibility(View.VISIBLE);
                                holder.schedule_insp.setText(recyclerdata.getButtonName());
                            }
                        }else {
                            holder.rl2.setVisibility(View.GONE);
                        }
                    }
                }
            }
        }


//        if(position==2){
//            holder.text_warranty_name.setTextColor(Color.parseColor("#d3d3d3"));
//            holder.tv_description.setTextColor(Color.parseColor("#d3d3d3"));
//            holder.expires_on.setTextColor(Color.parseColor("#d3d3d3"));
//            holder.rl2.setVisibility(View.VISIBLE);
//            holder.image_logo.setImageResource(R.drawable.shield_grey);
//        }else {
//            holder.rl2.setVisibility(View.GONE);
////            Glide.with(context).load(recyclerdata.getProduct_icon()).
////                    placeholder(R.drawable.service_image).into(holder.image_logo);
//        }


        if(is_pack_act==null||is_pack_act.equals("null")){
            holder.label_inactive.setVisibility(View.VISIBLE);
            holder.tv_description.setVisibility(View.VISIBLE);
            holder.expires_on.setVisibility(View.INVISIBLE);
        }
        else if(is_pack_act.equalsIgnoreCase("n")){
            holder.label_inactive.setVisibility(View.GONE);
            holder.label_inactive.setText("INACTIVE");
            holder.tv_description.setVisibility(View.VISIBLE);
            holder.expires_on.setVisibility(View.VISIBLE);

        }
        else{
            holder.label_inactive.setVisibility(View.GONE);
            holder.expires_on.setVisibility(View.VISIBLE);

                if(pend_count==null||pend_count.equals("null")||pend_count.equals("")||
                        pend_count.equals("0"))
                {
                    holder.tv_description.setVisibility(View.VISIBLE);
                    if(recyclerdata.getIs_warranty().equalsIgnoreCase("y")||
                            recyclerdata.getProduct_id().equals("5"))
                    {
                        holder.tv_description.setText("No pending claims");
                    }else {
                        holder.tv_description.setText("No pending services");
                    }

                }else{
                    holder.tv_description.setVisibility(View.VISIBLE);
                    holder.tv_description.setText("You have "+recyclerdata.getServiceCount().getPending_count()+"\t"+
                            recyclerdata.getServiceCount().getProduct_name()+" pending"+" which can be availed ");
                }
        }



        if(recyclerdata.getAcivationCode().equalsIgnoreCase("n")||
           recyclerdata.getInspectionRequested().equalsIgnoreCase("y")||
           recyclerdata.getInspectionRequested().equalsIgnoreCase("n"))
        {
            holder.label_inactive.setVisibility(View.VISIBLE);
            holder.label_inactive.setText(recyclerdata.getError_msg());
            holder.expires_on.setVisibility(View.INVISIBLE);
        }


        if(recyclerdata.getExpiry_date()==null||recyclerdata.getExpiry_date().equals("")){
            holder.expires_on.setVisibility(View.INVISIBLE);
        }else{
            holder.expires_on.setVisibility(View.VISIBLE);
            holder.label_inactive.setVisibility(View.INVISIBLE);

            holder.expires_on.setText("Valid till \t"+ Common.getDateFromString(recyclerdata.getExpiry_date()));
        }

        if(recyclerdata.getError_msg()!=null&&(recyclerdata.getError_msg().equalsIgnoreCase("in review")||
                recyclerdata.getError_msg().equalsIgnoreCase("pending for inspection")||
                recyclerdata.getError_msg().equalsIgnoreCase("request for inspection")||
        recyclerdata.getExpired().equalsIgnoreCase("y")))
        {
            holder.label_inactive.setVisibility(View.VISIBLE);
            holder.label_inactive.setText(recyclerdata.getError_msg());
            holder.tv_description.setVisibility(View.GONE);
            holder.expires_on.setVisibility(View.GONE);
            holder.image_logo.setImageResource(R.drawable.shield_grey);
            holder.text_warranty_name.setTextColor(Color.parseColor("#d3d3d3"));
            holder.tv_description.setTextColor(Color.parseColor("#d3d3d3"));
            holder.expires_on.setTextColor(Color.parseColor("#d3d3d3"));
            holder.label_inactive.setTextColor(Color.parseColor("#d3d3d3"));
            isblur="y";
        }


//        if(isblur.equals("y"))
//        {
//            holder.image_logo.setImageResource(R.drawable.shield_grey);
//            holder.text_warranty_name.setTextColor(Color.parseColor("#d3d3d3"));
//            holder.tv_description.setTextColor(Color.parseColor("#d3d3d3"));
//            holder.expires_on.setTextColor(Color.parseColor("#d3d3d3"));
//           // holder.label_inactive.setTextColor(Color.parseColor("#d3d3d3"));
//
//        }

        holder.rl_item.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                SPHelper.pack_type=recyclerdata.getPackage_type();
                SPHelper.product_id= recyclerdata.getProduct_id();
                SPHelper.product_name=recyclerdata.getProduct_name();
                SPHelper.package_id=recyclerdata.getPackage_id();
                SPHelper.dpp_id=recyclerdata.getDpp_id();
                is_pack_act= recyclerdata.getIs_active();

                if(is_pack_act==null||is_pack_act.equals("null"))
                {

                }
              else  if(is_pack_act.equalsIgnoreCase("n"))
               {

               }
               else{
                    SPHelper.is_exp=recyclerdata.getExpired();
                   if(recyclerdata.getIs_service().equalsIgnoreCase("y")){
                       Intent intent =  new Intent(context, Activity_Showroom_Services.class);
                       intent.putExtra("it_is","");
                       context.startActivity(intent);
                   }else{
                       Intent intent =  new Intent(context, ClaimWarranty.class);
                       intent.putExtra("is_active",is_pack_act);
                       context.startActivity(intent);
                   }
               }
            }
        });

        holder.schedule_insp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.schedule_insp.getText().toString().equalsIgnoreCase("activate"))
                {
                    ActYourPack bottomSheetDialogFragment1 = new ActYourPack();
                    bottomSheetDialogFragment1.show(((FragmentActivity)context).getSupportFragmentManager(), bottomSheetDialogFragment1.getTag());

                }else {
                    PopupChooseTypeInsp bottomSheetDialogFragment = new PopupChooseTypeInsp();
                    bottomSheetDialogFragment.show(((FragmentActivity)context).getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return pojo_my_car_warranty_listArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView text_warranty_name,tv_description,expires_on,label_inactive,schedule_insp,text_name1;
        ImageView image_logo,right_icon;
        RelativeLayout rl2,rl_cancel1;
        carbon.widget.RelativeLayout rl_item;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            text_name1=itemView.findViewById(R.id.text_name1);
            rl_cancel1=itemView.findViewById(R.id.rl_cancel1);
            schedule_insp=itemView.findViewById(R.id.schedule_insp);
            expires_on=itemView.findViewById(R.id.expires_on);
            text_warranty_name = itemView.findViewById(R.id.text_warranty_name);
            tv_description= itemView.findViewById(R.id.tv_description);
            image_logo = itemView.findViewById(R.id.image_logo);
            right_icon = itemView.findViewById(R.id.right_icon);
            rl2= itemView.findViewById(R.id.rl2);
            label_inactive=itemView.findViewById(R.id.label_inactive);
            rl_item=itemView.findViewById(R.id.rl_item);
        }
    }
}
