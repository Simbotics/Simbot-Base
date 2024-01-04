// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.mechanisms.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.drive.DriveConstants;
import frc.robot.subsystems.drive.DriveSubsystem;
import frc.robot.subsystems.led.LEDSubsystem;

public class RobotContainer {
  private class Controller {
    public static final CommandController driver = new CommandController(0);
    public static final CommandController operator = new CommandController(1);
  }

  private final LEDSubsystem ledSubsystem;

  // Set up the base for the drive and drivetrain
  final DriveSubsystem drivetrain = DriveConstants.DriveTrain;
  private final SwerveRequest.FieldCentric drive =
      new SwerveRequest.FieldCentric()
          .withDeadband(DriveConstants.kMaxAngularRate * 0.1)
          .withRotationalDeadband(DriveConstants.kMaxAngularRate * 0.1)
          .withDriveRequestType(DriveRequestType.OpenLoopVoltage);

    /* Path follower */
    private Command runAuto = drivetrain.getAutoPath("Tests");

  private final Telemetry logger = new Telemetry(DriveConstants.kMaxSpeed);

    private void configureBindings() {
        ledSubsystem.setDefaultCommand(new InstantCommand(() -> ledSubsystem.periodic(), ledSubsystem));
        drivetrain.registerTelemetry(logger::telemeterize);

        drivetrain.setDefaultCommand(
                drivetrain
                        .applyRequest(
                                () -> drive
                                        .withVelocityX(-Controller.driver.getLeftY() * DriveConstants.kMaxSpeed) // Drive forward with
                                        // negative Y (forward)
                                        .withVelocityY(
                                                -Controller.driver.getLeftX() * DriveConstants.kMaxSpeed) // Drive left with negative X (left)
                                        .withRotationalRate(
                                                -Controller.driver.getRightX()
                                                        * DriveConstants.kMaxAngularRate) // Drive counterclockwise with negative X
                                                                          // (left)
                        )
                        .ignoringDisable(true));
    }

  public RobotContainer() {
    configureBindings();
    ledSubsystem = new LEDSubsystem();
  }

  public Command getAutonomousCommand() {
    return runAuto;
  }
}
