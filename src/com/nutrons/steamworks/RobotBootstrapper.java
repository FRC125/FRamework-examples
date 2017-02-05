package com.nutrons.steamworks;


import com.nutrons.framework.Robot;
import com.nutrons.framework.StreamManager;
import com.nutrons.framework.controllers.Talon;
import com.nutrons.framework.inputs.WpiGamepad;
import com.nutrons.framework.inputs.WpiXboxGamepad;
import com.nutrons.steamworks.Subsystems.Drivetrain;
import com.nutrons.steamworks.Subsystems.Intake;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class RobotBootstrapper extends Robot {
    private Talon leftLeader;
    private Talon leftFollower;
    private Talon rightLeader;
    private Talon rightFollower;
    private WpiGamepad driverPad;
    private Talon driveRollers;
    private WpiXboxGamepad operatorPad;
    private ADXRS450_Gyro headingGyro;


    @Override
    protected void constructStreams() {
        // Motors
        this.leftLeader = new Talon(RobotMap.FRONT_LEFT);
        this.leftFollower = new Talon(RobotMap.BACK_LEFT, this.leftLeader);
        this.rightLeader = new Talon(RobotMap.BACK_RIGHT);
        this.rightFollower = new Talon(RobotMap.FRONT_RIGHT, this.rightLeader);
        this.driverPad = new WpiXboxGamepad(RobotMap.DRIVER_PAD);
        this.driveRollers = new Talon(RobotMap.INTAKE_MOTOR);
        this.headingGyro = new  ADXRS450_Gyro(RobotMap.HEADING_GYRO);
    }
    @Override
    protected StreamManager provideStreamManager() {
        StreamManager sm = new StreamManager(this);
            sm.registerSubsystem(new Drivetrain(driverPad.joy2X().map(x -> -x), driverPad.joy1Y(),
                    leftLeader, rightLeader, headingGyro));
        sm.registerSubsystem(new Intake(operatorPad.joy2Y(), driveRollers));
            return sm;
        }
}

