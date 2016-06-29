package edu.fjbatresv.android.socialphotomap.login.DI;

import javax.inject.Singleton;

import dagger.Component;
import edu.fjbatresv.android.socialphotomap.SocialPhotoMapModule;
import edu.fjbatresv.android.socialphotomap.domain.DI.DomainModule;
import edu.fjbatresv.android.socialphotomap.libs.DI.LibsModule;
import edu.fjbatresv.android.socialphotomap.login.ui.LoginActivity;

/**
 * Created by javie on 29/06/2016.
 */
@Singleton
@Component(modules = {LibsModule.class, DomainModule.class, LoginModule.class, SocialPhotoMapModule.class})
public interface LoginComponent {
    void inject(LoginActivity activity);
}
