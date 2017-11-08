package com.yasin.dbexample;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.yasin.dbHelper.SQLiteHelper;

public class EditActivity extends AppCompatActivity {
    private EditText etName, etInfo;

    public void add(View view) {
        etName = findViewById(R.id.etName);
        etInfo = findViewById(R.id.etInfo);

        String name = etName.getText().toString();
        String info = etInfo.getText().toString();
        try {
            SQLiteHelper helper = ((PersonApplication)getApplication()).getSQLiteHelper();
            SQLiteDatabase database = helper.getWritableDatabase();
            String sql = "insert into persons (name, info) values('" + name +
                    "','" + info + "')";
            database.execSQL(sql);
            Toast.makeText(getApplicationContext(), "新增成功", Toast.LENGTH_LONG).show();
        } catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
