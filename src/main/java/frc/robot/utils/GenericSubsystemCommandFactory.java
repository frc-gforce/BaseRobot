package frc.robot.utils;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.GenericCommand;
import frc.robot.subsystems.GenericSubsystem;

import java.util.Set;

public class GenericSubsystemCommandFactory {
    /**
     * Wraps a command as a subsystem command.
     * @param command The command to wrap.
     * @param commandName The name of the command.
     * @param subsystems The subsystems to wrap the command with.
     * @return The wrapped command.
     */
    public static Command getAsSubsystemsCommand(Command command, String commandName, GenericSubsystem... subsystems) {
        return getAsSubsystemsCommand(command, commandName, Set.of(subsystems));
    }

    /**
     * Wraps a command as a subsystem command.
     * @param command The command to wrap.
     * @param commandName The name of the command.
     * @param subsystems The subsystems to wrap the command with.
     * @return The wrapped command.
     */
    public static Command getAsSubsystemsCommand(Command command, String commandName, Set<GenericSubsystem> subsystems) {
        Command result = command;
        for (GenericSubsystem sub : subsystems) {
            result = sub.asSubsystemCommand(result, commandName);
        }
        return result;
    }

    /**
     * Convenience method for wrapping a GenericCommand as a subsystem command.
     * <br>
     * This extracts the GenericSubsystem requirements from the command and uses them as the subsystems.
     * @param command The command to wrap.
     * @param commandName The name of the command.
     * @return The wrapped command.
     */
    public static Command getAsSubsystemsCommand(GenericCommand command, String commandName) {
        return getAsSubsystemsCommand(command, commandName, command.getGenericRequirements());
    }
}
