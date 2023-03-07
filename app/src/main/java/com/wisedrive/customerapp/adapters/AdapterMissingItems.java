package com.wisedrive.customerapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.SubmitMissingItems;
import com.wisedrive.customerapp.pojos.PojoLostItems;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.wisedrive.customerapp.R.drawable.circle_orange;
import static com.wisedrive.customerapp.R.drawable.submit_cardview;
import static com.wisedrive.customerapp.R.drawable.submit_missing_items;

public class AdapterMissingItems extends  RecyclerView.Adapter<AdapterMissingItems.RecyclerViewHolder>{
    ArrayList<PojoLostItems> items_list;
    Context context;
    public AdapterMissingItems(ArrayList<PojoLostItems> items_list, Context context) {
        this.items_list = items_list;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public AdapterMissingItems.RecyclerViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_missing, parent, false);
        return new RecyclerViewHolder(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterMissingItems.RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        PojoLostItems recyclerdata=items_list.get(position);
        holder.missing_item.setText(recyclerdata.getName());
        SubmitMissingItems obj=(SubmitMissingItems) context;
        if (items_list.get(position).getIsSelected().equalsIgnoreCase("y")) {
            holder.missing_item.setBackground(context.getDrawable(submit_cardview));
            holder.missing_item.setTextColor(Color.parseColor("#FFFFFFFF"));
        } else {
            holder.missing_item.setBackground(context.getDrawable(submit_missing_items));
            holder.missing_item.setTextColor(Color.parseColor("#606060"));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (items_list.get(position).getIsSelected().equalsIgnoreCase("n"))
                {
                    items_list.get(position).setIsSelected("y");
                }
                else
                {
                    items_list.get(position).setIsSelected("n");
                }
                obj.adapterMissingItems.notifyDataSetChanged();

            }
        });
    }

    @Override
    public int getItemCount() {
        return items_list.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView missing_item;
        public RecyclerViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            missing_item=itemView.findViewById(R.id.missing_item);

        }
    }
}
