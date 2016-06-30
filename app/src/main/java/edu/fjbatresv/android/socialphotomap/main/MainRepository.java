package edu.fjbatresv.android.socialphotomap.main;

import android.location.Location;

/**
 * Created by javie on 29/06/2016.
 */
public interface MainRepository {
    void logout();
    void uploadPhoto(Location location, String path);
}
