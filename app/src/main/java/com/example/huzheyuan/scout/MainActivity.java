package com.example.huzheyuan.scout;
import android.app.backup.BackupDataInput;
import android.media.Image;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.os.CountDownTimer;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.GestureDetector.OnDoubleTapListener;


public class MainActivity extends Activity
{
    RelativeLayout frame;

    TextView cX;
    TextView cY;
    TextView cT;

    TextView nearRightScore;
    TextView farRightScore;
    TextView nearLeftScore;
    TextView farLeftScore;

    ImageView leftStart;
    ImageView leftInside;
    ImageView rightInside;
    ImageView rightStart;

    Button bAuto;
    Button bDriver;

    Switch sSide;
    Switch sPoint;

    Spinner teamNumSpin;

    //Gesture detector
    //private GestureDetector gDetector;
    //private SimpleOnGestureListener gListener;

    //定义相关变量,依次是妹子显示位置的X,Y坐标
    String strCX;
    String strCY;
    String strCAN;
    String strCAF;
    String strCDN;
    String strCDF;
    String strSAN;
    String strSAF;
    String strSDN;
    String strSDF;

    int cubeAutoNear = 0;
    int cubeAutoFar = 0;
    int starAutoNear = 0;
    int starAutoFar = 0;
    int cubeDriverNear = 0;
    int cubeDriverFar = 0;
    int starDriverNear = 0;
    int starDriverFar = 0;

    boolean leftSide = true;
    boolean up = true;
////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //实例化GestureListener与GestureDetector对象
        //gListener = new SimpleOnGestureListener();
        //gDetector = new GestureDetector(this, gListener);

        final GirlView girl = new GirlView(MainActivity.this);
        //final StarView star = new StarView(MainActivity.this);
        findViews();

        sSide.setChecked(false);
        sSide.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    leftSide = false;
                }
                else
                {
                    leftSide = true;
                }

                if(leftSide == true && up == true) {
                    girl.bitmapX = 15;
                    girl.bitmapY = 110;

                    girl.invalidate();

                    strCX = Float.toString(girl.bitmapX);
                    strCY = Float.toString(girl.bitmapY);
                    cX.setText("x-axis: " + strCX);
                    cY.setText("y-axis: " + strCY);
                }

                else if(leftSide == true && up == false){
                    girl.bitmapX = 15;
                    girl.bitmapY = 410;
                    girl.invalidate();

                    strCX = Float.toString(girl.bitmapX);
                    strCY = Float.toString(girl.bitmapY);
                    cX.setText("x-axis: " + strCX);
                    cY.setText("y-axis: " + strCY);
                }

                else if(leftSide == false && up == true){
                    girl.bitmapX = 510;
                    girl.bitmapY = 110;
                    girl.invalidate();

                    strCX = Float.toString(girl.bitmapX);
                    strCY = Float.toString(girl.bitmapY);
                    cX.setText("x-axis: " + strCX);
                    cY.setText("y-axis: " + strCY);
                }

                else if(leftSide == false && up == false){
                    girl.bitmapX = 510;
                    girl.bitmapY = 410;
                    girl.invalidate();

                    strCX = Float.toString(girl.bitmapX);
                    strCY = Float.toString(girl.bitmapY);
                    cX.setText("x-axis: " + strCX);
                    cY.setText("y-axis: " + strCY);
                }
            }
        });

        sPoint.setChecked(false);
        sPoint.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    up = false;
                }
                else
                {
                    up = true;
                }

                if(leftSide == true && up == true) {
                    girl.bitmapX = 15;
                    girl.bitmapY = 110;

                    girl.invalidate();

                    strCX = Float.toString(girl.bitmapX);
                    strCY = Float.toString(girl.bitmapY);
                    cX.setText("x-axis: " + strCX);
                    cY.setText("y-axis: " + strCY);
                }

                else if(leftSide == true && up == false){
                    girl.bitmapX = 15;
                    girl.bitmapY = 410;
                    girl.invalidate();

                    strCX = Float.toString(girl.bitmapX);
                    strCY = Float.toString(girl.bitmapY);
                    cX.setText("x-axis: " + strCX);
                    cY.setText("y-axis: " + strCY);
                }

                else if(leftSide == false && up == true){
                    girl.bitmapX = 510;
                    girl.bitmapY = 110;
                    girl.invalidate();

                    strCX = Float.toString(girl.bitmapX);
                    strCY = Float.toString(girl.bitmapY);
                    cX.setText("x-axis: " + strCX);
                    cY.setText("y-axis: " + strCY);
                }

                else if(leftSide == false && up == false){
                    girl.bitmapX = 510;
                    girl.bitmapY = 410;
                    girl.invalidate();

                    strCX = Float.toString(girl.bitmapX);
                    strCY = Float.toString(girl.bitmapY);
                    cX.setText("x-axis: " + strCX);
                    cY.setText("y-axis: " + strCY);
                }
            }
        });

        //Auto mode starts
        bAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(leftSide == true && up == true) {
                    girl.bitmapX = 15;
                    girl.bitmapY = 110;

                    girl.invalidate();

                    strCX = Float.toString(girl.bitmapX);
                    strCY = Float.toString(girl.bitmapY);
                    cX.setText("x-axis: " + strCX);
                    cY.setText("y-axis: " + strCY);
                }

                else if(leftSide == true && up == false){
                    girl.bitmapX = 15;
                    girl.bitmapY = 410;
                    girl.invalidate();

                    strCX = Float.toString(girl.bitmapX);
                    strCY = Float.toString(girl.bitmapY);
                    cX.setText("x-axis: " + strCX);
                    cY.setText("y-axis: " + strCY);
                }

                else if(leftSide == false && up == true){
                    girl.bitmapX = 510;
                    girl.bitmapY = 110;
                    girl.invalidate();

                    strCX = Float.toString(girl.bitmapX);
                    strCY = Float.toString(girl.bitmapY);
                    cX.setText("x-axis: " + strCX);
                    cY.setText("y-axis: " + strCY);
                }

                else if(leftSide == false && up == false){
                    girl.bitmapX = 510;
                    girl.bitmapY = 410;
                    girl.invalidate();

                    strCX = Float.toString(girl.bitmapX);
                    strCY = Float.toString(girl.bitmapY);
                    cX.setText("x-axis: " + strCX);
                    cY.setText("y-axis: " + strCY);
                }

                new CountDownTimer(15000, 1000) {
                    // Auto time limits 15s
                    public void onTick(long millisUntilFinished) {
                        cT.setText("End: " + millisUntilFinished / 1000);

                        girl.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View view, MotionEvent event) {
                                //设置妹子显示的位置
                                girl.bitmapX = event.getX() - 36;
                                girl.bitmapY = event.getY() - 44;

                                if(leftSide == true)
                                {
                                    farRightScore.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            ++starAutoFar;
                                            strSAF = Integer.toString(starAutoFar);
                                            farRightScore.setText(strSAF);
                                        }
                                    });

                                    nearRightScore.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            ++starAutoNear;
                                            strSAN = Integer.toString(starAutoNear);
                                            nearRightScore.setText(strSAN);
                                        }
                                    });

                                    if(girl.bitmapX < 0 )
                                    {
                                        girl.bitmapX = 0 ;
                                    }
                                    else if(girl.bitmapX > 240)
                                    {
                                        girl.bitmapX = 240;
                                    }
                                    if(girl.bitmapY < 0)
                                    {
                                        girl.bitmapY = 0;
                                    }
                                    else if(girl.bitmapY > 520)
                                    {
                                        girl.bitmapY = 520;
                                    }
                                }

                                else if (leftSide == false)
                                {
                                    if(girl.bitmapX < 295 )
                                    {
                                        girl.bitmapX = 295 ;
                                    }
                                    else if(girl.bitmapX > 530)
                                    {
                                        girl.bitmapX = 530;
                                    }
                                    if(girl.bitmapY < 0)
                                    {
                                        girl.bitmapY = 0;
                                    }
                                    else if(girl.bitmapY > 520)
                                    {
                                        girl.bitmapY = 520;
                                    }
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
                        if(leftSide == true && up == true) {
                            girl.bitmapX = 15;
                            girl.bitmapY = 110;

                            girl.invalidate();

                            strCX = Float.toString(girl.bitmapX);
                            strCY = Float.toString(girl.bitmapY);
                            cX.setText("x-axis: " + strCX);
                            cY.setText("y-axis: " + strCY);
                        }

                        else if(leftSide == true && up == false){
                            girl.bitmapX = 15;
                            girl.bitmapY = 410;
                            girl.invalidate();

                            strCX = Float.toString(girl.bitmapX);
                            strCY = Float.toString(girl.bitmapY);
                            cX.setText("x-axis: " + strCX);
                            cY.setText("y-axis: " + strCY);
                        }

                        else if(leftSide == false && up == true){
                            girl.bitmapX = 510;
                            girl.bitmapY = 110;
                            girl.invalidate();

                            strCX = Float.toString(girl.bitmapX);
                            strCY = Float.toString(girl.bitmapY);
                            cX.setText("x-axis: " + strCX);
                            cY.setText("y-axis: " + strCY);
                        }

                        else if(leftSide == false && up == false){
                            girl.bitmapX = 510;
                            girl.bitmapY = 410;
                            girl.invalidate();

                            strCX = Float.toString(girl.bitmapX);
                            strCY = Float.toString(girl.bitmapY);
                            cX.setText("x-axis: " + strCX);
                            cY.setText("y-axis: " + strCY);
                        }
                        cT.setText("End!");
                    }
                }.start();
            }
        });

        //Driver Mode Start
        bDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(leftSide == true && up == true) {
                    girl.bitmapX = 15;
                    girl.bitmapY = 110;

                    girl.invalidate();

                    strCX = Float.toString(girl.bitmapX);
                    strCY = Float.toString(girl.bitmapY);
                    cX.setText("x-axis: " + strCX);
                    cY.setText("y-axis: " + strCY);
                }

                else if(leftSide == true && up == false){
                    girl.bitmapX = 15;
                    girl.bitmapY = 410;
                    girl.invalidate();

                    strCX = Float.toString(girl.bitmapX);
                    strCY = Float.toString(girl.bitmapY);
                    cX.setText("x-axis: " + strCX);
                    cY.setText("y-axis: " + strCY);
                }

                else if(leftSide == false && up == true){
                    girl.bitmapX = 510;
                    girl.bitmapY = 110;
                    girl.invalidate();

                    strCX = Float.toString(girl.bitmapX);
                    strCY = Float.toString(girl.bitmapY);
                    cX.setText("x-axis: " + strCX);
                    cY.setText("y-axis: " + strCY);
                }

                else if(leftSide == false && up == false){
                    girl.bitmapX = 510;
                    girl.bitmapY = 410;
                    girl.invalidate();

                    strCX = Float.toString(girl.bitmapX);
                    strCY = Float.toString(girl.bitmapY);
                    cX.setText("x-axis: " + strCX);
                    cY.setText("y-axis: " + strCY);
                }

                new CountDownTimer(150000, 1000) {
                    // driving portion is 1:45 min
                    public void onTick(long millisUntilFinished) {
                        cT.setText("End: " + millisUntilFinished / 1000);
                        girl.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View view, MotionEvent event) {
                                //设置妹子显示的位置
                                girl.bitmapX = event.getX() - 36;
                                girl.bitmapY = event.getY() - 44;

                                if(leftSide == true)
                                {
                                    if(girl.bitmapX < 0 )
                                    {
                                        girl.bitmapX = 0 ;
                                    }
                                    else if(girl.bitmapX > 240)
                                    {
                                        girl.bitmapX = 240;
                                    }
                                    if(girl.bitmapY < 0)
                                    {
                                        girl.bitmapY = 0;
                                    }
                                    else if(girl.bitmapY > 520)
                                    {
                                        girl.bitmapY = 520;
                                    }
                                }

                                else if (leftSide == false)
                                {
                                    if(girl.bitmapX < 295 )
                                    {
                                        girl.bitmapX = 295 ;
                                    }
                                    else if(girl.bitmapX > 530)
                                    {
                                        girl.bitmapX = 530;
                                    }
                                    if(girl.bitmapY < 0)
                                    {
                                        girl.bitmapY = 0;
                                    }
                                    else if(girl.bitmapY > 520)
                                    {
                                        girl.bitmapY = 520;
                                    }
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
                        if(leftSide == true && up == true) {
                            girl.bitmapX = 15;
                            girl.bitmapY = 110;

                            girl.invalidate();

                            strCX = Float.toString(girl.bitmapX);
                            strCY = Float.toString(girl.bitmapY);
                            cX.setText("x-axis: " + strCX);
                            cY.setText("y-axis: " + strCY);
                        }

                        else if(leftSide == true && up == false){
                            girl.bitmapX = 15;
                            girl.bitmapY = 410;
                            girl.invalidate();

                            strCX = Float.toString(girl.bitmapX);
                            strCY = Float.toString(girl.bitmapY);
                            cX.setText("x-axis: " + strCX);
                            cY.setText("y-axis: " + strCY);
                        }

                        else if(leftSide == false && up == true){
                            girl.bitmapX = 510;
                            girl.bitmapY = 110;
                            girl.invalidate();

                            strCX = Float.toString(girl.bitmapX);
                            strCY = Float.toString(girl.bitmapY);
                            cX.setText("x-axis: " + strCX);
                            cY.setText("y-axis: " + strCY);
                        }

                        else if(leftSide == false && up == false){
                            girl.bitmapX = 510;
                            girl.bitmapY = 410;
                            girl.invalidate();

                            strCX = Float.toString(girl.bitmapX);
                            strCY = Float.toString(girl.bitmapY);
                            cX.setText("x-axis: " + strCX);
                            cY.setText("y-axis: " + strCY);
                        }
                        cT.setText("End!");
                    }
                }.start();

            }
        });
        frame.addView(girl);
        //frame.addView(star);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////

    //public boolean onTouchEvent(MotionEvent event) {
        //return gDetector.onTouchEvent(event);
    //}
    public void findViews() {
        cX = (TextView) findViewById(R.id.coordinateX);
        cY = (TextView) findViewById(R.id.coordinateY);
        cT = (TextView) findViewById(R.id.countDown);
        bAuto = (Button) findViewById(R.id.btnAuto);
        bDriver = (Button) findViewById(R.id.btnDrive);
        sSide = (Switch) findViewById(R.id.stchSide);
        sPoint = (Switch) findViewById(R.id.stchPoint);
        frame = (RelativeLayout) findViewById(R.id.fieldLayout);
        teamNumSpin = (Spinner) findViewById(R.id.spinTeamNum);
        leftStart = (ImageView) findViewById(R.id.leftStart);
        leftInside = (ImageView) findViewById(R.id.leftInside);
        rightStart = (ImageView) findViewById(R.id.rightStart);
        rightInside = (ImageView) findViewById(R.id.rightInside);
        nearRightScore = (TextView) findViewById(R.id.rightNear);
        farRightScore = (TextView) findViewById(R.id.rightFar);
        nearLeftScore = (TextView) findViewById(R.id.leftNear);
        farLeftScore = (TextView) findViewById(R.id.leftFar);
    }
}