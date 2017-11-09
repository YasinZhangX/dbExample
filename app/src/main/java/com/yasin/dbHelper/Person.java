package com.yasin.dbHelper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 *  database instance
 */

public class Person {
    public int _id;
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

    public static List<Person> getAll(SQLiteDatabase database) {
        ArrayList<Person> list = new ArrayList<>();
        String[] projection = {"_id", "name", "info"};
        String selection = null;
        String[] selectionArgs = null;
        String sortOrder = "_id DESC";
        Cursor c = database.query("person", projection, selection, selectionArgs,
                null, null, sortOrder);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            Person person = cursorToPerson(c);
            list.add(person);
            c.moveToNext();
        }
        c.close();
        return list;
    }

    private static Person cursorToPerson(Cursor c) {
        Person person = new Person();
        person._id = c.getInt(c.getColumnIndex("_id"));
        person.name = c.getString(c.getColumnIndex("name"));
        person.info = c.getString(c.getColumnIndex("info"));
        return person;
    }
}
