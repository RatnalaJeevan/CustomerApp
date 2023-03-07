package com.wisedrive.customerapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.Addons;
import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.Pojo_Class_Addons_List;
import com.wisedrive.customerapp.pojos.Pojo_Combo_Plans;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Adapter_Addons_list  extends RecyclerView.Adapter<Adapter_Addons_list.MyViewHolder> {
    private DecimalFormat IndianCurrencyFormat;
    Context context;
    private View view;
    ArrayList<Pojo_Class_Addons_List> pojo_class_addons_listArrayList;
    private int selectedPosition = -1;

    public Adapter_Addons_list(Context context, ArrayList<Pojo_Class_Addons_List> pojo_class_addons_listArrayList) {
        this.context = context;
        this.pojo_class_addons_listArrayList=pojo_class_addons_listArrayList;
    }

    @Override
    public Adapter_Addons_list.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_addons_list, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Addons_list.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        IndianCurrencyFormat = new DecimalFormat("##,##,###");
        Pojo_Class_Addons_List list = pojo_class_addons_listArrayList.get(position);
        holder.add_on_validity.setText(list.getPlan_validity());
        holder.tv_warranty_name.setText(list.getAddon_name());
        holder. tv_description.setText(list.getPackage_discription());
        if((int)list.getAmount_saved()==0){
            holder.tv_scratch_amount.setVisibility(View.GONE);
        }else {
            holder.tv_scratch_amount.setVisibility(View.VISIBLE);
            holder.tv_scratch_amount.setText("INR "+IndianCurrencyFormat.format((int)list.getAmount_saved()));
        }
        holder.tv_amount.setText(IndianCurrencyFormat.format(list.getFinal_price()));
        if(list.getPercentage_amount_saved().startsWith("0")){
            holder.tv_percent.setVisibility(View.GONE);
        }else{
            holder.tv_percent.setVisibility(View.VISIBLE);
            holder.tv_percent.setText(Integer.parseInt(list.getPercentage_amount_saved())+"%"+"\toff");
        }

        if (list.getIs_recommended().equalsIgnoreCase("y")) {
            holder.r_l_status.setBackgroundResource(R.drawable.rl_background_1);
            holder.label_rec.setText("Recommended");
            holder.label_rec.setTextColor(Color.parseColor("#cc8899"));
        } else  {
            holder.r_l_status.setBackgroundResource(R.drawable.blue_background);
            holder.label_rec.setText("New");
            holder.label_rec.setTextColor(Color.parseColor("#0619C3"));
        }


        if(list.getIsSelected().equals("y")){
           // holder.rl_select_button.setSelected(true); //using selector drawable
            holder.rl_select_button.setBackgroundResource(R.drawable.select_green);
            holder.tv_select.setTextColor(Color.parseColor("#FFFFFFFF"));
            holder.white_check.setVisibility(View.VISIBLE);
        }
        else{
            holder.rl_select_button.setBackgroundResource(R.drawable.select);
            holder.tv_select.setTextColor(Color.parseColor("#aeaeb2"));
            holder.white_check.setVisibility(View.INVISIBLE);
        }
        holder.rl_select_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if(list.getIsSelected().equals("y")){
                        SPHelper.disc_amount=0;
                        SPHelper.coupon_code="";
                        SPHelper.coupon_type="";
                        SPHelper.coupon_id="";
                        Addons.getInstance().tv_dis_amount.setText("0");
                        Addons.getInstance().coupon_label.setVisibility(View.GONE);
                        Addons.getInstance().rl_coupon_applied.setVisibility(View.INVISIBLE);
                        Addons.getInstance().rl_coupon_label.setVisibility(View.VISIBLE);
                        SPHelper.final_amount=SPHelper.final_amount-list.getFinal_price();
                        Addons.getInstance().tv_total_amount.setText(String.valueOf(IndianCurrencyFormat.format((int) (SPHelper.final_amount-SPHelper.disc_amount))));
                        Addons.getInstance().tv_amount_buy.setText(String.valueOf(IndianCurrencyFormat.format((int) SPHelper.final_amount)));
                        list.setIsSelected("n");
                        SPHelper.addon_list.get(position).setIsSelected("n");
                    }else{
                        list.setIsSelected("y");
                        SPHelper.final_amount=SPHelper.final_amount+list.getFinal_price();
                        Addons.getInstance().tv_total_amount.setText(String.valueOf(IndianCurrencyFormat.format((int)(SPHelper.final_amount-SPHelper.disc_amount))));
                        Addons.getInstance().tv_amount_buy.setText(String.valueOf(IndianCurrencyFormat.format((int) SPHelper.final_amount)));
                        SPHelper.addon_list.get(position).setIsSelected("y");
                    }
                    notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return pojo_class_addons_listArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView label_rec,tv_warranty_name,tv_select,tv_description,tv_scratch_amount,tv_amount,tv_percent,add_on_validity;
        RelativeLayout r_l_status;
        RelativeLayout rl_select_button;
        ImageView white_check;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            add_on_validity=itemView.findViewById(R.id.add_on_validity);
            label_rec = itemView.findViewById(R.id.label_rec);
            tv_warranty_name = (TextView) itemView.findViewById(R.id.tv_warranty_name);
            tv_select = (TextView) itemView.findViewById(R.id.tv_select);
            tv_description = (TextView) itemView.findViewById(R.id.tv_description);
            tv_scratch_amount = (TextView) itemView.findViewById(R.id.tv_scratch_amount);
            tv_amount= (TextView) itemView.findViewById(R.id.tv_amount);
            tv_percent= (TextView) itemView.findViewById(R.id.tv_percent);
            r_l_status= (RelativeLayout) itemView.findViewById(R.id.r_l_status);
            rl_select_button= (RelativeLayout) itemView.findViewById(R.id.rl_select_button);
            white_check= (ImageView) itemView.findViewById(R.id.white_check);

        }
    }

}
