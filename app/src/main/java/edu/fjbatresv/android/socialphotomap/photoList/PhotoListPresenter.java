package edu.fjbatresv.android.socialphotomap.photoList;

import edu.fjbatresv.android.socialphotomap.entities.Foto;
import edu.fjbatresv.android.socialphotomap.photoList.events.PhotoListEvent;

/**
 * Created by javie on 30/06/2016.
 */
public interface PhotoListPresenter {
    void onCreate();
    void onDestroy();

    void subscribe();
    void unSubscribe();

    void removePhoto(Foto foto);

    void onEventMainThread(PhotoListEvent event);
}
