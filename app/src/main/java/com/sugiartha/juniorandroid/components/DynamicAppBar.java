package com.sugiartha.juniorandroid.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.sugiartha.juniorandroid.R;

public class DynamicAppBar extends AppBarLayout {
    private ImageView backButton;
    private TextView titleTextView;

    public DynamicAppBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dynamic_app_bar, this, true);
        backButton = view.findViewById(R.id.iv_back_arrow);
        titleTextView = view.findViewById(R.id.tv_app_title);
    }

    public void setTitle(String title) {
        titleTextView.setText(title);
    }

    public void setBackButtonClickListener(View.OnClickListener listener) {
        backButton.setOnClickListener(listener);
    }

    public ImageView getBackButton() {
        return backButton;
    }
}

