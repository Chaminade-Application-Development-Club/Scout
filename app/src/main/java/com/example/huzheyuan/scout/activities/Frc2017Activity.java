package com.example.huzheyuan.scout.activities;

import android.Manifest;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.huzheyuan.scout.R;
import com.example.huzheyuan.scout.qRCode.QRUtils;
import com.example.huzheyuan.scout.realmService.FrcSteamRealm;
import com.example.huzheyuan.scout.realmService.RealmUtils;
import com.example.huzheyuan.scout.utilities.FloatActionUtil;
import com.example.huzheyuan.scout.utilities.IconUtil;
import com.example.huzheyuan.scout.utilities.ScoreUtil;
import com.example.huzheyuan.scout.utilities.ScreenUtil;
import com.example.huzheyuan.scout.utilities.TimerUtils;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.zxing.WriterException;
import com.yanzhenjie.permission.AndPermission;

import java.util.LinkedList;

import butterknife.ButterKnife;
import io.realm.Realm;
import static android.content.ContentValues.TAG;

public class Frc2017Activity extends AppCompatActivity{

    RelativeLayout frame;
    FloatingActionButton fabAuto, fabTeleop, fabQR, fabEdit, fabReset, fabLeftGear, fabRightGear,
    fabLeftFuel, fabRightFuel;
    FloatingActionMenu famAction, famFunction;
    FloatActionUtil fAU;
    TextView countDownFRC, teamIDText, xyText;
    EditText gameNumber;
    Toolbar toolbar;
    Switch sideSwitch;
    NumberPicker positionPicker;

    int gear = 0, lowGoal = 0, highGoal = 0;
    int startTime = 0, nowTime = 0;
    long time = 0;
    float x = 0, y = 0;
    String gameID = "", teamID = "", initPos = "Top";
    String editLabel = "Edit Game Setting";
    String[] positionArray = new String[] {"Top", "Middle", "Bottom"};
    boolean leftSide = true, createGame = false, inGame = false, lifted = false;
    boolean gameModeAuto = true, waste = true;
    boolean isHighGoal = false;
    private LinkedList<FloatActionUtil.ProgressType> mProgressTypes;
    private int mMaxProgress = 100;

    Realm realm;
    CountDownTimer autoTimer, teleopTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frc2017);
        ButterKnife.bind(this);
        runTimePermission();
        Realm.init(this);
        realm = RealmUtils.openOrCreateRealm(this,"frcData.realm");
        findView(); // bind all view components
        setSupportActionBar(toolbar);
        final IconUtil iconUtil = new IconUtil(Frc2017Activity.this, "Frc2017");
        initFamFunction(iconUtil);
        initScout(iconUtil);
        frame.addView(iconUtil);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TimerUtils tU = new TimerUtils();
        tU.timerBug(autoTimer,teleopTimer);
        realm.close(); // Remember to close Realm when done.
    }

    private void findView(){
        famFunction = (FloatingActionMenu) findViewById(R.id.multiFunctionFAB);
        fabAuto = (FloatingActionButton) findViewById(R.id.fabAuto);
        fabTeleop = (FloatingActionButton) findViewById(R.id.fabTeleop);
        fabQR = (FloatingActionButton) findViewById(R.id.fabGenerateQR);
        fabEdit = (FloatingActionButton) findViewById(R.id.fabEdit);
        fabReset = (FloatingActionButton) findViewById(R.id.fabReset);
        fabLeftGear = (FloatingActionButton) findViewById(R.id.redGear);
        fabRightGear = (FloatingActionButton) findViewById(R.id.blueGear);
        fabLeftFuel = (FloatingActionButton) findViewById(R.id.leftFuel);
        fabRightFuel = (FloatingActionButton) findViewById(R.id.rightFuel);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        countDownFRC = (TextView) findViewById(R.id.countDownFrc);
        teamIDText = (TextView) findViewById(R.id.teamIDText);
        xyText = (TextView) findViewById(R.id.xyText);
        frame = (RelativeLayout) findViewById(R.id.include);
    }

    private void showNewGame(final IconUtil iconUtil){
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title("Create New Game")
                .customView(R.layout.newgamedialog,true)
                .positiveText("Start")
                .negativeText("Cancel")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        gameNumber = (EditText) dialog.getCustomView().findViewById(R.id.inputTeamId);
                        teamID = gameNumber.getText().toString();
                        teamIDText.setText("Team " + teamID);
                        initPos = positionArray[positionPicker.getValue()];
                        initPosition(leftSide,positionArray[positionPicker.getValue()], iconUtil);
                        createGame = true;
                        famFunction.close(true);
                        fabEdit.showButtonInMenu(true);
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback(){
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        famFunction.close(true);
                    }
                })
                .build();
        positionPicker = (NumberPicker) dialog.getCustomView().findViewById(R.id.positionPicker);
        pickerInit(positionPicker);
        sideSwitch = (Switch) dialog.getCustomView().findViewById(R.id.sideSwitch);
        changeSide(sideSwitch);
        dialog.show();
    }

    private void pickerInit(NumberPicker picker){
        picker.setMinValue(0);
        picker.setMaxValue(positionArray.length - 1);
        picker.setDisplayedValues(positionArray);
        picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS); // disable keyboard
        picker.setWrapSelectorWheel(false);
    }

    private void initPosition(boolean side, String position, IconUtil iconUtil){
        ScreenUtil screenUtil = new ScreenUtil();
        float x,y;
        if(side) x = (float)130/(float)1024 * ((float)screenUtil.getWidth(this));
        else x = (float)830/(float)1024 * ((float)screenUtil.getWidth(this));
        if(position.equals("Top")) y = (float)80/(float)552 * ((float)screenUtil.getHeight(this));
        else if(position.equals("Middle"))
            y = (float)160/(float)552 * ((float)screenUtil.getHeight(this));
        else y = (float)240/(float)552 * ((float)screenUtil.getHeight(this));
        iconUtil.bitmapX = x;
        iconUtil.bitmapY = y;
        iconUtil.invalidate();
    }

    private void changeSide(Switch mSwitch){
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) leftSide = false;
                else leftSide = true;
            }
        });
    }

    public void moveIcon(final IconUtil iconUtil){
        iconUtil.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(!famFunction.isOpened()){ //User Friendly Feature, prevent misbehaviour
                    iconUtil.bitmapX =
                            event.getX()-36 * getResources().getDisplayMetrics().density;
                    iconUtil.bitmapY =
                            event.getY()-44 * getResources().getDisplayMetrics().density;
                    invalidateIcon(Frc2017Activity.this,iconUtil);
                }
                return true;
            }
        });
    }

    public void invalidateIcon (Activity activity, IconUtil iconUtil){
        //调用重绘方法
        ScoreUtil su = new ScoreUtil();
        su.mapBoundary(activity,iconUtil);
        iconUtil.invalidate();
        x = iconUtil.bitmapX;
        y = iconUtil.bitmapY;
    }

    void editAction(final IconUtil iconUtil){
        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNewGame(iconUtil);
            }
        });
    }

    void resetAction(){
        fabReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(autoTimer != null) {
                    autoTimer.cancel();
                    autoTimer.onFinish();
                }
                if (teleopTimer != null){
                    teleopTimer.cancel();
                    teleopTimer.onFinish();
                }
                resetValue();
                famFunction.close(true);
            }
        });
    }

    void gearAction(){
        fabLeftGear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inGame){
                    gear++;
                }
            }
        });
        fabRightGear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inGame){
                    gear++;
                }
            }
        });
    }

    void fuelAction(){
        fAU= new FloatActionUtil();
        fabLeftFuel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inGame){
                    lowGoal++;
                }
            }
        });
        fabRightFuel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inGame){
                    lowGoal++;
                };
            }
        });
        progressUtil(fabLeftFuel);
        progressUtil(fabRightFuel);
    }

    private void initFamFunction(final IconUtil iconUtil){
        fAU = new FloatActionUtil();
        famFunction.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                if(!opened && !createGame) {
                    fabEdit.hideButtonInMenu(true);
                    fAU.createMenuAnimation(famFunction);
                }
                if(opened && !createGame && !inGame){
                    fabEdit.hideButtonInMenu(true);
                    fabReset.hideButtonInMenu(true);
                    showNewGame(iconUtil);
                }
                if(opened && createGame){
                    editAction(iconUtil);
                    resetAction();
                }
            }
        });
    }
    private void initScout(final IconUtil iconUtil){
        final TimerUtils t = new TimerUtils();
        fabAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                famFunction.close(true);
                inGame = true;
                moveIcon(iconUtil);
                fabReset.showButtonInMenu(true);
                t.timerBug(autoTimer, teleopTimer);
                autoTimer = timerSystem(15000,1000, iconUtil);
                gearAction();
                fuelAction();
                gameModeAuto = true;
            }
        });
        fabTeleop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                famFunction.close(true);
                inGame = true;
                moveIcon(iconUtil);
                fabReset.showButtonInMenu(true);
                t.timerBug(autoTimer,teleopTimer);
                teleopTimer = timerSystem(105000,1000, iconUtil);
                gearAction();
                fuelAction();
                gameModeAuto = false;
            }
        });
    }

    private CountDownTimer timerSystem (long range, long tick, final IconUtil iconUtil) {
        final ScoreUtil sUtil = new ScoreUtil();
        return new CountDownTimer(range,tick){
            public void onTick(long millisUntilFinished){
                //insertRealm();
                fabAuto.hideButtonInMenu(true);
                fabTeleop.hideButtonInMenu(true);
                time = millisUntilFinished/1000;
                countDownFRC.setText("Ends in: " + millisUntilFinished/1000);
                insertRealm();
            }
            public void onFinish() {
                inGame = false;
                fabAuto.showButtonInMenu(true);
                fabTeleop.showButtonInMenu(true);
                fabLeftFuel.hideProgress();
                fabRightFuel.hideProgress();
                initPosition(leftSide,initPos, iconUtil);
                countDownFRC.setText("End!");
                summarize();
            }
        }.start();
    }

    private void insertRealm(){
        realm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm){
                FrcSteamRealm frcSteamRealm = realm.createObject(FrcSteamRealm.class);
                frcSteamRealm.setGameID(gameID);
                frcSteamRealm.setTeamName(teamID);
                frcSteamRealm.setGameMode(gameModeAuto);
                frcSteamRealm.setTime(time);
                frcSteamRealm.setLifted(lifted);
                frcSteamRealm.setPositionX(x);
                frcSteamRealm.setPositionY(y);
                frcSteamRealm.setGear(gear);
                frcSteamRealm.setLowGoal(lowGoal);
                frcSteamRealm.setHighGoal(highGoal);
            }
        });
    }

    private void runTimePermission(){
        AndPermission.with(this)
                .requestCode(100)
                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .send();
    }

    public void progressUtil(final FloatingActionButton fab){
        final TimerUtils tU = new TimerUtils();
        mProgressTypes = new LinkedList<>();
        for (FloatActionUtil.ProgressType type : FloatActionUtil.ProgressType.values()) {
            mProgressTypes.offer(type);
        }
        fab.setMax(mMaxProgress);
        fab.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP && isHighGoal){
                    nowTime= tU.timeNow();
                    fab.hideProgress();
                    highGoal += tU.difference(startTime,nowTime);
                    Log.d(TAG, "onTouch: " + highGoal);
                    isHighGoal = false;
                }
                return false;
            }
        });
        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startTime = tU.startTimer();
                isHighGoal = true;
                FloatActionUtil.ProgressType type = mProgressTypes.poll();
                Log.d(TAG, "onLongClick: " + type);
                fab.setShowProgressBackground(false);
                fab.setIndeterminate(true);
                mProgressTypes.offer(FloatActionUtil.ProgressType.PROGRESS_NO_BACKGROUND);
                return false;
            }
        });
    }

    private void summarize(){
        fabQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qRDialog();
            }
        });
    }

    private void qRDialog(){
        QRUtils qr = new QRUtils();
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title("QR Code")
                .customView(R.layout.frcqr_dialog,true)
                .positiveText("Save")
                .negativeText("Cancel")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        famFunction.close(true);
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback(){
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        famFunction.close(true);
                    }
                })
                .build();
        String sum = gameID + "\n" + teamID + "\n" + gameModeAuto + "\n" + time + "\n" + lifted
                + "\n" + gear + "\n" + lowGoal + "\n" + highGoal;
        ImageView qRCodeImage = (ImageView) dialog.getCustomView().findViewById(R.id.QRView);
        TextView sumText = (TextView) dialog.getCustomView().findViewById(R.id.summarizeText);
        sumText.setText(sum);
        try {
            // 调用方法createCode生成二维码, using createCode method to create QR code
            Bitmap bm = qr.createCode(Frc2017Activity.this,sum);
            // 将二维码在界面中显示, show it in the UI
            qRCodeImage.setImageBitmap(bm);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        dialog.show();
    }

    private void resetValue(){
        gameID = "";
        teamID = "";
        gameModeAuto = true;
        time = 0;
        lifted = false;
        x = 0;
        y = 0;
        gear = 0;
        lowGoal = 0;
        highGoal = 0;
        inGame = false;
        createGame = false;
    }
}
