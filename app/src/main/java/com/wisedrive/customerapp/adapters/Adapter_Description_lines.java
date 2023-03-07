package com.wisedrive.customerapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.pojos.Pojo_Description_lines;

import java.util.ArrayList;

public class Adapter_Description_lines extends RecyclerView.Adapter<Adapter_Description_lines.MyViewHolder>  {
    Context context;
    private View view;
    ArrayList<Pojo_Description_lines> pojo_description_linesArrayList;

    public  Adapter_Description_lines(Context context, ArrayList<Pojo_Description_lines> pojo_description_linesArrayList) {
        this.context = context;
        this.view = view;
        this.pojo_description_linesArrayList =pojo_description_linesArrayList;
    }

    public  Adapter_Description_lines.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_description, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  Adapter_Description_lines.MyViewHolder holder,final int position) {
        Pojo_Description_lines recyclerdata = pojo_description_linesArrayList.get(position);
        holder.tv_description_lines.setText(recyclerdata.getActivity_name());
    }

    @Override
    public int getItemCount() {
        return pojo_description_linesArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_description_lines;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_description_lines=itemView.findViewById(R.id.tv_description_lines);
        }
    }
}
