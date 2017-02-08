package com.nutrons.steamworks;

import com.nutrons.framework.Subsystem;
import com.nutrons.framework.inputs.Serial;
import com.nutrons.framework.subsystems.WpiSmartDashboard;
import com.nutrons.framework.util.FlowOperators;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

import static com.nutrons.framework.util.FlowOperators.toFlow;

public class Logging implements Subsystem {
    private final Consumer<Double> error;
    private Serial serial;
    private Vision vision;
    private Consumer<Double> angleLogger;
    private Consumer<Double> distanceLogger;
    private Consumer<Double> setPointLogger;
    private Consumer<Double> arcLengthLogger;
    private Consumer<Double> encoderLogger;
    private WpiSmartDashboard sd;
    private Flowable<Double> angles;
    private Flowable<Double> distances;
    private Flowable<Double> encoders;
    private Flowable<Double> arclengths;
    private Flowable<Double> setPoint;

    Logging(){
        //this.serial = new Serial(20, 10);
        this.vision = new Vision(Flowable.never());

        this.sd = new WpiSmartDashboard();

        this.angles = vision.getAngle();
        this.distances = vision.getDistance();
        this.encoders = toFlow(() -> RobotBootstrapper.hoodMaster.getEncoderPosition());
        this.arclengths = TurretStaticPid.arcLength;
        this.setPoint = toFlow( () -> RobotBootstrapper.hmt.getSetpoint());

        this.angleLogger = sd.getTextField("angle");
        this.distanceLogger = sd.getTextField("distance");
        this.encoderLogger = sd.getTextField("encPosition");
        this.setPointLogger = sd.getTextField("setpoint");
        this.arcLengthLogger = sd.getTextField("arclength");
        this.error = sd.getTextField("error");
    }
    @Override
    public void registerSubscriptions() {
        angles.subscribe(angleLogger);
        distances.subscribe(distanceLogger);
        encoders.subscribe(encoderLogger);
        arclengths.subscribe(arcLengthLogger);
        setPoint.subscribe(setPointLogger);
        toFlow(() -> RobotBootstrapper.hmt.getError()).subscribe(error);
        toFlow(() -> RobotBootstrapper.hmt.getEncVelocity()).map(x -> x*1.0).subscribe(sd.getTextField("encVelocity"));
        toFlow(() -> RobotBootstrapper.hmt.getPosition()).subscribe(sd.getTextField("actual position"));
    }
}
