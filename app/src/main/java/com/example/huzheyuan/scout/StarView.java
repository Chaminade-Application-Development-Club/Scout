package com.example.huzheyuan.scout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class StarView extends View
{
    //定义相关变量,依次是star显示位置的X,Y坐标
    public float bitmapX;
    public float bitmapY;

    public StarView(Context context) {
        super(context);
        //设置star的起始坐标
        bitmapX = 15;
        bitmapY = 110;
    }

    //重写View类的onDraw()方法
    @Override

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //创建,并且实例化Paint的对象
        Paint paint = new Paint();

        //根据图片生成位图对象
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.star);

        //绘制star
        canvas.drawBitmap(bitmap, bitmapX, bitmapY,paint);

        //判断图片是否回收,木有回收的话强制收回图片
        if(bitmap.isRecycled())
        {
            bitmap.recycle();
        }
    }
}