package com.nutrons.steamworks;

import com.nutrons.framework.Subsystem;
import com.nutrons.framework.controllers.ControllerEvent;
import com.nutrons.framework.controllers.RunAtPowerEvent;
import com.nutrons.framework.controllers.Talon;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import org.reactivestreams.Subscriber;


public class Hopper implements Subsystem {
    private final static double spinsPower = 0.9;
    private final Flowable<ControllerEvent> runHopper;
    private final Consumer<ControllerEvent> hopperController;

    /**
     * @param hopperController passes in RunAtPowerEvent
     * @todo Tune spinsPower if needed
     */
    public Hopper(Consumer<ControllerEvent> hopperController) {
        this.runHopper = Flowable.just(new RunAtPowerEvent(spinsPower));
        this.hopperController = hopperController;

    }


    @Override
    public void registerSubscriptions() {
        runHopper.subscribe(hopperController);
    }
}
