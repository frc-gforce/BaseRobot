package frc.robot.commands;

import frc.robot.motors.TalonBaseMotor;
import frc.robot.motors.TalonFXMotor;

public class Test extends GenericCommand {
    TalonBaseMotor motor1;
    TalonBaseMotor motor2;
    public Test() {
        motor1 = new TalonFXMotor(9);
        motor2 = new TalonFXMotor(11);
        motor2.follow(motor1, true);
    }

    @Override
    public void initialize() {
        motor1.setSpeed(0.5);
//        motor2.setSpeed(-0.5);
    }

    @Override
    public void end(boolean interrupted) {
        motor1.setSpeed(0);
//        motor2.setSpeed(0);
    }
}
