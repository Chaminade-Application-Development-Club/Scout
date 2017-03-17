package com.example.huzheyuan.scout.utilities;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import static android.content.ContentValues.TAG;

/**
 * Created by leo428 on 2/11/17.
 */

public class ScoreUtil {

    public void mapBoundary(Activity activity, IconUtil iconUtil){ // I made it, it is optimized!
        //EXTREMELY COMPLEX MATH BEHIND HERE! x,y is pixel!
        ScreenUtil screenUtil = new ScreenUtil();
        float xLeft = (float)110/(float)1024 * ((float)screenUtil.getWidth(activity)) - (float)36;

        float xRight = (float)850/(float)1024 * ((float)screenUtil.getWidth(activity)) + (float)36;;

        float yTop = (float)20/(float)552 * ((float)screenUtil.getHeight(activity)) - (float)44;

        float yBottom = (float)340/(float)552 * ((float)screenUtil.getHeight(activity)) + (float)44;

        if(iconUtil.bitmapX < xLeft) iconUtil.bitmapX = xLeft;
        else if(iconUtil.bitmapX > xRight) iconUtil.bitmapX = xRight;

        if(iconUtil.bitmapY < yTop) iconUtil.bitmapY = yTop;
        else if(iconUtil.bitmapY > yBottom) iconUtil.bitmapY = yBottom;
    }
}
