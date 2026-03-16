// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.subsystems.ExampleSubsystem;


public final class Autos {
    /**
     * Example static factory for an autonomous command.
     */
    public static Command exampleAuto(ExampleSubsystem subsystem) {
        return Commands.sequence(subsystem.exampleMethodCommand(), new ExampleCommand(subsystem));
    }

    public static Command testAuto() {
        PathPlannerPath path = null;
        try {
            path = PathPlannerPath.fromPathFile("Test Path");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AutoBuilder.followPath(path);
    }

    public static Command bottomRampAuto(Command shootCommand, Command intakeCommand, Command stopCommand) {
        PathPlannerPath path1 = null;
        PathPlannerPath path2 = null;
        try {
            path1 = PathPlannerPath.fromPathFile("bottom-ramp to middle");
            //TODO create a new path for shooting pose
            path2 = PathPlannerPath.fromPathFile("bottom-ramp to shoot");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //parallel command
        return new SequentialCommandGroup(
                //shootCommand
                new ParallelRaceGroup(
                        AutoBuilder.followPath(path1),
                        //Open intake
                        intakeCommand.asProxy()
                ),
                AutoBuilder.followPath(path2),
                stopCommand,
                shootCommand.asProxy()
        );
    }

    public static Command bottomTrenchAuto(Command shootCommand, Command intakeCommand) {
        PathPlannerPath path1 = null;
        PathPlannerPath path2 = null;
        try {
            path1 = PathPlannerPath.fromPathFile("bottom-trench to middle");
            //TODO create a new path for shooting pose
            path2 = PathPlannerPath.fromPathFile("bottom-trench to shoot");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //parallel command
        return new SequentialCommandGroup(
                //shootCommand
                new ParallelRaceGroup(
                        AutoBuilder.followPath(path1),
                        //Open intake
                        intakeCommand.asProxy()
                ),
                AutoBuilder.followPath(path2),
                shootCommand.asProxy()
        );
    }

    public static Command upperRampAuto(Command shootCommand, Command intakeCommand) {
        PathPlannerPath path1 = null;
        PathPlannerPath path2 = null;
        try {
            path1 = PathPlannerPath.fromPathFile("bottom-ramp to middle").mirrorPath();
            //TODO create a new path for shooting pose
            path2 = PathPlannerPath.fromPathFile("bottom-ramp to shoot").mirrorPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //parallel command
        return new SequentialCommandGroup(
                //shootCommand
                new ParallelRaceGroup(
                        AutoBuilder.followPath(path1),
                        //Open intake
                        intakeCommand.asProxy()
                ),
                AutoBuilder.followPath(path2),
                shootCommand.asProxy()
        );
    }

    public static Command upperTrenchAuto(Command shootCommand, Command intakeCommand) {
        PathPlannerPath path1 = null;
        PathPlannerPath path2 = null;
        try {
            path1 = PathPlannerPath.fromPathFile("bottom-trench to middle").mirrorPath();
            //TODO create a new path for shooting pose
            path2 = PathPlannerPath.fromPathFile("bottom-trench to shoot").mirrorPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //parallel command
        return new SequentialCommandGroup(
                //shootCommand
                new ParallelRaceGroup(
                        AutoBuilder.followPath(path1),
                        //Open intake
                        intakeCommand.asProxy()
                ),
                AutoBuilder.followPath(path2),
                shootCommand.asProxy()
        );
    }

    private Autos() {
        throw new UnsupportedOperationException("This is a utility class!");
    }
}
