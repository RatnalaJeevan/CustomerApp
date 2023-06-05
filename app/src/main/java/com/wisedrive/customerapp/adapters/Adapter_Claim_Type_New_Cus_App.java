package com.wisedrive.customerapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import carbon.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.Pojo_Claim_Type_New_Cus_App;

import java.util.ArrayList;



public class Adapter_Claim_Type_New_Cus_App extends RecyclerView.Adapter<Adapter_Claim_Type_New_Cus_App.MyViewHolder> {
    Context context;
    private View view;
    ArrayList<Pojo_Claim_Type_New_Cus_App> pojo_claim_typeArrayList;
    private int selectedPosition = -1;

    public Adapter_Claim_Type_New_Cus_App(Context context, ArrayList<Pojo_Claim_Type_New_Cus_App>  pojo_claim_typeArrayList) {
        this.context = context;
        this. pojo_claim_typeArrayList= pojo_claim_typeArrayList;
    }

    @Override
    public Adapter_Claim_Type_New_Cus_App.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_claim_type_new_customer_app, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Claim_Type_New_Cus_App.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Pojo_Claim_Type_New_Cus_App list = pojo_claim_typeArrayList.get(position);
        holder. tv_claim_type.setText(list.getClaim_name());
        if (selectedPosition == position) {
            holder.rl_select_claim.setSelected(true); //using selector drawable
            holder.rl_select_claim.setBackgroundResource(R.color.black);
            holder. tv_claim_type.setTextColor(Color.parseColor("#FFFFFFFF"));

        } else {
            holder.rl_select_claim.setSelected(false);
            holder.rl_select_claim.setBackgroundResource(R.drawable.grey_marg);
            holder. tv_claim_type.setTextColor(Color.parseColor("#252a40"));
        }

        holder.rl_select_claim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.claim_type_id= list.getClaim_type_id();
                SPHelper.qa_list=new ArrayList<>();
                SPHelper.answer_details=new ArrayList<>();
                SPHelper.answerlist=new ArrayList<>();
                if (selectedPosition >= 0)
                    notifyItemChanged(selectedPosition);
                selectedPosition = holder.getAdapterPosition();
                notifyItemChanged(selectedPosition);
            }
        });


    }

    @Override
    public int getItemCount() {
        return  pojo_claim_typeArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView  tv_claim_type;
        RelativeLayout rl_select_claim;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_claim_type= itemView.findViewById(R.id.tv_claim_type);
            rl_select_claim=(RelativeLayout)itemView .findViewById(R.id.rl_select_claim);




        }
    }
}
