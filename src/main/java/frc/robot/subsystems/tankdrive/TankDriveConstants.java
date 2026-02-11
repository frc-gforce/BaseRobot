package frc.robot.subsystems.tankdrive;

/**
 * Constants for the drivetrain subsystem
 * @param logPath The path to the log
 * @param poseLogPath The path to the log for pose data
 */
public record TankDriveConstants(
        String logPath,
        String poseLogPath
) {
    public TankDriveConstants(
            String logPath
    ) {
        this(
                logPath,
                logPath + "/Pose"
        );
    }
}
