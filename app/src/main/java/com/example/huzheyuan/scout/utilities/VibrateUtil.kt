package com.example.huzheyuan.scout.utilities

import android.content.Context
import android.os.Vibrator

/**
 * Created by Zheyuan on 6/13/2017.
 */

class VibrateUtil(context: Context){
    val vibrate: Vibrator
            = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    public fun buzz(){
        if (vibrate.hasVibrator()) vibrate.vibrate(100)
    }

    public fun longBuzz(){
        if (vibrate.hasVibrator()) vibrate.vibrate(500)
    }
}