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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.si.Constants.Constant;
import com.example.si.Constants.Validation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

public class LoginOTPActivity extends AppCompatActivity {


    EditText edtPhone;
    EditText edtOtp;
    Button btnSend, btnLogin;
    FirebaseAuth firebaseAuth;
    String codeSent;
    RadioGroup radioGroup;
    RadioButton rbSecretary, rbUser, rbWatchman, radioButton;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_otp);
        setTitle("Login");

        sharedPreferences = getSharedPreferences("SI", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        codeSent = "";

        radioGroup = findViewById(R.id.rg_type_otp);
        rbSecretary = findViewById(R.id.rb_secretary_login);
        rbUser = findViewById(R.id.rb_user_login);
        rbWatchman = findViewById(R.id.rb_watchman_login);


        edtPhone = findViewById(R.id.edt_phone_otp);
        edtOtp = findViewById(R.id.edt_otp_otp);

        firebaseAuth = FirebaseAuth.getInstance();

        btnSend = findViewById(R.id.btn_send_otp);
        btnLogin = findViewById(R.id.btn_login_otp);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = edtPhone.getText().toString();
                if (Validation.isEmpty(phoneNumber)) {
                    edtPhone.setError("Enter Phone Number");
                } else {
                    Log.e("OTP", "SENDING");
                    sendVerificationCode();
                }
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = edtOtp.getText().toString();
                if (Validation.isEmpty(code)) {
                    edtOtp.setError("Enter OTP");
                } else {
                    verifySignInCode();
                }
            }
        });

    }

    private void verifySignInCode() {
        String otp = edtOtp.getText().toString();
        if(codeSent == "")
        {
            Toast.makeText(this, "Code Not Sent", Toast.LENGTH_SHORT).show();
            return;
        }
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, otp);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            redirect();
                            Toast.makeText(LoginOTPActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginOTPActivity.this, "Invalid code", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void redirect()
    {
        Log.e("OTP","FUNCTION CALLED");
        int id = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(id);
        Log.e("OTP", "" + radioButton.getText().toString());
        String typeUser = "";
        if(radioButton.getText().toString().equalsIgnoreCase("resident"))
        {
            typeUser = "user";
        }
        else
        {
            typeUser = "secretary";
        }
        final String txtRadio = typeUser;
        RequestQueue requestQueue = Volley.newRequestQueue(LoginOTPActivity.this);
        String p = edtPhone.getText().toString();
        String URL = Constant.URL + "userss/" + p +"/" + txtRadio;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if(jsonArray.length() == 0)
                    {
                        Log.e("OTP","IF");
                        Toast.makeText(LoginOTPActivity.this, txtRadio+" Not Registered", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Log.e("OTP","ELSE");
                        String userPhoneNumber = "", userName = "", userTypeId = "";
                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                        userPhoneNumber = jsonObject1.getString("userPhoneNumber");
                        userName = jsonObject1.getString("userName");
                        userTypeId = jsonObject1.getString("userTypeId");
                        String type = jsonObject1.getString("name");
                        editor.putString("userPhoneNumber", userPhoneNumber);
                        editor.putString("userName", userName);
                        editor.putString("userTypeId", userTypeId);
                        editor.putString("type",type);
                        editor.commit();
                        Log.e("OTP","SUCCESS");
                        Intent intent;
                        if (type.equals("secretary")) {
                            intent = new Intent(LoginOTPActivity.this, SecretaryActivity.class);
                        } else if (type.equals("user")) {
                            intent = new Intent(LoginOTPActivity.this, UserActivity.class);
                        } else {
                            intent = new Intent(LoginOTPActivity.this, LoginActivity.class);
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
                Log.e("OTP","ERROR RESPONSE");
                Log.e("OTP",""+error.toString());

            }
        });
        requestQueue.add(stringRequest);
    }

    private void sendVerificationCode() {

        String phoneNumber = edtPhone.getText().toString();
        if (phoneNumber.length() != 10) {
            edtPhone.setError("Enter Valid PhoneNumber");
            return;
        }
        Log.e("OTP", "AFTER VERIFICATION");
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + phoneNumber,        // Phone number to verify
                120,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        FirebaseAuthSettings firebaseAuthSettings = firebaseAuth.getFirebaseAuthSettings();
        firebaseAuthSettings.setAutoRetrievedSmsCodeForPhoneNumber("+91" + phoneNumber, codeSent);
    }


    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            signInWithPhoneAuthCredential(phoneAuthCredential);
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            if (e instanceof FirebaseException) {
                e.printStackTrace();
                e.getMessage();
                Log.e("Firebase",e.getMessage());
                Toast.makeText(LoginOTPActivity.this, "Verification failed", Toast.LENGTH_SHORT).show();
            }
            try{
                if(e instanceof FirebaseException)
                {
                    throw new Exception("Something went wrong");
                }
            }
            catch(Exception f){

            }
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            Log.e("OTP", "SENT");
            codeSent = s;
        }
    };


}
