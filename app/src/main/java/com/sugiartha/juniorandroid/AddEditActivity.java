package com.sugiartha.juniorandroid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import com.sugiartha.juniorandroid.utils.FormError;

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
                FormError.hideError(nameEditText, nameTextViewError);
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
                FormError.hideError(addressEditText, addressTextViewError);
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
        boolean isNameEmpty = nameEditText.getText().toString().isEmpty();
        boolean isAddressEmpty = addressEditText.getText().toString().isEmpty();
        Peserta newPeserta = null;
        // error handling
        if (isNameEmpty || isAddressEmpty) {
            if (isNameEmpty) {
                FormError.showError(nameEditText, nameTextViewError);
            }
            if (isAddressEmpty) {
                FormError.showError(addressEditText, addressTextViewError);
            }
        } else {
            // membuat instance peserta
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
        String pesertaName = nameEditText.getText().toString();
        String pesertaAddress = addressEditText.getText().toString();

        Peserta currentPeserta = null;
        if (pesertaName.isEmpty() || pesertaAddress.isEmpty()) {
            if (pesertaName.isEmpty()) {
                FormError.showError(nameEditText, nameTextViewError);
            }
            if (pesertaAddress.isEmpty()) {
                FormError.showError(addressEditText, addressTextViewError);
            }
        } else {
            try {
                int pesertaId = Integer.parseInt(txt_id.getText().toString());
                currentPeserta = new Peserta(pesertaId, pesertaName, pesertaAddress);
            } catch (Exception e) {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }

            // insert data to db
            dbHelper = new DbHelper(AddEditActivity.this);
            if (currentPeserta != null) {
                dbHelper.update(currentPeserta);
            } else {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

                alertDialogBuilder.setTitle("Error");

                // set pesan dari dialog
                alertDialogBuilder
                        .setMessage("Oops something went wrong when editting existing data")
                        .setIcon(R.drawable.ic_digitalent)
                        .setNegativeButton("Cancel", (dialog, id) -> dialog.cancel());

                // membuat alert dialog dari builder
                AlertDialog alertDialog = alertDialogBuilder.create();

                // menampilkan alert dialog
                alertDialog.show();
            }
            startSQLiteActivity();
        }
    }



    private void startSQLiteActivity() {
        Intent i = new Intent(AddEditActivity.this, SQLiteActivity.class);
        startActivity(i);
        finish();
    }
}
