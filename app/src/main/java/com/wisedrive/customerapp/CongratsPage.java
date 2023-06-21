package com.wisedrive.customerapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.wisedrive.customerapp.commonclasses.SPHelper;

public class CongratsPage extends BottomSheetDialogFragment {

    TextView label1;
    pl.droidsonroids.gif.GifImageView iv_anim;
    Activity activity;
    ImageView close;
    RelativeLayout rl_retry;
    public String coming_from="";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_congrats_page, container, false);
        activity=getActivity();
        label1=v.findViewById(R.id.label1);
        iv_anim=v.findViewById(R.id.iv_anim);
        rl_retry=v.findViewById(R.id.rl_retry);
        close=v.findViewById(R.id.close);
        label1.setText(SPHelper.cf_msg);
        SPHelper.comingfrom="";
//        if(SPHelper.isSuccess.equals("y")){
//            rl_retry.setVisibility(View.GONE);
//            iv_anim.setImageResource(R.drawable.success_anim);
//        }
//        else if(SPHelper.isSuccess.equals("add")){
//            rl_retry.setVisibility(View.GONE);
//            iv_anim.setImageResource(R.drawable.insp_approved);
//        }else{
//            rl_retry.setVisibility(View.VISIBLE);
//            iv_anim.setImageResource(R.drawable.alert_anim);
//        }

        if(SPHelper.isSuccess.equals("add")){
            iv_anim.setImageResource(R.drawable.insp_approved);
        }else{
           // rl_retry.setVisibility(View.VISIBLE);
            iv_anim.setImageResource(R.drawable.alert_anim);
        }

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
//        rl_retry.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//
//                if(coming_from.equals("addon")){
//                    Addons.getInstance().create_sessionID();
//                }else{
//                    Recommended_Activity.getInstance().create_sessionID();
//                }
//                dismiss();
//            }
//        });
        return v;
    }
}