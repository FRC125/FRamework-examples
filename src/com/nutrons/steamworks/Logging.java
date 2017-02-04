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
    private WpiSmartDashboard sd;
    private Flowable<Double> angles;
    private Flowable<Double> distances;

    Logging(){
        this.serial = new Serial(20, 10);
        this.vision = new Vision(serial.getDataStream());

        this.sd = new WpiSmartDashboard();

        this.angles = vision.getAngle();
        this.distances = vision.getDistance();

        this.angleLogger = sd.getProducer("angle");
        this.distanceLogger = sd.getProducer("distance");
        SmartDashboard.putNumber("angle", 10.0);
        SmartDashboard.putNumber("distance", FlowOperators.getLastValue(vision.getDistance()));
    }
    @Override
    public void registerSubscriptions() {
        angles.subscribe(angleLogger);
        distances.subscribe(distanceLogger);
    }
}
