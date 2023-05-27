package com.sugiartha.juniorandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sugiartha.juniorandroid.helper.DbHelper;
import com.sugiartha.juniorandroid.model.Peserta;

public class AddEditActivity extends AppCompatActivity {

    EditText txt_id, nameEditText, addressEditText;
    TextView nameTextViewError, addressTextViewError;
    Button submitButton, cancelButton;
    DbHelper dbHelper;
    String id, name, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txt_id = findViewById(R.id.txt_id);
        nameEditText = findViewById(R.id.txt_name);
        addressEditText = findViewById(R.id.txt_address);
        submitButton = findViewById(R.id.btn_submit);
        cancelButton = findViewById(R.id.btn_cancel);

        nameTextViewError = findViewById(R.id.tv_name_error);
        addressTextViewError = findViewById(R.id.tv_address_error);

        Intent intent = getIntent();
        int id = intent.getIntExtra(SQLiteActivity.TAG_ID, -1);
        String name = intent.getStringExtra(SQLiteActivity.TAG_NAME);
        String address = intent.getStringExtra(SQLiteActivity.TAG_ADDRESS);

        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                nameEditText.setBackgroundResource(R.drawable.form_edit);
                nameTextViewError.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        addressEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                addressEditText.setBackgroundResource(R.drawable.form_edit);
                addressTextViewError.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        if (id == -1) {
            setTitle("Add Data");
        } else {
            setTitle("Edit Data");
            txt_id.setText(String.valueOf(id));
            nameEditText.setText(name);
            addressEditText.setText(address);
        }

        submitButton.setOnClickListener(v -> {
            try {
                if (txt_id.getText().toString().equals("")) {
                    save();
                } else {
                    edit();
                }
            } catch (Exception e) {
                Log.e("Submit", e.toString());
            }
        });

        cancelButton.setOnClickListener(v -> {
            blank();
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            blank();
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Kosongkan semua Edit Teks
    private void blank() {
        nameEditText.requestFocus();
        txt_id.setText(null);
        nameEditText.setText(null);
        addressEditText.setText(null);
    }

    // Menyimpan Data ke Database SQLite
    private void save() {
        Peserta newPeserta = null;
        // error handling
        if (String.valueOf(nameEditText.getText()).equals("") || String.valueOf(addressEditText.getText()).equals("")) {

            if (nameEditText.getText().toString().equals("") && addressEditText.getText().toString().equals("")) {
                showError(true, false);
            } else showError(false, nameEditText.getText().toString().equals(""));

        } else {
            // create an instance of peserta
            try {
                newPeserta = new Peserta(-1, nameEditText.getText().toString(), addressEditText.getText().toString());
            } catch (Exception e) {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }

            // insert data to db
            dbHelper = new DbHelper(AddEditActivity.this);
            if (newPeserta != null) {
                boolean success = dbHelper.insert(newPeserta);

                if (success) {
                    startSQLiteActivity();
                }
            }
        }
    }

    // Update data kedalam Database SQLite
    private void edit() {
        Peserta currentPeserta = null;
        if (String.valueOf(nameEditText.getText()).equals("") || String.valueOf(addressEditText.getText()).equals("")) {

            if (nameEditText.getText().toString().equals("") && addressEditText.getText().toString().equals("")) {
                showError(true, false);
            } else showError(false, nameEditText.getText().toString().equals(""));

        } else {
            try {
                currentPeserta = new Peserta(Integer.parseInt(txt_id.getText().toString()), nameEditText.getText().toString(), addressEditText.getText().toString());
            } catch (Exception e) {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }

            // insert data to db
            dbHelper = new DbHelper(AddEditActivity.this);
            dbHelper.update(currentPeserta);
            startSQLiteActivity();
        }
    }

    private void showError(
            boolean nameAndAddress,
            boolean username
    ) {
        if (nameAndAddress) {
            nameEditText.setBackgroundResource(R.drawable.form_edit_error);
            nameTextViewError.setVisibility(View.VISIBLE);
            addressEditText.setBackgroundResource(R.drawable.form_edit_error);
            addressTextViewError.setVisibility(View.VISIBLE);
        } else if (username) {
            nameEditText.setBackgroundResource(R.drawable.form_edit_error);
            nameTextViewError.setVisibility(View.VISIBLE);
        } else {
            addressEditText.setBackgroundResource(R.drawable.form_edit_error);
            addressTextViewError.setVisibility(View.VISIBLE);
        }
    }

    private void startSQLiteActivity(){
        Intent i = new Intent(AddEditActivity.this, SQLiteActivity.class);
        startActivity(i);
        finish();
    }
}
