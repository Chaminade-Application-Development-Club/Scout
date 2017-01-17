package com.example.huzheyuan.scout;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.example.huzheyuan.scout.QRCode.QRUtils;
import com.google.zxing.WriterException;

public class QRcode extends AppCompatActivity {
    QRUtils qrUtils;
    ImageView QRCodeImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabGenerateQR);
        qrUtils = new QRUtils();
        QRCodeImage = (ImageView) findViewById(R.id.QRPicView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Generating QR CODE!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //设置二维码
                try {
                    // 调用方法createCode生成二维码
                    Bitmap bm = qrUtils.createCode(QRcode.this,"Hi! Aubrey!");
                    // 将二维码在界面中显示
                    QRCodeImage.setImageBitmap(bm);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
