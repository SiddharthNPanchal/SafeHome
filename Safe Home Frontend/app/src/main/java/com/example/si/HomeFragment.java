package com.example.si;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.si.Constants.Constant;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_home, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("USERDATA", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("userName","");
        String userPhoneNumber = sharedPreferences.getString("userPhoneNumber","");
        String secretaryPhoneNUmber = sharedPreferences.getString("secretaryPhoneNumber","");
        loadDataUser(v,username,userPhoneNumber);
        loadNotice(v,secretaryPhoneNUmber);
        CardView cardView = v.findViewById(R.id.crd_notice_fhome);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),NoticeviewActivityUser.class);
                getActivity().startActivity(intent);
            }
        });

        String userToken = sharedPreferences.getString("userToken","");
        if(!userToken.equals(FirebaseInstanceId.getInstance().getToken()))
        {
            userToken(userPhoneNumber);
        }

////        Button changepasswordbutton=v.findViewById(R.id.btnchangepassword);
//        changepasswordbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(getActivity(),changepassword.class);
//                getActivity().startActivity(intent);
//            }
//        });

        return v;
    }

    private void userToken(String userPhoneNumber) {

        String url = Constant.URL + "userToken" +"/" + userPhoneNumber;
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("USERDATA", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("userToken",FirebaseInstanceId.getInstance().getToken());
                editor.commit();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Log.e("FIRE", FirebaseInstanceId.getInstance().getToken());
                Map<String,String> params = new HashMap<>();
                params.put("userToken", FirebaseInstanceId.getInstance().getToken());
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }

    public void loadDataUser(View v,String userName,String userPhoneNumber)
    {
        TextView txtUserName = v.findViewById(R.id.txt_username_fhome);
        TextView txtUserPhoneNumber = v.findViewById(R.id.txt_userphone_fhome);
        txtUserName.setText(userPhoneNumber);
        txtUserPhoneNumber.setText(userName);

    }

    public void loadNotice(View v,String secretaryPhoneNumber)
    {

        final TextView date = v.findViewById(R.id.txt_inoutstatus_factivity);
        final TextView title = v.findViewById(R.id.txt_noticetitle_fhome);
        final TextView message = v.findViewById(R.id.textView5);

        String url = Constant.URL + "noticeLast" + "/" + secretaryPhoneNumber;
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    date.setText(jsonObject.getString("date"));
                    title.setText(jsonObject.getString("title"));
                    message.setText(jsonObject.getString("message"));

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

}