package com.example.huzheyuan.scout;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.os.CountDownTimer;

public class MainActivity extends Activity
{
    RelativeLayout frame;
    TextView cX;
    TextView cY;
    TextView cT;
    TextView tStarNear;
    TextView tStarFar;
    TextView tCubeNear;
    TextView tCubeFar;
    Button nearRightScore;
    Button farRightScore;
    Button nearLeftScore;
    Button farLeftScore;
    Button bAuto;
    Button bDriver;
    Button bClear;
    Switch sSide;
    Switch sPoint;
    Spinner teamNumSpin;
    CheckBox lifted;
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
    boolean liftedGirl = false;
    CountDownTimer cuteDriver = null;
    CountDownTimer cuteAuto = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final GirlView girl = new GirlView(MainActivity.this);
        findViews();
        clear();
        lift();
        sSide.setChecked(false);
        sSide.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) leftSide = false;
                else leftSide = true;
                resetGirl(girl);
            }
        });

        sPoint.setChecked(false);
        sPoint.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) up = false;
                else up = true;
                resetGirl(girl);
            }
        });

        //Auto mode starts
        bAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cuteDriver != null){
                    cuteDriver.cancel();
                    cuteDriver.onFinish();
                }
                if (cuteAuto != null){
                    cuteAuto.cancel();
                    cuteAuto.onFinish();
                }
                cuteAuto = new CountDownTimer(15000, 1000) {
                    // Auto time limits 15s
                    public void onTick(long millisUntilFinished) {
                        cT.setText("End: " + millisUntilFinished / 1000);
                        scoreStarTally();
                        scoreCubeTally();
                        girl.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View view, MotionEvent event) {
                                //设置妹子显示的位置
                                girl.bitmapX = event.getX() - 36;
                                girl.bitmapY = event.getY() - 44;
                                drawGirl(girl);
                                return true;
                            }
                        });
                    }
                    public void onFinish(){
                        resetGirl(girl);
                        cT.setText("End!");
                    }
                }.start();
            }
        });

        //Driver Mode Start
        bDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cuteDriver != null){
                    cuteDriver.cancel();
                    cuteDriver.onFinish();
                }
                if (cuteAuto != null){
                    cuteAuto.cancel();
                    cuteAuto.onFinish();
                }
                cuteDriver = new CountDownTimer(105000, 1000) {
                    // Driver time limits 105s
                    public void onTick(long millisUntilFinished) {
                        cT.setText("End: " + millisUntilFinished / 1000);
                        scoreStarTally();
                        scoreCubeTally();
                        girl.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View view, MotionEvent event) {
                                //设置妹子显示的位置
                                girl.bitmapX = event.getX() - 36;
                                girl.bitmapY = event.getY() - 44;
                                drawGirl(girl);
                                return true;
                            }
                        });
                    }
                    public void onFinish(){
                        resetGirl(girl);
                        cT.setText("End!");
                    }
                }.start();
            }
        });
        frame.addView(girl);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    public void findViews() {
        cX = (TextView) findViewById(R.id.coordinateX);
        cY = (TextView) findViewById(R.id.coordinateY);
        cT = (TextView) findViewById(R.id.countDown);
        tStarNear = (TextView) findViewById(R.id.textStarNear);
        tStarFar = (TextView) findViewById(R.id.textStarFar);
        tCubeNear = (TextView) findViewById(R.id.textCubeNear);
        tCubeFar = (TextView) findViewById(R.id.textCubeFar);
        bAuto = (Button) findViewById(R.id.btnAuto);
        bDriver = (Button) findViewById(R.id.btnDrive);
        bClear = (Button) findViewById(R.id.btnClear);
        sSide = (Switch) findViewById(R.id.stchSide);
        sPoint = (Switch) findViewById(R.id.stchPoint);
        frame = (RelativeLayout) findViewById(R.id.fieldLayout);
        teamNumSpin = (Spinner) findViewById(R.id.spinTeamNum);
        nearRightScore = (Button) findViewById(R.id.rightNear);
        farRightScore = (Button) findViewById(R.id.rightFar);
        nearLeftScore = (Button) findViewById(R.id.leftNear);
        farLeftScore = (Button) findViewById(R.id.leftFar);
        lifted = (CheckBox) findViewById(R.id.Checklifted);
    }

    public void clear()
    {
        bClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cubeAutoNear = 0;
                cubeAutoFar = 0;
                starAutoNear = 0;
                starAutoFar = 0;
                cubeDriverNear = 0;
                cubeDriverFar = 0;
                starDriverNear = 0;
                starDriverFar = 0;
                liftedGirl = false;
                lifted.setChecked(false);
                if(cuteDriver != null){
                    cuteDriver.cancel();
                    cuteDriver.onFinish();
                }
                if (cuteAuto != null){
                    cuteAuto.cancel();
                    cuteAuto.onFinish();
                }
                if(leftSide == true)
                {
                    strSAF = Integer.toString(starAutoFar);
                    farRightScore.setText(strSAF);
                    strSAN = Integer.toString(starAutoNear);
                    nearRightScore.setText(strSAN);
                    strSDF = Integer.toString(starDriverFar);
                    farRightScore.setText(strSDF);
                    strSDN = Integer.toString(starDriverNear);
                    nearRightScore.setText(strSDN);
                }
                else if(leftSide == false)
                {
                    strSAF = Integer.toString(starAutoFar);
                    farLeftScore.setText(strSAF);
                    strSAN = Integer.toString(starAutoNear);
                    nearLeftScore.setText(strSAN);
                    strSDF = Integer.toString(starDriverFar);
                    farLeftScore.setText(strSDF);
                    strSDN = Integer.toString(starDriverNear);
                    nearLeftScore.setText(strSDN);
                }
            }
        });
    }

    public void drawGirl(GirlView girl)
    {
        // For the next person trying to "optimize" this code, please increment the following counter for the
        // the next fool who doesn't read this:
        // HoursWasted = 42 hours;
                if(leftSide == true) {
                    if (girl.bitmapX < 0) girl.bitmapX = 0;
                    else if (girl.bitmapX > 240) girl.bitmapX = 240;
                    if (girl.bitmapY < 0) girl.bitmapY = 0;
                    else if (girl.bitmapY > 520) girl.bitmapY = 520;
                }
                else if(leftSide == false) {
                    if (girl.bitmapX < 295) girl.bitmapX = 295;
                    else if (girl.bitmapX > 530) girl.bitmapX = 530;
                    if (girl.bitmapY < 0) girl.bitmapY = 0;
                    else if (girl.bitmapY > 520) girl.bitmapY = 520;
                }
                //调用重绘方法
                girl.invalidate();
                strCX = Float.toString(girl.bitmapX);
                strCY = Float.toString(girl.bitmapY);
                cX.setText("x-axis: " + strCX);
                cY.setText("y-axis: " + strCY);
            }

    public void resetGirl(GirlView girl)
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
    }

    public void scoreStarTally()
    {
        if(leftSide == true) {
            farRightScore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    starDriverFar = starDriverFar + 2;
                    System.out.println(starDriverFar);
                    strSDF = Integer.toString(starDriverFar);
                    tStarFar.setText("Far Star: " + strSDF);
                }
            });
            nearRightScore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ++starDriverNear;
                    System.out.println(starDriverNear);
                    strSDN = Integer.toString(starDriverNear);
                    tStarNear.setText("Near Star: " +strSDN);
                }
            });
        }
        else if (leftSide == false) {
            farLeftScore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    starDriverFar = starDriverFar + 2;
                    strSDF = Integer.toString(starDriverFar);
                    tStarFar.setText("Far Star: " +strSDF);
                }
            });
            nearLeftScore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ++starDriverNear;
                    strSDN = Integer.toString(starDriverNear);
                    tStarNear.setText("Near Star: " +strSDN);
                }
            });
        }
    }

    public void scoreCubeTally()
    {
        if(leftSide == true) {
            farRightScore.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    cubeDriverFar = cubeDriverFar + 4;
                    System.out.println(cubeDriverFar);
                    strCDF = Integer.toString(cubeDriverFar);
                    tCubeFar.setText("Far Cube: " + strCDF);
                    return true;
                }
            });

            nearRightScore.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    cubeDriverNear = cubeDriverNear + 2;
                    System.out.println(cubeDriverNear);
                    strCDN = Integer.toString(cubeDriverNear);
                    tCubeNear.setText("Near Cube: " +strCDN);
                    return true;
                }
            });
        }
        else if (leftSide == false) {
            farRightScore.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    cubeDriverFar = cubeDriverFar + 4;
                    System.out.println(cubeDriverFar);
                    strCDF = Integer.toString(cubeDriverFar);
                    tCubeFar.setText("Far Cube: " + strCDF);
                    return true;
                }
            });
            nearRightScore.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    cubeDriverNear = cubeDriverNear + 2;
                    System.out.println(cubeDriverNear);
                    strCDN = Integer.toString(cubeDriverNear);
                    tCubeNear.setText("Near Cube: " + strCDN);
                    return true;
                }
            });
        }
    }

    public void lift()
    {
        lifted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(lifted.isChecked()) liftedGirl = true;
                else liftedGirl = false;
                System.out.println(liftedGirl);
            }
        });
    }
}