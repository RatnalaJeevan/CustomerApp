package com.wisedrive.customerapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.Warranty_Description;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.PojoComboProducts;
import com.wisedrive.customerapp.pojos.Pojo_Class_Mycar;
import com.wisedrive.customerapp.pojos.Pojo_Combo_Plans;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Adapter_Combo_Plans extends RecyclerView.Adapter<Adapter_Combo_Plans.MyViewHolder> {
    Context context;
    private View view;
    ArrayList<Pojo_Combo_Plans> pojo_combo_plansArrayList;
    ArrayList<PojoComboProducts> comboProducts;
    AdaperComboProducts adaperComboProducts;
    RecyclerView rv_combo_products;
    private DecimalFormat IndianCurrencyFormat;
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
        Pojo_Combo_Plans recyclerdata = pojo_combo_plansArrayList.get(position);
        IndianCurrencyFormat = new DecimalFormat("##,##,###");
        holder.tv_super_saver_plan.setText(recyclerdata.getDisplay_name());
        if((int)recyclerdata.getAmount_saved()==0){
            holder.rl1.setVisibility(View.GONE);
        }else{
            holder.rl1.setVisibility(View.VISIBLE);
            holder.tv_save_rupee.setText(IndianCurrencyFormat.format((int)recyclerdata.getAmount_saved()));
        }

        holder.tv_amount_buy.setText(IndianCurrencyFormat.format((int)recyclerdata.getFinal_price()));
        comboProducts=new ArrayList<>();
        comboProducts=recyclerdata.getComboProducts();
        adaperComboProducts = new AdaperComboProducts(comboProducts, context);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        rv_combo_products.setLayoutManager(linearLayoutManager1);
        rv_combo_products.setAdapter(adaperComboProducts);
        adaperComboProducts.notifyDataSetChanged();

       holder. rl_view_details_button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               SPHelper.package_id=recyclerdata.getPackage_id();
               SPHelper.package_name=recyclerdata.getDisplay_name();
               SPHelper.main_pack_id=recyclerdata.getMain_package_id();
               SPHelper.pack_amount=0;
               Intent intent=new Intent(view.getContext(), Warranty_Description.class);
               view.getContext().startActivity(intent);
           }
       });
    }

    @Override
    public int getItemCount() {
        return pojo_combo_plansArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_super_saver_plan,tv_save_rupee,tv_amount_buy;
        RelativeLayout rl_view_details_button,rl1;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rv_combo_products=itemView.findViewById(R.id.rv_combo_products);
            tv_super_saver_plan = itemView.findViewById(R.id.tv_super_saver_plan);
            tv_save_rupee = itemView.findViewById(R.id.tv_save_rupee);
            rl_view_details_button=itemView.findViewById(R.id.rl_view_details_button);
            tv_amount_buy=itemView.findViewById(R.id.tv_amount_buy);
            rl1=itemView.findViewById(R.id.rl1);
        }
    }


}





