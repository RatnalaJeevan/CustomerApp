package com.wisedrive.customerapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wisedrive.customerapp.R;
import com.wisedrive.customerapp.pojos.Pojo_Q_And_A;
import com.wisedrive.customerapp.pojos.Pojo_yes_no;

import java.util.ArrayList;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter

{

     Context context;
     ArrayList<Pojo_Q_And_A> pojo_q_and_aArrayList;
     ArrayList<Pojo_yes_no> pojo_yes_noArrayList;

    public CustomExpandableListAdapter(Context context, ArrayList<Pojo_Q_And_A> pojo_q_and_aArrayList, ArrayList<Pojo_yes_no> pojo_yes_noArrayList) {
        this.context = context;
        this.pojo_q_and_aArrayList = pojo_q_and_aArrayList;
        this.pojo_yes_noArrayList = pojo_yes_noArrayList;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return pojo_yes_noArrayList.size();
    }


    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }


    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent)
    {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.items_yes_no, null);
        }

        TextView tv_yes;
        ImageView dot_img;
        RelativeLayout rl_yes;
        tv_yes=convertView.findViewById(R.id.tv_yes);
        dot_img=convertView.findViewById(R.id.dot_img);
        rl_yes=convertView.findViewById(R.id.rl_yes);

        Pojo_yes_no childItem = pojo_q_and_aArrayList.get(listPosition).getAnswerlist().get(expandedListPosition);

        tv_yes.setText(childItem.getAnswer());

        if (childItem.getIsSelected().equals("y")) {
            dot_img.setVisibility(View.VISIBLE);
        } else {
            dot_img.setVisibility(View.GONE);
        }

        convertView.setOnClickListener(view -> {
            // Update the isSelected value of the clicked child item
            for (Pojo_yes_no item : pojo_q_and_aArrayList.get(listPosition).getAnswerlist()) {
                item.setIsSelected("n");
            }
            childItem.setIsSelected("y");

            notifyDataSetChanged();
        });



        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return pojo_q_and_aArrayList.get(listPosition).getAnswerlist().size();
    }

    @Override
    public int getGroupCount() {
        return this.pojo_q_and_aArrayList.size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.pojo_q_and_aArrayList.get(listPosition);
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.items_q_and_a, null);
        }



        TextView tv_question;
        tv_question=convertView.findViewById(R.id.tv_question);
        ExpandableListView mExpandableListView = (ExpandableListView) parent;
        mExpandableListView.setGroupIndicator(null);
        Pojo_Q_And_A list = pojo_q_and_aArrayList.get(listPosition);
        tv_question.setText(list.getSymptom_name());
        if (!isExpanded) {
            ExpandableListView expandableListView = (ExpandableListView) parent;
            expandableListView.expandGroup(listPosition);
        }

        convertView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.white));

        return convertView;
    }


    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }





}
