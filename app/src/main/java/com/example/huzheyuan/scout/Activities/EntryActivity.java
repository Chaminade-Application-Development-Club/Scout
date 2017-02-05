package com.example.huzheyuan.scout.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.huzheyuan.scout.R;

public class EntryActivity extends AppCompatActivity {
    ImageView vex2017;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        findView();
        enterGames();
    }

    public void findView(){
        vex2017 = (ImageView) findViewById(R.id.vexGame2017);
    }
    public void enterGames(){
        vex2017.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EntryActivity.this,VexActivity.class));
            }
        });
    }
}
