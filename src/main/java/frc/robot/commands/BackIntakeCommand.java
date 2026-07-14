package frc.robot.commands;

import frc.robot.subsystems.backintake.BackIntake;

public class BackIntakeCommand extends GenericCommand {
    private final BackIntake intake;
    
    public BackIntakeCommand(BackIntake intake) {
        this.intake = intake;
        
        addGenericRequirements(intake);
    }

    @Override
    public void initialize() {
        intake.intake();
    }

    @Override
    public void end(boolean interrupted) {
        intake.stopIntake();
    }
}
