package com.wisedrive.customerapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wisedrive.customerapp.adapters.Adapter_My_Car_Page_Package_list;
import com.wisedrive.customerapp.adapters.Adapter_class_mycar;
import com.wisedrive.customerapp.pojos.Pojo_Class_Mycar;
import com.wisedrive.customerapp.pojos.Pojo_My_Car_page_package_list;

import java.util.ArrayList;


public class MyCar_Fragment extends Fragment {
    private RecyclerView recycler_view_mycars;
    Adapter_class_mycar adapter_class_mycar;
    ArrayList<Pojo_Class_Mycar> pojo_class_mycarArrayList;
    Context context;
    View view;


    private  RecyclerView rv_service_list;
    Adapter_My_Car_Page_Package_list adapter_my_car_warranty_list;
    ArrayList<Pojo_My_Car_page_package_list>pojo_my_car_warranty_listArrayList;

    public MyCar_Fragment(){
    }
    public static Fragment newInstance() {
        return null;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_car_, container, false);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        RecyclerView recycler_view_mycars = (RecyclerView) view.findViewById(R.id.recycler_view_mycars);
        recycler_view_mycars.setHasFixedSize(true);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        RecyclerView  rv_service_list = (RecyclerView) view.findViewById(R.id. rv_service_list);
        rv_service_list.setHasFixedSize(true);

        pojo_class_mycarArrayList = new ArrayList<>();
        pojo_class_mycarArrayList.add(new Pojo_Class_Mycar("DATSUN-","Cross","4000kms","Manual","Diesel",R.drawable.blue_car_image,R.drawable.kms,R.drawable.transmission,R.drawable.fuel_type));
        pojo_class_mycarArrayList.add(new Pojo_Class_Mycar("DATSUN-","Cross","4000kms","Manual","Petrol",R.drawable.blue_car_image,R.drawable.kms,R.drawable.transmission,R.drawable.fuel_type));
        pojo_class_mycarArrayList.add(new Pojo_Class_Mycar("DATSUN-","Cross","4000kms","Manual","Diesel",R.drawable.blue_car_image,R.drawable.kms,R.drawable.transmission,R.drawable.fuel_type));
        pojo_class_mycarArrayList.add(new Pojo_Class_Mycar("DATSUN-","Cross","4000kms","Manual","Petrol",R.drawable.blue_car_image,R.drawable.kms,R.drawable.transmission,R.drawable.fuel_type));
        pojo_class_mycarArrayList.add(new Pojo_Class_Mycar("DATSUN-","Cross","4000kms","Manual","Petrol",R.drawable.blue_car_image,R.drawable.kms,R.drawable.transmission,R.drawable.fuel_type));
        adapter_class_mycar= new Adapter_class_mycar(getContext(), pojo_class_mycarArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recycler_view_mycars.setLayoutManager(linearLayoutManager);
        recycler_view_mycars.setAdapter(adapter_class_mycar);



        pojo_my_car_warranty_listArrayList = new ArrayList<>();
        pojo_my_car_warranty_listArrayList.add(new Pojo_My_Car_page_package_list(R.drawable.blue_right,"Comprehensive warranty","No Claim Pending","1",R.color.dark_bluu,R.drawable.right_arrow_blue));
        pojo_my_car_warranty_listArrayList.add(new Pojo_My_Car_page_package_list(R.drawable.green_image,"Showroom Service","No Claim Pending","2",R.color.dark_green,R.drawable.right_icin_green));
        pojo_my_car_warranty_listArrayList.add(new Pojo_My_Car_page_package_list(R.drawable.orange_image,"Roadside Assistance","No Claim Pending","3",R.color.dark_orange,R.drawable.right_icon_orange));
        adapter_my_car_warranty_list= new Adapter_My_Car_Page_Package_list(getContext(), pojo_my_car_warranty_listArrayList);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_service_list.setLayoutManager(linearLayoutManager2);
        rv_service_list.setAdapter(adapter_my_car_warranty_list);











        return view;
    }
}