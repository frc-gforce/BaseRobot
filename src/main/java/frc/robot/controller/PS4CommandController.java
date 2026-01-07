package frc.robot.controller;

import edu.wpi.first.wpilibj.event.EventLoop;
import edu.wpi.first.wpilibj2.command.button.CommandPS4Controller;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class PS4CommandController extends CommandPS4Controller implements GenericCommandController {
    /**
     * Construct an instance of a device.
     *
     * @param port The port index on the Driver Station that the device is plugged into.
     */
    public PS4CommandController(int port) {
        super(port);
    }

    //<editor-fold desc="Face buttons">
    @Override
    public Trigger faceDown() {
        return super.cross();
    }

    @Override
    public Trigger faceDown(EventLoop loop) {
        return super.cross(loop);
    }

    @Override
    public Trigger faceLeft() {
        return super.circle();
    }

    @Override
    public Trigger faceLeft(EventLoop loop) {
        return super.circle(loop);
    }

    @Override
    public Trigger faceUp() {
        return super.triangle();
    }

    @Override
    public Trigger faceUp(EventLoop loop) {
        return super.triangle(loop);
    }

    @Override
    public Trigger faceRight() {
        return super.square();
    }

    @Override
    public Trigger faceRight(EventLoop loop) {
        return super.square(loop);
    }
    //</editor-fold>
}
