package com.nutrons.stronghold;

import com.nutrons.framework.Subsystem;
import com.nutrons.framework.controllers.ControllerEvent;
import com.nutrons.framework.controllers.FollowerTalon;
import com.nutrons.framework.controllers.LoopPropertiesEvent;
import com.nutrons.framework.subsystems.Settings;
import com.nutrons.framework.util.FlowOperators;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

public class Turret implements Subsystem{
    private final Flowable<Double> angle;
    private final Consumer<ControllerEvent> hoodMaster;
    private final FollowerTalon hoodSlave;
    private final Flowable<ControllerEvent> PIDControllerA;
    private Settings PIDSettings;
    private final double initial = 0.0; //If the PID settings doesn't emit any values, then it defaults to 0.0

    private Flowable<Double> arcLength;
    private static final double HOOD_RADIUS_IN = 10.5;

    Turret(Flowable<Double> angle, Consumer<ControllerEvent> master, FollowerTalon slave){
        this.angle = angle;
        this.hoodMaster = master;
        this.hoodSlave = slave;
        arcLength = this.angle.map( x -> (x*2*Math.PI*HOOD_RADIUS_IN) / (360) );

        this.PIDControllerA = FlowOperators.deadzone(arcLength).map((x) -> new LoopPropertiesEvent(x,
                PIDSettings.getProperty("P").blockingMostRecent(initial).iterator().next(),
                PIDSettings.getProperty("I").blockingMostRecent(initial).iterator().next(),
                PIDSettings.getProperty("D").blockingMostRecent(initial).iterator().next(),
                0.0));
    }

    @Override
    public void registerSubscriptions() {
        PIDControllerA.subscribe(hoodMaster);
    }
}
