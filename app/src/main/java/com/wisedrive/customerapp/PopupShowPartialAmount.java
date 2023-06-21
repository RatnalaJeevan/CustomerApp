package com.wisedrive.customerapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.wisedrive.customerapp.adapters.AdapterCovers;
import com.wisedrive.customerapp.adapters.AdapterSelAddons;
import com.wisedrive.customerapp.adapters.Adapter_Addons_list;
import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.PojoCovers;
import com.wisedrive.customerapp.pojos.PojoSelAddOnn;
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
    TextView par_amount,remai_amount,tv_amount_buy,tv_total_amount,tv_total_add_on_amount;
    RelativeLayout confirm,rl_disc;
    String paack_id="";
    RecyclerView rv_sel_addon_list;
    ArrayList<PojoSelAddOnn> selec_add_ons_list;
    AdapterSelAddons adapterSelAddons;
    private DecimalFormat IndianCurrencyFormat;
    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_popup_show_partial_amount, container, false);
        IndianCurrencyFormat = new DecimalFormat("##,##,###.00");
        rl_disc=v.findViewById(R.id.rl_disc);
        rv_covers_list=v.findViewById(R.id.rv_covers_list);
        rv_sel_addon_list=v.findViewById(R.id.rv_sel_addon_list);
        par_amount=v.findViewById(R.id.par_amount);
        tv_total_amount=v.findViewById(R.id.tv_total_amount);
        remai_amount=v.findViewById(R.id.remai_amount);
        tv_amount_buy=v.findViewById(R.id.tv_amount_buy);
        tv_total_add_on_amount=v.findViewById(R.id.tv_total_add_on_amount);
        activity=getActivity();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        confirm=v.findViewById(R.id.confirm);

    //    par_amount.setText("Rs "+ IndianCurrencyFormat.format((int) (((SPHelper.upgrade_amount)*(SPHelper.per_amount/100)))) +"+ Rs "+IndianCurrencyFormat.format((int)SPHelper.add_on_amount));

        if(SPHelper.add_on_amount==0){
            rl_disc.setVisibility(View.GONE);
        }else {
            rl_disc.setVisibility(View.VISIBLE);
        }

        tv_amount_buy.setText( IndianCurrencyFormat.format((((SPHelper.upgrade_amount)*(SPHelper.final_per/100)))));

        tv_total_amount.setText(IndianCurrencyFormat.format(( ( (SPHelper.upgrade_amount)*(SPHelper.final_per/100) +SPHelper.add_on_amount) )  ));
        tv_total_add_on_amount.setText(IndianCurrencyFormat.format( ( SPHelper.add_on_amount)  ));
        SPHelper.part_amount=(SPHelper.upgrade_amount)*(SPHelper.final_per/100);
      //  System.out.println("final amount"+IndianCurrencyFormat.format(1000.50));
       // remai_amount.setText("Remaining payment of Rs "+((SPHelper.upgrade_amount-SPHelper.part_amount))+ " can be paid before availing service");

        Typeface font1 = Typeface.createFromAsset(activity.getAssets(), "fonts/bold.ttf");
        Typeface font2 = Typeface.createFromAsset(activity.getAssets(), "fonts/regular.ttf");

        SpannableString spannableString = new SpannableString("Remaining payment of Rs "+
                IndianCurrencyFormat.format( (SPHelper.upgrade_amount-SPHelper.part_amount))+
                " can be paid before availing service");

        String f_block="Remaining payment of ";
        String total="Remaining payment of Rs "+IndianCurrencyFormat.format( (SPHelper.upgrade_amount-SPHelper.part_amount));
        String c=IndianCurrencyFormat.format( (SPHelper.upgrade_amount-SPHelper.part_amount));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            spannableString.setSpan(new TypefaceSpan(font2), 0, f_block.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            spannableString.setSpan(new TypefaceSpan(font2), total.length()+1, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
//
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            spannableString.setSpan(new TypefaceSpan(font1), f_block.length()+1, spannableString.length()-total.length()-c.length()-1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        remai_amount.setText(spannableString);


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