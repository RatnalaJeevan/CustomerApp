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

import com.bumptech.glide.Glide;
import com.wisedrive.customerapp.CustomerHomepage;
import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.Warranty_Description;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.PojoComboProducts;
import com.wisedrive.customerapp.pojos.PojoEWPRc;
import com.wisedrive.customerapp.pojos.Pojo_Class_Mycar;
import com.wisedrive.customerapp.pojos.Pojo_Combo_Plans;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Adapter_Combo_Plans extends RecyclerView.Adapter<Adapter_Combo_Plans.MyViewHolder> {
    Context context;
    private View view;
    ArrayList<Pojo_Combo_Plans> pojo_combo_plansArrayList;
    ArrayList<PojoComboProducts> comboProducts;
    ArrayList<PojoEWPRc> pur_veh_list;
    AdaperComboProducts adaperComboProducts;
    RecyclerView rv_combo_products;
    private DecimalFormat IndianCurrencyFormat;
    RecyclerView rv_rc_ewp;
    AdapterEWPRCList adapterEWPRCList;
    ArrayList<PojoEWPRc> pojoEWPRcs;
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
        holder.plan.setText(recyclerdata.getPackage_type_name());

//        if(recyclerdata.getActiveVeh().getNo_of_cars()==null) {
//            holder.rl1.setVisibility(View.GONE);
//            holder.no_of_veh.setVisibility(View.INVISIBLE);
//            holder.rl_pack_aval.setVisibility(View.GONE);
//        }
//        else if(recyclerdata.getActiveVeh().getNo_of_cars().equals("0")){
//            holder.rl1.setVisibility(View.VISIBLE);
//            holder.rl_pack_aval.setVisibility(View.GONE);
//            holder.no_of_veh.setVisibility(View.INVISIBLE);
//            holder.veh_no.setText(recyclerdata.getActiveVeh().getVehicle_no().toUpperCase());
//        }
//        else {
//            holder.rl1.setVisibility(View.VISIBLE);
//            holder.rl_pack_aval.setVisibility(View.GONE);
//            holder.no_of_veh.setVisibility(View.VISIBLE);
//            holder.veh_no.setText(recyclerdata.getActiveVeh().getVehicle_no().toUpperCase());
//            holder.no_of_veh.setText("+"+String.valueOf(Integer.parseInt(recyclerdata.getActiveVeh().getNo_of_cars())));
//        }

        Glide.with(context).load(recyclerdata.getPackage_logo()).placeholder(R.drawable.icon_noimage).into(holder.iv2);



        holder.tv_amount_buy.setText(IndianCurrencyFormat.format((int)recyclerdata.getFinal_price()));
        comboProducts=new ArrayList<>();
        comboProducts=recyclerdata.getProductList();
        adaperComboProducts = new AdaperComboProducts(comboProducts, context);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        rv_combo_products.setLayoutManager(linearLayoutManager1);
        rv_combo_products.setAdapter(adaperComboProducts);
        adaperComboProducts.notifyDataSetChanged();

       holder. rl_view_details_button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view)
           {
               SPHelper.package_id=recyclerdata.getPackage_id();
               SPHelper.package_name=recyclerdata.getDisplay_name();
               SPHelper.main_pack_id=recyclerdata.getMain_package_id();
               SPHelper.pack_amount=0;
               SPHelper.plan_id=recyclerdata.getPackage_type_name_id();
               Intent intent=new Intent(view.getContext(), Warranty_Description.class);
               view.getContext().startActivity(intent);
           }
       });

//       holder.rl_bottom.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View view) {
//               SPHelper.comingfrom="";
//               SPHelper.fragment_is="cars";
//               Intent intent=new Intent(view.getContext(), CustomerHomepage.class);
//               view.getContext().startActivity(intent);
//           }
//       });

        pur_veh_list=new ArrayList<>();
        pur_veh_list=recyclerdata.getActiveVeh();
        if(pur_veh_list.isEmpty()){
            holder.rl2.setVisibility(View.GONE);
            holder.rl_bottom.setVisibility(View.GONE);
        }else {
            holder.rl2.setVisibility(View.VISIBLE);
            holder.rl_bottom.setVisibility(View.VISIBLE);
        }
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,
                false);
        adapterEWPRCList=new AdapterEWPRCList(pur_veh_list,context);
        rv_rc_ewp.setLayoutManager(linearLayoutManager);
        rv_rc_ewp.setAdapter(adapterEWPRCList);
    }

    @Override
    public int getItemCount() {
        return pojo_combo_plansArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_super_saver_plan,tv_amount_buy,no_of_veh,veh_no,plan;
        RelativeLayout rl_view_details_button,rl1,rl_pack_aval,rl2,rl_bottom;
        ImageView iv1,iv2;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rv_combo_products=itemView.findViewById(R.id.rv_combo_products);
            tv_super_saver_plan = itemView.findViewById(R.id.tv_super_saver_plan);
            rl_view_details_button=itemView.findViewById(R.id.rl_view_details_button);
            tv_amount_buy=itemView.findViewById(R.id.tv_amount_buy);
            rl1=itemView.findViewById(R.id.rl1);
            rl_pack_aval=itemView.findViewById(R.id.rl_pack_aval);
            rv_rc_ewp=itemView.findViewById(R.id.rv_rc_ewp);
            iv1=itemView.findViewById(R.id.iv1);
            no_of_veh=itemView.findViewById(R.id.no_of_veh);
            rl2=itemView.findViewById(R.id.rl2);
            rl_bottom=itemView.findViewById(R.id.rl_bottom);
            veh_no=itemView.findViewById(R.id.veh_no);
            plan=itemView.findViewById(R.id.plan);
            iv2=itemView.findViewById(R.id.iv2);
        }
    }


}





