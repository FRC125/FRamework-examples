package com.nutrons.stronghold;

import com.nutrons.framework.Subsystem;
import com.nutrons.framework.consumers.ControllerEvent;
import com.nutrons.framework.consumers.RunAtPowerEvent;
import com.nutrons.framework.factories.InputManager;
import com.nutrons.framework.factories.OutputFactory;
import com.nutrons.framework.factories.OutputManager;
import io.reactivex.Flowable;
import static java.lang.Math.abs;

public class Drivetrain implements Subsystem {

  private final Flowable<ControllerEvent> y1;
  private final Flowable<ControllerEvent> y2;

  public Drivetrain() {
    this.y1 = deadzone(InputManager.factory().controllerY(0)).map((x) -> new RunAtPowerEvent(x));
    this.y2 = deadzone(InputManager.factory().controllerY2(0)).map((x) -> new RunAtPowerEvent(-x));
  }

  @Override
  public void registerSubscriptions() {
    OutputFactory out = OutputManager.factory();
    y1.subscribe(out.motor(RobotMap.LEFT_DRIVE_MOTOR_A));
    y2.subscribe(out.motor(RobotMap.RIGHT_DRIVE_MOTOR_A));
  }

  private Flowable<Double> deadzone(Flowable<Double> input) {
    return input.map((x) -> abs(x) < 0.2 ? 0.0 : x);
  }
}
