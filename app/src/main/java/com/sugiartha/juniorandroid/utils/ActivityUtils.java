package com.sugiartha.juniorandroid.utils;

import android.app.Activity;
import android.content.Intent;

import com.sugiartha.juniorandroid.R;
import com.sugiartha.juniorandroid.components.DynamicAppBar;

public class ActivityUtils {
    private ActivityUtils() {}

    public static void setAppBar(Activity currentActivity, Class<? extends Activity> targetActivity, String title) {
        DynamicAppBar appBar = currentActivity.findViewById(R.id.appbar);
        appBar.setTitle(title);
        appBar.setBackButtonClickListener(v -> {
            Intent intent = new Intent(currentActivity, targetActivity);
            currentActivity.startActivity(intent);
            currentActivity.finish();
        });
    }
}
