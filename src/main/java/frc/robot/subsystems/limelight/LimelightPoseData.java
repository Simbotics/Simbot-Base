package frc.robot.subsystems.limelight;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;

public class LimelightPoseData {
  private double x;
  private double y;
  private double z;
  private double roll;
  private double pitch;
  private double yaw;
  private double totalLatency;

  public LimelightPoseData(double[] data) {
    if (data.length != 7) {
      throw new IllegalArgumentException("Data array must have 7 elements");
    }

    this.x = data[0];
    this.y = data[1];
    this.z = data[2];
    this.roll = data[3];
    this.pitch = data[4];
    this.yaw = data[5];
    this.totalLatency = data[6];
  }

  /**
   * Updates the pose data without creating a new instance of limelight pose data
   * @param data The data to update the pose data with
   */
  public void update(double[] data) {
    if (data.length != 7) {
      throw new IllegalArgumentException("Data array must have 7 elements");
    }

    this.x = data[0];
    this.y = data[1];
    this.z = data[2];
    this.roll = data[3];
    this.pitch = data[4];
    this.yaw = data[5];
    this.totalLatency = data[6];
  }

  /**
   * Gets the x position of the limelight
   * @return The x position of the limelight
   */
  public double getX() {
    return this.x;
  }

  /**
   * Gets the y position of the limelight
   * @return The y position of the limelight
   */
  public double getY() {
    return this.y;
  }

  /**
   * Gets the z position of the limelight
   * @return The z position of the limelight
   */
  public double getZ() {
    return this.z;
  }

  /**
   * Gets a 3d rotation position of the limelight
   * @return The 3d rotation position of the limelight
   */
  public Rotation3d getRotation() {
    return new Rotation3d(
        Math.toRadians(this.roll), Math.toRadians(this.pitch), Math.toRadians(this.yaw));
  }

  /**
   * Gets the raw latency of the limelight straight from the network table
   * @return The raw latency of the limelight straight from the network table
   */
  public double getRawTotalLatency() {
    return this.totalLatency;
  }

  /**
   * Gets the total latency of the limelight in seconds
   * @return The total latency of the limelight in seconds
   */
  public double getTotalLatency() {
    return this.totalLatency / 1000;
  }

  /**
   * Converts the limelight pose data to a 3d pose
   * @return A 3d pose of the limelight pose data
   */
  public Pose3d toPose3d() {
    return new Pose3d(
        this.x,
        this.y,
        this.z,
        new Rotation3d(
            Math.toRadians(this.roll), Math.toRadians(this.pitch), Math.toRadians(this.yaw)));
  }
}
