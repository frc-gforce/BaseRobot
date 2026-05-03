package frc.robot.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.subsystems.feed.Feed;
import frc.robot.subsystems.intakexshooter.IntakeXShooter;
import frc.robot.subsystems.intakexshooter.SpeedLookupTable;
import org.littletonrobotics.junction.Logger;

import java.util.function.Supplier;

/**
 * Command to control the shooter and feed subsystems to shoot fuels.
 */
public class ShootCommand extends GenericCommand {
    private final IntakeXShooter intakeXShooter;
    private final Feed feed;

    //pose: x: 3.2 y: 4.335, heading: -178.8
    private final double CLOSE_HUB_SHOOT = 3400;

    //pose: x: 2.621 y: 4.46 heading: 177.1
    private final double CLOSE_MIDDLE_HUB_SHOOT = 3600;

    //pose: x: 2.326 y: 4.408, heading: 180
    private final double MIDDLE_HUB_SHOOT = 3700;

    //dist: 1.91 speed: 3650
    //dist: 2.680 speed 4000

    private final Supplier<Translation2d> robotPose;
    private final Supplier<Translation2d> hubPose;

    private final SpeedLookupTable speedLookupTable;

    /**
     * Command to control the shooter and feed subsystems to shoot fuels.
     * @param intakeXShooter The intake x shooter subsystem.
     * @param feed The feed subsystem.
     */
    public ShootCommand(IntakeXShooter intakeXShooter, Feed feed, Supplier<Translation2d> robotPose, Supplier<Translation2d> hubPose) {
        this.intakeXShooter = intakeXShooter;
        this.feed = feed;
        this.robotPose = robotPose;
        this.hubPose = hubPose;
        addGenericRequirements(intakeXShooter, feed);
        speedLookupTable = new SpeedLookupTable();
    }

    @Override
    public void initialize() {
        double speed = speedLookupTable.getSpeed(robotPose.get(), hubPose.get());
        Logger.recordOutput("IntakeXShooterSpeed", speed);
        intakeXShooter.setMotorSpeed(speed);
//        feed.shoot();
    }

    @Override
    public void execute() {
        double speed = speedLookupTable.getSpeed(robotPose.get(), hubPose.get());
        Logger.recordOutput("IntakeXShooterSpeed", speed);
        intakeXShooter.setMotorSpeed(speed);
        if (Math.abs(speed - intakeXShooter.getCurrentSpeed()) < 10) {
            feed.shoot();
        }
        else {
            feed.stop();
        }
    }

    @Override
    public void end(boolean interrupted) {
        intakeXShooter.setMotorSpeed(0);
        feed.stop();
        Logger.recordOutput("IntakeXShooterSpeed", 0.0);
    }
}
