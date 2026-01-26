package frc.robot.utils;

import frc.robot.subsystems.drivetrain.DrivetrainConstants;

public class DrivetrainConstantsFactory {
    public static DrivetrainConstants createDrivetrainConstants() {
        return new DrivetrainConstants("Robot/Drivetrain");
    }
}
