package com.example.si;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.si.Constants.Constant;

public class WatchmanHomeActivity extends AppCompatActivity {

    CardView inentry, outentry, makenewentry, inoutentry;
    Button logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.watchman_home);
        logout=findViewById(R.id.btn_logout_watchmanhome);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.logoutuser(WatchmanHomeActivity.this);
            }
        });


        inentry = findViewById(R.id.inentry_crd_watchmanhome);
        outentry = findViewById(R.id.outentry_crd_watchmanhome);
        inoutentry = findViewById(R.id.inoutentry_crd_watchmanhome);
        makenewentry = findViewById(R.id.makenew_crd_watchmanhome);
        inentry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WatchmanHomeActivity.this, WatchmanInEntry.class);
                startActivity(intent);
            }
        });
        outentry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WatchmanHomeActivity.this, WatchmanOutEntry.class);
                startActivity(intent);
            }
        });
        inoutentry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WatchmanHomeActivity.this, WatchmanInOutEntry.class);
                startActivity(intent);
            }
        });
        makenewentry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WatchmanHomeActivity.this, WatchmanNewInEntry.class);
                startActivity(intent);
            }
        });
    }
}

