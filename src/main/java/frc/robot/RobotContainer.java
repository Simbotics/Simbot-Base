// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.intake.IntakeSubsystem;
import frc.robot.subsystems.intake.enums.IntakeGamepieces;
import frc.robot.subsystems.intake.enums.IntakeScoreType;
import frc.robot.subsystems.led.LEDSubsystem;

public class RobotContainer {
  final double MaxSpeed = 6; // 6 meters per second desired top speed
  final double MaxAngularRate = Math.PI; // Half a rotation per second max angular velocity

  /* Setting up bindings for necessary control of the swerve drive platform */
  CommandController driver = new CommandController(0); // Driver Controller
  CommandController operator = new CommandController(1); // Operator Controller
  
  private final LEDSubsystem ledSubsystem;
  private final IntakeSubsystem intakeSubsystem;

  PowerDistribution pdp = new PowerDistribution();
  
  CommandSwerveDrivetrain drivetrain = TunerConstants.DriveTrain; // My drivetrain
  SwerveRequest.FieldCentric drive =
      new SwerveRequest.FieldCentric()
          .withIsOpenLoop(true)
          .withDeadband(MaxSpeed * 0.1)
          .withRotationalDeadband(MaxAngularRate * 0.1); // I want field-centric
  // driving in open loop
  SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
  SwerveRequest.RobotCentric forwardStraight =
      new SwerveRequest.RobotCentric().withIsOpenLoop(true);
  SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

  /* Path follower */
  // Command runAuto = drivetrain.getAutoPath("Tests");

  Telemetry logger = new Telemetry(MaxSpeed);

  Pose2d odomStart = new Pose2d(0, 0, new Rotation2d(0, 0));

  private void configureBindings() {
    ledSubsystem.setDefaultCommand(new InstantCommand(() -> ledSubsystem.periodic(), ledSubsystem));
    intakeSubsystem.setDefaultCommand(new InstantCommand(() -> intakeSubsystem.periodic(), intakeSubsystem));

    drivetrain.setDefaultCommand( // Drivetrain will execute this command periodically
        drivetrain
            .applyRequest(
                () ->
                    drive
                        .withVelocityX(-driver.getLeftY() * MaxSpeed) // Drive forward with
                        // negative Y (forward)
                        .withVelocityY(
                            -driver.getLeftX() * MaxSpeed) // Drive left with negative X (left)
                        .withRotationalRate(
                            -driver.getRightX()
                                * MaxAngularRate) // Drive counterclockwise with negative X (left)
                )
            .ignoringDisable(true));

    driver.greenButton().whileTrue(drivetrain.applyRequest(() -> brake));
    driver
        .yellowButton()
        .whileTrue(
            drivetrain.applyRequest(
                () ->
                    point.withModuleDirection(
                        new Rotation2d(-driver.getLeftY(), -driver.getLeftX()))));

    // if (Utils.isSimulation()) {
    //   drivetrain.seedFieldRelative(new Pose2d(new Translation2d(), Rotation2d.fromDegrees(90)));
    // }
    drivetrain.registerTelemetry(logger::telemeterize);
    driver.POVUp()
        .whileTrue(
            drivetrain.applyRequest(() -> forwardStraight.withVelocityX(0.5).withVelocityY(0)));
    driver.POVDown()
        .whileTrue(
            drivetrain.applyRequest(() -> forwardStraight.withVelocityX(-0.5).withVelocityY(0)));

    operator.yellowButton().toggleOnTrue(intakeSubsystem.intakeHoldCommand(IntakeGamepieces.CONE));
    operator.blueButton().toggleOnTrue(intakeSubsystem.intakeHoldCommand(IntakeGamepieces.CUBE));
    operator.greenButton().toggleOnTrue(intakeSubsystem.intakeScoreCommand(IntakeScoreType.HIGH_CUBE, IntakeGamepieces.CONE));
  }

  public RobotContainer() {
    configureBindings();
    ledSubsystem = new LEDSubsystem();
    intakeSubsystem = new IntakeSubsystem(pdp);
  }

  public Command getAutonomousCommand() {
    /* First put the drivetrain into auto run mode, then run the auto */
    return new InstantCommand(() -> {});
  }

  public boolean seedPoseButtonDown() {
    return driver.leftBumper().getAsBoolean();
  }
}
