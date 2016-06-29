package edu.fjbatresv.android.socialphotomap.register.ui;

/**
 * Created by javie on 10/06/2016.
 */
public interface RegisterView {
    void toggleInputs(boolean status);
    void toogleProgressBar(boolean status);
    void succesSignUp();
    void errorSignUp(String error);
}
