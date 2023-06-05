package com.wisedrive.customerapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.wisedrive.customerapp.adapters.AdapterCarImgList;
import com.wisedrive.customerapp.adapters.AdapterCarVideos;
import com.wisedrive.customerapp.adapters.AdapterInspQList;
import com.wisedrive.customerapp.adapters.AdapterWarrantyList;
import com.wisedrive.customerapp.adapters.AdapterWdBenefs;
import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.ResponseModel;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopupWDBenefits extends BottomSheetDialogFragment {
    ImageView image_plus;
    Activity activity;
    ArrayList<ResponseModel.PojoWarrantyBenefits> pojoWarrantyBenefits;
    RecyclerView rv_wd_bene;
    private ApiInterface apiInterface;
    AdapterWdBenefs adapterWdBenefs;
    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_popup_wdbenefits, container, false);
        activity=getActivity();
        rv_wd_bene=v.findViewById(R.id.rv_wd_bene);
        image_plus=v.findViewById(R.id.image_plus);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        image_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        get_warr_benefit_list();
        return  v;
    }


    public void get_warr_benefit_list()
    {
        if (!Connectivity.isNetworkConnected(activity)) {
            Toast.makeText(activity,
                    "Plaese Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        } else {
            //idPBLoading.setVisibility(View.VISIBLE);
            SPHelper.lead_veh_id="";
            SPHelper.w_ins_id="";
            Call<AppResponse> call = apiInterface.get_warranty_benefits();
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                    AppResponse appResponse = response.body();
                    assert appResponse != null;
                    String response_code = appResponse.getResponseType();
                    if (response.body() != null) {
                        if (response_code.equals("200")) {
                            // idPBLoading.setVisibility(View.GONE);
                            pojoWarrantyBenefits = new ArrayList<>();
                            pojoWarrantyBenefits=appResponse.getResponseModel().getWarrantyBenifits();
                            adapterWdBenefs=new AdapterWdBenefs(pojoWarrantyBenefits,activity);
                            LinearLayoutManager l1=new LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false);
                            rv_wd_bene.setLayoutManager(l1);
                            rv_wd_bene.setAdapter(adapterWdBenefs);

                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapterWdBenefs.notifyDataSetChanged();
                                }
                            });
                        } else if (response_code.equals("300")) {
                            // idPBLoading.setVisibility(View.GONE);
                        }
                    } else {
                        // idPBLoading.setVisibility(View.GONE);
                        Toast.makeText(activity, "internal server error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                    Toast.makeText(activity,
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    // idPBLoading.setVisibility(View.GONE);
                }
            });
        }
    }

}