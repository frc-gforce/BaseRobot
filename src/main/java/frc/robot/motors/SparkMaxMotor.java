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
    private double currentLimit = 0;

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
    public void setCurrentLimit(double limit) {
        config.smartCurrentLimit((int) limit);
        currentLimit = limit;
        applyConfig(config);
    }

    @Override
    public double getCurrentLimit() {
        return currentLimit;
    }

    @Override
    public void toggleCurrentLimit() {
        if(currentLimit == 0){
            config.smartCurrentLimit(40);
            currentLimit = 40;
        }else{
            config.smartCurrentLimit(0);
            currentLimit = 0;
        }
        applyConfig(config);
    }

    @Override
    public void toggleCurrentLimit(boolean mode) {
        if(mode){
            config.smartCurrentLimit(40);
            currentLimit = 40;
        }else{
            config.smartCurrentLimit(0);
            currentLimit = 0;
        }
        applyConfig(config);
    }
}
