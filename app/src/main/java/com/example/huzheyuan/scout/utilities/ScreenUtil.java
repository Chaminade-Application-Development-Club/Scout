package com.example.huzheyuan.scout.utilities;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by huzhe on 3/15/2017.
 */

public class ScreenUtil {

    public int getHeight(Activity activity){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public int getWidth(Activity activity){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

}
