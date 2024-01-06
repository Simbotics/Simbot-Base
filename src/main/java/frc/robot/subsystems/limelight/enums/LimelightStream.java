package frc.robot.subsystems.limelight.enums;

import java.util.HashMap;
import java.util.Map;

public enum LimelightStream {
  STANDARD(0), // side-by-side streams if a webcam is attached to Limelight
  PIP_MAIN(1), // secondary camera stream in the lower-right corner of the
  // primary camera stream
  PIP_SECONDARY(2); // primary camera stream in the lower-right corner of the
  // secondary camera stream

  private int networkTableValue;
  private static Map<Integer, LimelightStream> streamMap = new HashMap<>();

  private LimelightStream(int networkTableValue) {
    this.networkTableValue = networkTableValue;
  }

  static {
    for (LimelightStream limelightStream : LimelightStream.values()) {
      streamMap.put(limelightStream.networkTableValue, limelightStream);
    }
  }

  public static LimelightStream valueOf(int limelightNetworkTableValue) {
    return streamMap.get(limelightNetworkTableValue);
  }

  public int getNetworkTableValue() {
    return this.networkTableValue;
  }
}
