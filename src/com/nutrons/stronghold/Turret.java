package com.nutrons.stronghold;

import com.nutrons.framework.Subsystem;
import com.nutrons.framework.consumers.ControllerEvent;
import com.nutrons.framework.consumers.LoopPropertiesEvent;
import com.nutrons.framework.util.FlowOperators;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

import static java.lang.Math.abs;

public class Turret implements Subsystem{
    private final Flowable<Double> angle;
    private final Consumer<ControllerEvent> hoodA;
    private final Consumer<ControllerEvent> hoodB;
    private final Flowable<ControllerEvent> PIDControllerA;
    private final Flowable<ControllerEvent> PIDControllerB;
    private Flowable<Double> arcLength;
    private static final double HOOD_RADIUS_IN = 10.5;


    private double P = 0.0;
    private double I = 0.0;
    private double D = 0.0;
    private double F = 0.0;

    Turret(Flowable<Double> angle, Consumer<ControllerEvent> leftDrive, Consumer<ControllerEvent> rightDrive){
        this.angle = angle;
        this.hoodA = leftDrive;
        this.hoodB = rightDrive;
        arcLength = this.angle.map(x -> x / (360*2*Math.PI*HOOD_RADIUS_IN));

        this.PIDControllerA = FlowOperators.deadzone(arcLength).map((x) -> new LoopPropertiesEvent(x, P, I, D, F));
        this.PIDControllerB = FlowOperators.deadzone(arcLength).map((x) -> new LoopPropertiesEvent(x, P, I, D, F));
    }

    @Override
    public void registerSubscriptions() {
        PIDControllerA.subscribe(hoodA);
        PIDControllerB.subscribe(hoodB);
    }
}
