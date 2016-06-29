package edu.fjbatresv.android.socialphotomap.login;

/**
 * Created by javie on 7/06/2016.
 */
//Aqui y solo aqui se usa la conexion con firebase
public interface LoginRepository {
    void signIn(String email, String password);
    void signUp(String email, String password);
}
