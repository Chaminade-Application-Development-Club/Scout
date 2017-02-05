package com.example.huzheyuan.scout.Activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import com.example.huzheyuan.scout.R;

public class IconView extends View
{
    //定义相关变量,依次是妹子显示位置的X,Y坐标, icon's x,y coordinates
    public float bitmapX;
    public float bitmapY;

    public IconView(Context context) {
        super(context);
        //设置妹子的起始坐标, starting coordinate
        bitmapX = 15;
        bitmapY = 110;
    }

    //重写View类的onDraw()方法, override onDraw() method
    @Override

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //创建,并且实例化Paint的对象
        Paint paint = new Paint();

        Bitmap bitmap;
        if(bitmapX < 250){
            bitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.roboticonleft);
            //绘制萌妹子, paint/draw icon
            canvas.drawBitmap(bitmap, bitmapX, bitmapY,paint);
        }
        else{
            //根据图片生成位图对象, generate bitmap image
            bitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.roboticon);
            //绘制萌妹子
            canvas.drawBitmap(bitmap, bitmapX, bitmapY,paint);
        }

        //判断图片是否回收,木有回收的话强制收回图片, recycle if it is not recycled >_<
        if(bitmap.isRecycled())
        {
            bitmap.recycle();
        }
    }
}