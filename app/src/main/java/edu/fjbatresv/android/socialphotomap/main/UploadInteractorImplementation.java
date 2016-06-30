package edu.fjbatresv.android.socialphotomap.main;

import android.location.Location;

/**
 * Created by javie on 30/06/2016.
 */
public class UploadInteractorImplementation implements UploadInteractor {
    private MainRepository repo;

    public UploadInteractorImplementation(MainRepository repo) {
        this.repo = repo;
    }

    @Override
    public void execute(Location location, String path) {
        repo.uploadPhoto(location, path);
    }
}
