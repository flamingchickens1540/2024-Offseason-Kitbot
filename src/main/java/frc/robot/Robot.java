/// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Shooter;


/**
 * The VM is configured to automatically run this class, and to call the methods corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    private final CommandXboxController controller = new CommandXboxController(0);
    private final Shooter shooter = new Shooter();
    private final Drivetrain drivetrain = new Drivetrain();


    /**
     * This method is run when the robot is first started up and should be used for any
     * initialization code.
     */
    @Override
    public void robotInit() {
        CommandScheduler.getInstance().enable();

        controller.leftBumper().whileTrue(shooter.commandShoot());
        controller.rightBumper().whileTrue(shooter.commandIntake());
        controller.y().whileTrue(shooter.commandOuttake());
        drivetrain.setDefaultCommand(drivetrain.commandArcadeDrive(controller.getHID()));
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
    public void autonomousInit() {
        Commands.sequence(
        shooter.commandShoot().withTimeout(4),
                Commands.startEnd(
                        () -> drivetrain.drive(0.5, 0.5),
                        () -> drivetrain.drive(0,0),
                        drivetrain
                ).withTimeout(1)
                        ).schedule();
    }


    /**
     * This method is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic() {
    }


    /**
     * This method is called once when teleop is enabled.
     */
    @Override
    public void teleopInit() {
    }


    /**
     * This method is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {
//        drivetrain.drive(
//                controller.getLeftY() - controller.getRightX(),
//                controller.getLeftY() + controller.getRightX()
//        );
//        drivetrain.drive(
//                controller.getLeftY(),
//                controller.getRightY()
//        );
    }


    /**
     * This method is called once when the robot is disabled.
     */
    @Override
    public void disabledInit() {
    }


    /**
     * This method is called periodically when disabled.
     */
    @Override
    public void disabledPeriodic() {
    }


    /**
     * This method is called once when test mode is enabled.
     */
    @Override
    public void testInit() {
    }


    /**
     * This method is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {
    }


    /**
     * This method is called once when the robot is first started up.
     */
    @Override
    public void simulationInit() {
    }


    /**
     * This method is called periodically whilst in simulation.
     */
    @Override
    public void simulationPeriodic() {
    }
}
