package frc.robot.commands;

import frc.robot.subsystems.backintake.BackIntake;
import frc.robot.subsystems.feed.Feed;
import org.littletonrobotics.junction.Logger;

public class OpenIntakeCommand extends GenericCommand {
    private final BackIntake intake;
    private final Feed feed;

    public OpenIntakeCommand(BackIntake intake, Feed feed) {
        this.intake = intake;
        this.feed = feed;

        addGenericRequirements(intake);
    }

    @Override
    public void initialize() {
        Logger.recordOutput("BackIntake", true);
        intake.openIntake();
        feed.openIntake();

    }

    @Override
    public void end(boolean interrupted) {
        Logger.recordOutput("BackIntake", false);
        intake.stopOpenIntake();
        feed.stop();
    }
}
