package com.nutrons.steamworks;

import com.ctre.CANTalon;
import com.nutrons.framework.Robot;
import com.nutrons.framework.StreamManager;
import com.nutrons.framework.controllers.LoopModeEvent;
import com.nutrons.framework.controllers.Talon;
import com.nutrons.framework.inputs.Serial;
import com.nutrons.framework.inputs.WpiGamepad;
import com.nutrons.framework.inputs.WpiXboxGamepad;
import com.nutrons.framework.subsystems.WpiSmartDashboard;
import io.reactivex.Flowable;

public class RobotBootstrapper extends Robot {
    private Talon driveLeftA;
    private Talon driveLeftB;
    private Talon driveRightA;
    private Talon driveRightB;

    public static Talon hoodMaster;

    LoopModeEvent velocityMode;
    //    private Talon shooter;

    private WpiGamepad driverPad;
    private Flowable<Boolean> fireButtonStream;
    public static Serial serial;
    private Vision vision;
    private WpiSmartDashboard sd;

    private Talon intakeController;
    private WpiXboxGamepad controller;
    private Talon shooterMotor1;
    private Talon shooterMotor2;

    @Override
    protected void constructStreams() {
        /**this.driveLeftA = new Talon(RobotMap.LEFT_DRIVE_MOTOR_A);
        this.driveLeftB = new FollowerTalon(RobotMap.LEFT_DRIVE_MOTOR_B,
                RobotMap.LEFT_DRIVE_MOTOR_A);
        this.driveRightA = new Talon(RobotMap.RIGHT_DRIVE_MOTOR_A);
        this.driveRightB = new FollowerTalon(RobotMap.RIGHT_DRIVE_MOTOR_B,
                RobotMap.RIGHT_DRIVE_MOTOR_A);**/
	this.driverPad = new WpiXboxGamepad(0);
        this.hoodMaster = new Talon(RobotMap.HOOD_MOTOR_A);
        hoodMaster.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Absolute);
        hoodMaster.configNominalOutputVoltage(+0f, -0f);
        hoodMaster.configPeakOutputVoltage(+12f, -12f);
        hoodMaster.setProfile(0);
        hoodMaster.reverseOutput(false);
        hoodMaster.reverseSensor(false);
        hoodMaster.setEncPosition(0);


        this.serial = new Serial(20, 10);
        this.vision = new Vision(serial.getDataStream());
        /**this.shooter = new Talon(RobotMap.SHOOTER);
        this.fireButtonStream = driverPad.button(RobotMap.SHOOT_BUTTON);
        this.velocityMode = new LoopModeEvent(ControllerMode.LOOP_SPEED);
        velocityMode.actOn(shooter);**/
        this.sd = new WpiSmartDashboard();

        this.intakeController = new Talon(RobotMap.INTAKE_MOTOR);
        this.shooterMotor1 = new Talon(RobotMap.SHOOTER_MOTOR_1);
        this.shooterMotor2 = new Talon(RobotMap.SHOOTER_MOTOR_2, this.shooterMotor1);
        this.controller = new WpiXboxGamepad(0);
        this.enabledStream();
    }

    @Override
    protected StreamManager provideStreamManager() {
        StreamManager sm = new StreamManager(this);

        /**sm.registerSubsystem(new Drivetrain(driverPad.joy1Y(), driverPad.joy2Y(),
                driveLeftA, driveRightA));**/
        /**sm.registerSubsystem(new Turret(vision.getAngle(), hoodMaster,
                fireButtonStream, shooter));**/

        sm.registerSubsystem(new TurretStaticPid(vision.getAngle(), hoodMaster));
        sm.registerSubsystem(sd);
        sm.registerSubsystem(new Logging());
        sm.registerSubsystem(new Shooter(shooterMotor1));
        sm.registerSubsystem(new Feeder(intakeController));
        return sm;
    }
}
