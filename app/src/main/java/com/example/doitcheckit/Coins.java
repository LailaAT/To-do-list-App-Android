package com.example.doitcheckit;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;

import com.example.doitcheckit.Model.TasksModel;

public class Coins {
    public int coins;
    private TasksModel task;
    private Bundle bundle;
    private Intent intent;

    public Coins(int coins, Bundle bundle, Intent intent){
        this.coins = coins;
        this.bundle = bundle;
        this.intent = intent;
    }



    public int getTaskDuration(){
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public int calculateCoins(int duration){
        duration = getTaskDuration();
        int hours = Math.floorDiv(duration,60);
        int halfHours = Math.floorDiv (duration - (60*hours), 30);
        int remainder = duration - (60*hours) - (30*halfHours);
        //if()
        return 0;
    }
}
