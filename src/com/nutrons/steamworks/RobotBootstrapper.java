package com.nutrons.steamworks;

import com.nutrons.framework.Robot;
import com.nutrons.framework.StreamManager;
import com.nutrons.framework.controllers.Talon;
import com.nutrons.framework.inputs.WpiXboxGamepad;

public class RobotBootstrapper extends Robot {
    private Talon intakeController;
    private WpiXboxGamepad controller;
    private Talon shooterMotor1;
    private Talon shooterMotor2;

    @Override
    protected void constructStreams(){
        this.intakeController = new Talon(RobotMap.INTAKE_MOTOR);
        this.shooterMotor1 = new Talon(RobotMap.SHOOTER_MOTOR_1);
        this.shooterMotor2 = new Talon(RobotMap.SHOOTER_MOTOR_2, this.shooterMotor1);
        this.controller = new WpiXboxGamepad(0);
    this.enabledStream();
    }

    @Override
    protected StreamManager provideStreamManager() {
       StreamManager sm = new StreamManager(this);
       sm.registerSubsystem( new Shooter(shooterMotor1));
       sm.registerSubsystem( new Feeder(intakeController));
       return sm;
    }
}
