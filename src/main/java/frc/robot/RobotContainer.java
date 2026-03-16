// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.hardware.TalonFX;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.config.RobotConfig;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.*;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.*;
import frc.robot.controller.GenericCommandController;
import frc.robot.controller.PS4CommandController;
import frc.robot.controller.XboxCommandController;
import frc.robot.motors.*;
import frc.robot.subsystems.feed.Feed;
import frc.robot.subsystems.swervedrive.SwerveDriveSubsystem;
import frc.robot.subsystems.tankdrive.TankDrive;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.intakexshooter.IntakeXShooter;
import frc.robot.utils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
    private final TankDrive<SparkBaseMotor> tankDrive;
    private final IntakeXShooter intakeXShooter;
    private final Feed feed;
    private final SwerveDriveSubsystem swerveDrive;


    private final Joystick driver = new Joystick(OperatorConstants.DRIVER_CONTROLLER_PORT);
    // The robot's subsystems and commands are defined here...
    private final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();

    // Replace with CommandPS4Controller or CommandJoystick if needed
    private final GenericCommandController driverController =
            new XboxCommandController(OperatorConstants.DRIVER_CONTROLLER_PORT);

    private final SendableChooser<Command> driverChooser = new SendableChooser<>();
    private SendableChooser<PathPlannerAuto> pathChooser = new SendableChooser<>();

    private final SparkMaxMotor frontLeftMotor;
    private final SparkMaxMotor frontRightMotor;
    private final SparkMaxMotor backLeftMotor;
    private final SparkMaxMotor backRightMotor;

    private final TalonFXMotor shootMotor;
    private final SparkMaxMotor feedMotor;

    private final Command shootCommand;
    private final Command intakeCommand;

    private List<PathPlannerAuto> pathPlannerAutos = new ArrayList<>();


    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        //<editor-fold desc="Motors creation">
        frontLeftMotor = MotorFactory.createSparkMotor(Motors.FRONT_LEFT);
        frontRightMotor = MotorFactory.createSparkMotor(Motors.FRONT_RIGHT);
        backLeftMotor = MotorFactory.createSparkMotor(Motors.BACK_LEFT);
        backRightMotor = MotorFactory.createSparkMotor(Motors.BACK_RIGHT);

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
        //</editor-fold>

        //<editor-fold desc="Default motors configs">
        //TODO put this in a more convenient place
        frontLeftMotor.setBreak(false);
        frontRightMotor.setBreak(false);
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
        tankDrive = new TankDrive<>(
                DrivetrainConstantsFactory.createDrivetrainConstants(),
                config,
                frontLeftMotor,
                frontRightMotor,
                backLeftMotor,
                backRightMotor
        );
        intakeXShooter = new IntakeXShooter(
                IntakeXShooterConstantsFactory.createIntakeXShooterConstants(),
                shootMotor,
                0.5,
                0.05
        );
        feed = new Feed(FeedConstantsFactory.createConstants(), feedMotor);
        //</editor-fold>

        swerveDrive = new SwerveDriveSubsystem();
        swerveDrive.setDefaultCommand(swerveDrive.driveCommand(
                () -> -MathUtil.applyDeadband(driverController.getLeftY(), 0.1), // קדימה/אחורה
                () -> -MathUtil.applyDeadband(driverController.getLeftX(), 0.1), // ימינה/שמאלה
                () -> -MathUtil.applyDeadband(driverController.getRightX(), 0.1) // סיבוב
        ));

        //<editor-fold desc="Commands creation">
        GenericCommand tempCommand = new ShootCommand(intakeXShooter, feed);
        shootCommand = GenericSubsystemCommandFactory.getAsSubsystemsCommand(
                tempCommand,
                "Shoot Command"
        );
        tempCommand = new IntakeCommand(intakeXShooter, feed);
        intakeCommand = GenericSubsystemCommandFactory.getAsSubsystemsCommand(
                tempCommand,
                "Intake Command"
        );
        //</editor-fold>

        driverChooser.setDefaultOption("Arcade", new DriveArcadeCommand(tankDrive, driverController));
        driverChooser.addOption("Tank", new DriveTankCommand(tankDrive, driverController));
        SmartDashboard.putData("Drive Mode", driverChooser);
//        drivetrain.setDefaultCommand(driverChooser.getSelected());

        loadAutos();
        // Configure the trigger bindings
        configureBindings();
    }

    private void loadAutos() {
        for (PathPlannerAutos auto : PathPlannerAutos.values()) {
            pathPlannerAutos.add(new PathPlannerAuto(auto.getName()));
        }
        pathChooser.setDefaultOption("None", null);
        for (PathPlannerAuto auto : pathPlannerAutos) {
            pathChooser.addOption(auto.getName(), auto);
        }
        SmartDashboard.putData("Path Planner Auto", pathChooser);
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
        driverController.faceLeft().onTrue(new InstantCommand(this::switchBreak));
        driverController.faceRight().whileTrue(shootCommand);
        driverController.faceUp().whileTrue(intakeCommand);
//        driverController.faceDown().whileTrue(swerveDrive.driveForward());

        //sets the gyro to zero
        //driverController.y().onTrue(new InstantCommand(swerveDrive::zeroGyro));
        //TODO make to gyro go to zero
    }

    private void switchBreak() {
        boolean breakState = frontLeftMotor.getBreak();
        frontLeftMotor.setBreak(!breakState);
        frontRightMotor.setBreak(!breakState);
        backLeftMotor.setBreak(!breakState);
        backRightMotor.setBreak(!breakState);
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An example command will be run in autonomous
//        return Autos.exampleAuto(exampleSubsystem);
        return pathChooser.getSelected();
    }

    public Command getDriveCommand() {
//        return swerveDrive.getDefaultCommand();
        return driverChooser.getSelected();
    }
}
