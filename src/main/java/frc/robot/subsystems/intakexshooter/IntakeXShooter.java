package frc.robot.subsystems.intakexshooter;

import frc.robot.Constants.OperatorConstants;
import frc.robot.motors.BaseMotor;
import frc.robot.subsystems.GenericSubsystem;

/**
 * Controls the ball collecting and shooting mechanisms
 */
public class IntakeXShooter extends GenericSubsystem {
    private final IntakeXShooterConstants constants;
    private final BaseMotor motor; //the motor used
    private double targetSpeed; //shooting speed
    private double speedError; //the offset for speed check

    /**
     * Controls the ball collecting and shooting mechanisms
     * @param constants The constants used in the system
     * @param motor The motor used in the system
     */
    public IntakeXShooter(IntakeXShooterConstants constants, BaseMotor<?, ?> motor) //builds object with default values
    {
        this(constants, motor, OperatorConstants.SHOOTER_TARGET_SPEED, OperatorConstants.SHOOTER_SPEED_ERROR);
    }

    /**
     * Controls the ball collecting and shooting mechanisms
     * @param constants The constants used in the system
     * @param motor The motor used in the system
     * @param targetSpeed The speed needed to shoot
     * @param speedError Determines how far can the speed be from the target speed
     */
    public IntakeXShooter(IntakeXShooterConstants constants, BaseMotor motor, double targetSpeed, double speedError)      //builds object with set values
    {
        super(constants.logPath());
        this.constants = constants;
        this.motor = motor;
        this.targetSpeed = targetSpeed;
        this.speedError = speedError;
    }

    /**
     * Returns the target speed needed inorder to shoot
     * @return target speed
     */
    public double getTargetSpeed() {
        return this.targetSpeed;
    }

    /**
     * Sets the target speed needed inorder to shoot
     * @param targetSpeed The speed needed in order to shoot
     */
    public void setTargetSpeed(double targetSpeed) {
        this.targetSpeed = targetSpeed;
    }

    /**
     * Returns the limit to how far can the speed be from the target speed
     * @return Speed error
     */
    public double getSpeedError() {
        return this.speedError;
    }

    /**
     * Sets the limit to how far can the speed be from the target speed
     * @param speedError Determines how far can the speed be from the target speed
     */
    public void setSpeedError(double speedError) {
        this.speedError = speedError;
    }

    /**
     * Checks if the motor is at the target speed and ready to shoot
     * @return Is the system ready to shoot
     */
    public boolean ReadyToShoot() {
        //TODO - motor speed calculation
        double speed = motor.getSpeed();
        return speed <= targetSpeed + speedError &&
               speed >= targetSpeed - speedError;
    }
}
