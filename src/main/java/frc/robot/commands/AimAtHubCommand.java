package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import frc.robot.subsystems.swervedrive.SwerveDriveSubsystem;
import frc.robot.utils.FieldUtils;

public class AimAtHubCommand extends GenericCommand {

    private final PIDController controller;
    private final SwerveDriveSubsystem swerveDrive;

    public AimAtHubCommand(SwerveDriveSubsystem swerveDrive) {
        controller = new PIDController(4.0, 0.0, 0.2);
        controller.enableContinuousInput(-Math.PI, Math.PI);
        controller.setTolerance(Math.toRadians(2.0));
        this.swerveDrive = swerveDrive;
    }

    private Rotation2d getTargetAngle(Translation2d targetPose, Translation2d robotPose) {
        Translation2d toHub = targetPose.minus(robotPose);
        return toHub.getAngle().plus(Rotation2d.fromDegrees(190));
    }

    private ChassisSpeeds getChassisSpeeds(Translation2d targetPose, Pose2d robotPose) {
        Rotation2d targetAngle = getTargetAngle(targetPose, robotPose.getTranslation());
        double omega = controller.calculate(robotPose.getRotation().getRadians(), targetAngle.getRadians());

        omega = MathUtil.clamp(omega, -3.0, 3.0);

        return new ChassisSpeeds(0, 0, omega);
    }

    private boolean isAimedAtHub() {
        return controller.atSetpoint();
    }

    @Override
    public void execute() {
        Translation2d targetPose = FieldUtils.getHubPose();
        Pose2d robotPose = swerveDrive.getPose();
        ChassisSpeeds chassisSpeeds = getChassisSpeeds(targetPose, robotPose);
        swerveDrive.driveRobotRelative(chassisSpeeds);
    }

    @Override
    public boolean isFinished() {
        return isAimedAtHub();
    }
}
