package com.example.doitcheckit.Utils;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class UtilDAO {
    public static final String TAG = "UtilDAO";

    private SQLiteDatabase db;
    private Database helper;
    private Context mContext;
    private String[] columns = { Database.USER_ID,
            Database.COINS, Database.USER_NAME,
            Database.USER_PASS };

    public UtilDAO(Context mContext){
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

    public void open() throws SQLException { db = helper.getWritableDatabase(); }

    public void close(){ helper.close(); }

}
