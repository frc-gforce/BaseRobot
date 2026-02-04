package frc.robot.utils;

import edu.wpi.first.math.geometry.Pose3d;
import frc.robot.limelight.LimelightHelpers;

public class LimelightUtils {
    public static final String NAME = "limelight";

    /**
     * Checks if the robot currently has a target
     * @return true if it has a target, false otherwise
     */
    public static boolean hasTarget() {
        return LimelightHelpers.getTV(NAME);
    }

    /**
     * Returns the robot's 3d position in the field
     * @return The robot's 3d position
     */
    public static Pose3d getPose3d()
    {
        return LimelightHelpers.getBotPose3d_wpiBlue(NAME);
    }
}
