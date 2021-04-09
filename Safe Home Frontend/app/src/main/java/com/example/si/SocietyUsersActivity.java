package com.example.si;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.si.Adapter.BlockRecyclerAdapter;
import com.example.si.Constants.Constant;
import com.example.si.model.Blocks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SocietyUsersActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    String userPhoneNumber;
    SearchView searchView;
    BlockRecyclerAdapter blockRecyclerAdapter;
    RecyclerView recyclerView;
    ArrayList<Blocks> blocksArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_society_users);

        sharedPreferences = getSharedPreferences("SI", MODE_PRIVATE);
        userPhoneNumber = sharedPreferences.getString("userPhoneNumber", "");

        recyclerView = findViewById(R.id.rv_blockrecyclerview);
        blocksArrayList = new ArrayList<>();


        loadData();

    }

    public void loadData() {
        RequestQueue requestQueue = Volley.newRequestQueue(SocietyUsersActivity.this);
        String url = Constant.URL + "flatsecretary/" + userPhoneNumber;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Blocks blocks = new Blocks();
                        blocks.setblockName(jsonObject.getString("blockName"));
                        blocks.setSecretaryPhoneNumber(jsonObject.getString("secretaryPhoneNumber"));
                        blocksArrayList.add(blocks);
                    }
                    blockRecyclerAdapter = new BlockRecyclerAdapter(SocietyUsersActivity.this,blocksArrayList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(SocietyUsersActivity.this));
                    recyclerView.setAdapter(blockRecyclerAdapter);

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