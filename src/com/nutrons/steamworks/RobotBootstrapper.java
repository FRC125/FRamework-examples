package com.nutrons.steamworks;

import com.nutrons.framework.Robot;
import com.nutrons.framework.StreamManager;
import com.nutrons.framework.controllers.Talon;
import com.nutrons.framework.inputs.WpiXboxGamepad;

public class RobotBootstrapper extends Robot {
    private Talon driveRollers;
    private WpiXboxGamepad controller;
    @Override
    protected void constructStreams(){
        this.driveRollers = new Talon(RobotMap.INTAKE_MOTOR);
        this.controller = new WpiXboxGamepad(0);
    }

    @Override
    protected StreamManager provideStreamManager() {
       StreamManager sm = new StreamManager(this);
       sm.registerSubsystem(new Intake(controller.joy2X(), driveRollers));
       return sm;
    }
}
