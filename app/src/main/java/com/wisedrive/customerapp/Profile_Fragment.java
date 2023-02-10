package com.wisedrive.customerapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class Profile_Fragment extends Fragment {
    TextView tv_my_car;
    View view;
    RelativeLayout rl_my_cars;



    public Profile_Fragment() {
        // Required empty public constructor

    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_, container, false);

        rl_my_cars=view.findViewById(R.id.rl_my_cars);
       rl_my_cars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),My_Cars.class);
                getActivity().startActivity(intent);

            }
        });


        return view;
    }
}