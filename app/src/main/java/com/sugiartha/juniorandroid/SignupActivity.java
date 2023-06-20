package com.sugiartha.juniorandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sugiartha.juniorandroid.helper.AuthDao;
import com.sugiartha.juniorandroid.model.Auth;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class SignupActivity extends AppCompatActivity {

    Spinner spinner;
    String gender;
    EditText fullname, username, password;
    TextView loginTextView;
    Auth user;
    Button submit;
    String hashedPassword;
    AuthDao dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        user = new Auth();
        dbHelper = new AuthDao(SignupActivity.this);
        fullname = findViewById(R.id.et_fullname);
        username = findViewById(R.id.et_username);
        password = findViewById(R.id.et_password);
        submit = findViewById(R.id.btn_signup);
        loginTextView = findViewById(R.id.tv_login);
        loginTextView.setOnClickListener(v -> {
            Intent i = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        });

        spinner = findViewById(R.id.spinner_id);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                user.setGender(adapterView.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        submit.setOnClickListener(v -> {
            boolean usernameEmpty = username.getText().toString().isEmpty();
            boolean fullnameEmpty = fullname.getText().toString().isEmpty();
            boolean passwordEmpty = password.getText().toString().isEmpty();
            if(usernameEmpty || fullnameEmpty || passwordEmpty){
                Toast.makeText(this, "Isi semua data",Toast.LENGTH_SHORT).show();
                return ;
            }
            hashedPassword = BCrypt.withDefaults().hashToString(10, password.getText().toString().toCharArray());
            user.setFullname(fullname.getText().toString());
            user.setUsername(username.getText().toString());
            user.setPassword(hashedPassword);
            // insert data
            dbHelper = new AuthDao(SignupActivity.this);
            boolean success = dbHelper.insert(user);

            if (success) {
                Toast.makeText(this, "berhasil signup", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "username already exists", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
