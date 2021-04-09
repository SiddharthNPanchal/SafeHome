package com.example.si;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.Map;

public class SecretaryActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ArrayList<Blocks> blocksArrayList;
    String userPhoneNumber, userName, userTypeId, type;
    AlertDialog.Builder alertDialog;
    SearchView searchView;
    BlockRecyclerAdapter blockRecyclerAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secretary);
        setTitle("Blocks");

        searchView = findViewById(R.id.sv_block_secretary);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                blockRecyclerAdapter.filter(newText);
                return false;
            }
        });

        recyclerView = findViewById(R.id.rv_blockrecyclerview);
        blocksArrayList = new ArrayList<>();

        sharedPreferences = getSharedPreferences("SI", MODE_PRIVATE);
        userPhoneNumber = sharedPreferences.getString("userPhoneNumber", "");
        userName = sharedPreferences.getString("userName", "");
        userTypeId = sharedPreferences.getString("userTypeId", "");
        type = sharedPreferences.getString("type", "");
        Log.e("SECRETARY", userName + " " + userPhoneNumber);
        loadData();

    }

    public void loadData() {
        RequestQueue requestQueue = Volley.newRequestQueue(SecretaryActivity.this);
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
                    blockRecyclerAdapter = new BlockRecyclerAdapter(SecretaryActivity.this,blocksArrayList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(SecretaryActivity.this));
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


    public void addFlat(final String flatName)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(SecretaryActivity.this);
        String URL = Constant.URL + "blocks";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("SEC", response);
                if (response.contains("Duplicate entry")) {
                    Toast.makeText(SecretaryActivity.this, "Already added", Toast.LENGTH_SHORT).show();
                } else {
                    blocksArrayList.clear();
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
                params.put("blockName", flatName);
                params.put("secretaryPhoneNumber", userPhoneNumber);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_add_flat_menu:
                alertDialog = new AlertDialog.Builder(this);
                View view = LayoutInflater.from(this).inflate(R.layout.layout_add_flat_secretary, null, false);
                alertDialog.setView(view);
                final EditText edtFlatname = view.findViewById(R.id.edt_flat_name_secretary);
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addFlat(edtFlatname.getText().toString());
                    }
                });

                alertDialog.show();
                break;
            case R.id.item_notice_menu:
                Intent intent=new Intent(this,NoticeActivity.class);
                startActivity(intent);
                break;

            case R.id.item_add_watchman:
                Intent intent1=new Intent(this,WatchmanViewActivity.class);
                startActivity(intent1);
                break;

            case R.id.item_logout_menu:

                Constant.logout(SecretaryActivity.this);
                break;
            case R.id.item_add_household:
                Intent intent2=new Intent(this,activity_everyday.class);
                startActivity(intent2);
                break;


            case R.id.item_view_household:
                Intent intent3=new Intent(this,CategoryViewUser.class);
                startActivity(intent3);
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.secretary_menu, menu);
        return true;

    }

    public void updateBlock(final int position)
    {
        alertDialog = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.layout_add_flat_secretary, null, false);
        alertDialog.setView(view);
        final EditText edtFlatname = view.findViewById(R.id.edt_flat_name_secretary);
        Blocks blocks = blocksArrayList.get(position);
        edtFlatname.setText(blocks.getblockName());
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.setPositiveButton("update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String flatName = edtFlatname.getText().toString();
                String url = Constant.URL + "blocks/" + blocksArrayList.get(position).getblockName() + "/" + userPhoneNumber;
                StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.contains(Constant.duplicate))
                        {
                            Toast.makeText(SecretaryActivity.this, "Already exists", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            blocksArrayList.clear();
                            loadData();
                            Toast.makeText(SecretaryActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String,String> params = new HashMap<>();
                        params.put("blockName",flatName);
                        params.put("secretaryPhoneNumber",userPhoneNumber);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(SecretaryActivity.this);
                requestQueue.add(stringRequest);
            }
        });

        alertDialog.show();
    }

    public void deleteBlock(Blocks blocks) {
        String url = Constant.URL + "blocks/" + blocks.getblockName() + "/" + blocks.getSecretaryPhoneNumber();
        RequestQueue requestQueue = Volley.newRequestQueue(SecretaryActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                blocksArrayList.clear();
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