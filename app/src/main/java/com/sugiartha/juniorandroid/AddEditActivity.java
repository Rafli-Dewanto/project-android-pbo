package com.sugiartha.juniorandroid;

import androidx.appcompat.app.AppCompatActivity;

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

public class AddEditActivity extends AppCompatActivity {

    EditText txt_id, nameEditText, addressEditText;
    TextView nameTextViewError, addressTextViewError;
    Button submitButton, cancelButton;
    DbHelper SQLite = new DbHelper(this);
    String id, name, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txt_id = (EditText) findViewById(R.id.txt_id);
        nameEditText = (EditText) findViewById(R.id.txt_name);
        addressEditText = (EditText) findViewById(R.id.txt_address);
        submitButton = (Button) findViewById(R.id.btn_submit);
        cancelButton = (Button) findViewById(R.id.btn_cancel);

        nameTextViewError = findViewById(R.id.tv_name_error);
        addressTextViewError = findViewById(R.id.tv_address_error);

        id = getIntent().getStringExtra(SQLiteActivity.TAG_ID);
        name = getIntent().getStringExtra(SQLiteActivity.TAG_NAME);
        address = getIntent().getStringExtra(SQLiteActivity.TAG_ADDRESS);

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

        if (id == null || id == "") {
            setTitle("Add Data");
        } else {
            setTitle("Edit Data");
            txt_id.setText(id);
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
        switch (item.getItemId()) {
            case android.R.id.home:
                blank();
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        if (String.valueOf(nameEditText.getText()).equals(null) || String.valueOf(nameEditText.getText()).equals("") ||
                String.valueOf(addressEditText.getText()).equals(null) || String.valueOf(addressEditText.getText()).equals("")) {

            if (nameEditText.getText().toString().equals("") && addressEditText.getText().toString().equals("")) {
                nameEditText.setBackgroundResource(R.drawable.form_edit_error);
                nameTextViewError.setVisibility(View.VISIBLE);

                addressEditText.setBackgroundResource(R.drawable.form_edit_error);
                addressTextViewError.setVisibility(View.VISIBLE);
            } else if (nameEditText.getText().toString().equals("")) {
                nameEditText.setBackgroundResource(R.drawable.form_edit_error);
                nameTextViewError.setVisibility(View.VISIBLE);
            } else {
                addressEditText.setBackgroundResource(R.drawable.form_edit_error);
                addressTextViewError.setVisibility(View.VISIBLE);
            }

        } else {
            SQLite.insert(nameEditText.getText().toString().trim(), addressEditText.getText().toString().trim());
            blank();
            finish();
        }
    }

    // Update data kedalam Database SQLite
    private void edit() {
        if (String.valueOf(nameEditText.getText()).equals(null) || String.valueOf(nameEditText.getText()).equals("") ||
                String.valueOf(addressEditText.getText()).equals(null) || String.valueOf(addressEditText.getText()).equals("")) {
            Toast.makeText(getApplicationContext(),
                    "Please input name or address ...", Toast.LENGTH_SHORT).show();
        } else {
            SQLite.update(Integer.parseInt(txt_id.getText().toString().trim()), nameEditText.getText().toString().trim(),
                    addressEditText.getText().toString().trim());
            blank();
            finish();
        }
    }
}
