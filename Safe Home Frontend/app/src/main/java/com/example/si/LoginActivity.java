package com.example.si;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtUsernameLogin, edtPasswordLogin;
    Button btnLoginLogin, btnSignupLogin;
    RadioGroup radioGroup;
    RadioButton rbSecretary, rbUser, rbWatchman, radioButton;
    RequestQueue requestQueue;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;

    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Login");
        Log.e("Firebase", "token " + FirebaseInstanceId.getInstance().getToken());

        textView = findViewById(R.id.txt_otp_login);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, LoginOTPActivity.class);
                startActivity(intent);
                finish();
            }
        });


        sharedPreferences = getSharedPreferences("SI", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        edtUsernameLogin = findViewById(R.id.edt_username_login);
        edtPasswordLogin = findViewById(R.id.edt_password_login);
        btnLoginLogin = findViewById(R.id.btn_login_login);
        btnSignupLogin = findViewById(R.id.btn_signup_login);
        radioGroup = findViewById(R.id.rg_type_login);
        rbSecretary = findViewById(R.id.rb_secretary_login);
        rbUser = findViewById(R.id.rb_user_login);
        rbWatchman = findViewById(R.id.rb_watchman_login);
        btnSignupLogin.setOnClickListener(this);
        btnLoginLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_login:
                final String username = edtUsernameLogin.getText().toString();
                final String password = edtPasswordLogin.getText().toString();
                boolean flag = true;
                if (Validation.isEmpty(username)) {
                    edtUsernameLogin.setError("Enter Username");
                    flag = false;
                }
                if (Validation.isEmpty(password)) {
                    edtPasswordLogin.setError("Enter Password");
                    flag = false;
                }


                int id = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(id);
                Log.e("HELLO", "" + radioButton.getText().toString());
                final String txtRadio = radioButton.getText().toString();
                if (flag) {
                    if (txtRadio.toLowerCase().equals("watchman")) //helly watchman start
                    {

                        String url = Constant.URL + "societywatchmanlogin";
//                        final String userTypeId = "3";
                        RequestQueue requestQueue = Volley.newRequestQueue(this);
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONArray jsonArray = new JSONArray(response);
                                    if (jsonArray.length() == 0) {
                                        Log.e("WL", response + " wrrongg");
                                        Toast.makeText(LoginActivity.this, "Invalid username or password or role", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.e("WATCHMAN", response + " HELLO");
                                        String secretaryPhoneNumber = "", userName = "", societyName = "";
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject watchmanObject = jsonArray.getJSONObject(i);
                                            secretaryPhoneNumber=watchmanObject.getString("secretaryPhoneNumber");
                                            userName=watchmanObject.getString("userName");
                                            societyName=watchmanObject.getString("societyName");
                                            editor.putString("secretaryPhoneNumber", secretaryPhoneNumber);
                                            editor.putString("userName", userName);
                                            editor.putString("societyName",societyName);
                                            editor.putString("type","watchman");
                                            editor.commit();

                                            Intent intent = new Intent(LoginActivity.this, WatchmanHomeActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                            finish();
//                                            Log.e("WATCHMAN", "name " + watchmanObject.getString("userName"));
//                                            Log.e("WATCHMAN", "secretaryPhoneNumber " + watchmanObject.getString("secretaryPhoneNumber"));
//                                            Log.e("WATCHMAN", "societyName " + watchmanObject.getString("societyName"));
                                        }

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
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
                                params.put("userTypeId", "3");
                                params.put("userPhoneNumber", username);
                                params.put("password", password);
                                return params;

                            }
                        };
                        requestQueue.add(stringRequest);
                    }
                    else {
//darshan code
                        requestQueue = Volley.newRequestQueue(this);
                        final String typeUser;
                        if(txtRadio.equalsIgnoreCase("resident"))
                        {
                            typeUser = "user";
                        }
                        else
                        {
                            typeUser = "secretary";
                        }
                        String URL = Constant.URL + "loginPost/";
                        Log.e("HELLO", "URL = " + URL);
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.e("HELLO", response);
                                try {
                                    JSONArray jsonArray = new JSONArray(response);
                                    if (jsonArray.length() == 0) {
                                        Toast.makeText(LoginActivity.this, "Invalid username or password or role", Toast.LENGTH_LONG).show();
                                    } else {
                                        Log.e("HELLO", response);
                                        String userPhoneNumber = "", userName = "", userTypeId = "";
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                                        userPhoneNumber = jsonObject1.getString("userPhoneNumber");
                                        userName = jsonObject1.getString("userName");
                                        userTypeId = jsonObject1.getString("userTypeId");
                                        String type = jsonObject1.getString("name");
                                        editor.putString("userPhoneNumber", userPhoneNumber);
                                        editor.putString("userName", userName);
                                        editor.putString("userTypeId", userTypeId);
                                        editor.putString("type", type);
                                        Log.e("LOGIN",type);
                                        editor.commit();

                                        Intent intent;
                                        if (type.equals("secretary")) {
                                            intent = new Intent(LoginActivity.this, SecretaryActivity.class);
                                        } else if (type.equals("user")) {
                                            intent = new Intent(LoginActivity.this, UserActivity.class);
                                        } else {
                                            intent = new Intent(LoginActivity.this, LoginActivity.class);
                                        }
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("HELLO", "ERROR" + error.toString());
                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {

                                Map<String,String> params = new HashMap<>();
                                params.put("username",username);
                                params.put("password",password);
                                params.put("userType",typeUser);
                                return params;
                            }
                        };
                        requestQueue.add(stringRequest);


                    }

//watchman login code end
                }

                break;

            case R.id.btn_signup_login:
                Log.e("HERE", "CLICK");
                Intent intent = new Intent(LoginActivity.this, SocietySignupActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }
}