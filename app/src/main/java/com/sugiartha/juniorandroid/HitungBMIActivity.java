package com.sugiartha.juniorandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class HitungBMIActivity extends AppCompatActivity {

    HitungBMI hitungBMI = new HitungBMI(); //Membuat object dari class HitungBMI (konsep OOP biasa)

    EditText txtBeratBadan, txtTinggibadan; //Deklarasi object bernama txtBeratBadan dan txtTinggiBadan yang merupakan EditText
    TextView txtStatusBadan; //Deklarasi object bernama txtStatusBadan yang merupakan TextView
    Button btnCekBMI; //Deklarasi object bernama btnCekBMI yang merupakan Button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hitung_bmi);

        txtBeratBadan = findViewById(R.id.txtBeratBadan); //Menyambungkan elemen dengan id txtBeratBadan yang ada di activity_main.xml kesini
        txtTinggibadan = findViewById(R.id.txtTinggiBadan); //Menyambungkan elemen dengan id txtTinggiBadan yang ada di activity_main.xml kesini
        txtStatusBadan = findViewById(R.id.txtStatusBadan); //Menyambungkan elemen dengan id txtStatusBadan yang ada di activity_main.xml kesini
        btnCekBMI = findViewById(R.id.btnCekBMI); //Menyambungkan elemen dengan id btnCekBMI yang ada di activity_main.xml kesini

        //btnCekBMI.setOnClickListener dibawah ini maksudnya adalah untuk me-listen (mengamati) btnCekBMI apakah ada click, apabila ada click, maka code didalamnya akan dieksekusi
        btnCekBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stringBeratBadan = txtBeratBadan.getText().toString(); //Mengambil value yang ada di EditText txtBeratBadan dan dimasukkan ke variabel stringBeratBadan
                String stringTinggiBadan = txtTinggibadan.getText().toString(); //Mengambil value yang ada di EditText txtTinggiBadan dan dimasukkan ke variabel stringTinggiBadan

                //Pada 2 baris code di atas, hasil yang didapatkan dari EditText adalah berbentuk STRING (defaultnya memang string)
                //Sedangkan yang kita butuhkan adalah berbentuk double, maka kita convert dulu 2 string tersebut ke tipe data double
                double beratBadan = Double.parseDouble(stringBeratBadan);
                double tinggiBadan = Double.parseDouble(stringTinggiBadan);

                String statusBadan = hitungBMI.hitungBMI(beratBadan, tinggiBadan); //Mengeksekusi method hitungBMI yang ada di class HitungBMI, karena method hitungBMI me-return string StatusBadan, maka hasil dari eksekusi method ini kita masukkan ke string statusBadan
                txtStatusBadan.setText(statusBadan); //men-set tulisan yang ada di TextView sesuai dengan hasil dari perhitungan BMI (yaitu ada di string statusBadan)
            }
        });
    }
}
