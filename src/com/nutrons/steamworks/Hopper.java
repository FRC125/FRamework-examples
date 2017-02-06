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
    private final Flowable<ControllerEvent> runHopper;
    private final Consumer<ControllerEvent> hopperController;
    private static double spinsPower = 0.9;

    /**
     * HopperController
     * Tune RunAtPowerEvent
     */
    public Hopper(Consumer<ControllerEvent> HopperController) {
        this.runHopper = Flowable.just(new RunAtPowerEvent(spinsPower));
        this.hopperController = HopperController;

    }


    @Override
    public void registerSubscriptions() {
        runHopper.subscribe(hopperController);
    }
}