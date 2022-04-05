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
    private List<TasksModel> priorityList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        taskDAO = new TaskDAO(this);
        taskDAO.open();

        priorityList = new ArrayList<>();

        recyclerView = findViewById(R.id.tasksoverview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        toDoAdapter = new ToDoAdapter(Priority.this, taskDAO);
        recyclerView.setAdapter(toDoAdapter);

        priorityList = toDoAdapter.priorityList();
        toDoAdapter.setTask(priorityList);
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        priorityList = toDoAdapter.priorityList();
        toDoAdapter.setTask(priorityList);
        toDoAdapter.notifyDataSetChanged();
    }
}
