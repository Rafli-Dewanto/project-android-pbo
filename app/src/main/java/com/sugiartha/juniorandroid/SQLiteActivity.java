package com.sugiartha.juniorandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.sugiartha.juniorandroid.adapter.Adapter;
import com.sugiartha.juniorandroid.helper.DbHelper;
import com.sugiartha.juniorandroid.model.Peserta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SQLiteActivity extends AppCompatActivity {

    ListView listView;
    AlertDialog.Builder dialog;
    List<Peserta> itemList;
    Adapter adapter;
    DbHelper SQLite;

    public static final String TAG_ID = "id";
    public static final String TAG_NAME = "name";
    public static final String TAG_ADDRESS = "address";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Tambah SQLite
        SQLite = new DbHelper(SQLiteActivity.this);

        //Tambah List View
        listView = findViewById(R.id.list_view);
        itemList = new ArrayList<>();

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(view -> {
            //Tambah Intent untuk pindah ke halaman Add dan Edit
            Intent intent = new Intent(SQLiteActivity.this, AddEditActivity.class);
            startActivity(intent);
        });

        //Tambah adapter dan listview
        adapter = new Adapter(SQLiteActivity.this, itemList);
        listView.setAdapter(adapter);

        // tekan lama daftar listview untuk menampilkan edit dan hapus
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view,
                                           final int position, long id) {
                // TODO Auto-generated method stub
                final int idx = itemList.get(position).getId();
                final String name = itemList.get(position).getName();
                final String address = itemList.get(position).getAddress();

                final CharSequence[] dialogitem = {"Edit", "Delete"};
                dialog = new AlertDialog.Builder(SQLiteActivity.this);
                dialog.setCancelable(true);
                dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        switch (which) {
                            case 0:
                                Intent intent = new Intent(SQLiteActivity.this, AddEditActivity.class);
                                intent.putExtra(TAG_ID, idx);
                                intent.putExtra(TAG_NAME, name);
                                intent.putExtra(TAG_ADDRESS, address);
                                startActivityForResult(intent, 1);
                                break;
                            case 1:
                                SQLite.delete(idx);
                                itemList.clear();
                                getAllData();
                                break;
                        }
                    }
                }).show();
                return false;
            }
        });
        getAllData();
    }

    private void getAllData() {
        List<Peserta> row = SQLite.getAllData();

        for (int i = 0; i < row.size(); i++) {
            int id = row.get(i).getId();
            String name = row.get(i).getName();
            String address = row.get(i).getAddress();

            Peserta data = new Peserta();

            data.setId(id);
            data.setName(name);
            data.setAddress(address);

            itemList.add(data);
        }
        adapter.notifyDataSetChanged();
    }
}
