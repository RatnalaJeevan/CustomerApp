package com.wisedrive.customerapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.wisedrive.customerapp.adapters.AdapterSSList;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.Pojo_Service_Includes;

import java.util.ArrayList;

public class PopupShowServiceDetails extends BottomSheetDialogFragment {

    ArrayList<Pojo_Service_Includes> pojo_service_includes;
    AdapterSSList adapterSSList;
    RecyclerView rv_ss_list;
    Activity activity;
    ImageView image_plus;
    TextView c_name;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_popup_show_service_details, container, false);
        rv_ss_list=v.findViewById(R.id.rv_ss_list);
        image_plus=v.findViewById(R.id.image_plus);
        c_name=v.findViewById(R.id.c_name);
        activity=getActivity();

        c_name.setText(SPHelper.package_name);
        LinearLayoutManager l1=new LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false);
        adapterSSList=new AdapterSSList(SPHelper.pojo_service_includes,activity);
        rv_ss_list.setLayoutManager(l1);
        rv_ss_list.setAdapter(adapterSSList);

        c_name.setText(SPHelper.package_name);
        image_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return  v;
    }
}