package edu.fjbatresv.android.socialphotomap.login.events;

/**
 * Created by javie on 8/06/2016.
 */
public class LoginEvent {
    public final static int onSignInError = 0;
    public final static int onSignInSuccess = 1;
    public final static int onFailedToRecoverSession = 4;
    public final static int onSignUpError = 2;
    public final static int onSignUpSuccess = 3;

    private int eventType;
    private String error;
    private String currentUserEmail;

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getCurrentUserEmail() {
        return currentUserEmail;
    }

    public void setCurrentUserEmail(String currentUserEmail) {
        this.currentUserEmail = currentUserEmail;
    }
}
