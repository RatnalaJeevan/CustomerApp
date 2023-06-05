package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.wisedrive.customerapp.adapters.AdapterAllClaimsList;
import com.wisedrive.customerapp.adapters.AdapterClaimQues;
import com.wisedrive.customerapp.adapters.AdapterMyPayments;
import com.wisedrive.customerapp.adapters.Adapter_Q_And_A;
import com.wisedrive.customerapp.commonclasses.AppResponse;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.PojoAnswerDetails;
import com.wisedrive.customerapp.pojos.Pojo_Q_And_A;
import com.wisedrive.customerapp.services.ApiClient;
import com.wisedrive.customerapp.services.ApiInterface;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_Q_And_A extends AppCompatActivity {
    RecyclerView rv_q_and_a;
    Adapter_Q_And_A adapter_q_and_a;
    AdapterClaimQues adapterClaimQues;
    ArrayList<Pojo_Q_And_A> pojo_q_and_aArrayList;
    AppCompatButton submit;
    RelativeLayout rl_back;
    private ApiInterface apiInterface;
    public ArrayList<PojoAnswerDetails> answerDetails=new ArrayList<>();
    public  int currentPage=1,TOTAL_PAGES=30;
    boolean isLoading,isLastPage;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qand);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        rv_q_and_a=findViewById(R.id.rv_q_and_a);
        rl_back=findViewById(R.id.rl_back);
        submit=findViewById(R.id.submit);
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getanswered_list();
            }
        });

        get_symptom_list();

    }



    public  void getanswered_list()
    {
        answerDetails= new ArrayList<>();
        SPHelper.answer_details=new ArrayList<>();
        for (int i = 0; i <SPHelper.qa_list.size(); i++)
        {
            for(int j=0;j<SPHelper.qa_list.get(i).getAnswerlist().size();j++)
            {
                if(SPHelper.qa_list.get(i).getAnswerlist().get(j).getIsSelected().equalsIgnoreCase("y")){
                    PojoAnswerDetails obj=new PojoAnswerDetails();
                    obj.setSymptom_question_id(SPHelper.qa_list.get(i).getSymptom_question_id());
                    obj.setAnswer_id(SPHelper.qa_list.get(i).getAnswerlist().get(j).getAnswer_id());
                    answerDetails.add(obj);
                }
            }
        }

        SPHelper.answer_details=answerDetails;
        System.out.println("size1"+answerDetails.size());
        System.out.println("size2"+SPHelper.qa_list.size());
        System.out.println("size3"+SPHelper.answerlist.size());
        System.out.println("size3"+SPHelper.answer_details.size());

        if(answerDetails.size()==SPHelper.qa_list.size()){
            finish();
            Toast.makeText(Activity_Q_And_A.this, "Symptoms added", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(Activity_Q_And_A.this, "Please answer all the questions ", Toast.LENGTH_SHORT).show();
        }

    }



    public  void get_symptom_list(){
        if(!Connectivity.isNetworkConnected(Activity_Q_And_A.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }else
        {
           // progress.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.getNewSymptoms(SPHelper.claim_type_id);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response)
                {
                    AppResponse appResponse = response.body();

                    if (response.body()!=null) {
                        assert appResponse != null;
                        if (appResponse.getResponseType().equals("200"))
                        {
                            pojo_q_and_aArrayList = new ArrayList<>();
                            pojo_q_and_aArrayList=appResponse.getResponseModel().getQuestionList();
                            if(SPHelper.qa_list.isEmpty()){
                                SPHelper.qa_list=pojo_q_and_aArrayList;
                            }

                                adapterClaimQues=new AdapterClaimQues(Activity_Q_And_A.this,SPHelper.qa_list);
                                LinearLayoutManager layoutManager = new LinearLayoutManager(Activity_Q_And_A.this, LinearLayoutManager.VERTICAL, false);
                                rv_q_and_a.setLayoutManager(layoutManager);
                                rv_q_and_a.setAdapter(adapterClaimQues);
                                Activity_Q_And_A.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapterClaimQues.notifyDataSetChanged();
                                    }
                                });
                        } else if (appResponse.getResponseType().equals("300")) {
                           // progress.setVisibility(View.GONE);
                            Toast.makeText(Activity_Q_And_A.this, "internal server error" + "response code:" + appResponse.getResponseType(), Toast.LENGTH_SHORT).show();

                        }
                    }
                    else {
                      //  progress.setVisibility(View.GONE);
                        Toast.makeText(Activity_Q_And_A.this, "internal server error" , Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AppResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                   // progress.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

    public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener
    {

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
}