package com.example.si;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.si.Adapter.UserBlockEverydayAdapter;
import com.example.si.Constants.Constant;
import com.example.si.model.UserBlockEveryDay;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HouseholdFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HouseholdFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HouseholdFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HouseholdFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HouseholdFragment newInstance(String param1, String param2) {
        HouseholdFragment fragment = new HouseholdFragment();
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
    ArrayList<UserBlockEveryDay> userBlockEveryDays = new ArrayList<>();
    ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        ListView listView;
//        ArrayList<UserBlockEveryDay> userBlockEveryDays;
//        SharedPreferences sharedPreferences;
//        SharedPreferences.Editor editor;
//        String blockNumber,flatName,secretaryphonenumber;

        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_activity, container, false);
        listView = v.findViewById(R.id.rv_last_activity_view);
        //helly te page alag inflate karayu che and te id alag page nu lidhu che

        loadData(listView);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                PopupMenu popupMenu = new PopupMenu(getActivity(), view);
                popupMenu.inflate(R.menu.userblockeveryday_delete);
                final UserBlockEveryDay userBlockEveryDay = (UserBlockEveryDay) parent.getItemAtPosition(position);
                final int p = position;
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.userblockeveryday_delete:
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setMessage("Are you sure you want to Delete these member??");
                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        deleteUser(userBlockEveryDay);
                                    }
                                });
                                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                builder.show();

                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
                return true;

            }
        });


        return v;
    }

    private void loadData(final ListView listview) {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("USERDATA", Context.MODE_PRIVATE);
        String blockNumber=sharedPreferences.getString("blockNumber", "");
        String flatName=sharedPreferences.getString("flatName","");
        String secretaryphonenumber=sharedPreferences.getString("secretaryPhoneNumber","");


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String url = Constant.URL + "getuserblockeveryday/"+blockNumber+"/"+flatName+"/"+secretaryphonenumber;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("res",response);
                try {
                    JSONArray jsonArray=new JSONArray(response);

                    for(int i = 0; i<jsonArray.length(); i++)
                    {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        UserBlockEveryDay userBlockEveryDay=new UserBlockEveryDay();

                        userBlockEveryDay.setBlockNumber(jsonObject.getString("blockNumber"));
                        userBlockEveryDay.setFlatName(jsonObject.getString("flatName"));
                        userBlockEveryDay.setSecretaryPhoneNumber(jsonObject.getString("secretaryPhoneNumber"));
                        userBlockEveryDay.setEverydayPhoneNumber(jsonObject.getString("everydayPhoneNumber"));
                        userBlockEveryDay.setName(jsonObject.getString("name"));
                        userBlockEveryDay.setCategoryId(jsonObject.getString("categoryId"));
                        userBlockEveryDay.setCategoryName(jsonObject.getString("categoryName"));
                        userBlockEveryDays.add(userBlockEveryDay);
                    }
                    UserBlockEverydayAdapter userBlockEverydayAdapter=new UserBlockEverydayAdapter(getActivity(),userBlockEveryDays);
                    listview.setAdapter((ListAdapter)userBlockEverydayAdapter);

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

        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_add_everyday_menu:
                Intent intent = new Intent(getActivity(), CategoryViewUser.class);
                startActivity(intent);
//                finish();
                break;

            case R.id.item_logout_menu:
                Constant.logoutuser(getActivity());
                break;
        }
        return true;
    }
    private void deleteUser(final UserBlockEveryDay userBlockEveryDay) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("USERDATA", Context.MODE_PRIVATE);
        String blockNumber=sharedPreferences.getString("blockNumber", "");
        String flatName=sharedPreferences.getString("flatName","");
        String secretaryphonenumber=sharedPreferences.getString("secretaryPhoneNumber","");

        String url = Constant.URL + "getuserblockeveryday/"+blockNumber+"/"+flatName+"/"+secretaryphonenumber+"/"+userBlockEveryDay.getEverydayPhoneNumber();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                userBlockEveryDays.clear();
                loadData(listView);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);




    }


}