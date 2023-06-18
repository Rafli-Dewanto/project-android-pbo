package com.sugiartha.juniorandroid.utils;

import android.app.Activity;
import android.content.Intent;

import com.sugiartha.juniorandroid.R;
import com.sugiartha.juniorandroid.components.DynamicAppBar;

public class ActivityUtils {
    private ActivityUtils() {}

    /**
     * Sets up the app bar with the specified title and back button functionality.
     * Your Appbar's id must be named = 'appbar' for this to work
     * @param currentActivity The current activity where the app bar is located.
     * @param targetActivity  The target activity to navigate to when the back button is clicked.
     * @param title           The title to set on the app bar.
     */
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
