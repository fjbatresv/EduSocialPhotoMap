package edu.fjbatresv.android.socialphotomap.login;


import edu.fjbatresv.android.socialphotomap.login.events.LoginEvent;

/**
 * Created by javie on 7/06/2016.
 */
public interface LoginPresenter {
    void onCreate();
    void onDestroy();
    void validateLogin(String email, String password);
    void onEventMainThread(LoginEvent event);
    void registerNewUser(String email, String password);
}
