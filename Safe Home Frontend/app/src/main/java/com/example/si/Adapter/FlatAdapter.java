package com.example.si.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.si.FlatActivity;
import com.example.si.R;
import com.example.si.UserFlatActivity;
import com.example.si.model.Blocks;
import com.example.si.model.Flat;

import java.util.ArrayList;

public class FlatAdapter extends BaseAdapter {

    Context context;
    ArrayList<Flat> flatArrayList;
    ArrayList<Flat> filteredList;

    public FlatAdapter(Context context, ArrayList<Flat> flatArrayList) {
        this.flatArrayList = flatArrayList;
        this.context = context;
        this.filteredList = new ArrayList<>();
        filteredList.addAll(flatArrayList);
    }

    @Override
    public int getCount() {
        return flatArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return flatArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.view_flat, parent, false);
        }
        TextView floorNumber = convertView.findViewById(R.id.txt_view_flat_number);
        floorNumber.setText(flatArrayList.get(position).getflatNumber());
        CardView cardView = convertView.findViewById(R.id.crdview_flat);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UserFlatActivity.class);
                Flat flat =  flatArrayList.get(position);
                intent.putExtra("flatNumber",flat.getflatNumber());
                intent.putExtra("blockName",flat.getblockName());
                intent.putExtra("secretaryPhoneNumber",flat.getSecretaryPhoneNumber());
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    public void filter(String text) {
        text = text.toLowerCase();
        flatArrayList.clear();
        if (text.trim().length() == 0) {
            flatArrayList.addAll(filteredList);
        } else {
            for (int i = 0; i < filteredList.size(); i++) {
                if (filteredList.get(i).getflatNumber().contains(text)) {
                    flatArrayList.add(filteredList.get(i));
                }
            }
        }
        notifyDataSetChanged();
    }
}
