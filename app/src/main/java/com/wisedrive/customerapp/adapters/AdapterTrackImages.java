package com.wisedrive.customerapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.pojos.PojoTrackImages;

import java.util.ArrayList;

public class AdapterTrackImages extends  RecyclerView.Adapter<AdapterTrackImages.RecyclerViewHolder>{
    ArrayList<PojoTrackImages> pojoTrackImages;
    Context context;

    public AdapterTrackImages(ArrayList<PojoTrackImages> pojoTrackImages, Context context) {
        this.pojoTrackImages = pojoTrackImages;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterTrackImages.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_invoices_list, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTrackImages.RecyclerViewHolder holder, int position) {

        holder.upload_iv1.setImageResource(R.drawable.icon_noimage);
    }

    @Override
    public int getItemCount() {
        return pojoTrackImages.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        ImageView upload_iv1;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            upload_iv1=itemView.findViewById(R.id.upload_iv1);
        }
    }
}
