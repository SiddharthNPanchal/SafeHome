package com.example.si.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.si.R;
import com.example.si.UserFlatActivity;
import com.example.si.model.UserFlat;

import java.util.ArrayList;

public class UserFlatAdapter extends RecyclerView.Adapter<UserFlatAdapter.ViewHolder>{

    ArrayList<UserFlat> userFlatArrayList,filteredList;
    Context context;

    public UserFlatAdapter(Context context,ArrayList<UserFlat> userFlatArrayList)
    {
        this.context = context;
        this.userFlatArrayList = userFlatArrayList;
        filteredList = new ArrayList<>();
        filteredList.addAll(userFlatArrayList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.view_user, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.txtuserName.setText(userFlatArrayList.get(position).getName());
        holder.txtUserPhoneNumber.setText(userFlatArrayList.get(position).getUserPhoneNumber());

        SharedPreferences sharedPreferences = context.getSharedPreferences("SI",Context.MODE_PRIVATE);
        String type = sharedPreferences.getString("type","");
        if(type.equalsIgnoreCase("secretary"))
        {
            holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(context, view);
                    popupMenu.inflate(R.menu.userblock_update_menu);
                    final UserFlat userFlat = userFlatArrayList.get(position);
                    final int p = position;
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            switch (item.getItemId()) {
                                case R.id.item_delete_user_menu:
                                    ((UserFlatActivity)context).deleteUser(userFlat);
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
        return userFlatArrayList.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtuserName;
        TextView txtUserPhoneNumber;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtUserPhoneNumber = itemView.findViewById(R.id.txt_view_everyday_phonenumber_user);
            txtuserName = itemView.findViewById(R.id.txt_view_everyday_name_user);
            cardView = itemView.findViewById(R.id.crdview_user);
        }
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
