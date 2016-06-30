package edu.fjbatresv.android.socialphotomap.main;

import android.location.Location;

import java.io.File;

import edu.fjbatresv.android.socialphotomap.domain.FirebaseApi;
import edu.fjbatresv.android.socialphotomap.entities.Foto;
import edu.fjbatresv.android.socialphotomap.libs.base.EventBus;
import edu.fjbatresv.android.socialphotomap.libs.base.ImageStorage;
import edu.fjbatresv.android.socialphotomap.libs.base.ImageStorageFinishedListener;
import edu.fjbatresv.android.socialphotomap.main.events.MainEvent;

/**
 * Created by javie on 30/06/2016.
 */
public class MainRepositoryImplementation implements MainRepository {
    private EventBus bus;
    private FirebaseApi api;
    private ImageStorage storage;

    public MainRepositoryImplementation(EventBus bus, FirebaseApi api, ImageStorage storage) {
        this.bus = bus;
        this.api = api;
        this.storage = storage;
    }

    @Override
    public void logout() {
        api.logout();
    }

    @Override
    public void uploadPhoto(Location location, String path) {
        final String newFotoId = api.create();
        final Foto foto = new Foto();
        foto.setId(newFotoId);
        foto.setEmail(api.getAuthEmail());
        if (location != null){
            foto.setLatitude(location.getLatitude());
            foto.setLongitud(location.getLongitude());
        }
        post(MainEvent.UPLOAD_INIT, null);
        ImageStorageFinishedListener listener = new ImageStorageFinishedListener() {
            @Override
            public void onSuccess() {
                String url = storage.getImageUrl(newFotoId);
                foto.setUrl(url);
                api.update(foto);
                post(MainEvent.UPLOAD_COMPLETE, null);
            }

            @Override
            public void onError(String error) {
                post(MainEvent.UPLOAD_ERROR, error);
            }
        };
        storage.upload(new File(path), newFotoId, listener);
    }

    private void post(int type, String error){
        bus.post(new MainEvent(type, error));
    }
}
