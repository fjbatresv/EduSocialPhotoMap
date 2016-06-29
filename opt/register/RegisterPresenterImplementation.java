package edu.fjbatresv.android.socialphotomap.register;

import org.greenrobot.eventbus.Subscribe;

import edu.fjbatresv.android.socialphotomap.libs.base.EventBus;
import edu.fjbatresv.android.socialphotomap.register.events.RegisterEvent;
import edu.fjbatresv.android.socialphotomap.register.ui.RegisterView;


/**
 * Created by javie on 10/06/2016.
 */
public class RegisterPresenterImplementation implements RegisterPresenter {
    private RegisterView view;
    private RegisterInteractor interactor;
    private EventBus bus;

    public RegisterPresenterImplementation(RegisterView view, RegisterInteractor interactor, EventBus bus) {
        this.view = view;
        this.interactor = interactor;
        this.bus = bus;
    }

    @Override
    public void onCreate() {
        bus.register(this);
    }

    @Override
    public void onDestoy() {
        bus.unRegister(this);
    }

    @Override
    public void addUser(String email, String password) {
        if(view != null){
            view.toggleInputs(false);
            view.toogleProgressBar(true);
            interactor.addUser(email, password);
        }
    }

    @Override
    @Subscribe
    public void onEventMainThred(RegisterEvent event) {
        switch (event.getEvent()){
            case RegisterEvent.singUpSuccess:
                view.succesSignUp();
                break;
            case RegisterEvent.singUpError:
                view.errorSignUp(event.getError());
                break;
        }
        view.toggleInputs(true);
        view.toogleProgressBar(false);
    }
}
