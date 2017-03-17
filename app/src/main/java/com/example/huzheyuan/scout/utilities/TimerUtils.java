package com.example.huzheyuan.scout.utilities;

import android.os.CountDownTimer;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by huzhe on 3/15/2017.
 */

public class TimerUtils {

    public void timerBug(CountDownTimer timer1, CountDownTimer timer2){// the if statements here are for avoiding multiple countdown timer bug
        if(timer1 != null){
            timer1.cancel();
            timer1.onFinish();
        }
        if (timer2 != null){
            timer2.cancel();
            timer2.onFinish();
        }
    }

    public int startTimer(){
        int time = 0;
        time = (int) (System.currentTimeMillis());
        Log.d(TAG, "startTimer: " + time);
        return time;
    }
    public int timeNow(){
        int time = 0;
        time = (int) (System.currentTimeMillis());
        Log.d(TAG, "timeNow: " + time);
        return time;
    }
    public int difference(int start, int now){
        int dT = (now - start)/1000;
        Log.d(TAG, "difference: " + dT);
        return dT;
    }
}
