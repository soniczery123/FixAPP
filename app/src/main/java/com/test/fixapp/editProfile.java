package com.test.fixapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.TextView;
import android.widget.Toast;

import com.test.fixapp.DB.UserDBHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class editProfile extends AppCompatActivity {
    private UserDBHelper mHelper;
    private SQLiteDatabase mDb;
    String getEmail;
    TextView textViewEmail;
    ImageView imageView;
    EditText editTextName,editTextTel,editTextPassword;
    Spinner dropdownProvince;
    Bitmap bitmap;
    private static final int PICK_IMAGE = 100;
    Uri image;
    byte[] byteArr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mHelper = new UserDBHelper(this);
        mDb = mHelper.getReadableDatabase();

        imageView = (ImageView)findViewById(R.id.imageViewProfile_Edit);
        textViewEmail = (TextView)findViewById(R.id.textEmail_Edit);
        editTextPassword = (EditText)findViewById(R.id.edittextPassword_Edit);
        editTextName = (EditText)findViewById(R.id.edittextName_Edit);
        editTextTel = (EditText)findViewById(R.id.edittextTel_Edit);
        String[] provinceList = new String[]{"กระบี่", "กรุงเทพมหานคร", "กาญจนบุรี", "กาฬสินธุ์", "กำแพงเพชร", "ขอนแก่น", "จันทบุรี"
                , "ฉะเชิงเทรา", "ชลบุรี", "ชัยนาท", "ชัยภูมิ", "ชุมพร", "เชียงราย", "เชียงใหม่", "ตรัง", "ตราด", "ตาก", "นครนายก", "นครปฐม", "นครพนม"
                , "นครราชสีมา", "นครศรีธรรมราช", "นครสวรรค์", "นนทบุรี", "นราธิวาส", "น่าน", "บึงกาฬ", "บุรีรัมย์", "ปทุมธานี", "ประจวบคีรีขันธ์", "ปราจีนบุรี", "ปัตตานี"
                , "พะเยา", "พังงา", "พัทลุง", "พิจิตร", "พิษณุโลก", "เพชรบุรี", "เพชรบูรณ์", "แพร่", "ภูเก็ต", "มหาสารคาม", "มุกดาหาร", "แม่ฮ่องสอน", "ยโสธร"
                , "ยะลา", "ร้อยเอ็ด", "ระนอง", "ระยอง", "ราชบุรี", "ลพบุรี", "ลำปาง", "ลำพูน", "เลย", "ศรีสะเกษ", "สกลนคร", "สงขลา", "สตูล"
                , "สมุทรปราการ", "สมุทรสงคราม", "สมุทรสาคร", "สระแก้ว", "สระบุรี", "สิงห์บุรี", "สุโขทัย", "สุพรรณบุรี", "สุราษฎร์ธานี", "สุรินทร์", "หนองคาย"
                , "หนองบัวลำภู", "อยุธยา", "อ่างทอง", "อำนาจเจริญ", "อุดรธานี", "อุตรดิตถ์", "อุทัยธานี", "อุบลราชธานี"};
        dropdownProvince = findViewById(R.id.dropdown_Edit);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this
                , android.R.layout.simple_spinner_dropdown_item
                , provinceList);
        dropdownProvince.setAdapter(adapter);

        Intent intent = getIntent();
        getEmail = intent.getStringExtra("email");

        Cursor cursor =
                mDb.rawQuery("SELECT email,password,name,tel,picture  FROM " + mHelper.TABLE_NAME_USER
                        + " WHERE email = \"" + getEmail + "\"", null);
        cursor.moveToFirst();
        textViewEmail.setText(cursor.getString(0));
        editTextPassword.setText(cursor.getString(1));
        editTextName.setText(cursor.getString(2));
        editTextTel.setText(cursor.getString(3));
        if(cursor.getBlob(4)!=null) {
            byte[] pic = cursor.getBlob(4);
            Bitmap bitmap = BitmapFactory.decodeByteArray(pic, 0, pic.length);
            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
            roundedBitmapDrawable.setCircular(true);
            imageView.setImageDrawable(roundedBitmapDrawable);
        }
        Button buttonSubmit = (Button)findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveToDB();
                Intent intent = new Intent(editProfile.this,MainActivity.class);
                intent.putExtra("email", textViewEmail.getText().toString());
                startActivity(intent);
                finish();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
    }

    private void saveToDB(){
        ContentValues values = new ContentValues();
        values.put("email",textViewEmail.getText().toString());
        values.put("password",editTextPassword.getText().toString());
        values.put("name",editTextName.getText().toString());
        values.put("tel",editTextTel.getText().toString());
        values.put("location",dropdownProvince.getSelectedItem().toString());
        values.put(mHelper.COL_PICTURE,byteArr);
        mDb.update(mHelper.TABLE_NAME_USER,values,"email" + " = ?",new String[]{textViewEmail.getText().toString()});
        Toast.makeText(editProfile.this,"Successfully",Toast.LENGTH_SHORT).show();
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
                imageView.setImageDrawable(roundedBitmapDrawable);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
