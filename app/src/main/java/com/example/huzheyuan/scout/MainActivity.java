package com.example.huzheyuan.scout;

import android.app.Service;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.app.Activity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.os.CountDownTimer;
import android.widget.Toast;
import tyrantgit.explosionfield.ExplosionField;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class MainActivity extends Activity
{
    RelativeLayout frame;
    TextView cX,cY,cT,tDriverStarNear,tDriverStarFar,tDriverCubeNear,tDriverCubeFar;
    TextView tAutoStarNear,tAutoStarFar, tAutoCubeNear,tAutoCubeFar ;
    ImageView starPic,cubePic;
    Button nearRightScore,farRightScore,nearLeftScore,farLeftScore,bAuto,bDriver,bClear;
    Switch sSide,sPoint;
    Spinner teamNumSpin;
    CheckBox lifted;
    String strCX,strCY,strCAN,strCAF,strCDN,strCDF,strSAN,strSAF,strSDN,strSDF,teamNumber;
    String[] teamNumberArray;
    int cubeAutoNear = 0,cubeAutoFar = 0,starAutoNear = 0,starAutoFar = 0;
    int cubeDriverNear = 0,cubeDriverFar = 0,starDriverNear = 0,starDriverFar = 0;
    boolean leftSide = true,up = true,liftedGirl = false,autoMode = false;
    boolean visibilityStar = false,visibilityCube = false; //Set all the variables
    CountDownTimer cuteDriver = null,cuteAuto = null;
    ExplosionField boom;
    DataBaseContext dBContext;
    DataBaseHelper dataBaseHelper;
    SQLiteDatabase dB;
    Vibrator girlFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final GirlView girl = new GirlView(MainActivity.this); // finalize the girl GirlView, which
        //you will understand soon...
        boom = ExplosionField.attach2Window(this);
        startDataBase();
        findViews();
        clear();
        lift();
        //Don't even try to make these switchers into method! And don't ask me why, think, plus I've
        //done the experiment already!
        //Switchers
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
        teamNumSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                teamNumberArray = getResources().getStringArray(R.array.data);
                teamNumber = teamNumberArray[position];
                Toast.makeText(MainActivity.this, "Team: "+teamNumberArray[position], Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //Auto mode starts
        bAuto.setOnClickListener(new View.OnClickListener() { //Auto start button
            @Override
            public void onClick(View v) {
                autoMode = true;
                Toast.makeText(MainActivity.this, "Auto Mode Starts", Toast.LENGTH_SHORT).show();
                timerBug();
                cuteAuto = new CountDownTimer(15000, 1000) {
                    // Auto time limits 15s
                    public void onTick(long millisUntilFinished) {
                        scoreStarTally();
                        scoreCubeTally();
                        cT.setText("End: " + millisUntilFinished / 1000);
                        //the tallies are for counting the scoring actions ...
                        //Then, this is the code for drawing a girlon the screen!!!
                        girl.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View view, MotionEvent event) {
                                //设置妹子显示的位置, set the girl's position
                                girl.bitmapX = event.getX() - 36;
                                girl.bitmapY = event.getY() - 44;
                                // the only reason why I minus number from the position is you can
                                // try to remove it and you will know why!!!
                                drawGirl(girl);
                                return true;
                            }
                        });
                    }
                    public void onFinish(){ // on finish is a important method to know, learn it!

                        resetGirl(girl);
                        cT.setText("End!");
                    }
                }.start(); // end of the timer
            }
        });
        //Driver Mode Start, same structure and usage as auto mode
        bDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoMode = false;
                Toast.makeText(MainActivity.this, "Driver Mode Starts", Toast.LENGTH_SHORT).show();
                timerBug();
                cuteDriver = new CountDownTimer(105000, 1000) { // Driver time limits 105s
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
        frame.addView(girl); // add a little cute girl on the screen, important!!!
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    // Here is all the methods I am using!!!
    // Try to make it explicit ...
    public void findViews() {
        cX = (TextView) findViewById(R.id.coordinateX);
        cY = (TextView) findViewById(R.id.coordinateY);
        cT = (TextView) findViewById(R.id.countDown);
        tDriverStarNear = (TextView) findViewById(R.id.textDriverStarNear);
        tDriverStarFar = (TextView) findViewById(R.id.textDriverStarFar);
        tDriverCubeNear = (TextView) findViewById(R.id.textDriverCubeNear);
        tDriverCubeFar = (TextView) findViewById(R.id.textDriverCubeFar);
        tAutoStarNear = (TextView) findViewById(R.id.textAutoStarNear);
        tAutoStarFar = (TextView) findViewById(R.id.textAutoStarFar);
        tAutoCubeNear = (TextView) findViewById(R.id.textAutoCubeNear);
        tAutoCubeFar = (TextView) findViewById(R.id.textAutoCubeFar);
        starPic = (ImageView) findViewById(R.id.popStarView);
        cubePic = (ImageView) findViewById(R.id.popCubeView);
        bAuto = (Button) findViewById(R.id.btnAuto);
        bDriver = (Button) findViewById(R.id.btnDrive);
        bClear = (Button) findViewById(R.id.btnClear);
        nearRightScore = (Button) findViewById(R.id.rightNear);
        farRightScore = (Button) findViewById(R.id.rightFar);
        nearLeftScore = (Button) findViewById(R.id.leftNear);
        farLeftScore = (Button) findViewById(R.id.leftFar);
        sSide = (Switch) findViewById(R.id.stchSide);
        sPoint = (Switch) findViewById(R.id.stchPoint);
        teamNumSpin = (Spinner) findViewById(R.id.spinTeamNum);
        lifted = (CheckBox) findViewById(R.id.Checklifted);
        frame = (RelativeLayout) findViewById(R.id.fieldLayout);
        girlFriend = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
    }
    public void clear() // Highly emergency issues right here!!! The clear method is broken and not function too properly.
    //It need to be rewritten and clarified!!!
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
                visibilityStar = false;
                visibilityCube = false;
                teamNumSpin.setSelection(0);
                lifted.setChecked(false);
                strSDN = Integer.toString(starDriverNear);
                tDriverStarNear.setText("Near Star: " +strSDN);
                strSDF = Integer.toString(starDriverFar);
                tDriverStarFar.setText("Far Star: " + strSDF);
                strCDN = Integer.toString(cubeDriverNear);
                tDriverCubeNear.setText("Near Cube: " +strCDN);
                strCDF = Integer.toString(cubeDriverFar);
                tDriverCubeFar.setText("Far Cube: " + strCDF);
                strSAN = Integer.toString(starAutoNear);
                tAutoStarNear.setText("Near Star: " +strSAN);
                strSAF = Integer.toString(starAutoFar);
                tAutoStarFar.setText("Far Star: " + strSAF);
                strCAN = Integer.toString(cubeAutoNear);
                tAutoCubeNear.setText("Near Cube: " +strCAN);
                strCAF = Integer.toString(cubeAutoFar);
                tAutoCubeFar.setText("Far Cube: " + strCAF);
                popStar();
                popCube();
                //end all the running timer!
                if(cuteDriver != null){
                    cuteDriver.cancel();
                    cuteDriver.onFinish();
                }
                if (cuteAuto != null){
                    cuteAuto.cancel();
                    cuteAuto.onFinish();
                } // need to clear all the textView to 0 or empty!
            }
        });
    }
    public void drawGirl(GirlView girl) {
        mapBoundary(girl);
        invalidateGirl(girl);
    }
    public void mapBoundary(GirlView girl){
        // For the next person trying to "optimize" this code, please increment the following counter for the
        // the next fool who doesn't read this:
        // HoursWasted = 42 hours;    ------ by Eddie, a little freshman who tried this!
        if(leftSide) {
            if (girl.bitmapX < 0) girl.bitmapX = 0;
            else if (girl.bitmapX > 240) girl.bitmapX = 240;
            if (girl.bitmapY < 0) girl.bitmapY = 0;
            else if (girl.bitmapY > 520) girl.bitmapY = 520;
        }
        else if(!leftSide) {
            if (girl.bitmapX < 295) girl.bitmapX = 295;
            else if (girl.bitmapX > 530) girl.bitmapX = 530;
            if (girl.bitmapY < 0) girl.bitmapY = 0;
            else if (girl.bitmapY > 520) girl.bitmapY = 520;
        }
    }
    public void invalidateGirl(GirlView girl){
        //调用重绘方法
        girl.invalidate();
        strCX = Float.toString(girl.bitmapX);
        strCY = Float.toString(girl.bitmapY);
        cX.setText("x-axis: " + strCX);
        cY.setText("y-axis: " + strCY);
    }
    public void resetGirl(GirlView girl) {
        if(leftSide && up) {
            girl.bitmapX = 15;
            girl.bitmapY = 110;
            invalidateGirl(girl);
        }
        else if(leftSide && !up){
            girl.bitmapX = 15;
            girl.bitmapY = 410;
            invalidateGirl(girl);
        }
        else if(!leftSide && up){
            girl.bitmapX = 510;
            girl.bitmapY = 110;
            invalidateGirl(girl);
        }
        else if(!leftSide && !up){
            girl.bitmapX = 510;
            girl.bitmapY = 410;
            invalidateGirl(girl);
        }
    }
    // Driver mode score counters
    public void driverNearStar() {
        ++starDriverNear;
        strSDN = Integer.toString(starDriverNear);
        tDriverStarNear.setText("Near Star: " +strSDN);
    }
    public void driverFarStar() {
        starDriverFar = starDriverFar + 2;
        strSDF = Integer.toString(starDriverFar);
        tDriverStarFar.setText("Far Star: " + strSDF);
    }
    public void driverNearCube() {
        cubeDriverNear = cubeDriverNear + 2;
        strCDN = Integer.toString(cubeDriverNear);
        tDriverCubeNear.setText("Near Cube: " +strCDN);
    }
    public void driverFarCube() {
        cubeDriverFar = cubeDriverFar + 4;
        strCDF = Integer.toString(cubeDriverFar);
        tDriverCubeFar.setText("Far Cube: " + strCDF);
    }
    // Auto mode score counters
    public void autoNearStar() {
        ++starAutoNear;
        strSAN = Integer.toString(starAutoNear);
        tAutoStarNear.setText("Near Star: " +strSAN);
    }
    public void autoFarStar() {
        starAutoFar = starAutoFar + 2;
        strSAF = Integer.toString(starAutoFar);
        tAutoStarFar.setText("Far Star: " + strSAF);
    }
    public void autoNearCube() {
        cubeAutoNear = cubeAutoNear + 2;
        strCAN = Integer.toString(cubeAutoNear);
        tAutoCubeNear.setText("Near Cube: " +strCAN);
    }
    public void autoFarCube() {
        cubeAutoFar = cubeAutoFar + 4;
        strCAF = Integer.toString(cubeAutoFar);
        tAutoCubeFar.setText("Far Cube: " + strCAF);
    }
    public void scoreStarTally() {
        if(leftSide) {
            farRightScore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(autoMode) autoFarStar();
                    else driverFarStar();
                    visibilityStar = true;
                    visibilityCube = false;
                    popCube();
                    popStar();
                }
            });
            nearRightScore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(autoMode) autoNearStar();
                    else driverNearStar();
                    visibilityStar = true;
                    visibilityCube = false;
                    popCube();
                    popStar();
                }
            });
        }
        else if (!leftSide) {
            farLeftScore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(autoMode)autoFarStar();
                    else driverFarStar();
                    visibilityStar = true;
                    visibilityCube = false;
                    popCube();
                    popStar();
                }
            });
            nearLeftScore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(autoMode) autoNearStar();
                    else driverNearStar();
                    visibilityStar = true;
                    visibilityCube = false;
                    popCube();
                    popStar();
                }
            });
        }
    }
    public void scoreCubeTally() {
        if(leftSide) {
            farRightScore.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(autoMode)autoFarCube();
                    else driverFarCube();
                    visibilityCube = true;
                    visibilityStar = false;
                    popStar();
                    popCube();
                    excited();
                    return true;
                }
            });

            nearRightScore.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(autoMode) autoNearCube();
                    else driverNearCube();
                    visibilityCube = true;
                    visibilityStar = false;
                    popStar();
                    popCube();
                    excited();
                    return true;
                }
            });
        }
        else if (!leftSide) {
            farLeftScore.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(autoMode)autoFarCube();
                    else driverFarCube();
                    visibilityCube = true;
                    visibilityStar = false;
                    popStar();
                    popCube();
                    excited();
                    return true;
                }
            });
            nearLeftScore.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(autoMode) autoNearCube();
                    else driverNearCube();
                    visibilityStar = false;
                    visibilityCube = true;
                    popStar();
                    popCube();
                    excited();
                    return true;
                }
            });
        }
    }
    public void excited(){ // this is a method for vibrator, which you should know how to use it!
        girlFriend.cancel();
        girlFriend.vibrate(500);
    }
    public void lift() {
        lifted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(lifted.isChecked()) liftedGirl = true;
                else liftedGirl = false;
                System.out.println(liftedGirl);
            }
        });
    }
    public void popStar() {
        if(visibilityStar) starPic.setVisibility(View.VISIBLE);
        else starPic.setVisibility(View.GONE);
    }
    public void popCube() {
        if (visibilityCube) cubePic.setVisibility(View.VISIBLE);
        else cubePic.setVisibility(View.GONE);
    }
    public void timerBug(){
        if(cuteDriver != null){
            cuteDriver.cancel();
            cuteDriver.onFinish();
        }
        if (cuteAuto != null){
            cuteAuto.cancel();
            cuteAuto.onFinish();
        }
    }// the if statements here are for avoiding multiple countdown timer bug!!!
    //start the timer for auto

    public void startDataBase() {
        dataBaseHelper = new DataBaseHelper(MainActivity.this);
        dataBaseHelper.getReadableDatabase();
        dataBaseHelper.getWritableDatabase();
        File f = new File(getExternalFilesDir(null).getAbsolutePath() + File.separator + dataBaseHelper.DATABASE_NAME);// 创建文件
        if(f.exists()) {
            Toast.makeText(MainActivity.this, "Database has been created", Toast.LENGTH_SHORT).show();
            System.out.println(f);
//            try {
//                FileOutputStream output = new FileOutputStream(f);
//                f.setReadable(true);
//                f.setWritable(true);
//                output.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }
}