package frc.robot.subsystems.limelight;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

public class Limelight implements LimelightIO {
    private String name; // The name of the limelight

    /**
     * Creates a new limelight
     * 
     * @param name         The name of the limelight
     */
    public Limelight(String name) {
        this.name = name;
    }

    @Override
    public void updateData(LimelightIOData limelightIOData) {
        NetworkTableEntry robotPoseEntry;
        double[] dataFromLimelight;

        // Sets the pose of the limelight based on what alliance we are on
        if (DriverStation.getAlliance().get() == DriverStation.Alliance.Blue) {
            robotPoseEntry = NetworkTableInstance.getDefault().getTable(name).getEntry("botpose_wpiblue");
        } else if (DriverStation.getAlliance().get() == DriverStation.Alliance.Red) {
            robotPoseEntry = NetworkTableInstance.getDefault().getTable(name).getEntry("botpose_wpired");
        } else {
            robotPoseEntry = NetworkTableInstance.getDefault().getTable(name).getEntry("botpose");
        }

        dataFromLimelight = robotPoseEntry.getDoubleArray(new double[7]);

        // Create a 3d pose from data from the limelight
        Pose3d pose = new Pose3d(dataFromLimelight[0], dataFromLimelight[1], dataFromLimelight[2],
                new Rotation3d(
                        Math.toRadians(dataFromLimelight[3]), 
                        Math.toRadians(dataFromLimelight[4]),
                        Math.toRadians(dataFromLimelight[5]))); // .transformBy(cameraOffset);

        // Set if the limelight is locked onto a target
        if (NetworkTableInstance.getDefault().getTable(name).getEntry("tv").getDouble(0) == 1) {
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

            // Set the pose of the limelight (x, y, rotation)
            Pose2d pose2d = pose.toPose2d();
            limelightIOData.x = pose2d.getX();
            limelightIOData.y = pose2d.getY();
            limelightIOData.rotation = pose2d.getRotation().getRadians();
        } else {
            limelightIOData.isNewPose = false;
        }
    }

    @Override
    public String getName() {
        return name;
    }
}
