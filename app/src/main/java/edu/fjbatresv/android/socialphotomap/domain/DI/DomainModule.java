package edu.fjbatresv.android.socialphotomap.domain.DI;

import android.content.Context;
import android.location.Geocoder;

import com.firebase.client.Firebase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.fjbatresv.android.socialphotomap.domain.FirebaseApi;
import edu.fjbatresv.android.socialphotomap.domain.Util;

/**
 * Created by javie on 28/06/2016.
 */
@Module
public class DomainModule {
    private final static String FIREBASE_URL = "https://socialphotomap.firebaseio.com/";

    @Provides
    @Singleton
    FirebaseApi providesFirebaseApi(Firebase firebase){
        return new FirebaseApi(firebase);
    }

    @Provides
    @Singleton
    Firebase providesFirebase(){
        return new Firebase(FIREBASE_URL);
    }

    @Provides
    @Singleton
    Util providesUtil(Geocoder geocoder){
        return new Util(geocoder);
    }

    @Provides
    @Singleton
    Geocoder providesGeoCoder(Context context){
        return new Geocoder(context);
    }
}
