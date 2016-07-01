package edu.fjbatresv.android.socialphotomap.photoMap;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;

import edu.fjbatresv.android.socialphotomap.domain.FirebaseActionListenerCallback;
import edu.fjbatresv.android.socialphotomap.domain.FirebaseApi;
import edu.fjbatresv.android.socialphotomap.domain.FirebaseEventListenerCallback;
import edu.fjbatresv.android.socialphotomap.entities.Foto;
import edu.fjbatresv.android.socialphotomap.libs.base.EventBus;
import edu.fjbatresv.android.socialphotomap.photoList.PhotoListRepository;
import edu.fjbatresv.android.socialphotomap.photoList.events.PhotoListEvent;
import edu.fjbatresv.android.socialphotomap.photoMap.events.PhotoMapEvent;

/**
 * Created by javie on 1/07/2016.
 */
public class PhotoMapRepositoryImplementation implements PhotoMapRepository {

    private FirebaseApi api;
    private EventBus bus;

    public PhotoMapRepositoryImplementation(FirebaseApi api, EventBus bus) {
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
                    post(PhotoMapEvent.READ_EVENT, null, error.getMessage());
                }else{
                    post(PhotoMapEvent.READ_EVENT, null, "");
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
                post(PhotoMapEvent.READ_EVENT, foto, null);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Foto foto = dataSnapshot.getValue(Foto.class);
                foto.setId(dataSnapshot.getKey());
                post(PhotoMapEvent.DELETE_EVENT, foto, null);
            }

            @Override
            public void onCancelled(FirebaseError error) {
                if (error != null){
                    post(PhotoMapEvent.READ_EVENT, null, error.getMessage());
                }else{
                    post(PhotoMapEvent.READ_EVENT, null, "");
                }
            }
        });
    }

    @Override
    public void unSubscribe() {
        api.unSubscribe();
    }

    private void post(int type, Foto foto, String error){
        bus.post(new PhotoMapEvent(type, foto, error));
    }

}
