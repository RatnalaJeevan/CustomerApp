package com.wisedrive.customerapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.pojos.Pojo_Q_And_A;
import com.wisedrive.customerapp.pojos.Pojo_yes_no;

import java.util.ArrayList;

public class Adapter_Q_And_A  extends RecyclerView.Adapter<Adapter_Q_And_A .MyViewHolder> {
    Context context;
    private View view;
    CardView cardview;
    ArrayList<Pojo_Q_And_A> pojo_q_and_aArrayList;

    RecyclerView rv_yes_no;
    Adapter_Yes_No adapter_yes_no;
    ArrayList<Pojo_yes_no> pojo_yes_noArrayList;



    public Adapter_Q_And_A (Context context, ArrayList<Pojo_Q_And_A> pojo_q_and_aArrayList) {
        this.context = context;
        this.pojo_q_and_aArrayList = pojo_q_and_aArrayList;


    }

    @Override
    public Adapter_Q_And_A .MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_q_and_a,null );
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Q_And_A .MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Pojo_Q_And_A list = pojo_q_and_aArrayList.get(position);
        holder.tv_question.setText(list.getTv_question());
        holder.tv_select_option.setText(list.getTv_select_option());


        pojo_yes_noArrayList = new ArrayList();
        pojo_yes_noArrayList.add(new Pojo_yes_no("Yes"));
        pojo_yes_noArrayList.add(new Pojo_yes_no("No"));
        adapter_yes_no= new Adapter_Yes_No(context,  pojo_yes_noArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        rv_yes_no.setLayoutManager(linearLayoutManager);
        rv_yes_no.setAdapter(adapter_yes_no);



    }

    @Override
    public int getItemCount() {
        return pojo_q_and_aArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_question,tv_select_option;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_question = itemView.findViewById(R.id.tv_question);
            tv_select_option = (TextView) itemView.findViewById(R.id.tv_select_option);
            rv_yes_no=(RecyclerView) itemView.findViewById(R.id.rv_yes_no);


        }
    }



}
