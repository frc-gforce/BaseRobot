package frc.robot.motors;

public interface BaseMotor {
    void setSpeed(double speed);
    Motor<?> getMotor();
    void setInverted(boolean inverted);
    MotorConfig<?> getConfig();
    void applyConfig();
    void setBreak(boolean mode);
    boolean getBreak();
    }
