package com.nutrons.steamworks;

import com.nutrons.FRamework.util.CompMode;
import com.nutrons.FRamework.StreamManager;
import io.reactivex.Flowable;

public class SubsystemManager extends StreamManager {

  public SubsystemManager(Flowable<Boolean> enabled, Flowable<CompMode> mode) {
    super(enabled, mode);
    super.registerSubsystem(new Drivetrain());
  }

}
