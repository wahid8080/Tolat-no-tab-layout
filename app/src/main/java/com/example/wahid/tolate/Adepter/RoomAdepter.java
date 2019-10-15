package com.example.wahid.tolate.Adepter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wahid.tolate.FaceRoomOrFlat;
import com.example.wahid.tolate.ModelClass.UploadRoomFlat;
import com.example.wahid.tolate.R;

import java.util.ArrayList;

public class RoomAdepter extends RecyclerView.Adapter<RoomAdepter.MyViewHolder> {


    private Context context;
    private ArrayList<UploadRoomFlat> uploadRoomFlats;
    private Bitmap bitmap;

    public RoomAdepter(Context context, ArrayList<UploadRoomFlat> uploadRoomFlats) {
        this.context = context;
        this.uploadRoomFlats = uploadRoomFlats;
    }

    public RoomAdepter(ArrayList<UploadRoomFlat> uploadRoomFlats) {
        this.uploadRoomFlats = uploadRoomFlats;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_room_by_list,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        final UploadRoomFlat roomFlat = uploadRoomFlats.get(i);

        myViewHolder.name.setText("House Name: "+roomFlat.getHouseName());
        myViewHolder.area.setText("Thana/Area: "+roomFlat.getArea());
        myViewHolder.category.setText("Category: "+roomFlat.getFamily()+"/"+roomFlat.getBachelor()+"/"+roomFlat.getOther());
        myViewHolder.price.setText("Price: "+roomFlat.getPrice());
        bitmap = StringToBitMap(roomFlat.getImage1());
        myViewHolder.imageView.setImageBitmap(bitmap);

        myViewHolder.seeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context, FaceRoomOrFlat.class);
                intent.putExtra("houseName",roomFlat.getHouseName());
                intent.putExtra("price",roomFlat.getPrice());
                intent.putExtra("area",roomFlat.getArea());
                intent.putExtra("category",roomFlat.getBachelor());
                intent.putExtra("details",roomFlat.getDetails());
                intent.putExtra("road",roomFlat.getRoadNo());
                intent.putExtra("phone",roomFlat.getPhoneNumber());
                intent.putExtra("img1",roomFlat.getImage1());
                intent.putExtra("img2",roomFlat.getImage2());
                intent.putExtra("img3",roomFlat.getImage3());
                intent.putExtra("img4",roomFlat.getImage4());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return uploadRoomFlats.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView name,area,category,price;
        Button seeMore,bookingNow;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageForViewProduct);

            name = itemView.findViewById(R.id.houseName);
            area = itemView.findViewById(R.id.houseArea);
            category = itemView.findViewById(R.id.houseCategory);
            price = itemView.findViewById(R.id.housePrice);
            seeMore = itemView.findViewById(R.id.seeMore);
            bookingNow = itemView.findViewById(R.id.bookingNow);

        }
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

}
