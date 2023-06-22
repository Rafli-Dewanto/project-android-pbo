package com.sugiartha.juniorandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
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
import com.sugiartha.juniorandroid.utils.FormError;
import com.sugiartha.juniorandroid.utils.Token;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class SignupActivity extends AppCompatActivity {

    Spinner spinner;
    String gender;
    EditText fullname, username, password;
    TextView loginTextView, fullnameTextViewError, usernameTextViewError, passwordTextViewError;
    Auth user;
    Button submit;
    String hashedPassword;
    SharedPreferences sharedPreferences;
    AuthDao dbHelper;
    boolean isPasswordVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        user = new Auth();
        dbHelper = new AuthDao(SignupActivity.this);
        fullname = findViewById(R.id.et_fullname);
        username = findViewById(R.id.et_username);
        password = findViewById(R.id.et_password);

        // error views
        fullnameTextViewError = findViewById(R.id.tv_fullname_error);
        usernameTextViewError = findViewById(R.id.tv_username_error);
        passwordTextViewError = findViewById(R.id.tv_password_error);

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

        sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);
        if (token != null) {
            Intent i = new Intent(SignupActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }

        submit.setOnClickListener(v -> {
            if (isFormError()) return;
            hashedPassword = BCrypt.withDefaults().hashToString(10, password.getText().toString().toCharArray());
            user.setFullname(fullname.getText().toString());
            user.setUsername(username.getText().toString());
            user.setPassword(hashedPassword);
            // insert data
            dbHelper = new AuthDao(SignupActivity.this);
            boolean success = dbHelper.insert(user);

            if (success) {
                String newToken = Token.generateToken(user);
                sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("token", newToken);
                editor.apply();
                Toast.makeText(this, "berhasil signup", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            } else {
                usernameTextViewError.setText("* username already exists");
                FormError.showErrorRounded(username, usernameTextViewError);
            }
        });

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                FormError.hideErrorRounded(username, usernameTextViewError);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        fullname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                FormError.hideErrorRounded(fullname, fullnameTextViewError);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                FormError.hideErrorRounded(password, passwordTextViewError);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        password.setOnTouchListener((v,e) -> {
            final int RIGHT = 2;
            if (e.getAction() == MotionEvent.ACTION_UP) {
                if (e.getRawX() >= password.getRight() - password.getCompoundDrawables()[RIGHT].getBounds().width()) {
                    int selection = password.getSelectionEnd();
                    if (isPasswordVisible) {
                        password.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visibility_off, 0);
                        password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        isPasswordVisible = false;
                    } else {
                        password.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visibility_on, 0);
                        password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        isPasswordVisible = true;
                    }
                    password.setSelection(selection);
                    return true;
                }
            }
            return false;
        });
    }

    private boolean isFormError(){
        boolean error = false;
        boolean usernameEmpty = username.getText().toString().isEmpty();
        boolean fullnameEmpty = fullname.getText().toString().isEmpty();
        boolean passwordEmpty = password.getText().toString().isEmpty();
        if (usernameEmpty || fullnameEmpty || passwordEmpty) {
            error = true;
            if (usernameEmpty) {
                FormError.showErrorRounded(username, usernameTextViewError);
            }
            if (fullnameEmpty) {
                FormError.showErrorRounded(fullname, fullnameTextViewError);
            }
            if (passwordEmpty) {
                FormError.showErrorRounded(password, passwordTextViewError);
            }
        }
        return error;
    }
}