package com.example.huzheyuan.scout;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.app.Activity;
import android.widget.TextView;


public class MainActivity extends Activity {

    FrameLayout frame;
    TextView cX;
    TextView cY;
    Button bClear;

    String strCX;
    String strCY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cX = (TextView) findViewById(R.id.coordinateX);
        cY = (TextView) findViewById(R.id.coordinateY);
        bClear = (Button) findViewById(R.id.btnClear);
        frame = (FrameLayout) findViewById(R.id.mylayout);


        startPoint();
    }

    public void startPoint() {

        final GirlView girl = new GirlView(MainActivity.this);
        //为我们的萌妹子添加触摸事件监听器

        girl.bitmapX = 350 - 36;
        girl.bitmapY = 220 - 44;

        bClear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                girl.bitmapX = 350 - 36;
                girl.bitmapY = 220 - 44;
                girl.invalidate();
                strCX = Float.toString(girl.bitmapX);
                strCY = Float.toString(girl.bitmapY);
                cX.setText("x-axis: " + strCX);
                cY.setText("y-axis: " + strCY);
            }
        });

        girl.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                //设置妹子显示的位置
                girl.bitmapX = event.getX() - 36;
                girl.bitmapY = event.getY() - 44;
                //调用重绘方法
                girl.invalidate();
                strCX = Float.toString(event.getX());
                strCY = Float.toString(event.getY());
                cX.setText("x-axis: " + strCX);
                cY.setText("y-axis: " + strCY);
                return true;
            }
        });

        frame.addView(girl);
    }
}

