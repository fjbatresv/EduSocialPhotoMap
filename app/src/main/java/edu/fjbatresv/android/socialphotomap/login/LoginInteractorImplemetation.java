package edu.fjbatresv.android.socialphotomap.login;

/**
 * Created by javie on 7/06/2016.
 */
public class LoginInteractorImplemetation implements LoginInteractor {
    private LoginRepository repo;

    public LoginInteractorImplemetation(LoginRepository repo) {
        this.repo = repo;
    }

    @Override
    public void doSignIn(String email, String password) {
        repo.signIn(email, password);
    }

    @Override
    public void doSignUp(String email, String password) {
        repo.signUp(email, password);
    }

}
