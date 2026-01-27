package frc.robot.subsystems.drivetrain;

/**
 * Constants for the drivetrain subsystem
 * @param logPath The path to the log
 * @param poseLogPath The path to the log for pose data
 */
public record DrivetrainConstants(
        String logPath,
        String poseLogPath
) {
    public DrivetrainConstants(
            String logPath
    ) {
        this(
                logPath,
                logPath + "/Pose"
        );
    }
}
