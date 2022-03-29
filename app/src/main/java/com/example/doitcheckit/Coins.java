package com.example.doitcheckit;

import android.content.Intent;
import android.os.Bundle;

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

    public int calculateCoins(int duration){
        duration = getTaskDuration();

        return 0;
    }
}
