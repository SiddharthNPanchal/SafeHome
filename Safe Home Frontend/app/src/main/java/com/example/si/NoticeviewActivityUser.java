package com.example.si;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.si.Adapter.NoticeAdapter;
import com.example.si.Constants.Constant;
import com.example.si.model.Notice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NoticeviewActivityUser extends AppCompatActivity {

    ListView listView;
    ArrayList<Notice> noticeArrayList;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String userPhoneNumber;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticeview_user);
        setTitle("Notice");
        listView=findViewById(R.id.rv_noticerecyclerview);
//        listView=findViewById(R.id.lv_notice_user);
        noticeArrayList=new ArrayList<>();
        searchView=findViewById(R.id.sv_notice_user);
        searchView.setVisibility(View.GONE);



        loadData();
    }

    private void loadData() {
        Log.e("cricket","MS");


        sharedPreferences = getSharedPreferences("USERDATA", MODE_PRIVATE);
        userPhoneNumber = sharedPreferences.getString("secretaryPhoneNumber", "");
        Log.e("mobile",userPhoneNumber);


        RequestQueue requestQueue = Volley.newRequestQueue(NoticeviewActivityUser.this);
        String url = Constant.URL + "getNotice/"+userPhoneNumber;

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("res",response);
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        Notice notice=new Notice();
                        notice.setId(jsonObject.getString("id"));
                        notice.setSecretaryPhoneNumber(jsonObject.getString("secretaryPhoneNumber"));
                        notice.setMessage(jsonObject.getString("message"));
                        Log.e("Date",jsonObject.getString("date"));
                        notice.setDate(jsonObject.getString("date"));
                        notice.setTitle(jsonObject.getString("title"));
                        noticeArrayList.add(notice);
                    }
                    NoticeAdapter noticeAdapter=new NoticeAdapter(NoticeviewActivityUser.this,noticeArrayList);
                    listView.setAdapter(noticeAdapter);

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
}