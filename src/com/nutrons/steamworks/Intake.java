package com.nutrons.steamworks;


import com.nutrons.framework.Subsystem;
import com.nutrons.framework.controllers.ControllerEvent;
import com.nutrons.framework.controllers.LoopPropertiesEvent;
import com.nutrons.framework.controllers.RunAtPowerEvent;
import com.nutrons.framework.controllers.Talon;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import org.reactivestreams.Subscriber;


import static java.lang.Math.abs;

/**
 * Created by Brian on 1/31/2017.
 */
class Intake implements Subsystem {
    private final Flowable<ControllerEvent> intakeSpeed;
    private final Consumer<ControllerEvent> driveRollers;

    Intake(Flowable<Double> intakeSpeed, Consumer<ControllerEvent> driveRollers) {
        this.intakeSpeed = Flowable.just(new RunAtPowerEvent(1.0));
        this.driveRollers = driveRollers;
    }

    @Override
    public void registerSubscriptions() {
        intakeSpeed.subscribe(driveRollers);
    }

}
