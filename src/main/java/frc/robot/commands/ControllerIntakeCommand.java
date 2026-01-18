package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.controller.GenericCommandController;

public class ControllerIntakeCommand extends Command {

    // Constructor – receives the controller instance

    private final GenericCommandController controller;
    public ControllerIntakeCommand(GenericCommandController controller) {
        this.controller = controller;

        // Add subsystem requirements here in the future
        // addRequirements(intakeSubsystem);

    }


    @Override
    public void initialize() {
        super.initialize();
    }

    @Override
    public void execute() {
        // check how many balls we took
        // pull ball into
    }

    @Override
    public boolean isFinished() {
        return super.isFinished();
    }
}