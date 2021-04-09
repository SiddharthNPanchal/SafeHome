package com.example.si.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.si.EverdayActivity;
import com.example.si.R;
import com.example.si.model.Everyday;

import java.util.ArrayList;

public class EverydayAdapter extends BaseAdapter {
    Context context;
    ArrayList<Everyday> everydayArrayList;

    public EverydayAdapter(Context context, ArrayList<Everyday> everydayArrayList) {
        this.context = context;
        this.everydayArrayList = everydayArrayList;
    }


    @Override
    public int getCount() {
        return everydayArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return everydayArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.view_everyday_details, parent, false);
        TextView textView = convertView.findViewById(R.id.txt_householdname_factivity);
        TextView textView1 = convertView.findViewById(R.id.txt_inoutstatus_factivity);
        textView.setText(everydayArrayList.get(position).getName());
        textView1.setText(everydayArrayList.get(position).getEverydayPhoneNumber());

        final TextView makeinentry=convertView.findViewById(R.id.txt_makeinentry_watchmaninentry);
        SharedPreferences sharedPreferences = context.getSharedPreferences("SI",Context.MODE_PRIVATE);

        String type = sharedPreferences.getString("type", "");
        Log.e("everydayadapter",type);
        if(!type.equalsIgnoreCase("watchman"))
        {
            makeinentry.setVisibility(View.GONE);
        }

        makeinentry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EverdayActivity)context).checkin(everydayArrayList.get(position).getEverydayPhoneNumber());
            }
        });

        return convertView;
    }
}
