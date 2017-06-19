package com.example.huzheyuan.scout.qRCode

/**
 * Created by leo428 on 1/16/17.
 */

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color

import com.example.huzheyuan.scout.utilities.DensityUtils
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException

class QRUtils {
    /**
     * 生成用户的二维码

     * @param context 上下文
     * *
     * @param content 二维码内容
     * *
     * @return 生成的二维码图片
     * *
     * @throws WriterException 生成二维码异常
     */
    @Throws(WriterException::class)
    fun createCode(context: Context, content: String): Bitmap {
        //生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
        val matrix = MultiFormatWriter()
                .encode(content, BarcodeFormat.QR_CODE,
                    DensityUtils(context).dp2px(350f),
                    DensityUtils(context).dp2px(350f))
        val width = matrix.width
        val height = matrix.height
        //二维矩阵转为一维像素数组,也就是一直横着排了
        val pixels = IntArray(width * height)
        for (y in 0..height - 1) {
            for (x in 0..width - 1) {
                if (matrix.get(x, y)) pixels[y * width + x] = 0xff000000.toInt()
                else pixels[y * width + x] = Color.WHITE
            }
        }
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        //通过像素数组生成bitmap
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
        return bitmap
    }
}
