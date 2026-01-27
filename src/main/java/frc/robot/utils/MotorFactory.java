package frc.robot.utils;

import frc.robot.motors.*;

/**
 * A utility class for creating motor instances based on motor specifications.
 */
public class MotorFactory {
    /**
     * Create a SparkMaxMotor instance based on motor specifications.
     * @param motor The motor specifications.
     * @return The created SparkMaxMotor instance.
     */
    public static SparkMaxMotor createSparkMotor(Motors motor) {
        SparkMaxMotor sparkMaxMotor = new SparkMaxMotor(motor.id, motor.type);
        sparkMaxMotor.setInverted(motor.inverted);
        return sparkMaxMotor;
    }

    /**
     * Creates a TalonFXMotor instance based on motor specifications.
     * @param motor The motor specifications.
     * @return The created TalonFXMotor instance.
     */
    public static TalonFXMotor createTalonFXMotor(Motors motor) {
        TalonFXMotor talonFXMotor = new TalonFXMotor(motor.id);
        talonFXMotor.setInverted(motor.inverted);
        return talonFXMotor;
    }
}
