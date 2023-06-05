package com.wisedrive.customerapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.SelfInspection;
import com.wisedrive.customerapp.pojos.PojoInspA;
import com.wisedrive.customerapp.pojos.PojoInspQ;

import java.util.ArrayList;

public class AdapterInspQList extends RecyclerView.Adapter<AdapterInspQList.MyViewHolder>{

    ArrayList<PojoInspQ> pojoInspQS;
    Context context;
    RecyclerView rv_ans_list;
    AdapterInspAList adapterInspAList;
    ArrayList<PojoInspA> pojoInspAS;

    public AdapterInspQList(ArrayList<PojoInspQ> pojoInspQS, Context context) {
        this.pojoInspQS = pojoInspQS;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterInspQList.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_s_insp_q_list,  parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterInspQList.MyViewHolder holder, int position) {
        PojoInspQ data=pojoInspQS.get(position);
        holder.tv_ques.setText(data.getQuestion());

        pojoInspAS=new ArrayList<>();
        pojoInspAS=data.getAnswerList();
        adapterInspAList = new AdapterInspAList(pojoInspAS, context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        rv_ans_list.setLayoutManager(linearLayoutManager);
        rv_ans_list.setAdapter(adapterInspAList);



        //notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return pojoInspQS.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_ques;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_ques=itemView.findViewById(R.id.tv_ques);
            rv_ans_list=itemView.findViewById(R.id.rv_ans_list);
        }
    }
}
