package com.sugiartha.juniorandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sugiartha.juniorandroid.helper.AuthDao;
import com.sugiartha.juniorandroid.model.Auth;
import com.sugiartha.juniorandroid.utils.Token;

public class LoginActivity extends AppCompatActivity {
    EditText usernameEditText, passwordEditText;
    TextView signUptextView;
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
        signUptextView = findViewById(R.id.tv_signup);


        SharedPreferences sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);
        if (token != null) {
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }

        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            user.setUsername(username);
            user.setPassword(password);
            dbHelper = new AuthDao(LoginActivity.this);
            try {
                Auth userExists = dbHelper.authenticate(LoginActivity.this, user);
                System.out.println(userExists);

                if (userExists != null) {
                    Toast.makeText(this, "Login berhasil", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(this, "wrong username or password", Toast.LENGTH_SHORT).show();
                }
            } catch(SQLiteException e) {
                Log.e("Login act", e.getMessage());
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

        signUptextView.setOnClickListener(v -> {
            Intent i = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(i);
            finish();
        });
    }
}
