package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.GenericSubsystem;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * A command that can have generic subsystem requirements.
 */
public class GenericCommand extends Command {
    private final Set<GenericSubsystem> requirements = new HashSet<>();

    /**
     * Adds generic subsystems to the command's requirements.
     * @param subsystem The subsystems to add.
     */
    public final void addGenericRequirements(GenericSubsystem... subsystem) {
        super.addRequirements(subsystem);
        Collections.addAll(requirements, subsystem);
    }

    /**
     * Adds generic subsystems to the command's requirements.
     * @param subsystem The subsystems to add.
     */
    public final void addGenericRequirements(Collection<GenericSubsystem> subsystem) {
        super.addRequirements(Collections.unmodifiableCollection(subsystem));
        requirements.addAll(subsystem);
    }

    /**
     * Gets the generic subsystem requirements for this command.
     * @return An immutable set of generic subsystem requirements.
     */
    public Set<GenericSubsystem> getGenericRequirements() {
        return Set.copyOf(requirements);
    }
}
