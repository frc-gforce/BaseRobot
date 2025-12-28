package frc.robot.motors;

public interface BaseMotor {
    void setSpeed(double speed);
    MotorConfig<?> getMotor();
    void setInverted();
    //getConfig();
    void applyConfig();
    }
