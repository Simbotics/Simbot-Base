package frc.robot.subsystems.limelight;

import edu.wpi.first.math.geometry.Pose2d;

public interface LimelightIO {

  /** Data that the limelight will collect and track */
  class LimelightIOData {
    public double limelightX;
    public double limelightY;
    public double limelightRotation;

    public double targetX;
    public double targetY;

    public boolean isNewPose;
    public Pose2d limelightPose;
    public double limelightLastUpdated;

    public double maxDistance;
    public double minDistance;

    public boolean lockedOnTarget = false;
    public int singleIDUsed;

    public double translationToTargetX;
    public double translationToTargetY;
  }

  /**
   * Updates the data of the limelight
   *
   * @param data
   */
  default void updateData(LimelightIOData data) {}

  /**
   * Gets, or sets the name of the limelight
   *
   * @return
   */
  default String getName() {
    return "";
  }

  /**
   * Sets the refrence pose of the limelight
   *
   * @param pose
   */
  default void setRefrencePose(Pose2d pose) {}
}
