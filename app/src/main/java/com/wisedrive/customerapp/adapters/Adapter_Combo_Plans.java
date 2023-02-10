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

import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.pojos.Pojo_Class_Mycar;
import com.wisedrive.customerapp.pojos.Pojo_Combo_Plans;

import java.util.ArrayList;

public class Adapter_Combo_Plans extends RecyclerView.Adapter<Adapter_Combo_Plans.MyViewHolder> {
    Context context;
    private View view;
    ArrayList<Pojo_Combo_Plans> pojo_combo_plansArrayList;

    public Adapter_Combo_Plans(Context context, ArrayList<Pojo_Combo_Plans> pojo_combo_plansArrayList) {
        this.context = context;
        this.pojo_combo_plansArrayList=pojo_combo_plansArrayList;
    }

    @Override
    public Adapter_Combo_Plans.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_combo_plans, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Combo_Plans.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Pojo_Combo_Plans list = pojo_combo_plansArrayList.get(position);
        holder.tv_super_saver_plan.setText(list.getTv_super_saver_plan());
        holder.tv_comprehensive_warranty.setText(list.getTv_comprehensive_warranty());
        holder. tv_comprehensive_warranty_description.setText(list.getTv_comprehensive_warranty_description());
        holder.tv_service_maintanance.setText(list.getTv_service_maintanance());
        holder. tv_service_maintanance_description.setText(list.getTv_service_maintanance_description());
        holder.tv_Roadside_Assis.setText(list.getTv_Roadside_Assis());
        holder.tv_roadsideassis_dscription.setText(list.getTv_roadsideassis_dscription());
        holder.tv_amount_buy.setText(list.getTv_amount_buy());
        holder.tv_save_rupee.setText(list.getTv_save_rupee());
        holder.image_1.setImageResource(list.getImage_1());
        holder.image_2.setImageResource(list.getImage_2());
        holder.image_3.setImageResource(list.getImage_3());



    }

    @Override
    public int getItemCount() {
        return pojo_combo_plansArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_super_saver_plan,tv_amount_save,tv_comprehensive_warranty,
                tv_comprehensive_warranty_description,tv_service_maintanance,
                tv_service_maintanance_description,tv_Roadside_Assis,tv_roadsideassis_dscription,tv_amount_buy,tv_save_rupee;
        ImageView image_1,image_2,image_3;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_super_saver_plan = itemView.findViewById(R.id.tv_super_saver_plan);
            tv_comprehensive_warranty = (TextView) itemView.findViewById(R.id.tv_comprehensive_warranty);
            tv_comprehensive_warranty_description = (TextView) itemView.findViewById(R.id.tv_comprehensive_warranty_description);
            tv_service_maintanance = (TextView) itemView.findViewById(R.id.tv_service_maintanance);
            tv_service_maintanance_description = (TextView) itemView.findViewById(R.id.tv_service_maintanance_description);
            tv_Roadside_Assis = (TextView) itemView.findViewById(R.id.tv_Roadside_Assis);
            tv_roadsideassis_dscription = (TextView) itemView.findViewById(R.id.tv_roadsideassis_dscription);
            tv_amount_buy = (TextView) itemView.findViewById(R.id.tv_amount_buy);
            tv_save_rupee = (TextView) itemView.findViewById(R.id.tv_save_rupee);
            image_1= itemView.findViewById(R.id.image_1);
            image_2= itemView.findViewById(R.id.image_2);
            image_3= itemView.findViewById(R.id.image_3);


        }
    }


}





