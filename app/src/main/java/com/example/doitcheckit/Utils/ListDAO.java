package com.example.doitcheckit.Utils;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.doitcheckit.Model.ListsModel;

import java.util.ArrayList;
import java.util.List;

public class ListDAO {

    public static final String TAG = "ListDAO";
    //attributes
    private SQLiteDatabase db;
    private Database helper;
    private Context mContext;
    private String[] columns = { Database.LIST_ID,
            Database.LIST_NAME, Database.LIST_COLOR};

    public ListDAO(Context mContext){
        this.mContext = mContext;
        helper = new Database(mContext);
        try{
            open();
        } catch(SQLException e){
            Log.e(TAG, "SQLException on opening Database" + e.getMessage());
            e.printStackTrace();
        }
    }

    //opening the database
    public void open() throws SQLException{ db = helper.getWritableDatabase(); }

    public void close(){ helper.close(); }

    //creating a list method
    public void insertList(ListsModel list){
        ContentValues cv = new ContentValues();
        cv.put(Database.LIST_NAME, list.getListName());
        cv.put(Database.LIST_COLOR, list.getColor());
        db.insert(Database.LIST_TABLE, null, cv);
    }

    //getting all the lists method
    public List<ListsModel> getAllLists(){
        List<ListsModel> categoryList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try{
            cur = db.query(Database.LIST_TABLE, null, null,
                    null, null, null, null, null);
            if(cur != null){
                do{
                    ListsModel list = new ListsModel();
                    list.setId(cur.getInt(cur.getColumnIndex(Database.LIST_ID)));
                    list.setListName(cur.getString(cur.getColumnIndex(Database.LIST_NAME)));
                    list.setColor(cur.getString(cur.getColumnIndex(Database.LIST_COLOR)));
                    categoryList.add(list);
                }while(cur.moveToNext());
            }
        } finally{
            db.endTransaction();
            cur.close();
        }
        return categoryList;
    }

    //methods to update list details

    public void updateName(int id, String name){
        ContentValues cv = new ContentValues();
        cv.put(Database.LIST_NAME, name);
        db.update(Database.LIST_TABLE, cv, Database.LIST_ID + "= ?", new String[] {String.valueOf(id)});
    }

    public void updateColor(int id, String color){
        ContentValues cv = new ContentValues();
        cv.put(Database.LIST_COLOR, color);
        db.update(Database.LIST_TABLE, cv, Database.LIST_ID + "= ?", new String[] {String.valueOf(id)});
    }

    //method to delete a list
    public void deleteList(int id){
        db.delete(Database.LIST_TABLE, Database.LIST_ID + "= ?", new String[] {String.valueOf(id)});
    }

    //method to get the list by its id

    public ListsModel getListById(int id){
        Cursor cur = db.query(Database.LIST_TABLE, columns, Database.LIST_ID + " = ?",
                new String[] { String.valueOf(id)}, null, null, null);
        if(cur != null){
            cur.moveToFirst();
        }
        ListsModel list = cursorToList(cur);
        return list;
    }

    public ListsModel cursorToList(Cursor cursor){
        ListsModel list = new ListsModel();
        list.setId(cursor.getInt(0));
        list.setListName(cursor.getString(1));
        list.setColor(cursor.getString(2));
        return list;
    }


}
