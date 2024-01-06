package frc.robot.subsystems.drive.swerve;

import com.ctre.phoenix6.mechanisms.swerve.SwerveModuleConstants;

import frc.robot.subsystems.drive.DriveConstants;
import frc.robot.subsystems.drive.DriveConstants.DriveMotorId;
import frc.robot.subsystems.drive.DriveConstants.EncoderId;
import frc.robot.subsystems.drive.DriveConstants.EncoderOffset;
import frc.robot.subsystems.drive.DriveConstants.SteerMotorId;
import frc.robot.subsystems.drive.DriveConstants.SwerveModulePosition;

/** This class houses the constants and assembled swerve module configurations for the robot */
public class SwerveModules {
  public static final SwerveModuleConstants FrontLeft =
      DriveConstants.ConstantCreator.createModuleConstants(
          SteerMotorId.kFrontLeftSteerMotorId,
          DriveMotorId.kFrontLeftDriveMotorId,
          EncoderId.kFrontLeftEncoderId,
          EncoderOffset.kFrontLeftEncoderOffset,
          SwerveModulePosition.kFrontLeft.getXPositionCalculated(),
          SwerveModulePosition.kFrontLeft.getXPositionCalculated(),
          DriveConstants.kInvertLeftSide);

  public static final SwerveModuleConstants FrontRight =
      DriveConstants.ConstantCreator.createModuleConstants(
          SteerMotorId.kFrontRightSteerMotorId,
          DriveMotorId.kFrontRightDriveMotorId,
          EncoderId.kFrontRightEncoderId,
          EncoderOffset.kFrontRightEncoderOffset,
          SwerveModulePosition.kFrontRight.getXPositionCalculated(),
          SwerveModulePosition.kFrontRight.getXPositionCalculated(),
          DriveConstants.kInvertRightSide);

  public static final SwerveModuleConstants BackLeft =
      DriveConstants.ConstantCreator.createModuleConstants(
          SteerMotorId.kBackLeftSteerMotorId,
          DriveMotorId.kBackLeftDriveMotorId,
          EncoderId.kBackLeftEncoderId,
          EncoderOffset.kBackLeftEncoderOffset,
          SwerveModulePosition.kBackLeft.getXPositionCalculated(),
          SwerveModulePosition.kBackLeft.getXPositionCalculated(),
          DriveConstants.kInvertLeftSide);

  public static final SwerveModuleConstants BackRight =
      DriveConstants.ConstantCreator.createModuleConstants(
          SteerMotorId.kBackRightSteerMotorId,
          DriveMotorId.kBackRightDriveMotorId,
          EncoderId.kBackRightEncoderId,
          EncoderOffset.kBackRightEncoderOffset,
          SwerveModulePosition.kBackRight.getXPositionCalculated(),
          SwerveModulePosition.kBackRight.getXPositionCalculated(),
          DriveConstants.kInvertRightSide);
}
