package com.example.doitcheckit;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ListView extends AppCompatActivity implements DialogCloseListener {

    private FloatingActionButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lists);

        backButton = findViewById(R.id.backButtonL);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),Menu.class);
                v.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public void handleDialogClose(DialogInterface dialog) {

    }
}
