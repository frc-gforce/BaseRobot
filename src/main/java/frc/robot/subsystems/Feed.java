package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.motors.BaseMotor;


public class Feed extends SubsystemBase {

    private final BaseMotor motor;
    private Boolean motorActive = false;


    public Feed(BaseMotor<?, ?> motor) {
        this.motor = motor;
    }


    public BaseMotor getMotor() {
        return motor;
    }

    public void turnOff() {
        motorActive = false;
        motor.setSpeed(0);     //
    }

    public void setSpeed(int speed) {
        motor.setSpeed(speed);
        motorActive = true;
    }

    public double getSpeed() {
        return motor.getSpeed();
    }

    public void setActivation() {
        motorActive = !motorActive;
    }


    public void setActivation(Boolean mode){
        motorActive = mode;
    }

}
