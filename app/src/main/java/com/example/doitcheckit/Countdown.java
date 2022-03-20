package com.example.doitcheckit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doitcheckit.Adapter.ToDoAdapter;
import com.example.doitcheckit.Utils.TaskDAO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

public class Countdown extends AppCompatActivity {
    //Objects to retrieve data
    private Bundle bundle;
    private Intent i;
    private Activity activity;

    //attribute for countdown text
    private TextView countdownView;

    //Buttons
    private Button startPause;
    private Button reset; //defining the start/pause + reset button
    private FloatingActionButton backButton;

    private CountDownTimer countdown; //Countdown
    private boolean timerRunning; //if timer is running or not
    //integer values for the timer
    private long startTime;
    private long timeLeft = startTime;
    private long endTime;

    //objects for deleting
    private ToDoAdapter adapter;
    private TaskDAO taskDAO;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.countdown);

        taskDAO = new TaskDAO(this);
        taskDAO.open();
        adapter = new ToDoAdapter(Countdown.this, taskDAO);

        //interface
        countdownView = findViewById(R.id.countdownText);
        startPause = findViewById(R.id.startPauseButton);
        reset = findViewById(R.id.resetButton);
        backButton = findViewById(R.id.backArrowC);
        //id's initially created in interface

        activity = new Activity();

        //duration the countdown will use
        startTime = getTaskDuration() * 60000;//converting it into milliseconds
        timeLeft = startTime;
        updateText();

        startPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timerRunning){
                    pauseTimer();
                    //when the timer is running and pause button appears
                } else{
                    startTimer();
                    //if not then timer continues
                }
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
                //method that will be used to reset timer
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timeLeft < 1000){
                    onFinish(v);
                } else {
                    goBack(v);
                }
            }
        });
    }

    public void goBack(View v){
        Intent intent = new Intent(v.getContext(), MainActivity.class);
        if(timeLeft < 1000){
            //if less than 1 sec left, go back straight away, task is completed
            v.getContext().startActivity(intent);
        } else{
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Go back to Task Overview");
            builder.setMessage("Are you sure you want to go back to the task overview, your task will not be completed." +
                    "\n You will not have coins taken away if you go back.");
            builder.setPositiveButton("Yes, go back",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            v.getContext().startActivity(intent);
                            //goes back to task overview
                        }
                    });
            builder.setNegativeButton(android.R.string.cancel,new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog dialog = builder.create();
            //creates warning message and shows it
            dialog.show();
        }
    }

    public int getTaskDuration(){
       i = getIntent();
       bundle = i.getExtras();
       int time = bundle.getInt("duration");
       return time;
    }

    public int getTaskPosition(){
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        int position = bundle.getInt("position");
        return position;
    }

    public void setTime(long milliseconds){
        startTime  = milliseconds;
        resetTimer();
        closeKeyboard();
    }

    private void startTimer(){
        endTime = System.currentTimeMillis() + timeLeft;
        countdown = new CountDownTimer(timeLeft, 1000) {
            //timer goes down by 1 second
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                updateText();
                //calls the method to update the text
            }
            @Override
            public void onFinish() {
                timerRunning = false;
                updateInterface();
            }
        }.start();
        timerRunning = true;
        updateInterface();
        //reset button will not be shown while timer is running
    }

    @Override
    protected void onStop(){
        super.onStop();
        pauseTimer();
        Toast.makeText(this, "Leaving app will stop countdown", Toast.LENGTH_SHORT).show();
    }

    private void pauseTimer(){
        countdown.cancel();
        timerRunning = false;
        updateInterface();
        //if pause is pressed then user can continue or reset
    }

    private void resetTimer(){
        timeLeft = startTime;
        updateText();
        updateInterface();
    }


    public void onFinish(View v){
        final int position = getTaskPosition();
        Intent intent = new Intent(v.getContext(), MainActivity.class);
        AlertDialog.Builder builder = new AlertDialog.Builder(adapter.getContext());
        builder.setTitle("Task finished, do you want to delete it?");
        builder.setMessage("Task is finished, do you want to delete the task?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                adapter.deleteItem(position);
                v.getContext().startActivity(intent);
                //adapter.notifyItemChanged(getTaskPosition());
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                v.getContext().startActivity(intent);
            }
        });
        AlertDialog dialog = builder.create();
        //creates warning message and shows it
        dialog.show();
    }

    private void updateText(){
        int hours = (int) (timeLeft/1000)/3600;
        //calculating hours
        int min = (int) ((timeLeft/1000)%3600)/60;
        //turns milliseconds to seconds then to minutes
        int sec = (int) (timeLeft/1000)%60;
        //returns what is left after calculating minutes
        String timeFormat;
        if(hours > 0){
            timeFormat = String.format(Locale.getDefault(),"%d:%02d:%02d",hours ,min, sec);
            //setting the format of the text that will appear
        } else{
            timeFormat = String.format(Locale.getDefault(),"%02d:%02d",min,sec);
        }
        countdownView.setText(timeFormat);
        //updates countdown text
    }

    private void updateInterface(){
        if(timerRunning){
           //reset button not needed when countdown is running
            reset.setVisibility(View.INVISIBLE);
            startPause.setText("Pause");
            //the user can only pause timer while it's running
        } else{
            startPause.setText("Start");
            if(timeLeft < 1000){
                startPause.setVisibility(View.INVISIBLE);
                //if only one second is left then start and pause button will be invisible
            } else{
                startPause.setVisibility(View.VISIBLE);
            }

            if(timeLeft < startTime){
                reset.setVisibility(View.VISIBLE);
                //if user hasn't started the timer then reset button will not appear
            } else{
                reset.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void closeKeyboard(){
        View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
}
