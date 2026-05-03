// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.config.RobotConfig;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.*;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.*;
import frc.robot.controller.GenericCommandController;
import frc.robot.controller.XboxCommandController;
import frc.robot.motors.*;
import frc.robot.motors.TalonFXMotor;
import frc.robot.subsystems.backintake.BackIntake;
import frc.robot.subsystems.feed.Feed;
import frc.robot.subsystems.swervedrive.SwerveDriveSubsystem;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.intakexshooter.IntakeXShooter;
import frc.robot.utils.*;
import swervelib.SwerveInputStream;

import java.util.function.BooleanSupplier;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer
{
//    private final TankDrive<SparkBaseMotor> tankDrive;
    private final IntakeXShooter intakeXShooter;
    private final Feed feed;
    private final SwerveDriveSubsystem swerveDrive;
    private final BackIntake backIntake;


    private final Joystick driver = new Joystick(OperatorConstants.DRIVER_CONTROLLER_PORT);
    // The robot's subsystems and commands are defined here...
    private final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();

    // Replace with CommandPS4Controller or CommandJoystick if needed
    private final GenericCommandController driverController =
            new XboxCommandController(OperatorConstants.DRIVER_CONTROLLER_PORT);

    private final SendableChooser<Command> driverChooser = new SendableChooser<>();
    private SendableChooser<Command> autoChooser = new SendableChooser<>();

//    private final SparkMaxMotor frontLeftMotor;
//    private final SparkMaxMotor frontRightMotor;
//    private final SparkMaxMotor backLeftMotor;
//    private final SparkMaxMotor backRightMotor;

    private final TalonFXMotor shootMotor;
    private final SparkMaxMotor feedMotor;
    private final SparkMaxMotor conveyorMotor;

    private final TalonFXMotor rightIntakeMotor;
    private final TalonFXMotor leftIntakeMotor;
    private final SparkMaxMotor openIntakeMotor;

    private final Command shootCommand;
    private final Command intakeCommand;
    private final Command backIntakeCommand;
    private final Command openIntakeCommand;
    private final Command aimCommand;

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        //<editor-fold desc="Motors creation">
//        frontLeftMotor = MotorFactory.createSparkMotor(Motors.FRONT_LEFT);
//        frontRightMotor = MotorFactory.createSparkMotor(Motors.FRONT_RIGHT);
//        backLeftMotor = MotorFactory.createSparkMotor(Motors.BACK_LEFT);
//        backRightMotor = MotorFactory.createSparkMotor(Motors.BACK_RIGHT);

        shootMotor = MotorFactory.createTalonFXMotor(Motors.SHOOT);
        shootMotor.setPID(new PID(
                0.25,
                0,
                0,
                0.18,
                0.12,
                0,
                0
        ));
        feedMotor = MotorFactory.createSparkMotor(Motors.FEED);
        conveyorMotor = MotorFactory.createSparkMotor(Motors.CONVEYOR);


        rightIntakeMotor = MotorFactory.createTalonFXMotor(Motors.BACK_RIGHT_INTAKE);
        leftIntakeMotor = MotorFactory.createTalonFXMotor(Motors.BACK_LEFT_INTAKE);
        openIntakeMotor = MotorFactory.createSparkMotor(Motors.OPEN_INTAKE);

        //</editor-fold>



        //<editor-fold desc="Default motors configs">
        //TODO put this in a more convenient place
//        frontLeftMotor.setBreak(false);
//        frontRightMotor.setBreak(false);
        shootMotor.setBreak(false);
        feedMotor.setBreak(false);
        //</editor-fold>

        //<editor-fold desc="Subsystems creation">
        RobotConfig config = null;
        try {
            config = RobotConfig.fromGUISettings();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        tankDrive = new TankDrive<>(
//                DrivetrainConstantsFactory.createDrivetrainConstants(),
//                config,
//                frontLeftMotor,
//                frontRightMotor,
//                backLeftMotor,
//                backRightMotor
//        );
        intakeXShooter = new IntakeXShooter(
                IntakeXShooterConstantsFactory.createIntakeXShooterConstants(),
                shootMotor,
                0.5,
                0.05
        );
        feed = new Feed(FeedConstantsFactory.createConstants(), feedMotor, conveyorMotor);

        backIntake = new BackIntake(
                BackIntakeConstantsFactory.createConstants(),
                leftIntakeMotor,
                rightIntakeMotor,
                openIntakeMotor
        );
        //</editor-fold>

        swerveDrive = new SwerveDriveSubsystem();
        SwerveInputStream driveAngularInput = SwerveInputStream.of(swerveDrive.getSwerveDrive(),
                () -> driverController.getLeftY() * -1,
                () -> driverController.getLeftX() * -1)
                .withControllerRotationAxis(() -> driverController.getRightY() * -1)
                .deadband(0.1)
                .scaleTranslation(0.8)
                .allianceRelativeControl(true);
        swerveDrive.setDefaultCommand(swerveDrive.driveFieldOriented(driveAngularInput));
//        swerveDrive.setDefaultCommand(swerveDrive.driveCommand(
//                () -> -MathUtil.applyDeadband(driverController.getLeftY(), 0.1), // קדימה/אחורה
//                () -> -MathUtil.applyDeadband(driverController.getLeftX(), 0.1), // ימינה/שמאלה
//                () -> -MathUtil.applyDeadband(driverController.getRightX(), 0.1), // סיבוב
//                () -> -MathUtil.applyDeadband(driverController.getRightY(), 0.1) // סיבוב
//        ));

        //<editor-fold desc="Commands creation">
        GenericCommand tempCommand = new ShootCommand(intakeXShooter, feed, () -> swerveDrive.getPose().getTranslation(), FieldUtils::getHubPose);
        shootCommand = GenericSubsystemCommandFactory.getAsSubsystemsCommand(
                tempCommand,
                "Shoot Command"
        );
        tempCommand = new IntakeCommand(intakeXShooter, feed);
        intakeCommand = GenericSubsystemCommandFactory.getAsSubsystemsCommand(
                tempCommand,
                "Intake Command"
        );
        tempCommand = new BackIntakeCommand(backIntake);
        backIntakeCommand = GenericSubsystemCommandFactory.getAsSubsystemsCommand(
                tempCommand,
                "Back Intake Command"
        );
        tempCommand = new OpenIntakeCommand(backIntake);
        openIntakeCommand = GenericSubsystemCommandFactory.getAsSubsystemsCommand(
                tempCommand,
                "Open Intake Command"
        );
        tempCommand = new AimAtHubCommand(swerveDrive);
        aimCommand = GenericSubsystemCommandFactory.getAsSubsystemsCommand(
                tempCommand,
                "Aim Command"
        );
        //</editor-fold>

//        driverChooser.setDefaultOption("Arcade", new DriveArcadeCommand(tankDrive, driverController));
//        driverChooser.addOption("Tank", new DriveTankCommand(tankDrive, driverController));
//        SmartDashboard.putData("Drive Mode", driverChooser);
//        drivetrain.setDefaultCommand(driverChooser.getSelected());

        loadAutos();
        // Configure the trigger bindings
        configureBindings();
    }

    private void loadAutos() {
        Command autonomousShootCommand = GenericSubsystemCommandFactory.getAsSubsystemsCommand(
                new AutonomousShootCommand(intakeXShooter, feed, 10),
                "Auto Shoot Command");
        autoChooser.addOption("Bottom Ramp", Autos.bottomRampAuto(autonomousShootCommand, intakeCommand, swerveDrive.stop()));
        autoChooser.addOption("Bottom Trench", Autos.bottomTrenchAuto(autonomousShootCommand, intakeCommand));
        autoChooser.addOption("Upper Ramp", Autos.upperRampAuto(autonomousShootCommand, intakeCommand));
        autoChooser.addOption("Upper Trench", Autos.upperTrenchAuto(autonomousShootCommand, intakeCommand));
        autoChooser.addOption("Test", Autos.testAuto());

        SmartDashboard.putData("Auto", autoChooser);
    }


    /**
     * Use this method to define your trigger->command mappings. Triggers can be created via the
     * {@link Trigger#Trigger(BooleanSupplier)} constructor with an arbitrary
     * predicate, or via the named factories in {@link
     * CommandGenericHID}'s subclasses for {@link
     * CommandXboxController Xbox}/{@link CommandPS4Controller
     * PS4} controllers or {@link CommandJoystick Flight
     * joysticks}.
     */
    private void configureBindings() {
        // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
        new Trigger(exampleSubsystem::exampleCondition)
                .onTrue(new ExampleCommand(exampleSubsystem));

        // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
        // cancelling on release.
//        driverController.b().whileTrue(exampleSubsystem.exampleMethodCommand());
//        driverController.faceLeft().onTrue(new InstantCommand(this::switchBreak));
        driverController.faceLeft().whileTrue(backIntakeCommand);
        driverController.faceRight().whileTrue(new SequentialCommandGroup(aimCommand.onlyIf(() -> FieldUtils.isInAllianceSide(swerveDrive.getPose())), shootCommand));
        driverController.faceUp().whileTrue(intakeCommand);
        driverController.faceDown().whileTrue(openIntakeCommand);

        //sets the gyro to zero
        //driverController.y().onTrue(new InstantCommand(swerveDrive::zeroGyro));
        //TODO make to gyro go to zero
    }

//    private void switchBreak() {
//        boolean breakState = frontLeftMotor.getBreak();
//        frontLeftMotor.setBreak(!breakState);
//        frontRightMotor.setBreak(!breakState);
//        backLeftMotor.setBreak(!breakState);
//        backRightMotor.setBreak(!breakState);
//    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An example command will be run in autonomous
//        return Autos.exampleAuto(exampleSubsystem);
        return autoChooser.getSelected();
    }

    public Command getDriveCommand() {
//        return swerveDrive.getDefaultCommand();
        return driverChooser.getSelected();
    }

    public void resetOdometry() {
        swerveDrive.resetOdometry(LimelightUtils.getPose2d());
    }
}
