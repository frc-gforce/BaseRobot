package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.motors.BaseMotor;
import org.littletonrobotics.junction.Logger;


public class Drivetrain extends SubsystemBase {

    private final BaseMotor leftMotor;
    private final BaseMotor rightMotor;

    private final DifferentialDrive drive;

    private final Field2d field = new Field2d();

    private Pose2d robotPose = new Pose2d(1.0, 2.0, new Rotation2d());

    // The X robot pose by meters
    private double xMeters = 0.0;
    // The Y robot pose by meters
    private double yMeters = 0.0;
    private Rotation2d headingRotation = Rotation2d.fromRadians(0.0);

    public Drivetrain(BaseMotor<?, ?> left, BaseMotor<?, ?> right) {
        leftMotor = left;
        rightMotor = right;
        drive = new DifferentialDrive(leftMotor::setSpeed, rightMotor::setSpeed);
        SmartDashboard.putData("Field", field);
    }

    public void tankDrive(double left, double right) {

        drive.tankDrive(left, right);
    }

    public void arcadeDrive(double forward, double right) {
        drive.arcadeDrive(forward, right);
    }

    @Override
    public void periodic() {
        Logger.recordOutput("Motors/Output/LeftFrontSpeed", leftMotor.getSpeed());
        Logger.recordOutput("Motors/Output/RightFrontSpeed", rightMotor.getSpeed());
        updatePose();
    }

    public Pose2d getRobotPose() {
        return robotPose;
    }

    public String getLogPath() {
        return "Robot/Drivetrain";
    }

    /**
     * Updates the robot's pose based on motor speeds and time step.
     */
    private void updatePose() {
        double lSpeed = leftMotor.getSpeed();
        double rSpeed = rightMotor.getSpeed();

        double dt = 0.02;
        double maxLinearSpeed = 3.0;
        double trackWidth = 0.6;

        double vL = lSpeed * maxLinearSpeed;
        double vR = rSpeed * maxLinearSpeed;

        double v = (vL + vR) / 2.0;
        double omega = (vR - vL) / trackWidth;

        headingRotation = headingRotation.plus(Rotation2d.fromRadians(omega * dt));

        xMeters += v * Math.cos(headingRotation.getRadians()) * dt;
        yMeters += v * Math.sin(headingRotation.getRadians()) * dt;

        robotPose = new Pose2d(xMeters, yMeters, headingRotation);

        field.setRobotPose(robotPose);
        Logger.recordOutput(getLogPath() + "/Pose", robotPose);
    }

    @Override
    public void simulationPeriodic() {
        Logger.recordOutput(getLogPath() + "/Angle", headingRotation);
    }
}
