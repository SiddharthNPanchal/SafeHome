package com.example.si;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class SocietySignupActivity extends AppCompatActivity {

    EditText edtNameSignup, edtPasswordSignup, edtConfirmSignup, edtMobileSignup, edtSocietySignup;
    Button button,backbutton;
    boolean flag;
    String uId;
    boolean newFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_society_signup);
        setTitle("Sign Up");



        edtNameSignup = findViewById(R.id.edt_name_signup);
        edtPasswordSignup = findViewById(R.id.edt_password_signup);
        edtConfirmSignup = findViewById(R.id.edt_confirm_signup);
        edtMobileSignup = findViewById(R.id.edt_mobile_signup);
//         edtSocietySignup = findViewById(R.id.edt_societyname_signup);
        button = findViewById(R.id.btn_signup_signup);
        backbutton=findViewById(R.id.btn_loginback_signup);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SocietySignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

       });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name = edtNameSignup.getText().toString();
                final String password = edtPasswordSignup.getText().toString();
                String confirm = edtConfirmSignup.getText().toString();
                final String mobile = edtMobileSignup.getText().toString();
                final String societyName = edtSocietySignup.getText().toString();
                flag = true;

                if (Validation.isEmpty(name)) {
                    edtNameSignup.setError("Enter Name");
                    flag = false;
                }
                if (password.length() <= 7) {
                    edtPasswordSignup.setError("Enter password of atleast 8 characters");
                    flag = false;
                }
                if (!(password.equals(confirm))) {
                    edtConfirmSignup.setError("Password do not match");
                    flag = false;
                }
                if (Validation.isEmpty(mobile)) {
                    edtMobileSignup.setError("Enter Mobile Number");
                    flag = false;
                }
                if (mobile.length() != 10) {
                    edtMobileSignup.setError("Enter 10 digit mobile number");
                    flag = false;
                }
                if (Validation.isEmpty(societyName)) {
                    edtSocietySignup.setError("Enter Society Name");
                    flag = false;
                }
                if (flag) {

                    RequestQueue requestQueue3 = Volley.newRequestQueue(SocietySignupActivity.this);
                            String url = Constant.URL + "usertypename/secretary";
                    StringRequest stringRequest3 = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                String id = jsonObject.getString("userTypeId");
                                uId = id;
                                Log.e("HELLO", "ID = " + uId);
                                Log.e("HELLO", "RESPONSE " + response);
                                RequestQueue requestQueue = Volley.newRequestQueue(SocietySignupActivity.this);
                                String URL = Constant.URL + "userss";
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (response.contains("ER_DUP_ENTRY")) {
                                            newFlag = false;
                                            Toast.makeText(SocietySignupActivity.this, "User Already exists", Toast.LENGTH_SHORT).show();
                                        } else {
                                            RequestQueue requestQueue1 = Volley.newRequestQueue(SocietySignupActivity.this);
                                            String URL = Constant.URL + "society";
                                            StringRequest stringRequest1 = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                }
                                            }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                }
                                            }) {
                                                @Override
                                                protected Map<String, String> getParams() throws AuthFailureError {
                                                    Map<String, String> params = new HashMap<>();
                                                    params.put("secretaryPhoneNumber", mobile);
                                                    params.put("societyName", societyName);
                                                    return params;
                                                }

                                                @Override
                                                public Map<String, String> getHeaders() throws AuthFailureError {
                                                    Map<String, String> params = new HashMap<String, String>();
                                                    params.put("Content-Type", "application/x-www-form-urlencoded");
                                                    return params;
                                                }
                                            };
                                            requestQueue1.add(stringRequest1);
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
                                        params.put("userName", name);
                                        params.put("password", password);
                                        params.put("userTypeId", uId);
                                        return params;
                                    }

                                    @Override
                                    public Map<String, String> getHeaders() throws AuthFailureError {
                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put("Content-Type", "application/x-www-form-urlencoded");
                                        return params;
                                    }
                                };
                                requestQueue.add(stringRequest);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    requestQueue3.add(stringRequest3);
                    requestQueue3.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<StringRequest>() {
                        @Override
                        public void onRequestFinished(Request<StringRequest> request) {
                            Intent intent = new Intent(SocietySignupActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }
        });
    }

}
