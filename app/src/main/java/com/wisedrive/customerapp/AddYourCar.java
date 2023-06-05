package com.wisedrive.customerapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wisedrive.customerapp.adapters.Adapter_Select_Model;
import com.wisedrive.customerapp.adapters.Adapter_select_make;
import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.PojoAddYourCar;
import com.wisedrive.customerapp.pojos.PojoAddresses;
import com.wisedrive.customerapp.pojos.PojoImges;
import com.wisedrive.customerapp.pojos.Pojo_Select_Make_list;
import com.wisedrive.customerapp.pojos.Pojo_Select_Model;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddYourCar extends DialogFragment
{
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    RecyclerView rv_make,rv_model;
    Adapter_select_make adapter_select_make;
    ArrayList<Pojo_Select_Make_list> pojo_select_make_listArrayList;
    Adapter_Select_Model adapter_select_model;
    ArrayList<Pojo_Select_Model> pojo_select_modelArrayList;
    RelativeLayout rl_back,rl_basic_details,rl_petrol,rl_diesel,rl_auto,rl_trans,rl_add1;
    String it_is="1",fuel_type="",trans_type="";
    public TextView label,year,owner;
    ImageView add_y,sub_y,sub_o,add_o,close;
    EditText selected_kms,selected_clr,selected_vehno;
    int sel_year=2008;
    int sel_owner=1;
    int max_owner=5;
    int max_year;
    Activity activity;
    String veh_patttern="^[A-Z]{2}";

    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_add_your_car, container, false);

        activity=getActivity();
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        SPHelper.sharedPreferenceInitialization(activity);
        getActivity().getWindow().setStatusBarColor(getActivity().getColor(R.color.new_app_bg));
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        rl_add1=view.findViewById(R.id.rl_add1);
        rl_petrol=view.findViewById(R.id.rl_petrol);
        rl_diesel=view.findViewById(R.id.rl_diesel);
        rl_auto=view.findViewById(R.id.rl_auto);
        rl_trans=view.findViewById(R.id.rl_trans);
        close=view.findViewById(R.id.close);
        rl_basic_details=view.findViewById(R.id.rl_basic_details);
        rv_make=view.findViewById(R.id.rv_make);
        rv_model=view.findViewById(R.id.rv_model);
        rl_back=view.findViewById(R.id.rl_back);
        label=view.findViewById(R.id.label);
        owner=view.findViewById(R.id.owner);
        year=view.findViewById(R.id.year);
        add_o=view.findViewById(R.id.add_o);
        sub_o=view.findViewById(R.id.sub_o);
        add_y=view.findViewById(R.id.add_y);
        sub_y=view.findViewById(R.id.sub_y);
        selected_kms=view.findViewById(R.id.selected_kms);
        selected_clr=view.findViewById(R.id.selected_clr);
        selected_vehno=view.findViewById(R.id.selected_vehno);
       // selected_vehno.setInputType( InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);



        get_carbrands_list();

        max_year = Calendar.getInstance().get(Calendar.YEAR);
        add_y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sel_year++;
                if(sel_year==max_year){
                    add_y.setVisibility(View.GONE);

                }else {
                    add_y.setVisibility(View.VISIBLE);
                }
                year.setText(String.valueOf(sel_year));
                sub_y.setVisibility(View.VISIBLE);
            }
        });
        sub_y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sel_year--;
                if(sel_year<=2008)
                {
                    sub_y.setVisibility(View.GONE);
                }else {
                    sub_y.setVisibility(View.VISIBLE);
                }
                year.setText(String.valueOf(sel_year));
                add_y.setVisibility(View.VISIBLE);

            }
        });
        add_o.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sel_owner++;
                if(sel_owner==max_owner){
                    add_o.setVisibility(View.GONE);

                }else {
                    add_o.setVisibility(View.VISIBLE);
                }
                owner.setText(String.valueOf(sel_owner));
                sub_o.setVisibility(View.VISIBLE);
            }
        });
        sub_o.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sel_owner--;
                if(sel_owner<=1)
                {
                    sub_o.setVisibility(View.GONE);
                }else {
                    sub_o.setVisibility(View.VISIBLE);
                }
                owner.setText(String.valueOf(sel_owner));
                add_o.setVisibility(View.VISIBLE);
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(it_is.equals("1")){
                    get_carbrands_list();
                }
                if(it_is.equals("2")){
                    get_carmodel_list();
                }
            }
        });

        rl_auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_auto.setBackgroundResource(R.drawable.grey_marg);
                rl_trans.setBackground(null);
                trans_type="Automatic";
            }
        });
        rl_trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_trans.setBackgroundResource(R.drawable.grey_marg);
                rl_auto.setBackground(null);
                trans_type="Manual";
            }
        });
        rl_diesel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_diesel.setBackgroundResource(R.drawable.grey_marg);
                rl_petrol.setBackground(null);
                fuel_type="Petrol";
            }
        });
        rl_petrol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_petrol.setBackgroundResource(R.drawable.grey_marg);
                rl_diesel.setBackground(null);
                fuel_type="Diesel";
            }
        });
        rl_add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //validation
                //select fule
                //select trans
                //select
                String firstTwoLetters = selected_vehno.getText().toString().substring(0, 2);
                if(fuel_type.equals("")){
                    Toast.makeText(activity, "select fuel type", Toast.LENGTH_SHORT).show();
                }else  if(trans_type.equals("")){
                    Toast.makeText(activity, "select trasmission type", Toast.LENGTH_SHORT).show();
                }else  if(selected_kms.getText().toString().equals("")){
                    Toast.makeText(activity, "enter kms ", Toast.LENGTH_SHORT).show();
                }
                else  if(selected_vehno.getText().toString().equals("")){
                    Toast.makeText(activity, "enter vehicle number", Toast.LENGTH_SHORT).show();
                }
                else  if(selected_clr.getText().toString().equals("")){
                    Toast.makeText(activity, "enter colour ", Toast.LENGTH_SHORT).show();
                }
                else if(!firstTwoLetters.matches(veh_patttern)){
                    Toast.makeText(activity, "enter valid vehicle number", Toast.LENGTH_SHORT).show();
                }
                else if(selected_vehno.getText().toString().length()<8){
                    Toast.makeText(activity, "enter valid vehicle number", Toast.LENGTH_SHORT).show();
                }

                else {
                    post_add_your_car();
                }

            }
        });



        return view;
    }

    public  void get_carbrands_list()
    {
        label.setText("Select Make");
        rl_back.setVisibility(View.GONE);
        rl_basic_details.setVisibility(View.GONE);
        {
            if (!Connectivity.isNetworkConnected(activity)) {
                Toast.makeText(activity,
                        "Plaese Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            } else {
                // progress_bar.setVisibility(View.VISIBLE);
                Call<AppResponse> call = apiInterface.getBrandList();
                call.enqueue(new Callback<AppResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                        AppResponse appResponse = response.body();
                        assert appResponse != null;
                        String response_code = appResponse.getResponseType();
                        if (response.body() != null) {
                            if (response_code.equals("200"))
                            {
                                // progress_bar.setVisibility(View.GONE);
                                rv_model.setVisibility(View.GONE);
                                rv_make.setVisibility(View.VISIBLE);
                                pojo_select_make_listArrayList=new ArrayList<>();
                                pojo_select_make_listArrayList=appResponse.getResponseModel().getBrandList();
                                adapter_select_make = new Adapter_select_make(AddYourCar.this, pojo_select_make_listArrayList);
                                GridLayoutManager layoutManager = new GridLayoutManager(activity, 3);
                                rv_make.setLayoutManager(layoutManager);
                                rv_make.setAdapter(adapter_select_make);
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter_select_make.notifyDataSetChanged();
                                    }
                                });
                            } else if (response_code.equals("300")) {
                                // progress_bar.setVisibility(View.GONE);
                            }
                        } else {
                            // progress_bar.setVisibility(View.GONE);
                            Toast.makeText(activity, "internal server error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                        Toast.makeText(activity,
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        // progress_bar.setVisibility(View.GONE);
                    }
                });
            }
        }
    }

    @SuppressLint("SetTextI18n")
    public  void get_carmodel_list()
    {
        label.setText("Select Model");
         it_is = "1";
        rl_back.setVisibility(View.VISIBLE);
        rl_basic_details.setVisibility(View.GONE);
        {
            if (!Connectivity.isNetworkConnected(activity)) {
                Toast.makeText(activity,
                        "Plaese Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            } else {
                // progress_bar.setVisibility(View.VISIBLE);
                Call<AppResponse> call = apiInterface.getModelList(SPHelper.carbrandid);
                call.enqueue(new Callback<AppResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                        AppResponse appResponse = response.body();
                        assert appResponse != null;
                        String response_code = appResponse.getResponseType();
                        if (response.body() != null) {
                            if (response_code.equals("200"))
                            {
                                //  progress_bar.setVisibility(View.GONE);
                                rv_model.setVisibility(View.VISIBLE);
                                rv_make.setVisibility(View.GONE);

                                pojo_select_modelArrayList = new ArrayList<>();
                                pojo_select_modelArrayList=appResponse.getResponseModel().getModelList();

                                adapter_select_model = new Adapter_Select_Model(AddYourCar.this, pojo_select_modelArrayList);
                                GridLayoutManager layoutManager1 = new GridLayoutManager(activity, 3);
                                rv_model.setLayoutManager(layoutManager1);
                                rv_model.setAdapter(adapter_select_model);

                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter_select_model.notifyDataSetChanged();
                                    }
                                });
                            } else if (response_code.equals("300")) {
                                // progress_bar.setVisibility(View.GONE);
                            }
                        } else {
                            // progress_bar.setVisibility(View.GONE);
                            Toast.makeText(activity, "internal server error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                        Toast.makeText(activity,
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        //progress_bar.setVisibility(View.GONE);
                    }
                });
            }
        }
    }

    public  void get_basic_details(){
        it_is="2";
        rl_basic_details.setVisibility(View.VISIBLE);
        label.setText("Basic");
        rv_make.setVisibility(View.GONE);
        rv_model.setVisibility(View.GONE);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setCanceledOnTouchOutside(true);

        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        return dialog;
    }


    private void post_add_your_car() {
        if (!Connectivity.isNetworkConnected(activity)) {
            Toast.makeText(activity, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
            return;
        } else {
            ArrayList<PojoImges> pojoImges=new ArrayList<>();
            progressDialog.show();
            String l_id=SPHelper.getSPData(activity,SPHelper.lead_id,"");
            String c_id=SPHelper.getSPData(activity,SPHelper.customer_id,"");
            PojoAddYourCar pojoAddYourCar=new PojoAddYourCar(l_id,selected_vehno.getText().toString(),"",
                    selected_kms.getText().toString(), owner.getText().toString(),selected_clr.getText().toString(),
                    "","","","",c_id,SPHelper.carmodelid,
                    fuel_type,year.getText().toString(),trans_type,"","","",pojoImges);
            Call<AppResponse> call = apiInterface.add_car(pojoAddYourCar);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                    System.out.print(response.body());
                    if (response.body() != null) {
                        AppResponse data = response.body();
                        if (data.getResponseType().equalsIgnoreCase("200")) {
                            progressDialog.dismiss();
                            SPHelper.saveSPdata(activity, SPHelper.lead_id, data.getResponseModel().getVehDetails().getLead_id());
                            SPHelper.saveSPdata(activity, SPHelper.customer_id, data.getResponseModel().getVehDetails().getCustomer_id());
                            Toast.makeText(activity, "Added Successfully", Toast.LENGTH_SHORT).show();
                            SPHelper.comingfrom="";
                            SPHelper.fragment_is="cars";
                            Intent intent=new Intent(activity, CustomerHomepage.class);
                            activity.startActivity(intent);
                            dismiss();
                            //finish();

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(activity, data.getResponseModel().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<AppResponse> call, Throwable th) {
                    Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }
    }

//    private void hideKeybaord() {
//        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
//        inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
//
//    }

    private void hideKeybaord() {
        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(requireActivity().getCurrentFocus().getWindowToken(), 0);
    }

}