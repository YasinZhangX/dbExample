package com.yasin.dbexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.menu_add:
                clickAdd();
                return true;

            case R.id.menu_del:
                clickDel();
                return true;

            case R.id.menu_edit:
                clickEdit();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void clickAdd() {
        Intent intent = new Intent(this, EditActivity.class);
        startActivity(intent);
    }

    private void clickDel() {

    }

    private void clickEdit() {

    }
}
