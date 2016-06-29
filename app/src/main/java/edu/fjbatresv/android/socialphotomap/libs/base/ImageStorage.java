package edu.fjbatresv.android.socialphotomap.libs.base;

import java.io.File;

/**
 * Created by javie on 28/06/2016.
 */
public interface ImageStorage {
    String getImageUrl(String id);
    void upload(File file, String string, ImageStorageFinishedListener listener);
}
