package com.wisedrive.customerapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.InitiateNewClaim;
import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.Pojo_Select_Date;
import com.wisedrive.customerapp.pojos.Pojo_Select_Your_Vehicle_no;

import java.util.ArrayList;

public class Adapter_Select_Your_Vehicle_No extends RecyclerView.Adapter<Adapter_Select_Your_Vehicle_No.MyViewHolder> {
    Context context;
    private View view;
    ArrayList<Pojo_Select_Your_Vehicle_no> pojo_select_your_vehicle_noArrayList;
    private int selectedPosition = -1;

    public Adapter_Select_Your_Vehicle_No(Context context, ArrayList<Pojo_Select_Your_Vehicle_no> pojo_select_your_vehicle_noArrayList) {
        this.context = context;
        this.pojo_select_your_vehicle_noArrayList=pojo_select_your_vehicle_noArrayList;

    }

    @Override
    public Adapter_Select_Your_Vehicle_No.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_select_your_vehicle, parent,false);
         view.getLayoutParams().width = (int) (getScreenWidth()/2);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Select_Your_Vehicle_No.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Pojo_Select_Your_Vehicle_no list = pojo_select_your_vehicle_noArrayList.get(position);
        holder.tv_vehicle_no.setText(list.getVehicle_no());

        if (selectedPosition == position) {
            holder.rl_vehicle_background.setSelected(true); //using selector drawable
            holder.rl_vehicle_background.setBackgroundResource(R.drawable.vehicle_blue_back);
            holder.tv_vehicle_no.setTextColor(Color.parseColor("#0619c3"));

        } else {
            holder.rl_vehicle_background.setSelected(false);
            holder.rl_vehicle_background.setBackgroundResource(R.drawable.rl_date_background);
            holder.tv_vehicle_no.setTextColor(Color.parseColor("#c7c7cc"));

        }

        holder.rl_vehicle_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.veh_no= list.getVehicle_no();
                SPHelper.claim_type_id="";
                SPHelper.qa_list=new ArrayList<>();
                SPHelper.answer_details=new ArrayList<>();
                SPHelper.answerlist=new ArrayList<>();
                SPHelper.selcted_veh_id=list.getVehicle_id();
                if (selectedPosition >= 0)
                    notifyItemChanged(selectedPosition);
                selectedPosition = holder.getAdapterPosition();
                notifyItemChanged(selectedPosition);

                ((InitiateNewClaim)context).get_claim_types();
                ((InitiateNewClaim)context).get_veh_image_list();
                ((InitiateNewClaim)context).rl.setVisibility(View.VISIBLE);

            }
        });

    }

    @Override
    public int getItemCount() {
        return pojo_select_your_vehicle_noArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_vehicle_no;
        RelativeLayout rl_vehicle_background;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_vehicle_no= itemView.findViewById(R.id.tv_vehicle_no);
            rl_vehicle_background= itemView.findViewById(R.id.rl_vehicle_background);




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
