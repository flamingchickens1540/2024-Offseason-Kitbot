package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
    private final VictorSPX feeder = new VictorSPX(5);
    private final VictorSPX shooter = new VictorSPX(6);

    public Shooter() {
        shooter.setNeutralMode(NeutralMode.Coast);
        shooter.setInverted(false);

        feeder.setNeutralMode(NeutralMode.Coast);
        shooter.setInverted(false);
    }

    public void set(double feederSpeed, double shooterSpeed) {
        feeder.set(ControlMode.PercentOutput, feederSpeed);
        shooter.set(ControlMode.PercentOutput, shooterSpeed);
        System.out.println(feederSpeed+ " | "+shooterSpeed);
    }


    public void stop() {
        this.set(0,0);
    }

    public Command commandIntake() {
        return Commands.startEnd(
                () -> this.set(-1, -1),
                this::stop,
                this
        );
    }

    public Command commandShoot() {
        Command command = Commands.parallel(
                Commands
                        .runOnce(() -> this.set(0,1))
                        .andThen(Commands.waitSeconds(0.2))
                        .andThen(Commands.runOnce(() -> this.set(1,1))),
                Commands.startEnd(() -> {}, this::stop)
        );
        command.addRequirements(this);
        return command;
    }
}
