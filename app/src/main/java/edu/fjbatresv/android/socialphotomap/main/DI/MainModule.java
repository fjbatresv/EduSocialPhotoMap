package edu.fjbatresv.android.socialphotomap.main.DI;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.fjbatresv.android.socialphotomap.domain.FirebaseApi;
import edu.fjbatresv.android.socialphotomap.libs.base.EventBus;
import edu.fjbatresv.android.socialphotomap.libs.base.ImageStorage;
import edu.fjbatresv.android.socialphotomap.main.MainPresenter;
import edu.fjbatresv.android.socialphotomap.main.MainPresenterImplementation;
import edu.fjbatresv.android.socialphotomap.main.MainRepository;
import edu.fjbatresv.android.socialphotomap.main.MainRepositoryImplementation;
import edu.fjbatresv.android.socialphotomap.main.SessionInteractor;
import edu.fjbatresv.android.socialphotomap.main.SessionInteractorImplementation;
import edu.fjbatresv.android.socialphotomap.main.UploadInteractor;
import edu.fjbatresv.android.socialphotomap.main.UploadInteractorImplementation;
import edu.fjbatresv.android.socialphotomap.main.ui.MainView;
import edu.fjbatresv.android.socialphotomap.main.ui.adapters.MainSectionsPagerAdapter;

/**
 * Created by javie on 30/06/2016.
 */
@Module
public class MainModule {
    private MainView view;
    private String[] titles;
    private Fragment[] fragments;
    private FragmentManager fragmentManager;

    public MainModule(MainView view, FragmentManager fragmentManager, Fragment[] fragments, String[] titles) {
        this.view = view;
        this.titles = titles;
        this.fragments = fragments;
        this.fragmentManager = fragmentManager;
    }

    @Provides
    @Singleton
    MainView providesMainView() {
        return this.view;
    }

    @Provides @Singleton
    MainPresenter providesMainPresenter(MainView view, EventBus eventBus, UploadInteractor uploadInteractor, SessionInteractor sessionInteractor) {
        return new MainPresenterImplementation(view, eventBus, uploadInteractor, sessionInteractor);
    }

    @Provides @Singleton
    UploadInteractor providesUploadInteractor(MainRepository repository) {
        return new UploadInteractorImplementation(repository);
    }

    @Provides @Singleton
    SessionInteractor providesSessionInteractor(MainRepository repository) {
        return new SessionInteractorImplementation(repository);
    }

    @Provides @Singleton
    MainRepository providesMainRepository(EventBus eventBus, FirebaseApi firebase, ImageStorage imageStorage) {
        return new MainRepositoryImplementation(eventBus, firebase, imageStorage);
    }

    @Provides @Singleton
    MainSectionsPagerAdapter providesAdapter(FragmentManager fm, Fragment[] fragments, String[] titles){
        return new MainSectionsPagerAdapter(fm, fragments, titles);
    }

    @Provides @Singleton
    FragmentManager providesAdapterFragmentManager(){
        return this.fragmentManager;
    }

    @Provides @Singleton
    Fragment[] providesFragmentArrayForAdapter(){
        return this.fragments;
    }

    @Provides @Singleton
    String[] providesStringArrayForAdapter(){
        return this.titles;
    }
}
