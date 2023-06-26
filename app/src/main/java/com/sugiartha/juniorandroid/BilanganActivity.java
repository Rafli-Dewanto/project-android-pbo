package com.sugiartha.juniorandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BilanganActivity extends AppCompatActivity {

    Button proses;
    EditText angka;
    TextView bilangan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bilangan);

        proses = (Button) findViewById(R.id.proses);
        angka = (EditText) findViewById(R.id.angka);
        bilangan = (TextView) findViewById(R.id.bilangan);

        proses.setOnClickListener(new Button.OnClickListener() {
            @Override public void onClick(View v) {
                int nilai = Integer.parseInt(angka.getText().toString());
                if ((nilai%2) == 0){
                    bilangan.setText("Bilangan Genap");
                }else{
                    bilangan.setText("Bilangan Ganjil");
                }
            }});
    }
}
