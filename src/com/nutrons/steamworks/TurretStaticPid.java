package com.nutrons.steamworks;

import com.nutrons.framework.Subsystem;
import com.nutrons.framework.controllers.ControllerEvent;
//import com.nutrons.framework.controllers.FollowerTalon;
import com.nutrons.framework.controllers.LoopPropertiesEvent;
import com.nutrons.framework.subsystems.Settings;
import com.nutrons.framework.util.FlowOperators;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

public class TurretStaticPid implements Subsystem {
    private final Flowable<Double> angle;
    private final Consumer<ControllerEvent> hoodMaster;
    private final Flowable<ControllerEvent> moveTurretMotor;
    public static double setpoint;
    int absolutePosition;

    public static Flowable<Double> arcLength;
    private static final double HOOD_RADIUS_IN = 10.5;

    public TurretStaticPid(Flowable<Double> angle, Consumer<ControllerEvent> master) {
        this.angle = angle;
        this.hoodMaster = master;
        arcLength = this.angle.map(x -> (x * Math.PI * (2 * HOOD_RADIUS_IN)) / 360);
        this.setpoint = FlowOperators.getLastValue(arcLength);
        this.moveTurretMotor = Flowable.just(new LoopPropertiesEvent(setpoint, 0.1, 0.0, 0.0, 0.0));

        //Calculates arc length turret needs to travel to reach a certain angle,
        //Finds ratio of angle to 360 and creates a proportion to ratio with arc length to full circumference


    }

    @Override
    public void registerSubscriptions() {
        moveTurretMotor.subscribe(hoodMaster);
    }
}
