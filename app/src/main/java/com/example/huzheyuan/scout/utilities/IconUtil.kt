package com.example.huzheyuan.scout.utilities

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import com.example.huzheyuan.scout.R

class IconUtil(context: Context) : View(context) {
    //定义相关变量,依次是icon显示位置的X,Y坐标, icon's x,y coordinates
    var bitmapX:Float = 0f
    var bitmapY:Float = 0f
    var isLeft = true
    private val paint = Paint()
    private var bmpRight:Bitmap
    private var bmpLeft:Bitmap

    init {
        bmpRight = BitmapFactory.decodeResource(resources, R.mipmap.doraemon_right)
        bmpLeft = BitmapFactory.decodeResource(resources, R.mipmap.doraemon_left)
    }

    //重写View类的onDraw()方法, override onDraw() method
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //创建,并且实例化Paint的对象

        //绘制萌妹子, paint/draw icon
        if (!isLeft)
            canvas.drawBitmap(bmpRight, bitmapX, bitmapY, paint)
        else
            canvas.drawBitmap(bmpLeft, bitmapX, bitmapY, paint)

        //判断图片是否回收,木有回收的话强制收回图片, recycle if it is not recycled >_<
        if (bmpLeft.isRecycled) bmpLeft.recycle()
        if (bmpRight.isRecycled) bmpRight.recycle()
    }
}