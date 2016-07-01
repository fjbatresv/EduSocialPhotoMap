package edu.fjbatresv.android.socialphotomap.photoList;

import org.greenrobot.eventbus.Subscribe;
import org.w3c.dom.ProcessingInstruction;

import edu.fjbatresv.android.socialphotomap.entities.Foto;
import edu.fjbatresv.android.socialphotomap.libs.base.EventBus;
import edu.fjbatresv.android.socialphotomap.photoList.events.PhotoListEvent;
import edu.fjbatresv.android.socialphotomap.photoList.ui.PhotoListView;

/**
 * Created by javie on 30/06/2016.
 */
public class PhotoListPresenterImplementation implements PhotoListPresenter {
    private static final String EMPTY_LIST_ERROR = "No hay fotografias";
    private EventBus bus;
    private PhotoListView view;
    private PhotoListInteractor interactor;

    public PhotoListPresenterImplementation(EventBus bus, PhotoListView view, PhotoListInteractor interactor) {
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
        this.view = null;
    }

    @Override
    public void subscribe() {
        if (view != null){
            view.toggleList(false);
            view.toggleProgress(true);
        }
        interactor.subscribe();
    }

    @Override
    public void unSubscribe() {
        interactor.subscribe();
    }

    @Override
    public void removePhoto(Foto foto) {
        interactor.removePhoto(foto);
    }

    @Override
    @Subscribe
    public void onEventMainThread(PhotoListEvent event) {
        if (view != null){
            view.toggleProgress(false);
            view.toggleList(true);
        }
        String error = event.getError();
        if (error != null){
            if (error.isEmpty()){
                view.onPhotosError(EMPTY_LIST_ERROR);
            }else {
                view.onPhotosError(error);
            }
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
