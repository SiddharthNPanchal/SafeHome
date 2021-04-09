package com.example.si;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.si.Adapter.LastActivityAdapter;
import com.example.si.Constants.Constant;
import com.example.si.model.LastActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ActivityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActivityFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ActivityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ActivityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActivityFragment newInstance(String param1, String param2) {
        ActivityFragment fragment = new ActivityFragment();
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
        View v =  inflater.inflate(R.layout.fragment_activity, container, false);
        ListView rv = v.findViewById(R.id.rv_last_activity_view);

        loadData(rv);

        return v;
    }

    public void loadData(final ListView rv)
    {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("USERDATA", Context.MODE_PRIVATE);
        String sp = sharedPreferences.getString("secretaryPhoneNumber","");
        String up = sharedPreferences.getString("userPhoneNumber","");
        String bn = sharedPreferences.getString("flatName","");
        String fn = sharedPreferences.getString("blockNumber","");

        String url = Constant.URL + "everydayinoutlastactivity" + "/" + sp + "/" + up + "/" + bn + "/" +fn;
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<LastActivity> lastActivities = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    Log.e("everydayinout",response);

                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        LastActivity lastActivity = new LastActivity();
                        lastActivity.setIntime(jsonObject.getString("inTime"));
                        lastActivity.setName(jsonObject.getString("name"));
                        lastActivity.setType(jsonObject.getString("categoryName"));
                        lastActivity.setOuttime(jsonObject.getString("outTime"));
                        lastActivities.add(lastActivity);
                    }
                    LastActivityAdapter lastActivityAdapter = new LastActivityAdapter(getActivity(),lastActivities);
                    rv.setAdapter((ListAdapter) lastActivityAdapter);
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