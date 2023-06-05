package com.wisedrive.customerapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.wisedrive.customerapp.commonclasses.SPHelper;


public class PopupChooseTypeInsp extends BottomSheetDialogFragment {

    RelativeLayout rl2,rl1;
    Activity activity;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_popup_choose_type_insp, container, false);
        activity=getActivity();
        rl2=v.findViewById(R.id.rl2);
        rl1=v.findViewById(R.id.rl1);
        rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(SPHelper.show_self.equalsIgnoreCase("y"))
                {
                    Intent intent=new Intent(activity,SelfInspection.class);
                    startActivity(intent);
                }
                else{
                Toast.makeText(activity,"Coming soon",
                        Toast.LENGTH_SHORT).show();
                }
            }
        });
        rl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity,Request_Inspection.class);
                startActivity(intent);
            }
        });
        return  v;
    }

}