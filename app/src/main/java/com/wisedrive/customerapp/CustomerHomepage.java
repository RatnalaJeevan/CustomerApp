package com.wisedrive.customerapp;

import static com.amazonaws.util.ClassLoaderHelper.getResource;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wisedrive.customerapp.commonclasses.SPHelper;

public class CustomerHomepage extends AppCompatActivity
{
    boolean doubleBackToExitPressedOnce = false;
    public RelativeLayout relative_layout1,rl_mycars,rl_back;
    LinearLayout linear_layout_plans,linear_layout_mycar,linear_layout_activate,linear_layout_profile;
    TextView text_plans,text_mycar,text_activate,text_profile;
    ImageView image_plans,image_mycar,image_activate,image_profile,back,wd_logo,image_bell;
    public Fragment fragment=null;
    public RelativeLayout rl_add_car_button;
    int count1=0,count2=0,count3=0,count4=0;
    public  static  CustomerHomepage instance;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home_page_bottom_navigation);
        instance=this;
        image_bell=findViewById(R.id.image_bell);
        wd_logo=findViewById(R.id.wd_logo);
        back=findViewById(R.id.back);
        rl_back=findViewById(R.id.rl_back);
        rl_mycars=findViewById(R.id.rl_mycars);
        relative_layout1=findViewById(R.id.relative_layout1);
        rl_add_car_button=findViewById(R.id.rl_add_car_button);
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


        if(SPHelper.comingfrom.equals("added"))
        {
            plans_fragment();
            CongratsPage bottomSheetDialogFragment = new CongratsPage();
            bottomSheetDialogFragment.show(CustomerHomepage.this.getSupportFragmentManager(), "CongratsPage");
        }else{
            if(SPHelper.fragment_is.equals("plans")){
                plans_fragment();
            }else if(SPHelper.fragment_is.equals("cars")){
                cars_fragment();
            }else if(SPHelper.fragment_is.equals("act")){
                act_fragment();
            }else {
                profile_fragmnet();
            }
        }

        linear_layout_plans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.comingfrom="";
                SPHelper.fragment_is="plans";
                plans_fragment();
            }
        });
        linear_layout_mycar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SPHelper.comingfrom="";
                SPHelper.fragment_is="cars";
                cars_fragment();
            }
        });
        linear_layout_activate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SPHelper.comingfrom="";
                SPHelper.fragment_is="act";
                act_fragment();
            }
        });
        linear_layout_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.comingfrom="";
                SPHelper.fragment_is="profile";
                profile_fragmnet();
            }
        });
        rl_add_car_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPHelper.carmodelid="";
                SPHelper.carbrandid="";
//                Intent intent=new Intent(CustomerHomepage.this,Add_New_Car.class);
//                startActivity(intent);

                Intent intent=new Intent(CustomerHomepage.this,EnterCarDetails.class);
                startActivity(intent);
            }
        });

        rl_mycars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SPHelper.comingfrom="";
                SPHelper.fragment_is="cars";
                cars_fragment();
            }
        });

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SPHelper.fragment_is.equals("cars")||SPHelper.fragment_is.equals("profile")||
                SPHelper.fragment_is.equals("act")){
                    back.setImageResource(R.drawable.menu);
                    SPHelper.comingfrom="";
                    SPHelper.fragment_is="plans";
                    plans_fragment();
                }
                else {
                    SPHelper.comingfrom="";
                    SPHelper.fragment_is="profile";
                    profile_fragmnet();
                }
            }
        });

        image_bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CustomerHomepage.this,PushNotification.class);
                startActivity(intent);

            }
        });
      }

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    public void plans_fragment()
    {
        if(count1==0){
            fragment= new Plans_Fragments();
            replaceFragment(fragment);
            text_plans.setVisibility(View.VISIBLE);
            text_mycar.setVisibility(View.GONE);
            text_activate.setVisibility(View.GONE);
            text_profile.setVisibility(View.GONE);
            image_plans.setImageResource(R.drawable.plans_selected);
            image_mycar.setImageResource(R.drawable.mycar);
            image_activate.setImageResource(R.drawable.gray_star);
            image_profile.setImageResource(R.drawable.profile);
            text_plans.setTextColor(getResources().getColor(R.color.orange));
            linear_layout_plans.setBackgroundResource(R.drawable.rounded_background);
            linear_layout_mycar.setBackgroundResource(R.drawable.rounded_background_2);
            linear_layout_activate.setBackgroundResource(R.drawable.rounded_background_2);
            linear_layout_profile.setBackgroundResource(R.drawable.rounded_background_2);
           // relative_layout1.setBackgroundResource(R.color.nav_bg);
            rl_add_car_button.setVisibility(View.INVISIBLE);
            image_bell.setVisibility(View.GONE);
            rl_mycars.setVisibility(View.VISIBLE);
            wd_logo.setImageResource(R.drawable.wd_neu);
            count1=1;
            count2=0;
            count3=0;
            count4=0;
        }

    }
    public void cars_fragment(){
        if(count2==0){
            fragment= new MyCar_Fragment();
            replaceFragment(fragment);
            text_plans.setVisibility(View.GONE);
            text_mycar.setVisibility(View.VISIBLE);
            text_activate.setVisibility(View.GONE);
            text_profile.setVisibility(View.GONE);
            image_plans.setImageResource(R.drawable.plans_unselected);
            image_mycar.setImageResource(R.drawable.car_image_2);
            image_activate.setImageResource(R.drawable.gray_star);
            image_profile.setImageResource(R.drawable.profile);
            text_mycar.setTextColor(getResources().getColor(R.color.orange));
            linear_layout_plans.setBackgroundResource(R.drawable.rounded_background_2);
            linear_layout_mycar.setBackgroundResource(R.drawable.rounded_background);
            linear_layout_activate.setBackgroundResource(R.drawable.rounded_background_2);
            linear_layout_profile.setBackgroundResource(R.drawable.rounded_background_2);
            back.setImageResource(R.drawable.back_new_black);
            wd_logo.setImageDrawable(null);
            image_bell.setVisibility(View.GONE);
            rl_add_car_button.setVisibility(View.VISIBLE);
            rl_mycars.setVisibility(View.GONE);
            count2=1;
            count1=0;
            count3=0;
            count4=0;
        }

    }
    public void act_fragment(){
        if(count3==0){
            fragment= new CheckMyCar();
            replaceFragment(fragment);
            back.setImageResource(R.drawable.back_new_black);
            text_plans.setVisibility(View.GONE);
            text_mycar.setVisibility(View.GONE);
            text_activate.setVisibility(View.VISIBLE);
            text_profile.setVisibility(View.GONE);
            text_activate.setTextColor(getResources().getColor(R.color.orange));
            image_plans.setImageResource(R.drawable.plans_unselected);
            image_mycar.setImageResource(R.drawable.mycar);
            image_activate.setImageResource(R.drawable.light_orange_star);
            image_profile.setImageResource(R.drawable.profile);
            linear_layout_plans.setBackgroundResource(R.drawable.rounded_background_2);
            linear_layout_mycar.setBackgroundResource(R.drawable.rounded_background_2);
            linear_layout_activate.setBackgroundResource(R.drawable.rounded_background);
            linear_layout_profile.setBackgroundResource(R.drawable.rounded_background_2);
            rl_add_car_button.setVisibility(View.INVISIBLE);
            rl_mycars.setVisibility(View.GONE);
            image_bell.setVisibility(View.GONE);
            wd_logo.setImageDrawable(null);
           // relative_layout1.setBackgroundResource(R.color.nav_bg);
            count3=1;
            count2=0;
            count1=0;
            count4=0;
        }

    }
    public void profile_fragmnet(){
        if(count4==0)
        {
            fragment= new Profile_Fragment();
            replaceFragment(fragment);
            back.setImageResource(R.drawable.back_new_black);
            text_plans.setVisibility(View.GONE);
            text_mycar.setVisibility(View.GONE);
            text_activate.setVisibility(View.GONE);
            text_profile.setVisibility(View.VISIBLE);
            text_profile.setTextColor(getResources().getColor(R.color.orange));
            image_plans.setImageResource(R.drawable.plans_unselected);
            image_mycar.setImageResource(R.drawable.mycar);
            image_activate.setImageResource(R.drawable.gray_star);
            image_profile.setImageResource(R.drawable.profile_2);
            linear_layout_plans.setBackgroundResource(R.drawable.rounded_background_2);
            linear_layout_mycar.setBackgroundResource(R.drawable.rounded_background_2);
            linear_layout_activate.setBackgroundResource(R.drawable.rounded_background_2);
            linear_layout_profile.setBackgroundResource(R.drawable.rounded_background);
           // relative_layout1.setBackgroundResource(R.color.nav_bg);
            rl_add_car_button.setVisibility(View.INVISIBLE);
            image_bell.setVisibility(View.GONE);
            rl_mycars.setVisibility(View.GONE);
            wd_logo.setImageResource(R.drawable.wd_neu);
            count4=1;
            count2=0;
            count3=0;
            count1=0;
        }
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce)
        {
           // super.onBackPressed();
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        }else {
            this.doubleBackToExitPressedOnce = true;
            // Common.CallToast(this,"Press again to close wisedrive",1);
            Toast.makeText(this,"Press again to close wisedrive", Toast.LENGTH_SHORT).show();
        }

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 4000);
    }

    public  static CustomerHomepage getInstance() {
        return instance;
    }
}

