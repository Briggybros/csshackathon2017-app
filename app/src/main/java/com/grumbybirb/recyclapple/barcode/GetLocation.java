package com.grumbybirb.recyclapple.barcode;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import static android.content.pm.PackageManager.PERMISSION_DENIED;

/**
 * Created by Joe on 28/02/2017.
 */

public class GetLocation {
    public static Location getLocation(Activity thisActivity) {

        return gps(thisActivity);
    }

    private static Location gps(Activity thisActivity) {
        LocationManager locationManager = (LocationManager)
                thisActivity.getSystemService(Context.LOCATION_SERVICE);
        if (PERMISSION_DENIED == ContextCompat.checkSelfPermission(thisActivity, Manifest.permission.ACCESS_FINE_LOCATION)) {
            requestLocPerms(thisActivity);
        }
        Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (loc == null) {
            Log.e("location", "Location not found");
        }
        return loc;
    }

    public static void requestLocPerms(Activity thisActivity) {
        int permissionCheck = ContextCompat.checkSelfPermission(thisActivity, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck == PERMISSION_DENIED) {
            String permissions[] = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(thisActivity, permissions, 0);
        }
    }
}
