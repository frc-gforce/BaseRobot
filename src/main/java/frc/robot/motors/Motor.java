package frc.robot.motors;

public class Motor<T>{
    private T value;

    public Motor(T value) {
        this.value = value;
    }
    public T get() {
        return value;
    }
}
