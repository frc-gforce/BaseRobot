package frc.robot.motors;

import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.spark.SparkMax;

/**
 * Motor class represents a generic motor controller with type parameter T.
 * @param <T> The type of motor controller, such as {@link TalonFX} or {@link SparkMax}.
 */
public class Motor<T>{
    private final T value;

    public Motor(T value) {
        this.value = value;
    }

    /**
     * Gets the motor controller.
     * @return The motor controller.
     */
    public T get() {
        return value;
    }
}
