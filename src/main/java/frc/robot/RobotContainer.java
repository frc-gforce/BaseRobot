// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.spark.SparkLowLevel;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.DriveArcadeCommand;
import frc.robot.commands.DriveTankCommand;
import frc.robot.commands.ExampleCommand;
import frc.robot.motors.BaseMotor;
import frc.robot.motors.Motors;
import frc.robot.motors.SparkBaseMotor;
import frc.robot.motors.SparkMaxMotor;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;



/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer
{
    private final Drivetrain drivetrain;
    private final Joystick driver = new Joystick(OperatorConstants.DRIVER_CONTROLLER_PORT);
    // The robot's subsystems and commands are defined here...
    private final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
    
    // Replace with CommandPS4Controller or CommandJoystick if needed
    private final CommandXboxController driverController =
            new CommandXboxController(OperatorConstants.DRIVER_CONTROLLER_PORT);

    private final SendableChooser<Command> driverChooser = new SendableChooser<>();

    private final SparkBaseMotor frontLeftMotor;
    private final SparkBaseMotor frontRightMotor;
    
    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer()
    {
        // Configure the trigger bindings
        configureBindings();
        frontLeftMotor = new SparkMaxMotor(Motors.FRONT_LEFT.id, Motors.FRONT_LEFT.type);
        frontRightMotor = new SparkMaxMotor(Motors.FRONT_RIGHT.id, Motors.FRONT_RIGHT.type);
        frontLeftMotor.setInverted(true);
        frontRightMotor.setInverted(false);
        frontLeftMotor.setBreak(false);
        frontRightMotor.setBreak(false);
        drivetrain = new Drivetrain(frontLeftMotor, frontRightMotor);
        driverChooser.setDefaultOption("Arcade", new DriveArcadeCommand(drivetrain, driverController));
        driverChooser.addOption("Tank", new DriveTankCommand(drivetrain, driverController));
        SmartDashboard.putData("Drive Mode", driverChooser);
//        drivetrain.setDefaultCommand(driverChooser.getSelected());
    }
    
    
    /**
     * Use this method to define your trigger->command mappings. Triggers can be created via the
     * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
     * predicate, or via the named factories in {@link
     * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
     * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
     * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
     * joysticks}.
     */
    private void configureBindings()
    {
        // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
        new Trigger(exampleSubsystem::exampleCondition)
                .onTrue(new ExampleCommand(exampleSubsystem));
        
        // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
        // cancelling on release.
        driverController.b().whileTrue(exampleSubsystem.exampleMethodCommand());
    }

    private void switchBreak() {
        frontLeftMotor.setBreak(!frontLeftMotor.getBreak());
        frontRightMotor.setBreak(!frontRightMotor.getBreak());
    }

    public void pressButtons(MotorControlDrive motor) {
        System.out.println("Press buttons on driver controller to drive");
        driverController.a().onTrue(new InstantCommand(() -> motor.drive(0.5)));
        driverController.x().onTrue(new InstantCommand(() -> motor.drive(-0.2)));
        driverController.b().onTrue(new InstantCommand(motor::invertDirections));
        driverController.y().onTrue(new InstantCommand(motor::stop));
    }
    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand()
    {
        // An example command will be run in autonomous
        return Autos.exampleAuto(exampleSubsystem);
    }

    public Command getDriveCommand() {
        return driverChooser.getSelected();
    }
}
