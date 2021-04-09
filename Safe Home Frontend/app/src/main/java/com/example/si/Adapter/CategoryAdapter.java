package com.example.si.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.si.R;
import com.example.si.model.Category;

import java.util.ArrayList;

public class CategoryAdapter extends BaseAdapter {
    Context context;

    ArrayList<Category> categoryArrayList;

    public CategoryAdapter(Context context, ArrayList<Category> categoryArrayList) {
        this.context = context;
        this.categoryArrayList = categoryArrayList;
    }

    @Override
    public int getCount() {
        return categoryArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
//        if (convertView == null)
        convertView = inflater.inflate(R.layout.view_category, parent, false);
        TextView textView1 = convertView.findViewById(R.id.txt_view_category_user);

        textView1.setText(categoryArrayList.get(position).getCategoryName());
        return convertView;
    }
}
