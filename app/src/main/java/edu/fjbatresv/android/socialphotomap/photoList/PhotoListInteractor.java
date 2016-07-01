package edu.fjbatresv.android.socialphotomap.photoList;

import edu.fjbatresv.android.socialphotomap.entities.Foto;

/**
 * Created by javie on 30/06/2016.
 */
public interface PhotoListInteractor {
    void subscribe();
    void unSubscribe();

    void removePhoto(Foto foto);

}
