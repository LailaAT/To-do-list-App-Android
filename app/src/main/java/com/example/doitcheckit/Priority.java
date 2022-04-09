package com.example.doitcheckit;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doitcheckit.Adapter.ToDoAdapter;
import com.example.doitcheckit.Model.TasksModel;
import com.example.doitcheckit.Utils.TaskDAO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Priority extends AppCompatActivity implements DialogCloseListener {

    private RecyclerView recyclerView;//view for tasks

    private ToDoAdapter toDoAdapter;//will help return the priority tasks

    private FloatingActionButton addtaskButton;
    private FloatingActionButton menuButton;
    private TaskDAO taskDAO;
    private List<TasksModel> pList; //list that will contain all tasks
    private List<TasksModel> priorityList;//list that will contain priority tasks


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);//defining the layout

        taskDAO = new TaskDAO(this);//initializing and opening the database
        taskDAO.open();

        pList = new ArrayList<>();//initializing array

        recyclerView = findViewById(R.id.tasksoverview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        toDoAdapter = new ToDoAdapter(Priority.this, taskDAO);
        recyclerView.setAdapter(toDoAdapter);

        pList = taskDAO.getAllTasks();
        priorityList = new ArrayList<>();
        for(TasksModel task : pList){
            int status = task.getStatus();
            if(status == 1){
                priorityList.add(task);
            }
        }
        toDoAdapter.setTask(priorityList);
    }

    public List<TasksModel> getpList(){
        List<TasksModel> tempList = new ArrayList<>();
        List<TasksModel> priorityList = new ArrayList<>();
        taskDAO = new TaskDAO(this);
        taskDAO.open();
        tempList = taskDAO.getAllTasks();
        for(TasksModel task : tempList){
            int status = task.getStatus();
            if(status == 1){
                priorityList.add(task);
            }
        }
        return priorityList;
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        priorityList = getpList();
        toDoAdapter.setTask(priorityList);
        toDoAdapter.notifyDataSetChanged();
    }
}
