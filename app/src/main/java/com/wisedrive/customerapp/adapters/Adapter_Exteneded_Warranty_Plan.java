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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.Warranty_Description;
import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.PojoEWPProducts;
import com.wisedrive.customerapp.pojos.PojoEWPRc;
import com.wisedrive.customerapp.pojos.Pojo_Extended_Warranty_Plan;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Adapter_Exteneded_Warranty_Plan extends RecyclerView.Adapter<Adapter_Exteneded_Warranty_Plan.MyViewHolder> {

    private DecimalFormat IndianCurrencyFormat;
    Context context;
    private View view;
    ArrayList<Pojo_Extended_Warranty_Plan>pojo_extended_warranty_planArrayList;
    RecyclerView rv_rc_ewp,rv_ewp_product_list;
    AdapterEWPRCList adapterEWPRCList;
    ArrayList<PojoEWPRc> pojoEWPRcs;
    ArrayList<PojoEWPProducts> pojoEWPProducts;
    AdapterEWPProductList adapterEWPProductList;
    public Adapter_Exteneded_Warranty_Plan(Context context, ArrayList<Pojo_Extended_Warranty_Plan>pojo_extended_warranty_planArrayList) {
        this.context = context;
        this.pojo_extended_warranty_planArrayList =pojo_extended_warranty_planArrayList;
    }

    @Override
    public Adapter_Exteneded_Warranty_Plan.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_extended_warranty_plan, parent,false);
       // view.getLayoutParams().width = (int) (getScreenWidth()/1.1);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Exteneded_Warranty_Plan.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Pojo_Extended_Warranty_Plan recyclerdata = pojo_extended_warranty_planArrayList.get(position);
        IndianCurrencyFormat = new DecimalFormat("##,##,###");
        holder. text_warranty_name.setText(recyclerdata.getDisplay_name());
        holder.text_amount.setText(IndianCurrencyFormat.format((int)recyclerdata.getFinal_price()));
       // holder.plan_validity.setText(recyclerdata.getPlan_validity());
//        if((int)recyclerdata.getAmount_saved()==0){
//            holder.rl_validity_save.setVisibility(View.INVISIBLE);
//        }else{
//            holder.rl_validity_save.setVisibility(View.VISIBLE);
//            holder.text_save_amount.setText("INR \t"+IndianCurrencyFormat.format((int)recyclerdata.getAmount_saved()));
//        }

        holder.rl_view_details_button.setOnClickListener(new View.OnClickListener() {

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

        pojoEWPRcs=new ArrayList<>();

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        adapterEWPRCList=new AdapterEWPRCList(pojoEWPRcs,context);
        rv_rc_ewp.setLayoutManager(linearLayoutManager);
        rv_rc_ewp.setAdapter(adapterEWPRCList);


        pojoEWPProducts=new ArrayList<>();
        pojoEWPProducts.add(new PojoEWPProducts("Comprehensive Warranty"));
        pojoEWPProducts.add(new PojoEWPProducts("Buy back Guarantee"));
        LinearLayoutManager l1=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        adapterEWPProductList=new AdapterEWPProductList(pojoEWPProducts,context);
        rv_ewp_product_list.setLayoutManager(l1);
        rv_ewp_product_list.setAdapter(adapterEWPProductList);

    }

    @Override
    public int getItemCount() {
        return pojo_extended_warranty_planArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image_icon;
        TextView text_warranty_name,text_amount,text_save_amount,plan_validity;
        RelativeLayout rl_amount,rl_view_details_button;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rl_amount=itemView.findViewById(R.id.rl_amount);
            text_warranty_name = (TextView) itemView.findViewById(R.id.text_warranty_name);
            text_amount =  itemView.findViewById(R.id.text_amount);
            rl_view_details_button = itemView.findViewById(R.id.rl_view_details_button);
            rv_rc_ewp=itemView.findViewById(R.id.rv_rc_ewp);
            rv_ewp_product_list=itemView.findViewById(R.id.rv_ewp_product_list);
        }

    }

  //  public int getScreenWidth() {
     //   WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
      //  Display display = wm.getDefaultDisplay();
       // Point size = new Point();
        //display.getSize(size);
     //   return size.x;
  //  }
}
