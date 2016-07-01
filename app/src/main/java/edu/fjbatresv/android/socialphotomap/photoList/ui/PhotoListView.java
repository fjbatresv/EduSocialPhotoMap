package edu.fjbatresv.android.socialphotomap.photoList.ui;

import edu.fjbatresv.android.socialphotomap.entities.Foto;

/**
 * Created by javie on 30/06/2016.
 */
public interface PhotoListView {
    void toggleList(boolean mostrar);
    void toggleProgress(boolean mostrar);

    void addPhoto(Foto foto);
    void removePhoto(Foto foto);
    void onPhotosError(String error);
}
