package com.wisedrive.customerapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wisedrive.customerapp.PopUpUpdateKms;
import com.wisedrive.customerapp.PopupUploadPic;
import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.ShowDocImg;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.PojoDocs;

import java.util.ArrayList;

public class AdapterDocsImg extends RecyclerView.Adapter<AdapterDocsImg.RecyclerrViewHolder>{
    ArrayList<PojoDocs> pojoDocs;
    Context context;

    public AdapterDocsImg(ArrayList<PojoDocs> pojoDocs, Context context) {
        this.pojoDocs = pojoDocs;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterDocsImg.RecyclerrViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_docs_list, parent,false);
        return new RecyclerrViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDocsImg.RecyclerrViewHolder holder, int position) {

        PojoDocs data=pojoDocs.get(position);
        holder.doc_position.setText(data.getDocument_name());
        holder.doc_name.setText(data.getDocument_type());

        if(data.getDoc_status()==null||data.getDoc_status().equals(""))
        {
            holder.status.setText("Not Uploaded");
            holder.status.setTextColor(context.getResources().getColor(R.color.blue));
            holder.iv2.setImageResource(R.drawable.doc_upl);
        }
        else if(data.getDoc_status().equalsIgnoreCase("approved")||
        data.getDoc_status().equalsIgnoreCase("approve")){
            holder.status.setText(data.getDoc_status());
            holder.status.setTextColor(context.getResources().getColor(R.color.newgreen));
            holder.iv2.setImageResource(R.drawable.doc_appr);
        }else if(data.getDoc_status().equalsIgnoreCase("rejected")||
                data.getDoc_status().equalsIgnoreCase("reject")){
            holder.status.setTextColor(context.getResources().getColor(R.color.red));
            holder.status.setText("Upload Again");
            holder.iv2.setImageResource(R.drawable.upl_again);

        }else {

            if(data.getDocument_image().equals("")){
                holder.status.setText("Not Uploaded");
                holder.status.setTextColor(context.getResources().getColor(R.color.blue));
                holder.iv2.setImageResource(R.drawable.doc_upl);
            }else {
                holder.status.setText(data.getDoc_status());
                holder.iv2.setImageResource(R.drawable.not_app);
                holder.status.setTextColor(context.getResources().getColor(R.color.orange));
            }

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                SPHelper.doc_id=data.getDocument_id();
                SPHelper.doc_name=data.getDocument_type();
                SPHelper.doc_img=data.getDocument_image();
                if(data.getDoc_status()==null||data.getDoc_status().equals("")){
                    SPHelper.doc_edited="n";
                    PopupUploadPic pic=new PopupUploadPic();
                    pic.show(((FragmentActivity)context).getSupportFragmentManager(), pic.getTag());
                }else {
                    ShowDocImg pic=new ShowDocImg();
                    pic.show(((FragmentActivity)context).getSupportFragmentManager(), pic.getTag());
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return pojoDocs.size();
    }

    public class RecyclerrViewHolder extends RecyclerView.ViewHolder {

        TextView doc_position,doc_name,status;
        ImageView iv2;
        public RecyclerrViewHolder(@NonNull View itemView) {
            super(itemView);
            doc_name=itemView.findViewById(R.id.doc_name);
            doc_position=itemView.findViewById(R.id.doc_position);
            iv2=itemView.findViewById(R.id.iv2);
            status=itemView.findViewById(R.id.status);
        }
    }
}
