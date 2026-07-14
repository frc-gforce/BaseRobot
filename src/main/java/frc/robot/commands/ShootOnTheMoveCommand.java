package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.feed.Feed;
import frc.robot.subsystems.intakexshooter.IntakeXShooter;
import frc.robot.subsystems.intakexshooter.SpeedLookupTable;
import frc.robot.subsystems.swervedrive.SwerveDriveSubsystem;
import frc.robot.utils.FieldUtils;
import org.littletonrobotics.junction.Logger;
import swervelib.SwerveInputStream;

import static edu.wpi.first.units.Units.*;

public class ShootOnTheMoveCommand extends GenericCommand {
    private final SwerveDriveSubsystem swerveDrive;
    private final IntakeXShooter intakeXShooter;
    private final Feed feed;

    private final SwerveInputStream shootOnTHeMoveInputSteam;
    private final Trigger aimedAtHub;

    private final PIDController angleController;

    private final SpeedLookupTable speedLookupTable;

    public ShootOnTheMoveCommand(SwerveDriveSubsystem swerveDrive, IntakeXShooter intakeXShooter, Feed feed, SwerveInputStream driveInputStream) {
        this.swerveDrive = swerveDrive;
        this.intakeXShooter = intakeXShooter;
        this.feed = feed;
        this.shootOnTHeMoveInputSteam = driveInputStream.copy()
                .aim(() -> new Pose2d(
                        FieldUtils.getHubPose(),
                        Rotation2d.kZero
                ))
                .aimWhile(true)
                .aimHeadingOffset(Rotation2d.fromDegrees(190))
                .aimHeadingOffset(true)
                .scaleTranslation(0.3)
//                .scaleRotation(1)
                .aimLookahead(Seconds.of(0.12));
        this.aimedAtHub = shootOnTHeMoveInputSteam
                .aimLock(Degrees.of(2))
                .debounce(0.05);
        this.angleController = new PIDController(4.0, 0.0, 0.2);
        this.angleController.enableContinuousInput(-Math.PI, Math.PI);
        this.angleController.setTolerance(Math.toRadians(2.0));
        this.speedLookupTable = new SpeedLookupTable();

//        this.swerveDrive.getSwerveDrive().setHeadingCorrection(false);
//        this.swerveDrive.getSwerveDrive().setCosineCompensator(false);
        addGenericRequirements(this.feed, this.intakeXShooter, this.swerveDrive);
    }

    @Override
    public void execute() {
        Pose2d pose = swerveDrive.getPose();
        Translation2d hubPose = FieldUtils.getHubPose();

        ChassisSpeeds chassisSpeeds = shootOnTHeMoveInputSteam.aim(new Pose2d(hubPose, Rotation2d.kZero)).aimWhile(true).get();
//        chassisSpeeds.omegaRadiansPerSecond = getOmega(
//                hubPose,
//                swerveDrive.getPose()
//        );
        swerveDrive.driveFieldOriented(chassisSpeeds);


        double speed = speedLookupTable.getSpeed(pose, hubPose);
        Logger.recordOutput("IntakeXShooterSpeed", speed);
        intakeXShooter.setMotorSpeed(speed);
        if (Math.abs(speed - intakeXShooter.getCurrentSpeed()) < 40 && isAimedAtHub()) {
            Logger.recordOutput("Shooting", true);
            feed.shoot();
        } else {
            Logger.recordOutput("Shooting", false);
            feed.stop();
        }
    }

    private Rotation2d getTargetAngle(Translation2d targetPose, Translation2d robotPose) {
        Translation2d toHub = targetPose.minus(robotPose);
        return toHub.getAngle().plus(Rotation2d.fromDegrees(190));
    }

    private double getOmega(Translation2d targetPose, Pose2d robotPose) {
        Rotation2d targetAngle = getTargetAngle(targetPose, robotPose.getTranslation());
        double omega = angleController.calculate(robotPose.getRotation().getRadians(), targetAngle.getRadians());

        omega = MathUtil.clamp(omega, -3.0, 3.0);

        return omega;
    }

    private boolean isAimedAtHub() {
//        return angleController.atSetpoint();
        return aimedAtHub.getAsBoolean();
    }

    @Override
    public void end(boolean interrupted) {
        intakeXShooter.setMotorSpeed(0);
        feed.stop();
    }
}
