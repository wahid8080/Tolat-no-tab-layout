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
import android.widget.Toast;

import com.example.wahid.tolate.FaceRoomOrFlat;
import com.example.wahid.tolate.ModelClass.UploadRoomFlat;
import com.example.wahid.tolate.R;

import java.util.ArrayList;

public class HostelAdepter extends RecyclerView.Adapter<HostelAdepter.MyViewHolder> {

    private Context context;
    private ArrayList<UploadRoomFlat> uploadRoomFlatsList;
    private Bitmap bitmap;

    public HostelAdepter(Context context, ArrayList<UploadRoomFlat> uploadRoomFlatsList) {
        this.context = context;
        this.uploadRoomFlatsList = uploadRoomFlatsList;
    }

    public HostelAdepter(ArrayList<UploadRoomFlat> uploadRoomFlatsList) {
        this.uploadRoomFlatsList = uploadRoomFlatsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_flat_by_list,viewGroup,false));

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {

        final UploadRoomFlat roomFlat = uploadRoomFlatsList.get(i);
        myViewHolder.houseName.setText(roomFlat.getHouseName());
        myViewHolder.price.setText(roomFlat.getPrice());
        myViewHolder.area.setText(roomFlat.getArea());
        bitmap = StringToBitMap(roomFlat.getImage1());
        myViewHolder.flatImg.setImageBitmap(bitmap);

        myViewHolder.seeDetailsOnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"clicked",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, FaceRoomOrFlat.class);
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

        myViewHolder.bookingOnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myViewHolder.bookingOnClick.getText().equals("Booking"))
                {
                    myViewHolder.bookingOnClick.setText("Booked");
                    Toast.makeText(context,"Booking Successful",Toast.LENGTH_SHORT).show();
                }
                else if (myViewHolder.bookingOnClick.getText().equals("Booked"))
                {
                    Toast.makeText(context,"Booking is Cancel",Toast.LENGTH_SHORT).show();
                    myViewHolder.bookingOnClick.setText("Booking");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return uploadRoomFlatsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        ImageView flatImg;
        TextView houseName,price,area;
        Button seeDetailsOnClick,bookingOnClick;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            flatImg = itemView.findViewById(R.id.imageViewForFlat);
            houseName = itemView.findViewById(R.id.houseNameForFlat);
            price = itemView.findViewById(R.id.priceForFlat);
            area = itemView.findViewById(R.id.houseAreaForFlat);
            seeDetailsOnClick = itemView.findViewById(R.id.seeDetailsOnClick);
            bookingOnClick = itemView.findViewById(R.id.bookingId);
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
