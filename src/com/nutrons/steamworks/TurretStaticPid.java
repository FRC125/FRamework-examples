package com.nutrons.steamworks;

import com.nutrons.framework.Subsystem;
import com.nutrons.framework.controllers.*;
//import com.nutrons.framework.controllers.FollowerTalon;
import com.nutrons.framework.subsystems.Settings;
import com.nutrons.framework.util.FlowOperators;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import io.reactivex.Flowable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

import java.util.concurrent.TimeUnit;

import static com.nutrons.framework.util.FlowOperators.toFlow;

public class TurretStaticPid implements Subsystem {
    private final Flowable<Double> angle;
    private final Consumer<ControllerEvent> hoodMaster;
    //private final Flowable<ControllerEvent> moveTurretMotor;
    public static double test;
    public Consumer<Double> jank;
    int absolutePosition;

    public static Flowable<Double> arcLength;
    private static final double HOOD_RADIUS_IN = 10.5;

    public TurretStaticPid(Flowable<Double> angle, Consumer<ControllerEvent> master) {

        this.angle = angle;
        this.hoodMaster = master;
        arcLength = this.angle.map(x -> (x * Math.PI * (2 * HOOD_RADIUS_IN)) / 360);
        //Calculates arc length turret needs to travel to reach a certain angle,
        //Finds ratio of angle to 360 and creates a proportion to ratio with arc length to full circumference
    }

    @Override
    public void registerSubscriptions() {
        //arcLength.map((x) -> new RunAtPowerEvent(0.1)).subscribe(hoodMaster);
        Flowable<ControllerEvent> source = toFlow(() -> new LoopPropertiesEvent(10000, 0.05, 0.0, 0.0, 0.0));
               source.mergeWith(toFlow(() -> new LoopModeEvent(ControllerMode.LOOP_POSITION))).subscribe(hoodMaster);
        RobotBootstrapper.hoodMaster.feedback().map(x -> x.error()).subscribe(System.out::println);
        Flowable.timer(5, TimeUnit.SECONDS).map(x -> (Action) () -> RobotBootstrapper.hmt.setPosition(0)).subscribe(Action::run);
    }
}
