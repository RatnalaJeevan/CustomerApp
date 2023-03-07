package com.wisedrive.customerapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.Pojo_Q_And_A;
import com.wisedrive.customerapp.pojos.Pojo_yes_no;

import org.json.JSONObject;

import java.util.ArrayList;

public class Adapter_Yes_No extends RecyclerView.Adapter<Adapter_Yes_No .MyViewHolder> {
    Context context;
    private View view;
    CardView cardview;
    ArrayList<Pojo_yes_no> pojo_yes_noArrayList;
    private int selectedPosition = -1;

    public Adapter_Yes_No (Context context, ArrayList<Pojo_yes_no> pojo_yes_noArrayList) {
        this.context = context;
        this.pojo_yes_noArrayList = pojo_yes_noArrayList;
    }

    @Override
    public Adapter_Yes_No .MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_yes_no,parent,false );
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Yes_No .MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Pojo_yes_no list = pojo_yes_noArrayList.get(position);
        holder.tv_yes.setText(list.getAnswer());
        if (list.getIsSelected().equals("y")) {
            holder.rl_yes.setBackgroundResource(R.drawable.rl_yes);
            holder.tv_yes.setTextColor(Color.parseColor("#0619C3"));

        }
        else {
            holder.rl_yes.setBackgroundResource(R.drawable.rl_no);
            holder.tv_yes.setTextColor(Color.parseColor("#aeaeb2"));
        }

        holder.rl_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i = 0; i < pojo_yes_noArrayList.size(); i++)
                {
                    if (i == position)
                    {
                        pojo_yes_noArrayList.get(i).setIsSelected("y");
                    } else {
                        pojo_yes_noArrayList.get(i).setIsSelected("n");
                    }
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return pojo_yes_noArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_yes;
        RelativeLayout rl_yes;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_yes = itemView.findViewById(R.id.tv_yes);
            rl_yes=itemView.findViewById(R.id.rl_yes);


        }
    }
}
