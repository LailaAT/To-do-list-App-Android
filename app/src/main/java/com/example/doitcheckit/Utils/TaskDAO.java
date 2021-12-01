package com.example.doitcheckit.Utils;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.doitcheckit.Model.ListsModel;
import com.example.doitcheckit.Model.TasksModel;

import java.util.ArrayList;
import java.util.List;

//this class will be responsible for all the data and information that will be saved for the app
public class TaskDAO {

    public static final String TAG = "TaskDAO";

    private SQLiteDatabase db;
    private Database helper;
    private Context mContext;
    private String[] columns = { Database.TASK_ID,      //
            Database.STATUS, //Database.TASK_SUCCESS,
            Database.TASK_NAME, //Database.CATEGORY,
            //Database.TASK_COLOR, Database.USER,
            Database.DURATION };


    public TaskDAO(Context mContext){
        this.mContext = mContext;
        helper = new Database(mContext);

        //attempting to open database
        try{
            open();
        } catch(SQLException e){
            Log.e(TAG, "SQLException on opening database" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void open() throws SQLException {
        db = helper.getWritableDatabase();      //
    }

    public void close(){ helper.close(); }


    public void insertTask(TasksModel task){
        ContentValues cv = new ContentValues();
        cv.put(helper.TASK_NAME, task.getTaskName()); // at: fix
        cv.put(helper.STATUS, 0);
        cv.put(helper.DURATION, task.getDuration());
        db.insertOrThrow(helper.TASK_TABLE, null, cv);
    }

    public List<TasksModel> getAllTasks(){
        List<TasksModel> taskList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        //enables the user to store the data safely
        try{
            cur = db.query(Database.TASK_TABLE, null, null,
                    null, null, null, null, null);
            if(cur != null){
                if(cur.moveToFirst()){
                    do{
                        TasksModel task = new TasksModel();
                        task.setId(cur.getInt(cur.getColumnIndex(Database.TASK_ID)));
                        task.setTaskName(cur.getString(cur.getColumnIndex(Database.TASK_NAME)));
                        task.setDuration(cur.getInt(cur.getColumnIndex(Database.DURATION)));
                        task.setStatus(cur.getInt(cur.getColumnIndex(Database.STATUS)));
                        taskList.add(task);
                    }while(cur.moveToNext());
                }
            }
        } finally {
            db.endTransaction();
            cur.close();
        }
        return  taskList;
    }

    /*public List<TasksModel> getTasksInList(int listId){
        List<TasksModel>  tasksInList = new ArrayList<>();

        Cursor cur = db.query(Database.TASK_TABLE, columns,
                Database.CATEGORY + " = ?",
                new String[] {String.valueOf(listId)}, null, null, null);

        cur.moveToFirst();
        while(!cur.isAfterLast()){
            TasksModel task = cursorToTask(cur);
            tasksInList.add(task);
            cur.moveToNext();
        }
        cur.close();
        return tasksInList;
    }*/

    /*private TasksModel cursorToTask(Cursor cur){
        TasksModel task = new TasksModel();
        task.setId(cur.getInt(0));
        task.setStatus(cur.getInt(1));
        task.setTaskName(cur.getString(3));
        task.setDuration(cur.getLong(7));

        //getting the list by its id
        int listId = (int) cur.getLong(4);
        ListDAO listDAO = new ListDAO(mContext);
        ListsModel list = listDAO.getListById(listId);
        if(list != null)
            task.setCategory(list);

        return task;
    }*/

    public void updateStatus(int id, int status){
        ContentValues cv = new ContentValues();
        cv.put(Database.STATUS, status);
        db.update(Database.TASK_TABLE, cv, Database.TASK_ID + "= ?", new String[] {String.valueOf(id)});
    }

    public void updateTask(int id, String taskName){
        ContentValues cv = new ContentValues();
        cv.put(Database.TASK_NAME, taskName);       // AT: is database properly initialized?
        db.update(Database.TASK_TABLE, cv, Database.TASK_ID + "= ?", new String[] {String.valueOf(id)});
    }

    public void updateDuration(int id, long duration){
        ContentValues cv = new ContentValues();
        cv.put(Database.DURATION, duration);
        db.update(Database.TASK_TABLE, cv, Database.TASK_ID + "= ?", new String[] {String.valueOf(id)});
    }

    public void deleteTask(int id){
        db.delete(Database.TASK_TABLE, Database.TASK_ID + "= ?", new String[] {String.valueOf(id)});
        //this will delete the task
    }



}
