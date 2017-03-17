package com.example.huzheyuan.scout.realmService;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import static android.content.ContentValues.TAG;

/**
 * Created by huzhe on 3/15/2017.
 */

public class RealmUtils {
    public static Realm openOrCreateRealm(Context context, String fileName){
        RealmConfiguration realmConfiguration = new
                RealmConfiguration.Builder()
                .directory(
                        context.getExternalFilesDir(null)) //LMAO, finally I found this!
                .name(fileName) //config the file name
                .build(); // build
        Realm realm = Realm.getInstance(realmConfiguration);
        File realmFile = new File(realmConfiguration.getPath());
        if(realmFile.exists()){
            Toast.makeText(context,realmFile.getPath(),Toast.LENGTH_LONG).show();
            Log.d(TAG, "openOrCreateRealm: created");
            Log.i("R: ", realmConfiguration.getPath());
        }
        return realm;
    }
}
