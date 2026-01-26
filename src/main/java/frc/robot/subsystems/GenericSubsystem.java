package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

public abstract class GenericSubsystem extends SubsystemBase {
    private final String logPath;
    private Command currentCommand;

    public GenericSubsystem(String logPath) {
        this.logPath = logPath;
        this.currentCommand = Commands.none().withName("None");
    }

    @Override
    public Command getCurrentCommand() {
        return currentCommand;
    }

    public String getLogPath() {
        return logPath;
    }

    @Override
    public final void periodic() {
        Logger.recordOutput(getLogPath() + "/CurrentCommand", getCurrentCommand().getName());
        subsystemPeriodic();
    }

    protected void subsystemPeriodic() {}

    public Command asSubsystemCommand(Command command, String commandName) {
        command.setName(commandName);
        command.addRequirements(this);
        return command.beforeStarting(() -> currentCommand = command);
    }
}
