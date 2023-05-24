package com.sugiartha.juniorandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NamaActivity extends AppCompatActivity {

    Button btnOk;
    EditText editNama;
    TextView txtHasil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nama);

        btnOk = (Button) findViewById(R.id.btnOk);
        editNama = (EditText) findViewById(R.id.editNama);
        txtHasil = (TextView) findViewById(R.id.txtHasil);

        btnOk.setOnClickListener(v -> {
            InputMethodManager imm = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            }
            assert imm != null;
            imm.hideSoftInputFromWindow(editNama.getWindowToken(), 0);
            txtHasil.setText(String.format("Hello %s!\nPeserta VSGA", editNama.getText().toString()));
        });
    }
}
