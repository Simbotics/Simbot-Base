package frc.robot.subsystems.drive;

import com.ctre.phoenix6.mechanisms.swerve.SwerveDrivetrainConstants;
import com.ctre.phoenix6.mechanisms.swerve.SwerveModuleConstantsFactory;
import com.ctre.phoenix6.mechanisms.swerve.SwerveModuleConstants.SteerFeedbackType;

import frc.robot.subsystems.drive.swerve.SwerveModuleLocation;
import frc.robot.subsystems.drive.swerve.SwerveModules;

public class DriveConstants {
  /** Only touch these if you know what you're doing */
  private static final SlotGains steerGains = new SlotGains(100, 0, 0.05, 0, 0);

  private static final SlotGains driveGains = new SlotGains(3, 0, 0, 0, 0);

  private static final double kCoupleRatio = 3.5;

  private static final double kWheelRadiusInches =
      2.167; // Estimated at first, then fudge-factored to make odom
  // match record

  private static final String kCANbusName = "rio";
  private static final int kPigeonId = 1;

  public static final double kSpeedAt12VoltsMps = 5.0;

  private static final boolean kSteerMotorReversed = true;
  public static final boolean kInvertLeftSide = false;
  public static final boolean kInvertRightSide = true;

  /** The ratios of the drive and steer gears in the swerve modules */
  private class GearRatio {
    private static final double kDriveGearRatio = 7.363636364;
    private static final double kSteerGearRatio = 15.42857143;
  }

  /**
   * The simulated voltage necessary to overcome friction for swerve. This is only used for
   * simulation
   */
  private class FrictionVoltage {
    private static final double kSteerFrictionVoltage = 0.25;
    private static final double kDriveFrictionVoltage = 0.25;
  }

  /**
   * The inertia of the drive and steer motors in the swerve modules. These should be left to
   * default
   */
  private class Intertia {
    private static final double kSteerInertia = 0.00001;
    private static final double kDriveInertia = 0.001;
  }

  /**
   * The CAN IDs of the drive motors in the swerve modules NOTE: Be sure to change these to the
   * correct values at the start of the season
   */
  public class DriveMotorId {
    public static final int kFrontLeftDriveMotorId = 5;
    public static final int kFrontRightDriveMotorId = 7;
    public static final int kBackLeftDriveMotorId = 1;
    public static final int kBackRightDriveMotorId = 3;
  }

  /**
   * The CAN IDs of the steer motors in the swerve modules NOTE: Be sure to change these to the
   * correct values at the start of the season
   */
  public class SteerMotorId {
    public static final int kFrontLeftSteerMotorId = 4;
    public static final int kFrontRightSteerMotorId = 6;
    public static final int kBackLeftSteerMotorId = 0;
    public static final int kBackRightSteerMotorId = 2;
  }

  /**
   * The CAN IDs of the encoders in the swerve modules NOTE: Be sure to change these to the correct
   * values at the start of the season
   */
  public class EncoderId {
    public static final int kFrontLeftEncoderId = 2;
    public static final int kFrontRightEncoderId = 3;
    public static final int kBackLeftEncoderId = 0;
    public static final int kBackRightEncoderId = 1;
  }

  public class EncoderOffset {
    public static final double kFrontLeftEncoderOffset = -0.83544921875;
    public static final double kFrontRightEncoderOffset = -0.15234375;
    public static final double kBackLeftEncoderOffset = -0.4794921875;
    public static final double kBackRightEncoderOffset = -0.84130859375;
  }

  /**
   * The X and Y positions of the swerve modules relative to the center of the robot in inches NOTE:
   * Be sure to change these to the correct values at the start of the season
   */
  public class SwerveModulePosition {
    public static final SwerveModuleLocation kFrontLeft = new SwerveModuleLocation(10.5, 10.5);
    public static final SwerveModuleLocation kFrontRight = new SwerveModuleLocation(10.5, -10.5);
    public static final SwerveModuleLocation kBackLeft = new SwerveModuleLocation(-10.5, 10.5);
    public static final SwerveModuleLocation kBackRight = new SwerveModuleLocation(-10.5, -10.5);
  }

  private static final SwerveDrivetrainConstants DrivetrainConstants =
      new SwerveDrivetrainConstants().withPigeon2Id(kPigeonId).withCANbusName(kCANbusName);

  public static final SwerveModuleConstantsFactory ConstantCreator =
      new SwerveModuleConstantsFactory()
          .withDriveMotorGearRatio(GearRatio.kDriveGearRatio)
          .withSteerMotorGearRatio(GearRatio.kSteerGearRatio)
          .withWheelRadius(kWheelRadiusInches)
          .withSlipCurrent(30)
          .withSteerMotorGains(steerGains)
          .withDriveMotorGains(driveGains)
          .withSpeedAt12VoltsMps(
              6) // Theoretical free speed is 10 meters per second at 12v applied output
          .withSteerInertia(Intertia.kSteerInertia)
          .withDriveInertia(Intertia.kDriveInertia)
          .withFeedbackSource(SteerFeedbackType.FusedCANcoder)
          .withSteerFrictionVoltage(FrictionVoltage.kSteerFrictionVoltage)
          .withDriveFrictionVoltage(FrictionVoltage.kDriveFrictionVoltage)
          .withCouplingGearRatio(
              kCoupleRatio) // Every 1 rotation of the azimuth results in couple ratio drive turns
          .withSteerMotorInverted(kSteerMotorReversed);

  /** Construct the final drivetrain object that we interact with */
  public static final DriveSubsystem DriveTrain =
      new DriveSubsystem(
          DrivetrainConstants,
          SwerveModules.FrontLeft,
          SwerveModules.FrontRight,
          SwerveModules.BackLeft,
          SwerveModules.BackRight);
}
