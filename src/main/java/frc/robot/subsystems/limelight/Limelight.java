package frc.robot.subsystems.limelight;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.limelight.enums.LimelightLEDMode;
import frc.robot.subsystems.limelight.enums.LimelightStream;

public class Limelight implements LimelightIO {
  private String limelightName;
  private NetworkTable networkTable;

  /**
   * Creates a new limelight
   *
   * @param limelightName The name of the limelight
   */
  public Limelight(String limelightName) {
    this.limelightName = limelightName;

    // Initializes and sets camera and pipeline of the limelight
    this.networkTable = NetworkTableInstance.getDefault().getTable(this.limelightName);
    this.networkTable.getEntry("camera").setNumber(0);
    this.networkTable.getEntry("pipeline").setNumber(0);
  }

  @Override
  public void updateData(LimelightIOData limelightIOData) {
    NetworkTableEntry robotPoseEntry;

    // Sets the pose of the limelight based on what alliance we are on
    if (DriverStation.getAlliance().get() == DriverStation.Alliance.Blue) {
      robotPoseEntry = this.networkTable.getEntry("botpose_wpiblue");
    } else if (DriverStation.getAlliance().get() == DriverStation.Alliance.Red) {
      robotPoseEntry = this.networkTable.getEntry("botpose_wpired");
    } else {
      robotPoseEntry = this.networkTable.getEntry("botpose");
    }

    LimelightPoseData limelightPoseData =
        new LimelightPoseData(robotPoseEntry.getDoubleArray(new double[7]));

    // Create a 3d pose from data from the limelight
    Pose3d limelightPose = limelightPoseData.toPose3d();

    // Set if the limelight is locked onto a target
    if (this.networkTable.getEntry("tv").getDouble(0) == 1) {
      limelightIOData.lockedOnTarget = true;
    } else {
      limelightIOData.lockedOnTarget = false;
    }

    if (limelightIOData.lockedOnTarget) {
      // Set the time that the limelight was updated
      limelightIOData.limelightLastUpdated =
          Timer.getFPGATimestamp() - limelightPoseData.getTotalLatency();

      limelightIOData.isNewPose = true;

      // Set the target x and y
      limelightIOData.targetX = this.networkTable.getEntry("tx").getDouble(0);
      limelightIOData.targetY = this.networkTable.getEntry("ty").getDouble(0);

      // Set the pose of the limelight (x, y, rotation)
      Pose2d pose2d = limelightPose.toPose2d();
      limelightIOData.limelightX = pose2d.getX();
      limelightIOData.limelightY = pose2d.getY();
      limelightIOData.limelightRotation = pose2d.getRotation().getRadians();
      limelightIOData.limelightPose = pose2d;

    } else {
      limelightIOData.isNewPose = false;

      // Set the target x and y to 0 because we don't have a target
      limelightIOData.targetX = 0;
      limelightIOData.targetY = 0;
    }
  }

  /** Gets the name of a limelight that's registered into the network table */
  @Override
  public String getName() {
    return this.limelightName;
  }

  /**
   * Sets the led mode of the limelight
   *
   * @param limelightLEDMode The led mode to set the limelight to (see LimelightLEDMode.java for
   *     options)
   */
  public void setLEDMode(LimelightLEDMode limelightLEDMode) {
    this.networkTable.getEntry("ledMode").setNumber(limelightLEDMode.getNetworkTableValue());
  }

  /**
   * Sets the streaming mode of the limelight
   *
   * @param stream The streaming mode to set the limelight to as a limelight stream type
   */
  public void setStream(LimelightStream limelightStream) {
    this.networkTable.getEntry("stream").setNumber(limelightStream.getNetworkTableValue());
  }

  /**
   * Sets the pipeline of the limelight based on our presets for it
   *
   * @param pipeline The pipeline to set the limelight to as an integer
   */
  public void setPipeline(int pipeline) {
    this.networkTable.getEntry("pipeline").setNumber(pipeline);
  }

  /**
   * Displays the pose values of the limelight on the smart dashboard
   *
   * @param pose The pose to retrieve and display the values from
   */
  public void displayLimelightPoseValues(Pose2d pose) {
    SmartDashboard.putNumber(this.limelightName + " x", pose.getX());
    SmartDashboard.putNumber(this.limelightName + " y", pose.getY());
    SmartDashboard.putNumber(this.limelightName + " angleDegrees", pose.getRotation().getDegrees());
  }
}
