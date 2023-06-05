package com.wisedrive.customerapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.PopupPackDetails;
import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.commonclasses.Common;
import com.wisedrive.customerapp.commonclasses.SPHelper;
import com.wisedrive.customerapp.pojos.PojoMyPayments;
import com.wisedrive.customerapp.pojos.Pojo_Q_And_A;
import com.wisedrive.customerapp.pojos.Pojo_yes_no;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Adapter_Q_And_A   extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public Context context;

    ArrayList<Pojo_Q_And_A> pojo_q_and_aArrayList;
    RecyclerView rv_yes_no;
    public ProgressBar progressBar;
    private boolean isLoadingAdded = false;
    private static final int LOADING = 0;
    private static final int ITEM = 1;
    private View view;
    Adapter_Yes_No1 adapterAns;

    public Adapter_Q_And_A(Context context) {
        this.context = context;
        this.view = view;
        pojo_q_and_aArrayList =new ArrayList<>() ;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                View viewItem = inflater.inflate(R.layout.items_q_and_a, parent, false);
                viewHolder = new MyViewHolder(viewItem);
                break;
            case LOADING:
                View viewLoading = inflater.inflate(R.layout.item_loading, parent, false);
                viewHolder = new LoadingViewHolder(viewLoading);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int position) {

        switch (getItemViewType(position))
        {
            case ITEM:
                final MyViewHolder holder = (MyViewHolder) holder1;
                    Pojo_Q_And_A list = pojo_q_and_aArrayList.get(position);
                    holder.tv_question.setText(list.getSymptom_name());


//                    adapter_yes_no= new Adapter_Yes_No(context,  answerList,pojo_q_and_aArrayList);
//                    rv_yes_no.setAdapter(adapter_yes_no);

                   adapterAns= new Adapter_Yes_No1( context,list.getAnswerlist());
                   rv_yes_no.setAdapter(adapterAns);

                break;
            case LOADING:
                LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder1;
                progressBar.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return pojo_q_and_aArrayList == null ? 0 : pojo_q_and_aArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == pojo_q_and_aArrayList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public void addLoadingFooter() throws JSONException {
        isLoadingAdded = true;
        add(new Pojo_Q_And_A());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = pojo_q_and_aArrayList.size() - 1;
        Pojo_Q_And_A result = getItem(position);

        if (result != null) {
            pojo_q_and_aArrayList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void add(Pojo_Q_And_A movie) {
        pojo_q_and_aArrayList.add(movie);
        notifyItemInserted(pojo_q_and_aArrayList.size() - 1);
    }

    public void addAll(ArrayList<Pojo_Q_And_A> moveResults) {
        for (Pojo_Q_And_A result : moveResults) {
            add(result);
        }
    }
    public Pojo_Q_And_A getItem(int position) {
        return pojo_q_and_aArrayList.get(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_question,tv_select_option;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_question = itemView.findViewById(R.id.tv_question);
            tv_select_option =  itemView.findViewById(R.id.tv_select_option);
            rv_yes_no=itemView.findViewById(R.id.rv_yes_no);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            rv_yes_no.setLayoutManager(linearLayoutManager);
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder
    {

        public LoadingViewHolder(View view) {
            super(view);
            progressBar =  view.findViewById(R.id.itemProgressbar);
        }
    }

    public class Adapter_Yes_No1 extends RecyclerView.Adapter<Adapter_Yes_No1.RecyclerViewHolder>
    {
        Context context;
        ArrayList<Pojo_yes_no> pojo_yes_noArrayList;

        public Adapter_Yes_No1 (Context context, ArrayList<Pojo_yes_no> pojo_yes_noArrayList) {
            this.context = context;
            this.pojo_yes_noArrayList = pojo_yes_noArrayList;
        }

        @NonNull
        @Override
        public Adapter_Yes_No1.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_yes_no,parent,false );
            return new RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull Adapter_Yes_No1.RecyclerViewHolder holder,
                                     int position) {
            Pojo_yes_no list = pojo_yes_noArrayList.get(position);

            holder.tv_yes.setText(list.getAnswer());
            if (list.getIsSelected().equals("y")) {
                holder.dot_img.setVisibility(View.VISIBLE);
            }
            else {
                holder.dot_img.setVisibility(View.GONE);
            }

            holder.rl_yes.setOnClickListener(view ->
            {
                for (int i = 0; i < pojo_yes_noArrayList.size(); i++)
                {
                    if (i == position)
                    {
                        pojo_yes_noArrayList.get(i).setIsSelected("y");
                    } else {
                        pojo_yes_noArrayList.get(i).setIsSelected("n");
                    }
                }

                notifyDataSetChanged();
            });

        }

        @Override
        public int getItemCount() {
            return pojo_yes_noArrayList.size();
        }

        public  class RecyclerViewHolder extends RecyclerView.ViewHolder
        {
            TextView tv_yes;
            RelativeLayout rl_yes;
            ImageView dot_img;

            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                tv_yes = itemView.findViewById(R.id.tv_yes);
                rl_yes=itemView.findViewById(R.id.rl_yes);
                dot_img=itemView.findViewById(R.id.dot_img);
            }
        }
    }





}
