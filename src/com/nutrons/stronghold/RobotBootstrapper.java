package com.nutrons.stronghold;

import com.nutrons.framework.Robot;
import com.nutrons.framework.StreamManager;
import com.nutrons.framework.consumers.FollowerTalon;
import com.nutrons.framework.consumers.Talon;
import com.nutrons.framework.producers.Serial;
import com.nutrons.framework.producers.WpiGamepad;
import com.nutrons.framework.producers.WpiXboxGamepad;

public class RobotBootstrapper extends Robot {
  private Talon driveLeftA;
  private Talon driveLeftB;
  private Talon driveRightA;
  private Talon driveRightB;
  private Talon hoodA;
  private Talon hoodB;
  private WpiGamepad driverPad;
  private Serial serial;
  private Vision vision;

  @Override
  protected void constructStreams() {
    this.driveLeftA = new Talon(RobotMap.LEFT_DRIVE_MOTOR_A);
    this.driveLeftB = new FollowerTalon(RobotMap.LEFT_DRIVE_MOTOR_B,
        RobotMap.LEFT_DRIVE_MOTOR_A);
    this.driveRightA = new Talon(RobotMap.RIGHT_DRIVE_MOTOR_A);
    this.driveRightB = new FollowerTalon(RobotMap.RIGHT_DRIVE_MOTOR_B,
        RobotMap.RIGHT_DRIVE_MOTOR_A);
    this.driverPad = new WpiXboxGamepad(0);
    this.hoodA = new Talon(RobotMap.HOOD_MOTOR_A);
    this.hoodB = new Talon(RobotMap.HOOD_MOTOR_B);
    this.serial = new Serial(20, 10, '\n');
    this.vision = new Vision(serial.dataStream());
  }

  @Override
  protected StreamManager provideStreamManager() {
    StreamManager sm = new StreamManager(this);
    sm.registerSubsystem(new Drivetrain(driverPad.joy1Y(), driverPad.joy2Y(),
        driveLeftA, driveRightA));
    sm.registerSubsystem(new Turret(vision.getAngle(), hoodA, hoodB));
    return sm;
  }
}