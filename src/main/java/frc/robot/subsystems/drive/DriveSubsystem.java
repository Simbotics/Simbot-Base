package frc.robot.subsystems.drive;

import java.util.function.Supplier;

import com.ctre.phoenix6.Utils;
import com.ctre.phoenix6.mechanisms.swerve.SwerveDrivetrain;
import com.ctre.phoenix6.mechanisms.swerve.SwerveDrivetrainConstants;
import com.ctre.phoenix6.mechanisms.swerve.SwerveModuleConstants;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.util.HolonomicPathFollowerConfig;
import com.pathplanner.lib.util.PIDConstants;
import com.pathplanner.lib.util.ReplanningConfig;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class DriveSubsystem extends SwerveDrivetrain implements Subsystem {
  private Notifier m_simNotifier = null;
  private double m_lastSimTime;

  private SwerveRequest.ApplyChassisSpeeds autoRequest = new SwerveRequest.ApplyChassisSpeeds();

  @Override
  public void simulationPeriodic() {
    updateSimState(0.02, 12);
  }

  /**
   * Configures path planner to have a holonomic path follower so it can move in any direction. This
   * is meant for holonomic drivetrains AND swerve
   */
  private void configurePathPlanner() {
    double driveBaseRadius = 0;
    for (var moduleLocation : m_moduleLocations) {
      driveBaseRadius = Math.max(driveBaseRadius, moduleLocation.getNorm());
    }

    AutoBuilder.configureHolonomic(
        this::getPose, // Supplier of current robot pose
        this::seedFieldRelative, // Consumer for seeding pose against auto
        this::getCurrentRobotChassisSpeeds,
        (speeds) ->
            this.setControl(
                autoRequest.withSpeeds(speeds)), // Consumer of ChassisSpeeds to drive the robot
        new HolonomicPathFollowerConfig(
            new PIDConstants(10, 0, 0),
            new PIDConstants(10, 0, 0),
            DriveConstants.kSpeedAt12VoltsMps,
            driveBaseRadius,
            new ReplanningConfig()),
        () -> {
          var alliance = DriverStation.getAlliance();
          if (!alliance.isPresent()) {
            return false;
          }
          return alliance.get() == DriverStation.Alliance.Red;
        },
        this);
  }

  /**
   * Applies and runs a command on the swerve drivetrain
   *
   * @param requestSupplier The supplier of the swerve request to apply (the request)
   * @return A command which runs the specified request
   */
  public Command applyRequest(Supplier<SwerveRequest> requestSupplier) {
    return new RunCommand(
        () -> {
          this.setControl(requestSupplier.get());
        },
        this);
  }

  /**
   * Returns the current pose of the robot
   *
   * @return The robots pose in a Pose2d object
   */
  public Pose2d getPose() {
    return this.getState().Pose;
  }

  /**
   * Returns a command that will run the specified pathplanner path
   *
   * @param pathName The name of the path created in pathplanner
   * @return A command which runs the specified path
   */
  public Command getAutoPath(String pathName) {
    return new PathPlannerAuto(pathName);
  }

  /**
   * Takes the specified location and makes it the current pose for field-relative maneuvers
   *
   * @param location Pose to make the current pose at.
   */
  @Override
  public void seedFieldRelative(Pose2d location) {
    try {
      m_stateLock.writeLock().lock();

      m_odometry.resetPosition(
          Rotation2d.fromDegrees(m_pigeon2.getYaw().getValue()), m_modulePositions, location);
    } finally {
      m_stateLock.writeLock().unlock();
    }
  }

  /**
   * Returns the current robot chassis speeds
   *
   * @return The current robot chassis speeds as a Twist2d object
   */
  public ChassisSpeeds getCurrentRobotChassisSpeeds() {
    return m_kinematics.toChassisSpeeds(getState().ModuleStates);
  }

  /**
   * Updates the state of the drivetrain for simulation
   *
   * @param deltaTime The time since the last update
   * @param batteryVoltage The current battery voltage
   */
  private void startSimThread() {
    m_lastSimTime = Utils.getCurrentTimeSeconds();

    /* Run simulation at a faster rate so PID gains behave more reasonably */
    m_simNotifier =
        new Notifier(
            () -> {
              final double currentTime = Utils.getCurrentTimeSeconds();
              double deltaTime = currentTime - m_lastSimTime;
              m_lastSimTime = currentTime;

              /* use the measured time delta, get battery voltage from WPILib */
              updateSimState(deltaTime, RobotController.getBatteryVoltage());
            });
    m_simNotifier.startPeriodic(DriveConstants.kSimLoopPeriod);
  }

  /** Add any methods you want to call when the drive subsystem is initialized and called */
  public void initialize() {
    configurePathPlanner();
  }

  /** Initialize the drive subsystem when we create an instance of it and configure it * */
  public DriveSubsystem(
      SwerveDrivetrainConstants driveTrainConstants,
      double OdometryUpdateFrequency,
      SwerveModuleConstants... modules) {
    super(driveTrainConstants, OdometryUpdateFrequency, modules);
    this.initialize();

    if (Utils.isSimulation()) {
      startSimThread();
    }
  }

  /** Initialize the drive subsystem when we create an instance of it and configure it * */
  public DriveSubsystem(
      SwerveDrivetrainConstants driveTrainConstants, SwerveModuleConstants... modules) {
    super(driveTrainConstants, modules);
    this.initialize();

    if (Utils.isSimulation()) {
      startSimThread();
    }
  }
}
