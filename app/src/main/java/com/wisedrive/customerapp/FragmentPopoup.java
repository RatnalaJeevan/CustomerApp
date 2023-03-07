package com.wisedrive.customerapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.wisedrive.customerapp.commonclasses.SPHelper;

public class FragmentPopoup extends BottomSheetDialogFragment {
    public String act_code="",insp_req="",error_msg="";
    public TextView rl_activate,req_for_insp,content;
   public ImageView close;
   public Activity activity;
   public Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.activity_fragment_popoup,
                container, false);
        activity=getActivity();
        rl_activate=v.findViewById(R.id.rl_activate);
        req_for_insp=v.findViewById(R.id.req_for_insp);
        content=v.findViewById(R.id.content);
        close=v.findViewById(R.id.close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


        rl_activate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.fragment_is="act";
                Intent intent=new Intent(activity,CustomerHomepage.class);
                startActivity(intent);
            }
        });
        req_for_insp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity,Request_Inspection.class);
                startActivity(intent);
            }
        });
        if(act_code.equalsIgnoreCase("n"))
        {
            req_for_insp.setVisibility(View.GONE);
            rl_activate.setVisibility(View.VISIBLE);
            content.setText(error_msg);
        }
        else  if(insp_req.equalsIgnoreCase("n"))
        {
            req_for_insp.setVisibility(View.VISIBLE);
            rl_activate.setVisibility(View.GONE);
            content.setText(error_msg);
        }else{
            req_for_insp.setVisibility(View.GONE);
            rl_activate.setVisibility(View.GONE);
            content.setText(error_msg);
        }
        return v;
    }

}