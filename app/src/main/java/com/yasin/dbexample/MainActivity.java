package com.yasin.dbexample;

import android.app.Activity;
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
import android.widget.Toast;

import com.yasin.dbHelper.Person;
import com.yasin.dbHelper.SQLiteHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ArrayList<Map<String, String>> list = new ArrayList<>();
    Person selectedPerson = new Person();
    private SimpleAdapter adapter;
    private ListView listView;

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
        startActivityForResult(intent, IntentCode.EditRequestCode);
    }

    private void clickDel() {
        SQLiteHelper helper = ((PersonApplication)getApplication()).getSQLiteHelper();
        SQLiteDatabase database = helper.open();
        int result = database.delete("persons", "name = ?",
                new String[]{String.valueOf(selectedPerson.name)});
        database.close();
        if (result != 0) {
            listPerson();
            Toast.makeText(this, "已删除ID=" + selectedPerson._id + "的数据", Toast.LENGTH_SHORT).show();
        }
    }

    private void clickEdit() {
        Intent intent = new Intent(this, EditActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("ID", selectedPerson._id);
        bundle.putString("NAME", selectedPerson.name);
        bundle.putString("INFO", selectedPerson.info);
        intent.putExtras(bundle);
        startActivityForResult(intent, IntentCode.EditRequestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == IntentCode.EditRequestCode) {
            if (resultCode == Activity.RESULT_OK) {
                listPerson();
            }
        }
    }

    private void listPerson() {

        SQLiteHelper helper = ((PersonApplication)getApplication()).getSQLiteHelper();
        SQLiteDatabase database = helper.open();
        List<Person> persons = Person.getAll(database);
        database.close();
        helper.close();

        list.clear();
        for (Person person : persons) {
            HashMap<String, String> map = new HashMap<>();
            map.put("name", person.name);
            map.put("info", person.info + " " + person._id);
            list.add(map);
        }

        if (adapter == null) {
            adapter = new SimpleAdapter(this, list,
                    android.R.layout.simple_list_item_2, new String[]{"name", "info"},
                    new int[]{android.R.id.text1, android.R.id.text2});
            listView = findViewById(R.id.listPerson);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    selectedPerson._id = i;
                    selectedPerson.name = list.get(i).get("name");
                    selectedPerson.info = list.get(i).get("info");
                }
            });
        } else {
            listView.setAdapter(adapter);
            Toast.makeText(this, "正在刷新", Toast.LENGTH_SHORT).show();
        }
    }
}
