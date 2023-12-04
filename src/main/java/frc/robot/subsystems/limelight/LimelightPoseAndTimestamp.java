package frc.robot.subsystems.limelight;

import edu.wpi.first.math.geometry.Pose2d;

public class LimelightPoseAndTimestamp {
  Pose2d pose;
  double timestamp;

  public LimelightPoseAndTimestamp(Pose2d pose, double timestamp) {
    this.pose = pose;
    this.timestamp = timestamp;
  }

  public Pose2d getPose() {
    return this.pose;
  }

  public double getTimestamp() {
    return this.timestamp;
  }
}
