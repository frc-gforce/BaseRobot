package frc.robot.subsystems.intakexshooter;

/**
 * Constants for the intake x shooter subsystem
 * @param logPath The path to the log
 * @param speedLogPath The path to the log for speed data
 */
public record IntakeXShooterConstants(
        String logPath,
        String speedLogPath
) {
    /**
     * Constants for the intake x shooter subsystem.
     * <br>
     * All relevant values are set by default.
     * <br><br>
     * This constructor calls the {@link #IntakeXShooterConstants(String, String)} with all the relevant values set to default.
     * @param logPath The path to the log
     */
    public IntakeXShooterConstants(String logPath) {
        this(logPath, logPath + "/Speed");
    }
}
