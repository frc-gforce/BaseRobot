package frc.robot.motors;

public record PID(
        double p,
        double i,
        double d,
        double s,
        double v,
        double a,
        double g
) {
}
