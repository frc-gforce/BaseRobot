package frc.robot.subsystems;

import com.revrobotics.spark.config.SparkBaseConfig;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.motors.BaseMotor;
import frc.robot.motors.Motors;
import org.littletonrobotics.junction.Logger;

public class Drivetrain extends SubsystemBase {
    private final BaseMotor leftFront = Motors.LEFT_FRONT;
    private final BaseMotor rightFront = Motors.RIGHT_FRONT;

    private final DifferentialDrive drive = new DifferentialDrive(leftFront::setSpeed, rightFront::setSpeed);

    private final Field2d field = new Field2d();

    private double xMeters = 0.0;
    private double yMeters = 0.0;
    private double headingRad = 0.0;

    private double lastLeftCmd = 0.0;
    private double lastRightCmd = 0.0;

    public Drivetrain() {
        SmartDashboard.putData("Field", field);
    }

    public void tankDrive(double left, double right) {
        lastLeftCmd = left;
        lastRightCmd = right;

        drive.tankDrive(left, right);
    }

    public void arcadeDrive(double forward, double right) {
        drive.arcadeDrive(forward, right);
    }

    @Override
    public void periodic() {
        Logger.recordOutput("Motors/Output/LeftFrontSpeed", leftFront.getSpeed());
        Logger.recordOutput("Motors/Output/RightFrontSpeed", rightFront.getSpeed());
        double dt = 0.02;
        double maxLinearSpeed = 3.0;
        double trackWidth = 0.6;

        double vL = lastLeftCmd * maxLinearSpeed;
        double vR = lastRightCmd * maxLinearSpeed;

        double v = (vL + vR) / 2.0;
        double omega = (vR - vL) / trackWidth;

        headingRad += omega * dt;

        xMeters += v * Math.cos(headingRad) * dt;
        yMeters += v * Math.sin(headingRad) * dt;

        field.setRobotPose(new Pose2d(xMeters, yMeters, new Rotation2d(headingRad)));
    }
}
