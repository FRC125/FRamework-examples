package com.nutrons.stronghold;

import com.nutrons.framework.Robot;
import com.nutrons.framework.StreamManager;
import com.nutrons.framework.controllers.Talon;
import com.nutrons.framework.inputs.WpiGamepad;
import com.nutrons.framework.inputs.WpiXboxGamepad;

public class RobotBootstrapper extends Robot {
  private Talon driveLeftA;
  private Talon driveLeftB;
  private Talon driveRightA;
  private Talon driveRightB;
  private WpiGamepad driverPad;

  @Override
  protected void constructStreams() {
    this.driveLeftA = new Talon(RobotMap.LEFT_DRIVE_MOTOR_A);
    this.driveLeftB = new Talon(RobotMap.LEFT_DRIVE_MOTOR_B,
        this.driveLeftA);
    this.driveRightA = new Talon(RobotMap.RIGHT_DRIVE_MOTOR_A);
    this.driveRightB = new Talon(RobotMap.RIGHT_DRIVE_MOTOR_B,
        this.driveRightA);
    this.driverPad = new WpiXboxGamepad(0);
  }

  @Override
  protected StreamManager provideStreamManager() {
    StreamManager sm = new StreamManager(this);
    sm.registerSubsystem(new Drivetrain(driverPad.joy1Y(), driverPad.joy2Y(),
        driveLeftA, driveRightA));
    return sm;
  }
}
