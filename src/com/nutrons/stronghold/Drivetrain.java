package com.nutrons.stronghold;

import static java.lang.Math.abs;

import com.nutrons.framework.Subsystem;
import com.nutrons.framework.controllers.ControllerEvent;
import com.nutrons.framework.controllers.RunAtPowerEvent;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

class Drivetrain implements Subsystem {
  private final Flowable<ControllerEvent> y1;
  private final Flowable<ControllerEvent> y2;
  private final Consumer<ControllerEvent> leftDrive;
  private final Consumer<ControllerEvent> rightDrive;

  Drivetrain(Flowable<Double> y1, Flowable<Double> y2,
                    Consumer<ControllerEvent> leftDrive, Consumer<ControllerEvent> rightDrive) {
    this.y1 = deadzone(y1).map((x) -> new RunAtPowerEvent(x));
    this.y2 = deadzone(y2).map((x) -> new RunAtPowerEvent(-x));
    this.leftDrive = leftDrive;
    this.rightDrive = rightDrive;
  }

  @Override
  public void registerSubscriptions() {
    y1.subscribe(leftDrive);
    y2.subscribe(rightDrive);
  }

  private Flowable<Double> deadzone(Flowable<Double> input) {
    return input.map((x) -> abs(x) < 0.2 ? 0.0 : x);
  }
}
