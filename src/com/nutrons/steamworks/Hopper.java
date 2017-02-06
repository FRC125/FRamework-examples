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
<<<<<<< HEAD
    private static double spinsPower = 0.9;

=======
    private final double hopperPower = 0.9;
>>>>>>> 69a8418f4b31c5ee358a59730556336f64f888ce
    /**
     * HopperController
     * Tune RunAtPowerEvent
     */
    public Hopper(Consumer<ControllerEvent> HopperController) {
<<<<<<< HEAD
        this.runHopper = Flowable.just(new RunAtPowerEvent(spinsPower));
=======
        this.runHopper = Flowable.just(new RunAtPowerEvent(hopperPower));
>>>>>>> 69a8418f4b31c5ee358a59730556336f64f888ce
        this.hopperController = HopperController;

    }


    @Override
    public void registerSubscriptions() {
        runHopper.subscribe(hopperController);
    }
}
