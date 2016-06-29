package edu.fjbatresv.android.socialphotomap.register;

import android.widget.Switch;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

import edu.fjbatresv.android.socialphotomap.domain.FirebaseActionListenerCallback;
import edu.fjbatresv.android.socialphotomap.domain.FirebaseApi;
import edu.fjbatresv.android.socialphotomap.libs.base.EventBus;
import edu.fjbatresv.android.socialphotomap.register.events.RegisterEvent;


/**
 * Created by javie on 10/06/2016.
 */
public class RegisterRepositoryImplementation implements RegisterRepository {
    private EventBus bus;
    private FirebaseApi api;
    private String emailA;
    private Firebase myUserReference;

    public RegisterRepositoryImplementation(EventBus bus, FirebaseApi api) {
        this.bus = bus;
        this.api = api;
    }

    private void registerNewUser(String email){
        if (email != null) {
            myUserReference = helper.getUserReference(email);
            User currentUser = new User();
            currentUser.setEmail(email);
            myUserReference.setValue(currentUser);
        }
    }

    @Override
    public void addUser(final String email, final String password) {
        this.emailA = email;
        api.signup(email, password, new FirebaseActionListenerCallback() {
            @Override
            public void onSuccess() {
                postEvent(RegisterEvent.singUpSuccess, null);
                registerNewUser(email);
            }

            @Override
            public void onError(FirebaseError error) {
                postEvent(RegisterEvent.singUpError, error.getMessage());
            }
        });
    }


    private void postEvent(int type, String error){
        bus.post(new RegisterEvent(this.emailA, error, type));
    }
}
