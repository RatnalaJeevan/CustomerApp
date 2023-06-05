package com.wisedrive.customerapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.Activity_Q_And_A;
import com.wisedrive.customerapp.R;

import com.wisedrive.customerapp.pojos.Pojo_Q_And_A;
import com.wisedrive.customerapp.pojos.Pojo_yes_no;

import java.util.ArrayList;

public class Adapter_Yes_No extends RecyclerView.Adapter<Adapter_Yes_No.RecyclerViewHolder>
{
    Context context;
    ArrayList<Pojo_yes_no> pojo_yes_noArrayList;
    ArrayList<Pojo_Q_And_A> pojo_q_and_aArrayList;

    public Adapter_Yes_No (Context context, ArrayList<Pojo_yes_no> pojo_yes_noArrayList,
                           ArrayList<Pojo_Q_And_A> pojo_q_and_aArrayList) {
        this.context = context;
        this.pojo_yes_noArrayList = pojo_yes_noArrayList;
        this.pojo_q_and_aArrayList=pojo_q_and_aArrayList;
    }

    @NonNull
    @Override
    public Adapter_Yes_No.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_yes_no,parent,false );
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Yes_No.RecyclerViewHolder holder,
                                int position) {
        Pojo_yes_no list = pojo_yes_noArrayList.get(position);

        holder.tv_yes.setText(list.getAnswer());
        if (list.getIsSelected().equals("y")) {
            holder.dot_img.setVisibility(View.VISIBLE);
        }
        else {
            holder.dot_img.setVisibility(View.GONE);
        }

        holder.rl_yes.setOnClickListener(view ->
        {
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
        });

    }

    @Override
    public int getItemCount() {
        return pojo_yes_noArrayList.size();
    }

    public  class RecyclerViewHolder extends RecyclerView.ViewHolder
    {

        TextView tv_yes;
        RelativeLayout rl_yes;
        ImageView dot_img;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_yes = itemView.findViewById(R.id.tv_yes);
            rl_yes=itemView.findViewById(R.id.rl_yes);
            dot_img=itemView.findViewById(R.id.dot_img);
        }


    }
}
