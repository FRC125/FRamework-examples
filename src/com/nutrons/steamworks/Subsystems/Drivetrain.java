package com.nutrons.steamworks.Subsystems;

import static java.lang.Math.*;

import com.nutrons.framework.Subsystem;
import com.nutrons.framework.controllers.ControllerEvent;
import com.nutrons.framework.controllers.RunAtPowerEvent;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

public class

Drivetrain implements Subsystem {
    private final Flowable<ControllerEvent> leftJoyY;
    private final Flowable<ControllerEvent> rightJoyX;
    private final Consumer<ControllerEvent> leftDrive;
    private final Consumer<ControllerEvent> rightDrive;
    private double x;
    private double speed;
    private double wheel;

    public Drivetrain(Flowable<Double> leftJoyY, Flowable<Double> rightJoYX,
               Consumer<ControllerEvent> leftDrive, Consumer<ControllerEvent> rightDrive) {
        deadzone(leftJoyY).subscribe(x -> speed = x);
        deadzone(rightJoYX).subscribe(x -> wheel  = x);
        this.leftJoyY = deadzone(leftJoyY).map((x) -> new RunAtPowerEvent(x));
        this.rightJoyX = deadzone(rightJoYX).map((x) -> new RunAtPowerEvent(-x));
        this.leftDrive = leftDrive;
        this.rightDrive = rightDrive;
    }


    public void drive(double leftPower, double rightPower) {
        try {
            leftDrive.accept(new RunAtPowerEvent(leftPower));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            rightDrive.accept(new RunAtPowerEvent(rightPower));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void driveCheesy() {
        double coeff = 1.0;
        double invert = 1.0;

        wheel = wheel * 0.6;
        drive(((speed * invert) - (wheel)) * coeff, ((speed * invert) + (wheel)) * coeff);
    }

    private Flowable<Double> deadzone(Flowable<Double> input) {
        return input.map((x) -> abs(x) < 0.2 ? 0.0 : x);
    }


    @Override
    public void registerSubscriptions() {

    }
}
