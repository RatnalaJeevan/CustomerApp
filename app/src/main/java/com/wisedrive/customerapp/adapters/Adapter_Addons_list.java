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

import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.pojos.Pojo_Class_Addons_List;
import com.wisedrive.customerapp.pojos.Pojo_Combo_Plans;

import java.util.ArrayList;

public class Adapter_Addons_list  extends RecyclerView.Adapter<Adapter_Addons_list.MyViewHolder> {
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
    public void onBindViewHolder(@NonNull Adapter_Addons_list.MyViewHolder holder,  int position) {
        Pojo_Class_Addons_List list = pojo_class_addons_listArrayList.get(position);
        holder.tv_status_amount.setText(list.getTv_status_amount());
        holder.tv_select.setText(list.getTv_select());
        holder.tv_warranty_name.setText(list.getTv_warranty_name());
        holder. tv_description.setText(list.getTv_description());
        holder.tv_scratch_amount.setText(list.getTv_scratch_amount());
        holder.tv_amount.setText(list.getTv_amount());
        holder.tv_percent.setText(list.getTv_percent());
        if (pojo_class_addons_listArrayList.get(position).getId().equals("1")) {
            holder.r_l_status.setBackgroundResource(R.drawable.rl_background_1);
           ;


        } else if (pojo_class_addons_listArrayList.get(position).getId().equals("2")) {
            holder.r_l_status.setBackgroundResource(R.drawable.blue_background);




        } else if (pojo_class_addons_listArrayList.get(position).getId().equals("3")) {
            holder.r_l_status.setBackgroundResource(R.drawable.rl_background_1);



        } else if (pojo_class_addons_listArrayList.get(position).getId().equals("4")) {
            holder.r_l_status.setBackgroundResource(R.drawable.blue_background);



        } else if (pojo_class_addons_listArrayList.get(position).getId().equals("5")) {
            holder.r_l_status.setBackgroundResource(R.drawable.rl_background_1);




        } else if (pojo_class_addons_listArrayList.get(position).getId().equals("6")) {
            holder.r_l_status.setBackgroundResource(R.drawable.rl_background_1);




        } else if (pojo_class_addons_listArrayList.get(position).getId().equals("7")) {
            holder.r_l_status.setBackgroundResource(R.drawable.rl_background_1);



        }

        if (selectedPosition == position) {
            holder.rl_select_button.setSelected(true); //using selector drawable
            holder.rl_select_button.setBackgroundResource(R.drawable.select_green);
            holder.tv_select.setTextColor(Color.parseColor("#FFFFFFFF"));
            holder.white_check.setVisibility(View.VISIBLE);

        } else {
            holder.rl_select_button.setSelected(false);
            holder.rl_select_button.setBackgroundResource(R.drawable.select);
            holder.tv_select.setTextColor(Color.parseColor("#aeaeb2"));
            holder.white_check.setVisibility(View.INVISIBLE);


        }

        holder.rl_select_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedPosition >= 0)
                    notifyItemChanged(selectedPosition);
                selectedPosition = holder.getAdapterPosition();
                notifyItemChanged(selectedPosition);

            }
        });








    }

    @Override
    public int getItemCount() {
        return pojo_class_addons_listArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_status_amount,tv_warranty_name,tv_select,tv_description,tv_scratch_amount,tv_amount,tv_percent;
        RelativeLayout r_l_status;
        RelativeLayout rl_select_button;
        ImageView white_check;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_status_amount = itemView.findViewById(R.id.tv_status_amount);
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
