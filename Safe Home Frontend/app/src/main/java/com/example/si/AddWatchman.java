package com.example.si;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.si.Constants.Constant;
import com.example.si.Constants.Validation;

import java.util.HashMap;
import java.util.Map;

public class AddWatchman extends AppCompatActivity {
    String username, userphno, pwd, usertypeid,secrataryphno;
    EditText edtWatchmanName, edtWatchmanPassword, edtWatchmanConfirm, edtWatchmanMobile, edtWatchmanTypeid;
    SharedPreferences sharedPreferences;
    Button button;
    boolean flag;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_watchman);
        setTitle("Watchman");


        edtWatchmanName = findViewById(R.id.edt_name_addwatchman);
        edtWatchmanPassword = findViewById(R.id.edt_password_addwatchman);
        edtWatchmanConfirm = findViewById(R.id.edt_confirm_addwatchman);
        edtWatchmanMobile = findViewById(R.id.edt_mobile_addwatchman);
        button = findViewById(R.id.btn_addwatchman);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name = edtWatchmanName.getText().toString();
                final String password = edtWatchmanPassword.getText().toString();
                String confirm = edtWatchmanConfirm.getText().toString();
                final String mobile = edtWatchmanMobile.getText().toString();
//                String usertypeid = edtWatchmanTypeid.getText().toString();
                flag = true;

                if (Validation.isEmpty(name)) {
                    edtWatchmanName.setError("Enter Name");
                    flag = false;
                }
                if (password.length() <= 7) {
                    edtWatchmanPassword.setError("Enter password of atleast 8 characters");
                    flag = false;
                }
                if (!(password.equals(confirm))) {
                    edtWatchmanConfirm.setError("Password do not match");
                    flag = false;
                }
                if (Validation.isEmpty(mobile)) {
                    edtWatchmanMobile.setError("Enter Mobile Number");
                    flag = false;
                }
                if (mobile.length() != 10) {
                    edtWatchmanMobile.setError("Enter 10 digit mobile number");
                    flag = false;
                }
                if (flag) {
                    addwatchman(mobile, name, password);
                }

            }



        });
    }

    private void addwatchman(final String userphno, final String username, final String pwd) {


        sharedPreferences = getSharedPreferences("SI", MODE_PRIVATE);
        secrataryphno = sharedPreferences.getString("userPhoneNumber", "");

        Log.e("mobile",secrataryphno);
        final RequestQueue requestQueue = Volley.newRequestQueue(AddWatchman.this);
        String URL = Constant.URL + "userss";
        final StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("SEC", response);


//added here
                        final RequestQueue requestQueue1 = Volley.newRequestQueue(AddWatchman.this);
                        String URL = Constant.URL + "societyWatchman";       //here
                        final StringRequest stringRequest1 = new StringRequest(Request.Method.POST,
                                URL,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.e("SEC", response);
                                        if (response.contains("Duplicate entry")) {
                                            Toast.makeText(AddWatchman.this, "Already added", Toast.LENGTH_SHORT).show();
                                        } else {

                                        }
                                    }
                                }, new ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {


                                Map<String, String> params = new HashMap<>();

                                params.put("userPhoneNumber", userphno);
                                params.put("secretaryPhoneNumber",secrataryphno);

                                return params;
                            }
                        };
                        requestQueue1.add(stringRequest1);

                        //end





                    }
                }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String, String> params = new HashMap<>();

                params.put("userName", username);
                params.put("userPhoneNumber", userphno);
                params.put("password", pwd);
                params.put("userTypeId", "3");
                return params;
            }
        };
        requestQueue.add(stringRequest);


        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<StringRequest>() {
            @Override
            public void onRequestFinished(Request<StringRequest> request) {



                Intent intent=new Intent(AddWatchman.this,WatchmanViewActivity.class);
                startActivity(intent);


            }


        });
    }
}


