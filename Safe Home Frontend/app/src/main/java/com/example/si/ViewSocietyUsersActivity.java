package com.example.si;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.si.Adapter.FlatRecyclerAdapter;
import com.example.si.Adapter.UserFlatAdapter;
import com.example.si.Constants.Constant;
import com.example.si.model.Flat;
import com.example.si.model.UserFlat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewSocietyUsersActivity extends AppCompatActivity {


    SearchView searchView;
    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;
    String secretaryPhoneNumber,flatName;
    UserFlatAdapter userFlatAdapter;
    ArrayList<UserFlat> userFlatArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_society_users);

        searchView = findViewById(R.id.sv_users_users);
        recyclerView = findViewById(R.id.rv_usersrecyclerview);
        userFlatArrayList=new ArrayList<>();
        loadData();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                userFlatAdapter.filter(newText);
                return false;
            }
        });

    }

    private void loadData() {
//        sharedPreferences = getSharedPreferences("USERDATA", MODE_PRIVATE);
        Intent intent=getIntent();

        flatName=intent.getStringExtra("flatname");

        secretaryPhoneNumber=intent.getStringExtra("secretaryPhonenNumber");
        RequestQueue requestQueue= Volley.newRequestQueue(ViewSocietyUsersActivity.this);

        String url = Constant.URL + "userflat" + "/" + flatName + "/" + secretaryPhoneNumber;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        UserFlat userFlat = new UserFlat();
                        userFlat.setblockName(jsonObject.getString("blockName"));
                        userFlat.setflatNumber(jsonObject.getString("flatNumber"));
                        userFlat.setSecretaryPhoneNumber(jsonObject.getString("secretaryPhoneNumber"));
                        userFlat.setUserPhoneNumber(jsonObject.getString("userPhoneNumber"));
                        userFlat.setName(jsonObject.getString("userName"));
                        userFlatArrayList.add(userFlat);
                    }
//                    userBlockAdapter = new UserBlockAdapter(UserFlatActivity.this, userFlatArrayList);
//                    listView.setAdapter(userBlockAdapter);

                    userFlatAdapter = new UserFlatAdapter(ViewSocietyUsersActivity.this,userFlatArrayList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ViewSocietyUsersActivity.this));
                    recyclerView.setAdapter(userFlatAdapter);


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
}