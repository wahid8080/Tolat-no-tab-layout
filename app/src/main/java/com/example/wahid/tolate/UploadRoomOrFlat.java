package com.example.wahid.tolate;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.wahid.tolate.GoogleApi.GPSTracker;
import com.example.wahid.tolate.ModelClass.UploadRoomFlat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UploadRoomOrFlat extends AppCompatActivity {

    private ImageView mImg1, mImg2, mImg3, mImg4;
    private EditText price,houseName,area,roadNo,houseNumber,houseDetails, contact;
    private CheckBox razuk, family, bachelor, other, get, internet, genarator, water;
    private String sRazuk, sFamily, sBachelor, sOther, sGet, sInternet, sGenarator, sWater;
    private static final int CODE_MULTIPLE_IMG_GALLERY = 2;
    private Bitmap bitmap1,bitmap2,bitmap3,bitmap4;
    private Button upload;
    private LinearLayout mLayout1,mLayout2;

    private DatabaseReference databaseReference,databaseReference2;

    private FirebaseUser user;
    private String passedArg;
    private double latitute, lagatitute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_room_or_flat);

        Bundle extras1 = getIntent().getExtras();
        if (extras1 != null) {
            passedArg = extras1.getString("arg");
        }

        price = findViewById(R.id.housePrice);
        mImg1 = findViewById(R.id.image1);
        mImg2 = findViewById(R.id.image2);
        mImg3 = findViewById(R.id.image3);
        mImg4 = findViewById(R.id.image4);

        razuk = findViewById(R.id.razukId);
        family = findViewById(R.id.familyId);
        bachelor = findViewById(R.id.bachelorId);
        other = findViewById(R.id.otherId);
        get = findViewById(R.id.gateGardId);
        internet = findViewById(R.id.internetId);
        genarator = findViewById(R.id.geneterId);
        water = findViewById(R.id.waterLineId);

        upload = findViewById(R.id.upload);
        mLayout1 = findViewById(R.id.mLayout1);
        mLayout2 = findViewById(R.id.mLayout2);

        houseName = findViewById(R.id.houseNameUploadProduct);
        area = findViewById(R.id.houseAreaUploadProduct);
        roadNo = findViewById(R.id.roadNumberUploadProduct);
        houseNumber = findViewById(R.id.houseNumber);
        houseDetails = findViewById(R.id.houseDetailsUploadProduct);
        contact = findViewById(R.id.contuctUploadProduct);

        user = FirebaseAuth.getInstance().getCurrentUser();


        GPSTracker gpsTracker = new GPSTracker(getApplicationContext());
        Location l = gpsTracker.getLocation();

        if (l != null)
        {
            latitute = l.getLatitude();
            lagatitute = l.getLongitude();
        }
    }

    public void submitData(View view) {
        getAllData();
    }


    public void imageUpload(View view) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Multiple image"),
                CODE_MULTIPLE_IMG_GALLERY);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CODE_MULTIPLE_IMG_GALLERY && resultCode == RESULT_OK )
        {
            ClipData clipData = data.getClipData();
            if (clipData != null&&clipData.getItemCount()>2)
            {

                mImg1.setImageURI(clipData.getItemAt(0).getUri());
                mImg2.setImageURI(clipData.getItemAt(1).getUri());
                mImg3.setImageURI(clipData.getItemAt(2).getUri());
                mImg4.setImageURI(clipData.getItemAt(3).getUri());

                try {
                    bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), clipData.getItemAt(0).getUri());
                    bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), clipData.getItemAt(1).getUri());
                    bitmap3 = MediaStore.Images.Media.getBitmap(getContentResolver(), clipData.getItemAt(2).getUri());
                    bitmap4 = MediaStore.Images.Media.getBitmap(getContentResolver(), clipData.getItemAt(3).getUri());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mLayout1.setVisibility(View.VISIBLE);
                mLayout2.setVisibility(View.VISIBLE);
                upload.setVisibility(View.GONE);

            } else
            {
                Toast.makeText(UploadRoomOrFlat.this,"Please Select 4 Images",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream);
        byte[] imgbyte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgbyte, Base64.DEFAULT);
    }

    void getAllData() {



        String m1 = imageToString(bitmap1);
        String m2 = imageToString(bitmap2);
        String m3 = imageToString(bitmap3);
        String m4 = imageToString(bitmap4);

        String houseN = houseName.getText().toString();
        String houseNu=houseNumber.getText().toString();
        String houseAr = area.getText().toString();
        String roadN=roadNo.getText().toString();
        String prices = price.getText().toString();
        String details = houseDetails.getText().toString();
        String contactByPhone = contact.getText().toString();

        if (razuk.isChecked()) {
            sRazuk = razuk.getText().toString();
        }else{
            sRazuk = "false";
        }
        if (family.isChecked()) {
            sFamily = family.getText().toString();
        }else{
            sFamily = "false";
        }
        if (bachelor.isChecked()) {
            sBachelor = bachelor.getText().toString();
        }else{
            sBachelor = "false";
        }
        if (other.isChecked()) {
            sOther = other.getText().toString();
        }else{
            sOther = "false";
        }
        if (get.isChecked()) {
            sGet = get.getText().toString();
        }else{
            sGet = "false";
        }
        if (internet.isChecked()) {
            sInternet = internet.getText().toString();
        }else{
            sInternet = "false";
        }
        if (water.isChecked()) {
            sWater = water.getText().toString();
        }else{
            sWater = "false";
        }
        if (genarator.isChecked()) {
            sGenarator = genarator.getText().toString();
        }else{
            sGenarator = "false";
        }


        databaseReference = FirebaseDatabase.getInstance().getReference("UploadData").child(passedArg).push();
        databaseReference2 = FirebaseDatabase.getInstance().getReference("UploadPersonalRoomOrFlar").child(passedArg).child(user.getUid()).push();
        UploadRoomFlat uploadRoomFlat = new UploadRoomFlat(sFamily,sBachelor,sOther,sRazuk,sGet,sInternet,sGenarator,
                sWater,m1,m2,m3,m4,prices,houseN,houseAr,roadN,houseNu,details,contactByPhone,latitute,lagatitute);
        databaseReference.setValue(uploadRoomFlat);
        databaseReference2.setValue(uploadRoomFlat);

        startActivity(new Intent(UploadRoomOrFlat.this,HomeOwnerProfile.class));
    }

}
