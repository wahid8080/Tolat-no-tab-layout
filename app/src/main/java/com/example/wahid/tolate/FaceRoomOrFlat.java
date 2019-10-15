package com.example.wahid.tolate;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FaceRoomOrFlat extends AppCompatActivity {

    private ImageView mImg1,mImg2,mImg3,mImg4;
    private TextView houseName,area,phone,category,roadNo,details,price;
    private String nameOfHouse,priceOfHouse,areaOfHouse,categoryOfHouse,
    detailOfHouse,roadOfHouse,phoneOfHouse,img1,img2,img3,img4;
    private Bitmap bitmap1,bitmap2,bitmap3,bitmap4;
    private Button bookNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_roo_ro_flat);

        mImg1 = findViewById(R.id.image1forFace);
        mImg2 = findViewById(R.id.image2forFace);
        mImg3 = findViewById(R.id.image3forFace);
        mImg4 = findViewById(R.id.image4forFace);

        houseName = findViewById(R.id.houseName);
        area = findViewById(R.id.areaTana);
        phone = findViewById(R.id.housePhone);
        category = findViewById(R.id.category);
        roadNo= findViewById(R.id.roadNo);
        details = findViewById(R.id.houseDetails);
        price = findViewById(R.id.housePrice);
        bookNow = findViewById(R.id.bookNow);



        nameOfHouse = getIntent().getStringExtra("houseName");
        priceOfHouse = getIntent().getStringExtra("price");
        areaOfHouse = getIntent().getStringExtra("area");
        categoryOfHouse = getIntent().getStringExtra("category");
        detailOfHouse = getIntent().getStringExtra("details");
        roadOfHouse = getIntent().getStringExtra("road");
        phoneOfHouse = getIntent().getStringExtra("phone");
        img1 = getIntent().getStringExtra("img1");
        img2 = getIntent().getStringExtra("img2");
        img3 = getIntent().getStringExtra("img3");
        img4 = getIntent().getStringExtra("img4");

        bitmap1 = StringToBitMap(img1);
        bitmap2 = StringToBitMap(img2);
        bitmap3 = StringToBitMap(img3);
        bitmap4 = StringToBitMap(img4);


        houseName.setText(nameOfHouse);
        phone.setText(phoneOfHouse);
        area.setText(areaOfHouse);
        category.setText(categoryOfHouse);
        roadNo.setText(roadOfHouse);
        details.setText(detailOfHouse);
        price.setText(priceOfHouse);

        mImg1.setImageBitmap(bitmap1);
        mImg2.setImageBitmap(bitmap2);
        mImg3.setImageBitmap(bitmap3);
        mImg4.setImageBitmap(bitmap4);


    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public void callNow(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+phoneOfHouse));
        startActivity(intent);
    }

    public void booking(View view) {

        if (bookNow.getText().equals("Booking"))
        {
            bookNow.setText("Booked");
            Toast.makeText(FaceRoomOrFlat.this,"Booking Successful",Toast.LENGTH_SHORT).show();
        }
        else if (bookNow.getText().equals("Booked"))
        {
            Toast.makeText(FaceRoomOrFlat.this,"Booking is Cancel",Toast.LENGTH_SHORT).show();
            bookNow.setText("Booking");
        }

    }
}
