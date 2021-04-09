package com.example.si.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.si.Constants.Constant;
import com.example.si.FlatActivity;
import com.example.si.R;
import com.example.si.SecretaryActivity;
import com.example.si.ViewSocietyUsersActivity;
import com.example.si.model.Blocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BlockRecyclerAdapter extends RecyclerView.Adapter<BlockRecyclerAdapter.ViewHolder> {

    ArrayList<Blocks> blocksArrayList;
    Context context;
    AlertDialog.Builder alertDialog;
    ArrayList<Blocks> filteredList;

    public BlockRecyclerAdapter(Context context, ArrayList<Blocks> blocksArrayList) {
        this.context = context;
        this.blocksArrayList = blocksArrayList;
        this.filteredList = new ArrayList<>();
        filteredList.addAll(blocksArrayList);
    }

    @NonNull
    @Override
    public BlockRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.view_block, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BlockRecyclerAdapter.ViewHolder holder, final int position) {
        holder.textView.setText(blocksArrayList.get(position).getblockName());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Blocks blocks = blocksArrayList.get(position);
                SharedPreferences sharedPreferences = context.getSharedPreferences("SI",Context.MODE_PRIVATE);
                String type=sharedPreferences.getString("type","");
                if(type.toLowerCase().equals("secretary")) {
                    Intent intent = new Intent(context, FlatActivity.class);
                    intent.putExtra("userPhoneNumber", blocks.getSecretaryPhoneNumber());
                    intent.putExtra("blockName", blocks.getblockName());
                    context.startActivity(intent);
                }
                else{
                    Intent intent=new Intent(context, ViewSocietyUsersActivity.class);
                    intent.putExtra("flatname",blocks.getblockName());
                    intent.putExtra("secretaryPhonenNumber",blocks.getSecretaryPhoneNumber());
                    context.startActivity(intent);
                }
            }
        });
        SharedPreferences sharedPreferences = context.getSharedPreferences("SI",Context.MODE_PRIVATE);
        String type = sharedPreferences.getString("type","");
        if(type.equalsIgnoreCase("secretary"))
        {
            holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(context, v);
                    popupMenu.inflate(R.menu.secretary_flat_menu);
                    final Blocks blocks = blocksArrayList.get(position);
                    final int p = position;
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            switch (item.getItemId()) {
                                case R.id.item_update_flat_menu:
                                    ((SecretaryActivity)context).updateBlock(position);
                                    break;
                                case R.id.item_delete_flat_menu:
                                    ((SecretaryActivity)context).deleteBlock(blocks);
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

    }
    @Override
    public int getItemCount() {
        return blocksArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.txt_view_block_name);
            cardView = itemView.findViewById(R.id.crdview_block);
        }
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
