package edu.fjbatresv.android.socialphotomap.main.ui;

/**
 * Created by javie on 29/06/2016.
 */
public interface MainView {
    void onUploadInit();
    void onUploadComplete();
    void onUploadError(String error);
}
