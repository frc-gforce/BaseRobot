package frc.robot.controller;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.event.EventLoop;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public interface GenericCommandController {
    //<editor-fold desc="CommandGenericHID">
    GenericHID getHID();

    Trigger button(int button);
    Trigger button(int button, EventLoop loop);

    Trigger pov(int angle);
    Trigger pov(int pov, int angle, EventLoop loop);

    Trigger povUp();
    Trigger povUpRight();
    Trigger povRight();
    Trigger povDownRight();
    Trigger povDown();
    Trigger povDownLeft();
    Trigger povLeft();
    Trigger povUpLeft();
    Trigger povCenter();

    Trigger axisLessThan(int axis, double threshold);
    Trigger axisLessThan(int axis, double threshold, EventLoop loop);

    Trigger axisGreaterThan(int axis, double threshold);
    Trigger axisGreaterThan(int axis, double threshold, EventLoop loop);

    Trigger axisMagnitudeGreaterThan(int axis, double threshold);
    Trigger axisMagnitudeGreaterThan(int axis, double threshold, EventLoop loop);

    double getRawAxis(int axis);

    void setRumble(GenericHID.RumbleType type, double value);

    boolean isConnected();
    //</editor-fold>

    //<editor-fold desc="Face buttons">
    Trigger faceDown();
    Trigger faceDown(EventLoop loop);

    Trigger faceLeft();
    Trigger faceLeft(EventLoop loop);

    Trigger faceUp();
    Trigger faceUp(EventLoop loop);

    Trigger faceRight();
    Trigger faceRight(EventLoop loop);
    //</editor-fold>
}
