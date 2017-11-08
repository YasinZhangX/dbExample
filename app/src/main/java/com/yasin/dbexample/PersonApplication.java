package com.yasin.dbexample;

import android.app.Application;

import com.yasin.dbHelper.SQLiteHelper;

public class PersonApplication extends Application {

    private SQLiteHelper helper;

    public SQLiteHelper getSQLiteHelper() {
        if (helper == null) {
            helper = new SQLiteHelper(this);
        }
        return helper;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getSQLiteHelper().create();
    }
}
