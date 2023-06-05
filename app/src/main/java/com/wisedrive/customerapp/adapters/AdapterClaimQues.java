package com.wisedrive.customerapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.pojos.Pojo_Q_And_A;
import com.wisedrive.customerapp.pojos.Pojo_yes_no;

import java.util.ArrayList;

public class AdapterClaimQues extends RecyclerView.Adapter<AdapterClaimQues.MyViewHolder>
{
    Context context;
    ArrayList<Pojo_Q_And_A> pojo_q_and_aArrayList;
    ArrayList<Pojo_yes_no> pojo_yes_noArrayList;
    public AdapterClaimQues(Context context, ArrayList<Pojo_Q_And_A> pojo_q_and_aArrayList) {
        this.context = context;
        this.pojo_q_and_aArrayList = pojo_q_and_aArrayList;
    }

    @NonNull
    @Override
    public AdapterClaimQues.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_q_and_a, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterClaimQues.MyViewHolder holder, int position) {
        Pojo_Q_And_A list = pojo_q_and_aArrayList.get(position);
        holder.tv_question.setText(list.getSymptom_name());

        inside_answer_list(holder.rv_yes_no,position);
    }

    @Override
    public int getItemCount() {
        return pojo_q_and_aArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_question;
        RecyclerView rv_yes_no;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_question = itemView.findViewById(R.id.tv_question);
            rv_yes_no=itemView.findViewById(R.id.rv_yes_no);
        }
    }

    private void inside_answer_list(RecyclerView recyclerView, int sel_pos)
    {
        pojo_yes_noArrayList=new ArrayList<>();
        pojo_yes_noArrayList = pojo_q_and_aArrayList.get(sel_pos).getAnswerlist();

        AdapterClaimAns adapter_feature_list = new AdapterClaimAns(context, pojo_yes_noArrayList);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager1);
        recyclerView.setAdapter(adapter_feature_list);
        adapter_feature_list.notifyDataSetChanged();
    }
}
