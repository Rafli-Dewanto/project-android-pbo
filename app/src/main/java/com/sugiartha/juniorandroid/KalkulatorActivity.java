package com.sugiartha.juniorandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class KalkulatorActivity extends AppCompatActivity {

    EditText angka1, angka2;
    Button btnHitung;
    TextView txtHasil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalkulator);

        btnHitung = (Button) findViewById(R.id.btnHitung);
        angka1 = (EditText) findViewById(R.id.angka1);
        angka2 = (EditText) findViewById(R.id.angka2);
        txtHasil = (TextView) findViewById(R.id.txtHasil);
        btnHitung.setOnClickListener(new Button.OnClickListener() {
            @Override public void onClick(View v) {
                double angkasatu = Double.parseDouble(angka1.getText().toString());
                double angkadua = Double.parseDouble(angka2.getText().toString());
                double result = angkasatu + angkadua;
                txtHasil.setText(Double.toString(result));
            }});
    }
}
