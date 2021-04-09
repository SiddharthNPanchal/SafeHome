package com.example.si;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.example.si.Adapter.NoticeAdapter;
import com.example.si.Constants.Constant;
import com.example.si.Constants.Validation;
import com.example.si.model.Notice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class changepassword extends AppCompatActivity {


    EditText edtoldpassword,edtnewpassword,edtnewpassword1;
    SharedPreferences sharedPreferences;
    String oldpassword,newpassword,newpassword1,userphonenumber,usertype,usertypeid;
    Button changepasswordbutton;
    public  String[] pass =new String[1];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);
//        edtoldpassword=findViewById(R.id.oldpassword);
        edtnewpassword=findViewById(R.id.newpassword);
        edtnewpassword1=findViewById(R.id.newpassword1);
        changepasswordbutton=findViewById(R.id.changepassword);

        sharedPreferences = getSharedPreferences("SI", MODE_PRIVATE);
        userphonenumber=sharedPreferences.getString("userPhoneNumber","");
        usertype=sharedPreferences.getString("type","");
        usertypeid=sharedPreferences.getString("userTypeId","");
        Log.e("passres",usertype+"user type"+usertypeid);



        Log.e("passwords",oldpassword+"   pass"+newpassword+" "+newpassword1);


       // loadpassword();


        changepasswordbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // oldpassword=edtoldpassword.getText().toString();
                newpassword=edtnewpassword.getText().toString();
                newpassword1=edtnewpassword1.getText().toString();
                Log.e("passwords",oldpassword+"   pass"+newpassword+" "+newpassword1);

                boolean flag = true;

//                if (Validation.isEmpty(oldpassword)) {
//                    edtoldpassword.setError("Enter Password");
//                    flag = false;
//                }
                 if (Validation.isEmpty(newpassword)) {
                    edtnewpassword.setError("Enter Password");
                    flag = false;
                }
                else if (Validation.isEmpty(newpassword1)) {
                    edtnewpassword1.setError("Enter Password");
                    flag = false;
                }

                else if (newpassword.length() <= 7) {
                    edtnewpassword.setError("Enter password of atleast 8 characters");
                    flag = false;
                }
                else if (!(newpassword1.equals(newpassword))) {
                    edtnewpassword1.setError("Password do not match");
                    flag = false;
                }

                else if(flag)
                {
                    updatepassword();
                }



            }
        });

    }

    private void loadpassword() {


        RequestQueue requestQueue = Volley.newRequestQueue(changepassword.this);
        String url = Constant.URL + "updatepassword/"+userphonenumber+"/"+usertype;

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("passres",response);
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        pass[i] =jsonObject.getString("password");
                        Log.e("passres",pass[i]);
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Cricket","VK"+error.toString());
            }
        });
        //Log.e("passres",pass[0]);

        requestQueue.add(stringRequest);

    }



    private void updatepassword() {

        Log.e("passres",pass[0]+"   pass");
        String password=pass[0];


        String url = Constant.URL + "updatepassword/"+userphonenumber+"/"+usertypeid;
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                    Toast.makeText(changepassword.this, " Password Updated", Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("password",newpassword);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(changepassword.this);
        requestQueue.add(stringRequest);
        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
//                Intent intent=new Intent(changepassword.this,HomeFragment.class);
//                startActivity(intent);
            }
        });
    }
}






