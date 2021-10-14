package com.example.doitcheckit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.doitcheckit.Adapter.ToDoAdapter;
import com.example.doitcheckit.Model.TasksModel;
import com.example.doitcheckit.Utils.Database;
import com.example.doitcheckit.Utils.TaskDAO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogCloseListener{
    private RecyclerView tasksRecyclerView;

    private ToDoAdapter taskAdapter;

    private FloatingActionButton addtaskButton;
    private Button start;
    private List<TasksModel>taskList;

    private Database db;
    private TaskDAO taskDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        /*start = (Button) findViewById(R.id.startButton);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCountdown();
            }
        });*/

        db = new Database(this);
        taskDAO = new TaskDAO(this);
        taskDAO.open();

        taskList = new ArrayList<>();
        //initializing task list
        tasksRecyclerView = findViewById(R.id.tasksoverview);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        taskAdapter = new ToDoAdapter(MainActivity.this, db, taskDAO, start);
        tasksRecyclerView.setAdapter(taskAdapter);
        //setting adapter to recycler view

        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new ItemHelper(taskAdapter));
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView);

        taskList = taskDAO.getAllTasks();
        Collections.reverse(taskList);

        taskAdapter.setTask(taskList);

        addtaskButton = findViewById(R.id.addTask);
        addtaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTask.newInstance().show(getSupportFragmentManager(), AddTask.TAG);
                //Intent intent = new Intent(v.getContext(), AddTask.class);
            }
        });

        start = findViewById(R.id.startButton);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Countdown.class);
            }
        });

    }


    public void openCountdown(){
        Intent intent = new Intent(MainActivity.this, Countdown.class);
        startActivity(intent);
    }
    @Override
    public void handleDialogClose(DialogInterface dialog){
        taskList = taskDAO.getAllTasks();
        Collections.reverse(taskList);
        taskAdapter.setTask(taskList);
        taskAdapter.notifyDataSetChanged();
    }
}

