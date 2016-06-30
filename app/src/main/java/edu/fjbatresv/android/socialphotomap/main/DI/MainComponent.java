package edu.fjbatresv.android.socialphotomap.main.DI;

import javax.inject.Singleton;

import dagger.Component;
import edu.fjbatresv.android.socialphotomap.SocialPhotoMapModule;
import edu.fjbatresv.android.socialphotomap.domain.DI.DomainModule;
import edu.fjbatresv.android.socialphotomap.libs.DI.LibsModule;
import edu.fjbatresv.android.socialphotomap.main.ui.MainActivity;

/**
 * Created by javie on 30/06/2016.
 */
@Singleton
@Component(modules = {MainModule.class, DomainModule.class, LibsModule.class, SocialPhotoMapModule.class})
public interface MainComponent {
    void inject(MainActivity activity);
}
