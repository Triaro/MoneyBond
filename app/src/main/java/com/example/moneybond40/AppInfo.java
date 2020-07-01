package com.example.moneybond40;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

public class AppInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Toolbar myToolbar = findViewById(R.id.toolbar3);
        myToolbar.setTitle("App Info");
        myToolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark));
        setSupportActionBar(myToolbar);
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
