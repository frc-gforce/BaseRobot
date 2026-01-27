package frc.robot.motors;

import com.revrobotics.spark.SparkLowLevel.MotorType;

/**
 * Enumeration representing all the motors on the robot.
 */
public enum Motors {
    FRONT_LEFT(18, "Front Left Motor", MotorType.kBrushed, MotorBrand.SPARK_MAX, true),
    FRONT_RIGHT(17, "Front Right Motor", MotorType.kBrushed, MotorBrand.SPARK_MAX, false),
    BACK_RIGHT(16, "Back Right Motor", MotorType.kBrushed, MotorBrand.SPARK_MAX, false),
    BACK_LEFT(19, "Back Left Motor", MotorType.kBrushed, MotorBrand.SPARK_MAX, true),
    SHOOT(24, "Shoot Motor", MotorType.kBrushed, MotorBrand.SPARK_MAX, false),
    FEED(23, "Feed Motor", MotorType.kBrushed, MotorBrand.SPARK_MAX, false);

    public final int id;
    public final String name;
    public final MotorType type;
    public final MotorBrand brand;
    public final boolean inverted;

    Motors(int id, String name, MotorType type, MotorBrand brand, boolean inverted) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.brand = brand;
        this.inverted = inverted ;
    }

    Motors(int id, String name, boolean inverted, MotorBrand brand) {
        this(id, name, null, brand, inverted);
    }
}
