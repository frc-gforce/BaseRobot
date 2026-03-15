package frc.robot.motors;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.AngularVelocityUnit;
import edu.wpi.first.units.Measure;
import edu.wpi.first.units.measure.AngularVelocity;

public interface BrushlessMotor<T, CT> extends BaseMotor<T, CT>{
    void setVelocity(AngularVelocity velocity);
    void setPosition(Rotation2d position);
    void stop();

    AngularVelocity getVelocity();
    Rotation2d getPosition();

    void setPID(PID pid);
}
