package com.nutrons.steamworks;

import com.ctre.CANTalon;
import com.nutrons.FRamework.OutputFactory;
import com.nutrons.FRamework.Robot;
import com.nutrons.FRamework.StreamManager;
import com.nutrons.FRamework.consumers.LoopControllerEvent;
import com.nutrons.FRamework.consumers.Talon;
import io.reactivex.functions.Consumer;
import java.util.HashMap;

public class RobotBootstrapper extends Robot {

  public RobotBootstrapper() {
    HashMap<Integer, Consumer<LoopControllerEvent>> motors = new HashMap<>();
    motors.put(RobotMap.LEFT_DRIVE_MOTOR_A, new Talon(new CANTalon(RobotMap.LEFT_DRIVE_MOTOR_A)));
    motors.put(RobotMap.LEFT_DRIVE_MOTOR_B, new Talon(new CANTalon(RobotMap.LEFT_DRIVE_MOTOR_B)));
    motors.put(RobotMap.RIGHT_DRIVE_MOTOR_A, new Talon(new CANTalon(RobotMap.RIGHT_DRIVE_MOTOR_A)));
    motors.put(RobotMap.RIGHT_DRIVE_MOTOR_B, new Talon(new CANTalon(RobotMap.RIGHT_DRIVE_MOTOR_B)));
    OutputFactory.instance().setControllers(motors);
}

  @Override
  protected StreamManager createStreamManager() {
    return new SubsystemManager(enabledStream(), competitionStream());
  }
}
