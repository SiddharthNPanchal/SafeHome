package com.example.si;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.si.Adapter.NoticeAdapter;
import com.example.si.Constants.Constant;
import com.example.si.model.Notice;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NoticeActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<Notice> noticeArrayList;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String secretaryPhoneNumber;
    AlertDialog.Builder alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        setTitle("Notice");


        listView=findViewById(R.id.lv_notice_secretary);
        noticeArrayList=new ArrayList<>();

        loadData();
    }





    private void loadData() {
        Log.e("cricket","MS");


        sharedPreferences = getSharedPreferences("SI", MODE_PRIVATE);
        secretaryPhoneNumber = sharedPreferences.getString("userPhoneNumber","");

        Log.e("cricket","MS"+secretaryPhoneNumber);


        RequestQueue requestQueue = Volley.newRequestQueue(NoticeActivity.this);
        String url = Constant.URL + "getNotice/"+secretaryPhoneNumber;

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
                        notice.setTitle(jsonObject.getString("title"));
                        Log.e("Date",jsonObject.getString("date"));
                        notice.setDate(jsonObject.getString("date"));
                        noticeArrayList.add(notice);
                    }
                    NoticeAdapter noticeAdapter=new NoticeAdapter(NoticeActivity.this,noticeArrayList);
                    listView.setAdapter(noticeAdapter);

                } catch (JSONException  e) {
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
    public void addNotice(final String addnoticemsg,final String addnoticetitle)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(NoticeActivity.this);
        String URL = Constant.URL + "getNotice";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("SEC", response);
                if (response.contains("Duplicate entry")) {
                    Toast.makeText(NoticeActivity.this, "Already added", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("Firebase", "token "+ FirebaseInstanceId.getInstance().getToken());

                    noticeArrayList.clear();
                    loadData();
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
                params.put("message", addnoticemsg);
                params.put("title", addnoticetitle);
                params.put("secretaryPhoneNumber", secretaryPhoneNumber);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_add_notice_menu:
                alertDialog = new AlertDialog.Builder(this);
                View view = LayoutInflater.from(this).inflate(R.layout.layout_add_notice_secretory, null, false);
                alertDialog.setView(view);
                final EditText addnoticemsg = view.findViewById(R.id.add_notice_secretary);
                final EditText addnoticetitle=view.findViewById(R.id.add_title_secretary);

                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addNotice(addnoticemsg.getText().toString(),addnoticetitle.getText().toString());


                    }
                });

                alertDialog.show();
                break;



            case R.id.item_logout_menu:

                Constant.logout(NoticeActivity.this);
                break;
        }
        return true;
    }









    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.notice_menu, menu);
        return true;

    }
}