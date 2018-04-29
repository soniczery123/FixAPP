package com.test.fixapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.test.fixapp.DB.UserDBHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class signup extends AppCompatActivity {
    EditText editText_Email, editText_Password, editText_Name, editText_Tel;
    ImageView profileDis;
    Button buttonSubmit;
    private UserDBHelper mHelper;
    private SQLiteDatabase mDb;
    Spinner dropdownProvince;
    Bitmap bitmap;
    private static final int PICK_IMAGE = 100;
    Uri image;
    byte[] byteArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mHelper = new UserDBHelper(this);
        mDb = mHelper.getReadableDatabase();
       // mDb.execSQL("DROP TABLE IF EXISTS " + mHelper.TABLE_NAME_USER);
       // mDb.execSQL("DROP TABLE IF EXISTS " + mHelper.TABLE_NAME_LOGIN);
       // mHelper.onCreate(mDb);

        profileDis = (ImageView) findViewById(R.id.imageButtonProfile_signup);
        buttonSubmit = (Button) findViewById(R.id.buttonSubmit_signup);
        editText_Email = (EditText) findViewById(R.id.edittextEmail_signup);
        editText_Password = (EditText) findViewById(R.id.edittextPassword_signup);
        editText_Name = (EditText) findViewById(R.id.edittextName_signup);
        editText_Tel = (EditText) findViewById(R.id.edittextTel_signup);

        //DROPDOWN
        String[] provinceList = new String[]{"กระบี่", "กรุงเทพมหานคร", "กาญจนบุรี", "กาฬสินธุ์", "กำแพงเพชร", "ขอนแก่น", "จันทบุรี"
                , "ฉะเชิงเทรา", "ชลบุรี", "ชัยนาท", "ชัยภูมิ", "ชุมพร", "เชียงราย", "เชียงใหม่", "ตรัง", "ตราด", "ตาก", "นครนายก", "นครปฐม", "นครพนม"
                , "นครราชสีมา", "นครศรีธรรมราช", "นครสวรรค์", "นนทบุรี", "นราธิวาส", "น่าน", "บึงกาฬ", "บุรีรัมย์", "ปทุมธานี", "ประจวบคีรีขันธ์", "ปราจีนบุรี", "ปัตตานี"
                , "พะเยา", "พังงา", "พัทลุง", "พิจิตร", "พิษณุโลก", "เพชรบุรี", "เพชรบูรณ์", "แพร่", "ภูเก็ต", "มหาสารคาม", "มุกดาหาร", "แม่ฮ่องสอน", "ยโสธร"
                , "ยะลา", "ร้อยเอ็ด", "ระนอง", "ระยอง", "ราชบุรี", "ลพบุรี", "ลำปาง", "ลำพูน", "เลย", "ศรีสะเกษ", "สกลนคร", "สงขลา", "สตูล"
                , "สมุทรปราการ", "สมุทรสงคราม", "สมุทรสาคร", "สระแก้ว", "สระบุรี", "สิงห์บุรี", "สุโขทัย", "สุพรรณบุรี", "สุราษฎร์ธานี", "สุรินทร์", "หนองคาย"
                , "หนองบัวลำภู", "อยุธยา", "อ่างทอง", "อำนาจเจริญ", "อุดรธานี", "อุตรดิตถ์", "อุทัยธานี", "อุบลราชธานี"};
        dropdownProvince = findViewById(R.id.dropdown_signup);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this
                , android.R.layout.simple_spinner_dropdown_item
                , provinceList);
        dropdownProvince.setAdapter(adapter);

        profileDis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean check = true;
                if (!checkEmail(editText_Email.getText().toString())) {
                    editText_Email.setError("Email นี้มีผู้ใช้แล้ว");
                    check = false;
                }
                if (editText_Password.getText().toString().length() < 6) {
                    editText_Password.setError("Password ต้องมีความยาวไม่น้อยกว่า 6 ตัวอักษร");
                    check = false;
                }
                if (editText_Tel.getText().toString().length() != 10) {
                    editText_Tel.setError("Tel ต้องมีความยาว 10 ตัวอักษร");
                    check = false;
                }
                if (check) {
                    insertDB();
                }
            }
        });

    }

    boolean checkEmail(String editEmail) {
        Cursor cursor =
                mDb.rawQuery("SELECT *  FROM " + mHelper.TABLE_NAME_USER
                        + " WHERE email = \"" + editEmail + "\"", null);
        if (cursor.getCount() > 0) {
            cursor.close();
            return false;
        } else {
            cursor.close();
            return true;
        }
    }

    private void insertDB() {
        ContentValues cv = new ContentValues();
        cv.put(mHelper.COL_EMAIL, editText_Email.getText().toString());
        cv.put(mHelper.COL_PASSWORD, editText_Password.getText().toString());
        cv.put(mHelper.COL_NAME, editText_Name.getText().toString());
        cv.put(mHelper.COL_TEL, editText_Tel.getText().toString());
        cv.put(mHelper.COL_LOCATION, dropdownProvince.getSelectedItem().toString());
        cv.put(mHelper.COL_PICTURE,byteArr);
        mDb.insert(mHelper.TABLE_NAME_USER, null, cv);

        Toast.makeText(signup.this, "Success", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(signup.this, MainActivity.class);
        String result = editText_Email.getText().toString();
        intent.putExtra("email", result);
        startActivity(intent);
        finish();
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            image = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                //circle
                RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
                roundedBitmapDrawable.setCircular(true);

                bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
                byteArr = stream.toByteArray();
                profileDis.setImageDrawable(roundedBitmapDrawable);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
