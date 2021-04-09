package com.example.si.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.si.R;

public class EverydayViewAdapter extends RecyclerView.Adapter<EverydayViewAdapter.ViewHolder> {
    private static final String TAG="Everyday_View_Adapter";
    int count=0;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Log.i(TAG,"hellohelly"+count++);

        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.everyday_view,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.everyday_name.setText(String.valueOf(position));

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView everyday_img;
        TextView everyday_name,everyday_phone;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            everyday_img=itemView.findViewById(R.id.iv_everydayview);
            everyday_name=itemView.findViewById(R.id.txt_view_everyday_name_user);
            everyday_phone=itemView.findViewById(R.id.txt_view_everyday_phonenumber_user);
        }
    }
}
