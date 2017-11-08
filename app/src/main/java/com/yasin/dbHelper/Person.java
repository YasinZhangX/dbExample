package com.yasin.dbHelper;

import android.database.sqlite.SQLiteDatabase;

/**
 *  database instance
 */

public class Person {
    private int _id;
    public String name;
    public String info;

    public static void create(SQLiteDatabase database) {
        String createTable = "create table if not exists persons ("
                + "_id integer primary key autoincrement, "
                + "name text not null. "
                + "info text not null );";
        database.execSQL(createTable);
    }

    public static void drop(SQLiteDatabase database)
    {
        String dropTable = "drop table if exists persons";
        database.execSQL(dropTable);
    }
}
