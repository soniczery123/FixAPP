package com.test.fixapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.test.fixapp.DB.UserDBHelper;

import java.io.IOException;

public class edit_order extends AppCompatActivity {
    String getEmail;
    ImageView imageViewOrder;
    CheckBox checkBoxUseProfile;
    EditText editTextName_Order,editTextTel_Order,getEditTextLocation_Order,editTextDetail_Order;
    private UserDBHelper mHelper;
    private SQLiteDatabase mDb;
    String tags[] , pos[];
    String transport;
    Spinner spinnerTag,spinnerPos;
    RadioButton radioButton1,radioButton2;
    Button buttonSubmit_Order,buttonCamera, buttonGallery;

    int REQUEST_IMAGE_CAPTURE = 1, PICK_IMAGE = 100;
    Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_order);
        checkBoxUseProfile = (CheckBox)findViewById(R.id.checkBoxUseProfile);
        Intent intent = getIntent();
        getEmail = intent.getStringExtra("email");

        buttonCamera = (Button) findViewById(R.id.buttonCamera);
        buttonGallery = (Button) findViewById(R.id.buttonGallery);
        editTextName_Order = (EditText)findViewById(R.id.editTextName_Order);
        editTextTel_Order = (EditText)findViewById(R.id.editTextTel_Order);
        getEditTextLocation_Order = (EditText)findViewById(R.id.editTextLocation_Order);
        editTextDetail_Order = (EditText)findViewById(R.id.editTextDetail_Order);
        spinnerTag = (Spinner)findViewById(R.id.spinnerTag);
        spinnerPos = (Spinner)findViewById(R.id.spinnerPos);
        radioButton1 = (RadioButton)findViewById(R.id.radioButton);
        radioButton2 = (RadioButton)findViewById(R.id.radioButton2);
        buttonSubmit_Order = (Button)findViewById(R.id.buttonSubmit_Order);

        tags = new String[]{"A","B","C"};
        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this
                , android.R.layout.simple_spinner_dropdown_item
                , tags);
        spinnerTag.setAdapter(adapter1);
        spinnerTag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                createArrPos(spinnerTag.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }}
        );
        buttonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }

            }
        });

        buttonGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });


        buttonSubmit_Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!onRadioButtonClicked()){
                    Toast.makeText(edit_order.this,"Please select transpot",Toast.LENGTH_SHORT).show();
                }else{
                AlertDialog.Builder dialog =
                        new AlertDialog.Builder(edit_order.this);
                LayoutInflater inflater = getLayoutInflater();

                view = inflater.inflate(R.layout.activity_custom_dialog, null);
                dialog.setView(view);
                Button buttonDraft = (Button) view.findViewById(R.id.buttonDraft);
                Button buttonSend = (Button) view.findViewById(R.id.buttonSend);
                buttonDraft.setBackgroundColor(Color.TRANSPARENT);
                buttonSend.setBackgroundColor(Color.TRANSPARENT);
                buttonDraft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(edit_order.this,"Save Order Successfully",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(edit_order.this,MainActivity.class);
                        startActivity(intent);
                    }
                });

                buttonSend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(edit_order.this,"Send Order Successfully",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(edit_order.this,MainActivity.class);
                        startActivity(intent);
                    }
                });
                dialog.show();
            }}
        });

        mHelper = new UserDBHelper(this);
        mDb = mHelper.getReadableDatabase();

        final Cursor cursor =
                mDb.rawQuery("SELECT *  FROM " + mHelper.TABLE_NAME_USER
                        + " WHERE email = \"" + getEmail + "\"", null);
        cursor.moveToFirst();
        imageViewOrder = (ImageView)findViewById(R.id.imageViewOrder);


        checkBoxUseProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBoxUseProfile.isChecked()){
                    editTextName_Order.setText(cursor.getString(3));
                    editTextTel_Order.setText(cursor.getString(4));
                    getEditTextLocation_Order.setText(cursor.getString(5));
                }
            }
        });


    }

    private void createArrPos(String s) {
        if(s.equals("A")){
            pos = new String[]{"1","2","3"};
        }else if(s.equals("B")){
            pos = new String[]{"4","5","6"};
        }else{
            pos = new String[]{"7","8","9"};
        }
        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this
                , android.R.layout.simple_spinner_dropdown_item
                , pos);
        spinnerPos.setAdapter(adapter2);
    }
    public boolean onRadioButtonClicked() {

        if(radioButton1.isChecked()){
            transport = radioButton1.getText().toString();
            return true;
        }else if(radioButton2.isChecked()){
            transport = radioButton2.getText().toString();
            return true;
        }
        return false;
    }
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
        } else {
            try {
                Uri image = data.getData();
                imageBitmap = (Bitmap)MediaStore.Images.Media.getBitmap(getContentResolver(), image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        imageViewOrder.setImageBitmap(imageBitmap);
   /*     Intent intent = new Intent(select_direction.this, edit_order.class);
        intent.putExtra("image", imageBitmap);
        intent.putExtra("email", getEmail);
        startActivity(intent);*/
    }

}
