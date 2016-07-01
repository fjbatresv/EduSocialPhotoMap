package edu.fjbatresv.android.socialphotomap.photoMap.DI;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.fjbatresv.android.socialphotomap.domain.FirebaseApi;
import edu.fjbatresv.android.socialphotomap.domain.Util;
import edu.fjbatresv.android.socialphotomap.entities.Foto;
import edu.fjbatresv.android.socialphotomap.libs.base.EventBus;
import edu.fjbatresv.android.socialphotomap.libs.base.ImageLoader;
import edu.fjbatresv.android.socialphotomap.photoList.PhotoListInteractor;
import edu.fjbatresv.android.socialphotomap.photoList.PhotoListInteractorImplementation;
import edu.fjbatresv.android.socialphotomap.photoList.PhotoListPresenter;
import edu.fjbatresv.android.socialphotomap.photoList.PhotoListPresenterImplementation;
import edu.fjbatresv.android.socialphotomap.photoList.PhotoListRepository;
import edu.fjbatresv.android.socialphotomap.photoList.PhotoListRepositoryImplementation;
import edu.fjbatresv.android.socialphotomap.photoList.ui.PhotoListView;
import edu.fjbatresv.android.socialphotomap.photoList.ui.adapters.OnItemClickListener;
import edu.fjbatresv.android.socialphotomap.photoList.ui.adapters.PhotoListAdapter;
import edu.fjbatresv.android.socialphotomap.photoMap.PhotoMapInteractor;
import edu.fjbatresv.android.socialphotomap.photoMap.PhotoMapInteractorImplementation;
import edu.fjbatresv.android.socialphotomap.photoMap.PhotoMapPresenter;
import edu.fjbatresv.android.socialphotomap.photoMap.PhotoMapPresenterImplementation;
import edu.fjbatresv.android.socialphotomap.photoMap.PhotoMapRepository;
import edu.fjbatresv.android.socialphotomap.photoMap.PhotoMapRepositoryImplementation;
import edu.fjbatresv.android.socialphotomap.photoMap.ui.PhotoMapView;

/**
 * Created by javie on 30/06/2016.
 */
@Module
public class PhotoMapModule {
    private PhotoMapView view;
    private OnItemClickListener listener;

    public PhotoMapModule(PhotoMapView view) {
        this.view = view;
        this.listener = listener;
    }

    @Provides
    @Singleton
    PhotoMapView providesPhotoMapView(){
        return this.view;
    }

    @Provides
    @Singleton
    OnItemClickListener providesOnItemClickListener(){
        return this.listener;
    }

    @Provides
    @Singleton
    PhotoMapPresenter providesPhotoMapPresenter(EventBus bus, PhotoMapView view, PhotoMapInteractor interactor){
        return new PhotoMapPresenterImplementation(bus, view, interactor);
    }

    @Provides
    @Singleton
    PhotoMapInteractor providesPhotoMapInteractor(PhotoMapRepository repo){
        return new PhotoMapInteractorImplementation(repo);
    }

    @Singleton
    @Provides
    PhotoMapRepository providesPhotoMapRepository(FirebaseApi api, EventBus bus){
        return new PhotoMapRepositoryImplementation(api, bus);
    }

}
