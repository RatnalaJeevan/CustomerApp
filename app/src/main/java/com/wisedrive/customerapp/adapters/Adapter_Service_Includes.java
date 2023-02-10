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

import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.pojos.Pojo_Service_Includes;
import com.wisedrive.customerapp.pojos.Pojo_Service_list;

import java.util.ArrayList;

public class Adapter_Service_Includes extends RecyclerView.Adapter<Adapter_Service_Includes.MyViewHolder> {
    Context context;
    private View view;
    ArrayList<Pojo_Service_Includes> pojo_service_includesArrayList;

    public Adapter_Service_Includes(Context context, ArrayList<Pojo_Service_Includes> pojo_service_includesArrayList) {
        this.context = context;
        this.pojo_service_includesArrayList = pojo_service_includesArrayList;
    }

    @Override
    public Adapter_Service_Includes.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_service_includes, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Service_Includes.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Pojo_Service_Includes list =pojo_service_includesArrayList.get(position);
        holder.text_service_name.setText(list.getText_service_name());
        holder.tv_description.setText(list.getTv_description());
        holder.image_logo.setImageResource(list.getImage_logo());

    }

    @Override
    public int getItemCount() {
        return pojo_service_includesArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView text_service_name,tv_description;
        ImageView image_logo;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            text_service_name = itemView.findViewById(R.id.text_service_name);
            tv_description= itemView.findViewById(R.id.tv_description);
            image_logo = itemView.findViewById(R.id.image_logo);



        }
    }
}
