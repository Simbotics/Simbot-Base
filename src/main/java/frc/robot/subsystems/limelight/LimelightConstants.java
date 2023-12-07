package frc.robot.subsystems.limelight;

import edu.wpi.first.math.util.Units;

public class LimelightConstants {
  public static final double LOWEST_DISTANCE = Units.feetToMeters(10.0);

  static class TableConstants {
    /**
     * All tables for limelights can be found here
     * https://docs.limelightvision.io/docs/docs-limelight/apis/complete-networktables-api
     */
    public static final String CAMERA = "camera";

    public static final String PIPELINE = "pipeline";
    public static final String LED_MODE = "ledMode";
    public static final String STREAM = "stream";

    // Driverstation/botposes
    public static final String BOT_POSE_BLUE = "botpose_wpiblue";
    public static final String BOT_POSE_RED = "botpose_wpired";
    public static final String BOT_POSE = "botpose";

    // Targeting related
    public static final String VALID_TARGET = "tv";
    public static final String TARGET_X = "tx";
    public static final String TARGET_Y = "ty";
  }
}
