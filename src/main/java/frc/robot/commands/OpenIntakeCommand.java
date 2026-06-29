package frc.robot.commands;

import frc.robot.subsystems.backintake.BackIntake;
import org.littletonrobotics.junction.Logger;

public class OpenIntakeCommand extends GenericCommand {
    private final BackIntake intake;

    public OpenIntakeCommand(BackIntake intake) {
        this.intake = intake;

        addGenericRequirements(intake);
    }

    @Override
    public void initialize() {
        Logger.recordOutput("BackIntake", true);
        intake.openIntake();

    }

    @Override
    public void end(boolean interrupted) {
        Logger.recordOutput("BackIntake", false);
        intake.stopOpenIntake();
    }
}
