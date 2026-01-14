package frc.robot.controller;

import edu.wpi.first.wpilibj.event.EventLoop;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class XboxCommandController extends CommandXboxController implements GenericCommandController {

    /**
     * Construct an instance of a device.
     *
     * @param port The port index on the Driver Station that the device is plugged into.
     */
    public XboxCommandController(int port) {
        super(port);
    }


    //<editor-fold desc="Face buttons">
    @Override
    public Trigger faceDown() {
        return super.a();
    }

    @Override
    public Trigger faceDown(EventLoop loop) {
        return super.a(loop);
    }

    @Override
    public Trigger faceLeft() {
        return super.b();
    }

    @Override
    public Trigger faceLeft(EventLoop loop) {
        return super.b(loop);
    }

    @Override
    public Trigger faceUp() {
        return super.y();
    }

    @Override
    public Trigger faceUp(EventLoop loop) {
        return super.y(loop);
    }

    @Override
    public Trigger faceRight() {
        return super.x();
    }

    @Override
    public Trigger faceRight(EventLoop loop) {
        return super.x(loop);
    }
    //</editor-fold>
}
