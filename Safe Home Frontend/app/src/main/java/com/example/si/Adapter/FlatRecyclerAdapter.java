package com.example.si.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.si.FlatActivity;
import com.example.si.R;
import com.example.si.SecretaryActivity;
import com.example.si.UserFlatActivity;
import com.example.si.model.Blocks;
import com.example.si.model.Flat;

import java.util.ArrayList;

public class FlatRecyclerAdapter extends RecyclerView.Adapter<FlatRecyclerAdapter.ViewHolder> {

    ArrayList<Flat> flatArrayList;
    Context context;
    AlertDialog.Builder alertDialog;
    ArrayList<Flat> filteredList;

    public FlatRecyclerAdapter(Context context, ArrayList<Flat> flatArrayList) {
        this.context = context;
        this.flatArrayList = flatArrayList;
        this.filteredList = new ArrayList<>();
        filteredList.addAll(flatArrayList);
    }

    @NonNull
    @Override
    public FlatRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.view_flat, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FlatRecyclerAdapter.ViewHolder holder, final int position) {
        holder.textView.setText(flatArrayList.get(position).getflatNumber());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UserFlatActivity.class);
                intent.putExtra("flatNumber", flatArrayList.get(position).getflatNumber());
                intent.putExtra("blockName", flatArrayList.get(position).getblockName());
                intent.putExtra("secretaryPhoneNumber", flatArrayList.get(position).getSecretaryPhoneNumber());
                context.startActivity(intent);

            }
        });

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, view);
                popupMenu.inflate(R.menu.block_update_menu);
                final Flat flat = flatArrayList.get(position);
                final int p = position;
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.item_update_block_menu:
                                ((FlatActivity) context).updateFlat(position);
                                break;
                            case R.id.item_delete_block_menu:
                                ((FlatActivity) context).deleteFlat(flatArrayList.get(position));
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return flatArrayList.size();
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

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.txt_view_flat_number);
            cardView = itemView.findViewById(R.id.crdview_flat);
        }
    }
}
