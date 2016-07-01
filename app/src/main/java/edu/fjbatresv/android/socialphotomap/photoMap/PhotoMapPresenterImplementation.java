package edu.fjbatresv.android.socialphotomap.photoMap;

import org.greenrobot.eventbus.Subscribe;

import edu.fjbatresv.android.socialphotomap.libs.base.EventBus;
import edu.fjbatresv.android.socialphotomap.photoList.events.PhotoListEvent;
import edu.fjbatresv.android.socialphotomap.photoMap.events.PhotoMapEvent;
import edu.fjbatresv.android.socialphotomap.photoMap.ui.PhotoMapView;

/**
 * Created by javie on 1/07/2016.
 */
public class PhotoMapPresenterImplementation implements PhotoMapPresenter {
    private EventBus bus;
    private PhotoMapView view;
    private PhotoMapInteractor interactor;

    public PhotoMapPresenterImplementation(EventBus bus, PhotoMapView view, PhotoMapInteractor interactor) {
        this.bus = bus;
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void onCreate() {
        bus.register(this);
    }

    @Override
    public void onDestroy() {
        bus.unRegister(this);
        view = null;
    }

    @Override
    public void subscribe() {
        interactor.subscribe();
    }

    @Override
    public void unSubscribe() {
        interactor.unSubscribe();
    }

    @Override
    @Subscribe
    public void onEventMainThread(PhotoMapEvent event) {
        if (view != null){
            String error = event.getError();
            if (error != null){
                    view.onPhotosError(error);
            }else {
                switch (event.getType()){
                    case PhotoListEvent.READ_EVENT:
                        view.addPhoto(event.getFoto());
                        break;
                    case PhotoListEvent.DELETE_EVENT:
                        view.removePhoto(event.getFoto());
                        break;
                }
            }
        }
    }
}
