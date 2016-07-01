package edu.fjbatresv.android.socialphotomap.photoMap.ui;

import edu.fjbatresv.android.socialphotomap.entities.Foto;

/**
 * Created by javie on 1/07/2016.
 */
public interface PhotoMapView {
    void addPhoto(Foto foto);
    void removePhoto(Foto foto);
    void onPhotosError(String error);
}
