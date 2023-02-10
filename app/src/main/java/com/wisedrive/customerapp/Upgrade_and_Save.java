package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wisedrive.customerapp.adapters.Adapter_Q_And_A;
import com.wisedrive.customerapp.adapters.Adapter_Upgrade_Save;
import com.wisedrive.customerapp.pojos.Pojo_Q_And_A;
import com.wisedrive.customerapp.pojos.Pojo_Upgrade_Save;

import java.util.ArrayList;

public class Upgrade_and_Save extends AppCompatActivity {
    RecyclerView rv_upgrade;
    Adapter_Upgrade_Save adapter_upgrade_save;
    ArrayList<Pojo_Upgrade_Save> pojo_upgrade_saveArrayList;
    Context context;
    View view;
    RelativeLayout rl_upgrade_button;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade_and_save);

        rv_upgrade=findViewById(R.id.rv_upgrade);

        pojo_upgrade_saveArrayList = new ArrayList<>();
        pojo_upgrade_saveArrayList.add(new Pojo_Upgrade_Save(R.drawable.up_arrow_1,"FREE upgrade to 1 year Comprehensive Warranty with Buy back guarantee"));
        pojo_upgrade_saveArrayList.add(new Pojo_Upgrade_Save(R.drawable.up_arrow_1,"Free Roadside Assistance for 1 year worth Rs 6000"));
        pojo_upgrade_saveArrayList.add(new Pojo_Upgrade_Save(R.drawable.up_arrow_1,"OEM Authorised Showroom Service ONLY"));
        pojo_upgrade_saveArrayList.add(new Pojo_Upgrade_Save(R.drawable.up_arrow_1,"ONE General Service (Includes Engine Oil,Parts and Labor"));
        pojo_upgrade_saveArrayList.add(new Pojo_Upgrade_Save(R.drawable.up_arrow_1,"ONE AC Service (Includes Gas top-up,Air Filter and Labor"));
        pojo_upgrade_saveArrayList.add(new Pojo_Upgrade_Save(R.drawable.up_arrow_1,"ONE Entire Vehicle Health Check-up"));
        adapter_upgrade_save = new Adapter_Upgrade_Save(this, pojo_upgrade_saveArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Upgrade_and_Save.this, LinearLayoutManager.VERTICAL, false);
        rv_upgrade.setLayoutManager(linearLayoutManager);
        rv_upgrade.setAdapter(adapter_upgrade_save);


        rl_upgrade_button=findViewById(R.id.rl_upgrade_button);
        rl_upgrade_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Upgrade_and_Save.this,Recommended_Activity.class);
                startActivity(intent);
            }
        });
    }
}