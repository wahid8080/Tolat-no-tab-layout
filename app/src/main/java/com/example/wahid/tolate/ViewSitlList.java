package com.example.wahid.tolate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import com.example.wahid.tolate.Adepter.FlatAdepter;
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

public class ViewSitlList extends AppCompatActivity {

    DatabaseReference databaseReference;
    ArrayList<UploadRoomFlat> uploadRoomFlats;
    RecyclerView recyclerView;
    SitAdepter sitAdepter;
    FirebaseUser user;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sitl_list);

        user = FirebaseAuth.getInstance().getCurrentUser();
        recyclerView = findViewById(R.id.recyclerViewForSit);
        uploadRoomFlats = new ArrayList<>();
        searchView = findViewById(R.id.sitSearchId);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseReference = FirebaseDatabase.getInstance().getReference("UploadData").child("Sit");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    UploadRoomFlat roomModel = dataSnapshot1.getValue(UploadRoomFlat.class);
                    uploadRoomFlats.add(roomModel);
                }

                sitAdepter = new SitAdepter(ViewSitlList.this, uploadRoomFlats);
                recyclerView.setAdapter(sitAdepter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    protected void onStart() {

        if (searchView != null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    search(s);
                    return false;
                }
            });
        }
        super.onStart();
    }

    private void search(String str) {
            ArrayList<UploadRoomFlat> myList = new ArrayList<>();
            for (UploadRoomFlat object : uploadRoomFlats) {
                if (object.getArea().toLowerCase().contains(str.toLowerCase())
                        || object.getPrice().toLowerCase().contains(str.toLowerCase())
                        || object.getFamily().toLowerCase().contains(str.toLowerCase())
                        || object.getBachelor().toLowerCase().contains(str.toLowerCase())
                        || object.getOther().toLowerCase().contains(str.toLowerCase())) {
                    myList.add(object);
                }
            }
            SitAdepter sitAdepter = new SitAdepter(myList);
            recyclerView.setAdapter(sitAdepter);
    }
}
