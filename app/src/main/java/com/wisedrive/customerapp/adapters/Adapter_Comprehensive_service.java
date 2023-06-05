package com.wisedrive.customerapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.Warranty_Description;
import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.Pojo_Comprehensive_Plans;

import java.util.ArrayList;

public class Adapter_Comprehensive_service extends RecyclerView.Adapter<Adapter_Comprehensive_service.MyViewHolder> {
    Context context;
    private View view;
    CardView cardview;
    ArrayList<Pojo_Comprehensive_Plans> pojo_comprehensive_plansArrayList;

    public Adapter_Comprehensive_service(Context context, ArrayList<Pojo_Comprehensive_Plans> pojo_comprehensive_plansArrayList) {
        this.context = context;
        this.pojo_comprehensive_plansArrayList = pojo_comprehensive_plansArrayList;
    }

    @Override
    public Adapter_Comprehensive_service.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_comprehensive_plans,  parent,false);
        view.getLayoutParams().width = (int) (getScreenWidth()/1.09);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Comprehensive_service.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Pojo_Comprehensive_Plans list = pojo_comprehensive_plansArrayList.get(position);
        holder.extended_warranty.setText(list.getExtended_warranty());
        holder.text_service_maintanance.setText(list.getText_service_maintanance());
        holder.text_view_rupee.setText(list.getText_view_rupee());
        holder.text_view_percent.setText(list.getText_view_percent());
        cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(), Warranty_Description.class);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pojo_comprehensive_plansArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView extended_warranty, text_service_maintanance, text_view_rupee, text_view_percent;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            extended_warranty = itemView.findViewById(R.id.extended_warranty);
            text_service_maintanance = (TextView) itemView.findViewById(R.id.text_service_maintanance);
            text_view_rupee = (TextView) itemView.findViewById(R.id.text_view_rupee);
            text_view_percent = (TextView) itemView.findViewById(R.id.text_view_percent);
            cardview=(CardView) view.findViewById(R.id.cardview);

        }
    }

        public int getScreenWidth() {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            return size.x;

        }







}






