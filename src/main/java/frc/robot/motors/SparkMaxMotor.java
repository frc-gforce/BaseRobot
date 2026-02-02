package frc.robot.motors;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;

public class SparkMaxMotor implements SparkBaseMotor {
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
    public SparkMax getMotor() {
        return motor;
    }

    @Override
    public void setInverted(boolean isInverted) {
        config.inverted(isInverted);
        applyConfig(getConfig());
    }

    @Override
    public SparkBaseConfig getConfig() {
        return config;
    }

    @Override
    public void applyConfig(SparkBaseConfig config) {
        motor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    @Override
    public void setBreak(boolean mode) {
        config.idleMode(mode ? SparkBaseConfig.IdleMode.kBrake : SparkBaseConfig.IdleMode.kCoast);
        applyConfig(getConfig());
    }

    @Override
    public boolean getBreak() {
        return motor.configAccessor.getIdleMode() == SparkBaseConfig.IdleMode.kBrake;
    }

    @Override
    public double getSpeed() {
        return motor.get();
    }

    @Override
    public double getCurrent() {
        return motor.getOutputCurrent();
    }

    @Override
    public void follow(BaseMotor<SparkMax, SparkBaseConfig> motor, boolean invert) {
        config.follow(motor.getMotor(), invert);
        applyConfig(config);
    }
}
