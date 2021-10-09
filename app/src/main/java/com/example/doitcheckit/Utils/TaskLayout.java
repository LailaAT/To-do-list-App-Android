package com.example.doitcheckit.Utils;

import android.app.AppComponentFactory;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.doitcheckit.Countdown;
import com.example.doitcheckit.MainActivity;
import com.example.doitcheckit.R;

import org.jetbrains.annotations.NotNull;

import static androidx.core.content.ContextCompat.startActivity;

public class TaskLayout extends CardView {
    private Button start;

    public TaskLayout(@NonNull @NotNull Context context) {
        super(context);
        start = findViewById(R.id.startButton);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //openCountdown();
            }
        });
    }

    /*public void openCountdown(){
        Intent intent = new Intent(this, Countdown.class);
        startActivity(intent);
    }*/
}
