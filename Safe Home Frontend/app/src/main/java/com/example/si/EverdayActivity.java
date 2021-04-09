package com.example.si;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.si.Adapter.EverydayAdapter;
import com.example.si.Constants.Constant;
import com.example.si.model.Everyday;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EverdayActivity extends AppCompatActivity {
    String id;
    ListView listView;
    ArrayList<Everyday> everydayArrayList;
    SharedPreferences sharedPreferences, sharedPreferences1;
    SharedPreferences.Editor editor;
    String secretaryPhoneNumber, blockNumber, flatName, everydayphone, type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_everday);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        sharedPreferences1 = getSharedPreferences("SI", MODE_PRIVATE);
        type = sharedPreferences1.getString("type", "");

        listView = findViewById(R.id.lv_everyday_secretary);
        everydayArrayList = new ArrayList<>();
        sharedPreferences = getSharedPreferences("USERDATA", MODE_PRIVATE);
        blockNumber = sharedPreferences.getString("blockNumber", "");
        flatName = sharedPreferences.getString("flatName", "");




        setTitle("Houesehold");
        if (type.equalsIgnoreCase("user")) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(EverdayActivity.this);
                    builder.setMessage("Are you sure you want to Add these member??");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            everydayphone = everydayArrayList.get(position).getEverydayPhoneNumber();
                            addeveryday(everydayphone);


                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();

                }
            });
        }

        if (type.equals("user")) {
            secretaryPhoneNumber = sharedPreferences1.getString("userPhoneNumber", "");

        } else if (type.equals("secretary")) {
            secretaryPhoneNumber = sharedPreferences1.getString("userPhoneNumber", "");
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    PopupMenu popupMenu = new PopupMenu(EverdayActivity.this, view);
                    popupMenu.inflate(R.menu.userblockeveryday_delete);
                    final Everyday everyday = (Everyday) parent.getItemAtPosition(position);
                    final int p = position;
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            switch (item.getItemId()) {
                                case R.id.userblockeveryday_delete:
                                    AlertDialog.Builder builder = new AlertDialog.Builder(EverdayActivity.this);
                                    builder.setMessage("Are you sure you want to Delete these member??");
                                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            deleteUser(everyday);
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
        else if(type.equalsIgnoreCase("watchman"))
        {
            secretaryPhoneNumber = sharedPreferences1.getString("secretaryPhoneNumber", "");
            Log.e("wat",secretaryPhoneNumber);
        }

        loadData(secretaryPhoneNumber);

    }

    private void deleteUser(Everyday everyday) {

        String everydayPhoneNumber;
        everydayPhoneNumber = everyday.getEverydayPhoneNumber();
        String url = Constant.URL + "everyday/" + everydayPhoneNumber;
        RequestQueue requestQueue = Volley.newRequestQueue(EverdayActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                everydayArrayList.clear();
                loadData(secretaryPhoneNumber);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);


    }


    private void loadData(String secretaryPhoneNumber) {
        Log.e("cricket", "MS");


        RequestQueue requestQueue = Volley.newRequestQueue(EverdayActivity.this);
        String url = Constant.URL + "everyday/" + id + "/" + secretaryPhoneNumber;
//        String url2 = Constant.URL + "everydayoutentries/" + secretaryPhoneNumber + "/" + id;
//
//        Log.e("Everyday", "out entry" + secretaryPhoneNumber);
//
//        if (type.equals("watchman")) {
//            StringRequest stringRequest = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    Log.e("res", response);
//                    try {
//                        JSONArray jsonArray = new JSONArray(response);
////                        Log.e("Everyday-inentry",response);
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            JSONObject jsonObject = jsonArray.getJSONObject(i);
//                            Everyday everyday = new Everyday();
//                            everyday.setEverydayPhoneNumber(jsonObject.getString("phoneNumber"));
//                            everyday.setName(jsonObject.getString("name"));
//                            everydayArrayList.add(everyday);
//                            if (jsonObject.getString("outTime").equals("00 00 0000 00:00:00"))
//                            {
//                                Log.e("res2", jsonObject.getString("outTime"));
////                                Everyday everyday = new Everyday();
//                                Everyday everydayw = new Everyday();
//                                everyday.setEverydayPhoneNumber(jsonObject.getString("everydayPhoneNumber"));
//                                everyday.setName(jsonObject.getString("name"));
//                                everydayArrayList.add(everydayw);
//
//                            }
//                            else {
////                                Everyday everydayw = new Everyday();
////                                everyday.setEverydayPhoneNumber(jsonObject.getString("everydayPhoneNumber"));
////                                everyday.setName(jsonObject.getString("name"));
////                                everydayArrayList.add(everydayw);
//                            }
//                        }
//                        EverydayAdapter everydayAdapter = new EverydayAdapter(EverdayActivity.this, everydayArrayList);
//                        listView.setAdapter(everydayAdapter);
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Log.e("Everyday", "category" + error.toString());
//                }
//            });
//
//            requestQueue.add(stringRequest);
//
//        } else {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("res", response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    Log.e("Everyday", response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Everyday everyday = new Everyday();
                        everyday.setEverydayPhoneNumber(jsonObject.getString("everydayPhoneNumber"));
                        everyday.setName(jsonObject.getString("name"));
                        everydayArrayList.add(everyday);
                    }
                    EverydayAdapter everydayAdapter = new EverydayAdapter(EverdayActivity.this, everydayArrayList);
                    listView.setAdapter(everydayAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Everyday", "category" + error.toString());
            }
        });

        requestQueue.add(stringRequest);
//       }


    }

    public void addeveryday(final String everydayphone) {
        RequestQueue requestQueue = Volley.newRequestQueue(EverdayActivity.this);
        String URL = Constant.URL + "getuserblockeveryday";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("SEC", response);
                if (response.contains("Duplicate entry")) {
                    Toast.makeText(EverdayActivity.this, "Already added", Toast.LENGTH_SHORT).show();
                } else {
                    everydayArrayList.clear();
                    loadData(secretaryPhoneNumber);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("blockNumber", blockNumber);
                params.put("flatName", flatName);
                params.put("secretaryPhoneNumber", secretaryPhoneNumber);
                params.put("everydayPhoneNumber", everydayphone);
                return params;
            }
        };
        requestQueue.add(stringRequest);
        Intent intent = new Intent(EverdayActivity.this, UserBlockEveryDayActivity.class);
        startActivity(intent);
        finish();
    }


    public void checkin(final String phoneNumber) {
        RequestQueue requestQueue = Volley.newRequestQueue(EverdayActivity.this);
        String URL = Constant.URL + "everydayinout";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("checkin", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("phoneNumber", phoneNumber);
                params.put("secretaryPhoneNumber", secretaryPhoneNumber);
                return params;
            }
        };
        requestQueue.add(stringRequest);
        Intent intent = new Intent(EverdayActivity.this, WatchmanHomeActivity.class);
        startActivity(intent);
        finish();
    }
}