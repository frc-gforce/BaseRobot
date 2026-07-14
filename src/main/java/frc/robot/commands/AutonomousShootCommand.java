package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.feed.Feed;
import frc.robot.subsystems.intakexshooter.IntakeXShooter;

public class AutonomousShootCommand extends GenericCommand{
    private final IntakeXShooter intakeXShooter;
    private final Feed feed;
    private final double commandRunTime;

    private double commandStart;

    public AutonomousShootCommand(IntakeXShooter intakeXShooter, Feed feed, double commandRunTime) {
        this.intakeXShooter = intakeXShooter;
        this.feed = feed;
        this.commandRunTime = commandRunTime;
        addGenericRequirements(intakeXShooter, feed);
    }

    @Override
    public void initialize() {
        commandStart = Timer.getFPGATimestamp();
        intakeXShooter.setMotorSpeed(1);
        feed.shoot();
    }

    @Override
    public boolean isFinished() {
        return Timer.getFPGATimestamp() > commandStart + commandRunTime;
    }

    @Override
    public void end(boolean interrupted) {
        intakeXShooter.setMotorSpeed(0);
        feed.stop();
    }
}
