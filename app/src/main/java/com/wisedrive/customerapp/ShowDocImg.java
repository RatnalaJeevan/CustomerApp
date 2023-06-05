package com.wisedrive.customerapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.jsibbold.zoomage.ZoomageView;
import com.wisedrive.customerapp.commonclasses.Common;
import com.wisedrive.customerapp.commonclasses.SPHelper;

public class ShowDocImg extends BottomSheetDialogFragment {
    TextView label,view_pdf;
    ZoomageView taken_odo;
    RelativeLayout rl_edit,rl_submit;
    String is_doc="";
    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_show_doc_img, container, false);
        taken_odo=v.findViewById(R.id.taken_odo);
        rl_edit=v.findViewById(R.id.rl_edit);
        label=v.findViewById(R.id.label);
        rl_submit=v.findViewById(R.id.rl_submit);
        view_pdf=v.findViewById(R.id.view_pdf);
        label.setText(SPHelper.doc_name);


        if(SPHelper.sel_uri.toString().equals(""))
        {
            if(SPHelper.doc_img.contains(".pdf"))
            {
                view_pdf.setVisibility(View.VISIBLE);
                taken_odo.setVisibility(View.GONE);

            }else{
                view_pdf.setVisibility(View.GONE);
                taken_odo.setVisibility(View.VISIBLE);
                if ( SPHelper.doc_img != null && !SPHelper.doc_img.isEmpty() && !SPHelper.doc_img.equals("null")) {
                    Glide.with(getActivity()).load(SPHelper.doc_img).placeholder(R.drawable.icon_noimage).into(taken_odo);
                }
            }
            rl_submit.setVisibility(View.GONE);
            rl_edit.setVisibility(View.VISIBLE);


        }
        else {

            if(String.valueOf(SPHelper.sel_uri).contains(".pdf")){
                view_pdf.setVisibility(View.VISIBLE);
                taken_odo.setVisibility(View.GONE);
                is_doc="y";
            }else{
                is_doc="n";
                view_pdf.setVisibility(View.GONE);
                taken_odo.setVisibility(View.VISIBLE);

                if ( SPHelper.sel_uri != null  && !SPHelper.sel_uri.equals("null")) {
                    Glide.with(getActivity()).load(SPHelper.sel_uri).placeholder(R.drawable.icon_noimage).into(taken_odo);
                }
            }

            rl_submit.setVisibility(View.VISIBLE);
            rl_edit.setVisibility(View.GONE);
        }

        view_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SPHelper.sel_uri.toString().equals(""))
                {
                    openPdfFile(SPHelper.doc_img);
                }else {
                    openPdfFile(String.valueOf(SPHelper.sel_uri));
                }
            }
        });

        rl_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                SPHelper.doc_edited="y";

                PopupUploadPic pic=new PopupUploadPic();
                pic.show(((FragmentActivity)getActivity()).getSupportFragmentManager(), pic.getTag());
            }
        });

        rl_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();

                if(is_doc.equals("y")){
                    MyDocs.getInstance().doc_url= String.valueOf(SPHelper.sel_uri);
                    MyDocs.getInstance().update_doc();
                }else {
                    MyDocs.getInstance().upload_to_s3(SPHelper.sel_uri);
                }

            }
        });

        return  v;
    }


    private void openPdfFile(String file) {
        Uri uri = Uri.parse(file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // Handle exception if no PDF viewer application is available
            Toast.makeText(getActivity(), "No PDF viewer application found", Toast.LENGTH_SHORT).show();
        }
    }



}