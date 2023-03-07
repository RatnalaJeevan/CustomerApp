package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.pojos.PojoFeedbackList;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerFeedBack extends AppCompatActivity
{
    ApiInterface apiInterface;
    ProgressDialog progressDialog;
    Spinner feedback_menu;
    ArrayList<String> statusname,statusid;
    String selectedstatusid;
    ImageView back;
    RatingBar rating;
    EditText customer_comments;
    TextView submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_feed_back);
        statusname = new ArrayList<>();
        statusid = new ArrayList<>();
        submit=findViewById(R.id.submit);
        customer_comments=findViewById(R.id.customer_comments);
        feedback_menu=(Spinner)findViewById(R.id.feedback_menu);
        rating=(RatingBar)findViewById(R.id.rating);
        back=findViewById(R.id.back);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        progressDialog = new ProgressDialog(CustomerFeedBack.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CustomerFeedBack.this, ServiceCompletedPage.class);
                startActivity(intent);
                finish();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ratingpoint=String.valueOf(rating.getRating());
                if(ratingpoint.equals("0.0")){
                    Toast.makeText(getApplicationContext(),"Please rate",Toast.LENGTH_SHORT).show();
                }else if(selectedstatusid.equals("0")){
                    Toast.makeText(getApplicationContext(),"Please Select Feedback",Toast.LENGTH_SHORT).show();
                }
                    else{
                    post_feedback();
                }

            }
        });
        servicecall_getFeedback();
        feedback_menu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                TextView tv = (TextView) view;
                String item = parent.getItemAtPosition(position).toString();
                if (position == 0) {
                    tv.setTextColor(Color.rgb(160, 160, 160));
                    tv.setTextSize(15);
                    tv.setTypeface(Typeface.DEFAULT);
                } else {
                    tv.setTextColor(Color.rgb(0, 0, 0));
                    tv.setTextSize(15);
                    tv.setTypeface(Typeface.DEFAULT);
                }


                 if(feedback_menu.getSelectedItemPosition()>=0){
                    selectedstatusid=statusid.get(position);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    public void servicecall_getFeedback() {
        if(!Connectivity.isNetworkConnected(CustomerFeedBack.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            progressDialog.show();
            Call<AppResponse> call = apiInterface.get_feedbackitems();
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                    if (response.body()!=null) {
                        if (response.code() == 200)
                        {
                            progressDialog.dismiss();
                            AppResponse appResponse = response.body();
                            statusname.clear();
                            statusid.clear();
                            statusname.add("Select Feedback");
                            statusid.add("0");

                            for (int i = 0; i < appResponse.getResponseModel().getFeedbackList().size(); i++)
                            {
                                statusname.add(appResponse.getResponseModel().getFeedbackList().get(i).getName());
                                statusid.add(appResponse.getResponseModel().getFeedbackList().get(i).getId());
                            }
                            CustomerFeedBack.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    status();
                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(CustomerFeedBack.this,"Error:"+response.code(),Toast.LENGTH_SHORT).show();
                        }
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void post_feedback() {
        if (!Connectivity.isNetworkConnected(CustomerFeedBack.this)) {
            Toast.makeText(CustomerFeedBack.this, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
            return;
        } else {
            progressDialog.show();
            System.out.println("Rating"+String.valueOf(rating.getRating()));
            PojoFeedbackList post1 = new PojoFeedbackList(SPHelper.service_id,selectedstatusid,customer_comments.getText().toString().trim(),String.valueOf(rating.getRating()));
            Call<AppResponse> call = apiInterface.update_feedback(post1);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                    System.out.print(response.body());
                    if (response.body() != null) {
                        AppResponse data = response.body();
                        if (data.getResponseType().equalsIgnoreCase("200")) {
                            Toast.makeText(CustomerFeedBack.this, "Thank you for your valuable feedback!", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(CustomerFeedBack.this, ServiceCompletedPage.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(CustomerFeedBack.this, data.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<AppResponse> call, Throwable th) {
                    Toast.makeText(CustomerFeedBack.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }
    }
    public void status() {
        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, statusname);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        feedback_menu.setAdapter(dataAdapter3);
    }
}