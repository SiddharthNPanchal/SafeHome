package com.example.si;

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

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.si.Adapter.WatchmanAdapter;
import com.example.si.Constants.Constant;
import com.example.si.model.watchman;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WatchmanViewActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<watchman> watchmanArrayList;
    SharedPreferences sharedPreferences,sharedPreferences1;
    SharedPreferences.Editor editor;
    String userPhoneNumber,type;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_watchman);
        setTitle("View Watchman");
        listView=findViewById(R.id.rv_watchman_view);
        watchmanArrayList=new ArrayList<>();
        sharedPreferences1 = getSharedPreferences("SI", MODE_PRIVATE);
        type = sharedPreferences1.getString("type", "");

        loadData();

        if (type.equals("user")) {

        } else if (type.equals("secretary")) {
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    PopupMenu popupMenu = new PopupMenu(WatchmanViewActivity.this, view);
                    popupMenu.inflate(R.menu.delete_watchman);
                    final watchman watchman = (watchman) parent.getItemAtPosition(position);
                    final int p = position;
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            switch (item.getItemId()) {
                                case R.id.delete_watchman:
                                    AlertDialog.Builder builder = new AlertDialog.Builder(WatchmanViewActivity.this);
                                    builder.setMessage("Are you sure you want to Delete these member??");
                                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            deleteWatchman(watchman);
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
    }

    private void deleteWatchman(watchman watchman) {
        String userPhoneNumber;
        userPhoneNumber = watchman.getUserPhoneNumber();
        String url = Constant.URL + "societyWatchman/" + userPhoneNumber;
        RequestQueue requestQueue = Volley.newRequestQueue(WatchmanViewActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                watchmanArrayList.clear();
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

        Log.e("cricket","MS");


        sharedPreferences = getSharedPreferences("SI", MODE_PRIVATE);
        userPhoneNumber = sharedPreferences.getString("userPhoneNumber", "");

        RequestQueue requestQueue = Volley.newRequestQueue(WatchmanViewActivity.this);
        String url = Constant.URL + "societyWatchman/"+userPhoneNumber;

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("res",response);
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        watchman watchman=new watchman();
                        watchman.setUserName(jsonObject.getString("userName"));
                        watchman.setUserPhoneNumber(jsonObject.getString("userPhoneNumber"));
                        watchmanArrayList.add(watchman);
                        Log.e("WATCH",watchman.getUserName());
                        Log.e("WATCH",watchman.getUserPhoneNumber());
                    }
                    Log.e("WATCH","SIZE " + watchmanArrayList.size());
                    WatchmanAdapter watchmanAdapter=new WatchmanAdapter(WatchmanViewActivity.this,watchmanArrayList);
                    listView.setAdapter(watchmanAdapter);

                } catch (JSONException e) {
                    Log.e("WATCH",e.getMessage());
                    //e.printStackTrace();
                }

            }
        },new Response.ErrorListener() {
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

            case R.id.item_watchman_add:
                Intent intent1=new Intent(this,AddWatchman.class);
                startActivity(intent1);
                break;

            case R.id.item_logout_menu:
                Constant.logout(WatchmanViewActivity.this);
                break;
        }
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        sharedPreferences = getSharedPreferences("SI", MODE_PRIVATE);
        type=sharedPreferences.getString("type","");
        if(type.equals("secretary"))
        {
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.menu_watchman, menu);

        }

        return true;

    }

}