package com.example.huzheyuan.scout.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.huzheyuan.scout.R;
import com.example.huzheyuan.scout.fragment.NewGameFragment;
import com.github.clans.fab.FloatingActionButton;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Frc2017Activity extends AppCompatActivity{
    Button btnNewGame;
    FloatingActionButton fabAuto, fabTeleop, fabQR;
    NumberPicker numberPickerID;
    EditText gameNumber;

    final FragmentManager fragmentManager = getSupportFragmentManager();
    final NewGameFragment newGameFragment = new NewGameFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frc2017);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        findView(); // bind all view components

    }
    @Override
    protected void onStart(){
        super.onStart();
    }

    private void findView(){
        fabAuto = (FloatingActionButton) findViewById(R.id.fabAuto);
        fabTeleop = (FloatingActionButton) findViewById(R.id.fabTeleop);
        fabQR = (FloatingActionButton) findViewById(R.id.fabGenerateQR);
        numberPickerID = (NumberPicker) findViewById(R.id.numberPickerID);
    }
    @OnClick(R.id.btnNewGame)
    public void showNewGame(){
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title("Create New Game")
                .customView(R.layout.newgamedialog,true)
                .positiveText("Start")
                .negativeText("Cancel")
                .build();
        gameNumber = (EditText) dialog.getCustomView().findViewById(R.id.inputGameId);
    }
}
