package edu.fjbatresv.android.socialphotomap.photoMap.DI;

import javax.inject.Singleton;

import dagger.Component;
import edu.fjbatresv.android.socialphotomap.SocialPhotoMapModule;
import edu.fjbatresv.android.socialphotomap.domain.DI.DomainModule;
import edu.fjbatresv.android.socialphotomap.libs.DI.LibsModule;
import edu.fjbatresv.android.socialphotomap.photoList.ui.FotoListFragment;
import edu.fjbatresv.android.socialphotomap.photoMap.ui.FotoMapFragment;

/**
 * Created by javie on 30/06/2016.
 */
@Singleton
@Component(modules = {DomainModule.class, LibsModule.class, SocialPhotoMapModule.class, PhotoMapModule.class})
public interface PhotoMapComponent {
    void inject(FotoMapFragment fragment);
}
