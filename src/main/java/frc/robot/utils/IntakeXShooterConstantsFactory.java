package frc.robot.utils;

import frc.robot.subsystems.intakexshooter.IntakeXShooterConstants;

public class IntakeXShooterConstantsFactory {
    /**
     * Create the default constants for the intake x shooter subsystem
     * @return The default constants
     */
    public static IntakeXShooterConstants createIntakeXShooterConstants() {
        return new IntakeXShooterConstants("Robot/IntakeXShooter");
    }
}
