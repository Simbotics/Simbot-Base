package frc.robot.subsystems.limelight;

import edu.wpi.first.math.geometry.Pose2d;

public class LimelightPoseAndTimestamp {
  Pose2d limelightPose;
  double lastUpdatedTimestamp;

  public LimelightPoseAndTimestamp(Pose2d limelightPose, double lastUpdatedTimestamp) {
    this.limelightPose = limelightPose;
    this.lastUpdatedTimestamp = lastUpdatedTimestamp;
  }

  public Pose2d getLimelightPose() {
    return this.limelightPose;
  }

  public double getLastUpdatedTimestamp() {
    return this.lastUpdatedTimestamp;
  }
}
