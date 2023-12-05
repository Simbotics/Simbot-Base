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

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public Rotation3d getRotation() {
        return new Rotation3d(
            Math.toRadians(this.roll),
            Math.toRadians(this.pitch),
            Math.toRadians(this.yaw)
        );
    }

    public double getRawTotalLatency() {
        return this.totalLatency;
    }

    public double getTotalLatency() {
        return this.totalLatency / 1000;
    }

    public Pose3d toPose3d() {
        return new Pose3d(
            this.x,
            this.y,
            this.z,
            new Rotation3d(
                Math.toRadians(this.roll),
                Math.toRadians(this.pitch),
                Math.toRadians(this.yaw)
            )
        );
    }
}
