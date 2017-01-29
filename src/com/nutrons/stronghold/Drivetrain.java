package com.nutrons.stronghold;

import static java.lang.Math.abs;

import com.nutrons.framework.Subsystem;
import com.nutrons.framework.consumers.ControllerEvent;
import com.nutrons.framework.consumers.RunAtPowerEvent;
import com.nutrons.framework.util.FlowOperators;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.PIDCommand;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

class Drivetrain implements Subsystem {
  private final Flowable<ControllerEvent> y1;
  private final Flowable<ControllerEvent> y2;
  private final Consumer<ControllerEvent> leftDrive;
  private final Consumer<ControllerEvent> rightDrive;

  Drivetrain(Flowable<Double> y1, Flowable<Double> y2,
                    Consumer<ControllerEvent> leftDrive, Consumer<ControllerEvent> rightDrive) {
    this.y1 = FlowOperators.deadzone(y1).map((x) -> new RunAtPowerEvent(x));
    this.y2 = FlowOperators.deadzone(y2).map((x) -> new RunAtPowerEvent(-x));
    this.leftDrive = leftDrive;
    this.rightDrive = rightDrive;
  }

  @Override
  public void registerSubscriptions() {
    y1.subscribe(leftDrive);
    y2.subscribe(rightDrive);
  }
}
