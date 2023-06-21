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
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.AddYourCar;
import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.Pojo_Select_Model;

import java.util.ArrayList;

public class Adapter_Select_Model extends RecyclerView.Adapter<Adapter_Select_Model.MyViewHolder> {

    private AddYourCar myFragment;

    Context context;
    private View view;
    ArrayList<Pojo_Select_Model> pojo_select_modelArrayList;
    private int selectedPosition = -1;



    public Adapter_Select_Model(AddYourCar myFragment, ArrayList<Pojo_Select_Model> pojo_select_modelArrayList) {
        this.myFragment = myFragment;
        this.view = view;
        this.pojo_select_modelArrayList = pojo_select_modelArrayList;
    }
    public Adapter_Select_Model.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_model_list, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Select_Model.MyViewHolder holder,  int position) {
        Pojo_Select_Model recyclerdata = pojo_select_modelArrayList.get(position);
        holder.tv_model.setText(recyclerdata.getCar_model());


        holder.rl_select_model.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.carmodelid=recyclerdata.getModel_id();
                SPHelper.car_model_name=recyclerdata.getCar_model();
                if (selectedPosition >= 0)
                    notifyItemChanged(selectedPosition);
                selectedPosition = holder.getAdapterPosition();
                notifyItemChanged(selectedPosition);

                myFragment.get_basic_details();

            }
        });

    }
    @Override
    public int getItemCount() {
        return pojo_select_modelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_model ;
        RelativeLayout rl_select_model;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_model = itemView.findViewById(R.id.tv_model);
            rl_select_model=(RelativeLayout) view.findViewById(R.id.rl_select_model);
        }
    }


}
