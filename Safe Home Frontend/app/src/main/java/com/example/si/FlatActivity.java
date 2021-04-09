package com.example.si;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.si.Adapter.FlatAdapter;
import com.example.si.Adapter.FlatRecyclerAdapter;
import com.example.si.Constants.Constant;
import com.example.si.model.Flat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FlatActivity extends AppCompatActivity {


    String phoneNumber,flatName;
    ArrayList<Flat> flatArrayList;
    ListView listView;
    SearchView searchView;
    FlatAdapter flatAdapter;
    AlertDialog.Builder alertDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    RecyclerView recyclerView;
    FlatRecyclerAdapter flatRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flat);

        setTitle("Flat");

        sharedPreferences = getSharedPreferences("SI", MODE_PRIVATE);

        Intent intent = getIntent();
        phoneNumber = intent.getStringExtra("userPhoneNumber");
        flatName = intent.getStringExtra("blockName");

        flatArrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.rv_flatrecyclerview);
        //listView = findViewById(R.id.lv_block_block);
        searchView = findViewById(R.id.sv_flat_secretary);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                flatRecyclerAdapter.filter(newText);
                return false;
            }
        });
        loadData();

    }
    public void deleteFlat(Flat flat) {
        String url = Constant.URL + "flat/" + flat.getflatNumber() + "/" + flat.getblockName() + "/" + flat.getSecretaryPhoneNumber();
        RequestQueue requestQueue = Volley.newRequestQueue(FlatActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                flatArrayList.clear();
                loadData();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    public void updateFlat(final int position)
    {
        alertDialog = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.layout_add_block_block, null, false);
        alertDialog.setView(view);
        final EditText edtBlockNumber = view.findViewById(R.id.edt_block_number_block);
        Flat block = flatArrayList.get(position);
        edtBlockNumber.setText(block.getflatNumber());
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.setPositiveButton("update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String blockNumber = edtBlockNumber.getText().toString();
                String url = Constant.URL + "flat/" + flatArrayList.get(position).getflatNumber() + "/" + flatArrayList.get(position).getblockName() + "/" + phoneNumber;
                StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("BLOCK",response);
                        if(response.contains(Constant.duplicate))
                        {
                            Toast.makeText(FlatActivity.this, "Already exists", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            flatArrayList.clear();
                            loadData();
                            Toast.makeText(FlatActivity.this, "Updated", Toast.LENGTH_SHORT).show();
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
                        params.put("flatNumber",blockNumber);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(FlatActivity.this);
                requestQueue.add(stringRequest);
            }
        });

        alertDialog.show();
    }
    public void loadData()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(FlatActivity.this);
        String url = Constant.URL + "flat/" + flatName + "/" + phoneNumber;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Flat flat = new Flat();
                        flat.setblockName(jsonObject.getString("blockName"));
                        flat.setSecretaryPhoneNumber(jsonObject.getString("secretaryPhoneNumber"));
                        flat.setflatNumber(jsonObject.getString("flatNumber"));
                        flatArrayList.add(flat);
                    }
                    flatRecyclerAdapter = new FlatRecyclerAdapter(FlatActivity.this,flatArrayList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(FlatActivity.this));
                    recyclerView.setAdapter(flatRecyclerAdapter);

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
            case R.id.item_add_block_menu:
                alertDialog = new AlertDialog.Builder(this);
                View view = LayoutInflater.from(this).inflate(R.layout.layout_add_block_block, null, false);
                alertDialog.setView(view);
                final EditText edtBlockNumber = view.findViewById(R.id.edt_block_number_block);
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addBlock(edtBlockNumber.getText().toString());
                    }
                });

                alertDialog.show();
                break;

            case R.id.item_logout_menu:

                Constant.logout(FlatActivity.this);
                break;
        }
        return true;
    }

    private void addBlock(final String blockNumber) {
        RequestQueue requestQueue = Volley.newRequestQueue(FlatActivity.this);
        String url = Constant.URL + "flat";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("BLOCK", response);
                if (response.contains("Duplicate entry")) {
                    Toast.makeText(FlatActivity.this, "Already added", Toast.LENGTH_SHORT).show();
                } else {
                    flatArrayList.clear();
                    loadData();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("blockName", flatName);
                params.put("flatNumber", blockNumber);
                params.put("secretaryPhoneNumber", phoneNumber);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.block_block_menu, menu);
        return true;

    }


}
