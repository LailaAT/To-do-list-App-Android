package com.example.doitcheckit.Utils;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.example.doitcheckit.Countdown;
import com.example.doitcheckit.R;

public class ViewHolder extends RecyclerView.ViewHolder {

    private RelativeLayout main;
    private Button start;

    public ViewHolder(final View parent){
        super(parent);
        main = (RelativeLayout) parent.findViewById(R.id.taskMain);
        start = (Button) parent.findViewById(R.id.startButton);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(v.getContext(), Countdown.class);
            }
        });
    }
}
