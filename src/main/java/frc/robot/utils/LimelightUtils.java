package frc.robot.utils;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import frc.robot.limelight.LimelightHelpers;

public class LimelightUtils {
    public static final String NAME = "limelight";
    public enum VisionMode {APRILTAG, BALL}
    static private VisionMode currentMode;

    /**
     * Sets the limelight pipeline to the wanted target detection.
     * @param mode Which target to detect. VisionMode.APRILTAG for tags (location), VisionMode.BALL for ball detection
     */
    public static void setVisionMode(VisionMode mode) {
        switch (mode) {
            case APRILTAG:
                LimelightHelpers.setPipelineIndex(NAME, 0);
                currentMode = VisionMode.APRILTAG;
                break;
            case BALL:
                LimelightHelpers.setPipelineIndex(NAME, 1);
                currentMode = VisionMode.BALL;
                break;
        }
    }

    /**
     * Returns the current detection mode.
     * @return VisionMode.APRILTAG or VisionMode.BALL for tags or ball detection
     */
    public static VisionMode getCurrentMode() {
        return currentMode;
    }

    /**
     * Checks if the robot currently has a target
     *
     * @return true if it has a target, false otherwise
     */
    public static boolean hasTarget() {
        return LimelightHelpers.getTV(NAME);
    }

    /**
     * Returns the robot's 2d position in the field
     *
     * @return The robot's 2d position if in tags mode, otherwise null
     */
    public static Pose2d getPose2d() {
        if (currentMode != VisionMode.APRILTAG) return null;
        return LimelightHelpers.getBotPose2d_wpiBlue(NAME);
    }

    /**
     * Returns the robot's 3d position in the field
     *
     * @return The robot's 3d position if in tags mode, otherwise null
     */
    public static Pose3d getPose3d() {
        if (currentMode != VisionMode.APRILTAG) return null;
        return LimelightHelpers.getBotPose3d_wpiBlue(NAME);
    }

    /**
     * Returns the amount of tags in sight
     *
     * @return Amount of tags detected in sight if in tags mode, otherwise -1
     */
    public static int getTagCount() {
        if (currentMode != VisionMode.APRILTAG) return -1;
        return LimelightHelpers.getBotPoseEstimate_wpiBlue(NAME).tagCount;
    }

    /**
     * Returns the time to receive information from the camera (latency)
     * @return Latency from camera in ms (milliseconds)
     */
    public static double getLatency() {
        return LimelightHelpers.getLatency_Capture(NAME)
                + LimelightHelpers.getLatency_Pipeline(NAME);
    }
}
