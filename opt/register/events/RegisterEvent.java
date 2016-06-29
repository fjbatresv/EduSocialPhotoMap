package edu.fjbatresv.android.socialphotomap.register.events;

/**
 * Created by javie on 10/06/2016.
 */
public class RegisterEvent {
    public static final int singUpSuccess = 0;
    public static final int singUpError = 1;

    private String email;
    private String error;
    private int event;

    public RegisterEvent() {
    }

    public RegisterEvent(String email, String error, int event) {
        this.email = email;
        this.error = error;
        this.event = event;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getEvent() {
        return event;
    }

    public void setEvent(int event) {
        this.event = event;
    }
}
