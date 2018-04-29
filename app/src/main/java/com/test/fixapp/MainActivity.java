package com.test.fixapp;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.test.fixapp.DB.UserDBHelper;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private UserDBHelper mHelper;
    private SQLiteDatabase mDb;
    String getEmail;
    TextView textViewEmail, textViewName;
    ImageView imageViewProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);

        textViewEmail = (TextView) headerView.findViewById(R.id.textViewEmail_Nav);
        textViewName = (TextView) headerView.findViewById(R.id.textViewName_Nav);
        imageViewProfile = (ImageView) headerView.findViewById(R.id.imageViewProfile_Nav);

        Intent intent = getIntent();
        getEmail = intent.getStringExtra("email");

        mHelper = new UserDBHelper(this);
        mDb = mHelper.getReadableDatabase();
      // Toast.makeText(MainActivity.this, getEmail, Toast.LENGTH_SHORT).show();
        Cursor cursor =
                mDb.rawQuery("SELECT email,name,picture  FROM " + mHelper.TABLE_NAME_USER
                        + " WHERE email = \"" + getEmail + "\"", null);
        if (cursor.moveToFirst()) {
            textViewEmail.setText(cursor.getString(0));
            textViewName.setText(cursor.getString(1));
            if(cursor.getBlob(2)!=null) {
                byte[] pic = cursor.getBlob(2);
                Bitmap bitmap = BitmapFactory.decodeByteArray(pic, 0, pic.length);
                RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
                roundedBitmapDrawable.setCircular(true);
                imageViewProfile.setImageDrawable(roundedBitmapDrawable);
            }
            cursor.close();
        }
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public boolean onCreateOptionsMenu(Menu menu) { // Method Inflate Menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.noti, menu); // เริ่ม Inflate menu
        return super.onCreateOptionsMenu(menu); // ไปแสดงผลที่ Activity
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { // Method ทำงานหลังกด Menu
        int id = item.getItemId();
        if (id == R.id.notificationButton){
            Toast.makeText(MainActivity.this,"ALERT",Toast.LENGTH_SHORT).show();
        }
        if (toggle.onOptionsItemSelected(item)) //navi
            return true;
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.closeDrawer(GravityCompat.START);
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Do you want to exit?");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    finish();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //dialog.dismiss();
                }
            });
            builder.show();

        }
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id == R.id.nav_logout){
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Do you want to log out?");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    ContentValues cv = new ContentValues();
                    cv.put(mHelper.COL_EMAIL, "none");
                    mDb.update(mHelper.TABLE_NAME_LOGIN,cv,"id" + " = 1",null);
                    Intent intent = new Intent(MainActivity.this, login.class);
                    startActivity(intent);
                    finish();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //dialog.dismiss();
                }
            });
            builder.show();
        }
        else if (id == R.id.nav_Edit) {
            Intent intent = new Intent(MainActivity.this, editProfile.class);
            String result = getEmail;
            intent.putExtra("email", result);
            startActivity(intent);
        }else if(id == R.id.nav_canlendar){
            Intent intent = new Intent(MainActivity.this, calendar.class);
            startActivity(intent);
        }else if(id == R.id.nav_orderHistory){
            Intent intent = new Intent(MainActivity.this, order_history.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
