package com.sugiartha.juniorandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sugiartha.juniorandroid.helper.AuthDao;
import com.sugiartha.juniorandroid.model.Auth;
import com.sugiartha.juniorandroid.utils.FormError;
import com.sugiartha.juniorandroid.utils.Token;

public class LoginActivity extends AppCompatActivity {
    EditText usernameEditText, passwordEditText;
    TextView signUptextView;
    Button loginButton;
    AuthDao dbHelper;
    Auth user;
    boolean isPasswordVisible;
    TextView usernameTextViewError, passwordTextViewError;

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
        usernameTextViewError = findViewById(R.id.tv_username_error);
        passwordTextViewError = findViewById(R.id.tv_password_error);



        SharedPreferences sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);
        if (token != null) {
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }

        loginButton.setOnClickListener(v -> {
            if (isFormError()) return;
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            user.setUsername(username);
            user.setPassword(password);
            dbHelper = new AuthDao(LoginActivity.this);
            try {
                Auth userExists = dbHelper.authenticate(LoginActivity.this, user);

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

        passwordEditText.setOnTouchListener((v,e) -> {
            final int RIGHT = 2;
            if (e.getAction() == MotionEvent.ACTION_UP) {
                if (e.getRawX() >= passwordEditText.getRight() - passwordEditText.getCompoundDrawables()[RIGHT].getBounds().width()) {
                    int selection = passwordEditText.getSelectionEnd();
                    if (isPasswordVisible) {
                        passwordEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visibility_off, 0);
                        passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        isPasswordVisible = false;
                    } else {
                        passwordEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visibility_on, 0);
                        passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        isPasswordVisible = true;
                    }
                    passwordEditText.setSelection(selection);
                    return true;
                }
            }
            return false;
        });
    }

    private boolean isFormError(){
        boolean error = false;
        boolean usernameEmpty = usernameEditText.getText().toString().isEmpty();
        boolean passwordEmpty = passwordEditText.getText().toString().isEmpty();
        if (usernameEmpty || passwordEmpty) {
            error = true;
            if (usernameEmpty) {
                FormError.showErrorRounded(usernameEditText, usernameTextViewError);
            }
            if (passwordEmpty) {
                FormError.showErrorRounded(passwordEditText, passwordTextViewError);
            }
        }
        return error;
    }
}
