package edu.fjbatresv.android.socialphotomap.photoList.DI;

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

/**
 * Created by javie on 30/06/2016.
 */
@Module
public class PhotoListModule {
    private PhotoListView view;
    private OnItemClickListener listener;

    public PhotoListModule(PhotoListView view, OnItemClickListener listener) {
        this.view = view;
        this.listener = listener;
    }

    @Provides
    @Singleton
    PhotoListView providesPhotoListView(){
        return this.view;
    }

    @Provides
    @Singleton
    OnItemClickListener providesOnItemClickListener(){
        return this.listener;
    }

    @Provides
    @Singleton
    PhotoListPresenter providesPhotoListPresenter(EventBus bus, PhotoListView view, PhotoListInteractor interactor){
        return new PhotoListPresenterImplementation(bus, view, interactor);
    }

    @Provides
    @Singleton
    PhotoListInteractor providesPhotoListInteractor(PhotoListRepository repo){
        return new PhotoListInteractorImplementation(repo);
    }

    @Singleton
    @Provides
    PhotoListRepository providesPhotoListRepository(FirebaseApi api, EventBus bus){
        return new PhotoListRepositoryImplementation(api, bus);
    }

    @Provides
    @Singleton
    PhotoListAdapter providesPhotoListAdapter(Util utils, List<Foto> fotoList, ImageLoader imageLoader, OnItemClickListener listener){
        return new PhotoListAdapter(utils, fotoList, imageLoader, listener);
    }

    @Provides
    @Singleton
    List<Foto> providesFotoList(){
        return new ArrayList<Foto>();
    }
}
