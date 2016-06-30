package edu.fjbatresv.android.socialphotomap.main;

import android.location.Location;

import org.greenrobot.eventbus.Subscribe;

import edu.fjbatresv.android.socialphotomap.libs.base.EventBus;
import edu.fjbatresv.android.socialphotomap.main.events.MainEvent;
import edu.fjbatresv.android.socialphotomap.main.ui.MainView;

/**
 * Created by javie on 30/06/2016.
 */
public class MainPresenterImplementation implements MainPresenter {
    private MainView view;
    private EventBus bus;
    private UploadInteractor uploadInteractor;
    private SessionInteractor sessionInteractor;

    public MainPresenterImplementation(MainView view, EventBus bus, UploadInteractor uploadInteractor, SessionInteractor sessionInteractor) {
        this.view = view;
        this.bus = bus;
        this.uploadInteractor = uploadInteractor;
        this.sessionInteractor = sessionInteractor;
    }

    @Override
    public void onCreate() {
        bus.register(this);
    }

    @Override
    public void onDestroy() {
        view = null;
        bus.unRegister(this);
    }

    @Override
    public void logout() {
        sessionInteractor.logout();
    }

    @Override
    public void uploadPhoto(Location location, String path) {
        uploadInteractor.execute(location, path);
    }

    @Override
    @Subscribe
    public void onEventMainThread(MainEvent event) {
        if (view != null){
            switch (event.getType()){
                case MainEvent.UPLOAD_INIT:
                    view.onUploadInit();
                    break;
                case MainEvent.UPLOAD_COMPLETE:
                    view.onUploadComplete();
                    break;
                case MainEvent.UPLOAD_ERROR:
                    view.onUploadError(event.getError());
                    break;
            }
        }
    }
}
