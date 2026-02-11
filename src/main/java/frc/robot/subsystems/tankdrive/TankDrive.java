package frc.robot.subsystems.tankdrive;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.motors.BaseMotor;
import frc.robot.subsystems.GenericSubsystem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.littletonrobotics.junction.Logger;

import static java.util.Objects.*;

/**
 * Controls the drivetrain mechanisms.
 */
@SuppressWarnings("rawtypes")
public class TankDrive<T extends BaseMotor> extends GenericSubsystem {

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

    DifferentialDriveOdometry odometry;
    private Rotation2d gyroAngle = headingRotation;

    /**
     * Controls the drivetrain mechanisms.
     * @param constants The constants for the drivetrain
     * @param frontLeft The front left motor
     * @param frontRight The front right motor
     * @param backLeft The back left motor (optional)
     * @param backRight The back right motor (optional)
     */
    public TankDrive(TankDriveConstants constants, T frontLeft, T frontRight, @Nullable T backLeft, @Nullable T backRight) {
        super(constants.logPath());
        if (nonNull(backLeft) != nonNull(backRight)) {
            throw new IllegalArgumentException("Both back motors must be specified or neither.");
        }
        leftMotor = frontLeft;
        rightMotor = frontRight;
        if (nonNull(backLeft)) {
            follow(frontLeft, backLeft);
            follow(frontRight, backRight);
        }
        drive = new DifferentialDrive(leftMotor::setSpeed, rightMotor::setSpeed);
        SmartDashboard.putData("Field", field);

        odometry = new DifferentialDriveOdometry(headingRotation, leftMotor.getSpeed(), rightMotor.getSpeed());

    }

    /**
     * Controls the drivetrain mechanisms.
     * <br>
     * Back motors are set to null.
     * @param constants The constants for the drivetrain
     * @param frontLeft The front left motor
     * @param frontRight The front right motor
     */
    public TankDrive(TankDriveConstants constants, T frontLeft, T frontRight) {
        this(constants, frontLeft, frontRight, null, null);
    }

    /**
     * Drives the robot using tank controls.
     * @param left The left side speed
     * @param right The right side speed
     */
    public void tankDrive(double left, double right) {
        drive.tankDrive(left, right);
    }

    /**
     * Drives the robot using arcade controls.
     * @param forward The forward/backword speed
     * @param right The right/left turn speed
     */
    public void arcadeDrive(double forward, double right) {
        drive.arcadeDrive(forward, right);
    }

    @Override
    public void subsystemPeriodic() {
        Logger.recordOutput("Motors/Output/LeftFrontSpeed", leftMotor.getSpeed());
        Logger.recordOutput("Motors/Output/RightFrontSpeed", rightMotor.getSpeed());
        updatePose();
        gyroAngle = headingRotation;
        robotPose = odometry.update(gyroAngle, leftMotor.getSpeed(), rightMotor.getSpeed());
    }

    /**
     * Returns the robot's pose.
     * @return The robot's current pose.
     */
    public Pose2d getRobotPose() {
        return robotPose;
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

    // since every class that extends BaseMotor have matching types, we didn't specify them.
    // this makes the code more concise and readable, but giving some `unchecked` warnings.
    /**
     * Make the slave motor follow the master motor.
     * @param master The motor to follow.
     * @param slave The following motor.
     */
    @SuppressWarnings("unchecked")
    private void follow(T master, @NotNull T slave) {
        slave.follow(master, false);
    }
}
