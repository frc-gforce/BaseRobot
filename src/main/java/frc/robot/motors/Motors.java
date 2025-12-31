package frc.robot.motors;

import com.revrobotics.spark.SparkLowLevel.MotorType;

/**
 * Enumeration representing all the motors on the robot.
 */
public enum Motors {
    FRONT_LEFT(1, "Front Left Motor", MotorType.kBrushed),
    FRONT_RIGHT(2, "Front Right Motor", MotorType.kBrushed);

    public final int id;
    public final String name;
    public final MotorType type;

    Motors(int id, String name, MotorType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    Motors(int id, String name) {
        this(id, name, null);
    }
}
