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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.SelfInspection;
import com.wisedrive.customerapp.pojos.PojoCarVideoList;

import java.util.ArrayList;

public class AdapterCarVideos extends RecyclerView.Adapter<AdapterCarVideos.MyViewHolder> {

    ArrayList<PojoCarVideoList> pojoCarVideoLists;
    Context context;
    public int adapter_position=0;
    public AdapterCarVideos(ArrayList<PojoCarVideoList> pojoCarVideoLists, Context context) {
        this.pojoCarVideoLists = pojoCarVideoLists;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterCarVideos.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_car_videos, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCarVideos.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        PojoCarVideoList data= pojoCarVideoLists.get(position);
        holder.title.setText(data.getPart_name());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter_position=position;
                ((SelfInspection)context).capture_video(position);

            }
        });

        // if "video"  parameter is null  and "taken video"  is also null means we need to show sample video.
        //else check if taken video is null then check if video is not null then  set video .
        //else set taken video.
        if (pojoCarVideoLists.get(position).getTaken_video() == null)
        {
            RequestOptions requestOptions = new RequestOptions();
            Glide.with(context)
                    .load(pojoCarVideoLists.get(position).getSample_video())
                    .apply(requestOptions)
                    .thumbnail(Glide.with(context).load(pojoCarVideoLists.get(position).getSample_video()))
                    .into(holder.iv_cw);

        } else {
            RequestOptions requestOptions = new RequestOptions();
            Glide.with(context)
                    .load(pojoCarVideoLists.get(position).getTaken_video())
                    .apply(requestOptions)
                    .thumbnail(Glide.with(context).load(pojoCarVideoLists.get(position).getTaken_video()))
                    .into(holder.iv_cw);
            //}
        }
    }

    @Override
    public int getItemCount() {
        return pojoCarVideoLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView iv_cw;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            iv_cw=itemView.findViewById(R.id.iv_cw);
        }
    }
}
