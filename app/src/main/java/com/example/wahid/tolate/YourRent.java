package com.example.wahid.tolate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.wahid.tolate.Adepter.FlatAdepter;
import com.example.wahid.tolate.Adepter.HostelAdepter;
import com.example.wahid.tolate.Adepter.RoomAdepter;
import com.example.wahid.tolate.Adepter.SitAdepter;
import com.example.wahid.tolate.ModelClass.UploadRoomFlat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class YourRent extends AppCompatActivity {

    RecyclerView recyclerViewForFlat;
    FirebaseUser user;

    DatabaseReference databaseReference,databaseReference2,databaseReference3,databaseReference4;
    ArrayList<UploadRoomFlat> uploadRoomFlats;

    FlatAdepter flatAdepter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_rent);

        recyclerViewForFlat = findViewById(R.id.recyclerViewForYourRentFlat);

        user = FirebaseAuth.getInstance().getCurrentUser();

        uploadRoomFlats = new ArrayList<>();

        recyclerViewForFlat.setLayoutManager(new LinearLayoutManager(this));

        databaseReference = FirebaseDatabase.getInstance().getReference("UploadPersonalRoomOrFlar").child("Flat").child(user.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    UploadRoomFlat roomModel = dataSnapshot1.getValue(UploadRoomFlat.class);
                    uploadRoomFlats.add(roomModel);
                }

                flatAdepter = new FlatAdepter(YourRent.this,uploadRoomFlats);
                recyclerViewForFlat.setAdapter(flatAdepter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseReference2 = FirebaseDatabase.getInstance().getReference("UploadPersonalRoomOrFlar").child("Hostel").child(user.getUid());
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    UploadRoomFlat roomModel = dataSnapshot1.getValue(UploadRoomFlat.class);
                    uploadRoomFlats.add(roomModel);
                }

                flatAdepter = new FlatAdepter(YourRent.this,uploadRoomFlats);
                recyclerViewForFlat.setAdapter(flatAdepter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseReference3 = FirebaseDatabase.getInstance().getReference("UploadPersonalRoomOrFlar").child("Room").child(user.getUid());
        databaseReference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    UploadRoomFlat roomModel = dataSnapshot1.getValue(UploadRoomFlat.class);
                    uploadRoomFlats.add(roomModel);
                }

                flatAdepter = new FlatAdepter(YourRent.this,uploadRoomFlats);
                recyclerViewForFlat.setAdapter(flatAdepter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseReference4 = FirebaseDatabase.getInstance().getReference("UploadPersonalRoomOrFlar").child("Sit").child(user.getUid());
        databaseReference4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    UploadRoomFlat roomModel = dataSnapshot1.getValue(UploadRoomFlat.class);
                    uploadRoomFlats.add(roomModel);
                }

                flatAdepter = new FlatAdepter(YourRent.this,uploadRoomFlats);
                recyclerViewForFlat.setAdapter(flatAdepter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
