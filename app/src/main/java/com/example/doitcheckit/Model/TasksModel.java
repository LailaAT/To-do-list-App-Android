package com.example.doitcheckit.Model;
public class TasksModel {
    private int id, status; //taskSuccess;
    //the id which we will refer to in the database
    //status is a type of boolean variable but we can't use a boolean data type in this case
    //so we will use 0 for false and 1 for true
    private String taskName; //color, user;

    //private ListsModel category;
    //the category will be the list the task is added to
    private long duration;
    //whether a task was completed or not
    //true for completed


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

    public void setDuration(long duration){ this.duration = duration; }

    public long getDuration(){ return this.duration; }

    //public ListsModel getCategory() { return category; }

    //public void setCategory(ListsModel category) { this.category = category; }

    //public int getTaskSuccess() { return taskSuccess; }

    //public void setTaskSuccess(int taskSuccess) { this.taskSuccess = taskSuccess; }

    //public String getColor() { return color; }

    //public void setColor(String color) { this.color = color; }

    //public String getUser() { return user; }

    //public void setUser(String user) { this.user = user; }
}

