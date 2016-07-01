package edu.fjbatresv.android.socialphotomap;

import android.app.Application;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.firebase.client.Firebase;

import edu.fjbatresv.android.socialphotomap.domain.DI.DomainModule;
import edu.fjbatresv.android.socialphotomap.libs.DI.LibsModule;

import edu.fjbatresv.android.socialphotomap.login.DI.DaggerLoginComponent;
import edu.fjbatresv.android.socialphotomap.login.DI.LoginComponent;
import edu.fjbatresv.android.socialphotomap.login.DI.LoginModule;
import edu.fjbatresv.android.socialphotomap.login.ui.LoginView;

import edu.fjbatresv.android.socialphotomap.main.DI.DaggerMainComponent;
import edu.fjbatresv.android.socialphotomap.main.DI.MainComponent;
import edu.fjbatresv.android.socialphotomap.main.DI.MainModule;
import edu.fjbatresv.android.socialphotomap.main.ui.MainView;
import edu.fjbatresv.android.socialphotomap.photoList.DI.DaggerPhotoListComponent;
import edu.fjbatresv.android.socialphotomap.photoList.DI.PhotoListComponent;
import edu.fjbatresv.android.socialphotomap.photoList.DI.PhotoListModule;
import edu.fjbatresv.android.socialphotomap.photoList.ui.FotoListFragment;
import edu.fjbatresv.android.socialphotomap.photoList.ui.PhotoListView;
import edu.fjbatresv.android.socialphotomap.photoList.ui.adapters.OnItemClickListener;
import edu.fjbatresv.android.socialphotomap.photoMap.DI.DaggerPhotoMapComponent;
import edu.fjbatresv.android.socialphotomap.photoMap.DI.PhotoMapComponent;
import edu.fjbatresv.android.socialphotomap.photoMap.DI.PhotoMapModule;
import edu.fjbatresv.android.socialphotomap.photoMap.ui.FotoMapFragment;
import edu.fjbatresv.android.socialphotomap.photoMap.ui.PhotoMapView;

/**
 * Created by javie on 28/06/2016.
 */
public class SocialPhotoMapApp extends Application {
    private final static String EMAIL_KEY = "email";
    private final static String SHARED_PREFERENCES_NAME = "UserPrefs";
    private final static String FIREBASE_URL = "https://socialphotomap.firebaseio.com/";

    private DomainModule domainModule;
    private SocialPhotoMapModule socialPhotoMapModule;

    @Override
    public void onCreate() {
        super.onCreate();
        initFirebase();
        initModule();
    }

    private void initModule() {
        socialPhotoMapModule = new SocialPhotoMapModule(this);
        domainModule = new DomainModule();
    }

    private void initFirebase(){
        Firebase.setAndroidContext(this);
    }

    public LoginComponent getLoginComponent(LoginView view){
        return DaggerLoginComponent
                .builder()
                .socialPhotoMapModule(socialPhotoMapModule)
                .domainModule(domainModule)
                .libsModule(new LibsModule(null))
                .loginModule(new LoginModule(view))
                .build();
    }
    public MainComponent getMainComponent(MainView view, FragmentManager manager, Fragment[] fragments, String[] titles){
        return DaggerMainComponent
                .builder()
                .socialPhotoMapModule(socialPhotoMapModule)
                .domainModule(domainModule)
                .libsModule(new LibsModule(null))
                .mainModule(new MainModule(view, manager, fragments, titles))
                .build();
    }

    public PhotoListComponent getPhotoListComponent(FotoListFragment fragment, PhotoListView view, OnItemClickListener listener){
        return DaggerPhotoListComponent
                .builder()
                .socialPhotoMapModule(socialPhotoMapModule)
                .domainModule(domainModule)
                .libsModule(new LibsModule(fragment))
                .photoListModule(new PhotoListModule(view, listener))
                .build();
    }

    public PhotoMapComponent getPhotoMapComponent(FotoMapFragment fotoMapFragment, PhotoMapView view) {
        return DaggerPhotoMapComponent
                .builder()
                .socialPhotoMapModule(socialPhotoMapModule)
                .domainModule(domainModule)
                .libsModule(new LibsModule(fotoMapFragment))
                .photoMapModule(new PhotoMapModule(view))
                .build();
    }

//GETTERS
    public String getEmailKey() {
        return EMAIL_KEY;
    }

    public String getSharedPreferencesName() {
        return SHARED_PREFERENCES_NAME;
    }

}
