package com.example.si;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.si.Constants.Constant;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

public class TempUserActivity extends AppCompatActivity {


    TextView textView;
    SharedPreferences sharedPreferences;
    String blockNumber,flatName,userPhoneNumber,secretaryPhoneNumber,userName;

    Button view_notice,viewfamilymember;
    Button userblockeveryday,logoutuser;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_user);

        sharedPreferences = getSharedPreferences("USERDATA",MODE_PRIVATE);
        textView = findViewById(R.id.txt_data_temp);
        blockNumber = sharedPreferences.getString("blockNumber","");
        flatName = sharedPreferences.getString("flatName","");
        userPhoneNumber = sharedPreferences.getString("userPhoneNumber","");
        secretaryPhoneNumber = sharedPreferences.getString("secretaryPhoneNumber","");
        userName = sharedPreferences.getString("userName","");
        textView.setText(blockNumber + " " + flatName + " " + secretaryPhoneNumber + " " + userName +" " + userPhoneNumber);

        userToken();

        view_notice=findViewById(R.id.notice_view_user);
        view_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TempUserActivity.this,NoticeviewActivityUser.class);
                startActivity(intent);
            }
        });

        viewfamilymember=findViewById(R.id.family_member_view_user);
        viewfamilymember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(TempUserActivity.this, UserFlatActivity.class);
                intent.putExtra("blockName",flatName);
                intent.putExtra("flatNumber",blockNumber);
                intent.putExtra("secretaryPhoneNumber",secretaryPhoneNumber);

                startActivity(intent);
            }
        });



        userblockeveryday=findViewById(R.id.userblockeveryday_view_user);
        userblockeveryday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TempUserActivity.this,UserBlockEveryDayActivity.class);
                startActivity(intent);
            }
        });
        logoutuser=findViewById(R.id.btn_logout_user);
        logoutuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.logoutuser(TempUserActivity.this);
            }
        });



    }

    private void userToken() {

        String url = Constant.URL + "userToken" +"/" + userPhoneNumber;
        RequestQueue requestQueue = Volley.newRequestQueue(TempUserActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
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

                Log.e("FIRE",FirebaseInstanceId.getInstance().getToken());
                Map<String,String> params = new HashMap<>();
                params.put("userToken", FirebaseInstanceId.getInstance().getToken());
                return params;
            }
        };
    requestQueue.add(stringRequest);

    }

}

