package com.example.isaimrafael.kardexv11;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by IsaimRafael on 26/11/2015.
 */
public class LocalDB extends SQLiteOpenHelper {

    private String sql= "CREATE TABLE IF NOT EXISTS temporal (control TEXT, passw TEXT)";

    public LocalDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
