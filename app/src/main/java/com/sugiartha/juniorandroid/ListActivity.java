package com.sugiartha.juniorandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.sugiartha.juniorandroid.adapter.NegaraAdapter;
import com.sugiartha.juniorandroid.utils.ActivityUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    //mendeklarasikan listview var dan menginisialasi array tipe data string
    RecyclerView lvItem;
    List<String> namanegara;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ActivityUtils.setAppBar(ListActivity.this, MainActivity.class, "Negara List View");

        namanegara = new ArrayList<>(Arrays.asList(
                "Indonesia", "Malaysia", "Singapore", "Italia", "Inggris", "Belanda",
                "Argentina", "Chile", "Mesir", "Uganda", "Australia", "Canada",
                "Brazil", "Germany", "France", "Spain", "Japan", "South Africa",
                "Thailand", "Mexico", "India", "China", "Russia", "Egypt", "New Zealand"));
        lvItem = findViewById(R.id.rv_negara);
        NegaraAdapter adapter = new NegaraAdapter(namanegara, data -> Toast.makeText(ListActivity.this, "Anda memilih " + data , Toast.LENGTH_LONG).show());
        lvItem.setLayoutManager(new LinearLayoutManager(this));
        lvItem.setAdapter(adapter);
    }
}
