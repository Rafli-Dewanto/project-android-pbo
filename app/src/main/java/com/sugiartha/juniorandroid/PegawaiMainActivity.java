package com.sugiartha.juniorandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sugiartha.juniorandroid.utils.ActivityUtils;
import com.sugiartha.juniorandroid.utils.FormError;

import java.util.HashMap;

public class PegawaiMainActivity extends AppCompatActivity {

    EditText editTextName;
    EditText editTextDesg;
    EditText editTextSal;

    Button buttonAdd;
    Button buttonView;
    TextView salaryError, desgError, nameError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pegawai_main);
        ActivityUtils.setAppBar(PegawaiMainActivity.this, MainActivity.class, "Employee");

        editTextName = findViewById(R.id.editTextName);
        editTextDesg = findViewById(R.id.editTextDesg);
        editTextSal = findViewById(R.id.editTextSalary);
        salaryError = findViewById(R.id.tv_salary_error);
        desgError = findViewById(R.id.tv_desg_error);
        nameError = findViewById(R.id.tv_name_error);

        buttonAdd = findViewById(R.id.buttonAdd);
        buttonView = findViewById(R.id.buttonView);

        buttonAdd.setOnClickListener(v -> {
            if (isFormError()) return;
            addEmployee();
        });
        buttonView.setOnClickListener(v -> startActivity(new Intent(PegawaiMainActivity.this, PegawaiTampilSemuaActivity.class)));
        editTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                FormError.hideError(editTextName, nameError);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editTextDesg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                FormError.hideError(editTextDesg, desgError);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editTextSal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                FormError.hideError(editTextSal, salaryError);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    //Dibawah ini merupakan perintah untuk Menambahkan Pegawai (CREATE)
    private void addEmployee(){
        final String name = editTextName.getText().toString().trim();
        final String desg = editTextDesg.getText().toString().trim();
        final String sal = editTextSal.getText().toString().trim();

        class AddEmployee extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(PegawaiMainActivity.this,"Menambahkan...","Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(PegawaiMainActivity.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(konfigurasi.KEY_EMP_NAMA,name);
                params.put(konfigurasi.KEY_EMP_POSISI,desg);
                params.put(konfigurasi.KEY_EMP_GAJIH,sal);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(konfigurasi.URL_ADD, params);
                return res;
            }
        }

        AddEmployee ae = new AddEmployee();
        ae.execute();
    }

    private boolean isFormError(){
        boolean error = false;
        boolean nameEmpty = editTextName.getText().toString().isEmpty();
        boolean positionEmpty = editTextDesg.getText().toString().isEmpty();
        boolean salaryEmpty = editTextSal.getText().toString().isEmpty();
        if (nameEmpty || positionEmpty || salaryEmpty) {
            error = true;
            if (nameEmpty) {
                FormError.showError(editTextName, nameError);
            }
            if (positionEmpty) {
                FormError.showError(editTextDesg, desgError);
            }
            if (salaryEmpty) {
                FormError.showError(editTextSal, salaryError);
            }
        }
        return error;
    }
}
