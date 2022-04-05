package com.example.doitcheckit.Model;
public class TasksModel {
    private int id, status; //unique id, primary key for task.
    //Status is whether the task is high priority
    private String taskName; //the name of the task
    private ListsModel category; //the category a task belongs to
    private int duration; //duration of a task will be an integer value
    //private int taskSuccess; (whether a task is completed successfully)

    //Getters and setters for each attribute
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String task) {
        this.taskName = task;
    }

    public void setDuration(int duration){ this.duration = duration; }

    public int getDuration(){ return this.duration; }

    public ListsModel getCategory() { return category; }

    public void setCategory(ListsModel category) { this.category = category; }
}

