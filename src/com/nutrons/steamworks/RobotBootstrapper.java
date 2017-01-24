package com.nutrons.steamworks;

import com.nutrons.framework.Robot;
import com.nutrons.framework.StreamManager;
import com.nutrons.framework.consumers.ControllerEvent;
import com.nutrons.framework.consumers.Talon;
import com.nutrons.framework.factories.OutputManager;
import io.reactivex.functions.Consumer;
import java.util.HashMap;

public class RobotBootstrapper extends Robot {

  public RobotBootstrapper() {
    HashMap<Integer, Consumer<ControllerEvent>> motors = new HashMap<>();
    motors.put(RobotMap.LEFT_DRIVE_MOTOR_A, new Talon(RobotMap.LEFT_DRIVE_MOTOR_A));
    motors.put(RobotMap.LEFT_DRIVE_MOTOR_B, new Talon(RobotMap.LEFT_DRIVE_MOTOR_B));
    motors.put(RobotMap.RIGHT_DRIVE_MOTOR_A, new Talon(RobotMap.RIGHT_DRIVE_MOTOR_A));
    motors.put(RobotMap.RIGHT_DRIVE_MOTOR_B, new Talon(RobotMap.RIGHT_DRIVE_MOTOR_B));
    OutputManager.factory().setControllers(motors);
}

  @Override
  protected StreamManager provideStreamManager() {
    StreamManager sm =  new StreamManager(this);
    sm.registerSubsystem(new Drivetrain());
    return sm;
  }
}
