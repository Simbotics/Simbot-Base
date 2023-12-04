package frc.robot.subsystems.limelight;

import edu.wpi.first.math.geometry.Pose2d;

public interface LimelightIO {

    /**
     * Data that the limelight will collect and track
     */
    class LimelightIOData {
        public double x;
        public double y;
        public double rotation;

        public boolean isNewPose;
        public double timestamp;

        public double maxAmbiguity;
        public double maxDistance;
        public double minDistance;

        public boolean lockedOnTarget = false;
        public int singleIDUsed;
        public double singleIDUsedDouble;

        public double translationToTargetX;
        public double translationToTargetY;
    }

    /**
     * Updates the data of the limelight
     * @param data
     */
    default void updateData(LimelightIOData data) {}

    /**
     * Gets, or sets the name of the limelight
     * @return
     */
    default String getName() {
        return "";
    }

    /**
     * Sets the refrence pose of the limelight
     * @param pose
     */
    default void setRefrencePose(Pose2d pose) {}
}
