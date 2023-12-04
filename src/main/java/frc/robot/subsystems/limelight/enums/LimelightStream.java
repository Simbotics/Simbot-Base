package frc.robot.subsystems.limelight.enums;

public enum LimelightStream {
  STANDARD, // 0 in the network table; side-by-side streams if a webcam is attached to Limelight
  PIP_MAIN, // 1 in the network table; secondary camera stream in the lower-right corner of the
            // primary camera stream
  PIP_SECONDARY // 2 in the network table; primary camera stream in the lower-right corner of the
                // secondary camera stream
}
