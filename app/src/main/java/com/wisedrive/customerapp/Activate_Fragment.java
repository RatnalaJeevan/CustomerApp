package com.wisedrive.customerapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


public class Activate_Fragment extends Fragment {
    RelativeLayout rl_activate_now;



    public Activate_Fragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_activate_, container, false);

        rl_activate_now=view.findViewById(R.id.rl_activate_now);
        rl_activate_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Upgrade_and_Save.class);
                getActivity().startActivity(intent);

            }
        });



        return view;
    }
}