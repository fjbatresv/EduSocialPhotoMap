package edu.fjbatresv.android.socialphotomap.libs.DI;

import javax.inject.Singleton;

import dagger.Component;
import edu.fjbatresv.android.socialphotomap.SocialPhotoMapModule;

/**
 * Created by javie on 29/06/2016.
 */
@Singleton
@Component(modules = {LibsModule.class, SocialPhotoMapModule.class})
public interface LibsComponent {
}
