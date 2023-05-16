package com.sugiartha.juniorandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class SharedPreferenceActivity extends AppCompatActivity {

    TextInputEditText textUsername, textPassword;
    Button masuk;

    Intent intent;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preference);

        textUsername = findViewById(R.id.username);
        textPassword = findViewById(R.id.password);

        masuk = findViewById(R.id.login);

        sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);
        sharedPreferences.contains("username");
        sharedPreferences.contains("password");

        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = textUsername.getText().toString();
                String pw = textPassword.getText().toString();

                if (uname.equals("android") && pw.equals("1234")) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", uname);
                    editor.putString("password", pw);
                    editor.apply();
                    intent = new Intent(SharedPreferenceActivity.this, DetailSharedPreferenceActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(SharedPreferenceActivity.this, "Data not valid", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}