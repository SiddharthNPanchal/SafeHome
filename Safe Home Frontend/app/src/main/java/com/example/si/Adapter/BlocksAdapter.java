package com.example.si.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.si.FlatActivity;
import com.example.si.R;
import com.example.si.SecretaryActivity;
import com.example.si.model.Blocks;

import java.util.ArrayList;

public class BlocksAdapter extends BaseAdapter{


    Context context;
    ArrayList<Blocks> blocksArrayList;
    ArrayList<Blocks> filteredList;

    public BlocksAdapter(Context context, ArrayList<Blocks> blocksArrayList) {
        this.context = context;
        this.blocksArrayList = blocksArrayList;
        this.filteredList = new ArrayList<>();
        filteredList.addAll(blocksArrayList);
    }

    @Override
    public int getCount() {
        return blocksArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return blocksArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null)
            convertView = layoutInflater.inflate(R.layout.view_block, null);
        TextView textView = convertView.findViewById(R.id.txt_view_block_name);
        textView.setText(blocksArrayList.get(position).getblockName());
        CardView cardView = convertView.findViewById(R.id.crdview_block);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ADAPTER","ADAPTER");
                Intent intent = new Intent(context, FlatActivity.class);
                intent.putExtra("userPhoneNumber",blocksArrayList.get(position).getSecretaryPhoneNumber());
                intent.putExtra("blockName", blocksArrayList.get(position).getblockName());
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    public void filter(String text) {
        text = text.toLowerCase();
        blocksArrayList.clear();
        if (text.trim().length() == 0) {
            blocksArrayList.addAll(filteredList);
        } else {
            for(int i=0;i<filteredList.size();i++)
            {
                if(filteredList.get(i).getblockName().toLowerCase().contains(text))
                {
                    blocksArrayList.add(filteredList.get(i));
                }
            }
        }
        notifyDataSetChanged();
    }



}
