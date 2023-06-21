package com.wisedrive.customerapp.adapters;

import static com.wisedrive.customerapp.R.drawable.cardview_lightgrey_margined;
import static com.wisedrive.customerapp.R.drawable.cardview_lightorange_margined;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.Addons;
import com.wisedrive.customerapp.PopupSelectPack;
import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.Warranty_Description;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.PojoUpgrade;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdapterUpgrade extends RecyclerView.Adapter<AdapterUpgrade.RecyclerViewHolder> {
    private DecimalFormat IndianCurrencyFormat;
    ArrayList<PojoUpgrade> pojoUpgrades;
    Context context;
    private int selectedPosition = 0;
    public AdapterUpgrade(ArrayList<PojoUpgrade> pojoUpgrades, Context context) {
        this.pojoUpgrades = pojoUpgrades;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterUpgrade.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_upgrade_list, parent,false);
        return  new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterUpgrade.RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        PojoUpgrade list=pojoUpgrades.get(position);
        IndianCurrencyFormat = new DecimalFormat("##,##,###");
        holder.pack_name.setText(list.getPackage_name());
        holder.tv_amount_buy.setText(IndianCurrencyFormat.format( list.getFinal_price()));


        if(list.getIsSelected().equals("y"))
        {
          //  holder.tv_not_appluy.setBackgroundTintList(context.getColorStateList(R.color.black));
            holder.tv_not_appluy.setTextColor(context.getColorStateList(R.color.white));
            holder.rl_apply.setBackgroundTintList(context.getColorStateList(R.color.white));
            holder.rl_upgra.setBackgroundResource(R.drawable.white_mar_bg);
            holder.rl_upgra.setBackgroundTintList(context.getColorStateList(R.color.newgreen));
            holder.iv_tick.setVisibility(View.VISIBLE);
        }
        else
        {
           if(SPHelper.is_upgrade.equalsIgnoreCase("y"))
           {
               if(SPHelper.gone_to.equals("add_on")){
                  // holder.tv_not_appluy.setBackgroundTintList(null);
                   holder.tv_not_appluy.setTextColor(context.getColorStateList(R.color.white));
                   holder.rl_apply.setBackgroundTintList(context.getColorStateList(R.color.black));
                   holder.rl_upgra.setBackgroundResource(R.drawable.white_mar_bg);
                   holder.rl_upgra.setBackgroundTintList(null);
                   holder.iv_tick.setVisibility(View.INVISIBLE);
               }else {
                   if(selectedPosition==position)
                   {

                      // holder.tv_not_appluy.setBackgroundTintList(context.getColorStateList(R.color.black));
                       holder.tv_not_appluy.setTextColor(context.getColorStateList(R.color.white));
                       holder.rl_apply.setBackgroundTintList(context.getColorStateList(R.color.white));
                       holder.rl_upgra.setBackgroundResource(R.drawable.white_mar_bg);
                       holder.rl_upgra.setBackgroundTintList(context.getColorStateList(R.color.newgreen));
                       holder.iv_tick.setVisibility(View.VISIBLE);
                       pojoUpgrades.get(0).setIsSelected("y");
                       SPHelper.upgrade_amount=list.getFinal_price();
                       SPHelper.sel_upgrade_pac_id=list.getPackage_id();
                       SPHelper.is_partial_pay=list.getPayAsyouGoEligibility().getIs_eligible();
                       SPHelper.per_amount=list.getPayAsyouGoEligibility().getPercentage_amount_to_pay();
                       Addons.getInstance().rl_disc.setVisibility(View.GONE);
                       Addons.getInstance().tv_dis_amount.setText("0");
                       SPHelper.disc_amount=0;
                       Addons.getInstance().coupon_label.setVisibility(View.GONE);
                       Addons.getInstance().rl_coupon_applied.setVisibility(View.INVISIBLE);
                       Addons.getInstance().rl_coupon_label.setVisibility(View.VISIBLE);
                       Addons.getInstance().get_update_amounnt();
                   }
                   else {
                      // holder.tv_not_appluy.setBackgroundTintList(null);
                       holder.tv_not_appluy.setTextColor(context.getColorStateList(R.color.white));
                       holder.rl_apply.setBackgroundTintList(context.getColorStateList(R.color.black));
                       holder.rl_upgra.setBackgroundResource(R.drawable.white_mar_bg);
                       holder.rl_upgra.setBackgroundTintList(null);
                       holder.iv_tick.setVisibility(View.INVISIBLE);
                   }
               }

           }
           else {
               // holder.tv_not_appluy.setBackgroundTintList(null);
                holder.tv_not_appluy.setTextColor(context.getColorStateList(R.color.white));
               holder.rl_apply.setBackgroundTintList(context.getColorStateList(R.color.black));
               holder.rl_upgra.setBackgroundResource(R.drawable.white_mar_bg);
               holder.rl_upgra.setBackgroundTintList(null);
               holder.iv_tick.setVisibility(View.INVISIBLE);
            }

        }
        holder.rl_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
//                if (selectedPosition >= 0)
//                    notifyItemChanged(selectedPosition);
//                selectedPosition = holder.getAdapterPosition();

                if(SPHelper.is_upgrade.equalsIgnoreCase("y"))
                {
                    if (position != RecyclerView.NO_POSITION)
                    {
                        selectedPosition = holder.getAdapterPosition();
                    }
                }

                if(list.getIsSelected().equals("y"))
                {
                    for (int i=0;i<pojoUpgrades.size();i++)
                    {
                        if (i == position)
                        {
                            pojoUpgrades.get(i).setIsSelected("n");
                            SPHelper.sel_upgrade_pac_id="";
                            Addons.getInstance().rl_disc.setVisibility(View.GONE);
                            Addons.getInstance().tv_dis_amount.setText("0");
                            Addons.getInstance().coupon_label.setVisibility(View.GONE);
                            Addons.getInstance().rl_coupon_applied.setVisibility(View.INVISIBLE);
                            Addons.getInstance().rl_coupon_label.setVisibility(View.VISIBLE);
                            SPHelper.is_partial_pay="";
                            SPHelper.upgrade_amount=SPHelper.pack_amount;
                            SPHelper.final_per=SPHelper.per_amount;
                            Addons.getInstance().get_update_amounnt();
                        }
                    }

                }

                else {
                    // subPackLists.get(position).setIsSelected("y");
                    for (int i=0;i<pojoUpgrades.size();i++)
                    {
                        if (i == position)
                        {
                            pojoUpgrades.get(i).setIsSelected("y");
                            SPHelper.upgrade_amount=list.getFinal_price();
                            SPHelper.sel_upgrade_pac_id=list.getPackage_id();
                            SPHelper.is_partial_pay=list.getPayAsyouGoEligibility().getIs_eligible();
                            SPHelper.final_per=list.getPayAsyouGoEligibility().getPercentage_amount_to_pay();
                            Addons.getInstance().rl_disc.setVisibility(View.GONE);
                            Addons.getInstance().tv_dis_amount.setText("0");
                            SPHelper.disc_amount=0;
                            Addons.getInstance().coupon_label.setVisibility(View.GONE);
                            Addons.getInstance().rl_coupon_applied.setVisibility(View.INVISIBLE);
                            Addons.getInstance().rl_coupon_label.setVisibility(View.VISIBLE);
                            Addons.getInstance().get_update_amounnt();
                        }
                        else {
                            pojoUpgrades.get(i).setIsSelected("n");
                        }
                    }
                }
                notifyDataSetChanged();

            }
        });

        holder.rl_ser_det.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                SPHelper.it_is="upgrade";

                PopupSelectPack bottomSheetDialogFragment = new PopupSelectPack();
                bottomSheetDialogFragment.pojoSpecificPacLists=list.getProductList();
                bottomSheetDialogFragment.show(((FragmentActivity)context).getSupportFragmentManager(),
                        bottomSheetDialogFragment.getTag());
            }
        });
    }

    @Override
    public int getItemCount() {
        return pojoUpgrades.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView tv_not_appluy,pack_name,tv_amount_buy;
        RelativeLayout rl_apply,rl_ser_det,rl_upgra;
        ImageView iv_tick;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_not_appluy=itemView.findViewById(R.id.tv_not_appluy);
            pack_name=itemView.findViewById(R.id.pack_name);
            tv_amount_buy=itemView.findViewById(R.id.tv_amount_buy);
            rl_apply=itemView.findViewById(R.id.rl_apply);
            rl_ser_det=itemView.findViewById(R.id.rl_ser_det);
            rl_upgra=itemView.findViewById(R.id.rl_upgra);
            iv_tick=itemView.findViewById(R.id.iv_tick);
        }
    }
}
