package edu.fjbatresv.android.socialphotomap.login;

/**
 * Created by javie on 7/06/2016.
 */
public interface LoginInteractor {
    void doSignIn(String email, String password);
    void doSignUp(String email, String password);
}
