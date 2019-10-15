package com.example.wahid.tolate;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import java.io.ByteArrayOutputStream;

public class RenterProfile extends AppCompatActivity {


    private DatabaseReference databaseReference;
    private FirebaseUser user;
    private ImageView profilePic;
    private TextView district, thana, phone, email, nid, userName;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renter_profile);

        user = FirebaseAuth.getInstance().getCurrentUser();
        district = findViewById(R.id.renderDistrectId);
        thana = findViewById(R.id.renderAreaId);
        phone = findViewById(R.id.renderPhoneId);
        email = findViewById(R.id.renderEmailId);
        nid = findViewById(R.id.renderNidId);
        userName = findViewById(R.id.userNameForRenterId2);
        profilePic = findViewById(R.id.ProfilePicIdForRenderId);


        databaseReference = FirebaseDatabase.getInstance().getReference("User").child("renter").child(user.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UploadClintInfoModel Model = dataSnapshot.getValue(UploadClintInfoModel.class);

                try {
                    userName.setText("Name: " + Model.getName());
                    district.setText("District: " + Model.getDistric());
                    thana.setText("Thana: " + Model.getArea());
                    phone.setText("Phone: " + Model.getPhone());
                    email.setText("Email: " + user.getEmail());
                    nid.setText("N ID: " + Model.getNid());
                    bitmap = StringToBitMap(Model.getImage());
                    profilePic.setImageBitmap(bitmap);
                } catch (Exception e) {
                    startActivity(new Intent(RenterProfile.this, UploadRenderInfo.class));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.editProfile:
                editProfile();
                return true;
            case R.id.logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void editProfile() {
    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, Login.class));
    }

    public void goTocatagory(View view) {
        startActivity(new Intent(RenterProfile.this, Dashbord.class));
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

    public void EditProfile(View view) {
    }


    public void logoutRenter(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, Login.class));
    }
}
