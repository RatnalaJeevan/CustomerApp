package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.wisedrive.customerapp.adapters.AdapterMyPayments;
import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.PojoMyPayments;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPayments extends AppCompatActivity {
    ProgressBar idPBLoading;
    private ApiInterface apiInterface;
    RecyclerView rv_mypayments;
    AdapterMyPayments adapterWarrantyDetails;
    ArrayList<PojoMyPayments> pojoMyPaymentsArrayList;
    public  int currentPage=1,TOTAL_PAGES=30;
    boolean isLoading,isLastPage;
    ImageView back;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_payments);
        idPBLoading=findViewById(R.id.idPBLoading);
        rv_mypayments=findViewById(R.id.rv_mypayments);
        back=findViewById(R.id.back);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        getPaymentHistory();
        adapterWarrantyDetails = new AdapterMyPayments(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyPayments.this, LinearLayoutManager.VERTICAL, false);
        rv_mypayments.setLayoutManager(linearLayoutManager);
        rv_mypayments.setAdapter(adapterWarrantyDetails);
        rv_mypayments.addOnScrollListener(new  PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                if(pojoMyPaymentsArrayList.size()>=30){
                    getPaymentPagination();
                }
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {

        private LinearLayoutManager layoutManager;

        public PaginationScrollListener(LinearLayoutManager layoutManager) {
            this.layoutManager = layoutManager;
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
            if (!isLoading() && !isLastPage())
            {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0) {
                    loadMoreItems();
                }
            }
        }

        protected abstract void loadMoreItems();

        public abstract boolean isLastPage();

        public abstract boolean isLoading();

    }


    public void getPaymentHistory() {
        currentPage=1;
        if(!Connectivity.isNetworkConnected(MyPayments.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Plaese Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        }else
        {
//            if(search_foryourcars.getText().toString().equals("")){
//                load_list.setVisibility(View.GONE);
//                idPBLoading.setVisibility(View.VISIBLE);
//            }else{
//                load_list.setVisibility(View.VISIBLE);
//                idPBLoading.setVisibility(View.GONE);
//            }
            idPBLoading.setVisibility(View.VISIBLE);
            Call<AppResponse> call =  apiInterface.get_payment_history( String.valueOf(currentPage),SPHelper.getSPData(MyPayments.this,SPHelper.customer_id,""),
                    SPHelper.getSPData(MyPayments.this,SPHelper.lead_id,""));
            call.enqueue(new Callback<AppResponse>()
            {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response)
                {
                    AppResponse appResponse = response.body();
                    assert appResponse != null;
                    String response_code = appResponse.getResponseType();
                    if (response.body() != null)
                    {
                        if (response_code.equals("200"))
                        {
                            // load_list.setVisibility(View.GONE);
                            idPBLoading.setVisibility(View.GONE);
                            pojoMyPaymentsArrayList = new ArrayList<>();
                            pojoMyPaymentsArrayList = appResponse.getResponseModel().getPaymentHistory();
                            if(pojoMyPaymentsArrayList.isEmpty()){

                            }
                            else
                            {
                                adapterWarrantyDetails.addAll(pojoMyPaymentsArrayList);
                                if (currentPage <= TOTAL_PAGES)
                                {
                                    try {
                                        adapterWarrantyDetails.addLoadingFooter();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else {
                                    isLastPage = true;
                                }
                                adapterWarrantyDetails.removeLoadingFooter();
                            }
                        } else if (response_code.equals("300")) {
                            //load_list.setVisibility(View.GONE);
                           idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(MyPayments.this, appResponse.getResponseModel().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //load_list.setVisibility(View.GONE);
                        idPBLoading.setVisibility(View.GONE);
                        Toast.makeText(MyPayments.this, "internal server error", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<AppResponse> call, Throwable t) {
                    // load_list.setVisibility(View.GONE);
                    idPBLoading.setVisibility(View.GONE);
                    t.printStackTrace();
                }
            });
        }
    }
    private void getPaymentPagination()
    {

        if(!Connectivity.isNetworkConnected(MyPayments.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Plaese Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        }else
        {
            Call<AppResponse> call =  apiInterface.get_payment_history( String.valueOf(currentPage),SPHelper.getSPData(MyPayments.this,SPHelper.customer_id,""),
                    SPHelper.getSPData(MyPayments.this,SPHelper.lead_id,""));
            call.enqueue(new Callback<AppResponse>()
            {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response)
                {
                    AppResponse appResponse = response.body();
                    assert appResponse != null;
                    String response_code = appResponse.getResponseType();
                    if (response.body() != null) {
                        if (response_code.equals("200")) {
                            pojoMyPaymentsArrayList = new ArrayList<>();
                            pojoMyPaymentsArrayList = appResponse.getResponseModel().getPaymentHistory();
                            if(pojoMyPaymentsArrayList.isEmpty()){
                                adapterWarrantyDetails.removeLoadingFooter();
                                //noresults.setVisibility(View.VISIBLE);
                                //rv_veh_list.setVisibility(View.GONE);
                            }
                            else
                            {
                                //noresults.setVisibility(View.GONE);
                                // rv_veh_list.setVisibility(View.VISIBLE);
                                adapterWarrantyDetails.removeLoadingFooter();
                                isLoading = false;
                                adapterWarrantyDetails.addAll(pojoMyPaymentsArrayList);

                                if (currentPage != TOTAL_PAGES)
                                {
//                                    try {
//                                        adapterallcars.addLoadingFooter();
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
                                } else isLastPage = true;
                            }

                        } else if (response_code.equals("300")) {
                            Toast.makeText(MyPayments.this, appResponse.getResponseModel().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    else {
                        Toast.makeText(MyPayments.this, "internal server error", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<AppResponse> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}