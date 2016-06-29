package edu.fjbatresv.android.socialphotomap.domain.DI;

import javax.inject.Singleton;

import dagger.Component;
import edu.fjbatresv.android.socialphotomap.SocialPhotoMapModule;

/**
 * Created by javie on 28/06/2016.
 */
@Singleton
@Component(modules = {DomainModule.class, SocialPhotoMapModule.class})
public interface DomainComponent {

}
