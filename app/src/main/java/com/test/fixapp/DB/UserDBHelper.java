package com.test.fixapp.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.test.fixapp.R;

/**
 * Created by MSI-GL62 on 23/4/2561.
 */

public class UserDBHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 7;
    private static final String DATABASE_NAME = "user.db";

    public static final String TABLE_NAME_USER = "user_detail";
    public static final String TABLE_NAME_LOGIN = "login_detail";

    public static final String COL_EMAIL = "email";
    public static final String COL_ID = "id";
    public static final String COL_PASSWORD = "password";
    public static final String COL_NAME = "name";
    public static final String COL_TEL = "tel";
    public static final String COL_LOCATION = "location";
    public static final String COL_PICTURE = "picture";

    public static final String TABLE_NAME_ORDER = "order_detail";
    public static final String COL_TITLE= "title";
    public static final String COL_DETAIL = "detail";
    public static final String COL_DATE = "date";

    private static final String CREATE_TABLE_ORDER = "CREATE TABLE " + TABLE_NAME_ORDER + "("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_TITLE + " TEXT, "
            + COL_PICTURE + " INTEGER, "
            + COL_DETAIL + " TEXT, "
            + COL_DATE + " TEXT)";

    private static final String CREATE_TABLE_LOGIN = "CREATE TABLE " + TABLE_NAME_LOGIN + "("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_EMAIL + " TEXT)";

    private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_NAME_USER + "("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_EMAIL + " TEXT, "
            + COL_PASSWORD + " TEXT, "
            + COL_NAME + " TEXT, "
            + COL_TEL + " TEXT, "
            + COL_LOCATION + " TEXT, "
            + COL_PICTURE + " BLOB)";

    public UserDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_LOGIN);
        db.execSQL(CREATE_TABLE_ORDER);
        insertInitialData(db);
    }
    private void insertInitialData(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(COL_EMAIL, "admin");
        cv.put(COL_PASSWORD, "1234567890");
        cv.put(COL_NAME, "ADMINFIX");
        cv.put(COL_TEL, "123456789");
        cv.put(COL_LOCATION, "นครปฐม");
        db.insert(TABLE_NAME_USER, null, cv);

        cv = new ContentValues();
        cv.put(COL_EMAIL,"none");
        db.insert(TABLE_NAME_LOGIN, null, cv);

        cv = new ContentValues();
        cv.put(COL_TITLE,"ซ่อมแอร์");
        cv.put(COL_PICTURE, R.drawable.ic_clear_black_24dp);
        cv.put(COL_DETAIL,"ลมเย็นไม่ออก");
        cv.put(COL_DATE,"25/4/2561");
        db.insert(TABLE_NAME_ORDER, null, cv);
        cv = new ContentValues();
        cv.put(COL_TITLE,"ซ่อมเครื่องซักผ้า");
        cv.put(COL_PICTURE, R.drawable.ic_done_black_24dp);
        cv.put(COL_DETAIL,"มีเสียงดัง");
        cv.put(COL_DATE,"25/4/2561");
        db.insert(TABLE_NAME_ORDER, null, cv);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_LOGIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ORDER);
        onCreate(db);
    }
}
