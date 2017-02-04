package com.nutrons.steamworks;

import com.nutrons.framework.Subsystem;
import com.nutrons.framework.controllers.ControllerEvent;
import com.nutrons.framework.controllers.RunAtPowerEvent;
import com.nutrons.framework.util.FlowOperators;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import com.nutrons.framework.controllers.Talon;

class Drivetrain implements Subsystem {
    private final Flowable<ControllerEvent> joystickStream1;
    private final Flowable<ControllerEvent> joystickStream2;
    private final Consumer<ControllerEvent> leftDrive;
    private final Consumer<ControllerEvent> rightDrive;

    Drivetrain(Flowable<Double> joystickStream1, Flowable<Double> joystickStream2,
               Consumer<ControllerEvent> leftDrive, Consumer<ControllerEvent> rightDrive) {
        this.joystickStream1 = FlowOperators.deadband(joystickStream1).map(RunAtPowerEvent::new);
        this.joystickStream2 = FlowOperators.deadband(joystickStream2).map(RunAtPowerEvent::new);
        this.leftDrive = leftDrive;
        this.rightDrive = rightDrive;
    }

    @Override
    public void registerSubscriptions() {
        joystickStream1.subscribe(leftDrive);
        joystickStream2.subscribe(rightDrive);
    }
}
