package com.wisedrive.customerapp;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wisedrive.customerapp.adapters.Adapter_Select_Model;
import com.wisedrive.customerapp.adapters.Adapter_Uplaod_Image;
import com.wisedrive.customerapp.adapters.Adapter_select_make;
import com.wisedrive.customerapp.pojos.Pojo_Select_Make_list;
import com.wisedrive.customerapp.pojos.Pojo_Select_Model;
import com.wisedrive.customerapp.pojos.Pojo_Upload_Image;

import java.util.ArrayList;
import java.util.Calendar;

public class Add_New_Car extends AppCompatActivity {
    TextView tv_next, tv_back, tv_make, tv_basic, tv_photos, tv_insurance, tv_calender;
    RelativeLayout rl_fuel_transmission_details, check_box_diesel, check_box_petrol,
            check_box_manual, check_box_automatic, rl_select_model, rl_select_make, rl_basic_details,
            rl_photos, rl_insurance;
    ImageView tick_diesel, tick_petrol, tick_manual, tick_automatic,back;
    Spinner spinner, spinner_insurance_type, spinner_select_bank;
    DatePickerDialog picker;
    AppCompatButton add_car_button;
    View view_make,view_basic,view_photos,view_insurance,view_back,view_next;

    String[] year = {"Select Year", "2001", "2002", "2003", "2004", "2005", "2006", "2007"};
    String[] insurance_type = {"Select Insurance type", "Comprehensice-Standard",
            "Comprehensive-Cover", "Third Party Liability", "Own Damage Cover"};
    String[] select_bank_name = {"Select Bank Name", "Bank of Baroda", "HDFC", "SBI", "Kotak", "Bank of Baroda"};

    RecyclerView recycler_view_select_make;
    Adapter_select_make adapter_select_make;
    ArrayList<Pojo_Select_Make_list> pojo_select_make_listArrayList;

    RecyclerView recycler_view_select_model;
    Adapter_Select_Model adapter_select_model;
    ArrayList<Pojo_Select_Model> pojo_select_modelArrayList;

    RecyclerView recycler_view_upload_images;
    Adapter_Uplaod_Image adapter_uplaod_image;
    ArrayList<Pojo_Upload_Image> pojo_upload_imageArrayList;
    private RecyclerView grid;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_car);
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Add_New_Car.this, Warranty_Description.class);
                startActivity(intent);
            }
        });

        recycler_view_select_make = findViewById(R.id.recycler_view_select_make);
        recycler_view_select_model = findViewById(R.id.recycler_view_select_model);
        recycler_view_upload_images = findViewById(R.id.recycler_view_upload_images);


        pojo_select_make_listArrayList = new ArrayList<>();
        pojo_select_make_listArrayList.add(new Pojo_Select_Make_list(R.drawable.logo_fiat));
        pojo_select_make_listArrayList.add(new Pojo_Select_Make_list(R.drawable.logo_fiat));
        pojo_select_make_listArrayList.add(new Pojo_Select_Make_list(R.drawable.logo_fiat));
        pojo_select_make_listArrayList.add(new Pojo_Select_Make_list(R.drawable.logo_fiat));
        pojo_select_make_listArrayList.add(new Pojo_Select_Make_list(R.drawable.logo_fiat));
        pojo_select_make_listArrayList.add(new Pojo_Select_Make_list(R.drawable.logo_fiat));
        pojo_select_make_listArrayList.add(new Pojo_Select_Make_list(R.drawable.logo_fiat));
        pojo_select_make_listArrayList.add(new Pojo_Select_Make_list(R.drawable.logo_fiat));
        pojo_select_make_listArrayList.add(new Pojo_Select_Make_list(R.drawable.logo_fiat));
        pojo_select_make_listArrayList.add(new Pojo_Select_Make_list(R.drawable.logo_fiat));
        pojo_select_make_listArrayList.add(new Pojo_Select_Make_list(R.drawable.logo_fiat));
        pojo_select_make_listArrayList.add(new Pojo_Select_Make_list(R.drawable.logo_fiat));
        pojo_select_make_listArrayList.add(new Pojo_Select_Make_list(R.drawable.logo_fiat));
        pojo_select_make_listArrayList.add(new Pojo_Select_Make_list(R.drawable.logo_fiat));
        pojo_select_make_listArrayList.add(new Pojo_Select_Make_list(R.drawable.logo_fiat));
        pojo_select_make_listArrayList.add(new Pojo_Select_Make_list(R.drawable.logo_fiat));
        adapter_select_make = new Adapter_select_make(this, pojo_select_make_listArrayList);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        recycler_view_select_make.setLayoutManager(layoutManager);
        recycler_view_select_make.setAdapter(adapter_select_make);


        pojo_select_modelArrayList = new ArrayList<>();
        pojo_select_modelArrayList.add(new Pojo_Select_Model("Omni"));
        pojo_select_modelArrayList.add(new Pojo_Select_Model("Zen Zen Classic"));
        pojo_select_modelArrayList.add(new Pojo_Select_Model("Grand Vitara xl7"));
        pojo_select_modelArrayList.add(new Pojo_Select_Model("Omni"));
        pojo_select_modelArrayList.add(new Pojo_Select_Model("Omni"));
        pojo_select_modelArrayList.add(new Pojo_Select_Model("Omni"));
        pojo_select_modelArrayList.add(new Pojo_Select_Model("Omni"));
        pojo_select_modelArrayList.add(new Pojo_Select_Model("Omni"));
        pojo_select_modelArrayList.add(new Pojo_Select_Model("Omni"));
        pojo_select_modelArrayList.add(new Pojo_Select_Model("Omni"));
        pojo_select_modelArrayList.add(new Pojo_Select_Model("Omni"));
        pojo_select_modelArrayList.add(new Pojo_Select_Model("Omni"));
        adapter_select_model = new Adapter_Select_Model(this, pojo_select_modelArrayList);
        GridLayoutManager layoutManager1 = new GridLayoutManager(this, 3);
        recycler_view_select_model.setLayoutManager(layoutManager1);
        recycler_view_select_model.setAdapter(adapter_select_model);

        pojo_upload_imageArrayList = new ArrayList<>();
        pojo_upload_imageArrayList.add(new Pojo_Upload_Image("Front side Car Number", R.drawable.upload_image_icon));
        pojo_upload_imageArrayList.add(new Pojo_Upload_Image("Front side Car Number", R.drawable.upload_image_icon));
        pojo_upload_imageArrayList.add(new Pojo_Upload_Image("Front side Car Number", R.drawable.upload_image_icon));
        pojo_upload_imageArrayList.add(new Pojo_Upload_Image("Front side Car Number", R.drawable.upload_image_icon));
        pojo_upload_imageArrayList.add(new Pojo_Upload_Image("Front side Car Number", R.drawable.upload_image_icon));
        pojo_upload_imageArrayList.add(new Pojo_Upload_Image("Front side Car Number", R.drawable.upload_image_icon));
        pojo_upload_imageArrayList.add(new Pojo_Upload_Image("Front side Car Number", R.drawable.upload_image_icon));
        pojo_upload_imageArrayList.add(new Pojo_Upload_Image("Front side Car Number", R.drawable.upload_image_icon));
        pojo_upload_imageArrayList.add(new Pojo_Upload_Image("Front side Car Number", R.drawable.upload_image_icon));
        adapter_uplaod_image = new Adapter_Uplaod_Image(this, pojo_upload_imageArrayList);
        GridLayoutManager linearLayoutManager2 = new GridLayoutManager(this, 3);
        recycler_view_upload_images.setLayoutManager(linearLayoutManager2);
        recycler_view_upload_images.setAdapter(adapter_uplaod_image);


        rl_fuel_transmission_details = findViewById(R.id.rl_fuel_transmission_details);
        rl_select_model = findViewById(R.id.rl_select_model);
        rl_select_make = findViewById(R.id.rl_select_make);
        rl_basic_details = findViewById(R.id.rl_basic_details);
        rl_photos = findViewById(R.id.rl_photos);
        rl_insurance = findViewById(R.id.rl_insurance);
        view_next=findViewById(R.id.view_next);
        tv_next = findViewById(R.id.tv_next);
        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_select_model.setVisibility(View.VISIBLE);
                rl_select_make.setVisibility(View.INVISIBLE);
                rl_fuel_transmission_details.setVisibility(View.INVISIBLE);
                rl_photos.setVisibility(View.INVISIBLE);
                rl_insurance.setVisibility(View.INVISIBLE);


            }
        });
        view_back=findViewById(R.id.view_back);
        tv_back = findViewById(R.id.tv_back);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_select_model.setVisibility(View.INVISIBLE);
                rl_select_make.setVisibility(View.VISIBLE);
                rl_basic_details.setVisibility(View.INVISIBLE);
                rl_photos.setVisibility(View.INVISIBLE);
                rl_insurance.setVisibility(View.INVISIBLE);

            }
        });

        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Add_New_Car.this, android.R.layout.simple_spinner_item, year);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_insurance_type = findViewById(R.id.spinner_insurance_type);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(Add_New_Car.this, android.R.layout.simple_spinner_item, insurance_type);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_insurance_type.setAdapter(adapter1);

        spinner_insurance_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_select_bank = findViewById(R.id.spinner_select_bank);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(Add_New_Car.this, android.R.layout.simple_spinner_item, select_bank_name);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_select_bank.setAdapter(adapter2);

        spinner_select_bank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        check_box_petrol = findViewById(R.id.check_box_petrol);
        check_box_diesel = findViewById(R.id.check_box_diesel);
        check_box_manual = findViewById(R.id.check_box_manual);
        check_box_automatic = findViewById(R.id.check_box_automatic);
        tick_diesel = findViewById(R.id.tick_diesel);
        tick_petrol = findViewById(R.id.tick_petrol);
        tick_manual = findViewById(R.id.tick_manual);
        tick_automatic = findViewById(R.id.tick_automatic);
        check_box_diesel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_box_diesel.setBackgroundResource(R.drawable.checkbox_selected);
                check_box_petrol.setBackgroundResource(R.drawable.background_checkbox);
                tick_diesel.setVisibility(View.VISIBLE);
                tick_petrol.setVisibility(View.INVISIBLE);


            }
        });
        check_box_petrol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_box_diesel.setBackgroundResource(R.drawable.background_checkbox);
                check_box_petrol.setBackgroundResource(R.drawable.checkbox_selected);
                tick_diesel.setVisibility(View.INVISIBLE);
                tick_petrol.setVisibility(View.VISIBLE);

            }
        });

        check_box_manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_box_manual.setBackgroundResource(R.drawable.checkbox_selected);
                check_box_automatic.setBackgroundResource(R.drawable.background_checkbox);
                tick_manual.setVisibility(View.VISIBLE);
                tick_automatic.setVisibility(View.INVISIBLE);
            }
        });
        check_box_automatic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_box_manual.setBackgroundResource(R.drawable.background_checkbox);
                check_box_automatic.setBackgroundResource(R.drawable.checkbox_selected);
                tick_manual.setVisibility(View.INVISIBLE);
                tick_automatic.setVisibility(View.VISIBLE);

            }
        });
        view_basic=findViewById(R.id.view_basic);
        tv_basic = findViewById(R.id.tv_basic);
        tv_basic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_basic_details.setVisibility(View.VISIBLE);
                rl_select_make.setVisibility(View.INVISIBLE);
                rl_select_model.setVisibility(View.INVISIBLE);
                rl_photos.setVisibility(View.INVISIBLE);
                rl_insurance.setVisibility(View.INVISIBLE);
                tv_make.setTextColor(getResources().getColor(R.color.lightgrey));
                tv_basic.setTextColor(getResources().getColor(R.color.blue));
                tv_photos.setTextColor(getResources().getColor(R.color.lightgrey));
                tv_insurance.setTextColor(getResources().getColor(R.color.lightgrey));
                view_next.setVisibility(View.VISIBLE);
                view_basic.setVisibility(View.VISIBLE);
                view_make.setVisibility(View.INVISIBLE);
                view_photos.setVisibility(View.INVISIBLE);
                view_insurance.setVisibility(View.INVISIBLE);
                add_car_button.setVisibility(View.INVISIBLE);


            }
        });
        view_make=findViewById(R.id.view_make);
        tv_make = findViewById(R.id.tv_make);
        tv_make.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_make.setTextColor(getResources().getColor(R.color.blue));
                tv_basic.setTextColor(getResources().getColor(R.color.lightgrey));
                tv_photos.setTextColor(getResources().getColor(R.color.lightgrey));
                tv_insurance.setTextColor(getResources().getColor(R.color.lightgrey));
                rl_select_make.setVisibility(View.VISIBLE);
                rl_basic_details.setVisibility(View.INVISIBLE);
                rl_photos.setVisibility(View.INVISIBLE);
                rl_insurance.setVisibility(View.INVISIBLE);
                view_next.setVisibility(View.VISIBLE);
                view_make.setVisibility(View.VISIBLE);
                view_basic.setVisibility(View.INVISIBLE);
                view_photos.setVisibility(View.INVISIBLE);
                view_insurance.setVisibility(View.INVISIBLE);
                add_car_button.setVisibility(View.INVISIBLE);
            }
        });
        view_photos=findViewById(R.id.view_photos);
        tv_photos = findViewById(R.id.tv_photos);
        tv_photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_make.setTextColor(getResources().getColor(R.color.lightgrey));
                tv_basic.setTextColor(getResources().getColor(R.color.lightgrey));
                tv_photos.setTextColor(getResources().getColor(R.color.blue));
                tv_insurance.setTextColor(getResources().getColor(R.color.lightgrey));
                rl_basic_details.setVisibility(View.INVISIBLE);
                rl_select_make.setVisibility(View.INVISIBLE);
                rl_select_model.setVisibility(View.INVISIBLE);
                rl_insurance.setVisibility(View.INVISIBLE);
                rl_photos.setVisibility(View.VISIBLE);
                view_next.setVisibility(View.VISIBLE);
                view_photos.setVisibility(View.VISIBLE);
                view_make.setVisibility(View.INVISIBLE);
                view_insurance.setVisibility(View.INVISIBLE);
                view_basic.setVisibility(View.INVISIBLE);
                add_car_button.setVisibility(View.INVISIBLE);

            }
        });
        add_car_button = findViewById(R.id.add_car_button);
        view_insurance=findViewById(R.id.view_insurance);
        tv_insurance = findViewById(R.id.tv_insurance);
        tv_insurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_basic_details.setVisibility(View.INVISIBLE);
                rl_select_make.setVisibility(View.INVISIBLE);
                rl_select_model.setVisibility(View.INVISIBLE);
                rl_photos.setVisibility(View.INVISIBLE);
                rl_insurance.setVisibility(View.VISIBLE);
                tv_make.setTextColor(getResources().getColor(R.color.lightgrey));
                tv_basic.setTextColor(getResources().getColor(R.color.lightgrey));
                tv_photos.setTextColor(getResources().getColor(R.color.lightgrey));
                tv_insurance.setTextColor(getResources().getColor(R.color.blue));
                view_next.setVisibility(View.INVISIBLE);
                view_insurance.setVisibility(View.VISIBLE);
                view_photos.setVisibility(View.INVISIBLE);
                view_make.setVisibility(View.INVISIBLE);
                view_basic.setVisibility(View.INVISIBLE);
                add_car_button.setVisibility(View.VISIBLE);

            }
        });

        tv_calender = findViewById(R.id.tv_calender);
        tv_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                picker = new DatePickerDialog(Add_New_Car.this,
                        new DatePickerDialog.OnDateSetListener() {


                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                tv_calender.setText(String.valueOf(dayOfMonth) + "/" + (monthOfYear + 1) + "/" + year);
                                Toast.makeText(Add_New_Car.this, "Date Added Successfully", Toast.LENGTH_LONG).show();


                            }
                        }, mYear, mMonth, mDay);
                picker.show();

            }
        });
        add_car_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Add_New_Car.this, Warranty_Description.class);
               // Description_page. relative_layout_mycar.setVisibility(View.VISIBLE);

                startActivity(intent);


            }
        });


    }
}









