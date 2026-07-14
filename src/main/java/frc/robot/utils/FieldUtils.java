package frc.robot.utils;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;

public class FieldUtils {
    public static final Translation2d blueHub = new Translation2d(4.62, 4.03);
    public static final Translation2d redHub = new Translation2d(11.92048, 4.03958);

    public static final double blueAlliance = 3.977894;
    public static final double redAlliance = 12.563096;
    public static Translation2d getHubPose() {
        return isRedTeam() ? redHub : blueHub;
    }

    public static boolean isRedTeam() {
        var alliance = DriverStation.getAlliance();
        if (alliance.isPresent()) {
            return alliance.get() == DriverStation.Alliance.Red;
        }
        return false;
    }

    public static boolean isInAllianceSide(Pose2d pose) {
        return isRedTeam() ? pose.getX() > redAlliance : pose.getX() < blueAlliance;
    }
}
