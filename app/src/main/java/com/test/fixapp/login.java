package com.test.fixapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.test.fixapp.DB.UserDBHelper;

public class login extends AppCompatActivity {
    Button buttonLogin, buttonSignup;
    private UserDBHelper mHelper;
    private SQLiteDatabase mDb;
    CheckBox checkBox;
    EditText editEmail, editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getActionBar().hide();
        mHelper = new UserDBHelper(this);
        mDb = mHelper.getReadableDatabase();

        checkBox = (CheckBox) findViewById(R.id.checkBox);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonSignup = (Button) findViewById(R.id.buttonSignup);
        editEmail = (EditText) findViewById(R.id.edittextEmail_login);
        editPassword = (EditText) findViewById(R.id.edittextPassword_login);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, MainActivity.class);
                String result = checkLogin(editEmail.getText().toString(), editPassword.getText().toString());

                if (result == null) {
                    Toast.makeText(login.this, "กรุณาป้อน Email และ Password ให้ถูกต้อง", Toast.LENGTH_SHORT).show();
                    editPassword.getText().clear();
                } else {
                    if (checkBox.isChecked()) {
                        ContentValues cv = new ContentValues();
                        cv.put(mHelper.COL_EMAIL, result);
                        mDb.update(mHelper.TABLE_NAME_LOGIN,cv,"id" + " = 1",null);
                    }
                    intent.putExtra("email", result);
                    startActivity(intent);
                    finish();
                }
            }
        });
        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, signup.class);
                startActivity(intent);
                finish();

            }
        });
    }

    String checkLogin(String editEmail, String editPassword) {
        Cursor cursor =
                mDb.rawQuery("SELECT email  FROM " + mHelper.TABLE_NAME_USER
                        + " WHERE email = \"" + editEmail + "\" AND password = \"" + editPassword + "\"", null);
        if (cursor.getCount() == 1) {
            if (cursor.moveToFirst()) ;
            String ans = cursor.getString(0);
            return ans;
        } else {
            return null;
        }
    }


}
