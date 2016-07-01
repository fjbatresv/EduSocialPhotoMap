package edu.fjbatresv.android.socialphotomap.photoMap;

import edu.fjbatresv.android.socialphotomap.entities.Foto;
import edu.fjbatresv.android.socialphotomap.photoMap.events.PhotoMapEvent;

/**
 * Created by javie on 1/07/2016.
 */
public interface PhotoMapPresenter {
    void onCreate();
    void onDestroy();

    void subscribe();
    void unSubscribe();

    void onEventMainThread(PhotoMapEvent event);
}
