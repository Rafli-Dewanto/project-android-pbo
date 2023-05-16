package com.sugiartha.juniorandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailSharedPreferenceActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_shared_preference);

        TextView result = findViewById(R.id.detailUsername);
        Button btnLogout = findViewById(R.id.buttonLogOut);

        sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);

        result.setText(sharedPreferences.getString("username",null));
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                Intent intent = new Intent(DetailSharedPreferenceActivity.this, SharedPreferenceActivity.class);
                startActivity(intent);
            }
        });
    }
}