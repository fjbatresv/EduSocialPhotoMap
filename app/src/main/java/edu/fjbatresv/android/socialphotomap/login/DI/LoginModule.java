package edu.fjbatresv.android.socialphotomap.login.DI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.fjbatresv.android.socialphotomap.domain.FirebaseApi;
import edu.fjbatresv.android.socialphotomap.libs.base.EventBus;
import edu.fjbatresv.android.socialphotomap.login.LoginInteractor;
import edu.fjbatresv.android.socialphotomap.login.LoginInteractorImplemetation;
import edu.fjbatresv.android.socialphotomap.login.LoginPresenter;
import edu.fjbatresv.android.socialphotomap.login.LoginPresenterImplementation;
import edu.fjbatresv.android.socialphotomap.login.LoginRepository;
import edu.fjbatresv.android.socialphotomap.login.LoginRepositoryImplementation;
import edu.fjbatresv.android.socialphotomap.login.ui.LoginView;

/**
 * Created by javie on 29/06/2016.
 */
@Module
public class LoginModule {
    LoginView view;

    public LoginModule(LoginView view) {
        this.view = view;
    }

    @Provides
    @Singleton
    LoginView providesLoginView(){
        return this.view;
    }

    @Provides
    @Singleton
    LoginPresenter providesLoginPresenter(LoginView loginView, EventBus eventBus, LoginInteractor interactor){
        return new LoginPresenterImplementation(loginView, eventBus, interactor);
    }

    @Provides
    @Singleton
    LoginInteractor providesLoginInteractor(LoginRepository repo){
        return new LoginInteractorImplemetation(repo);
    }

    @Provides
    @Singleton
    LoginRepository providesLoginRepository(EventBus bus, FirebaseApi api){
        return new LoginRepositoryImplementation(bus, api);
    }
}
