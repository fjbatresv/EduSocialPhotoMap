package edu.fjbatresv.android.socialphotomap.login;

import org.greenrobot.eventbus.Subscribe;

import edu.fjbatresv.android.socialphotomap.libs.GreenRobotEventBus;
import edu.fjbatresv.android.socialphotomap.libs.base.EventBus;
import edu.fjbatresv.android.socialphotomap.login.events.LoginEvent;
import edu.fjbatresv.android.socialphotomap.login.ui.LoginView;


/**
 * Created by javie on 29/06/2016.
 */
public class LoginPresenterImplementation implements LoginPresenter {
    private EventBus eventBus;
    private LoginView loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenterImplementation(LoginView loginView, EventBus eventBus, LoginInteractor interactor) {
        this.loginView = loginView;
        this.loginInteractor = interactor;
        this.eventBus = eventBus;
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        loginView = null;
        eventBus.unRegister(this);
    }

    @Override
    public void validateLogin(String email, String password) {
        if(loginView != null){
            loginView.disableInputs();
            loginView.showProgress();
        }
        loginInteractor.doSignIn(email, password);
    }


    @Override
    @Subscribe
    public void onEventMainThread(LoginEvent event) {
        switch (event.getEventType()){
            case LoginEvent.onSignInSuccess:
                onSignInSuccess(event.getCurrentUserEmail());
                break;
            case LoginEvent.onSignInError:
                onSignInError(event.getError());
                break;
            case LoginEvent.onSignUpSuccess:
                onSignUpSuccess(event.getCurrentUserEmail());
                break;
            case LoginEvent.onSignUpError:
                onSignUpError(event.getError());
                break;
            case LoginEvent.onFailedToRecoverSession:
                onFailedRecoverSession();
                break;
        }
    }

    @Override
    public void registerNewUser(String email, String password) {
        if(loginView != null){
            loginView.disableInputs();
            loginView.showProgress();
        }
        loginInteractor.doSignUp(email, password);
    }

    private void onFailedRecoverSession() {
        if(loginView != null){
            loginView.enableInputs();
            loginView.hideProgress();
        }
    }

    private void onSignInSuccess(String email){
        if(loginView != null){
            loginView.setUserEmail(email);
            loginView.navigateToMainScreen();
        }
    }

    private void onSignInError(String error){
        if(loginView != null){
            loginView.enableInputs();
            loginView.hideProgress();
            loginView.loginError(error);
        }
    }

    private void onSignUpSuccess(String email){
        if(loginView != null){
            loginView.newUserSuccess();
        }
    }

    private void onSignUpError(String error){
        if(loginView != null){
            loginView.enableInputs();
            loginView.hideProgress();
            loginView.newUserError(error);
        }
    }

}
