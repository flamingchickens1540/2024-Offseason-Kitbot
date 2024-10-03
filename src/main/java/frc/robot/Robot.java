/// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;


/**
 * The VM is configured to automatically run this class, and to call the methods corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot
{
    private static final String DEFAULT_AUTO = "Default";
    private static final String CUSTOM_AUTO = "My Auto";
    private String autoSelected;
    private final SendableChooser<String> chooser = new SendableChooser<>();
    private final VictorSPX driveLeftFront = new VictorSPX(2);
    private final VictorSPX driveLeftRear = new VictorSPX(1);
    private final VictorSPX driveRightFront = new VictorSPX(4);
    private final VictorSPX driveRightRear = new VictorSPX(3);
    private final VictorSPX feeder = new VictorSPX(5);
    private final VictorSPX shooter = new VictorSPX(6);
    private final CommandXboxController controller = new CommandXboxController(0);
    private final Subsystem sIntake = new SubsystemBase() {};
    private final Subsystem sShooter = new SubsystemBase() {};

    public Command commandShooter() {
        return Commands.parallel(
                Commands.startEnd(
                        () -> shooter.set(ControlMode.PercentOutput, 1),
                        () -> shooter.set(ControlMode.PercentOutput, 0),
                        sShooter
                ),

                Commands.waitSeconds(0.2)
                        .andThen(Commands.startEnd(
                                () -> feeder.set(ControlMode.PercentOutput, 1),
                                () -> feeder.set(ControlMode.PercentOutput, 0),
                                sIntake
                        ))
        );
    }
    public Command commandIntake() {
        return Commands.parallel(
                Commands.startEnd(
                        () -> feeder.set(ControlMode.PercentOutput, -1),
                        () -> feeder.set(ControlMode.PercentOutput, 0),
                        sIntake
                ),
                Commands.startEnd(
                        () -> shooter.set(ControlMode.PercentOutput, -1),
                        () -> shooter.set(ControlMode.PercentOutput, 0),
                        sShooter
                )
        );
    }
    
    /**
     * This method is run when the robot is first started up and should be used for any
     * initialization code.
     */
    @Override
    public void robotInit()
    {
        CommandScheduler.getInstance().enable();
        shooter.setNeutralMode(NeutralMode.Coast);
        shooter.setInverted(false);

        feeder.setNeutralMode(NeutralMode.Coast);
        shooter.setInverted(false);

        driveLeftFront.setNeutralMode(NeutralMode.Brake);
        driveRightFront.setNeutralMode(NeutralMode.Brake);
        driveLeftRear.setNeutralMode(NeutralMode.Brake);
        driveRightRear.setNeutralMode(NeutralMode.Brake);


        driveLeftFront.setInverted(true);
        driveLeftRear.setInverted(true);
        driveRightFront.setInverted(false);
        driveRightRear.setInverted(false);

       driveLeftRear.follow(driveLeftFront);
       driveRightRear.follow(driveRightFront);

       controller.leftBumper().whileTrue(this.commandShooter());
       controller.rightBumper().whileTrue(this.commandIntake());
    }
    
    
    /**
     * This method is called every 20 ms, no matter the mode. Use this for items like diagnostics
     * that you want ran during disabled, autonomous, teleoperated and test.
     *
     * <p>This runs after the mode specific periodic methods, but before LiveWindow and
     * SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
//        System.out.println(controller.leftBumper().getAsBoolean());
//        System.out.println(controller.getLeftY());
    }
    
    
    /**
     * This autonomous (along with the chooser code above) shows how to select between different
     * autonomous modes using the dashboard. The sendable chooser code works with the Java
     * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all the chooser code and
     * uncomment the getString line to get the auto name from the text box below the Gyro
     *
     * <p>You can add additional auto modes by adding additional comparisons to the switch structure
     * below with additional strings. If using the SendableChooser, make sure to add them to the
     * chooser code above as well.
     */
    @Override
    public void autonomousInit()
    {

    }
    
    
    /** This method is called periodically during autonomous. */
    @Override
    public void autonomousPeriodic()
    {
        switch (autoSelected)
        {
            case CUSTOM_AUTO:
                // Put custom auto code here
                break;
            case DEFAULT_AUTO:
            default:
                // Put default auto code here
                break;
        }
    }
    
    
    /** This method is called once when teleop is enabled. */
    @Override
    public void teleopInit() {
    }
    
    
    /** This method is called periodically during operator control. */
    @Override
    public void teleopPeriodic() {
        driveLeftFront.set(ControlMode.PercentOutput, controller.getLeftY()-controller.getRightX());
        driveRightFront.set(ControlMode.PercentOutput, controller.getLeftY()+controller.getRightX());
    }
    
    
    /** This method is called once when the robot is disabled. */
    @Override
    public void disabledInit() {}
    
    
    /** This method is called periodically when disabled. */
    @Override
    public void disabledPeriodic() {}
    
    
    /** This method is called once when test mode is enabled. */
    @Override
    public void testInit() {}
    
    
    /** This method is called periodically during test mode. */
    @Override
    public void testPeriodic() {}
    
    
    /** This method is called once when the robot is first started up. */
    @Override
    public void simulationInit() {}
    
    
    /** This method is called periodically whilst in simulation. */
    @Override
    public void simulationPeriodic() {}
}
