package com.wisedrive.customerapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.commonclasses.Common;
import com.wisedrive.customerapp.pojos.PojoMyPayments;
import com.wisedrive.customerapp.pojos.PojoPaidAddonList;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdapterMyPayments extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private DecimalFormat IndianCurrencyFormat;
    public ProgressBar progressBar;
    private boolean isLoadingAdded = false;
    private static final int LOADING = 0;
    private static final int ITEM = 1;
    Context context;
    private View view;
    ArrayList<PojoMyPayments> pojoMyPayments;
    ArrayList<PojoPaidAddonList> pojoServiceListArrayList;
    AdapterPaidAddonList adapterServiceList;

    public AdapterMyPayments(Context context) {
        this.context = context;
        this.view = view;
        pojoMyPayments =new ArrayList<>() ;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                View viewItem = inflater.inflate(R.layout.items_mypayments, parent, false);
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
        switch (getItemViewType(position)) {
            case ITEM:
                final MyViewHolder holder = (MyViewHolder) holder1;
                IndianCurrencyFormat = new DecimalFormat("##,##,###");
                PojoMyPayments list = pojoMyPayments.get(position);
                holder.tv_warranty_name.setText(list.getPackage_name());
                holder.tv_make.setText(list.getVehicle_make()+"-"+list.getVehicle_model()+"-"+list.getVehicle_no());
                holder.tv_amount.setText(IndianCurrencyFormat.format((int)(list.getAmount())));
                holder.tv_date.setText(Common.getDateFromString(list.getPackage_purchased_on()));

                pojoServiceListArrayList = new ArrayList();
                pojoServiceListArrayList=list.getAddonHistory();
                if(pojoServiceListArrayList.isEmpty()){
                    holder.tv_addons.setVisibility(View.GONE);
                    holder.rv_service_name.setVisibility(View.GONE);
                }else{
                    holder.rv_service_name.setVisibility(View.VISIBLE);
                    holder.tv_addons.setVisibility(View.VISIBLE);
                    adapterServiceList = new AdapterPaidAddonList(context, pojoServiceListArrayList);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                    holder. rv_service_name.setLayoutManager(linearLayoutManager);
                    holder.rv_service_name.setAdapter(adapterServiceList);
                }

                break;
            case LOADING:
                LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder1;
                progressBar.setVisibility(View.VISIBLE);
                break;
        }
    }


    @Override
    public int getItemCount() {
        return pojoMyPayments == null ? 0 : pojoMyPayments.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == pojoMyPayments.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public void addLoadingFooter() throws JSONException {
        isLoadingAdded = true;
        add(new PojoMyPayments());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = pojoMyPayments.size() - 1;
        PojoMyPayments result = getItem(position);

        if (result != null) {
            pojoMyPayments.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void add(PojoMyPayments movie) {
        pojoMyPayments.add(movie);
        notifyItemInserted(pojoMyPayments.size() - 1);
    }

    public void addAll(ArrayList<PojoMyPayments> moveResults) {
        for (PojoMyPayments result : moveResults) {
            add(result);
        }
    }
    public PojoMyPayments getItem(int position) {
        return pojoMyPayments.get(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_warranty_name,tv_make,tv_amount,tv_date,tv_addons ;
        RecyclerView rv_service_name;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_warranty_name = itemView.findViewById(R.id.tv_warranty_name);
            tv_make = itemView.findViewById(R.id.tv_make);
            tv_amount = itemView.findViewById(R.id.tv_amount);
            tv_date= itemView.findViewById(R.id.tv_date);
            rv_service_name=itemView.findViewById(R.id.rv_service_name);
            tv_addons=itemView.findViewById(R.id.tv_addons);
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder
    {


        public LoadingViewHolder(View view) {
            super(view);
            progressBar =  view.findViewById(R.id.itemProgressbar);
        }
    }


}
