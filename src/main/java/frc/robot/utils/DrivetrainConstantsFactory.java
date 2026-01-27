package frc.robot.utils;

import frc.robot.subsystems.drivetrain.DrivetrainConstants;

public class DrivetrainConstantsFactory {
    /**
     * Create the default constants for the drivetrain subsystem
     * @return The default constants
     */
    public static DrivetrainConstants createDrivetrainConstants() {
        return new DrivetrainConstants("Robot/Drivetrain");
    }
}
