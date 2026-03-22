package frc.robot.subsystems.intakexshooter;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;

public class SpeedLookupTable {
    public final InterpolatingDoubleTreeMap speeds;

    public SpeedLookupTable() {
        speeds = new InterpolatingDoubleTreeMap();

        speeds.put(1.452, 3400.0);
        speeds.put(2.044, 3600.0);
        speeds.put(2.3249, 3700.0);
    }

    public double getSpeed(double distance) {
        return speeds.get(distance);
    }

    public double getSpeed(Pose2d robot, Translation2d hub) {
        return getSpeed(robot.getTranslation().getDistance(hub));
    }

    public double getSpeed(Translation2d robot, Translation2d hub) {
        return getSpeed(robot.getDistance(hub));
    }
}
