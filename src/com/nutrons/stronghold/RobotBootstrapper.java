package com.nutrons.stronghold;

import com.nutrons.framework.Robot;
import com.nutrons.framework.StreamManager;
import com.nutrons.framework.controllers.FollowerTalon;
import com.nutrons.framework.controllers.Talon;
import com.nutrons.framework.producers.Serial;
import com.nutrons.framework.inputs.WpiGamepad;
import com.nutrons.framework.inputs.WpiXboxGamepad;

public class RobotBootstrapper extends Robot {
  private Talon driveLeftA;
  private Talon driveLeftB;
  private Talon driveRightA;
  private Talon driveRightB;

  private Talon hoodMaster;
  private FollowerTalon hoodSlave;

  private Talon shooter;

  private WpiGamepad driverPad;
  private WpiGamepad.WpiButton fireButton;
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
    this.hoodMaster = new Talon(RobotMap.HOOD_MOTOR_A);
    this.hoodSlave = new FollowerTalon(RobotMap.HOOD_MOTOR_B, RobotMap.HOOD_MOTOR_A);
    this.serial = new Serial(20, 10, '\n');
    this.vision = new Vision(serial.dataStream());
    this.shooter = new Talon(RobotMap.SHOOTER);
    this.fireButton = driverPad.new WpiButton(driverPad, RobotMap.SHOOT_BUTTON);
  }

  @Override
  protected StreamManager provideStreamManager() {
    StreamManager sm = new StreamManager(this);
    sm.registerSubsystem(new Drivetrain(driverPad.joy1Y(), driverPad.joy2Y(),
        driveLeftA.output(), driveRightA.output()));
    sm.registerSubsystem(new Turret(vision.getAngle(), hoodMaster.output(), hoodSlave));
    sm.registerSubsystem(new Shooter(fireButton.values(), shooter.output()));
    return sm;
  }
}