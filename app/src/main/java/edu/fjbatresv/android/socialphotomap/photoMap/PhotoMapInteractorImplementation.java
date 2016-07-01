package edu.fjbatresv.android.socialphotomap.photoMap;

/**
 * Created by javie on 1/07/2016.
 */
public class PhotoMapInteractorImplementation implements PhotoMapInteractor {
    private PhotoMapRepository repository;

    public PhotoMapInteractorImplementation(PhotoMapRepository repository) {
        this.repository = repository;
    }

    @Override
    public void subscribe() {
        repository.subscribe();
    }

    @Override
    public void unSubscribe() {
        repository.unSubscribe();
    }
}
