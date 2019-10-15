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
import android.widget.Toast;

import com.example.wahid.tolate.ModelClass.UserInformation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeOwnerProfile extends AppCompatActivity {

    private TextView mArea,mRoad,mHouseNumber,mPhone,mEmail,userName;


    private DatabaseReference databaseReference;
    private FirebaseUser user;
    private ImageView profilePic;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_owner_profile);

        profilePic = findViewById(R.id.ProfilePicIdForOwner);
        user = FirebaseAuth.getInstance().getCurrentUser();

        mArea = findViewById(R.id.ownerAreaid);
        mRoad = findViewById(R.id.ownerRoadNoId);
        mHouseNumber = findViewById(R.id.ownerHouseId);
        mPhone = findViewById(R.id.ownerPhoneId);
        mEmail = findViewById(R.id.ownerEmailId);
        userName = findViewById(R.id.nameForOwner);

        databaseReference = FirebaseDatabase.getInstance().getReference("User").child("owner").child(user.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    UserInformation information = dataSnapshot.getValue(UserInformation.class);

                    userName.setText("Name: "+information.getUserName());
                    mRoad.setText("Road : "+information.getRoad());
                    mHouseNumber.setText("House : "+information.getHouseNo());
                    mPhone.setText("Contact: "+information.getPhone());
                    mArea.setText("Area: "+information.getArea());
                    mEmail.setText("Email: "+user.getEmail());
                    bitmap = StringToBitMap(information.getImage());
                    profilePic.setImageBitmap(bitmap);
                } catch (Exception e)
                {
                    startActivity(new Intent(HomeOwnerProfile.this,UploadRenderInfo.class));
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


    public void goTocatagory(View view) {
        startActivity(new Intent(HomeOwnerProfile.this,Category.class));
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this,Login.class));
    }

    public void goToYourRent(View view) {
        startActivity(new Intent(HomeOwnerProfile.this,YourRent.class));
    }
}
