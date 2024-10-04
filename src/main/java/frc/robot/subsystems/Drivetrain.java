package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {
    private final VictorSPX driveLeftFront = new VictorSPX(2);
    private final VictorSPX driveLeftRear = new VictorSPX(1);
    private final VictorSPX driveRightFront = new VictorSPX(4);
    private final VictorSPX driveRightRear = new VictorSPX(3);

    public Drivetrain() {
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
    }

    public void drive(double left, double right) {
        driveLeftFront.set(ControlMode.PercentOutput, left);
        driveRightFront.set(ControlMode.PercentOutput, right);
    }


    public Command commandArcadeDrive(XboxController controller) {
        return Commands.run(
                () -> {
                    double speed = controller.getLeftY();
                    double rot = Math.abs(1 - speed / 2) * controller.getRightX();
                    this.drive(
                            speed - rot,
                            speed + rot
                    );
                },
                this
        );
    }
}
