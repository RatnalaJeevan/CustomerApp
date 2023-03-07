package com.wisedrive.customerapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.pojos.PojoInvoicesList;

import java.util.ArrayList;

public class AdapterInvoicesList extends  RecyclerView.Adapter<AdapterInvoicesList.RecyclerViewHolder>{
    ArrayList<PojoInvoicesList> pojoInvoicesLists;
    Context context;

    public AdapterInvoicesList(ArrayList<PojoInvoicesList> pojoInvoicesLists, Context context) {
        this.pojoInvoicesLists = pojoInvoicesLists;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterInvoicesList.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_invoices_list, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterInvoicesList.RecyclerViewHolder holder, int position) {

        PojoInvoicesList recyclerdata=pojoInvoicesLists.get(position);
        if(recyclerdata.getInvoice_image()!=null||!recyclerdata.getInvoice_image().equals("")
                ||!recyclerdata.getInvoice_image().equals("null"))
        {
            Glide.with(context).load(recyclerdata.getInvoice_image()).placeholder(R.drawable.icon_noimage).into(holder.upload_iv1);
        }
    }

    @Override
    public int getItemCount() {
        return pojoInvoicesLists.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder
    {
        ImageView upload_iv1;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            upload_iv1=itemView.findViewById(R.id.upload_iv1);

        }
    }
}
