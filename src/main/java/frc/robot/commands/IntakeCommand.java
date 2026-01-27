package frc.robot.commands;

import frc.robot.subsystems.feed.Feed;
import frc.robot.subsystems.intakexshooter.IntakeXShooter;

/**
 * Command to control the intake and feed subsystems together to collect fuels.
 */
public class IntakeCommand extends GenericCommand {
    private final IntakeXShooter intakeXShooter;
    private final Feed feed;

    /**
     * Command to control the intake and feed subsystems together to collect fuels.
     * @param intakeXShooter The intake x shooter subsystem.
     * @param feed The feed subsystem.
     */
    public IntakeCommand(IntakeXShooter intakeXShooter, Feed feed) {
        this.intakeXShooter = intakeXShooter;
        this.feed = feed;
        addGenericRequirements(intakeXShooter, feed);
    }

    @Override
    public void initialize() {
        intakeXShooter.setMotorSpeed(1);
        feed.setSpeed(1);
    }

    @Override
    public void end(boolean interrupted) {
        intakeXShooter.setMotorSpeed(0);
        feed.setSpeed(0);
    }
}
