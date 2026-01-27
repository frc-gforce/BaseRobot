package frc.robot.subsystems.intakexshooter;

public record IntakeXShooterConstants(
        String logPath,
        String speedLogPath
) {
    public IntakeXShooterConstants(String logPath) {
        this(logPath, logPath + "/Speed");
    }
}
