package edu.fjbatresv.android.socialphotomap.login;

import android.util.Log;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Map;

import edu.fjbatresv.android.socialphotomap.domain.FirebaseActionListenerCallback;
import edu.fjbatresv.android.socialphotomap.domain.FirebaseApi;
import edu.fjbatresv.android.socialphotomap.libs.GreenRobotEventBus;
import edu.fjbatresv.android.socialphotomap.libs.base.EventBus;
import edu.fjbatresv.android.socialphotomap.login.events.LoginEvent;


/**
 * Created by javie on 7/06/2016.
 */
public class LoginRepositoryImplementation implements LoginRepository {
    private EventBus bus;
    private FirebaseApi api;

    public LoginRepositoryImplementation(EventBus bus, FirebaseApi api) {
        this.bus = bus;
        this.api = api;
    }

    @Override
    public void signIn(String email, String password) {
        if(email != null && password != null){
            api.login(email, password, new FirebaseActionListenerCallback() {
                @Override
                public void onSuccess() {
                    String email = api.getAuthEmail();
                    postEvent(LoginEvent.onSignInSuccess, null, email);
                }

                @Override
                public void onError(FirebaseError error) {
                    postEvent(LoginEvent.onSignInError, error.getMessage(), null);
                }
            });
        }else{
            api.checkForSession(new FirebaseActionListenerCallback() {
                @Override
                public void onSuccess() {
                    String email = api.getAuthEmail();
                    postEvent(LoginEvent.onSignInSuccess, null, email);
                }

                @Override
                public void onError(FirebaseError error) {
                    postEvent(LoginEvent.onFailedToRecoverSession, null, null);
                }
            });
        }
    }

    @Override
    public void signUp(final String email, final String password) {
        api.signup(email, password, new FirebaseActionListenerCallback() {
            @Override
            public void onSuccess() {
                postEvent(LoginEvent.onSignUpSuccess, null, null);
                signIn(email, password);
            }

            @Override
            public void onError(FirebaseError error) {
                postEvent(LoginEvent.onSignUpError, error.getMessage(), null);
            }
        });
    }

    private void postEvent(int type, String error, String currentUserEmail){
        LoginEvent loginEvent = new LoginEvent();
        loginEvent.setEventType(type);
        loginEvent.setCurrentUserEmail(currentUserEmail);
        if(error != null){
            loginEvent.setError(error);
        }
        this.bus.post(loginEvent);
    }
}
