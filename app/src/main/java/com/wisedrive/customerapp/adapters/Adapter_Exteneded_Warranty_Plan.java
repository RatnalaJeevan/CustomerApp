package com.wisedrive.customerapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.Warranty_Description;
import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.pojos.Pojo_Extended_Warranty_Plan;

import java.util.ArrayList;

public class Adapter_Exteneded_Warranty_Plan extends RecyclerView.Adapter<Adapter_Exteneded_Warranty_Plan.MyViewHolder> {

    Context context;
    private View view;
    ArrayList<Pojo_Extended_Warranty_Plan>pojo_extended_warranty_planArrayList;

    public Adapter_Exteneded_Warranty_Plan(Context context, ArrayList<Pojo_Extended_Warranty_Plan>pojo_extended_warranty_planArrayList) {
        this.context = context;
        this.pojo_extended_warranty_planArrayList =pojo_extended_warranty_planArrayList;
    }

    @Override
    public Adapter_Exteneded_Warranty_Plan.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_extended_warranty_plan, parent,false);
       // view.getLayoutParams().width = (int) (getScreenWidth()/1.1);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Exteneded_Warranty_Plan.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Pojo_Extended_Warranty_Plan list = pojo_extended_warranty_planArrayList.get(position);
        holder. text_warranty_name.setText(list.getText_warranty_name());
        holder.text_amount.setText(list.getText_amount());
        holder.text_save_amount.setText(list.getText_save_amount());
        holder.rl_amount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(), Warranty_Description.class);
            //Bundle b = ActivityOptions.makeSceneTransitionAnimation((Plans_Activity_Page)context).toBundle();
                view.getContext().startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return pojo_extended_warranty_planArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image_icon;
        TextView text_warranty_name,text_amount,text_save_amount;
        RelativeLayout rl_amount;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            text_warranty_name = (TextView) itemView.findViewById(R.id.text_warranty_name);
            text_amount = (TextView) itemView.findViewById(R.id.text_amount);
            text_save_amount = (TextView) itemView.findViewById(R.id.text_save_amount);
            rl_amount=(RelativeLayout) view.findViewById(R.id.rl_amount);






        }

    }

  //  public int getScreenWidth() {
     //   WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
      //  Display display = wm.getDefaultDisplay();
       // Point size = new Point();
        //display.getSize(size);
     //   return size.x;
  //  }
}
