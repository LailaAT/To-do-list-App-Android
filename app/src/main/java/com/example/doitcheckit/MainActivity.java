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
import android.widget.RelativeLayout;
import android.widget.TextView;

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
    private FloatingActionButton menuButton;
    private TextView coinT;
    private List<TasksModel>taskList;


    private Database db;
    private TaskDAO taskDAO;

    private int coins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new Database(this);
        taskDAO = new TaskDAO(this);
        taskDAO.open();

        taskList = new ArrayList<>();
        //initializing task list
        tasksRecyclerView = findViewById(R.id.tasksoverview);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        taskAdapter = new ToDoAdapter(MainActivity.this, taskDAO);
        tasksRecyclerView.setAdapter(taskAdapter);
        //setting adapter to recycler view

        //adding the swiping feature to tasks
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
            }
        });

        //menu button takes the user to the menu bar
        menuButton = findViewById(R.id.menuTO);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),Menu.class);
                v.getContext().startActivity(intent);
            }
        });

    }

    public List<TasksModel> priorityTasks(){
        List<TasksModel> priorityTasksList = new ArrayList<>();
        taskList = taskDAO.getAllTasks();
        for(TasksModel item : taskList){
            if(item.getStatus() == 1){
                priorityTasksList.add(item);
            }
        }
        return priorityTasksList;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        // Save the user's current game state
        savedInstanceState.putInt(Database.COINS, coins);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void handleDialogClose(DialogInterface dialog){
        taskList = taskDAO.getAllTasks();
        Collections.reverse(taskList);
        taskAdapter.setTask(taskList);
        taskAdapter.notifyDataSetChanged();
    }
}

