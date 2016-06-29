package edu.fjbatresv.android.socialphotomap.register;

/**
 * Created by javie on 10/06/2016.
 */
public class RegisterInteractorImplementation implements RegisterInteractor {
    private RegisterRepository repo;

    public RegisterInteractorImplementation() {
        this.repo = new RegisterRepositoryImplementation();
    }

    @Override
    public void addUser(String email, String password) {
        repo.addUser(email, password);
    }
}
