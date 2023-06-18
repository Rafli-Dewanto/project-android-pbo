package com.sugiartha.juniorandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sugiartha.juniorandroid.helper.AuthDao;
import com.sugiartha.juniorandroid.model.Auth;

public class LoginActivity extends AppCompatActivity {
    EditText usernameEditText, passwordEditText;
    Button loginButton;
    AuthDao dbHelper;
    Auth user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbHelper = new AuthDao(LoginActivity.this);
        user = new Auth();

        usernameEditText = findViewById(R.id.et_username);
        passwordEditText = findViewById(R.id.et_password);
        loginButton = findViewById(R.id.btn_login);

        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            user.setUsername(username);
            user.setPassword(password);
            dbHelper = new AuthDao(LoginActivity.this);
            try {
                Auth userExists = dbHelper.authenticate(user);
                System.out.println(userExists);

                if (userExists != null) {
                    Toast.makeText(this, "Login berhasil", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "wrong username or password", Toast.LENGTH_SHORT).show();
                }
            } catch(SQLiteException e) {
                Log.e("Login act", e.getMessage());
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
