package com.example.doitcheckit.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database extends SQLiteOpenHelper {

    //task variables
    public static final String TASK_TABLE = "taskTable";
    public static final String TASK_ID = "task_id";
    public static final String STATUS = "status";
    public static final String TASK_SUCCESS = "taskSuccess";
    public static final String TASK_NAME = "taskName";
    public static final String CATEGORY = "category";
    public static final String TASK_COLOR = "color";
    public static final String USER = "user";
    //This will be an id from another table
    public static final String DURATION = "duration";


    //list variables
    public static final String LIST_TABLE = "listTable";
    public static final String LIST_ID = "id";
    public static final String LIST_NAME = "listName";
    public static final String LIST_COLOR = "listColor";

    //Other properties such as database name
    public static final String TAG = "db";
    private static final String NAME = "AppDatabase.db";
    private static final int VERSION = 1;

    //Task table creation
    private static final String CREATE_TASK_TABLE = "CREATE TABLE " + TASK_TABLE + "("
            + TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TASK_NAME + " TEXT NOT NULL, "
            + DURATION + " BIGINT, "
            + CATEGORY + " TEXT, "
            + STATUS + " INTEGER, "
            + TASK_SUCCESS + " INTEGER, "
            + TASK_COLOR + " TEXT, "
            + USER + " INTEGER NOT NULL)";

    //list table creation
    private static final String CREATE_LIST_TABLE = "CREATE TABLE " + LIST_TABLE + "("
            + LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + LIST_NAME + " TEXT NOT NULL, "
            + LIST_COLOR + " TEXT)";


    public Database(Context context){
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TASK_TABLE);
        db.execSQL(CREATE_LIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        Log.w(TAG,
                "Upgrading the database from version " + oldVersion + " to " + newVersion);
        //clearing the data
        db.execSQL("DROP TABLE IF EXISTS " + TASK_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + LIST_TABLE);

        //recreating the table
        onCreate(db);
    }

    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, NAME, factory, VERSION);
    }



}
