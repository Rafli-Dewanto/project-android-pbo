package com.sugiartha.juniorandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sugiartha.juniorandroid.components.DynamicAppBar;

public class NamaActivity extends AppCompatActivity {

    Button btnOk;
    EditText editNama;
    TextView txtHasil, errorTextView;
    DynamicAppBar dynamicAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nama);

        btnOk = findViewById(R.id.btnOk);
        editNama = findViewById(R.id.editNama);
        txtHasil = findViewById(R.id.txtHasil);
        errorTextView = findViewById(R.id.tv_error);

        dynamicAppBar = findViewById(R.id.dynamic_app_bar);
        dynamicAppBar.setTitle("Nama");
        dynamicAppBar.setBackButtonClickListener(v -> {
            Intent i = new Intent(NamaActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        });


        editNama.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    errorTextView.setVisibility(View.GONE);
                    editNama.setBackgroundResource(R.drawable.form_edit);
                }
            }
        });

        btnOk.setOnClickListener(v -> {
            InputMethodManager imm = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            }
            assert imm != null;
            imm.hideSoftInputFromWindow(editNama.getWindowToken(), 0);

            if (editNama.getText().toString().isEmpty()) {
                errorTextView.setVisibility(View.VISIBLE);
                editNama.setBackgroundResource(R.drawable.form_edit_error);
            } else {
                editNama.setBackgroundResource(R.drawable.form_edit);
                errorTextView.setVisibility(View.GONE);
                txtHasil.setText(String.format("Hello %s!\nPeserta VSGA", editNama.getText().toString()));
                txtHasil.setVisibility(View.VISIBLE);
                editNama.setText("");
                editNama.clearFocus();
            }
        });
    }
}
