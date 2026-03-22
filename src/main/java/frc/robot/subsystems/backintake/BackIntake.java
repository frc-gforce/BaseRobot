package frc.robot.subsystems.backintake;

import frc.robot.motors.BaseMotor;
import frc.robot.subsystems.GenericSubsystem;
import org.littletonrobotics.junction.Logger;

@SuppressWarnings("rawtypes")
public class BackIntake extends GenericSubsystem {

    private final BaseMotor leftIntakeMotor;
    private final BaseMotor rightIntakeMotor;
    private final BaseMotor openIntakeMotor;

    public BackIntake(BackIntakeConstants constants, BaseMotor leftIntakeMotor, BaseMotor rightIntakeMotor, BaseMotor openIntakeMotor) {
        super(constants.logPath());
        this.leftIntakeMotor = leftIntakeMotor;
        this.rightIntakeMotor = rightIntakeMotor;
        this.openIntakeMotor = openIntakeMotor;

        leftIntakeMotor.follow(rightIntakeMotor, true);
    }

    public void openIntake() {
        openIntakeMotor.setSpeed(0.5);
    }

    public void stopOpenIntake() {
        openIntakeMotor.setSpeed(0);
    }

    public void intake() {
        rightIntakeMotor.setSpeed(0.5);
    }

    public void stopIntake() {
        rightIntakeMotor.setSpeed(0);
    }

    @Override
    protected void subsystemPeriodic() {
        Logger.recordOutput("OpenIntakeMotor", openIntakeMotor.getSpeed());
    }
}
