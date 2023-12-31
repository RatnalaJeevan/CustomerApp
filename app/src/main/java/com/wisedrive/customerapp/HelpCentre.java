package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HelpCentre extends AppCompatActivity {
    ImageView back;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    private String email_id="",phone_no="";
    TextView label_call;
    RelativeLayout rl_cc,rl_mail,rl_chat;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_centre);
        getWindow().setStatusBarColor(getColor(R.color.white));
        progressDialog = new ProgressDialog(HelpCentre.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        rl_chat=findViewById(R.id.rl_chat);
        back=findViewById(R.id.back);
        label_call=findViewById(R.id.label_call);
        rl_mail=findViewById(R.id.rl_mail);
        rl_cc=findViewById(R.id.rl_cc);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });
        get_help_support();
        rl_cc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+phone_no));
                startActivity(callIntent);
            }
        });
        rl_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + email_id));
                startActivity(intent);
            }
        });
        float alphaValue = 0.85f; // Set your desired alpha value here (0.0f to 1.0f)
        rl_chat.setAlpha(alphaValue);
    }

    public  void get_help_support() {
        {
            if (!Connectivity.isNetworkConnected(HelpCentre.this)) {
                Toast.makeText(getApplicationContext(),
                        "Plaese Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            } else {
                progressDialog.show();
                Call<AppResponse> call = apiInterface.get_help_support();
                call.enqueue(new Callback<AppResponse>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                        AppResponse appResponse = response.body();
                        assert appResponse != null;
                        String response_code = appResponse.getResponseType();
                        if (response.body() != null) {
                            if (response_code.equals("200"))
                            {
                                progressDialog.dismiss();
                                phone_no=appResponse.getResponseModel().getSupportdetails().getCustomer_support_phone_no();
                                email_id=appResponse.getResponseModel().getSupportdetails().getEmail_id();
                                label_call.setText("Call us at"+" "+phone_no);
                            } else if (response_code.equals("300")) {
                                progressDialog.dismiss();
                            }
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(HelpCentre.this, "internal server error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                        Toast.makeText(getApplicationContext(),
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
            }
        }
    }

    public void onchat(View view) {

        Toast.makeText(getApplicationContext(),
                "Coming soon",
                Toast.LENGTH_SHORT).show();
//        try {
//            String contact = "+917353343536"; // Replace with the phone number or contact you want to redirect to
//            String url = "https://api.whatsapp.com/send?phone=" + contact;
//            Intent i = new Intent(Intent.ACTION_VIEW);
//            i.setData(Uri.parse(url));
//            startActivity(i);
//        } catch (Exception e){
//            // Handle exception if any
//        }
    }
}