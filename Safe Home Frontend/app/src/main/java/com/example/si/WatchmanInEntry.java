package com.example.si;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.si.Adapter.LastActivityAdapter;
import com.example.si.Constants.Constant;
import com.example.si.model.LastActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
public class WatchmanInEntry extends AppCompatActivity {


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ArrayList<LastActivity> inentryArrayList;
    ListView listView;
    TextView checkout;
    String phoneNumber,secretaryPhoneNumber,userName,societyName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity);
        listView=findViewById(R.id.rv_last_activity_view);
        checkout=findViewById(R.id.txt_makeoutentry_watchmaninentry);
        inentryArrayList=new ArrayList<>();
        sharedPreferences = getSharedPreferences("SI", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        secretaryPhoneNumber = sharedPreferences.getString("secretaryPhoneNumber", "");
        userName = sharedPreferences.getString("userName", "");
        societyName = sharedPreferences.getString("societyName", "");

        loadData();
    }

    public void loadData() {
          String url = Constant.URL + "everydayinentries" + "/" + secretaryPhoneNumber;
          RequestQueue requestQueue = Volley.newRequestQueue(WatchmanInEntry.this);
          StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("WATCH",response);

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        LastActivity lastActivity = new LastActivity();
                        //Log.e("phoneNumber",jsonObject.getString("phoneNumber"));
                        lastActivity.setIntime(jsonObject.getString("inTime"));
                        lastActivity.setName(jsonObject.getString("name"));
                        lastActivity.setType(jsonObject.getString("categoryName"));
                        lastActivity.setOuttime(jsonObject.getString("outTime"));
                        lastActivity.setPhoneNumber(jsonObject.getString("phoneNumber"));
//                        Log.e("phoneNumber",phoneNumber);
                        editor.putString("phoneNumber",phoneNumber);
                        inentryArrayList.add(lastActivity);
                    }
                    LastActivityAdapter lastActivityAdapter = new LastActivityAdapter(WatchmanInEntry.this,inentryArrayList);
                    listView.setAdapter((ListAdapter) lastActivityAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    public void checkout(String phoneNumber) {
        String url = Constant.URL + "everydayinout" + "/" + phoneNumber;
        RequestQueue requestQueue = Volley.newRequestQueue(WatchmanInEntry.this);
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("WATCH",response);
                inentryArrayList.clear();
                loadData();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }
}


