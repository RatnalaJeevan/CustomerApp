package com.wisedrive.customerapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.commonclasses.Common;
import com.wisedrive.customerapp.pojos.PojoKmsData;

import java.util.ArrayList;

public class AdapterKmsData extends  RecyclerView.Adapter<AdapterKmsData.RecyclerViewHolder>{
    ArrayList<PojoKmsData> pojoKmsData;
    Context context;

    public AdapterKmsData(ArrayList<PojoKmsData> pojoKmsData, Context context) {
        this.pojoKmsData = pojoKmsData;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterKmsData.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_kms_data, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterKmsData.RecyclerViewHolder holder, int position) {
        PojoKmsData data=pojoKmsData.get(position);
        holder.validity.setText(data.getOdometer()+" kms");
        holder.odo_upd_on.setText(Common.getDateFromString(data.getOdometer_updated_on()));
    }

    @Override
    public int getItemCount() {

        if(pojoKmsData.size()<=3){
            return pojoKmsData.size();
        }else {
            return 3;
        }

    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView validity,odo_upd_on;
        ImageView odo;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            odo=itemView.findViewById(R.id.odo);
            validity=itemView.findViewById(R.id.validity);
            odo_upd_on=itemView.findViewById(R.id.odo_upd_on);
        }
    }
}
