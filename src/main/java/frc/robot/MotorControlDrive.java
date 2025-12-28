package frc.robot;

import com.revrobotics.REVLibError;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SoftLimitConfig;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.math.geometry.Rotation2d;
import org.littletonrobotics.junction.Logger;

public class MotorControlDrive {
    private final SparkMax motor;
    private SparkBaseConfig config;
    private SoftLimitConfig limitConfig;

    public MotorControlDrive(int id) {
        motor = new SparkMax(id, SparkMax.MotorType.kBrushed);
        config = new SparkMaxConfig();
        limitConfig = new SoftLimitConfig();
        configure(config);
        resetPosition();
    }

    private void configure(SparkBaseConfig config) {
        motor.configure(config, SparkBase.ResetMode.kNoResetSafeParameters, SparkBase.PersistMode.kNoPersistParameters);
    }

    public void drive(double speed) {
        assert -1 <= speed && speed <= 1 : "Speed must be between -1 and 1";
        System.out.println(speed);
        motor.set(speed);
    }

    public void stop() {
        motor.stopMotor();
    }

    public void setInverted(boolean isInverted) {
        config = config.inverted(isInverted);
        configure(config);
    }

    public boolean isInverted() {
        return motor.configAccessor.getInverted();
    }

    public void invertDirections() {
        config = config.inverted(!isInverted());
        configure(config);
    }

    public Rotation2d getPosition() {
        return Rotation2d.fromRotations(motor.getEncoder().getPosition());
    }

    public double getSpeed() {
        return motor.getEncoder().getVelocity();
    }

    public double getCurrent() {
        return motor.getOutputCurrent();
    }

    public double getVoltage() {
        return motor.getBusVoltage();
    }

    public void logStats() {
        Logger.recordOutput("Motor/Position", getPosition());
        Logger.recordOutput("Motor/Speed", getSpeed());
        Logger.recordOutput("Motor/Current", getCurrent());
        Logger.recordOutput("Motor/Voltage", getVoltage());
    }

    public void setForwardLimit(double limit) {
        limitConfig = limitConfig.forwardSoftLimitEnabled(true)
                .forwardSoftLimit(limit);
        config.apply(limitConfig);
        configure(config);
    }

    public void setReverseLimit(double limit) {
        limitConfig = limitConfig.reverseSoftLimitEnabled(true)
                .reverseSoftLimit(limit);
        config.apply(limitConfig);
        configure(config);
    }

    public void setCurrentLimit(double limit) {
        config.smartCurrentLimit((int) limit);
        configure(config);
    }

    public void changeNeutralMode(IdleMode mode) {
        config.idleMode(mode);
        configure(config);
    }

    public void resetPosition() {
        motor.getEncoder().setPosition(0);
    }

    public void disableCurrentLimit() {
        setCurrentLimit(Integer.MAX_VALUE);
    }

    public void disableForwardLimit() {
        limitConfig = limitConfig.forwardSoftLimitEnabled(false);
        config.apply(limitConfig);
        configure(config);

    }

    public void disableReverseLimit() {
        limitConfig = limitConfig.reverseSoftLimitEnabled(false);
        config.apply(limitConfig);
        configure(config);
    }
}
