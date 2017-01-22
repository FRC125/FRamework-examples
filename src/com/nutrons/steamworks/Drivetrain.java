package com.nutrons.steamworks;

import com.nutrons.FRamework.InputFactory;
import com.nutrons.FRamework.OutputFactory;
import com.nutrons.FRamework.Subsystem;
import com.nutrons.FRamework.consumers.LoopControllerEvent;
import com.nutrons.FRamework.consumers.LoopFollowEvent;
import com.nutrons.FRamework.consumers.RunAtPowerEvent;
import io.reactivex.Flowable;

public class Drivetrain implements Subsystem {

  private final Flowable<LoopControllerEvent> y1;
  private final Flowable<LoopControllerEvent> y2;

  public Drivetrain() {
    this.y1 = deadzone(InputFactory.instance().joystickY(0)).map((x) -> new RunAtPowerEvent(x));
    this.y2 = deadzone(InputFactory.instance().joystickY(1)).map((x) -> new RunAtPowerEvent(x));
  }

  @Override
  public void registerSubscriptions() {
    OutputFactory out = OutputFactory.instance();
    Flowable.just(new LoopFollowEvent(RobotMap.LEFT_DRIVE_MOTOR_A))
        .subscribe(out.motor(RobotMap.LEFT_DRIVE_MOTOR_B));
    Flowable.just(new LoopFollowEvent(RobotMap.RIGHT_DRIVE_MOTOR_A))
        .subscribe(out.motor(RobotMap.RIGHT_DRIVE_MOTOR_B));
    y1.subscribe(out.motor(RobotMap.LEFT_DRIVE_MOTOR_A));
    y2.subscribe(out.motor(RobotMap.RIGHT_DRIVE_MOTOR_A));
  }

  private Flowable<Double> deadzone(Flowable<Double> input) {
    return input.map((x) -> x < 0.1 ? 0.0 : x);
  }
}
