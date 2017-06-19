package com.example.huzheyuan.scout.utilities

/**
 * Created by leo428 on 1/16/17.
 * Credit for Eddie for doing nothing
 */
import android.content.Context

class DensityUtils(context: Context) {
    /**
     * px与dp互相转换
     */
    var density = 0f
    init {
        density = context.resources.displayMetrics.density //获取设备密度
    }
    fun dp2px(dp: Float): Int {
        //加0.5是为了四舍五入
        val pX = (dp * density + 0.5f).toInt()
        return pX
    }

    fun px2dp(px: Int): Float {
        val dP = px / density
        return dP
    }
}
