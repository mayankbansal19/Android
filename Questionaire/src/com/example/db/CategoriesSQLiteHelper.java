package com.example.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CategoriesSQLiteHelper extends SQLiteOpenHelper
{

    public static final String  TABLE_NAME   = "comments";
    public static final String  COLUMN_ID        = "_id";
    public static final String  COLUMN_TITLE   = "title";
    public static final String  COLUMN_DESCRIPTION   = "description";
    
    private static final String DATABASE_NAME    = "categories.db";
    private static final int    DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE  = "create table " + TABLE_NAME + "(" + COLUMN_ID
                                                         + " integer primary key autoincrement, " + COLUMN_TITLE + " text not null, " + COLUMN_DESCRIPTION + " text not null)";

    public CategoriesSQLiteHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database)
    {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.w(CategoriesSQLiteHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}