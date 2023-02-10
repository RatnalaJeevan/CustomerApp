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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.Warranty_Description;
import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.pojos.Pojo_Service_and_Maintanance_Plans;

import java.util.ArrayList;

public class Adapter_Service_and_Maintanance_Plan extends RecyclerView.Adapter<Adapter_Service_and_Maintanance_Plan.MyViewHolder> {

    Context context;
    private View view;
    CardView cardview;
    ArrayList<Pojo_Service_and_Maintanance_Plans> pojo_service_and_maintanance_plansArrayList;

    public Adapter_Service_and_Maintanance_Plan(Context context, ArrayList<Pojo_Service_and_Maintanance_Plans> pojo_service_and_maintanance_plansArrayList) {
        this.context = context;
        this.pojo_service_and_maintanance_plansArrayList = pojo_service_and_maintanance_plansArrayList;


    }

    @Override
    public Adapter_Service_and_Maintanance_Plan.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_service_and_maintanance_plan,  parent,false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull Adapter_Service_and_Maintanance_Plan.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Pojo_Service_and_Maintanance_Plans list = pojo_service_and_maintanance_plansArrayList.get(position);
        holder.tv_general_service_name.setText(list.getTv_general_service_name());
        holder.tv_general_service_description.setText(list.getTv_general_service_description());
        holder. tv_ac_service.setText(list.getTv_ac_service());
        holder.tv_ac_service_description.setText(list.getTv_ac_service_description());
        holder.tv_health_checkup.setText(list.getTv_health_checkup());
        holder.  tv_health_checkup_dscription.setText(list.getTv_health_checkup_dscription());
        holder.tv_maintanance_plan.setText(list.getTv_maintanance_plan());
        holder.tv_amount_buy.setText(list.getTv_amount_buy());
        holder.tv_rupee.setText(list.getTv_rupee());
        holder.image_1.setImageResource(list.getImage_1());
        holder.image_2.setImageResource(list.getImage_2());
        holder.image_3.setImageResource(list.getImage_3());
        holder.rl_view_details_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(), Warranty_Description.class);
                view.getContext().startActivity(intent);


            }
        });







    }

    @Override
    public int getItemCount() {
        return pojo_service_and_maintanance_plansArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_general_service_name,tv_general_service_description,tv_ac_service,
                tv_ac_service_description,tv_health_checkup,tv_health_checkup_dscription,
                tv_maintanance_plan,tv_amount_buy,tv_rupee;
        ImageView image_1,image_2,image_3;
        RelativeLayout rl_view_details_button;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_general_service_name  = itemView.findViewById(R.id.tv_general_service_name);
            tv_general_service_description = (TextView) itemView.findViewById(R.id.tv_general_service_description);
            tv_ac_service= (TextView) itemView.findViewById(R.id.tv_ac_service);
            tv_ac_service_description=(TextView) itemView.findViewById(R.id.tv_ac_service_description);
            tv_health_checkup= (TextView) itemView.findViewById(R.id.tv_health_checkup);
            tv_health_checkup_dscription= (TextView) itemView.findViewById(R.id.tv_health_checkup_dscription);
            tv_maintanance_plan= (TextView) itemView.findViewById(R.id.tv_maintanance_plan);
            tv_amount_buy= (TextView) itemView.findViewById(R.id.tv_amount_buy);
            tv_rupee= (TextView) itemView.findViewById(R.id.tv_rupee);
            image_1= itemView.findViewById(R.id.image_1);
            image_2= itemView.findViewById(R.id.image_2);
            image_3= itemView.findViewById(R.id.image_3);
            rl_view_details_button=(RelativeLayout) view.findViewById(R.id.rl_view_details_button);




        }
    }


}
