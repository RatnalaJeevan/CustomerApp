package com.wisedrive.customerapp.adapters;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.Request_Inspection;
import com.wisedrive.customerapp.commonclasses.Common;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.Pojo_Additional_Services;
import com.wisedrive.customerapp.pojos.Pojo_Select_Date;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Adapter_Select_Date  extends RecyclerView.Adapter<Adapter_Select_Date.MyViewHolder> {
    Context context;
    private View view;
    ArrayList<Pojo_Select_Date> pojo_select_dateArrayList;
    private int selectedPosition = -1;
    public String server_date="",is_selcted="",datte="",newtime="",time="";
    DatePickerDialog date_picker;

    public Adapter_Select_Date(Context context, ArrayList<Pojo_Select_Date> pojo_select_dateArrayList) {
        this.context = context;
        this.pojo_select_dateArrayList=pojo_select_dateArrayList;
    }

    @Override
    public Adapter_Select_Date.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_select_date, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Select_Date.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Pojo_Select_Date recyclerdata = pojo_select_dateArrayList.get(position);

        if(position==0||position==1)
        {
            holder.tv_date.setText(recyclerdata.getDate());
        }else {
            holder.tv_date.setText(Common.getDateFromString(recyclerdata.getDate()));
        }

        if (recyclerdata.getIsSelected().equals("y"))
        {
            holder.rl_cal.setBackgroundResource(R.drawable.select_green);
            holder.rl_cal.setBackgroundTintList(ContextCompat.getColorStateList(context,R.color.white));
            holder.tv_date1.setVisibility(View.GONE);
            holder.white_check1.setVisibility(View.GONE);
            holder.rl_select_date.setBackgroundResource(R.drawable.select_green);
            holder.rl_select_date.setBackgroundTintList(ContextCompat.getColorStateList(context,R.color.black));
            holder.tv_date.setTextColor(Color.parseColor("#FFFFFFFF"));
            holder.white_check.setVisibility(View.VISIBLE);
            holder.tv_time1.setVisibility(View.GONE);

            if(SPHelper.current_page.equals("book")){
            }else {
                holder.tv_time.setVisibility(View.VISIBLE);
                holder.tv_time.setText(time);
                holder.tv_time.setTextColor(Color.parseColor("#FFFFFFFF"));
            }
        }
        else {
            holder.rl_cal.setBackgroundResource(R.drawable.select_green);
            holder.rl_cal.setBackgroundTintList(ContextCompat.getColorStateList(context,R.color.white));
            holder.tv_date1.setVisibility(View.GONE);
            holder.white_check1.setVisibility(View.GONE);
            holder.rl_select_date.setBackgroundResource(R.drawable.rl_date_background);
            holder.rl_select_date.setBackgroundTintList(ContextCompat.getColorStateList(context,R.color.background));
            holder.tv_date.setTextColor(Color.parseColor("#606060"));
            holder.white_check.setVisibility(View.INVISIBLE);
            holder.tv_time1.setVisibility(View.GONE);
            holder.tv_time.setVisibility(View.GONE);
           // holder.tv_time.setTextColor(Color.parseColor("#FFFFFFFF"));
        }

        if(position==5 )
        {
            holder.rl_select_date.setVisibility(View.GONE);
            holder.rl_cal.setVisibility(View.VISIBLE);
            if(is_selcted.equals("y")){
                holder.tv_date1.setVisibility(View.VISIBLE);
                holder.tv_time.setVisibility(View.GONE);
                if(SPHelper.current_page.equals("book")){
                }else {
                    holder.tv_time1.setVisibility(View.VISIBLE);
                    holder.tv_time1.setText(time);
                }

                holder. tv_date1.setText(datte);
                holder.white_check1.setVisibility(View.VISIBLE);
                holder.rl_cal.setBackgroundResource(R.drawable.select_green);
                holder.calendar.setVisibility(View.GONE);
                holder.rl_cal.setBackgroundTintList(ContextCompat.getColorStateList(context,R.color.profile_text));
            }else {
                holder.tv_date1.setVisibility(View.GONE);
                holder.tv_time1.setVisibility(View.GONE);
                holder.tv_time.setVisibility(View.GONE);
                holder.white_check1.setVisibility(View.GONE);
                holder.calendar.setVisibility(View.VISIBLE);
                holder.rl_cal.setBackgroundResource(R.drawable.select_green);
                holder.rl_cal.setBackgroundTintList(ContextCompat.getColorStateList(context,R.color.white));
            }
        }
        else {
//            if(is_selcted.equals("y")){
//                holder.tv_time1.setVisibility(View.GONE);
//                holder.tv_time.setVisibility(View.GONE);
//            }else {
//
//            }
            holder.rl_select_date.setVisibility(View.VISIBLE);
            holder.rl_cal.setVisibility(View.GONE);
        }

        holder.rl_select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                is_selcted="n";
                SPHelper.server_date=recyclerdata.getDisplay_date();

                if(pojo_select_dateArrayList.get(5).getIsSelected().equals("y"))
                {
                    SPHelper.server_date="";
                    pojo_select_dateArrayList.get(5).setIsSelected("n");
                }
                if(recyclerdata.getIsSelected().equals("n"))
                {
                    for (int i=0;i<5;i++)
                    {
                        if (i == position )
                        {
                            pojo_select_dateArrayList.get(i).setIsSelected("y");

                            if(SPHelper.current_page.equals("book")){

                            }else {
                                Calendar mcurrentTime = Calendar.getInstance();
                                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                                int minute = mcurrentTime.get(Calendar.MINUTE);
                                int seconds=mcurrentTime.get(Calendar.SECOND);
                                TimePickerDialog t_picker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener()
                                {
                                    @Override
                                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int selectedMinute)
                                    {
                                        time = hour+":"+minute;
                                        newtime=hour+":"+minute+":"+seconds;
                                        if(hourOfDay>=0 && hourOfDay<12){
                                            time =hourOfDay+":"+selectedMinute+" "+"AM";
                                            newtime=hourOfDay+":"+selectedMinute+":"+seconds;
                                        }
                                        else {
                                            if(hourOfDay == 12){
                                                time =hourOfDay+":"+selectedMinute+" "+"PM";
                                                newtime=hourOfDay+":"+selectedMinute+":"+seconds;
                                            } else{
                                                hourOfDay = hourOfDay -12;
                                                time =hourOfDay +":"+selectedMinute+" "+"PM";
                                                newtime=(hourOfDay+12)+":"+selectedMinute+":"+seconds;
                                            }
                                        }
                                        notifyDataSetChanged();
                                    }
                                },hour,minute,false);
                                t_picker.setTitle("Select Time");
                                t_picker.show();
                            }
                        }
                        else {
                            pojo_select_dateArrayList.get(i).setIsSelected("n");
                        }
                    }
                }
                else{
                    for (int i=0;i<5;i++)
                    {
                        if (i == position)
                        {
                            SPHelper.server_date="";
                            pojo_select_dateArrayList.get(i).setIsSelected("n");
                        }
                    }
                }
                notifyDataSetChanged();
            }
        });

        holder.rl_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                date_picker = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener()
                        {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                            {
                                is_selcted="y";
                                for (int i=0;i<5;i++)
                                {
                                        pojo_select_dateArrayList.get(i).setIsSelected("n");
                                }
                                datte=Common.getdate(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                SPHelper.server_date=year+"-"+(monthOfYear + 1)+"-"+dayOfMonth;
                                //pojo_select_dateArrayList.get(position).setIsSelected("n");
                                notifyDataSetChanged();

                                if(SPHelper.current_page.equals("book")){
                                }else {
                                    Calendar mcurrentTime = Calendar.getInstance();
                                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                                    int minute = mcurrentTime.get(Calendar.MINUTE);
                                    int seconds=mcurrentTime.get(Calendar.SECOND);
                                    TimePickerDialog t_picker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener()
                                    {
                                        @Override
                                        public void onTimeSet(TimePicker timePicker, int hourOfDay, int selectedMinute)
                                        {
                                            time = hour+":"+minute;
                                            newtime=hour+":"+minute+":"+seconds;
                                            if(hourOfDay>=0 && hourOfDay<12){
                                                time =hourOfDay+":"+selectedMinute+" "+"AM";
                                                newtime=hourOfDay+":"+selectedMinute+":"+seconds;
                                            }
                                            else {
                                                if(hourOfDay == 12){
                                                    time =hourOfDay+":"+selectedMinute+" "+"PM";
                                                    newtime=hourOfDay+":"+selectedMinute+":"+seconds;
                                                } else{
                                                    hourOfDay = hourOfDay -12;
                                                    time =hourOfDay +":"+selectedMinute+" "+"PM";
                                                    newtime=(hourOfDay+12)+":"+selectedMinute+":"+seconds;
                                                }
                                            }

                                            notifyDataSetChanged();
                                        }
                                    },hour,minute,false);
                                    t_picker.setTitle("Select Time");
                                    t_picker.show();
                                }


                            }
                        }, year, month, day);
                date_picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                date_picker.show();

//                Calendar calendar = Calendar.getInstance();
//                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        // Set the selected date to the calendar instance
//                        calendar.set(year, month, dayOfMonth);
//
//                        // Create the TimePickerDialog instance
//
//                    }
//                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
//
//// Show the DatePickerDialog instance
//                datePickerDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        RelativeLayout rl_select_date,rl_cal;
        ImageView white_check,calendar,white_check1;
        TextView tv_date,tv_date1;
        public TextView tv_time1,tv_time;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_time1=itemView.findViewById(R.id.tv_time1);
            tv_time=itemView.findViewById(R.id.tv_time);
            rl_cal=itemView.findViewById(R.id.rl_cal);
            tv_date = itemView.findViewById(R.id.tv_date);
            rl_select_date=itemView.findViewById(R.id.rl_select_date);
            white_check=itemView.findViewById(R.id.white_check);
            tv_date1 = itemView.findViewById(R.id.tv_date1);
            white_check1=itemView.findViewById(R.id.white_check1);
            calendar=itemView.findViewById(R.id.calendar);
        }
    }

    public  void show_time(){

    }
}
