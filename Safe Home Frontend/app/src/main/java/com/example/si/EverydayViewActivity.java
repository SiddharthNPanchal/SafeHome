package com.example.si;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.si.Adapter.EverydayViewAdapter;
import com.example.si.model.Everyday;

import java.util.ArrayList;

public class EverydayViewActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EverydayViewAdapter everydayViewAdapter;
    String id;
    CardView card;
//    ListView listView;
    ArrayList<Everyday> everydayArrayList;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String secretaryPhoneNumber,blockNumber,flatName,everydayphone;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_container);

        Intent intent=getIntent();
        id=intent.getStringExtra("id");
        everydayArrayList=new ArrayList<>();
        card=findViewById(R.id.crdview_everydayview);
        sharedPreferences = getSharedPreferences("USERDATA",MODE_PRIVATE);
        blockNumber = sharedPreferences.getString("blockNumber", "");
        flatName=sharedPreferences.getString("flatName","");
        secretaryPhoneNumber = sharedPreferences.getString("secretaryPhoneNumber","");

       setTitle("Household");
        recyclerView=findViewById(R.id.rv_recyclerviewcontainer);
        everydayViewAdapter=new EverydayViewAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(everydayViewAdapter);

    }
}
