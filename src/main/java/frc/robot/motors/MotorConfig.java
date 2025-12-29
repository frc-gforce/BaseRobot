package frc.robot.motors;

public class MotorConfig<T>{
    private final T value;

    public MotorConfig(T value) {
        this.value = value;
    }
    public T get() {
        return value;
    }
}
