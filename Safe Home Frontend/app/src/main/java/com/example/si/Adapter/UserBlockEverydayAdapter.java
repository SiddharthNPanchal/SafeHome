package com.example.si.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.si.R;
import com.example.si.model.UserBlockEveryDay;

import java.util.ArrayList;

public class UserBlockEverydayAdapter extends BaseAdapter {
    Context context;
    ArrayList<UserBlockEveryDay> userBlockEveryDays;

    public UserBlockEverydayAdapter(Context context, ArrayList<UserBlockEveryDay> userBlockEveryDays) {
        this.context = context;
        this.userBlockEveryDays = userBlockEveryDays;
    }

    @Override
    public int getCount() {
        return userBlockEveryDays.size();
    }

    @Override
    public Object getItem(int position) {
        return userBlockEveryDays.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (convertView == null)
        convertView = inflater.inflate(R.layout.view_userblockeveryday, parent, false);
        TextView textView = convertView.findViewById(R.id.txt_householdname_factivity);
        TextView textView1 = convertView.findViewById(R.id.txt_inoutstatus_factivity);
        TextView textView2=convertView.findViewById(R.id.txt_householdtype_factivity);
        textView.setText(userBlockEveryDays.get(position).getName());
        textView1.setText(userBlockEveryDays.get(position).getEverydayPhoneNumber());
        textView2.setText(userBlockEveryDays.get(position).getCategoryName());
        return convertView;
    }
}
