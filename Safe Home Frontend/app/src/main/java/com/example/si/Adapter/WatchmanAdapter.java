package com.example.si.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.si.R;
import com.example.si.model.watchman;

import java.util.ArrayList;

public class WatchmanAdapter extends BaseAdapter {
    ArrayList<watchman> watchmanArrayList;
    Context context;

    public WatchmanAdapter(Context context, ArrayList<watchman> watchmanArrayList) {
        this.context = context;
        this.watchmanArrayList = watchmanArrayList;
    }

//    public WatchmanAdapter(ArrayList<watchman> watchmanArrayList, Context context) {
//        this.watchmanArrayList = watchmanArrayList;
//        this.context = context;
//    }

    @Override
    public int getCount() {
        return watchmanArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return watchmanArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.view_watchman, parent, false);
        TextView watchmanName = convertView.findViewById(R.id.txt_view_everyday_name_user);
        TextView watchmanPhoneNumber = convertView.findViewById(R.id.txt_view_everyday_phonenumber_user);
        watchmanPhoneNumber.setText(watchmanArrayList.get(position).getUserPhoneNumber());
        watchmanName.setText(watchmanArrayList.get(position).getUserName());
        return convertView;
    }
}
