package com.example.si;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.si.Adapter.UserBlockEverydayAdapter;
import com.example.si.Constants.Constant;
import com.example.si.model.UserBlockEveryDay;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserBlockEveryDayActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<UserBlockEveryDay> userBlockEveryDays;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String blockNumber,flatName,secretaryphonenumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_block_every_day);
        setTitle("My Household List");


        SharedPreferences sharedPreferences = getSharedPreferences("USERDATA",MODE_PRIVATE);
        blockNumber=sharedPreferences.getString("blockNumber", "");
        flatName=sharedPreferences.getString("flatName","");
        secretaryphonenumber=sharedPreferences.getString("secretaryPhoneNumber","");


        listView=findViewById(R.id.lv_userblockeveryday_user);
        userBlockEveryDays=new ArrayList<>();
        loadData();

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                PopupMenu popupMenu = new PopupMenu(UserBlockEveryDayActivity.this, view);
                popupMenu.inflate(R.menu.userblockeveryday_delete);
                final UserBlockEveryDay userBlockEveryDay = (UserBlockEveryDay) parent.getItemAtPosition(position);
                final int p = position;
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.userblockeveryday_delete:
                                AlertDialog.Builder builder = new AlertDialog.Builder(UserBlockEveryDayActivity.this);
                                builder.setMessage("Are you sure you want to Delete these member??");
                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        deleteUser(userBlockEveryDay);
                                    }
                                });
                                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                builder.show();

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

    private void deleteUser(final UserBlockEveryDay userBlockEveryDay) {
        String url = Constant.URL + "getuserblockeveryday/"+blockNumber+"/"+flatName+"/"+secretaryphonenumber+"/"+userBlockEveryDay.getEverydayPhoneNumber();
        RequestQueue requestQueue = Volley.newRequestQueue(UserBlockEveryDayActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                userBlockEveryDays.clear();
                loadData();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);




    }


    private void loadData() {





        RequestQueue requestQueue = Volley.newRequestQueue(UserBlockEveryDayActivity.this);
        String url = Constant.URL + "getuserblockeveryday/"+blockNumber+"/"+flatName+"/"+secretaryphonenumber;

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("res",response);
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        UserBlockEveryDay userBlockEveryDay=new UserBlockEveryDay();

                        userBlockEveryDay.setBlockNumber(jsonObject.getString("blockNumber"));
                        userBlockEveryDay.setFlatName(jsonObject.getString("flatName"));
                        userBlockEveryDay.setSecretaryPhoneNumber(jsonObject.getString("secretaryPhoneNumber"));
                        userBlockEveryDay.setEverydayPhoneNumber(jsonObject.getString("everydayPhoneNumber"));
                        userBlockEveryDay.setName(jsonObject.getString("name"));
                        userBlockEveryDay.setCategoryId(jsonObject.getString("categoryId"));
                        userBlockEveryDay.setCategoryName(jsonObject.getString("categoryName"));
                        userBlockEveryDays.add(userBlockEveryDay);
                    }
                    UserBlockEverydayAdapter userBlockEverydayAdapter=new UserBlockEverydayAdapter(UserBlockEveryDayActivity.this,userBlockEveryDays);
                    listView.setAdapter(userBlockEverydayAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Cricket","VK"+error.toString());
            }
        });

        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_add_everyday_menu:
                Intent intent = new Intent(UserBlockEveryDayActivity.this, CategoryViewUser.class);
                startActivity(intent);
                finish();
                break;

            case R.id.item_logout_menu:
                Constant.logoutuser(UserBlockEveryDayActivity.this);
                break;
        }
        return true;
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.userblockeveryday_add, menu);
        return true;

    }
}