package frc.robot.utils;

import frc.robot.subsystems.tankdrive.TankDriveConstants;

public class DrivetrainConstantsFactory {
    /**
     * Create the default constants for the drivetrain subsystem
     * @return The default constants
     */
    public static TankDriveConstants createDrivetrainConstants() {
        return new TankDriveConstants("Robot/Drivetrain");
    }
}
