package com.nutrons.steamworks;

import com.nutrons.framework.Subsystem;
import com.nutrons.framework.controllers.ControllerEvent;
import com.nutrons.framework.controllers.LoopPropertiesEvent;
import com.nutrons.framework.controllers.RunAtPowerEvent;
import com.nutrons.framework.controllers.Talon;
import io.reactivex.Flowable;

import java.util.function.Consumer;

/**
 * Created by Brian on 2/1/2017.
 */
public class Shooter implements Subsystem {
    private final Flowable<ControllerEvent> runShooter;
    private final Talon shooterMotor1;
    private final Talon shooterMotor2;

    public Shooter(Flowable<ControllerEvent> runShooter,
                   Talon shooterMotor1,
                   Talon shooterMotor2) {

        this.runShooter = Flowable.just(new LoopPropertiesEvent (3250.0, 0.05, 0.0, 0.33, 0.029));
        this.shooterMotor1 = shooterMotor1;
        this.shooterMotor2 = shooterMotor2;
    }


    @Override
    public void registerSubscriptions() {
        runShooter.subscribe(shooterMotor1);
        runShooter.subscribe(shooterMotor2);
    }
}
