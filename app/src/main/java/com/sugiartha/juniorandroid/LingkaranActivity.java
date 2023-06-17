package com.sugiartha.juniorandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sugiartha.juniorandroid.utils.ActivityUtils;

import java.text.DecimalFormat;

public class LingkaranActivity extends AppCompatActivity {

    Button submitButton;
    EditText radiusEditText;
    TextView luasTextView, kelilingTextView, errorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lingkaran);
        ActivityUtils.setAppBar(LingkaranActivity.this, MainActivity.class, "Lingkaran");

        submitButton = findViewById(R.id.btn_submit);
        radiusEditText = findViewById(R.id.et_radius);
        luasTextView = findViewById(R.id.tv_luas);
        kelilingTextView = findViewById(R.id.tv_keliling);
        errorTextView = findViewById(R.id.tv_error);

        radiusEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() > 0) {
                    errorTextView.setVisibility(View.GONE);
                    luasTextView.setVisibility(View.GONE);
                    kelilingTextView.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        submitButton.setOnClickListener(v -> {
            try {
                errorTextView.setVisibility(View.GONE);
                double radius = Double.parseDouble(radiusEditText.getText().toString());
                double luas = Math.PI * Math.pow(radius, 2);
                double keliling = 2 * Math.PI * radius;

                DecimalFormat decimalFormat = new DecimalFormat("#.##");
                String luasFormatted = decimalFormat.format(luas);
                String kelilingFormatted = decimalFormat.format(keliling);

                luasTextView.setText(String.format("Luas: %s", luasFormatted));
                kelilingTextView.setText(String.format("Keliling: %s", kelilingFormatted));
                luasTextView.setVisibility(View.VISIBLE);
                kelilingTextView.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                errorTextView.setVisibility(View.VISIBLE);
            }
        });
    }
}
