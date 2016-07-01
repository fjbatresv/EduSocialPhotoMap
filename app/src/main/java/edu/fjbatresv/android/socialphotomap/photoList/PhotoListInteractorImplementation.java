package edu.fjbatresv.android.socialphotomap.photoList;

import edu.fjbatresv.android.socialphotomap.entities.Foto;

/**
 * Created by javie on 30/06/2016.
 */
public class PhotoListInteractorImplementation implements PhotoListInteractor {
    private PhotoListRepository repo;

    public PhotoListInteractorImplementation(PhotoListRepository repo) {
        this.repo = repo;
    }

    @Override
    public void subscribe() {
        repo.subscribe();
    }

    @Override
    public void unSubscribe() {
        repo.unSubscribe();
    }

    @Override
    public void removePhoto(Foto foto) {
        repo.removePhoto(foto);
    }
}
