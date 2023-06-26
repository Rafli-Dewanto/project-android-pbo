package com.sugiartha.juniorandroid;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import com.sugiartha.juniorandroid.utils.ActivityUtils;
import com.fathzer.soft.javaluator.DoubleEvaluator;

public class CalculatorActivity extends AppCompatActivity implements View.OnClickListener {

    EditText input;
    Button addButton, minusButton, divisionButton, multiplyButton, percentButton, equalButton, parentButton, clearButton;
    Button decimalButton;
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0;
    String proses = "";
    double hasil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        ActivityUtils.setAppBar(CalculatorActivity.this, MainActivity.class, "Calculator");
        input = findViewById(R.id.et_input);

        // Disable focus and prevent keyboard from appearing
        input.setFocusable(false);
        input.setClickable(false);
        input.setCursorVisible(false);
        input.setFocusableInTouchMode(false);

        // Optional: Prevent the screen from automatically focusing on the EditText
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Initialize buttons
        btn0 = findViewById(R.id.btn_0);
        btn1 = findViewById(R.id.btn_1);
        btn2 = findViewById(R.id.btn_2);
        btn3 = findViewById(R.id.btn_3);
        btn4 = findViewById(R.id.btn_4);
        btn5 = findViewById(R.id.btn_5);
        btn6 = findViewById(R.id.btn_6);
        btn7 = findViewById(R.id.btn_7);
        btn8 = findViewById(R.id.btn_8);
        btn9 = findViewById(R.id.btn_9);
        addButton = findViewById(R.id.btn_add);
        minusButton = findViewById(R.id.btn_minus);
        divisionButton = findViewById(R.id.btn_div);
        multiplyButton = findViewById(R.id.btn_multiply);
        percentButton = findViewById(R.id.btn_percent);
        equalButton = findViewById(R.id.btn_equals);
        parentButton = findViewById(R.id.btn_del);
        clearButton = findViewById(R.id.btn_ac);
        decimalButton = findViewById(R.id.btn_decimal);

        // Set click listeners for buttons
        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        addButton.setOnClickListener(this);
        minusButton.setOnClickListener(this);
        divisionButton.setOnClickListener(this);
        multiplyButton.setOnClickListener(this);
        percentButton.setOnClickListener(this);
        equalButton.setOnClickListener(this);
        parentButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);
        decimalButton.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_0:
                proses += "0";
                input.setText(proses);
                break;
            case R.id.btn_1:
                proses += "1";
                input.setText(proses);
                break;
            case R.id.btn_2:
                proses += "2";
                input.setText(proses);
                break;
            case R.id.btn_3:
                proses += "3";
                input.setText(proses);
                break;
            case R.id.btn_4:
                proses += "4";
                input.setText(proses);
                break;
            case R.id.btn_5:
                proses += "5";
                input.setText(proses);
                break;
            case R.id.btn_6:
                proses += "6";
                input.setText(proses);
                break;
            case R.id.btn_7:
                proses += "7";
                input.setText(proses);
                break;
            case R.id.btn_8:
                proses += "8";
                input.setText(proses);
                break;
            case R.id.btn_9:
                proses += "9";
                input.setText(proses);
                break;
            case R.id.btn_add:
                proses += "+";
                input.setText(proses);
                break;
            case R.id.btn_minus:
                proses += "-";
                input.setText(proses);
                break;
            case R.id.btn_div:
                proses += "/";
                input.setText(proses);
                break;
            case R.id.btn_multiply:
                proses += "*";
                input.setText(proses);
                break;
            case R.id.btn_percent:
                proses += "%";
                input.setText(proses);
                proses = proses.replace("%", "/100");
                break;
            case R.id.btn_equals:
                DoubleEvaluator eval = new DoubleEvaluator();
                double result = eval.evaluate(proses);
                proses = String.valueOf(result);
                input.setText(String.format("%s", result));
                break;
            case R.id.btn_del:
                if (proses.length() > 0) {
                    proses = proses.substring(0, proses.length() - 1);
                    input.setText(proses);
                }
                break;
            case R.id.btn_ac:
                proses = "";
                input.setText("");
                break;
            case R.id.btn_decimal:
                proses += ".";
                input.setText(proses);
                break;
        }
    }


}
