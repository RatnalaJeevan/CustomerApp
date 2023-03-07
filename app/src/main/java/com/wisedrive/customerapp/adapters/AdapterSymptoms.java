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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.InitiateClaim;
import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.PojoClaimTypes;
import com.wisedrive.customerapp.pojos.PojoSymptoms;

import org.jetbrains.annotations.NotNull;

import java.security.SecurityPermission;
import java.util.ArrayList;

public class AdapterSymptoms extends  RecyclerView.Adapter<AdapterSymptoms.RecyclerViewHolder>{
    ArrayList<PojoSymptoms> symptoms;
    Context context;

    public AdapterSymptoms(ArrayList<PojoSymptoms> symptoms, Context context) {
        this.symptoms = symptoms;
        this.context = context;
    }
    @NonNull
    @NotNull
    @Override
    public AdapterSymptoms.RecyclerViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_symptom_list, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterSymptoms.RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        PojoSymptoms recyclerdata=symptoms.get(position);
        holder.symptoms.setText(recyclerdata.getSymptom_name());
        InitiateClaim obj=(InitiateClaim) context;


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //SPHelper.selected_symptom=symptoms.get(position).getSymptom_id();
                if (symptoms.get(position).getIsSelected().equalsIgnoreCase("y"))
                {
                    symptoms.get(position).setIsSelected("n");
                } else {
                    symptoms.get(position).setIsSelected("y");
                }
                obj.adapterSymptoms.notifyDataSetChanged();
                InitiateClaim.getInstance().getselected_symptoms();
            }
        });
        if (symptoms.get(position).getIsSelected().equals("y"))
        {
            holder.rl_symptoms.setBackground(context.getDrawable(R.drawable.cardview_dealership));
            holder.rl_symptoms.setBackgroundTintList(ContextCompat.getColorStateList(context.getApplicationContext(),R.color.orange));
            holder.symptoms.setTextColor(Color.parseColor("#FFFFFFFF"));
        } else {
            holder.rl_symptoms.setBackground(context.getDrawable(R.drawable.cardview_dealership));
            holder.rl_symptoms.setBackgroundTintList(ContextCompat.getColorStateList(context.getApplicationContext(),R.color.white));
            holder.symptoms.setTextColor(Color.parseColor("#FF000000"));
        }

    }

    @Override
    public int getItemCount() {
        return symptoms.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView symptoms;
        RelativeLayout rl_symptoms;
        public RecyclerViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            symptoms=itemView.findViewById(R.id.symptom);
            rl_symptoms=itemView.findViewById(R.id.rl_symptoms);
        }
    }
}
