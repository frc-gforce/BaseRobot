package frc.robot.subsystems.feed;

import frc.robot.motors.BaseMotor;
import frc.robot.subsystems.GenericSubsystem;
import org.littletonrobotics.junction.Logger;

/**
 *
 * @param <T> Motor type
 */
@SuppressWarnings("rawtypes")
public class Feed<T extends BaseMotor> extends GenericSubsystem {

    private final T motor;
    private final T conveyorMotor;
    private final FeedConstants constants;
    private Boolean motorActive = false;

    /**
     * Controls the feed mechanisms
     * @param constants The constants for the feed subsystem
     * @param motor The motor used in the feed subsystem
     */
    public Feed(FeedConstants constants, T motor, T conveyorMotor) {
        super(constants.logPath());
        this.motor = motor;
        this.constants = constants;
        this.conveyorMotor = conveyorMotor;
    }

    @Override
    protected void subsystemPeriodic() {
        Logger.recordOutput(constants.speedLogPath(), motor.getSpeed());
    }

    /**
     * Returns the motor of the feed's subsystem
     * @return The motor
     */
    public T getFeedMotor() {
        return motor;
    }

    public T getConveyorMotor() {
        return conveyorMotor;
    }

    /**
     * Stop the feed's motor.
     */
    public void stop() {
        motorActive = false;
        motor.setSpeed(0);
        conveyorMotor.setSpeed(0);
    }

    /**
     * Sets the conveyor motor and the feed motor to intake position
     */
    public void intake() {
        motor.setSpeed(0.2);
        conveyorMotor.setSpeed(0);
        motorActive = true;
    }

    /**
     * Sets the conveyor motor and the feed motor to shoot position
     */
    public void shoot(){
        motor.setSpeed(-0.2);
        conveyorMotor.setSpeed(1);
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
