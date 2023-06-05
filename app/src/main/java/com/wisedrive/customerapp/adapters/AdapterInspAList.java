package com.wisedrive.customerapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.pojos.PojoInspA;

import java.util.ArrayList;

public class AdapterInspAList extends RecyclerView.Adapter<AdapterInspAList.MyViewHolder>{
    ArrayList<PojoInspA> pojoInspAS;
    Context context;

    public AdapterInspAList(ArrayList<PojoInspA> pojoInspAS, Context context) {
        this.pojoInspAS = pojoInspAS;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterInspAList.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_insp_answer_list,  parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterInspAList.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        PojoInspA data= pojoInspAS.get(position);
        holder.tv_ans.setText(data.getAnswer());

        if (data.getIsSelected().equals("y")) {
            holder.dot_img.setVisibility(View.VISIBLE);
            // holder.rl_yes.setBackgroundResource(R.drawable.rl_yes);
            // holder.tv_yes.setTextColor(Color.parseColor("#0619C3"));
        }
        else {
            holder.dot_img.setVisibility(View.GONE);
            // holder.rl_yes.setBackgroundResource(R.drawable.rl_no);
            // holder.tv_yes.setTextColor(Color.parseColor("#aeaeb2"));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i = 0; i < pojoInspAS.size(); i++)
                {
                    if (i == position)
                    {
                        pojoInspAS.get(i).setIsSelected("y");
                    } else {
                        pojoInspAS.get(i).setIsSelected("n");
                    }
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return pojoInspAS.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_ans;
        ImageView dot_img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_ans=itemView.findViewById(R.id.tv_ans);
            dot_img=itemView.findViewById(R.id.dot_img);

        }
    }
}
