package com.wisedrive.customerapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.pojos.Pojo_Q_And_A;
import com.wisedrive.customerapp.pojos.Pojo_yes_no;

import java.util.ArrayList;

public class AdapterClaimAns extends RecyclerView.Adapter<AdapterClaimAns.MyViewHolder>

{
    Context context;
    ArrayList<Pojo_yes_no> pojo_yes_noArrayList;

    public AdapterClaimAns (Context context, ArrayList<Pojo_yes_no> pojo_yes_noArrayList) {
        this.context = context;
        this.pojo_yes_noArrayList = pojo_yes_noArrayList;
    }
    @NonNull
    @Override
    public AdapterClaimAns.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_yes_no,parent,false );
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterClaimAns.MyViewHolder holder, int position) {
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

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_yes;
        RelativeLayout rl_yes;
        ImageView dot_img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_yes = itemView.findViewById(R.id.tv_yes);
            rl_yes=itemView.findViewById(R.id.rl_yes);
            dot_img=itemView.findViewById(R.id.dot_img);
        }
    }
}
