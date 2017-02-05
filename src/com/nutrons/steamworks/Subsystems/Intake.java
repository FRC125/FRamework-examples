package com.nutrons.steamworks.Subsystems;


import com.nutrons.framework.Subsystem;
import com.nutrons.framework.controllers.ControllerEvent;
import com.nutrons.framework.controllers.RunAtPowerEvent;
import com.nutrons.framework.controllers.Talon;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import org.reactivestreams.Subscriber;

import static java.lang.Math.abs;

public class Intake implements Subsystem {
    private final Flowable<ControllerEvent> intakeSpeed;
    private final Consumer<ControllerEvent> driveRollers;

    public Intake(Flowable<Double> intakeSpeed, Consumer<ControllerEvent> driveRollers) {
        this.intakeSpeed = deadzone(intakeSpeed).map(RunAtPowerEvent::new );
        this.driveRollers = driveRollers;
    }

    @Override
    public void registerSubscriptions() {
        intakeSpeed.subscribe(driveRollers);
    }

    private Flowable<Double> deadzone(Flowable<Double> input){
        return input.map((x) -> abs(x) < 0.2 ? 0.0 : x);
    }

}
