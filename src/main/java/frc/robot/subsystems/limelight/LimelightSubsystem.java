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

    private final List<LimelightSubsystem.PoseAndTimestamp> results = new ArrayList<>();

    
    private int acceptableTagID;
    private boolean useSingleTag = false;

    /**
     * Initializes cameras and input loggers
     *
     * @param cameras Array of cameras being used
     */
    public LimelightSubsystem(Limelight[] limelights) {
        this.limelights = limelights;
        limelightData = new LimelightIOData[limelights.length];

        for (int i = 0; i < limelights.length; i++) {
            limelightData[i] = new LimelightIOData();
        }
    }

    @Override
    public void periodic() {

        // clear results from last periodic
        results.clear();

        for (int i = 0; i < limelightData.length; i++) {
            // update and process new inputs
            limelights[i].updateData(limelightData[i]);

            if (limelightData[i].lockedOnTarget
                    && limelightData[i].isNewPose
                    && !DriverStation.isAutonomous()
                    && limelightData[i].maxDistance < LimelightConstants.LOWEST_DISTANCE) {
                if (useSingleTag) {
                    if (limelightData[i].singleIDUsed == acceptableTagID) {
                        processLimelight(i);
                    }
                } else {
                    processLimelight(i);
                }
            }
        }
    }

    public void processLimelight(int limelightNumber) {
        // Create a new pose based off the new limelight data
        Pose2d currentPose =
                new Pose2d(limelightData[limelightNumber].x, limelightData[limelightNumber].y, new Rotation2d(limelightData[limelightNumber].rotation));

        // Add the new pose to the list of poses
        results.add(new PoseAndTimestamp(currentPose, limelightData[limelightNumber].timestamp));
    }

    /**
     * Returns the last recorded pose
     */
    public List<LimelightSubsystem.PoseAndTimestamp> getLimelightOdometry() {
        return results;
    }
    
    /**
     * Inner class to record a pose and its timestamp
     */
    public static class PoseAndTimestamp {
        Pose2d pose;
        double timestamp;

        public PoseAndTimestamp(Pose2d pose, double timestamp) {
            this.pose = pose;
            this.timestamp = timestamp;
        }

        public Pose2d getPose() {
            return pose;
        }

        public double getTimestamp() {
            return timestamp;
        }
    }

    public void setUseSingleTag(boolean useSingleTag) {
        setUseSingleTag(useSingleTag, 0);
    }

    public void setUseSingleTag(boolean useSingleTag, int acceptableTagID) {
        this.useSingleTag = useSingleTag;
        this.acceptableTagID = acceptableTagID;
    }

    public void setReferencePose(Pose2d pose) {
        for (Limelight limelight : limelights) {
            limelight.setRefrencePose(pose);
        }
    }

    public double getMinDistance(int camera) {
        return limelightData[camera].minDistance;
    }
}
