package com.example.si.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.si.R;
import com.example.si.WatchmanInEntry;
import com.example.si.model.LastActivity;

import java.util.ArrayList;

public class LastActivityAdapter extends BaseAdapter {

    Context context;
    ArrayList<LastActivity> lastActivities;

    public LastActivityAdapter(Context context, ArrayList<LastActivity> lastActivities) {
        this.context = context;
        this.lastActivities = lastActivities;
    }

    @Override
    public int getCount() {
        return lastActivities.size();
    }

    @Override
    public Object getItem(int position) {
        return lastActivities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(R.layout.view_last_activity, null);
        //String name,status,intime,outtime,type;
        TextView name = convertView.findViewById(R.id.txt_householdname_factivity);
        TextView status = convertView.findViewById(R.id.txt_inoutstatus_factivity);
        TextView inouttime = convertView.findViewById(R.id.txt_inouttime_factivity);
        TextView category = convertView.findViewById(R.id.txt_householdtype_factivity);
        final TextView makeoutentry = convertView.findViewById(R.id.txt_makeoutentry_watchmaninentry);

        makeoutentry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((WatchmanInEntry) context).checkout(lastActivities.get(position).getPhoneNumber());
            }
        });

        LastActivity lastActivity = lastActivities.get(position);
        SharedPreferences sharedPreferences = context.getSharedPreferences("SI", Context.MODE_PRIVATE);

        String societyName = sharedPreferences.getString("societyName", "");
        String type = sharedPreferences.getString("type", "");
        Log.e("Last adaptor type", type);

        String s = "";
        String t = "";
        name.setText(lastActivity.getName());
        if(lastActivity.getOuttime().equals("null") || lastActivity.getOuttime().equals("00 00 0000 00:00:00" ) )
        {
            s="Inside";
            t = lastActivity.getIntime()+lastActivity.getIntime();
            Log.e("intime",lastActivity.getIntime());
            status.setBackgroundResource(R.drawable.active_bg);
//            if (type.equalsIgnoreCase("user")) ;
//            {
//                makeoutentry.setVisibility(View.GONE);
//            }
            //status.setBackground(R.drawable.active_bg);
        } else {
            s = "Left";
            t = lastActivity.getOuttime();
            status.setBackgroundResource(R.drawable.inactive_bg);
            if (societyName.equals("") || s.equals("Left") || type.equalsIgnoreCase("watchman")) {
                makeoutentry.setVisibility(View.GONE);
            }
        }
        if (societyName.equals("") || s.equals("Left") || type.equalsIgnoreCase("user")) {
            makeoutentry.setVisibility(View.GONE);
        }
        status.setText(s);
        inouttime.setText(t);
        category.setText(lastActivity.getType());
        return convertView;
    }
}