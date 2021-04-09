package com.example.si;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.si.Adapter.FlatRecyclerAdapter;
import com.example.si.Adapter.UserBlockAdapter;
import com.example.si.Adapter.UserFlatAdapter;
import com.example.si.Constants.Constant;
import com.example.si.model.UserFlat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserFlatActivity extends AppCompatActivity {


    String flatName, blockNumber, secretaryPhoneNumber;

    ArrayList<UserFlat> userFlatArrayList;
    ListView listView;
    SearchView searchView;
    UserBlockAdapter userBlockAdapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    RecyclerView recyclerView;
    UserFlatAdapter userFlatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_block);

        setTitle("User");
        Intent intent = getIntent();
        flatName = intent.getStringExtra("blockName");
        blockNumber = intent.getStringExtra("flatNumber");
        secretaryPhoneNumber = intent.getStringExtra("secretaryPhoneNumber");

        loadData();
            recyclerView=findViewById(R.id.rv_userflatrecyclerview);

//        listView = findViewById(R.id.lv_userBlock_userBlock);
        searchView = findViewById(R.id.sv_userflat_secretary);
        userFlatArrayList = new ArrayList<>();


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

    public void deleteUser(UserFlat userFlat)
    {
        String url = Constant.URL + "userflat/" + userFlat.getUserPhoneNumber() + "/" + userFlat.getflatNumber() + "/" + userFlat.getblockName() + "/" + userFlat.getSecretaryPhoneNumber();
        RequestQueue requestQueue = Volley.newRequestQueue(UserFlatActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                userFlatArrayList.clear();
                loadData();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    public void loadData() {
        RequestQueue requestQueue = Volley.newRequestQueue(UserFlatActivity.this);
        String url = Constant.URL + "userflat/" + blockNumber + "/" + flatName + "/" + secretaryPhoneNumber;

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

                    userFlatAdapter = new UserFlatAdapter(UserFlatActivity.this,userFlatArrayList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(UserFlatActivity.this));
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_add_user_menu:
                Intent intent = new Intent(UserFlatActivity.this, AddUserActivity.class);
                intent.putExtra("blockName", flatName);
                intent.putExtra("flatNumber", blockNumber);
                intent.putExtra("secretaryPhoneNumber", secretaryPhoneNumber);
                startActivity(intent);
                finish();
                break;

            case R.id.item_logout_menu:
                Constant.logout(UserFlatActivity.this);
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.userblock_userblock_menu, menu);
        return true;

    }
}
