package com.nutrons.steamworks;

import com.ctre.CANTalon;
import com.nutrons.framework.Robot;
import com.nutrons.framework.StreamManager;
import com.nutrons.framework.controllers.Talon;
import com.nutrons.framework.inputs.WpiGamepad;
import com.nutrons.framework.inputs.WpiXboxGamepad;

import com.nutrons.steamworks.Subsystems.Drivetrain;

public class RobotBootstrapper extends Robot {
    private Talon leftLeader;
    private Talon leftFollower;
    private Talon rightLeader;
    private Talon rightFollower;
    private WpiGamepad driverPad;

    @Override
    protected void constructStreams() {
        // Motors
        this.leftLeader = new Talon(RobotMap.FRONT_LEFT);
        this.leftFollower = new Talon(RobotMap.BACK_LEFT, this.leftLeader);
        this.rightLeader = new Talon(RobotMap.BACK_RIGHT);
        this.rightFollower = new Talon(RobotMap.FRONT_RIGHT, this.rightLeader);
        this.driverPad = new WpiXboxGamepad(RobotMap.DRIVER_PAD);

    }

    @Override
    protected StreamManager provideStreamManager() {
        StreamManager sm = new StreamManager(this);
        sm.registerSubsystem(new Drivetrain(driverPad.joy1Y(), driverPad.joy2X(),
                leftLeader, rightLeader));
        return sm;
    }
}

