package com.nutrons.steamworks.Subsystems;

import static io.reactivex.Flowable.combineLatest;
import static java.lang.Math.*;

import com.nutrons.framework.Subsystem;
import com.nutrons.framework.controllers.ControllerEvent;
import com.nutrons.framework.controllers.PIDGyro;
import com.nutrons.framework.controllers.RunAtPowerEvent;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

public class

Drivetrain implements Subsystem {
    private final Flowable<Double> throttle;
    private final Flowable<Double> yaw;
    private final Consumer<ControllerEvent> leftDrive;
    private final Consumer<ControllerEvent> rightDrive;
    private double coeff = 0.6;
    private final PIDGyro headingGyro;


    public Drivetrain(Flowable<Double> throttle, Flowable<Double> yaw,
               Consumer<ControllerEvent> leftDrive, Consumer<ControllerEvent> rightDrive, Flowable<Double> headingGyro) {

        this.throttle = deadzone(throttle);
        this.yaw = deadzone(yaw);
        this.leftDrive = leftDrive;
        this.rightDrive = rightDrive;
        this.headingGyro = new PIDGyro(0.0, 0.0, 0.0, 0.0);
    }

    private Flowable<Double> deadzone(Flowable<Double> input) {
        return input.map((x) -> abs(x) < 0.2 ? 0.0 : x);
    }

    @Override
    public void registerSubscriptions() {
        combineLatest(throttle, yaw,(x, y) -> x + y).map(x -> x * coeff).map(RunAtPowerEvent::new).subscribe(leftDrive);
        combineLatest(throttle, yaw,(x, y) -> x - y).map(x -> x * coeff).map(RunAtPowerEvent::new).subscribe(rightDrive);
        headingGyro.
    }
}
