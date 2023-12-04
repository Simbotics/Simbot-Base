package frc.robot.subsystems.limelight;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.limelight.enums.LimelightLEDMode;
import frc.robot.subsystems.limelight.enums.LimelightStream;

public class Limelight implements LimelightIO {
  private String name; // The name of the limelight
  private NetworkTable networkTable;

  /**
   * Creates a new limelight
   *
   * @param name The name of the limelight
   */
  public Limelight(String name) {
    this.name = name;

    // Initializes and sets camera and pipeline of the limelight
    this.networkTable = NetworkTableInstance.getDefault().getTable(this.name);
    this.networkTable.getEntry("camera").setNumber(0);
    this.networkTable.getEntry("pipeline").setNumber(0);
  }

  @Override
  public void updateData(LimelightIOData limelightIOData) {
    NetworkTableEntry robotPoseEntry;
    double[] dataFromLimelight;

    // Sets the pose of the limelight based on what alliance we are on
    if (DriverStation.getAlliance().get() == DriverStation.Alliance.Blue) {
      robotPoseEntry = this.networkTable.getEntry("botpose_wpiblue");
    } else if (DriverStation.getAlliance().get() == DriverStation.Alliance.Red) {
      robotPoseEntry = this.networkTable.getEntry("botpose_wpired");
    } else {
      robotPoseEntry = this.networkTable.getEntry("botpose");
    }

    dataFromLimelight = robotPoseEntry.getDoubleArray(new double[7]);

    // Create a 3d pose from data from the limelight
    Pose3d pose =
        new Pose3d(
            dataFromLimelight[0],
            dataFromLimelight[1],
            dataFromLimelight[2],
            new Rotation3d(
                Math.toRadians(dataFromLimelight[3]),
                Math.toRadians(dataFromLimelight[4]),
                Math.toRadians(dataFromLimelight[5]))); // .transformBy(cameraOffset);

    // Set if the limelight is locked onto a target
    if (this.networkTable.getEntry("tv").getDouble(0) == 1) {
      limelightIOData.lockedOnTarget = true;
    } else {
      limelightIOData.lockedOnTarget = false;
    }

    // Calculate the latency of the limelight
    double latency = dataFromLimelight[6] / 1000;

    if (limelightIOData.lockedOnTarget) {
      // Set the time that the limelight was updated
      limelightIOData.timestamp = Timer.getFPGATimestamp() - latency;

      limelightIOData.isNewPose = true;

      // Set the target x and y
      limelightIOData.targetX = this.networkTable.getEntry("tx").getDouble(0);
      limelightIOData.targetY = this.networkTable.getEntry("ty").getDouble(0);

      // Set the pose of the limelight (x, y, rotation)
      Pose2d pose2d = pose.toPose2d();
      limelightIOData.x = pose2d.getX();
      limelightIOData.y = pose2d.getY();
      limelightIOData.rotation = pose2d.getRotation().getRadians();
      limelightIOData.pose = pose2d;

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
    return this.name;
  }

  /**
   * Sets the led mode of the limelight
   *
   * @param limelightLEDMode The led mode to set the limelight to (see LimelightLEDMode.java for
   *     options)
   */
  public void setLEDMode(LimelightLEDMode limelightLEDMode) {
    switch (limelightLEDMode) {
      case PIPELINE_DEFAULT -> this.networkTable.getEntry("ledMode").setNumber(0);
      case OFF -> this.networkTable.getEntry("ledMode").setNumber(1);
      case BLINK -> this.networkTable.getEntry("ledMode").setNumber(2);
      case ON -> this.networkTable.getEntry("ledMode").setNumber(3);
      default -> this.networkTable.getEntry("ledMode").setNumber(0); // Default to pipeline default
    }
  }

  /**
   * Sets the streaming mode of the limelight
   *
   * @param stream The streaming mode to set the limelight to as a limelight stream type
   */
  public void setStream(LimelightStream limelightStream) {
    switch (limelightStream) {
      case STANDARD -> this.networkTable.getEntry("stream").setNumber(0);
      case PIP_MAIN -> this.networkTable.getEntry("stream").setNumber(1);
      case PIP_SECONDARY -> this.networkTable.getEntry("stream").setNumber(2);
      default -> this.networkTable.getEntry("stream").setNumber(0); // Default to standard
    }
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
    SmartDashboard.putNumber(this.name + " x", pose.getX());
    SmartDashboard.putNumber(this.name + " y", pose.getY());
    SmartDashboard.putNumber(this.name + " angleDegrees", pose.getRotation().getDegrees());
  }
}
