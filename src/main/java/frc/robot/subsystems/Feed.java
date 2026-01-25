package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.motors.BaseMotor;


public class Feed extends SubsystemBase {

    private final BaseMotor motor;
    private Boolean motorActive = false;

    /**
     * Controls the feed mechanisms
     * @param motor The motor used in the feed subsystem
     */
    public Feed(BaseMotor<?, ?> motor) {
        this.motor = motor;
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
