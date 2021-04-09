package com.example.si.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.si.R;
import com.example.si.model.UserFlat;

import java.util.ArrayList;

public class UserBlockAdapter extends BaseAdapter {

    ArrayList<UserFlat> userFlatArrayList;
    Context context;
    ArrayList<UserFlat> filteredList;


    public UserBlockAdapter(Context context, ArrayList<UserFlat> userFlatArrayList) {
        this.context = context;
        this.userFlatArrayList = userFlatArrayList;
        this.filteredList = new ArrayList<>();
        this.filteredList.addAll(userFlatArrayList);
    }

    @Override
    public int getCount() {
        return userFlatArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return userFlatArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.view_userblock, parent, false);
        }
        TextView txtUserName = convertView.findViewById(R.id.txt_user_name_userblock);
        TextView txtUserPhoneNumber = convertView.findViewById(R.id.txt_user_phone_number_userblock);
        txtUserPhoneNumber.setText(userFlatArrayList.get(position).getUserPhoneNumber());
        txtUserName.setText(userFlatArrayList.get(position).getName());
        return convertView;
    }

    public void filter(String text) {
        userFlatArrayList.clear();
        if (text.trim().length() == 0) {
            userFlatArrayList.addAll(filteredList);
        } else {
            for (int i = 0; i < filteredList.size(); i++) {
                if (filteredList.get(i).getName().toLowerCase().contains(text) || filteredList.get(i).getUserPhoneNumber().contains(text)) {
                    userFlatArrayList.add(filteredList.get(i));
                }
            }
        }
        notifyDataSetChanged();
    }
}
