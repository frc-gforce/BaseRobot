package frc.robot.motors;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.SlotConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.AngularVelocityUnit;
import edu.wpi.first.units.Measure;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.AngularVelocity;
import org.littletonrobotics.junction.Logger;

public class TalonFXMotor implements TalonBaseMotor {
    private final TalonFX motor;
    private final TalonFXConfiguration config;

    private final VelocityVoltage velocityVoltage;
    private final PositionVoltage positionVoltage;

    private final Slot0Configs slotConfigs;

    public TalonFXMotor(int id) {
        //TODO change CANBus to dynamic
        motor = new TalonFX(id);
        config = new TalonFXConfiguration();

        velocityVoltage = new VelocityVoltage(0).withSlot(0);
        positionVoltage = new PositionVoltage(0).withSlot(0);
        slotConfigs = new Slot0Configs();
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
        this.getMotor().setControl(new Follower(
                motor.getMotor().getDeviceID(),
                invert ? MotorAlignmentValue.Opposed : MotorAlignmentValue.Aligned
        ));
    }

    @Override
    public void setVelocity(AngularVelocity velocity) {
        Logger.recordOutput("setVelocity", velocity.in(Units.RotationsPerSecond));
        Logger.recordOutput("setVelocityRPM", velocity.in(Units.RPM));
        motor.setControl(velocityVoltage.withVelocity(velocity));
    }

    @Override
    public void setPosition(Rotation2d position) {
        motor.setControl(positionVoltage.withPosition(position.getMeasure()));
    }

    @Override
    public void stop() {
        Logger.recordOutput("setVelocity", 0);
        Logger.recordOutput("setVelocityRPM", 0);
        motor.stopMotor();
    }

    @Override
    public AngularVelocity getVelocity() {
        return motor.getVelocity().getValue();
    }

    @Override
    public Rotation2d getPosition() {
        return Rotation2d.fromRotations(
                motor.getPosition().getValue().in(Units.Rotations)
        );
    }

    @Override
    public void setPID(PID pid) {
        slotConfigs.kP = pid.p();
        slotConfigs.kI = pid.i();
        slotConfigs.kD = pid.d();
        slotConfigs.kA = pid.a();
        slotConfigs.kG = pid.g();
        slotConfigs.kS = pid.s();
        slotConfigs.kV = pid.v();

        applyConfig(config.withSlot0(slotConfigs));
    }
}
