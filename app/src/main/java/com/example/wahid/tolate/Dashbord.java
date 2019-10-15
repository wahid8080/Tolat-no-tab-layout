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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Dashbord extends AppCompatActivity {

    private ImageView pic1 ;
    private TextView userNameForRenter;
    private DatabaseReference databaseReference;
    private FirebaseUser user;
    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashbord);

        user = FirebaseAuth.getInstance().getCurrentUser();
        pic1 = findViewById(R.id.ProfilePicDashbord);
        userNameForRenter = findViewById(R.id.userNameForRenter);
        databaseReference = FirebaseDatabase.getInstance().getReference("User").child("renter").child(user.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UploadClintInfoModel clintInfoModel = dataSnapshot.getValue(UploadClintInfoModel.class);

                try {
                    userNameForRenter.setText(clintInfoModel.getName());
                    bitmap = StringToBitMap(clintInfoModel.getImage());
                    pic1.setImageBitmap(bitmap);
                } catch (Exception e)
                {

                }


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
        startActivity(new Intent(Dashbord.this,ViewFlatList.class));
    }

    public void goToUploadHostel(View view) {
        startActivity(new Intent(Dashbord.this,ViewHostelList.class));
    }

    public void goToUploadRoom(View view) {
        startActivity(new Intent(Dashbord.this,ViewRoomList.class));
    }

    public void goToUploadSit(View view) {
        startActivity(new Intent(Dashbord.this,ViewSitlList.class));
    }
}
