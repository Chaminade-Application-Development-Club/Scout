package com.example.huzheyuan.scout;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.app.Activity;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.os.CountDownTimer;


public class MainActivity extends Activity {

    RelativeLayout frame;
    TextView cX;
    TextView cY;
    TextView cT;
    Button bClear;

    String strCX;
    String strCY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        startPoint();
    }


    public void findViews() {
        cX = (TextView) findViewById(R.id.coordinateX);
        cY = (TextView) findViewById(R.id.coordinateY);
        cT = (TextView) findViewById(R.id.countDown);
        bClear = (Button) findViewById(R.id.btnClear);
        frame = (RelativeLayout) findViewById(R.id.fieldLayout);
    }

    public void startPoint() {

        final GirlView girl = new GirlView(MainActivity.this);
        //为我们的萌妹子添加触摸事件监听器

        bClear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                girl.bitmapX = 138;
                girl.bitmapY = 175;
                girl.invalidate();

                strCX = Float.toString(girl.bitmapX);
                strCY = Float.toString(girl.bitmapY);
                cX.setText("x-axis: " + strCX);
                cY.setText("y-axis: " + strCY);

                new CountDownTimer(30000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        cT.setText("End: " + millisUntilFinished / 1000);
                        girl.setOnTouchListener(new OnTouchListener() {
                            @Override
                            public boolean onTouch(View view, MotionEvent event) {
                                //设置妹子显示的位置
                                girl.bitmapX = event.getX() - 36;
                                girl.bitmapY = event.getY() - 44;

                                if(girl.bitmapX < 170 -36)
                                {
                                    girl.bitmapX = 170 -36;
                                }
                                else if(girl.bitmapX > 275)
                                {
                                    girl.bitmapX = 275;
                                }
                                if(girl.bitmapY < 150 - 44)
                                {
                                    girl.bitmapY = 150 -44;
                                }
                                else if(girl.bitmapY > 480 - 44)
                                {
                                    girl.bitmapY = 480 - 44;
                                }

                                //调用重绘方法
                                girl.invalidate();

                                strCX = Float.toString(girl.bitmapX);
                                strCY = Float.toString(girl.bitmapY);
                                cX.setText("x-axis: " + strCX);
                                cY.setText("y-axis: " + strCY);
                                return true;
                            }
                        });

                    }

                    public void onFinish()
                    {
                        cT.setText("done!");
                        girl.bitmapX = 138;
                        girl.bitmapY = 175;
                        girl.invalidate();
                    }
                }.start();
            }
        });


        frame.addView(girl);

    }
}