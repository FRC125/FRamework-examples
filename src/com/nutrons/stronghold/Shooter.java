package com.nutrons.stronghold;

import com.nutrons.framework.Subsystem;
import com.nutrons.framework.controllers.ControllerEvent;
import com.nutrons.framework.controllers.LoopSpeedController;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;


public class Shooter implements Subsystem {
    Consumer<ControllerEvent> shooter;
    Flowable<LoopSpeedController> fire;

    Shooter(Flowable<Boolean> values, Consumer<ControllerEvent> shooter){
        this.shooter = shooter;

        if(values.blockingLatest().iterator().next()){

        }
    }

    @Override
    public void registerSubscriptions() {

    }
}
