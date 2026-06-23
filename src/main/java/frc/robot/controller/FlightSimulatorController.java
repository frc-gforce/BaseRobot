package frc.robot.controller;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class FlightSimulatorController extends CommandXboxController {
    /**
     * Construct an instance of a controller.
     *
     * @param port The port index on the Driver Station that the controller is plugged into (0-5).
     */
    public FlightSimulatorController(int port) {
        super(port);
    }

    public double getThrottle() {
        return getRawAxis(Axis.Throttle.value);
    }

    public double getJoystickX() {
        return getRawAxis(Axis.JoystickX.value);
    }

    public double getJoystickY() {
        return getRawAxis(Axis.JoystickY.value);
    }

    public double getRotate() {
        return getRawAxis(Axis.Rotate.value);
    }

    public enum Button {
        f1(1),
        f2(2),
        b1(3),
        b2(4),
        x(5),
        a(6),
        b(7),
        y(8),
        b3(9),
        b4(10),
        menu(12),
        screenshot(11),
        prev(13),
        next(14),
        share(15);

        public final int value;

        Button(int value) {
            this.value = value;
        }
    }

    public enum Axis {
        Throttle(2),
        JoystickY(1),
        JoystickX(0),
        Rotate(5);

        /** Axis value. */
        public final int value;

        Axis(int value) {
            this.value = value;
        }

    }

    public Trigger f1() {
        return button(Button.f1.value);
    }

    public  Trigger f2() {
        return button(Button.f2.value);
    }

    public  Trigger b1() {
        return button(Button.b1.value);
    }

    public  Trigger b2() {
        return button(Button.b2.value);
    }

    public  Trigger b3() {
        return button(Button.b3.value);
    }

    public  Trigger b4() {
        return button(Button.b4.value);
    }

    public Trigger x() {
        return button(Button.x.value);
    }

    public  Trigger a() {
        return button(Button.a.value);
    }

    public  Trigger b() {
        return button(Button.b.value);
    }


    public  Trigger y() {
        return button(Button.y.value);
    }

    public Trigger menu() {
        return button(Button.menu.value);
    }

    public Trigger screenshot() {
        return button(Button.screenshot.value);
    }

    public Trigger prev() {
        return button(Button.prev.value);
    }

    public Trigger next() {
        return button(Button.next.value);
    }

    public Trigger share() {
        return button(Button.share.value);
    }


}
