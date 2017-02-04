package com.nutrons.steamworks;

import com.nutrons.framework.Subsystem;
import com.nutrons.framework.controllers.ControllerEvent;
//import com.nutrons.framework.controllers.FollowerTalon;
import com.nutrons.framework.controllers.LoopPropertiesEvent;
import com.nutrons.framework.subsystems.Settings;
import com.nutrons.framework.util.FlowOperators;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

public class Turret implements Subsystem {
    private final Flowable<Double> angle;
    private final Consumer<ControllerEvent> hoodMaster;
    //private final FollowerTalon hoodSlave;
    private final Flowable<ControllerEvent> PIDControllerMotors;

    private final Consumer<ControllerEvent> shooter;
    private Flowable<ControllerEvent> PIDControllerShooter;
    private Flowable<Double> MOTOR_POWER;

    private Settings PIDSettings; //If the PID settings doesn't emit any values, then it defaults to 0.0
    private final double initial = 0.0;

    private Flowable<Double> arcLength;
    private static final double HOOD_RADIUS_IN = 10.5;

    Turret(Flowable<Double> angle,
           Consumer<ControllerEvent> master,
           //FollowerTalon slave,
           Flowable<Boolean> triggerValues,
           Consumer<ControllerEvent> shooter) {
        this.angle = angle;
        this.hoodMaster = master;
        //this.hoodSlave = slave;

        //Calculates arc length turret needs to travel to reach a certain angle,
        //Finds ratio of angle to 360 and creates a proportion to ratio with arc length to full circumference
        arcLength = this.angle.map(x -> (x * Math.PI * (2 * HOOD_RADIUS_IN)) / 360);

        this.PIDControllerMotors = FlowOperators
                .deadband(arcLength)
                .map((x) -> new LoopPropertiesEvent(
                        x,
                        FlowOperators.getLastValue(PIDSettings.getProperty("P_Turret")),
                        FlowOperators.getLastValue(PIDSettings.getProperty("I_Turret")),
                        FlowOperators.getLastValue(PIDSettings.getProperty("D_Turret")),
                        0.0));

        this.shooter = shooter;
        MOTOR_POWER = PIDSettings.getProperty("motorPower");

        if (FlowOperators.getLastValue(triggerValues)) {
            this.PIDControllerShooter = FlowOperators
                    .deadband(MOTOR_POWER)
                    .map((x) -> new LoopPropertiesEvent(
                            x,
                            FlowOperators.getLastValue(PIDSettings.getProperty("P_Shooter")),
                            FlowOperators.getLastValue(PIDSettings.getProperty("I_Shooter")),
                            FlowOperators.getLastValue(PIDSettings.getProperty("D_Shooter")),
                            0.0));
        }
    }

    @Override
    public void registerSubscriptions() {
        PIDControllerMotors.subscribe(hoodMaster);
        PIDControllerShooter.subscribe(shooter);
    }
}