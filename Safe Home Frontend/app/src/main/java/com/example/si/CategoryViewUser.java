package com.example.si;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.si.Adapter.CategoryAdapter;
import com.example.si.Constants.Constant;
import com.example.si.model.Category;
import com.example.si.model.Flat;
import com.example.si.model.UserBlockEveryDay;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CategoryViewUser extends AppCompatActivity {

    ArrayList<Category> categoryArrayList;
    private CategoryAdapter categoryAdapter;
    ListView listView;
    SharedPreferences sharedPreferences;
    String type;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_view_user);
        setTitle("Category");


        listView=findViewById(R.id.lv_category_user);
        categoryArrayList=new ArrayList<>();
        loaddata();
        Log.e("CATEGORY","CATEGORY");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Category category= (Category) parent.getItemAtPosition(position);
                Log.e("CATEGORY",category.getCategoryName() + " " + category.getCategoryId());
                Intent intent=new Intent(CategoryViewUser.this,EverdayActivity.class);
                intent.putExtra("id",category.getCategoryId());
                startActivity(intent);
                finish();

            }
        });


    }





    private void loaddata() {
        RequestQueue requestQueue = Volley.newRequestQueue(CategoryViewUser.this);
        String url = Constant.URL + "getCategory" ;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Category category=new Category();

                        category.setCategoryId(jsonObject.getString("categoryId"));
                        category.setCategoryName(jsonObject.getString("categoryName"));
                        categoryArrayList.add(category);

                    }
                    categoryAdapter = new CategoryAdapter(CategoryViewUser.this, categoryArrayList);
                    listView.setAdapter(categoryAdapter);

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