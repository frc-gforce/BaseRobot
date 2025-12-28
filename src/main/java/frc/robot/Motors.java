package frc.robot;

import com.revrobotics.spark.SparkMax;

public enum Motors {
    test,
    leftFront(1),
    rightFront(2);

    public final SparkMax motor;

    Motors() {
        motor = null;
    }

    Motors(int id) {
        motor = new SparkMax(id, SparkMax.MotorType.kBrushed);
    }
}
