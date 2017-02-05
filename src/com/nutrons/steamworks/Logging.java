package com.nutrons.steamworks;

import com.nutrons.framework.Subsystem;
import com.nutrons.framework.inputs.Serial;
import com.nutrons.framework.subsystems.WpiSmartDashboard;
import com.nutrons.framework.util.FlowOperators;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

public class Logging implements Subsystem {
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
    private double setPoint;

    Logging(){
        this.serial = new Serial(20, 10);
        this.vision = new Vision(serial.getDataStream());

        this.sd = new WpiSmartDashboard();

        this.angles = vision.getAngle();
        this.distances = vision.getDistance();
        this.encoders = FlowOperators.toFlow(() -> RobotBootstrapper.hoodMaster.getPosition());
        this.arclengths = TurretStaticPid.arcLength;
        this.setPoint = TurretStaticPid.setpoint;

        this.angleLogger = sd.getTextField("angle");
        this.distanceLogger = sd.getTextField("distance");
        this.encoderLogger = sd.getTextField("encoder");
        this.setPointLogger = sd.getTextField("setpoint");
        this.arcLengthLogger = sd.getTextField("arclength");
    }
    @Override
    public void registerSubscriptions() {
        angles.subscribe(angleLogger);
        distances.subscribe(distanceLogger);
        encoders.subscribe(encoderLogger);
        arclengths.subscribe(arcLengthLogger);
    }
}
