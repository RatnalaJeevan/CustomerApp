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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.Warranty_Description;
import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.PojoServiceDetails;
import com.wisedrive.customerapp.pojos.Pojo_Service_and_Maintanance_Plans;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Adapter_Service_and_Maintanance_Plan extends RecyclerView.Adapter<Adapter_Service_and_Maintanance_Plan.MyViewHolder> {

    private DecimalFormat IndianCurrencyFormat;
    Context context;
    private View view;
    CardView cardview;
    ArrayList<Pojo_Service_and_Maintanance_Plans> pojo_service_and_maintanance_plansArrayList;
    ArrayList<PojoServiceDetails> serviceDetails;
    AdapterServiceDetails adapterServiceDetails;
    RecyclerView rv_service_details;
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
        Pojo_Service_and_Maintanance_Plans recyclerdata = pojo_service_and_maintanance_plansArrayList.get(position);
        IndianCurrencyFormat = new DecimalFormat("##,##,###");
        holder.tv_maintanance_plan.setText(recyclerdata.getDisplay_name());
//        if((int)recyclerdata.getAmount_saved()==0){
//            holder.rl1.setVisibility(View.GONE);
//        }else{
//            holder.rl1.setVisibility(View.VISIBLE);
//            holder.tv_rupee.setText(IndianCurrencyFormat.format((int)recyclerdata.getAmount_saved()));
//        }

        holder.tv_amount_buy.setText(IndianCurrencyFormat.format((int)recyclerdata.getFinal_price()));
        serviceDetails=new ArrayList<>();
        serviceDetails=recyclerdata.getServiceDetails();
        adapterServiceDetails = new AdapterServiceDetails(serviceDetails, context);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        rv_service_details.setLayoutManager(linearLayoutManager1);
        rv_service_details.setAdapter(adapterServiceDetails);
        adapterServiceDetails.notifyDataSetChanged();

        holder.rl_view_details_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.package_id=recyclerdata.getPackage_id();
                SPHelper.package_name=recyclerdata.getPackage_name();
                SPHelper.main_pack_id=recyclerdata.getMain_package_id();
                SPHelper.pack_amount=0;
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

        TextView tv_maintanance_plan,tv_rupee,tv_amount_buy;
        ImageView image_1,image_2,image_3;
        RelativeLayout rl_view_details_button,rl1;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rv_service_details=itemView.findViewById(R.id.rv_service_details);
            tv_maintanance_plan= (TextView) itemView.findViewById(R.id.tv_maintanance_plan);
            tv_rupee= (TextView) itemView.findViewById(R.id.tv_rupee);
            rl_view_details_button=itemView.findViewById(R.id.rl_view_details_button);
            tv_amount_buy=itemView.findViewById(R.id.tv_amount_buy);
            rl1=itemView.findViewById(R.id.rl1);




        }
    }


}
