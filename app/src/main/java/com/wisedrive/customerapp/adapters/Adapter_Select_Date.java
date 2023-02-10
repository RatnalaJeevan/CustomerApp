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
import com.wisedrive.customerapp.pojos.Pojo_Additional_Services;
import com.wisedrive.customerapp.pojos.Pojo_Select_Date;

import java.util.ArrayList;

public class Adapter_Select_Date  extends RecyclerView.Adapter<Adapter_Select_Date.MyViewHolder> {
    Context context;
    private View view;
    ArrayList<Pojo_Select_Date> pojo_select_dateArrayList;
    private int selectedPosition = -1;

    public Adapter_Select_Date(Context context, ArrayList<Pojo_Select_Date> pojo_select_dateArrayList) {
        this.context = context;
        this.pojo_select_dateArrayList=pojo_select_dateArrayList;
    }

    @Override
    public Adapter_Select_Date.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_select_date, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Select_Date.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Pojo_Select_Date list = pojo_select_dateArrayList.get(position);
        holder.tv_date.setText(list.getTv_date());
        if (selectedPosition == position) {
            holder. rl_select_date.setSelected(true); //using selector drawable
            holder. rl_select_date.setBackgroundResource(R.drawable.select_green);
            holder.tv_date.setTextColor(Color.parseColor("#FFFFFFFF"));
            holder.white_check.setVisibility(View.VISIBLE);

        } else {
            holder. rl_select_date.setSelected(false);
            holder. rl_select_date.setBackgroundResource(R.drawable.rl_date_background);
            holder.tv_date.setTextColor(Color.parseColor("#c7c7cc"));
            holder.white_check.setVisibility(View.INVISIBLE);


        }

        holder. rl_select_date.setOnClickListener(new View.OnClickListener() {
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
        return pojo_select_dateArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_date;
        RelativeLayout rl_select_date;
        ImageView white_check;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_date = itemView.findViewById(R.id.tv_date);
            rl_select_date=itemView.findViewById(R.id.rl_select_date);
            white_check=itemView.findViewById(R.id.white_check);






        }
    }
}
