package edu.fjbatresv.android.socialphotomap.register;


import edu.fjbatresv.android.socialphotomap.register.events.RegisterEvent;

/**
 * Created by javie on 10/06/2016.
 */
public interface RegisterPresenter {
    void onCreate();
    void onDestoy();
    void addUser(String email, String password);
    void onEventMainThred(RegisterEvent event);
}
