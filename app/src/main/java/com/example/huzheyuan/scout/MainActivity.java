package com.example.huzheyuan.scout;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.app.Activity;
import android.widget.TextView;


public class MainActivity extends Activity {

    String strCX;
    String strCY;
    TextView cX ;
    TextView cY ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cX = (TextView) findViewById(R.id.coordinateX);
        cY = (TextView) findViewById(R.id.coordinateY);

        FrameLayout frame = (FrameLayout) findViewById(R.id.mylayout);
        final GirlView mezi = new GirlView(MainActivity.this);
        //为我们的萌妹子添加触摸事件监听器
        mezi.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                //设置妹子显示的位置
                mezi.bitmapX = event.getX() - 150;
                mezi.bitmapY = event.getY() - 150;
                //调用重绘方法
                mezi.invalidate();

                strCX = Float.toString(mezi.bitmapX);
                strCY = Float.toString(mezi.bitmapY);


                cX.setText("x-axis: " + strCX);
                cY.setText("y-axis: " + strCY);

                return true;
            }
        });
        frame.addView(mezi);
    }
}