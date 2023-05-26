package com.sugiartha.juniorandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NamaActivity extends AppCompatActivity {

    Button btnOk;
    EditText editNama;
    TextView txtHasil, errorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nama);

        btnOk = (Button) findViewById(R.id.btnOk);
        editNama = (EditText) findViewById(R.id.editNama);
        txtHasil = (TextView) findViewById(R.id.txtHasil);
        errorTextView = (TextView) findViewById(R.id.tv_error);


        editNama.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0 && errorTextView.getVisibility() != View.VISIBLE) {
                    errorTextView.setVisibility(View.VISIBLE);
                } else if (charSequence.length() > 0 && errorTextView.getVisibility() == View.VISIBLE) {
                    errorTextView.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    errorTextView.setVisibility(View.GONE);
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
            } else {
                errorTextView.setVisibility(View.GONE);
                txtHasil.setText(String.format("Hello %s!\nPeserta VSGA", editNama.getText().toString()));
                txtHasil.setVisibility(View.VISIBLE);
            }
        });
    }
}
