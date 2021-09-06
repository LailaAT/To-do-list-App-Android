package com.example.doitcheckit.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.recyclerview.widget.RecyclerView;

import com.example.doitcheckit.AddTask;
import com.example.doitcheckit.MainActivity;
import com.example.doitcheckit.Model.TasksModel;
import com.example.doitcheckit.R;
import com.example.doitcheckit.Utils.TaskDAO;
import com.example.doitcheckit.Utils.Database;
import com.example.doitcheckit.Utils.TaskDAO;

import java.util.List;

//import kotlinx.coroutines.scheduling.Task;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {
    private List<TasksModel> todoList;
    //this will be a list of our todo's

    private MainActivity activity;
    //attribute for main activity

    private Database db;
    private TaskDAO taskDAO;
    //defining the database

    public ToDoAdapter(MainActivity activity, Database db) {
        this.activity = activity;
        this.db = db;
    }

    //ViewHolder allows the access of each list item without the need to look up for them
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        taskDAO.open();
        //opening the database
        TasksModel item = todoList.get(position);
        //getting the position of items in a list
        holder.task.setText(item.getTaskName());
        holder.task.setChecked(toBoolean(item.getStatus()));
        //this needs a boolean so we need to create a method to convert status into a boolean

        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    taskDAO.updateStatus(item.getId(), 1);
                } else{
                    taskDAO.updateStatus(item.getId(), 0);
                }
            }
        });
        //this function will update the task status
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    //method to convert status into boolean
    private boolean toBoolean(int n){
        return n != 0;
        //if no. is one it will return true, otherwise it will return false
    }

    public void setTask(List<TasksModel> todoList){
        this.todoList = todoList;
        notifyDataSetChanged();
        //to ensure recycler view is updated
    }

    public Context getContext(){
        return activity;
    }

    //allows you to edit task
    public void editItem(int position){
        TasksModel item = todoList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("task_name", item.getTaskName());
        bundle.putLong("duration",item.getDuration());
        AddTask fragment = new AddTask();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(), AddTask.TAG);
    }


    public void deleteItem(int position){
        TasksModel item = todoList.get(position);
        taskDAO.deleteTask(item.getId());
        todoList.remove(position);
        notifyItemRemoved(position);
        //updates recycler view
    }

    //A recycler view is class that displays a collection of data
    //when you add it it would be like any other UI element
    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox task;
        ViewHolder(View view){
            super(view);
            task = view.findViewById(R.id.todoCheckBox);
        }
    }
}
