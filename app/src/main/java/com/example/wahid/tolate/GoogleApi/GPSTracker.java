package com.example.wahid.tolate.GoogleApi;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

public class GPSTracker implements LocationListener {

    private Context context;

    public GPSTracker(Context context) {
        this.context = context;
    }

    public Location getLocation()
    {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(context,"Location permission is not granted", Toast.LENGTH_SHORT).show();

            return null;
        }

        LocationManager mManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean isGPSLocationEnabel = mManager.isProviderEnabled(LocationManager.GPS_PROVIDER);


        if (isGPSLocationEnabel)
        {
            mManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,6000,10,this);

            Location l = mManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            return l;
        }
        else {
            Toast.makeText(context,"Enamle GPS", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
