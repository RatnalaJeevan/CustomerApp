package com.wisedrive.customerapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class PopupUploadPic extends BottomSheetDialogFragment {

    RelativeLayout rl_pic,rl_file;
    Activity activity;
    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_popup_upload_pic, container, false);
        rl_file=v.findViewById(R.id.rl_file);
        rl_pic=v.findViewById(R.id.rl_pic);
        activity=getActivity();
        rl_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                ((MyDocs)getContext()).opencamera();
            }
        });

        rl_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                ((MyDocs)getContext()).open_gallery();
            }
        });
        return  v;
    }
}