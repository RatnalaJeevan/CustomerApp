package com.wisedrive.customerapp.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.pojos.PojoComboProducts;


import java.util.ArrayList;

public class AdaperComboProducts extends RecyclerView.Adapter<AdaperComboProducts.RecyclerViewHolder> {

    ArrayList<PojoComboProducts> comboProducts;
    Context context;

    public AdaperComboProducts(ArrayList<PojoComboProducts> comboProducts, Context context) {
        this.comboProducts = comboProducts;
        this.context = context;
    }

    @NonNull
    @Override
    public AdaperComboProducts.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_combo_products,  parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        PojoComboProducts recyclerdata=comboProducts.get(position);

        Typeface font1 = Typeface.createFromAsset(context.getAssets(), "fonts/medium.ttf");
        Typeface font2 = Typeface.createFromAsset(context.getAssets(), "fonts/regular.ttf");

        SpannableString spannableString = new SpannableString(recyclerdata.getValidity()+" "+recyclerdata.getProduct_name());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            spannableString.setSpan(new TypefaceSpan(font1), 0, recyclerdata.getValidity().length()+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            spannableString.setSpan(new TypefaceSpan(font2), recyclerdata.getValidity().length()+1, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        holder.tv_comprehensive_warranty_description.setText(spannableString);

    }

    @Override
    public int getItemCount() {
        return comboProducts.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView tv_comprehensive_warranty,tv_comprehensive_warranty_description,validity;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_comprehensive_warranty_description=itemView.findViewById(R.id.tv_comprehensive_warranty_description);
            tv_comprehensive_warranty=itemView.findViewById(R.id.tv_comprehensive_warranty);
            validity=itemView.findViewById(R.id.validity);
        }
    }
}
