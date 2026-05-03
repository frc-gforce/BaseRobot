package frc.robot.commands;

import frc.robot.subsystems.backintake.BackIntake;
import org.littletonrobotics.junction.Logger;

public class CloseIntakeCommand extends GenericCommand {
    private final BackIntake intake;

    public CloseIntakeCommand(BackIntake intake) {
        this.intake = intake;

        addGenericRequirements(intake);
    }

    @Override
    public void initialize() {
        Logger.recordOutput("BackIntake", true);
        intake.closeIntake();
    }

    @Override
    public void end(boolean interrupted) {
        Logger.recordOutput("BackIntake", false);
        intake.stopCloseIntake();
    }
}
