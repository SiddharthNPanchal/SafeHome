package com.example.si;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.si.Constants.Constant;
import com.example.si.Constants.Validation;
import com.example.si.model.Category;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class activity_everyday<spinnerhouseHold> extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    Button button;
    String userName, userPhoneNumber, secretaryPhoneNumber;
    EditText edthouseholdname, edthouseholdphno;
    Spinner spinnerhouseHold;
      String categoryid;
    boolean flag;
    String household[];


    ArrayList<Category> householdArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_everyday);
        setTitle("Add households");
        spinnerhouseHold = (Spinner) findViewById(R.id.household_spinner);
                edthouseholdname = findViewById(R.id.household_txt1);
                edthouseholdphno = findViewById(R.id.household_txt2);

        sharedPreferences = getSharedPreferences("SI", MODE_PRIVATE);
        secretaryPhoneNumber = sharedPreferences.getString("secretaryPhoneNumber", "");

        householdArrayList=new ArrayList<>();
        spinnerhouseHold = (Spinner) findViewById(R.id.household_spinner);
        loadHouseholdData();
        spinnerhouseHold.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryid=householdArrayList.get(position).getCategoryId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        button = findViewById(R.id.evryday_btn);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = edthouseholdname.getText().toString();
                final String phno = edthouseholdphno.getText().toString();

                flag = true;

                if (Validation.isEmpty(name)) {
                    edthouseholdname.setError("Enter Name");
                    flag = false;
                }

                if (Validation.isEmpty(phno)) {
                    edthouseholdphno.setError("Enter Mobile Number");
                    flag = false;
                }
                if (flag) {
                    addhousehold(phno, name,categoryid);
                }


            }
        });


    }

    private void addhousehold(final String phno, final String name,final String categoryid) {
        sharedPreferences = getSharedPreferences("SI", MODE_PRIVATE);
        userPhoneNumber = sharedPreferences.getString("userPhoneNumber", "");

        final RequestQueue requestQueue = Volley.newRequestQueue(activity_everyday.this);
        Log.e("everyday",name+phno+userPhoneNumber+categoryid+"data");

        String URL = Constant.URL + "everyday";
        final StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("SEC", response);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Log.e("everyday",name+phno+userPhoneNumber+"data");
                Map<String, String> params = new HashMap<>();

                params.put("name", name);
                params.put("everydayPhoneNumber", phno);
                params.put("secretaryPhoneNumber",userPhoneNumber);
                params.put("categoryId",categoryid);

                return params;
            }
        };
        requestQueue.add(stringRequest);
        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<StringRequest>() {
            @Override
            public void onRequestFinished(Request<StringRequest> request) {
                Intent intent=new Intent(activity_everyday.this,SecretaryActivity.class);
                startActivity(intent);
            }


        });
    }






    private void loadHouseholdData() {

        String url = Constant.URL + "getCategory" ;
        RequestQueue requestQueue = Volley.newRequestQueue(activity_everyday.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    household = new String[jsonArray.length()];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        household[i] = jsonObject.getString("categoryName");
                        Category category = new Category();

                        category.setCategoryName(jsonObject.getString("categoryName"));
                        category.setCategoryId(jsonObject.getString("categoryId"));
                        householdArrayList.add(category);
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
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity_everyday.this, android.R.layout.simple_spinner_item,household);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerhouseHold.setAdapter(adapter);

            }
        });
    }


}