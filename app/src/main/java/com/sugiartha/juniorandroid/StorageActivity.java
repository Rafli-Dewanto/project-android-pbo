package com.sugiartha.juniorandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class StorageActivity extends AppCompatActivity {

    private Button Internal, External;
    private EditText InputData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        InputData = findViewById(R.id.input_data);
        Internal = findViewById(R.id.save_internal);
        External = findViewById(R.id.save_external);

        Internal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String setData = InputData.getText().toString();
                //Digunakan untuk membuat dan menulis File/Data pada Penyimpanan
                FileOutputStream fileOutputStream;
                try {
                    //Membuat Berkas Baru dengan mode Private
                    fileOutputStream = openFileOutput("DataSaya", Context.MODE_PRIVATE);

                    //Menulis Data Baru dan Mengkonversinya kedalam bentuk byte
                    fileOutputStream.write(setData.getBytes());

                    //Menutup FileOutputStream
                    fileOutputStream.close();

                    Toast.makeText(getApplicationContext(), "Data Disimpan di Internal", Toast.LENGTH_SHORT).show();
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        External.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Digunakan Untuk Mengecek Apakah Tersebut Peyimpanan External atau Tidak
                String state = Environment.getExternalStorageState();
                if (Environment.MEDIA_MOUNTED.equals(state)){
                    //Digunakan Untuk Mengecek Lokasi Penyimpanan Direktori di Memory External
                    File dirExternal = Environment.getExternalStorageDirectory();

                    //Membuat Direktori Baru dengan Nama "ContohDirektori"
                    File createDir = new File(dirExternal.getAbsolutePath()+"/ContohDirektori");

                    //Jika Direktori "ContohDirektori" Tidak Ada, Maka akan Membuatnya
                    if (!createDir.exists()){
                        createDir.mkdir();
                        //Membuat File Baru didalam Direktori "ContohDirektori"
                        File file = new File(createDir, "ContohFile.txt");

                        String setData = InputData.getText().toString();
                        //Digunakan untuk membuat dan menulis File/Data di Internal
                        FileOutputStream fileOutputStream;
                        try {
                            //Membuat Berkas Baru dengan mode Private
                            fileOutputStream = new FileOutputStream(file);

                            fileOutputStream.write(setData.getBytes());

                            fileOutputStream.close();

                            Toast.makeText(getApplicationContext(), "Data Disimpan di External", Toast.LENGTH_SHORT).show();
                        }catch (IOException ex){
                            ex.printStackTrace();
                        }
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Penyimpanan External Tidak Tersedia", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}