package com.yasin.dbexample;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.yasin.dbHelper.Person;
import com.yasin.dbHelper.SQLiteHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listPerson();
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
        Intent intent = new Intent(this, EditActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("ID", selectedPerson._id);
        bundle.putString("NAME", selectedPerson.name);
        bundle.putString("INFO", selectedPerson.info);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    ArrayList<Map<String, String>> list = new ArrayList<>();
    Person selectedPerson;

    private void listPerson() {
        selectedPerson = new Person();

        SQLiteHelper helper = ((PersonApplication)getApplication()).getSQLiteHelper();
        SQLiteDatabase database = helper.open();
        List<Person> persons = Person.getAll(database);
        helper.close();

        for (Person person : persons) {
            HashMap<String, String> map = new HashMap<>();
            map.put("name", person.name);
            map.put("info", person.info);
            list.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, list,
                android.R.layout.simple_list_item_2, new String[]{"name", "info"},
                new int[]{android.R.id.text1, android.R.id.text2});
        final ListView listView = findViewById(R.id.listPerson);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedPerson._id = i;
                selectedPerson.name = list.get(i).get("name");
                selectedPerson.info = list.get(i).get("info");
            }
        });
    }
}
