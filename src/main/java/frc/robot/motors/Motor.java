package frc.robot.motors;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;

public class Motor<T>{
    private final T value;

    public Motor(T value) {
        this.value = value;
    }
    public T get() {
        return value;
    }
}
