package frc.robot.motors;

/**
 * BaseMotor interface defines common methods for motor control and configuration.
 */
public interface BaseMotor {
    /**
     * Sets the speed of the motor.
     * @param speed The speed to set, ranging from {@code -1.0} (full reverse) to {@code 1.0} (full forward).
     */
    void setSpeed(double speed);

    /**
     * Gets the motor object.
     * @return The motor object.
     */
    Motor<?> getMotor();

    /**
     * Sets the inversion state of the motor.
     * @param inverted {@code true}: invert the motor direction, {@code false}: use the default direction.
     */
    void setInverted(boolean inverted);

    /**
     * Gets the motor configuration.
     * @return The motor configuration.
     */
    MotorConfig<?> getConfig();

    /**
     * Applies the motor configuration.
     */
    void applyConfig();

    /**
     * Sets the break mode of the motor.
     * @param mode {@code true}: enable brake mode, {@code false}: disable brake mode.
     */
    void setBreak(boolean mode);

    /**
     * Gets the break mode of the motor.
     * @return {@code true}: brake mode is enabled, {@code false}: brake mode is disabled.
     */
    boolean getBreak();

    /**
     * Gets the current speed of the motor.
     * @return The current speed of the motor.
     */
    double getSpeed();

    /**
     * Gets the current draw of the motor.
     * @return The current draw of the motor.
     */
    double getCurrent();
    }
