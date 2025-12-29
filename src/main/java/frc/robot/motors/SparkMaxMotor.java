package frc.robot.motors;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;

public class SparkMaxMotor implements BaseMotor {
    private final SparkMax motor;
    private final SparkBaseConfig config;

    public SparkMaxMotor(int id, MotorType type) {
        motor = new SparkMax(id, type);
        config = new SparkMaxConfig();
    }

    @Override
    public void setSpeed(double speed) {
        motor.set(speed);
    }

    @Override
    public Motor<SparkMax> getMotor() {
        return new Motor<>(motor);
    }

    @Override
    public void setInverted(boolean isInverted) {
        config.inverted(isInverted);
        applyConfig();
    }

    @Override
    public MotorConfig<SparkBaseConfig> getConfig() {
        return new MotorConfig<>(config);
    }

    @Override
    public void applyConfig() {
        motor.configure(config, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters);
    }

    @Override
    public void setBreak(boolean mode) {
        config.idleMode(mode ? SparkBaseConfig.IdleMode.kBrake : SparkBaseConfig.IdleMode.kCoast);
        applyConfig();
    }
}
