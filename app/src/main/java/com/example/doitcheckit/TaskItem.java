package com.example.doitcheckit;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class TaskItem extends CardView {
    private Button start;
    public TaskItem(@NonNull @NotNull Context context) {
        super(context);
        start = findViewById(R.id.startButton);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCountdown();
            }
        });
    }

    public void openCountdown(){
        //Intent intent = new Intent(Countdown.class);
        //startActivity(intent);
    }

    private void startActivity(Intent intent) {
    }
}
