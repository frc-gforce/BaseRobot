package frc.robot.motors;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

public class TalonFXMotor implements TalonBaseMotor {
    private final TalonFX motor;
    private final TalonFXConfiguration config;

    public TalonFXMotor(int id) {
        //TODO change CANBus to dynamic
        motor = new TalonFX(id);
        config = new TalonFXConfiguration();
    }
    @Override
    public void setSpeed(double speed) {
        motor.set(speed);
    }

    @Override
    public TalonFX getMotor() {
        return motor;
    }

    @Override
    public void setInverted(boolean inverted) {
        config.MotorOutput.Inverted = inverted ? InvertedValue.Clockwise_Positive : InvertedValue.CounterClockwise_Positive;
        applyConfig(getConfig());
    }

    @Override
    public TalonFXConfiguration getConfig() {
        return config;
    }

    @Override
    public void applyConfig(TalonFXConfiguration config) {
        motor.getConfigurator().apply(config);
    }

    @Override
    public void setBreak(boolean mode) {
        motor.setNeutralMode(mode ? NeutralModeValue.Brake : NeutralModeValue.Coast);
    }

    @Override
    public boolean getBreak() {
        motor.getConfigurator().refresh(config);
        return config.MotorOutput.NeutralMode == NeutralModeValue.Brake;
    }

    @Override
    public double getSpeed() {
        return motor.get();
    }

    @Override
    public double getCurrent() {
        return motor.getTorqueCurrent().getValueAsDouble();
    }

    @Override
    public void follow(BaseMotor<TalonFX, TalonFXConfiguration> motor, boolean invert) {
        motor.getMotor().setControl(new Follower(
                this.getMotor().getDeviceID(),
                invert ? MotorAlignmentValue.Opposed : MotorAlignmentValue.Aligned
        ));
    }
}
