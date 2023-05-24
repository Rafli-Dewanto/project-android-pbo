package com.sugiartha.juniorandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class LingkaranActivity extends AppCompatActivity {

    Button submitButton;
    EditText radiusEditText;
    TextView luasTextView, kelilingTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lingkaran);

        submitButton = findViewById(R.id.btn_submit);
        radiusEditText = findViewById(R.id.et_radius);
        luasTextView = findViewById(R.id.tv_luas);
        kelilingTextView = findViewById(R.id.tv_keliling);

        submitButton.setOnClickListener(v -> {
            double radius = Double.parseDouble(radiusEditText.getText().toString());
            double luas = Math.PI * Math.pow(radius, 2);
            double keliling = 2 * Math.PI * radius;

            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            String luasFormatted = decimalFormat.format(luas);
            String kelilingFormatted = decimalFormat.format(keliling);

            luasTextView.setText(String.format("Luas: %s", luasFormatted));
            kelilingTextView.setText(String.format("Keliling: %s", kelilingFormatted));
        });
    }
}
