package frc.robot.subsystems.limelight;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.limelight.LimelightIO.LimelightIOData;

public class LimelightSubsystem extends SubsystemBase {
  private final Limelight[] limelights;
  private final LimelightIOData[] limelightData;

  private final List<LimelightPoseAndTimestamp> results = new ArrayList<>();

  private int acceptableTagID;
  private boolean useSingleTag = false;

  /**
   * Initializes cameras and input loggers
   *
   * @param cameras Array of cameras being used
   */
  public LimelightSubsystem(Limelight[] limelights) {
    this.limelights = limelights;
    this.limelightData = new LimelightIOData[this.limelights.length];

    for (int i = 0; i < this.limelights.length; i++) {
      this.limelightData[i] = new LimelightIOData();
    }
  }

  @Override
  public void periodic() {
    // Clear results from last periodic
    this.results.clear();

    for (int i = 0; i < this.limelightData.length; i++) {
      // update and process new inputs
      this.limelights[i].updateData(limelightData[i]);

      if (this.limelightData[i].lockedOnTarget
          && this.limelightData[i].isNewPose
          && !DriverStation.isAutonomous()
          && this.limelightData[i].maxDistance < LimelightConstants.LOWEST_DISTANCE) {
        if (this.useSingleTag) {
          if (this.limelightData[i].singleIDUsed == this.acceptableTagID) {
            this.processLimelight(i);
          }
        } else {
          this.processLimelight(i);
        }
      }
    }
  }

  /**
   * Processes the limelight data and adds the pose to the list of poses
   *
   * @param limelightNumber The numerical ID of the limelight
   */
  public void processLimelight(int limelightNumber) {
    // Create a new pose based off the new limelight data
    Pose2d currentPose =
        new Pose2d(
            this.limelightData[limelightNumber].limelightX,
            this.limelightData[limelightNumber].limelightY,
            new Rotation2d(this.limelightData[limelightNumber].limelightRotation));

    // Add the new pose to the list of poses
    this.results.add(
        new LimelightPoseAndTimestamp(currentPose, limelightData[limelightNumber].limelightLastUpdated));
  }

  /** Returns the last recorded pose */
  public List<LimelightPoseAndTimestamp> getLimelightOdometry() {
    return this.results;
  }

  public void setUseSingleTag(boolean useSingleTag) {
    this.setUseSingleTag(useSingleTag, 0);
  }

  public void setUseSingleTag(boolean useSingleTag, int acceptableTagID) {
    this.useSingleTag = useSingleTag;
    this.acceptableTagID = acceptableTagID;
  }

  public void setReferencePose(Pose2d pose) {
    for (Limelight limelight : this.limelights) {
      limelight.setRefrencePose(pose);
    }
  }

  public double getMinDistance(int camera) {
    return this.limelightData[camera].minDistance;
  }
}
