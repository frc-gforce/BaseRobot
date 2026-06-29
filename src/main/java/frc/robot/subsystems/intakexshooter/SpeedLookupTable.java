package frc.robot.subsystems.intakexshooter;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;
import frc.robot.utils.LoggedTunableNumber;

public class SpeedLookupTable {
    public final InterpolatingDoubleTreeMap speeds;
    LoggedTunableNumber shootingOverride = new LoggedTunableNumber("ShootingOverride", 0, true);

    public SpeedLookupTable() {
        speeds = new InterpolatingDoubleTreeMap();

        speeds.put(1.4, 2500.0);
        speeds.put(1.98, 2700.0);
        speeds.put(2.53, 3000.0);
//        speeds.put(1.452, 3450.0);
//        speeds.put(2.044, 3600.0);
//        speeds.put(1.910, 3660.0);
//        speeds.put(2.3249, 3700.0);
//        speeds.put(2.680, 4000.0);
    }

    public double getSpeed(double distance) {
        if (shootingOverride.getAsDouble() == 0) {
            return speeds.get(distance);
        } else {
            return shootingOverride.getAsDouble();
        }
    }


    public double getSpeed(Pose2d robot, Translation2d hub) {
        return getSpeed(robot.getTranslation().getDistance(hub));
    }

    public double getSpeed(Translation2d robot, Translation2d hub) {
        return getSpeed(robot.getDistance(hub));
    }
}
