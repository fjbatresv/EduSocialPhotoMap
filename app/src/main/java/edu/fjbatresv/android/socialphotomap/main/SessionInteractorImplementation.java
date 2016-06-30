package edu.fjbatresv.android.socialphotomap.main;

/**
 * Created by javie on 30/06/2016.
 */
public class SessionInteractorImplementation implements SessionInteractor {
    private MainRepository repo;

    public SessionInteractorImplementation(MainRepository repo) {
        this.repo = repo;
    }

    @Override
    public void logout() {
        repo.logout();
    }
}
