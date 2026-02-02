package frc.robot.subsystems.feed;

import frc.robot.motors.BaseMotor;
import frc.robot.subsystems.GenericSubsystem;
import org.littletonrobotics.junction.Logger;


public class Feed extends GenericSubsystem {

    private final BaseMotor motor;
    private final FeedConstants constants;
    private Boolean motorActive = false;

    /**
     * Controls the feed mechanisms
     * @param constants The constants for the feed subsystem
     * @param motor The motor used in the feed subsystem
     */
    public Feed(FeedConstants constants, BaseMotor<?, ?> motor) {
        super(constants.logPath());
        this.motor = motor;
        this.constants = constants;
    }

    @Override
    protected void subsystemPeriodic() {
        Logger.recordOutput(constants.speedLogPath(), motor.getSpeed());
    }

    /**
     * Returns the motor of the feed's subsystem
     * @return The motor
     */
    public BaseMotor getMotor() {
        return motor;
    }

    /**
     * Stop the feed's motor.
     */
    public void stop() {
        motorActive = false;
        motor.setSpeed(0);
    }

    /**
     * Sets the speed of the motor for the feed subsystem
     * @param speed the intended speed of the motor
     */
    public void setSpeed(int speed) {
        motor.setSpeed(speed);
        motorActive = true;
    }

    /**
     * Returns the speed to the feed motor
     * @return motor speed
     */
    public double getSpeed() {
        return motor.getSpeed();
    }


    /***
     * Toggle the activation of the motor.
     * The function flips its mode.
     */
    public void toggleActivation() {
        motorActive = !motorActive;
        if(motorActive) {
            motor.setSpeed(1);
        }else{
            motor.setSpeed(0);
        }
    }


    /**
     * Gets the intended mode of the motor (true - on, false - off).
     * @param mode The intended mode of the motor
     */
    public void toggleActivation(Boolean mode){
        motorActive = mode;
        if(motorActive) {
            motor.setSpeed(1);
        }
        else {
            motor.setSpeed(0);
        }
    }

}
