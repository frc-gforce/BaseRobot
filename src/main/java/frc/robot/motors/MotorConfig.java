package frc.robot.motors;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.revrobotics.spark.config.SparkMaxConfig;

/**
 * MotorConfig class represents a generic motor configuration with type parameter T.
 * @param <T> The type of motor configuration, such as {@link TalonFXConfiguration} or {@link SparkMaxConfig}.
 */
public class MotorConfig<T>{
    private final T value;

    public MotorConfig(T value) {
        this.value = value;
    }

    /**
     * Gets the motor configuration.
     * @return The motor configuration.
     */
    public T get() {
        return value;
    }
}
