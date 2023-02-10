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
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.pojos.Pojo_Class_Plan_2;
import com.wisedrive.customerapp.pojos.Pojo_Service_list;

import java.util.ArrayList;

public class Adapter_Service_List extends RecyclerView.Adapter<Adapter_Service_List.MyViewHolder>  {
    Context context;
    private View view;
    ArrayList<Pojo_Service_list> pojo_service_listArrayList;

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
        Pojo_Service_list list = pojo_service_listArrayList.get(position);
        holder.text_service_name.setText(list.getService_name());
        holder.tv_description.setText(list.getTv_description());
        holder.tv_description_lines.setText(list.getTv_description_lines());
        holder.image_logo.setImageResource(list.getImage_logo());
        holder.relative_layout_services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pojo_service_listArrayList.get(position).isVisible){
                    holder.tv_description_lines.setVisibility(View.GONE);
                    holder.description_page.setVisibility(View.GONE);
                    holder.tv_description.setVisibility(View.VISIBLE);
                    pojo_service_listArrayList.get(position).isVisible=false;
                    holder.plus_icon.setVisibility(View.VISIBLE);
                    holder.minus_icon.setVisibility(View.INVISIBLE);


                }
                else {
                    holder.tv_description_lines.setVisibility(View.VISIBLE);
                    holder.description_page.setVisibility(View.VISIBLE);
                    holder.tv_description.setVisibility(View.VISIBLE);
                    pojo_service_listArrayList.get(position).isVisible=true;
                   holder.plus_icon.setVisibility(View.INVISIBLE);
                    holder.minus_icon.setVisibility(View.VISIBLE);

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return pojo_service_listArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView text_service_name, tv_description,tv_description_lines;
        ImageView image_logo,plus_icon,minus_icon;
        RelativeLayout relative_layout_services,description_page;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            text_service_name= itemView.findViewById(R.id.text_service_name);
            tv_description= itemView.findViewById(R.id.tv_description);
            tv_description_lines = itemView.findViewById(R.id.tv_description_lines);
            image_logo = itemView.findViewById(R.id.image_logo);
            relative_layout_services=(RelativeLayout) view.findViewById(R.id.relative_layout_services);
            description_page=(RelativeLayout) view.findViewById(R.id.description_page);
            plus_icon = itemView.findViewById(R.id.plus_icon);
            minus_icon = itemView.findViewById(R.id.minus_icon);


        }


    }
}
