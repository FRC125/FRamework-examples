package com.nutrons.steamworks;

import com.nutrons.framework.Robot;
import com.nutrons.framework.StreamManager;
import com.nutrons.framework.consumers.LoopSpeedController;
import com.nutrons.framework.consumers.Talon;
import com.nutrons.framework.factories.OutputManager;
import java.util.ArrayList;
import java.util.List;

public class RobotBootstrapper extends Robot {

  public RobotBootstrapper() {
    List<LoopSpeedController> motors = new ArrayList<>();
    motors.add(new Talon(RobotMap.LEFT_DRIVE_MOTOR_A));
    motors.add(new Talon(RobotMap.LEFT_DRIVE_MOTOR_B));
    motors.add(new Talon(RobotMap.RIGHT_DRIVE_MOTOR_A));
    motors.add(new Talon(RobotMap.RIGHT_DRIVE_MOTOR_B));
    OutputManager.factory().setControllers(motors);
  }

  @Override
  protected StreamManager provideStreamManager() {
    StreamManager sm = new StreamManager(this);
    sm.registerSubsystem(new Drivetrain());
    return sm;
  }
}
