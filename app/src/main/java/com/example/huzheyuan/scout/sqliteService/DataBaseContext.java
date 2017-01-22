package com.example.huzheyuan.scout.sqliteService;

import android.content.ContextWrapper;
import java.io.File;
import java.io.IOException;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

import com.example.huzheyuan.scout.MainActivity;

/**
 * This is a class for creating database on SD card, it is necessary to create file on the SD card!
 * if you have any problems please let me know
 */

public class DataBaseContext extends ContextWrapper{
    public DataBaseContext(MainActivity base){ // Build the constructor
        super(base);
    }
    @Override
    public File getDatabasePath(String name) {
        //判断是否存在sd卡 check if there is a SD card
        boolean sdExist = android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment.getExternalStorageState());
        if(!sdExist){//如果不存在, if it does not exist
            Log.e("SD card：", "SD card does not exist, please check!");
            return null;
        }
        else{//如果存在. if it does exist
            String dbDir=android.os.Environment.getExternalStorageDirectory().toString();
            //获取sd卡路径 get the path of it
            System.out.println(dbDir);
            Log.e("SD card：", "SD card exist!");
            dbDir += "scoutData";//数据库所在目录, database directory
            String dbPath = dbDir+"/"+name;//数据库路径 database file path

            File dirFile = new File(dbDir);
            //判断目录是否存在，不存在则创建该目录 check if the directory exist, if not, create one
            if(!dirFile.exists()) dirFile.mkdirs();

            boolean isFileCreateSuccess = false; // if the database file is created successfully

            File dbFile = new File(dbPath);
            //判断文件是否存在，不存在则创建该文件 check if the database file's path exist,
            // if not, create one
            if(!dbFile.exists()){
                try {
                    isFileCreateSuccess = dbFile.createNewFile();//创建文件 create the file
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            else isFileCreateSuccess = true;

            if(isFileCreateSuccess) return dbFile; //返回数据库文件对象, return database object
            else return null;
        }
    }
    /**
     * Android 4.0会调用此方法获取数据库。
     *
     * @see android.content.ContextWrapper#openOrCreateDatabase(java.lang.String, int,
     *              android.database.sqlite.SQLiteDatabase.CursorFactory,
     *              android.database.DatabaseErrorHandler)
     * @param    name
     * @param    mode
     * @param    factory
     * @param     errorHandler
     */
    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, CursorFactory factory,
                                               DatabaseErrorHandler errorHandler) {
        SQLiteDatabase result = SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), null);
        return result;
    }
}
