package com.example.doitcheckit;

import android.app.Activity;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doitcheckit.Adapter.ToDoAdapter;
import com.example.doitcheckit.Model.TasksModel;
import com.example.doitcheckit.Utils.Database;
import com.example.doitcheckit.Utils.TaskDAO;

import java.util.List;
import java.util.Locale;

public class Countdown extends AppCompatActivity {
    //Objects
    private TasksModel task;
    private List<TasksModel> todoList;

    //attribute for countdown text
    private TextView countdownView;

    //Buttons
    private Button startPause;
    private Button reset; //defining the start/pause + reset button

    private CountDownTimer countdown; //Countdown
    private boolean timerRunning; //if timer is running or not
    //integer values for the timer
    private long startTime;
    private long timeLeft = startTime;
    private long endTime;
    //database variables
    private TaskDAO taskDAO;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.countdown);
        //interface
        countdownView = findViewById(R.id.countdownText);
        startPause = findViewById(R.id.startPauseButton);
        reset = findViewById(R.id.resetButton);
        //id's initially created in interface

        //duration the countdown will use
        //setTime(task.getDuration());

        startPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timerRunning){
                    pauseTimer();
                    //when the timer is running and pause button is clicked then stop timer
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

    }

    //public void onStart(){ }


    public void getTaskDuration(int position){
        taskDAO.open();
        TasksModel item = todoList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putLong("duration", item.getDuration());
    }

    public int getDuration(int duration){
      return duration;
    }

    public void setTime(long milliseconds){
        startTime  = milliseconds;
        resetTimer();
        closeKeyboard();
    }


    /*private void setTime(long milliseconds){
        startTime = milliseconds;
        resetTimer();
        //this will update countdown time and text
        closeKeyboard();
    }*/

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

    private void updateText(){
        int hours = (int) (timeLeft/1000)/3600;
        //calculating hours
        int min = (int) ((timeLeft/1000)%3600)/60;
        //turns milleseconds to seconds then to minutes
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
