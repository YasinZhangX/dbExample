package com.yasin.dbexample;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.yasin.dbHelper.SQLiteHelper;

public class EditActivity extends AppCompatActivity {
    private EditText etName, etInfo;
    private int _id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etName = findViewById(R.id.etName);
        etInfo = findViewById(R.id.etInfo);
        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            _id = bundle.getInt("ID");
            etName.setText(String.format("%s%s", bundle.getString("NAME"),
                    String.valueOf(_id)));
            etInfo.setText(bundle.getString("INFO"));
        }
    }

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
            database.close();
            Intent intent = new Intent();
            setResult(Activity.RESULT_OK, intent);
            finish();
        } catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void update(View view){
        String name = etName.getText().toString();
        String info = etInfo.getText().toString();
        try {
            SQLiteHelper helper = ((PersonApplication)getApplication()).getSQLiteHelper();
            SQLiteDatabase database = helper.open();
            ContentValues values = new ContentValues();
            values.put("_id", _id);
            values.put("name", name);
            values.put("info", info);
            if (database.update("persons", values, "name=?", new String[]{String.valueOf(name)}) > 0) {
                database.close();
                Toast.makeText(getApplicationContext(), "成功", Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
