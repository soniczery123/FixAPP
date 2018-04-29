package com.test.fixapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.test.fixapp.DB.UserDBHelper;
import com.test.fixapp.model.order;
import com.test.fixapp.model.orderlistAdapter;

import java.util.ArrayList;

public class order_history extends AppCompatActivity {
    private UserDBHelper mHelper;
    private SQLiteDatabase mDb;
    private orderlistAdapter mAdapter;
    private ArrayList<order> orederItemList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        getSupportActionBar().setTitle("Order History");
        mHelper = new UserDBHelper(this);
        mDb = mHelper.getReadableDatabase();
        Cursor cursor = mDb.query(
                UserDBHelper.TABLE_NAME_ORDER,
                null,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex(mHelper.COL_TITLE));
            String detail = cursor.getString(cursor.getColumnIndex(mHelper.COL_DETAIL));
            String date = cursor.getString(cursor.getColumnIndex(mHelper.COL_DATE));
            int picture = cursor.getInt(cursor.getColumnIndex(mHelper.COL_PICTURE));

            order item = new order(title, detail, date, picture);
            orederItemList.add(item);
        }
        mAdapter = new orderlistAdapter(
                this,
                R.layout.item,
                orederItemList
        );

        ListView lv = (ListView) findViewById(R.id.list_item_order_history);
        lv.setAdapter(mAdapter);
    }
}
