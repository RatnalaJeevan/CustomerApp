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
import com.wisedrive.customerapp.pojos.Pojo_Service_Includes;
import com.wisedrive.customerapp.pojos.Pojo_Upgrade_Save;

import java.util.ArrayList;

public class Adapter_Upgrade_Save extends RecyclerView.Adapter<Adapter_Upgrade_Save.MyViewHolder> {
    Context context;
    private View view;
    ArrayList<Pojo_Upgrade_Save> pojo_upgrade_saveArrayList;

    public Adapter_Upgrade_Save(Context context, ArrayList<Pojo_Upgrade_Save>  pojo_upgrade_saveArrayList) {
        this.context = context;
        this. pojo_upgrade_saveArrayList =  pojo_upgrade_saveArrayList;
    }

    @Override
    public Adapter_Upgrade_Save.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_upgrade_save_list, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Upgrade_Save.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Pojo_Upgrade_Save list = pojo_upgrade_saveArrayList.get(position);
        holder.upgrade_list.setText(list.getUpgrade_list());
        holder.image.setImageResource(list.getImage());

    }

    @Override
    public int getItemCount() {
        return  pojo_upgrade_saveArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView upgrade_list ;
        ImageView image ;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            upgrade_list = itemView.findViewById(R.id.upgrade_list);
            image = itemView.findViewById(R.id.image);



        }
    }
}
