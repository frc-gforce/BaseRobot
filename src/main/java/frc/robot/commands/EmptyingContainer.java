package frc.robot.commands;

import frc.robot.subsystems.backintake.BackIntake;
import frc.robot.subsystems.feed.Feed;
import frc.robot.subsystems.intakexshooter.IntakeXShooter;

public class EmptyingContainer extends GenericCommand{
    private final Feed feed;
    private final BackIntake intake;

    public EmptyingContainer(Feed feed, BackIntake intake) {
        this.feed = feed;
        this.intake = intake;
        addGenericRequirements(feed, intake);
    }

    @Override
    public void initialize() {
        feed.emptying();
        intake.emptying();
    }

    @Override
    public void end(boolean interrupted) {
        feed.stop();
        intake.stopIntake();
    }
}
