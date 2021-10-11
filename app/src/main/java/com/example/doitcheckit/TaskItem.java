package com.example.doitcheckit;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doitcheckit.Model.TasksModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TaskItem extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private Button start;
    private ArrayList<TasksModel> taskList;

    public TaskItem(Context context, ArrayList<TasksModel> taskList){
        this.context = context;
        this.taskList = taskList;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        //return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
    /*public TaskItem(@NonNull @NotNull Context context) {
        super(context);
        start = findViewById(R.id.startButton);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Countdown.class);
                context.startActivity(intent);
            }
        });
    }*/

    //public void openCountdown(){ }

    //private void startActivity(Intent intent) {
        //switch()
    //}
}
