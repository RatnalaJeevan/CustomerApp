package com.wisedrive.customerapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.PojoGetVehdetails;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import carbon.widget.LinearLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CheckMyCar extends Fragment
{
    public   RadioButton radioButton;
    RelativeLayout rl_car_info,rl_cnfrm_car,rl_benefits,rl_bottom,rl_next,rl_check;
    Activity activity;
    EditText entered_act_code,selected_vehno;
    private ApiInterface apiInterface;
    ProgressBar idPBLoading;
    LinearLayout l1;
    TextView text_warranty_name,tv_description1,label_inactive,status,tv_what_todo,expires_on,
            tv_desription,make_model,vehno,fuel_type,mf_year;
    ImageView right_icon,tick;
    RadioButton yes,no;
    RadioGroup radioGroup1;
    public CheckMyCar() {

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.checkmy_car, container, false);
        activity=getActivity();
        getActivity().getWindow().setStatusBarColor(getActivity().getColor(R.color.new_app_bg));
        tick=view.findViewById(R.id.tick);
        rl_check=view.findViewById(R.id.rl_check);
        expires_on=view.findViewById(R.id.expires_on);
        tv_what_todo=view.findViewById(R.id.tv_what_todo);
        rl_bottom=view.findViewById(R.id.rl_bottom);
        right_icon=view.findViewById(R.id.right_icon);
        rl_benefits=view.findViewById(R.id.rl_benefits);
        radioGroup1=view.findViewById(R.id.radioGroup1);
        tv_desription=view.findViewById(R.id.tv_desription);
        status=view.findViewById(R.id.status);
        yes =view. findViewById(R.id.yes);
        no=view.findViewById(R.id.no);
        make_model=view.findViewById(R.id.make_model);
        vehno=view.findViewById(R.id.veh_no);
        fuel_type=view.findViewById(R.id.fuel);
        mf_year=view.findViewById(R.id.mf_year);
        text_warranty_name=view.findViewById(R.id.text_warranty_name);
        tv_description1=view.findViewById(R.id.tv_description1);
        label_inactive=view.findViewById(R.id.label_inactive);
        rl_car_info=view.findViewById(R.id.rl_car_info);
        idPBLoading=view.findViewById(R.id.idPBLoading);
        selected_vehno=view.findViewById(R.id.selected_vehno);
        l1=view.findViewById(R.id.l1);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        rl_next=view.findViewById(R.id.rl_next);
        entered_act_code=view.findViewById(R.id.entered_act_code);
        rl_cnfrm_car=view.findViewById(R.id.rl_cnfrm_car);

        right_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupInfo bottomSheetDialogFragment = new PopupInfo();
                bottomSheetDialogFragment.show(((FragmentActivity)activity).getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
            }
        });

        selected_vehno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(selected_vehno.getText().toString().length()>5){
                   // get_veh_details();

                }else {
                    rl_car_info.setVisibility(View.GONE);
                    rl_cnfrm_car.setVisibility(View.GONE);
                    rl_benefits.setVisibility(View.GONE);
                    rl_bottom.setVisibility(View.GONE);
                    l1.setVisibility(View.GONE);
                }
            }
        });

        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                 radioButton =view.findViewById(checkedId);
                String selectedOption = radioButton.getText().toString();

                if(selectedOption.equalsIgnoreCase("yes"))
                {
                    hideKeybaord();
                    yes.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
                    no.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.grey)));
                    l1.setVisibility(View.VISIBLE);
                    rl_benefits.setVisibility(View.VISIBLE);
                    rl_bottom.setVisibility(View.VISIBLE);
                    radioButton.setChecked(false);

                }else {
                    hideKeybaord();
                    no.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
                    yes.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.grey)));
                    l1.setVisibility(View.GONE);
                    selected_vehno.setText("");
                    rl_benefits.setVisibility(View.GONE);
                    rl_bottom.setVisibility(View.GONE);
                    radioButton.setChecked(false);
                }
            }
        });

        rl_benefits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupWDBenefits bottomSheetDialogFragment = new PopupWDBenefits();
                bottomSheetDialogFragment.show(((FragmentActivity)activity).getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
            }
        });

        rl_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.veh_no=selected_vehno.getText().toString();
                PopupChooseTypeInsp bottomSheetDialogFragment = new PopupChooseTypeInsp();
                bottomSheetDialogFragment.show(((FragmentActivity)activity).getSupportFragmentManager(), bottomSheetDialogFragment.getTag());

            }
        });

        rl_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(selected_vehno.getText().toString().equals("")){
                    Toast.makeText(activity,
                            "Enter a vehicle number",
                            Toast.LENGTH_SHORT).show();
                }else if(selected_vehno.getText().toString().length()<5){
                    Toast.makeText(activity,
                            "Enter valid vehicle number",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    get_veh_details();
                }
            }
        });
        return view;
    }

    public  void get_veh_details(){


        if(!Connectivity.isNetworkConnected(activity))
        {
            Toast.makeText(activity,
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }else
        {
           // progressDialog.show();

            PojoGetVehdetails pojoGetVehdetails=new PojoGetVehdetails(selected_vehno.getText().toString().trim(),
                    SPHelper.getSPData(activity,SPHelper.lead_id,""),
                    SPHelper.getSPData(activity,SPHelper.customer_id,""));
            Call<AppResponse> call =  apiInterface.getVeh_details(pojoGetVehdetails);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response)
                {
                    AppResponse appResponse = response.body();

                    if (response.body()!=null) {
                        assert appResponse != null;
                        if (appResponse.getResponseType().equals("200"))
                        {
                           //if veh is not present
                            //if insp status is nnull
                            //if insp expired
                            //

                            rl_car_info.setVisibility(View.VISIBLE);
                            rl_cnfrm_car.setVisibility(View.VISIBLE);
                            SPHelper.veh_id=appResponse.getResponseModel().getGetDetails().getVehicle_id();
                            SPHelper.lead_veh_id=appResponse.getResponseModel().getGetDetails().getLead_vehicle_id();
                            SPHelper.w_ins_id=appResponse.getResponseModel().getGetDetails().getWilvd_id();
                            String insp_status=appResponse.getResponseModel().getGetDetails().getInspection_status();
                            String make=appResponse.getResponseModel().getGetDetails().getVehicle_make();
                            String model=appResponse.getResponseModel().getGetDetails().getVehicle_model();
                            String veh_no=appResponse.getResponseModel().getGetDetails().getVehicle_no();
                            String fuel=appResponse.getResponseModel().getGetDetails().getFuel_type();
                            String year=appResponse.getResponseModel().getGetDetails().getManufacturing_year();
                            String odo=appResponse.getResponseModel().getGetDetails().getOdometer();
                            String dis_msg=appResponse.getResponseModel().getGetDetails().getDisplayMsg();
                            String exp_days=appResponse.getResponseModel().getGetDetails().getExpired_in_days();
                            String err_msg=appResponse.getResponseModel().getGetDetails().getError_msg();
                            SPHelper.veh_make=make;
                            SPHelper.veh_model=model;
                            SPHelper.veh_no=veh_no;
                            SPHelper.fuel_type=fuel;
                            SPHelper.mnf_year=year;
                            SPHelper.kms_driven=odo;

                            make_model.setText(make+"-"+model);
                            vehno.setText(veh_no);
                            fuel_type.setText(fuel);
                            mf_year.setText(year);

                            tv_description1.setText(dis_msg);

                            if(insp_status==null||insp_status.equals("null")||insp_status.isEmpty())
                            {
                                text_warranty_name.setText("Not Requested");
                                text_warranty_name.setTextColor(getResources().getColor(R.color.blue));
                                tick.setVisibility(View.GONE);
                                tv_what_todo.setText("Request For Inspection");

                            }
                            else
                            {

                                 insp_status = insp_status.substring(0, 1).toUpperCase() + insp_status.substring(1);
                                text_warranty_name.setText(insp_status);
                             if (insp_status.equalsIgnoreCase("approved")) {
                                text_warranty_name.setTextColor(getResources().getColor(R.color.newgreen));
                                tick.setVisibility(View.VISIBLE);
                            } else if (insp_status.equalsIgnoreCase("repair requested") ||
                                    insp_status.equalsIgnoreCase("expired")) {
                                text_warranty_name.setTextColor(getResources().getColor(R.color.red));
                                tick.setVisibility(View.GONE);
                            } else if (insp_status.equalsIgnoreCase("reinspect")) {
                                text_warranty_name.setTextColor(getResources().getColor(R.color.orange));
                                tick.setVisibility(View.GONE);
                            } else {
                                text_warranty_name.setTextColor(getResources().getColor(R.color.blue));
                                tick.setVisibility(View.GONE);
                            }
                            }
                            if(appResponse.getResponseModel().getGetDetails().getShowButton().equalsIgnoreCase("y")){
                                rl_next.setVisibility(View.VISIBLE);
                            }else {
                                rl_next.setVisibility(View.GONE);
                            }
//                            if(err_msg.equalsIgnoreCase("y")){
//                                rl_benefits.setVisibility(View.VISIBLE);
//                                rl_bottom.setVisibility(View.VISIBLE);
//                                l1.setVisibility(View.VISIBLE);
//                            }else {
//                                rl_benefits.setVisibility(View.GONE);
//                                rl_bottom.setVisibility(View.GONE);
//                                l1.setVisibility(View.GONE);
                           // }
                            if(exp_days==null||exp_days.equals("")){
                                expires_on.setVisibility(View.GONE);
                            }else {
                                expires_on.setVisibility(View.VISIBLE);
                                expires_on.setText("Expires in "+exp_days+" days");
                            }
                            //progressDialog.dismiss();


                        }
                        else if (appResponse.getResponseType().equals("300")) {
                         //   progressDialog.dismiss();
                            Toast.makeText(activity, appResponse.getResponseModel().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                    else {
                      //  progressDialog.dismiss();
                        Toast.makeText(activity, "internal server error" , Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AppResponse> call, Throwable t) {

                    Toast.makeText(activity,
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                   // progressDialog.dismiss();
                }
            });
        }
    }


    private void hideKeybaord() {

        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } }

}