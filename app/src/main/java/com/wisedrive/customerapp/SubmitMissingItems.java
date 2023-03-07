package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wisedrive.customerapp.adapters.AdapterMissingItems;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.pojos.ItemsList;
import com.wisedrive.customerapp.pojos.PojoLostItems;
import com.wisedrive.customerapp.pojos.PojoSubmitItems;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubmitMissingItems extends AppCompatActivity {

    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    RecyclerView rv_missingitems;
    ArrayList<PojoLostItems> itemslist;
    ArrayList<ItemsList>  itemsListArrayList;
     public  AdapterMissingItems adapterMissingItems;
     TextView submit_missing_items;
     EditText feedback_comments;
     ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_missing_items);
        itemslist=new ArrayList<PojoLostItems>();
        itemsListArrayList=new ArrayList<ItemsList>();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        progressDialog = new ProgressDialog(SubmitMissingItems.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        back=findViewById(R.id.back);
        feedback_comments=findViewById(R.id.feedback_comments);
        submit_missing_items=findViewById(R.id.submit_missing_items);
        rv_missingitems=findViewById(R.id.rv_missingitems);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        get_lost_items();
        submit_missing_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                itemsListArrayList=new ArrayList<>();
                for(int i=0;i<itemslist.size();i++)
                {
                    ItemsList model=new ItemsList();
                    if(itemslist.get(i).getIsSelected().equalsIgnoreCase("y"))
                    {
                        model.setServiceId(SPHelper.service_id);
                        model.setLostId(itemslist.get(i).getId());
                        model.setComment(feedback_comments.getText().toString());
                        itemsListArrayList.add(model);
                    }
                }

                if(itemsListArrayList.isEmpty()){
                    Toast.makeText(SubmitMissingItems.this,"select items",Toast.LENGTH_SHORT).show();
                }else{
                    post_lostitems();
                }
            }
        });
    }
    public  void get_lost_items(){
        if(!Connectivity.isNetworkConnected(SubmitMissingItems.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }else
        {
            progressDialog.show();
            Call<AppResponse> call =  apiInterface.get_lostitems();
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response)
                {
                    AppResponse appResponse = response.body();

                    if (response.body()!=null) {
                        assert appResponse != null;
                        if (appResponse.getResponseType().equals("200")) {
                            progressDialog.dismiss();
                            itemslist = new ArrayList();
                            itemslist=appResponse.getResponseModel().getLostItemList();
                            adapterMissingItems = new AdapterMissingItems(itemslist, SubmitMissingItems.this);
                            GridLayoutManager layoutManager1 = new GridLayoutManager(getApplicationContext(),3);
                            rv_missingitems.setLayoutManager(layoutManager1);
                            rv_missingitems.setAdapter(adapterMissingItems);

                            SubmitMissingItems.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapterMissingItems.notifyDataSetChanged();
                                }
                            });

                        } else if (appResponse.getResponseType().equals("300")) {
                            progressDialog.dismiss();
                            Toast.makeText(SubmitMissingItems.this, "internal server error" + "response code:" + appResponse.getResponseType(), Toast.LENGTH_SHORT).show();

                        }
                    }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(SubmitMissingItems.this, "internal server error" , Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AppResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }
    }
    private void post_lostitems() {
        if (!Connectivity.isNetworkConnected(SubmitMissingItems.this)) {
            Toast.makeText(SubmitMissingItems.this, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
            return;
        } else {
            progressDialog.show();
            PojoSubmitItems post1=new PojoSubmitItems(itemsListArrayList);
            Call<AppResponse> call = apiInterface.update_lostitems(post1);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                    System.out.print(response.body());
                    if (response.body() != null) {
                        AppResponse data = response.body();
                        if (data.getResponseType().equalsIgnoreCase("200")) {
                            Toast.makeText(SubmitMissingItems.this, "We have taken your request,We will contact you soon!", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(SubmitMissingItems.this, ServiceCompletedPage.class);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(SubmitMissingItems.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<AppResponse> call, Throwable th) {
                    Toast.makeText(SubmitMissingItems.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }
    }
}