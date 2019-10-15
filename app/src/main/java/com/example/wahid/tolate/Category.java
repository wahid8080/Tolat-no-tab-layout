package com.example.wahid.tolate;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wahid.tolate.ModelClass.UploadClintInfoModel;
import com.example.wahid.tolate.ModelClass.UserInformation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Category extends AppCompatActivity {

    private ImageView pic;
    private DatabaseReference databaseReference;
    private FirebaseUser user;
    private Bitmap bitmap;
    private TextView categoryOfUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        user = FirebaseAuth.getInstance().getCurrentUser();
        pic = findViewById(R.id.ProfilePicIdCategoryId);
        categoryOfUserName = findViewById(R.id.categoryOfUserName);
        databaseReference = FirebaseDatabase.getInstance().getReference("User").child("owner").child(user.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInformation information = dataSnapshot.getValue(UserInformation.class);
                categoryOfUserName.setText(information.getUserName());
                bitmap = StringToBitMap(information.getImage());
                pic.setImageBitmap(bitmap);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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

    public void goToUploadFilat(View view) {

        Intent intent = new Intent(this, UploadRoomOrFlat.class);
        intent.putExtra("arg", "Flat"); // getText() SHOULD NOT be static!!!
        startActivity(intent);

    }

    public void goToUploadHostel(View view) {
        Intent intent = new Intent(this, UploadRoomOrFlat.class);
        intent.putExtra("arg", "Hostel"); // getText() SHOULD NOT be static!!!
        startActivity(intent);
    }

    public void goToUploadRoom(View view) {
        Intent intent = new Intent(this, UploadRoomOrFlat.class);
        intent.putExtra("arg", "Room"); // getText() SHOULD NOT be static!!!
        startActivity(intent);
    }

    public void goToUploadSit(View view) {

        Intent intent = new Intent(this, UploadRoomOrFlat.class);
        intent.putExtra("arg", "Sit"); // getText() SHOULD NOT be static!!!
        startActivity(intent);

    }
}
