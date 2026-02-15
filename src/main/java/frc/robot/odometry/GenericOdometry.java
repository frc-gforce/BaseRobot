package frc.robot.odometry;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.Odometry;

public interface GenericOdometry<T extends Odometry> {
    void resetPose(Pose2d pose2d);
    void resetRotation(Rotation2d rotation2d);
    Pose2d getPoseMeters();
    T getOdometry();
}
