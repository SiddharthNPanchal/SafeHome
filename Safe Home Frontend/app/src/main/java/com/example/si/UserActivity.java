package com.example.si;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.si.Constants.Constant;
import com.example.si.model.UserFlat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    String userPhoneNumber,userName,userTypeId,type;
    Button button;
    Spinner spinnerSociety,spinnerFlat,spinnerBlock;
    ArrayList<UserFlat> userFlatArrayList;
    ArrayList<UserFlat> userFlatArrayListFlat;
    ArrayList<UserFlat> userBlockArrayListFlat;

    UserFlat userDataAdd;
    int id;
    String society[];
    String flat[];
    String block[];

    Button btnAddUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        setTitle("Select Society");


        sharedPreferences = getSharedPreferences("SI",MODE_PRIVATE);
        userPhoneNumber = sharedPreferences.getString("userPhoneNumber", "");
        userName = sharedPreferences.getString("userName", "");
        userTypeId = sharedPreferences.getString("userTypeId", "");
        type = sharedPreferences.getString("type", "");

        userFlatArrayList = new ArrayList<>();
        userFlatArrayListFlat = new ArrayList<>();
        userBlockArrayListFlat = new ArrayList<>();


        spinnerSociety = (Spinner) findViewById(R.id.spn_society_user);
        loadSocietyData();
        spinnerSociety.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(UserActivity.this, "" + userBlockArrayList.get(position).getSecretaryPhoneNumber() + " " + position, Toast.LENGTH_SHORT).show();
                loadFlatData(userFlatArrayList.get(position).getSecretaryPhoneNumber());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerFlat = findViewById(R.id.spn_flat_user);
        spinnerFlat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadBlockData(userFlatArrayListFlat.get(position).getblockName(), userFlatArrayListFlat.get(position).getSecretaryPhoneNumber());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerBlock = findViewById(R.id.spn_block_user);
        spinnerBlock.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                UserFlat userFlat = userBlockArrayListFlat.get(position);
                Log.e("BLOCK","Block number " + userFlat.getflatNumber());
                Log.e("BLOCK","Secretary PhoneNumber " + userFlat.getSecretaryPhoneNumber());
                Log.e("BLOCK","Flat Name " + userFlat.getblockName());
                Log.e("BLOCK","User Name " + userFlat.getName());
                Log.e("BLOCK","User Number " + userFlat.getUserPhoneNumber());
                id = position;
                userDataAdd = userFlat;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        button = findViewById(R.id.btn_logout_users);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.logout(UserActivity.this);
            }
        });

        btnAddUserData = findViewById(R.id.btn_userdata_user);
        btnAddUserData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ADD","HELLO " +userDataAdd.getUserPhoneNumber());
                SharedPreferences sharedPreferences = getSharedPreferences("USERDATA",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("blockNumber",userDataAdd.getflatNumber());
                editor.putString("flatName",userDataAdd.getblockName());
                editor.putString("userPhoneNumber",userDataAdd.getUserPhoneNumber());
                editor.putString("secretaryPhoneNumber",userDataAdd.getSecretaryPhoneNumber());
                editor.putString("userName",userDataAdd.getName());
                editor.commit();
                societyUserLogin(userDataAdd.getSecretaryPhoneNumber());
                Intent intent = new Intent(UserActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private void societyUserLogin(final String secretaryPhoneNumber) {

        RequestQueue requestQueue = Volley.newRequestQueue(UserActivity.this);
        String url = Constant.URL + "usersocietylogin";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("secretaryPhoneNumber",secretaryPhoneNumber);
                params.put("userPhoneNumber",userPhoneNumber);
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }

    private void loadBlockData(String fName,String sNo) {
        String url = Constant.URL + "userblock2/" + fName + "/" + userPhoneNumber + "/" + sNo;
        RequestQueue requestQueue = Volley.newRequestQueue(UserActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    block = new String[jsonArray.length()];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        UserFlat userFlat = new UserFlat();
                        userFlat.setblockName(jsonObject.getString("blockName"));
                        userFlat.setflatNumber(jsonObject.getString("flatNumber"));
                        userFlat.setSecretaryPhoneNumber(jsonObject.getString("secretaryPhoneNumber"));
                        userFlat.setName(userName);
                        userFlat.setUserPhoneNumber(jsonObject.getString("userPhoneNumber"));
                        userBlockArrayListFlat.add(userFlat);
                        block[i] = jsonObject.getString("flatNumber");
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(UserActivity.this, android.R.layout.simple_spinner_item,block);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerBlock.setAdapter(adapter);

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

    private void loadFlatData(String sp) {

        Log.e("USERss",sp);
        String url = Constant.URL + "userblock2/" + userPhoneNumber + "/" + sp;
        RequestQueue requestQueue = Volley.newRequestQueue(UserActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    flat = new String[jsonArray.length()];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        UserFlat userFlat = new UserFlat();
                        userFlat.setblockName(jsonObject.getString("blockName"));
                        userFlat.setflatNumber(jsonObject.getString("flatNumber"));
                        userFlat.setSecretaryPhoneNumber(jsonObject.getString("secretaryPhoneNumber"));
                        userFlat.setName(userName);
                        userFlat.setUserPhoneNumber(jsonObject.getString("userPhoneNumber"));
                        userFlatArrayListFlat.add(userFlat);
                        flat[i] = jsonObject.getString("blockName");
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(UserActivity.this, android.R.layout.simple_spinner_item,flat);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerFlat.setAdapter(adapter);

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

    private void loadSocietyData() {
        String url = Constant.URL + "userblock2/" + userPhoneNumber;
        RequestQueue requestQueue = Volley.newRequestQueue(UserActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    society = new String[jsonArray.length()];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        UserFlat userFlat = new UserFlat();
                        userFlat.setblockName(jsonObject.getString("blockName"));
                        userFlat.setflatNumber(jsonObject.getString("flatNumber"));
                        userFlat.setSecretaryPhoneNumber(jsonObject.getString("secretaryPhoneNumber"));
                        userFlat.setName(userName);
                        userFlat.setUserPhoneNumber(jsonObject.getString("userPhoneNumber"));
                        userFlatArrayList.add(userFlat);
                        society[i] = jsonObject.getString("societyName");

                    }

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
        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<StringRequest>() {
            @Override
            public void onRequestFinished(Request<StringRequest> request) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(UserActivity.this, android.R.layout.simple_spinner_item,society);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerSociety.setAdapter(adapter);
            }
        });


    }
}
