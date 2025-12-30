package frc.robot.motors;

import com.revrobotics.spark.SparkLowLevel.MotorType;

import java.lang.reflect.Constructor;

public enum Motors implements BaseMotor {
    LEFT_FRONT(SparkMaxMotor.class, false, 1, MotorType.kBrushed),
    RIGHT_FRONT(SparkMaxMotor.class, true, 2, MotorType.kBrushed);

    public final BaseMotor motor;

    <T extends BaseMotor> Motors(Class<T> type, boolean inverted, Object... conArgs) {
        BaseMotor motorTmp = null;
        for (Constructor<?> constructor : type.getDeclaredConstructors()) {
            if (constructor.getParameterCount() == conArgs.length) {
                try {
                    motorTmp = (BaseMotor) constructor.newInstance(conArgs);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                break;
            }
        }
        motor = motorTmp;
        motor.setInverted(inverted);
    }

    @Override
    public void setSpeed(double speed) {
        motor.setSpeed(speed);
    }

    @Override
    public Motor<?> getMotor() {
        return motor.getMotor();
    }

    @Override
    public void setInverted(boolean inverted) {
        motor.setInverted(inverted);
    }

    @Override
    public MotorConfig<?> getConfig() {
        return motor.getConfig();
    }

    @Override
    public void applyConfig() {
        motor.applyConfig();
    }

    @Override
    public void setBreak(boolean mode) {
        motor.setBreak(mode);
    }

    @Override
    public boolean getBreak() {
        return motor.getBreak();
    }

    @Override
    public double getSpeed() {
        return motor.getSpeed();
    }

    @Override
    public double getCurrent() {
        return motor.getCurrent();
    }
}
