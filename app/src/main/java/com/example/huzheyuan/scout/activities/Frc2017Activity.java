package com.example.huzheyuan.scout.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.huzheyuan.scout.R;
import com.example.huzheyuan.scout.scoring.IconView;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import butterknife.ButterKnife;

public class Frc2017Activity extends AppCompatActivity{
    RelativeLayout frame;
    FloatingActionButton fabAuto, fabTeleop, fabQR;
    FloatingActionMenu famAction, famFunction;
    TextView countDownFRC, teamIDText, xyText;
    EditText gameNumber;
    Toolbar toolbar;
    Switch sideSwitch;
    NumberPicker positionPicker;
    String teamID = null, position = "Top";
    String editLabel = "Edit Game Setting";
    String[] positionArray = new String[] {"Top", "Middle", "Bottom"};
    boolean leftSide = true, createGame = false, inGame = false;
    final FloatingActionButton fabEdit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frc2017);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart(){
        super.onStart();
        findView(); // bind all view components
        setSupportActionBar(toolbar);
        toolbar.setTitle("FRC");
        final IconView iconView = new IconView(Frc2017Activity.this, "Frc2017");
        moveIcon(iconView);
        frame.addView(iconView);
        initFamFunction(iconView);
    }

    private void findView(){
        famAction = (FloatingActionMenu) findViewById(R.id.actionFAB);
        famFunction = (FloatingActionMenu) findViewById(R.id.multiFunctionFAB);
        fabAuto = (FloatingActionButton) findViewById(R.id.fabAuto);
        fabTeleop = (FloatingActionButton) findViewById(R.id.fabTeleop);
        fabQR = (FloatingActionButton) findViewById(R.id.fabGenerateQR);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        countDownFRC = (TextView) findViewById(R.id.countDownFrc);
        teamIDText = (TextView) findViewById(R.id.teamIDText);
        xyText = (TextView) findViewById(R.id.xyText);
        frame = (RelativeLayout) findViewById(R.id.include);
    }

    private void showNewGame(final IconView iconView){
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
                        initPosition(leftSide,positionArray[positionPicker.getValue()], iconView);
                        createGame = true;
                        famFunction.close(true);
                        progAddFab(fabEdit, iconView, editLabel);
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

    private void moveIcon(final IconView iconView){
        iconView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                iconView.bitmapX = event.getX() - 36;
                iconView.bitmapY = event.getY() - 44;
                invalidateIcon(iconView);
                return true;
            }
        });
    }

    private void mapBoundary(IconView iconView){ // I made it, it is optimized!
        if(iconView.bitmapX < 110) iconView.bitmapX = 110;
        else if(iconView.bitmapX > 850) iconView.bitmapX = 850;
        if(iconView.bitmapY < 20) iconView.bitmapY = 20;
        else if(iconView.bitmapY > 340) iconView.bitmapY = 340;
    }

    private void invalidateIcon(IconView iconView){
        //调用重绘方法
        mapBoundary(iconView);
        iconView.invalidate();
        String strCX = Float.toString(iconView.bitmapX);
        String strCY = Float.toString(iconView.bitmapY);
        xyText.setText(strCX + "|" + strCY);
    }

    private void pickerInit(NumberPicker picker){
        picker.setMinValue(0);
        picker.setMaxValue(positionArray.length - 1);
        picker.setDisplayedValues(positionArray);
        picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS); // disable keyboard
        picker.setWrapSelectorWheel(false);
    }

    private void initPosition(boolean side, String position, IconView iconView){
        float x,y;
        if(side) x = 130;
        else x = 830;
        if(position.equals("Top")) y = 80;
        else if(position.equals("Middle")) y = 160;
        else y = 240;
        iconView.bitmapX = x;
        iconView.bitmapY = y;
        iconView.invalidate();
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

    private void createCustomAnimation() {
        AnimatorSet set = new AnimatorSet();

        ObjectAnimator scaleOutX = ObjectAnimator.ofFloat(famFunction.getMenuIconView(), "scaleX", 1.0f, 0.2f);
        ObjectAnimator scaleOutY = ObjectAnimator.ofFloat(famFunction.getMenuIconView(), "scaleY", 1.0f, 0.2f);

        ObjectAnimator scaleInX = ObjectAnimator.ofFloat(famFunction.getMenuIconView(), "scaleX", 0.2f, 1.0f);
        ObjectAnimator scaleInY = ObjectAnimator.ofFloat(famFunction.getMenuIconView(), "scaleY", 0.2f, 1.0f);

        scaleOutX.setDuration(50);
        scaleOutY.setDuration(50);

        scaleInX.setDuration(150);
        scaleInY.setDuration(150);

        scaleInX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                famFunction.getMenuIconView().setImageResource(famFunction.isOpened()
                        ? R.drawable.ic_send_black_24dp : R.drawable.ic_add_black_24dp);
            }
        });
        set.play(scaleOutX).with(scaleOutY);
        set.play(scaleInX).with(scaleInY).after(scaleOutX);
        set.setInterpolator(new OvershootInterpolator(2));
        famFunction.setIconToggleAnimatorSet(set);
    }

    private FloatingActionButton progAddFab(FloatingActionButton fab, final IconView iconView, String labelText){
        fab = new FloatingActionButton(this);
        fab.setButtonSize(FloatingActionButton.SIZE_MINI);
        fab.setLabelText(labelText);
        fab.setImageResource(R.drawable.ic_edit_black_24dp);
        famFunction.addMenuButton(fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNewGame(iconView);
            }
        });
        return fab;
    }

    private void progRemoveFab(FloatingActionMenu menu, FloatingActionButton fab){
        menu.removeMenuButton(fab);
    }

    private void initFamFunction(final IconView iconView){
        famAction.bringToFront();
        famFunction.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                if(!opened && !createGame) {
                    progRemoveFab(famFunction,progAddFab(fabEdit, iconView, editLabel));
                    createCustomAnimation();
                }
                if(opened && !createGame && !inGame){
                    showNewGame(iconView);
                }
            }
        });
    }
    private void initScout(){
        fabAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        fabTeleop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    private void timerSystem(CountDownTimer timer, long range, long tick){

    }
}
