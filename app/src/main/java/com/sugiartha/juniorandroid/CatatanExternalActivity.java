package com.sugiartha.juniorandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class CatatanExternalActivity extends AppCompatActivity {

    TextInputEditText inputText;
    Button btnSimpan, btnLihat;
    TextView textCatatan;

    static final int READ_BLOCK_SIZE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catatan_external);

        inputText = findViewById(R.id.input_text);
        btnSimpan = findViewById(R.id.btn_simpan);
        btnLihat = findViewById(R.id.btn_baca);
        textCatatan = findViewById(R.id.text_catatan);

        String path = this.getFilesDir().getAbsolutePath() + "/catatan.txt";
        File file = new File(path);

        btnSimpan.setOnClickListener(view -> {
            if (inputText.getText().toString().isEmpty()) {
                Toast.makeText(this, "Mohon masukkan catatan", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    FileOutputStream fileout = openFileOutput("catatan.txt", MODE_PRIVATE);
                    OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
                    outputWriter.write(inputText.getText().toString());
                    outputWriter.close();

                    Toast.makeText(getBaseContext(), "Catatan berhasil disimpan di " + path, Toast.LENGTH_LONG).show();
                    inputText.setText("");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btnLihat.setOnClickListener(view -> {
            if (file.exists()) {
                try {
                    FileInputStream fileIn = openFileInput("catatan.txt");
                    InputStreamReader InputRead = new InputStreamReader(fileIn);

                    char[] inputBuffer = new char[READ_BLOCK_SIZE];
                    StringBuilder s = new StringBuilder();
                    int charRead;

                    while ((charRead = InputRead.read(inputBuffer)) > 0) {
                        // char to string conversion
                        String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                        s.append(readstring);
                    }
                    InputRead.close();
                    textCatatan.setText(s.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, "Anda belum menulis catatan", Toast.LENGTH_SHORT).show();
            }
        });
    }
}