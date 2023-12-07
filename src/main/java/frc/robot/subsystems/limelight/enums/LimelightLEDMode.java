package frc.robot.subsystems.limelight.enums;

import java.util.HashMap;
import java.util.Map;

public enum LimelightLEDMode {
  PIPELINE_DEFAULT(0),
  OFF(1),
  BLINK(2),
  ON(3);

  private int networkTableValue;
  private static Map<Integer, LimelightLEDMode> ledModeMap = new HashMap<>();

  private LimelightLEDMode(int networkTableValue) {
    this.networkTableValue = networkTableValue;
  }

  static {
    for (LimelightLEDMode limelightLEDMode : LimelightLEDMode.values()) {
      ledModeMap.put(limelightLEDMode.networkTableValue, limelightLEDMode);
    }
  }

  public static LimelightLEDMode valueOf(int limelightNetworkTableValue) {
    return ledModeMap.get(limelightNetworkTableValue);
  }

  public int getNetworkTableValue() {
    return this.networkTableValue;
  }
}
