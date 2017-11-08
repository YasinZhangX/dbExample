package com.yasin.dbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.yasin.dbexample.R;

/**
 * SQLiteHelper类
 * 用于管理SQLite
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "persondata.db";
    private static final int DATABASE_VERSION = 1;
    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Person.create(sqLiteDatabase);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        db.execSQL("pragma foreign_keys=ON;");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Person.drop(sqLiteDatabase);
        onCreate(sqLiteDatabase);
    }

    private SQLiteDatabase open() { return getWritableDatabase(); }

    public void create() { open(); }
}
