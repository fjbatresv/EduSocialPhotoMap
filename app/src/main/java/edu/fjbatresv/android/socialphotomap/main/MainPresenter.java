package edu.fjbatresv.android.socialphotomap.main;

import android.location.Location;

import edu.fjbatresv.android.socialphotomap.main.events.MainEvent;

/**
 * Created by javie on 29/06/2016.
 */
public interface MainPresenter {
    void onCreate();
    void onDestroy();
    void logout();
    void uploadPhoto(Location location, String path);
    void onEventMainThread(MainEvent event);
}
