package com.sugiartha.juniorandroid.utils;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sugiartha.juniorandroid.R;

public class FormError {
    public static void showError(EditText editText, TextView errorTextView) {
        editText.setBackgroundResource(R.drawable.form_edit_error);
        errorTextView.setVisibility(View.VISIBLE);
    }

    public static void showError(EditText editText) {
        editText.setBackgroundResource(R.drawable.form_edit_error);
    }

    public static void hideError(EditText editText, TextView errorTextView) {
        editText.setBackgroundResource(R.drawable.form_edit_success);
        errorTextView.setVisibility(View.GONE);
    }

    public static void hideError(EditText editText) {
        editText.setBackgroundResource(R.drawable.form_edit_success);
    }
}
