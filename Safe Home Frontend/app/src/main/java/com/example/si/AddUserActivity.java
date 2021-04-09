package com.example.si;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddUserActivity extends AppCompatActivity {

    String flatName, blockNumber, secretaryPhoneNumber;
    EditText edtName, edtPassword, edtConfirm, edtMobile, edtSociety;
    Button button;
    boolean flag;
    String uid,type;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        sharedPreferences = getSharedPreferences("SI",MODE_PRIVATE);
        type = sharedPreferences.getString("type", "");

        setTitle("Add User");

        flag = true;
        Intent intent = getIntent();
        flatName = intent.getStringExtra("blockName");
        blockNumber = intent.getStringExtra("flatNumber");
        secretaryPhoneNumber = intent.getStringExtra("secretaryPhoneNumber");

        edtName = findViewById(R.id.edt_name_adduser);
        edtPassword = findViewById(R.id.edt_password_adduser);
        edtConfirm = findViewById(R.id.edt_confirm_adduser);
        edtMobile = findViewById(R.id.edt_mobile_adduser);
        button = findViewById(R.id.btn_adduser);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name = edtName.getText().toString();
                final String password = edtPassword.getText().toString();
                String confirm = edtConfirm.getText().toString();
                final String mobile = edtMobile.getText().toString();
                flag = true;

                if (Validation.isEmpty(name)) {
                    edtName.setError("Enter Name");
                    flag = false;
                }
                if (password.length() <= 7) {
                    edtPassword.setError("Enter password of atleast 8 characters");
                    flag = false;
                }
                if (!(password.equals(confirm))) {
                    edtConfirm.setError("Password do not match");
                    flag = false;
                }
                if (Validation.isEmpty(mobile)) {
                    edtMobile.setError("Enter Mobile Number");
                    flag = false;
                }
                if (mobile.length() != 10) {
                    edtMobile.setError("Enter 10 digit mobile number");
                    flag = false;
                }
                if (flag) {
                    add(mobile, name, password);
                }

            }
        });

    }

    public void add(final String mobile, final String name, final String password) {
        RequestQueue requestQueue1 = Volley.newRequestQueue(AddUserActivity.this);
        String url1 = Constant.URL + "usertypename/user";
        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String id = jsonObject.getString("userTypeId");
                    Log.e("ADD", id);
                    uid = id;

                    RequestQueue requestQueue2 = Volley.newRequestQueue(AddUserActivity.this);
                    String url2 = Constant.URL + "userss";
                    StringRequest stringRequest2 = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            RequestQueue requestQueue3 = Volley.newRequestQueue(AddUserActivity.this);
                            String url3 = Constant.URL + "userflat";
                            StringRequest stringRequest3 = new StringRequest(Request.Method.POST, url3, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if (response.contains(Constant.duplicate)) {
                                        Toast.makeText(AddUserActivity.this, "Already Exists", Toast.LENGTH_SHORT).show();
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
                                    params.put("userPhoneNumber", mobile);
                                    params.put("flatNumber", blockNumber);
                                    params.put("blockName", flatName);
                                    params.put("secretaryPhoneNumber", secretaryPhoneNumber);
                                    return params;
                                }
                            };
                            requestQueue3.add(stringRequest3);

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("userPhoneNumber", mobile);
                            params.put("userName", name);
                            params.put("password", password);
                            params.put("userTypeId", uid);
                            return params;
                        }
                    };
                    requestQueue2.add(stringRequest2);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("ADD", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue1.add(stringRequest1);
        requestQueue1.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<StringRequest>() {
            @Override
            public void onRequestFinished(Request<StringRequest> request) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (type.equals("secretary")) {
                            Intent intent = new Intent(AddUserActivity.this, UserFlatActivity.class);
                            intent.putExtra("blockName", flatName);
                            intent.putExtra("flatNumber", blockNumber);
                            intent.putExtra("secretaryPhoneNumber", secretaryPhoneNumber);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                        if (type.equals("user")) {
                            Intent intent=new Intent(AddUserActivity.this,TempUserActivity.class);
                            startActivity(intent);
                        }

                    }
                },1000);


                }


        });
    }
}