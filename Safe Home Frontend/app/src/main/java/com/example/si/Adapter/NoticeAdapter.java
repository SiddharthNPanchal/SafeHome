package com.example.si.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.si.R;
import com.example.si.model.Notice;

import java.util.ArrayList;

public class NoticeAdapter extends BaseAdapter {
    Context context;
    ArrayList<Notice> NoticeArrayList;

    public NoticeAdapter(Context context, ArrayList<Notice> noticeArrayList) {
        this.context = context;
        this.NoticeArrayList = noticeArrayList;
    }

    public NoticeAdapter() {
    }

    @Override
    public int getCount() {
       return NoticeArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return NoticeArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
//        if (convertView == null)
            convertView = inflater.inflate(R.layout.view_notice, parent, false);
        TextView textView = convertView.findViewById(R.id.txt_msg_user_noticeview);
        TextView textView1 = convertView.findViewById(R.id.txt_date_user_noticeview);
        TextView textView2 = convertView.findViewById(R.id.txt_title_user_noticeview);
        textView.setText(NoticeArrayList.get(position).getMessage());
        textView1.setText(NoticeArrayList.get(position).getDate());
        textView2.setText(NoticeArrayList.get(position).getTitle());
        return convertView;
    }
}
