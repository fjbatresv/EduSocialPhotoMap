package edu.fjbatresv.android.socialphotomap;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by javie on 28/06/2016.
 */
@Module
public class SocialPhotoMapModule {
    private SocialPhotoMapApp app;

    public SocialPhotoMapModule(SocialPhotoMapApp app) {
        this.app = app;
    }

    @Provides
    @Singleton
    Application providesSocialPhotoMapApp(){
        return this.app;
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Application application){
        return application.getSharedPreferences(
                app.getSharedPreferencesName(),
                Context.MODE_PRIVATE
        );
    }

    @Provides
    @Singleton
    Context provideApplicationContext(){
        return this.app.getApplicationContext();
    }
}
