package edu.fjbatresv.android.socialphotomap.photoList;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;

import edu.fjbatresv.android.socialphotomap.domain.FirebaseActionListenerCallback;
import edu.fjbatresv.android.socialphotomap.domain.FirebaseApi;
import edu.fjbatresv.android.socialphotomap.domain.FirebaseEventListenerCallback;
import edu.fjbatresv.android.socialphotomap.entities.Foto;
import edu.fjbatresv.android.socialphotomap.libs.base.EventBus;
import edu.fjbatresv.android.socialphotomap.photoList.events.PhotoListEvent;

/**
 * Created by javie on 30/06/2016.
 */
public class PhotoListRepositoryImplementation implements PhotoListRepository {
    private FirebaseApi api;
    private EventBus bus;

    public PhotoListRepositoryImplementation(FirebaseApi api, EventBus bus) {
        this.api = api;
        this.bus = bus;
    }

    @Override
    public void subscribe() {
        api.cheackForData(new FirebaseActionListenerCallback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError(FirebaseError error) {
                if (error != null){
                    post(PhotoListEvent.READ_EVENT, null, error.getMessage());
                }else{
                    post(PhotoListEvent.READ_EVENT, null, "");
                }
            }
        });
        api.subscribe(new FirebaseEventListenerCallback() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot) {
                Foto foto = dataSnapshot.getValue(Foto.class);
                foto.setId(dataSnapshot.getKey());
                String email = api.getAuthEmail();
                foto.setPublishedByMe(email.equals(foto.getEmail()));
                post(PhotoListEvent.READ_EVENT, foto, null);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Foto foto = dataSnapshot.getValue(Foto.class);
                foto.setId(dataSnapshot.getKey());
                post(PhotoListEvent.DELETE_EVENT, foto, null);
            }

            @Override
            public void onCancelled(FirebaseError error) {
                if (error != null){
                    post(PhotoListEvent.READ_EVENT, null, error.getMessage());
                }else{
                    post(PhotoListEvent.READ_EVENT, null, "");
                }
            }
        });
    }

    @Override
    public void unSubscribe() {
        api.unSubscribe();
    }

    @Override
    public void removePhoto(final Foto foto) {
        api.remove(foto, new FirebaseActionListenerCallback() {
            @Override
            public void onSuccess() {
                post(PhotoListEvent.DELETE_EVENT, foto, null);
            }

            @Override
            public void onError(FirebaseError error) {
                if (error != null){
                    post(PhotoListEvent.DELETE_EVENT, null, error.getMessage());
                }else{
                    post(PhotoListEvent.DELETE_EVENT, null, "");
                }
            }
        });
    }

    private void post(int type, Foto foto, String error){
        bus.post(new PhotoListEvent(type, foto, error));
    }
}
