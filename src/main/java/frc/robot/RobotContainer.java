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
    private double MaxSpeed = 6; // 6 meters per second desired top speed
    private double MaxAngularRate = Math.PI; // Half a rotation per second max angular velocity

    /* Setting up bindings for necessary control of the swerve drive platform */
    private final CommandController driver = new CommandController(0); // Driver Controller
    private final CommandController operator = new CommandController(1); // Operator Controller
    final DriveSubsystem drivetrain = DriveConstants.DriveTrain; // My drivetrain
    private final LEDSubsystem ledSubsystem;
    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // I want field-centric
    // driving in open loop

    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    private final SwerveRequest.RobotCentric forwardStraight = new SwerveRequest.RobotCentric()
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage);
    private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

    /* Path follower */
    private Command runAuto = drivetrain.getAutoPath("Tests");

    private final Telemetry logger = new Telemetry(MaxSpeed);

    private void configureBindings() {
        ledSubsystem.setDefaultCommand(new InstantCommand(() -> ledSubsystem.periodic(), ledSubsystem));

        drivetrain.setDefaultCommand( // Drivetrain will execute this command periodically
                drivetrain
                        .applyRequest(
                                () -> drive
                                        .withVelocityX(-driver.getLeftY() * MaxSpeed) // Drive forward with
                                        // negative Y (forward)
                                        .withVelocityY(
                                                -driver.getLeftX() * MaxSpeed) // Drive left with negative X (left)
                                        .withRotationalRate(
                                                -driver.getRightX()
                                                        * MaxAngularRate) // Drive counterclockwise with negative X
                                                                          // (left)
                        )
                        .ignoringDisable(true));

        driver.greenButton().whileTrue(drivetrain.applyRequest(() -> brake));
        driver
                .yellowButton()
                .whileTrue(
                        drivetrain.applyRequest(
                                () -> point.withModuleDirection(
                                        new Rotation2d(-driver.getLeftY(), -driver.getLeftX()))));

        // if (Utils.isSimulation()) {
        // drivetrain.seedFieldRelative(new Pose2d(new Translation2d(),
        // Rotation2d.fromDegrees(90)));
        // }
        drivetrain.registerTelemetry(logger::telemeterize);
        driver.POVUp()
                .whileTrue(
                        drivetrain.applyRequest(() -> forwardStraight.withVelocityX(0.5).withVelocityY(0)));
        driver.POVDown()
                .whileTrue(
                        drivetrain.applyRequest(() -> forwardStraight.withVelocityX(-0.5).withVelocityY(0)));
    }

    public RobotContainer() {
        configureBindings();
        ledSubsystem = new LEDSubsystem();
    }

    public Command getAutonomousCommand() {
        /* First put the drivetrain into auto run mode, then run the auto */
        return runAuto;
    }

    public boolean seedPoseButtonDown() {
        return driver.leftBumper().getAsBoolean();
    }
}
