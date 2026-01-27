package frc.robot.commands;

import frc.robot.subsystems.feed.Feed;
import frc.robot.subsystems.intakexshooter.IntakeXShooter;

/**
 * Command to control the shooter and feed subsystems to shoot fuels.
 */
public class ShootCommand extends GenericCommand {
    private final IntakeXShooter intakeXShooter;
    private final Feed feed;

    /**
     * Command to control the shooter and feed subsystems to shoot fuels.
     * @param intakeXShooter The intake x shooter subsystem.
     * @param feed The feed subsystem.
     */
    public ShootCommand(IntakeXShooter intakeXShooter, Feed feed) {
        this.intakeXShooter = intakeXShooter;
        this.feed = feed;
        addGenericRequirements(intakeXShooter, feed);
    }

    @Override
    public void initialize() {
        intakeXShooter.setMotorSpeed(1);
        feed.setSpeed(-1);
    }

    @Override
    public void end(boolean interrupted) {
        intakeXShooter.setMotorSpeed(0);
        feed.setSpeed(0);
    }
}
