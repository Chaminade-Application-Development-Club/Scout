package com.example.huzheyuan.scout.sqliteService;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.huzheyuan.scout.activities.Vex2016Activity;

import java.io.File;

public class DataBaseHelper extends SQLiteOpenHelper {

    Vex2016Activity vex2016Activity = new Vex2016Activity();
    public final static int VERSION = 1;// 版本号
    String TABLE_NAME = "teamData";// 表名
    //public final static String ID = "id";// 后面ContentProvider使用
    public final static String TEXT = "text";
    public static final String DATABASE_NAME = "teamData.db";
    public DataBaseHelper(Context context) {
        super(context, context.getExternalFilesDir(null)
                + File.separator + DATABASE_NAME,null,VERSION);
        SQLiteDatabase.openOrCreateDatabase(context.getExternalFilesDir(null)
                + File.separator + DATABASE_NAME,null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { //创建数据表的操作
        /**
         * 在数据库第一次生成的时候会调用这个方法，同时我们在这个方法里边生成数据库表
         */
        String strSql = "CREATE TABLE " + TABLE_NAME +
                " (_id TEXT PRIMARY KEY , teamNumber TEXT," +
                " Mode TEXT, time TEXT, " +
                "positionX TEXT, positionY TEXT, SAN TEXT, SAF TEXT, SDN TEXT, SDF TEXT, CAN TEXT, CAF TEXT, " +
                "CDN TEXT, CDF TEXT, lifted TEXT)";
        // CREATE TABLE 创建一张表 然后后面是我们的表名
        // 然后表的列，第一个是id 方便操作数据,int类型
        // PRIMARY KEY 是指主键 这是一个int型,用于唯一的标识一行;
        // AUTOINCREMENT 表示数据库会为每条记录的key加一，确保记录的唯一性;
        // 最后我加入一列文本 String类型
        // ----------注意：这里str_sql是sql语句，类似dos命令，要注意空格！
        db.execSQL(strSql);
        // execSQL()方法是执行一句sql语句
        // 虽然此句我们生成了一张数据库表和包含该表的sql文件,
        // 但是要注意 不是方法是创建，是传入的一句str_sql这句sql语句表示创建！！
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /**
         * 更新或者升级数据库的时候会自动调用这个方法，一般我们会在这个方法中
         * 删除数据表，然后再创建新的数据表操作。
         */
        Log.v("Now", "onUpgrade");
    }
}
