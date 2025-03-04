// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.constants;

import static edu.wpi.first.units.Units.*;

import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.LimitSwitchConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.units.measure.Mass;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;

public class Superstructure {

  public static final class CANIds {

    public static final int kElevatorMotorCanId = 9;
    public static final int kElevatorMotorFollowerCanId = 10;

    public static final int kWristMotorCanId = 11;
    public static final int kTakeMotorCanId = 12;

  }

  public static final class Configs {

    public static final SparkMaxConfig WristConfig = new SparkMaxConfig();
    public static final SparkMaxConfig elevatorConfig = new SparkMaxConfig();
    public static final SparkMaxConfig elevatorFollowerConfig = new SparkMaxConfig();
    public static final SparkMaxConfig takeConfig = new SparkMaxConfig();

    static {
      // Configure basic settings of the Wrist motor
      WristConfig
          .idleMode(IdleMode.kBrake)
          .smartCurrentLimit(40)
          .voltageCompensation(12);
      WristConfig.absoluteEncoder.inverted(true).zeroCentered(false);
      // elevatorConfig.encoder.positionConversionFactor(0.048676).velocityConversionFactor(0.048676);
      elevatorConfig.idleMode(IdleMode.kBrake).inverted(true).smartCurrentLimit(60).voltageCompensation(12);
      elevatorFollowerConfig.smartCurrentLimit(60).follow(CANIds.kElevatorMotorCanId, true);
      takeConfig.idleMode(IdleMode.kBrake);
      takeConfig.limitSwitch.forwardLimitSwitchType(LimitSwitchConfig.Type.kNormallyOpen).forwardLimitSwitchEnabled(false);
      takeConfig.closedLoop.pidf(5, 0,0, 1.1);
      takeConfig.signals.primaryEncoderVelocityAlwaysOn(true).primaryEncoderPositionAlwaysOn(true);
    }
  }

  public static final class ElevatorConstants {
    public static final double kElevatorkGStage1 = 0.78141;
    public static final double kElevatorkGStage2 = 0.9465;

    public static final double kElevatorkS = 0.8672;
    public static final double kElevatorkV = 1.5766;
    public static final double kElevatorkA = 0.42745;

    public static final double kElevatorkP = 33.899;
    public static final double kElevatorkI = 0;
    public static final double kElevatorkD = 3.9366;

    public static final double kElevatorMaxVelocity = Meters.of(4)
        .per(Second)
        .in(MetersPerSecond);
    public static final double kElevatorMaxAcceleration = Meters.of(3)
        .per(Second)
        .per(Second)
        .in(MetersPerSecondPerSecond);
  }

  public static final class WristConstants {

    public static final double kWristkG = 0.39721;
    public static final double kWristkS = 0.45126;
    public static final double kWristkV = 1.0745;
    public static final double kWristkA = 0.61657;

    public static final double kWristkP = 19.6;
    public static final double kWristkI = 0;
    public static final double kWristkD = 0.4;

    public static final double kWristMaxVelocityRPM = 15;
    public static final double kWristMaxAccelerationRPMperSecond = 10;
    public static final Angle kMinAngle = Degrees.of(0);
    public static final Angle kMaxAngle = Degrees.of(110);

  }

  public static final class PhysicalRobotConstants {

    public static final double kElevatorGearing = 5;
    public static final double kCarriageMass = 6.80388555;
    public static final double kElevatorDrumRadius = (Units.inchesToMeters(2.808000) + Units.inchesToMeters(0.125) * 2)
        / 2.0; // m
    public static final double kMinElevatorCarriageHeightMeters = 0.2286; // m
    public static final double kMinElevatorStage1HeightMeters = 0.9652; // m
    public static final double kMaxElevatorCarriageHeightMeters = 0.9144; // m
    public static final double kMaxElevatorStage1HeightMeters = 1.72794689; // m
    public static final double kCarriageTravelHeightMeters = kMaxElevatorCarriageHeightMeters
        - kMinElevatorCarriageHeightMeters;
    public static final double kStage1TravelHeightMeters = kMaxElevatorStage1HeightMeters
        - kMinElevatorStage1HeightMeters;

    public static final Distance kWristLength = Meters.of(0.45076397);
    public static final Mass kWristMass = Pounds.of(7.5258052);
    public static final double kWristReduction = 32; // TODO: double check
    public static final double kMinAngleRads = -8 * Math.PI;
    public static final double kMaxAngleRads = 8 * Math.PI;
  }

  public static final class Mechanisms {

    public static final Mechanism2d m_mech2d = new Mechanism2d(10, 10);
    public static final MechanismRoot2d m_mech2dRoot = m_mech2d.getRoot(
        "ElevatorWrist Root",
        25,
        0);
    public static final MechanismLigament2d m_elevatorStage1Mech2d = m_mech2dRoot.append(
        new MechanismLigament2d(
            "Elevator Stage 1",
            PhysicalRobotConstants.kMinElevatorStage1HeightMeters,
            90));
    public static final MechanismLigament2d m_elevatorCarriageMech2d = m_elevatorStage1Mech2d.append(
        new MechanismLigament2d(
            "Elevator Carriage",
            PhysicalRobotConstants.kMinElevatorCarriageHeightMeters,
            0));

    public static final MechanismLigament2d m_wristMech2d = m_elevatorCarriageMech2d.append(
        new MechanismLigament2d(
            "Wrist",
            PhysicalRobotConstants.kWristLength.in(Meters),
            180 -
                Units.radiansToDegrees(PhysicalRobotConstants.kMinAngleRads) -
                180));
  }
}
