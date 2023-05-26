package com.sugiartha.juniorandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class SharedPreferenceActivity extends AppCompatActivity {

    TextInputEditText usernameEditText, passwordEditText;
    Button masuk;
    TextView usernameErrorTextView, passwordErrorTextView, invalidErrorTextView;

    Intent intent;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preference);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);

        usernameErrorTextView = findViewById(R.id.tv_username_error);
        passwordErrorTextView = findViewById(R.id.tv_password_error);
        invalidErrorTextView = findViewById(R.id.tv_invalid);

        masuk = findViewById(R.id.login);

        sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);
        sharedPreferences.contains("username");
        sharedPreferences.contains("password");

        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                usernameEditText.setBackgroundResource(R.drawable.form_edit);
                usernameErrorTextView.setVisibility(View.GONE);
                invalidErrorTextView.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                passwordEditText.setBackgroundResource(R.drawable.form_edit);
                passwordErrorTextView.setVisibility(View.GONE);
                invalidErrorTextView.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        masuk.setOnClickListener(v -> {
            String uname = usernameEditText.getText().toString();
            String pw = passwordEditText.getText().toString();

            if (uname.length() == 0 || pw.equals("")) {

                if (uname.length() == 0 && pw.equals("")) {
                    usernameEditText.setBackgroundResource(R.drawable.form_edit_error);
                    usernameErrorTextView.setVisibility(View.VISIBLE);

                    passwordEditText.setBackgroundResource(R.drawable.form_edit_error);
                    passwordErrorTextView.setVisibility(View.VISIBLE);
                    return;
                } else if (pw.equals("")) {
                    passwordEditText.setBackgroundResource(R.drawable.form_edit_error);
                    passwordErrorTextView.setVisibility(View.VISIBLE);
                    return;
                } else {
                    usernameEditText.setBackgroundResource(R.drawable.form_edit_error);
                    usernameErrorTextView.setVisibility(View.VISIBLE);
                    return;
                }

            }

            if (uname.equals("android") && pw.equals("12345678")) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", uname);
                editor.putString("password", pw);
                editor.apply();
                intent = new Intent(SharedPreferenceActivity.this, DetailSharedPreferenceActivity.class);
                startActivity(intent);
            } else {
                invalidErrorTextView.setVisibility(View.VISIBLE);
            }
        });
    }
}