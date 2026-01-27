package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

/**
 * Base class for all subsystems in the robot.
 * Provides common functionality, command helper, and logging for subsystems.
 */
public abstract class GenericSubsystem extends SubsystemBase {
    private final String logPath;
    private Command currentCommand;

    /**
     * Base class for all subsystems in the robot.
     * Provides common functionality, command helper, and logging for subsystems.
     * @param logPath The path to the log for the subsystem
     */
    public GenericSubsystem(String logPath) {
        this.logPath = logPath;
        this.currentCommand = Commands.none().withName("None");
    }

    /**
     * Returns the currently executing command for the subsystem.
     * @return The current command
     */
    @Override
    public Command getCurrentCommand() {
        return currentCommand;
    }

    /**
     * Returns the log path to the for the subsystem
     * @return The log path
     */
    public String getLogPath() {
        return logPath;
    }

    @Override
    public final void periodic() {
        Logger.recordOutput(getLogPath() + "/CurrentCommand", getCurrentCommand().getName());
        subsystemPeriodic();
    }

    /**
     * Called periodically by the subsystems.
     * <br>
     * This method is intended to be overridden by subclasses to provide custom periodic behavior.
     */
    protected void subsystemPeriodic() {}

    /**
     * Wraps a command as a subsystem command.
     * <br>
     * This updates the subsystem's current command and adds the subsystem as a requirement to the command.
     * @param command The command to wrap
     * @param commandName The name of the command
     * @return The wrapped command
     */
    public Command asSubsystemCommand(Command command, String commandName) {
        command.setName(commandName);
        command.addRequirements(this);
        return command.beforeStarting(() -> currentCommand = command);
    }
}
