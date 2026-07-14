package frc.robot.subsystems.swervedrive;

import java.io.File;
import java.io.IOException;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.utils.FieldUtils;
import frc.robot.utils.LimelightUtils;
import org.littletonrobotics.junction.Logger;
import swervelib.SwerveDrive;
import swervelib.math.SwerveMath;
import swervelib.parser.SwerveParser;
import swervelib.telemetry.SwerveDriveTelemetry;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.GenericSubsystem;

public class SwerveDriveSubsystem extends GenericSubsystem {

    double maximumSpeed = 5.3;//Units.feetToMeters(4.5);
    File swerveJsonDirectory = new File(Filesystem.getDeployDirectory(), "swerve");
    SwerveDrive swerveDrive;

    Pose2d startPose = new Pose2d(3.5, 0.5, new Rotation2d(0));

    //TODO add constants
    public SwerveDriveSubsystem() {
        super("SwerveDriveSubsystem");
        try {
            swerveDrive = new SwerveParser(swerveJsonDirectory).createSwerveDrive(maximumSpeed);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SwerveDriveTelemetry.verbosity = SwerveDriveTelemetry.TelemetryVerbosity.HIGH;
        RobotConfig config;
        try {

            final boolean enableFeedforward = true;
            config = RobotConfig.fromGUISettings();
            AutoBuilder.configure(
                    swerveDrive::getPose,
                    swerveDrive::resetOdometry,
                    swerveDrive::getRobotVelocity,
                    (speeds, feedForward) -> {
                        if (enableFeedforward) {
                            swerveDrive.drive(
                                    speeds,
                                    swerveDrive.kinematics.toSwerveModuleStates(speeds),
                                    feedForward.linearForces()
                            );
                        }
                        else {
                            swerveDrive.setChassisSpeeds(speeds);
                        }
                    },
                    new PPHolonomicDriveController(
                            new PIDConstants(5.0, 0.0, 0.0),
                            new PIDConstants(5.0, 0.0, 0.0)
                    ),
                    config,
                    FieldUtils::isRedTeam,
                    this
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
//        swerveDrive.resetOdometry(LimelightUtils.getPose2d());
        swerveDrive.resetOdometry(startPose);
    }

    public Pose2d getPose() {
        return swerveDrive.getPose();
    }

    public Command stop() {
        return driveCommand(() -> 0, () -> 0, () -> 0, () -> 0);
    }

    public void driveRobotRelative(ChassisSpeeds velocity) {
        swerveDrive.drive(velocity);
    }

    public Command driveRobotRelative(Supplier<ChassisSpeeds> velocity) {
        return run(() -> swerveDrive.drive(velocity.get()));
    }

    public Command driveForward() {
        return run(() -> {
            swerveDrive.drive(new Translation2d(1, 0), 0, false, false);
        });
    }

    /**
     * Command to drive the robot using translative values and heading as a setpoint.
     *
     * @param translationX Translation in the X direction.
     * @param translationY Translation in the Y direction.
     * @param headingX     Heading X to calculate angle of the joystick.
     * @param headingY     Heading Y to calculate angle of the joystick.
     * @return Drive command.
     */
    public Command driveCommand(DoubleSupplier translationX, DoubleSupplier translationY, DoubleSupplier headingX,
                                DoubleSupplier headingY) {
        return run(() -> {

            Translation2d scaledInputs = SwerveMath.scaleTranslation(new Translation2d(translationX.getAsDouble(),
                    translationY.getAsDouble()), 0.8);

            // Make the robot move
            driveFieldOriented(swerveDrive.swerveController.getTargetSpeeds(scaledInputs.getX(), scaledInputs.getY(),
                    headingX.getAsDouble(),
                    headingY.getAsDouble(),
                    swerveDrive.getOdometryHeading().getRadians(),
                    swerveDrive.getMaximumChassisVelocity()));
            Logger.recordOutput("JoystickA", new Translation2d(translationX.getAsDouble(), translationY.getAsDouble()));
            Logger.recordOutput("JoystickB", new Translation2d(headingX.getAsDouble(), headingY.getAsDouble()));
        });
    }

    /**
     * Command to drive the robot using translative values and heading as angular velocity.
     *
     * @param translationX     Translation in the X direction.
     * @param translationY     Translation in the Y direction.
     * @param angularRotationX Rotation of the robot to set
     * @return Drive command.
     */
    public Command driveCommand(DoubleSupplier translationX, DoubleSupplier translationY, DoubleSupplier angularRotationX) {
        return asSubsystemCommand(run(() -> {
                    // Make the robot move
                    swerveDrive.drive(new Translation2d(translationX.getAsDouble() * swerveDrive.getMaximumChassisVelocity(),
                                    translationY.getAsDouble() * swerveDrive.getMaximumChassisVelocity()),
                            angularRotationX.getAsDouble() * swerveDrive.getMaximumChassisAngularVelocity(),
                            false,
                            false);
                }),
                "DriveCommand");
    }

    public void driveFieldOriented(ChassisSpeeds velocity) {
        swerveDrive.driveFieldOriented(velocity);
    }

    public Command driveFieldOriented(Supplier<ChassisSpeeds> velocity) {
        return run(() -> swerveDrive.driveFieldOriented(velocity.get()));
    }

    public void resetOdometry(Pose2d pose) {
        swerveDrive.resetOdometry(pose);
    }

    @Override
    protected void subsystemPeriodic() {
        log();
        if (LimelightUtils.getTagCount() > 0) {
            swerveDrive.addVisionMeasurement(LimelightUtils.getPose2d(), Timer.getFPGATimestamp());
        }
    }

    public SwerveDrive getSwerveDrive() {
        return swerveDrive;
    }

    private void log() {
        Translation2d hubPose = FieldUtils.getHubPose();
        Logger.recordOutput(getLogPath() + "/Pose", swerveDrive.getPose());
        Logger.recordOutput(getLogPath() + "/Pose/HubDistance", getPose().getTranslation().getDistance(hubPose));
        Logger.recordOutput(getLogPath() + "/Pose/HubAimed", Math.abs(hubPose.minus(getPose().getTranslation()).getAngle().plus(Rotation2d.fromDegrees(190)).getRadians()) - Math.abs(getPose().getRotation().getRadians()) >= Math.toRadians(2) );
        Logger.recordOutput(getLogPath() + "/Vision", LimelightUtils.getPose2d());
        Logger.recordOutput(getLogPath() + "/Vision/TagCount", LimelightUtils.getTagCount());
    }
}
