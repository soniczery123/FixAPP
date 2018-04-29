package com.test.fixapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;

import com.test.fixapp.DB.UserDBHelper;

public class downloading extends AppCompatActivity {
    private UserDBHelper mHelper;
    private SQLiteDatabase mDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloading);
        getSupportActionBar().hide() ;
        mHelper = new UserDBHelper(this);
        mDb = mHelper.getReadableDatabase();

        CountDownTimer cdt = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long milli) {

            }

            @Override
            public void onFinish() {
                String rememberEmail = checkRememberLogin();
                if (rememberEmail != null) {
                    Intent intent = new Intent(downloading.this, MainActivity.class);
                    intent.putExtra("email", rememberEmail);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(downloading.this, step.class);
                    startActivity(intent);
                    finish();
                }
            }
        }.start();
    }
    String checkRememberLogin() {
        Cursor cursor =
                mDb.rawQuery("SELECT email  FROM " + mHelper.TABLE_NAME_LOGIN + " WHERE email != \"none\"", null);
        if (cursor.getCount() == 1) {
            if (cursor.moveToFirst()) ;
            String ans = cursor.getString(0);
            return ans;
        } else {
            return null;
        }
    }
    }

