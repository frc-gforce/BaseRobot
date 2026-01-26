package frc.robot.subsystems.drivetrain;

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
