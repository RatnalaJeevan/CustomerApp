package com.wisedrive.customerapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.wisedrive.customerapp.adapters.AdapterPaidAddonList;
import com.wisedrive.customerapp.adapters.AdapterSSList;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.PojoPaidAddonList;
import com.wisedrive.customerapp.pojos.Pojo_Service_Includes;

import java.util.ArrayList;

public class PopupPackDetails extends BottomSheetDialogFragment {

    RelativeLayout rl_add_ser,rl_addon;
    TextView tv_pack,tv2,label1;
    View v1,v2;
    Activity activity;
    ArrayList<Pojo_Service_Includes> pojo_service_includes;
    AdapterSSList adapterSSList;
    RecyclerView rv_ss_list,rv_add_on_list;

    ArrayList<PojoPaidAddonList> pojoServiceListArrayList;
    AdapterPaidAddonList adapterServiceList;
    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_popup_pack_details, container, false);
        activity=getActivity();
        label1=v.findViewById(R.id.label1);
        rv_ss_list=v.findViewById(R.id.rv_ss_list);
        rv_add_on_list=v.findViewById(R.id.rv_add_on_list);
        rl_addon=v.findViewById(R.id.rl_addon);
        rl_add_ser=v.findViewById(R.id.rl_add_ser);
        tv2=v.findViewById(R.id.tv2);
        tv_pack=v.findViewById(R.id.tv_pack);
        v1=v.findViewById(R.id.v1);
        v2=v.findViewById(R.id.v2);
        rl_add_ser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                label1.setVisibility(View.VISIBLE);
                tv_pack.setTextColor(Color.parseColor("#252a40"));
                tv2.setTextColor(Color.parseColor("#D3D3D3"));
                v1.setVisibility(View.VISIBLE);
                v2.setVisibility(View.GONE);
                rv_ss_list.setVisibility(View.VISIBLE);
                rv_add_on_list.setVisibility(View.GONE);
            }
        });

        rl_addon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                label1.setVisibility(View.GONE);
                tv_pack.setTextColor(Color.parseColor("#D3D3D3"));
                tv2.setTextColor(Color.parseColor("#252a40"));
                v2.setVisibility(View.VISIBLE);
                v1.setVisibility(View.GONE);
                rv_ss_list.setVisibility(View.GONE);
                rv_add_on_list.setVisibility(View.VISIBLE);
            }
        });

        LinearLayoutManager l1=new LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false);
        adapterSSList=new AdapterSSList(SPHelper.pojo_service_includes,activity);
        rv_ss_list.setLayoutManager(l1);
        rv_ss_list.setAdapter(adapterSSList);

        adapterServiceList = new AdapterPaidAddonList(activity, SPHelper.pojoServiceListArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        rv_add_on_list.setLayoutManager(linearLayoutManager);
        rv_add_on_list.setAdapter(adapterServiceList);
        return  v;
    }




}