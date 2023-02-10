package com.wisedrive.customerapp;

import static com.amazonaws.util.ClassLoaderHelper.getResource;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Customer_Home_page_bottom_navigation extends AppCompatActivity {
    LinearLayout linear_layout_plans,linear_layout_mycar,linear_layout_activate,linear_layout_profile;
    TextView text_plans,text_mycar,text_activate,text_profile;
    ImageView image_plans,image_mycar,image_activate,image_profile;
   public Fragment fragment=null;
   RecyclerView recyclerView;
  public RelativeLayout rl_add_car_button;




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home_page_bottom_navigation);
        rl_add_car_button=findViewById(R.id.rl_add_car_button);
        rl_add_car_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Customer_Home_page_bottom_navigation.this,Add_New_Car.class);
                startActivity(intent);

            }
        });


        linear_layout_plans=findViewById(R.id.linear_layout_plans);
        linear_layout_mycar=findViewById(R.id.linear_layout_mycar);
        linear_layout_activate=findViewById(R.id.linear_layout_activate);
        linear_layout_profile=findViewById(R.id.linear_layout_profile);
        text_plans=findViewById(R.id.text_plans);
        text_mycar=findViewById(R.id.text_mycar);
        text_activate=findViewById(R.id.text_activate);
        text_profile=findViewById(R.id.text_profile);
        image_plans=findViewById(R.id.image_plans);
        image_mycar=findViewById(R.id.image_mycar);
        image_activate=findViewById(R.id.image_activate);
        image_profile=findViewById(R.id.image_profile);

        fragment= new Plans_Fragments();
        replaceFragment(fragment);


        linear_layout_plans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment= new Plans_Fragments();
                replaceFragment(fragment);

                text_plans.setVisibility(View.VISIBLE);
                text_mycar.setVisibility(View.GONE);
                text_activate.setVisibility(View.GONE);
                text_profile.setVisibility(View.GONE);
                image_plans.setImageResource(R.drawable.plans_2);
                image_mycar.setImageResource(R.drawable.mycar);
                image_activate.setImageResource(R.drawable.gray_star);
                image_profile.setImageResource(R.drawable.profile);
                text_plans.setTextColor(getResources().getColor(R.color.orange));
                linear_layout_plans.setBackgroundResource(R.drawable.rounded_background);
                linear_layout_mycar.setBackgroundResource(R.drawable.rounded_background_2);
                linear_layout_activate.setBackgroundResource(R.drawable.rounded_background_2);
                linear_layout_profile.setBackgroundResource(R.drawable.rounded_background_2);
                rl_add_car_button.setVisibility(View.INVISIBLE);



            }
        });
        linear_layout_mycar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                fragment= new MyCar_Fragment();
                replaceFragment(fragment);
                text_plans.setVisibility(View.GONE);
                text_mycar.setVisibility(View.VISIBLE);
                text_activate.setVisibility(View.GONE);
                text_profile.setVisibility(View.GONE);
                image_plans.setImageResource(R.drawable.plans);
                image_mycar.setImageResource(R.drawable.car_image_2);
                image_activate.setImageResource(R.drawable.gray_star);
                image_profile.setImageResource(R.drawable.profile);
                text_mycar.setTextColor(getResources().getColor(R.color.orange));
                linear_layout_plans.setBackgroundResource(R.drawable.rounded_background_2);
                linear_layout_mycar.setBackgroundResource(R.drawable.rounded_background);
                linear_layout_activate.setBackgroundResource(R.drawable.rounded_background_2);
                linear_layout_profile.setBackgroundResource(R.drawable.rounded_background_2);
                rl_add_car_button.setVisibility(View.VISIBLE);



            }
        });
        linear_layout_activate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                fragment= new Activate_Fragment();
                replaceFragment(fragment);
                text_plans.setVisibility(View.GONE);
                text_mycar.setVisibility(View.GONE);
                text_activate.setVisibility(View.VISIBLE);
                text_profile.setVisibility(View.GONE);
                text_activate.setTextColor(getResources().getColor(R.color.orange));
                image_plans.setImageResource(R.drawable.plans);
                image_mycar.setImageResource(R.drawable.mycar);
                image_activate.setImageResource(R.drawable.light_orange_star);
                image_profile.setImageResource(R.drawable.profile);
                linear_layout_plans.setBackgroundResource(R.drawable.rounded_background_2);
                linear_layout_mycar.setBackgroundResource(R.drawable.rounded_background_2);
                linear_layout_activate.setBackgroundResource(R.drawable.rounded_background);
                linear_layout_profile.setBackgroundResource(R.drawable.rounded_background_2);
                rl_add_car_button.setVisibility(View.INVISIBLE);

            }
        });
        linear_layout_profile.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                fragment= new Profile_Fragment();
                replaceFragment(fragment);
                text_plans.setVisibility(View.GONE);
                text_mycar.setVisibility(View.GONE);
                text_activate.setVisibility(View.GONE);
                text_profile.setVisibility(View.VISIBLE);
                text_profile.setTextColor(getResources().getColor(R.color.orange));
                image_plans.setImageResource(R.drawable.plans);
                image_mycar.setImageResource(R.drawable.mycar);
                image_activate.setImageResource(R.drawable.gray_star);
                image_profile.setImageResource(R.drawable.profile_2);
                linear_layout_plans.setBackgroundResource(R.drawable.rounded_background_2);
                linear_layout_mycar.setBackgroundResource(R.drawable.rounded_background_2);
                linear_layout_activate.setBackgroundResource(R.drawable.rounded_background_2);
                linear_layout_profile.setBackgroundResource(R.drawable.rounded_background);
                rl_add_car_button.setVisibility(View.INVISIBLE);
            }
        });






            }
    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }









    }

