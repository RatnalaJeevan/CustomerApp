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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.Pojo_Class_Plan_2;
import com.wisedrive.customerapp.pojos.Pojo_Description_lines;
import com.wisedrive.customerapp.pojos.Pojo_Service_list;

import java.util.ArrayList;

public class Adapter_Service_List extends RecyclerView.Adapter<Adapter_Service_List.MyViewHolder>  {
    Context context;
    private View view;
    ArrayList<Pojo_Service_list> pojo_service_listArrayList;
    ArrayList<Pojo_Description_lines> pojo_description_linesArrayList;
    Adapter_Description_lines adapter_description_lines;
    RecyclerView rv_description_lines;
    public Adapter_Service_List(Context context, ArrayList<Pojo_Service_list> pojo_service_listArrayList) {
        this.context = context;
        this.pojo_service_listArrayList = pojo_service_listArrayList;
    }

    @Override
    public Adapter_Service_List.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_list_services, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Service_List.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Pojo_Service_list recyclerdata = pojo_service_listArrayList.get(position);
        holder.text_service_name.setText(recyclerdata.getPackage_name());
       holder.tv_description.setText(recyclerdata.getPackage_description());

       Glide.with(context).load(recyclerdata.getIcon_url()).placeholder(R.drawable.service_image).into(holder.image_logo);

       if(position==0)
       {
           holder.description_page.setVisibility(View.VISIBLE);
           pojo_service_listArrayList.get(position).isVisible=true;
           holder.plus_icon.setVisibility(View.INVISIBLE);
           holder.minus_icon.setVisibility(View.VISIBLE);
       }

        holder.rl_max.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pojo_service_listArrayList.get(position).isVisible){

                    holder.description_page.setVisibility(View.GONE);
                    pojo_service_listArrayList.get(position).isVisible=false;
                    holder.plus_icon.setVisibility(View.VISIBLE);
                    holder.minus_icon.setVisibility(View.INVISIBLE);
                }
                else {

                    holder.description_page.setVisibility(View.VISIBLE);
                    pojo_service_listArrayList.get(position).isVisible=true;
                    holder.plus_icon.setVisibility(View.INVISIBLE);
                    holder.minus_icon.setVisibility(View.VISIBLE);

                }
            }
        });
        pojo_description_linesArrayList = new ArrayList();
        pojo_description_linesArrayList=recyclerdata.getServiceIncludes();
        adapter_description_lines= new Adapter_Description_lines(context, pojo_description_linesArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rv_description_lines.setLayoutManager(linearLayoutManager);
        rv_description_lines.setAdapter(adapter_description_lines);
        adapter_description_lines.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return pojo_service_listArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView text_service_name, tv_description,tv_description_lines;
        ImageView image_logo,plus_icon,minus_icon;
        RelativeLayout rl_max,description_page;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            rv_description_lines=itemView.findViewById(R.id.rv_description_lines);
            text_service_name= itemView.findViewById(R.id.text_service_name);
            tv_description= itemView.findViewById(R.id.tv_description);
            tv_description_lines = itemView.findViewById(R.id.tv_description_lines);
            image_logo = itemView.findViewById(R.id.image_logo);
            rl_max=itemView.findViewById(R.id.rl_max);
            description_page=itemView.findViewById(R.id.description_page);
            plus_icon = itemView.findViewById(R.id.plus_icon);
            minus_icon = itemView.findViewById(R.id.minus_icon);


        }


    }
}
