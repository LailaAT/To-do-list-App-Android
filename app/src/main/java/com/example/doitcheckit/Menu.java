package com.example.doitcheckit;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public class Menu extends AppCompatActivity{
    //buttons
    private FloatingActionButton backButton;

    //text
    private EditText lists;
    private EditText stats;
    private EditText shop;
    private EditText priority;
    private EditText settings;
    //each of these refers to the corresponding layout it goes to

    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.menu_settings);

        backButton = findViewById(R.id.backButtonM);
        lists = findViewById(R.id.listMenu);
        stats = findViewById(R.id.statsMenu);
        shop = findViewById(R.id.shopMenu);
        priority = findViewById(R.id.priority);
        settings = findViewById(R.id.settingsMenu);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        lists. setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ListView.class);
                v.getContext().startActivity(intent);
            }
        });

        priority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(v.getContext(),);
                //v.getContext().startActivity(intent);
            }
        });

    }
}
