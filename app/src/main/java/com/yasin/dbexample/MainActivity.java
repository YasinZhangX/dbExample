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
    private RefreshableView refreshableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refreshableView = (RefreshableView) findViewById(R.id.refreshable_view);
        refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                //listPerson();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                refreshableView.finishRefreshing();
            }
        }, 0);

        //确定已创建数据库
        SQLiteHelper helper = ((PersonApplication)getApplication()).getSQLiteHelper();
        SQLiteDatabase database = helper.open();
        database.close();

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
        startActivityForResult(intent, IntentCode.EditRequestCode);
    }

    private void clickDel() {
        if (selectedPerson != null) {
            SQLiteHelper helper = ((PersonApplication) getApplication()).getSQLiteHelper();
            SQLiteDatabase database = helper.open();
            int result = database.delete("persons", "_id = ?",
                    new String[]{String.valueOf(selectedPerson._id)});
            database.close();
            if (result != 0) {
                listPerson();
                Toast.makeText(this, "已删除ID=" + selectedPerson._id + "的数据", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "请先选择数据", Toast.LENGTH_SHORT).show();
        }
    }

    private void clickEdit() {
        if (selectedPerson != null) {
            Intent intent = new Intent(this, EditActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("ID", selectedPerson._id);
            bundle.putString("NAME", selectedPerson.name);
            bundle.putString("INFO", selectedPerson.info);
            intent.putExtras(bundle);
            startActivityForResult(intent, IntentCode.EditRequestCode);
        } else {
            Toast.makeText(this, "请先选择数据", Toast.LENGTH_SHORT).show();
        }
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
            map.put("name", person._id + ":" + person.name);
            map.put("info", person.info);
            list.add(map);
        }

        if (persons.size() == 0)
            Toast.makeText(this, "请先新增数据", Toast.LENGTH_SHORT).show();

        if (adapter == null) {
            adapter = new SimpleAdapter(this, list,
                    android.R.layout.simple_list_item_2, new String[]{"name", "info"},
                    new int[]{android.R.id.text1, android.R.id.text2});
            listView = findViewById(R.id.listPerson);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    selectedPerson._id = Integer.valueOf(list.get(i).get("name").split(":")[0]);
                    selectedPerson.name = list.get(i).get("name").split(":")[1];
                    selectedPerson.info = list.get(i).get("info");
                }
            });
        } else {
            listView.setAdapter(adapter);
            Toast.makeText(this, "正在刷新", Toast.LENGTH_SHORT).show();
        }
    }
}
