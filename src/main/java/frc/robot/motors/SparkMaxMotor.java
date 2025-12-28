package frc.robot.motors;

import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;

public class SparkMaxMotor {
    private final SparkMax motor;
    private final SparkBaseConfig config;

    public SparkMaxMotor(int id, MotorType type) {
        motor = new SparkMax(id, type);
        config = new SparkMaxConfig();
    }

    public void setSpeed(double speed) {
        motor.set(speed);
    }

    public SparkMax getMotor() {
        return motor;
    }

    public void setInverted(boolean isInverted) {
        config.inverted(isInverted);
    }

    public SparkBaseConfig getConfig() {
        return config;
    }

    public void applyConfig(SparkBaseConfig newConfig) {
        motor.configure(newConfig);
    }
}
