package edu.fjbatresv.android.socialphotomap.domain;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Map;

import edu.fjbatresv.android.socialphotomap.entities.Foto;

/**
 * Created by javie on 28/06/2016.
 */
public class FirebaseApi {
    private Firebase firebase;
    private ChildEventListener photosEventListener;

    public FirebaseApi(Firebase firebase) {
        this.firebase = firebase;
    }

    public void cheackForData(final FirebaseActionListenerCallback callback){
        firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0){
                    callback.onSuccess();
                }else{
                    callback.onError(null);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                callback.onError(firebaseError);
            }
        });
    }

    public void subscribe(final FirebaseEventListenerCallback callback){
        if (photosEventListener == null){
            photosEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    callback.onChildAdded(dataSnapshot);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    callback.onChildRemoved(dataSnapshot);
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    callback.onCancelled(firebaseError);
                }
            };
            firebase.addChildEventListener(photosEventListener);
        }
    }

    public void unSubscribe(){
        if (photosEventListener != null){
            firebase.removeEventListener(photosEventListener);
        }
    }

    public String create(){
        return firebase.push().getKey();
    }

    public void update(Foto foto){
        this.firebase.child(foto.getId()).setValue(foto);
    }

    public void remove(Foto foto, FirebaseActionListenerCallback callback){
        this.firebase.child(foto.getId()).removeValue();
        callback.onSuccess();
    }

    public String getAuthEmail(){
        String email = null;
        if (firebase.getAuth() != null){
            Map<String, Object> providerData = firebase.getAuth().getProviderData();
            email = providerData.get("email").toString();
        }
        return email;
    }

    public void logout(){
        firebase.unauth();
    }

    public void login(String email, String password,  final FirebaseActionListenerCallback callback){
        firebase.authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                callback.onSuccess();
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                callback.onError(firebaseError);
            }
        });
    }

    public void signup(String email, String password,  final FirebaseActionListenerCallback callback){
        firebase.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> stringObjectMap) {
                callback.onSuccess();
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                callback.onError(firebaseError);
            }
        });
    }

    public void checkForSession(FirebaseActionListenerCallback callback){
        if(firebase.getAuth() != null){
            callback.onSuccess();
        }else{
            callback.onError(null);
        }
    }
}
