package com.example.doitcheckit;

import android.os.Bundle;
import android.widget.ExpandableListView;

import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity {
    private ExpandableListView settingsView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        settingsView = findViewById(R.id.expand_listView);
    }

}
