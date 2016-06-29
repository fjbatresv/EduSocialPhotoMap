package edu.fjbatresv.android.socialphotomap.libs;


import edu.fjbatresv.android.socialphotomap.libs.base.EventBus;

/**
 * Created by javie on 14/06/2016.
 */
public class GreenRobotEventBus implements EventBus {
    private org.greenrobot.eventbus.EventBus bus;

    public GreenRobotEventBus(org.greenrobot.eventbus.EventBus bus) {
        this.bus = bus;
    }

    @Override
    public void register(Object suscriber) {
        bus.register(suscriber);
    }

    @Override
    public void unRegister(Object suscriber) {
        bus.unregister(suscriber);
    }

    @Override
    public void post(Object event) {
        bus.post(event) ;
    }
}
