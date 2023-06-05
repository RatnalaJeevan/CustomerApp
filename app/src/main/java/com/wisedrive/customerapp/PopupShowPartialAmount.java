package com.wisedrive.customerapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.wisedrive.customerapp.adapters.AdapterCovers;
import com.wisedrive.customerapp.adapters.Adapter_Addons_list;
import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.PojoCovers;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopupShowPartialAmount  extends BottomSheetDialogFragment {
    RecyclerView rv_covers_list;
    AdapterCovers adapterCovers;
    ArrayList<PojoCovers> pojoCovers;
    Activity activity;
    private ApiInterface apiInterface;
    TextView par_amount,remai_amount;
    RelativeLayout confirm;
    String paack_id="";
    private DecimalFormat IndianCurrencyFormat;
    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_popup_show_partial_amount, container, false);
        IndianCurrencyFormat = new DecimalFormat("##,##,###");
        rv_covers_list=v.findViewById(R.id.rv_covers_list);
        par_amount=v.findViewById(R.id.par_amount);
        remai_amount=v.findViewById(R.id.remai_amount);
        activity=getActivity();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        confirm=v.findViewById(R.id.confirm);

        par_amount.setText("Rs "+ IndianCurrencyFormat.format((int) (((SPHelper.upgrade_amount)*(SPHelper.per_amount/100)))) +"+ Rs "+IndianCurrencyFormat.format((int)SPHelper.add_on_amount));
        SPHelper.part_amount=(SPHelper.upgrade_amount)*(SPHelper.per_amount/100);
        remai_amount.setText("Remaining payment of Rs "+IndianCurrencyFormat.format((int) (SPHelper.upgrade_amount-SPHelper.part_amount))+ " can be paid before availing service");
        System.out.println("add_on_amount"+SPHelper.add_on_amount);
        System.out.println("part_amount"+SPHelper.part_amount);
        get_covers_list();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.is_part="Y";
                Addons.getInstance().create_sessionID();
                dismiss();
            }
        });
        return  v;
    }


    public void get_covers_list() {
        if(SPHelper.sel_upgrade_pac_id.equals("")){
            paack_id=SPHelper.package_id;
        }else {
            paack_id=SPHelper.sel_upgrade_pac_id;
        }
        if (!Connectivity.isNetworkConnected(activity)) {
            Toast.makeText(activity,
                    "Plaese Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        } else {
           // idPBLoading.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.get_partial_payment_products(paack_id);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                    AppResponse appResponse = response.body();
                    assert appResponse != null;
                    String response_code = appResponse.getResponseType();
                    if (response.body() != null) {
                        if (response_code.equals("200")) {
                           // idPBLoading.setVisibility(View.GONE);

                            pojoCovers = new ArrayList<>();
                            pojoCovers = appResponse.getResponseModel().getPartialPaymentProductList();

                            adapterCovers = new AdapterCovers(pojoCovers, activity);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
                            rv_covers_list.setLayoutManager(linearLayoutManager);
                            rv_covers_list.setAdapter(adapterCovers);

                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapterCovers.notifyDataSetChanged();
                                }
                            });
                        } else if (response_code.equals("300")) {
                           // idPBLoading.setVisibility(View.GONE);
                        }
                    } else {
                        //idPBLoading.setVisibility(View.GONE);
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