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

import com.google.android.material.textfield.TextInputEditText;
import com.sugiartha.juniorandroid.utils.FormError;

import java.util.Objects;

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
                FormError.hideError(usernameEditText, usernameErrorTextView);
                invalidErrorTextView.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() == 0) {
                    FormError.showError(usernameEditText, usernameErrorTextView);
                }
            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                FormError.hideError(passwordEditText, passwordErrorTextView);
                invalidErrorTextView.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() == 0) {
                    FormError.showError(passwordEditText, passwordErrorTextView);
                }
            }
        });


        masuk.setOnClickListener(v -> {
            String uname = Objects.requireNonNull(usernameEditText.getText()).toString();
            String pw = Objects.requireNonNull(passwordEditText.getText()).toString();

            boolean isUsernameEmpty = uname.isEmpty();
            boolean isPasswordEmpty = pw.isEmpty();

            if (isUsernameEmpty || isPasswordEmpty) {
                if (isUsernameEmpty) {
                    FormError.showError(usernameEditText, usernameErrorTextView);
                } else {
                    FormError.hideError(usernameEditText, usernameErrorTextView);
                }

                if (isPasswordEmpty) {
                    FormError.showError(passwordEditText, passwordErrorTextView);
                } else {
                    FormError.hideError(passwordEditText, passwordErrorTextView);
                }
                return;
            }

            if (uname.equals("android") && pw.equals("12345678")) {
                FormError.hideError(usernameEditText, usernameErrorTextView);
                FormError.hideError(passwordEditText, passwordErrorTextView);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", uname);
                editor.putString("password", pw);
                editor.apply();
                intent = new Intent(SharedPreferenceActivity.this, DetailSharedPreferenceActivity.class);
                startActivity(intent);
            } else {
                FormError.showError(usernameEditText);
                FormError.showError(passwordEditText);
                invalidErrorTextView.setVisibility(View.VISIBLE);
            }
        });
    }


}